package com.cb.qiangqiang2.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cb.qiangqiang2.R;
import com.cb.qiangqiang2.common.base.BaseActivity;
import com.cb.qiangqiang2.common.base.BaseFragment;
import com.cb.qiangqiang2.common.util.AppUtils;
import com.cb.qiangqiang2.data.api.ApiService;
import com.cb.qiangqiang2.data.model.PostModel;
import com.cb.qiangqiang2.mvpview.PostMvpView;
import com.cb.qiangqiang2.presenter.PostPresenter;
import com.cb.qiangqiang2.ui.activity.WebViewActivity;
import com.cb.qiangqiang2.ui.adapter.PostListAdapter;
import com.cb.qiangqiang2.ui.adapter.listener.OnItemClickListener;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 帖子列表Fragment
 */
public class PostFragment extends BaseFragment implements PostMvpView {
    private static final String BOARD_ID = "mBoardId";
    private static final String SORT_BY = "sortBy";

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
//    @BindView(R.id.swipe_refresh_layout)
//    WaveSwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recycle_view)
    RecyclerView mRecycleView;

    @Inject
    PostListAdapter mAdapter;
    @Inject
    ApiService apiService;
    @Inject
    PostPresenter postPresenter;

    private int mBoardId = 0;
    private String mSortBy = "";
    private int nextPage = 2;
    private boolean isLoadingMore = false;

    public PostFragment() {
        // Required empty public constructor
    }

    public static PostFragment newInstance(int boardId, String sortBy) {
        PostFragment fragment = new PostFragment();
        Bundle args = new Bundle();
        args.putInt(BOARD_ID, boardId);
        args.putString(SORT_BY, sortBy);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mBoardId = getArguments().getInt(BOARD_ID);
            mSortBy = getArguments().getString(SORT_BY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post, container, false);
        ButterKnife.bind(this, view);
        ((BaseActivity) getActivity()).getActivityComponent().inject(this);
        postPresenter.attachView(this);
        initView();
        return view;
    }

    private void initView() {

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                postPresenter.refreshPostListData(mSortBy, 1, mBoardId);
            }
        });

        //初始化下拉刷新和上拉加载布局
        int homepage_refresh_spacing = AppUtils.dip2px(getActivity(), 10);
//        mSwipeRefreshLayout.setProgressViewOffset(false, -homepage_refresh_spacing * 2, homepage_refresh_spacing);
//        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
//        mSwipeRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                postPresenter.refreshPostListData(mSortBy, 1, mBoardId);
//            }
//
//            @Override
//            public void onLoad() {
//                postPresenter.loadMorePostListData(mSortBy, nextPage, mBoardId);
//            }
//
//            @Override
//            public boolean canLoadMore() {
//                return true;
//            }
//
//            @Override
//            public boolean canRefresh() {
//                return true;
//            }
//        });

        mAdapter.setOnItemClickListener(new OnItemClickListener<PostModel.ListBean>() {
            @Override
            public void onItemClick(int position, View view, PostModel.ListBean listBean) {
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
                intent.putExtra(WebViewActivity.TITLE, listBean.getBoard_name());
                intent.putExtra(WebViewActivity.URL, url);
                getActivity().startActivity(intent);
            }
        });

        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
//        mRecycleView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        mRecycleView.setAdapter(mAdapter);
        mRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (!isLoadingMore && isScrollToBottom(recyclerView)) {
                    isLoadingMore = true;
                    postPresenter.loadMorePostListData(mSortBy, nextPage, mBoardId);
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        postPresenter.refreshPostListData(mSortBy, 1, mBoardId);
    }

    private boolean isScrollToBottom(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int count = recyclerView.getAdapter().getItemCount();
        if (layoutManager instanceof LinearLayoutManager && count > 0) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == count - 1) {
                return true;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
            int[] lastItems = new int[2];
            staggeredGridLayoutManager
                    .findLastCompletelyVisibleItemPositions(lastItems);
            int lastItem = Math.max(lastItems[0], lastItems[1]);
            if (lastItem == count - 1) {
                return true;
            }
        }
        return false;
    }

    @OnClick({})
    public void onClicked(View view) {
        switch (view.getId()) {

        }
    }

    public void scrollToTop() {
        if (mRecycleView != null && mRecycleView.getAdapter() != null
                && mRecycleView.getAdapter().getItemCount() > 0) {
            mRecycleView.smoothScrollToPosition(0);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        postPresenter.detachView();
    }

    @Override
    public void refreshData(PostModel postModel) {
        mAdapter.setPostModel(postModel);
    }

    @Override
    public void loadMoreData(PostModel postModel) {
        mAdapter.addData(postModel);
        nextPage++;
        isLoadingMore = false;
    }

    @Override
    public void loadMoreError(Throwable e) {
        isLoadingMore = false;
        e.printStackTrace();
        Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void refreshEmpty() {

    }

    @Override
    public void loadMoreEmpty() {
        isLoadingMore = false;
    }

    @Override
    public void showLoadMoreView() {

    }

    @Override
    public void hideLoadMoreView() {
//        mSwipeRefreshLayout.setLoading(false);
    }

    @Override
    public void showLoading() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void loadError(Throwable e) {
        e.printStackTrace();
        Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
    }
}
