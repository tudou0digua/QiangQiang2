package com.cb.qiangqiang2.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cb.qiangqiang2.R;
import com.cb.qiangqiang2.common.base.BaseActivity;
import com.cb.qiangqiang2.common.base.BaseFragment;
import com.cb.qiangqiang2.common.util.AppUtils;
import com.cb.qiangqiang2.data.api.ApiService;
import com.cb.qiangqiang2.data.model.PostModel;
import com.cb.qiangqiang2.mvpview.PostMvpView;
import com.cb.qiangqiang2.presenter.PostPresenter;
import com.cb.qiangqiang2.ui.adapter.PostListAdapter;
import com.cb.qiangqiang2.ui.view.DividerItemDecoration;
import com.maimengmami.waveswiperefreshlayout.WaveSwipeRefreshLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 帖子列表Fragment
 */
public class PostFragment extends BaseFragment implements PostMvpView {
    private static final String BOARD_ID = "mBoardId";
    private static final String SORT_BY = "sortBy";

    @BindView(R.id.rl_top)
    RelativeLayout mRlTop;
    @BindView(R.id.swipe_refresh_layout)
    WaveSwipeRefreshLayout mSwipeRefreshLayout;
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
        if (mBoardId != 0 && mRlTop != null) mRlTop.setVisibility(View.GONE);

        //初始化下拉刷新和上拉加载布局
        int homepage_refresh_spacing = AppUtils.dip2px(getActivity(), 10);
        mSwipeRefreshLayout.setProgressViewOffset(false, -homepage_refresh_spacing * 2, homepage_refresh_spacing);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        mSwipeRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                postPresenter.refreshPostListData(mSortBy, 1, mBoardId);
            }

            @Override
            public void onLoad() {
                postPresenter.loadMorePostListData(mSortBy, nextPage, mBoardId);
            }

            @Override
            public boolean canLoadMore() {
                return true;
            }

            @Override
            public boolean canRefresh() {
                return true;
            }
        });

        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        mRecycleView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        mRecycleView.setAdapter(mAdapter);

        postPresenter.refreshPostListData(mSortBy, 1, mBoardId);
    }

    public void scrollToTop(){
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
        mSwipeRefreshLayout.setLoading(false);
    }

    @Override
    public void loadMoreError(Throwable e) {
        e.printStackTrace();
        Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void refreshEmpty() {

    }

    @Override
    public void loadMoreEmpty() {

    }

    @Override
    public void showLoadMoreView() {

    }

    @Override
    public void hideLoadMoreView() {

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
