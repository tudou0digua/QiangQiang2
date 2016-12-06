package com.cb.qiangqiang2.ui.fragment;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cb.qiangqiang2.R;
import com.cb.qiangqiang2.common.base.BaseActivity;
import com.cb.qiangqiang2.common.base.BaseFragment;
import com.cb.qiangqiang2.data.model.SearchUserResultModel;
import com.cb.qiangqiang2.event.HideSearchHistoryEvent;
import com.cb.qiangqiang2.event.SearchEvent;
import com.cb.qiangqiang2.mvpview.SearchUserMvpView;
import com.cb.qiangqiang2.presenter.SearchUserPresenter;
import com.cb.qiangqiang2.ui.activity.UserInfoActivity;
import com.cb.qiangqiang2.ui.adapter.SearchUserAdapter;
import com.cb.qiangqiang2.ui.adapter.listener.OnItemClickListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

import static com.cb.qiangqiang2.common.util.AppUtils.isScrollToBottom;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchUserFragment extends BaseFragment implements SearchUserMvpView {

    @Inject
    SearchUserPresenter mSearchUserPresenter;
    @Inject
    SearchUserAdapter mAdapter;

    @BindView(R.id.recycle_view)
    RecyclerView mRecycleView;
    @BindView(R.id.progress_bar)
    MaterialProgressBar mProgressBar;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private String searchContent;
    private int nextPage = 2;
    private boolean canLoadingMore = true;
    private boolean hasLoadAllData = false;


    public SearchUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_user, container, false);
        ButterKnife.bind(this, view);
        ((BaseActivity) getActivity()).getActivityComponent().inject(this);
        mSearchUserPresenter.attachView(this);
        initView();
        return view;
    }

    private void initView() {
        //设置mSwipeRefreshLayout
        mSwipeRefreshLayout.setEnabled(false);
        Resources resources = getActivity().getResources();
        mSwipeRefreshLayout.setColorSchemeColors(resources.getColor(R.color.colorPrimary));
        //设置RecycleView
        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        mRecycleView.setAdapter(mAdapter);
        mRecycleView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        mRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                boolean isScrollToBottom = isScrollToBottom(recyclerView);
                if (!hasLoadAllData && canLoadingMore && isScrollToBottom) {
                    canLoadingMore = false;
                    mSearchUserPresenter.searchUser(true, searchContent, nextPage, 10);
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        mRecycleView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                EventBus.getDefault().post(new HideSearchHistoryEvent());
                return false;
            }
        });
        //设置Adapter
        mAdapter.setOnItemClickListener(new OnItemClickListener<SearchUserResultModel.BodyBean.ListBean>() {
            @Override
            public void onItemClick(int position, View view, SearchUserResultModel.BodyBean.ListBean listBean) {
                if (view.getId() == R.id.tv_follow) {
                    // TODO: 2016/12/6 关注 取消关注

                } else {
                    UserInfoActivity.startUserInfoActivity(getActivity(), listBean.getUid(), listBean.getName());
                }
            }
        });
    }

    @Override
    public void refreshData(SearchUserResultModel searchUserResultModel) {
        if (searchUserResultModel.getHas_next() == 0 && nextPage == 2) {
            hasLoadAllData = true;
        }
        nextPage = 2;
        mAdapter.setSearchUserResultModel(searchUserResultModel);
        canLoadingMore = true;
    }

    @Override
    public void refreshEmpty() {
        canLoadingMore = true;
    }

    @Override
    public void loadMoreData(SearchUserResultModel searchUserResultModel) {
        if (searchUserResultModel.getHas_next() == 0
                && (searchUserResultModel.getBody().getList() == null || searchUserResultModel.getBody().getList().size() == 0)) {
            hasLoadAllData = true;
            Toast.makeText(getActivity(), R.string.no_more_data, Toast.LENGTH_SHORT).show();
        } else {
            nextPage++;
        }
        mAdapter.addData(searchUserResultModel);
        canLoadingMore = true;
    }

    @Override
    public void loadMoreEmpty() {
        canLoadingMore = true;
    }

    @Override
    public void loadMoreError(Throwable e) {
        canLoadingMore = true;
        if (e != null) e.printStackTrace();
    }

    @Override
    public void showLoadMoreView() {
        if (mProgressBar.getVisibility() != View.VISIBLE)
            mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadMoreView() {
        if (mProgressBar.getVisibility() == View.VISIBLE)
            mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
        if (!mSwipeRefreshLayout.isRefreshing())
            mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        if (mSwipeRefreshLayout.isRefreshing())
            mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void loadError(Throwable e) {
        canLoadingMore = true;
        if (e != null) e.printStackTrace();
    }

    private void search(String searchContent) {
        this.searchContent = searchContent;
        mSearchUserPresenter.searchUser(false, searchContent, 1, 10);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SearchEvent event) {
        search(event.getSearchContent());
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
        mSearchUserPresenter.detachView();
    }
}
