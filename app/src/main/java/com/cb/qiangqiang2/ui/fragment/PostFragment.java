package com.cb.qiangqiang2.ui.fragment;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cb.qiangqiang2.R;
import com.cb.qiangqiang2.adapter.PostListAdapter;
import com.cb.qiangqiang2.adapter.listener.OnItemClickListener;
import com.cb.qiangqiang2.adapter.listener.OnItemLongClickListener;
import com.cb.qiangqiang2.common.base.BaseActivity;
import com.cb.qiangqiang2.common.base.BaseFragment;
import com.cb.qiangqiang2.common.constant.Constants;
import com.cb.qiangqiang2.common.util.AppUtils;
import com.cb.qiangqiang2.data.model.PostModel;
import com.cb.qiangqiang2.event.TotalNumEvent;
import com.cb.qiangqiang2.mvpview.PostMvpView;
import com.cb.qiangqiang2.presenter.OtherPresenter;
import com.cb.qiangqiang2.presenter.PostPresenter;
import com.cb.qiangqiang2.ui.activity.PostDetailActivity;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

import static com.cb.qiangqiang2.common.util.AppUtils.isScrollToBottom;

/**
 * 帖子列表Fragment
 */
public class PostFragment extends BaseFragment implements PostMvpView {
    private static final String BOARD_ID = "mBoardId";
    private static final String SORT_BY = "sortBy";
    private static final String IS_FROM_USER = "isFromUser";//是否是用户信息页面列表
    private static final String USER_ID = "userId";
    private static final String TYPE = "type";

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    //    @BindView(R.id.swipe_refresh_layout)
//    WaveSwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recycle_view)
    RecyclerView mRecycleView;
    @BindView(R.id.progress_bar)
    MaterialProgressBar mProgressBar;

    @Inject
    PostListAdapter mAdapter;
    @Inject
    PostPresenter postPresenter;
    @Inject
    OtherPresenter mOtherPresenter;

    private int mBoardId;
    private int mUserId;
    private String mSortBy;
    private String mType;
    private boolean isFromUser;
    private int nextPage = 2;
    private boolean canLoadingMore = false;
    private boolean hasLoadAllData = false;

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

    public static PostFragment newInstance(boolean isFromUser, int userId, String type) {
        PostFragment fragment = new PostFragment();
        Bundle args = new Bundle();
        args.putInt(USER_ID, userId);
        args.putString(TYPE, type);
        args.putBoolean(IS_FROM_USER, isFromUser);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mBoardId = getArguments().getInt(BOARD_ID);
            mSortBy = getArguments().getString(SORT_BY);
            mUserId = getArguments().getInt(USER_ID);
            mType = getArguments().getString(TYPE);
            isFromUser = getArguments().getBoolean(IS_FROM_USER);
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
                postPresenter.refreshPostListData(isFromUser, mSortBy, 1, mBoardId, mType, mUserId);
                canLoadingMore = false;
            }
        });
        Resources resources = getActivity().getResources();
        mSwipeRefreshLayout.setColorSchemeColors(resources.getColor(R.color.colorPrimary),
                resources.getColor(android.R.color.holo_green_light),
                resources.getColor(android.R.color.holo_orange_light),
                resources.getColor(android.R.color.holo_red_light));

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
//                String url =
//                        "http://www.qiangqiang5.com/api/mobile/index.php?module=%s&page=%s&charset=%s&image=%s&ppp=%s&debug=%s&tid=%s&mobile=%s&version=%s";
//                url = String.format(url,
//                        "viewthread",
//                        "1",
//                        "utf-8",
//                        "1",
//                        "10",
//                        "1",
//                        String.valueOf(listBean.getTopic_id()),
//                        "no",
//                        "3");
//                Intent intent = new Intent(getActivity(), WebViewActivity.class);
//                intent.putExtra(WebViewActivity.TITLE, listBean.getBoard_name());
//                intent.putExtra(WebViewActivity.URL, url);
//                getActivity().startActivity(intent);
                PostDetailActivity.startPostDetailActivity(getActivity(), listBean.getBoard_id(), listBean.getTopic_id(), listBean.getBoard_name(), listBean.getTitle());
            }
        });
        mAdapter.setOnItemLongClickListener(new OnItemLongClickListener<PostModel.ListBean>() {
            @Override
            public void onItemLongClick(int position, View view, PostModel.ListBean listBean) {
                mOtherPresenter.setCollectionStatus(Constants.POST_ACTION_FAVORITE, Constants.POST_ID_TYPE_TID, listBean.getTopic_id());
            }
        });

        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
//        mRecycleView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        mRecycleView.setAdapter(mAdapter);
        mRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                boolean isScrollToBottom = isScrollToBottom(recyclerView);
                if (!hasLoadAllData && canLoadingMore && isScrollToBottom) {
                    canLoadingMore = false;
                    postPresenter.loadMorePostListData(isFromUser, mSortBy, nextPage, mBoardId, mType, mUserId);
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        postPresenter.refreshPostListData(isFromUser, mSortBy, 1, mBoardId, mType, mUserId);
        canLoadingMore = false;
    }

    @OnClick({})
    public void onClicked(View view) {
        switch (view.getId()) {

        }
    }

    public void scrollToTop() {
        if (mRecycleView != null && mRecycleView.getAdapter() != null
                && mRecycleView.getAdapter().getItemCount() > 0) {
            if (mRecycleView.getLayoutManager() != null && mRecycleView.getLayoutManager() instanceof LinearLayoutManager) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) mRecycleView.getLayoutManager();
                if (layoutManager.findLastVisibleItemPosition() >= Integer.parseInt(Constants.DEFAULT_PAGE_SIZE)) {
                    mRecycleView.scrollToPosition(0);
                    return;
                }
            }
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
        //没有下一页并且nextPage为初始值2，代表没有进行过上拉加载且所以页面都已经加载完成
        if (postModel.getHas_next() == 0 && nextPage == 2) {
            hasLoadAllData = true;
        } else if (postModel.getHas_next() != 0 && nextPage > 2) {
            hasLoadAllData = false;
        }
        nextPage = 2;
        canLoadingMore = true;
        mAdapter.setPostModel(postModel);
        if (Constants.USER_POST_FAVORITE.equals(mType)) {
            EventBus.getDefault().post(new TotalNumEvent(postModel.getTotal_num(), Constants.USER_POST_FAVORITE));
        }
    }

    @Override
    public void loadMoreData(PostModel postModel) {
        if (postModel.getHas_next() == 0
                && (postModel.getList() == null || postModel.getList().size() == 0)) {
            hasLoadAllData = true;
            Toast.makeText(getActivity(), R.string.no_more_data, Toast.LENGTH_SHORT).show();
        } else {
            nextPage++;
        }
        mAdapter.addData(postModel);
        canLoadingMore = true;
    }

    @Override
    public void loadMoreError(Throwable e) {
        canLoadingMore = true;
        e.printStackTrace();
        Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void refreshEmpty() {
        canLoadingMore = true;
    }

    @Override
    public void loadMoreEmpty() {
        canLoadingMore = true;
    }

    @Override
    public void showLoadMoreView() {
        if (mProgressBar.getVisibility() != View.VISIBLE)
            mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadMoreView() {
//        mSwipeRefreshLayout.setLoading(false);
        if (mProgressBar.getVisibility() == View.VISIBLE)
            mProgressBar.setVisibility(View.GONE);
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
        canLoadingMore = true;
        if (e != null) {
            e.printStackTrace();
            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
