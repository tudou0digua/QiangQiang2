package com.cb.qiangqiang2.ui.fragment;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cb.qiangqiang2.R;
import com.cb.qiangqiang2.common.base.BaseActivity;
import com.cb.qiangqiang2.common.base.BaseFragment;
import com.cb.qiangqiang2.common.constant.Constants;
import com.cb.qiangqiang2.data.model.UserListModel;
import com.cb.qiangqiang2.event.TotalNumEvent;
import com.cb.qiangqiang2.mvpview.UserListMvpView;
import com.cb.qiangqiang2.presenter.UserListPresenter;
import com.cb.qiangqiang2.ui.activity.UserInfoActivity;
import com.cb.qiangqiang2.adapter.UserListAdapter;
import com.cb.qiangqiang2.adapter.listener.OnItemClickListener;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

import static com.cb.qiangqiang2.common.util.AppUtils.isScrollToBottom;

/**
 * 用户列表
 */
public class UserListFragment extends BaseFragment implements UserListMvpView {
    public static final String UID = "uid";
    public static final String TYPE = "type";

    @Inject
    UserListPresenter mUserListPresenter;
    @Inject
    UserListAdapter mAdapter;

    @BindView(R.id.recycle_view)
    RecyclerView mRecycleView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.progress_bar)
    MaterialProgressBar mProgressBar;

    private int mUserId;
    private String mType;
    private int nextPage = 2;
    private boolean canLoadingMore = true;
    private boolean hasLoadAllData = false;

    public UserListFragment() {
        // Required empty public constructor
    }

    public static UserListFragment newInstance(int userId, String type) {

        Bundle args = new Bundle();
        args.putInt(UID, userId);
        args.putString(TYPE, type);
        UserListFragment fragment = new UserListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUserId = getArguments().getInt(UID);
            mType = getArguments().getString(TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);
        ButterKnife.bind(this, view);
        ((BaseActivity) getActivity()).getActivityComponent().inject(this);
        mUserListPresenter.attachView(this);
        initView();
        return view;
    }

    private void initView() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mUserListPresenter.getUserList(false, mUserId, mType, 1);
                canLoadingMore = false;
            }
        });
        Resources resources = getActivity().getResources();
        mSwipeRefreshLayout.setColorSchemeColors(resources.getColor(R.color.colorPrimary),
                resources.getColor(android.R.color.holo_green_light),
                resources.getColor(android.R.color.holo_orange_light),
                resources.getColor(android.R.color.holo_red_light));

        mAdapter.setOnItemClickListener(new OnItemClickListener<UserListModel.ListBean>() {
            @Override
            public void onItemClick(int position, View view, UserListModel.ListBean listBean) {
                UserInfoActivity.startUserInfoActivity(getActivity(), listBean.getUid(), listBean.getName());
            }
        });

        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        mRecycleView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        mRecycleView.setAdapter(mAdapter);
        mRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                boolean isScrollToBottom = isScrollToBottom(recyclerView);
                if (!hasLoadAllData && canLoadingMore && isScrollToBottom) {
                    canLoadingMore = false;
                    mUserListPresenter.getUserList(true, mUserId, mType, nextPage);
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        mUserListPresenter.getUserList(false, mUserId, mType, 1);
        canLoadingMore = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUserListPresenter.detachView();
    }

    @Override
    public void refreshData(UserListModel userListModel) {
        if (userListModel.getHas_next() == 0 && nextPage == 2) {
            hasLoadAllData = true;
        } else if (userListModel.getHas_next() != 0 && nextPage > 2) {
            hasLoadAllData = false;
        }
        nextPage = 2;
        mAdapter.setUserListModel(userListModel);
        canLoadingMore = true;
        if (Constants.USER_LIST_FRIEND.equals(mType)) {
            EventBus.getDefault().post(new TotalNumEvent(userListModel.getTotal_num(), Constants.USER_LIST_FRIEND));
        }
    }

    @Override
    public void refreshEmpty() {
        canLoadingMore = true;
    }

    @Override
    public void loadMoreData(UserListModel userListModel) {
        if (userListModel.getHas_next() == 0
                && (userListModel.getList() == null || userListModel.getList().size() == 0)) {
            hasLoadAllData = true;
            Toast.makeText(getActivity(), R.string.no_more_data, Toast.LENGTH_SHORT).show();
        } else {
            nextPage++;
        }
        mAdapter.addData(userListModel);
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
}
