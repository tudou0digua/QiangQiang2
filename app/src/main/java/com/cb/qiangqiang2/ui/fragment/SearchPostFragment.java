package com.cb.qiangqiang2.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cb.qiangqiang2.R;
import com.cb.qiangqiang2.common.base.BaseActivity;
import com.cb.qiangqiang2.common.util.AppUtils;
import com.cb.qiangqiang2.data.model.SearchPostResultModel;
import com.cb.qiangqiang2.event.HideSearchHistoryEvent;
import com.cb.qiangqiang2.event.SearchEvent;
import com.cb.qiangqiang2.mvpview.SearchPostMvpView;
import com.cb.qiangqiang2.presenter.SearchPostPresenter;
import com.cb.qiangqiang2.ui.activity.WebViewActivity;
import com.cb.qiangqiang2.ui.adapter.SearchPostListAdapter;
import com.cb.qiangqiang2.ui.adapter.listener.OnItemClickListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

/**
 * 搜索帖子
 */
public class SearchPostFragment extends Fragment implements SearchPostMvpView {

    @Inject
    SearchPostPresenter searchPostPresenter;
    @Inject
    SearchPostListAdapter adapter;

    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    @BindView(R.id.pb_load_more)
    MaterialProgressBar pbLoadMore;
    @BindView(R.id.pb_search)
    MaterialProgressBar pbSearch;

    private boolean canLoadingMore = false;
    private int currentPage = 1;
    private String searchContent;

    public SearchPostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_post, container, false);
        ButterKnife.bind(this, view);
        ((BaseActivity) getActivity()).getActivityComponent().inject(this);
        searchPostPresenter.attachView(this);
        initData();
        initView();
        return view;
    }

    private void initData() {
        adapter.setOnItemClickListener(new OnItemClickListener<SearchPostResultModel.ListBean>() {
            @Override
            public void onItemClick(int position, View view, SearchPostResultModel.ListBean listBean) {
                String url =
                        "http://www.qiangqiang5.com/api/mobile/index.php?module=%s&page=%s&charset=%s&image=%s&ppp=%s&debug=%s&tid=%s&mobile=%s&version=%s";
                url = String.format(url,
                        "viewthread",
                        "1",
                        "utf-8",
                        "1",
                        "10",
                        "1",
                        String.valueOf(listBean.getTopic_id()),
                        "no",
                        "3");
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra(WebViewActivity.TITLE, String.valueOf(listBean.getBoard_id()));
                intent.putExtra(WebViewActivity.URL, url);
                getActivity().startActivity(intent);
            }
        });
    }

    private void initView() {
        recycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycleView.setItemAnimator(new DefaultItemAnimator());
        recycleView.setAdapter(adapter);
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                boolean isScrollToBottom = AppUtils.isScrollToBottom(recyclerView);
                if (canLoadingMore && isScrollToBottom) {
                    currentPage++;
                    canLoadingMore = false;
                    searchPostPresenter.searchPost(searchContent, currentPage, 10);
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        recycleView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                EventBus.getDefault().post(new HideSearchHistoryEvent());
                return false;
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SearchEvent event) {
        search(event.getSearchContent());
    }

    private void search(String searchContent) {
        this.searchContent = searchContent;
        currentPage = 1;
        searchPostPresenter.searchPost(this.searchContent, currentPage, 10);
        canLoadingMore = false;
    }

    @Override
    public void searchPostResult(SearchPostResultModel model) {
        if (currentPage == 1) {
            canLoadingMore = true;
        } else {
            if (model.getHas_next() == 1) {
                canLoadingMore = true;
            } else {
                canLoadingMore = false;
                Toast.makeText(getActivity(), "到底了。。。", Toast.LENGTH_SHORT).show();
            }
        }
        if (currentPage == 1) {
            adapter.setData(model);
        } else {
            adapter.addData(model);
        }
    }

    @Override
    public void showLoading() {
        if (currentPage == 1) {
            pbSearch.setVisibility(View.VISIBLE);
            if (pbLoadMore.isShown()) {
                pbLoadMore.setVisibility(View.GONE);
            }
        } else {
            pbLoadMore.setVisibility(View.VISIBLE);
            if (pbSearch.isShown()) {
                pbSearch.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void hideLoading() {
        if (currentPage == 1) {
            pbSearch.setVisibility(View.GONE);
            if (pbLoadMore.isShown()) {
                pbLoadMore.setVisibility(View.GONE);
            }
        } else {
            pbLoadMore.setVisibility(View.GONE);
            if (pbSearch.isShown()) {
                pbSearch.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void loadError(Throwable e) {
        if (e != null) {
            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        searchPostPresenter.detachView();
    }
}
