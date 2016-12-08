package com.cb.qiangqiang2.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cb.qiangqiang2.R;
import com.cb.qiangqiang2.common.base.BaseSwipeBackActivity;
import com.cb.qiangqiang2.data.model.PostDetailModel;
import com.cb.qiangqiang2.mvpview.PostDetailMvpView;
import com.cb.qiangqiang2.presenter.PostDetailPresenter;
import com.cb.qiangqiang2.ui.adapter.PostDetailAdapter;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import javax.inject.Inject;

import butterknife.BindView;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

import static com.cb.qiangqiang2.common.util.AppUtils.isScrollToBottom;

public class PostDetailActivity extends BaseSwipeBackActivity implements PostDetailMvpView {
    public static final String BOARD_ID = "board_id";
    public static final String BOARD_NAME = "board_name";
    public static final String TOPIC_ID = "topic_id";
    public static final String TITLE = "title";

    @Inject
    PostDetailPresenter mPostDetailPresenter;
    @Inject
    PostDetailAdapter mAdapter;

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycle_view)
    RecyclerView mRecycleView;
    @BindView(R.id.progress_bar)
    MaterialProgressBar mProgressBar;
    @BindView(R.id.btn_collection)
    FloatingActionButton btnCollection;
    @BindView(R.id.btn_reply)
    FloatingActionButton btnReply;
    @BindView(R.id.floating_action_menu)
    FloatingActionMenu floatingActionMenu;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private int nextPage = 2;
    private boolean canLoadingMore = true;
    private boolean hasLoadAllData = false;
    private int boardId;
    private int topicId;
    private String boardName;
    private String title;

    public static void startPostDetailActivity(Context context, int boardId, int topicId, String boardName, String title) {
        Intent intent = new Intent(context, PostDetailActivity.class);
        intent.putExtra(BOARD_ID, boardId);
        intent.putExtra(BOARD_NAME, boardName);
        intent.putExtra(TOPIC_ID, topicId);
        intent.putExtra(TITLE, title);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_post_detail;
    }

    @Override
    protected void attachPresenter() {
        mPostDetailPresenter.attachView(this);
    }

    @Override
    protected void injectActivity() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initData() {
        boardId = getIntent().getIntExtra(BOARD_ID, -1);
        boardName = getIntent().getStringExtra(BOARD_NAME);
        topicId = getIntent().getIntExtra(TOPIC_ID, -1);
        title = getIntent().getStringExtra(TITLE);
    }

    @Override
    protected void initView() {
        tvTitle.setText(title);
        mAdapter.setBoardName(boardName);
        //设置 toolbar
        toolbar.setTitle("");
        toolbar.setLogo(null);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_open_with_browser:
                        Toast.makeText(mContext, getString(R.string.post_detail_open_with_browser), Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishActivity();
            }
        });

        //设置 SwipeRefreshLayout
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPostDetailPresenter.getPostDetail(false, topicId, boardId, 1, 10);
                canLoadingMore = false;
            }
        });
        Resources resources = mContext.getResources();
        mSwipeRefreshLayout.setColorSchemeColors(resources.getColor(R.color.colorPrimary),
                resources.getColor(android.R.color.holo_green_light),
                resources.getColor(android.R.color.holo_orange_light),
                resources.getColor(android.R.color.holo_red_light));
        //设置 mRecycleView
        mRecycleView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        mRecycleView.setAdapter(mAdapter);
        mRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                boolean isScrollToBottom = isScrollToBottom(recyclerView);
                if (!hasLoadAllData && canLoadingMore && isScrollToBottom) {
                    canLoadingMore = false;
                    mPostDetailPresenter.getPostDetail(true, topicId, boardId, nextPage, 10);
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        //设置 FloatingActionMenu

        mPostDetailPresenter.getPostDetail(false, topicId, boardId, 1, 10);
        canLoadingMore = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_post__detail, menu);
        return true;
    }

    private void finishActivity() {
        finish();
    }

    @Override
    public void refreshData(PostDetailModel postDetailModel) {
        if (postDetailModel.getRs() != 1) {
            Toast.makeText(PostDetailActivity.this, postDetailModel.getErrcode(), Toast.LENGTH_SHORT).show();
            return;
        }

        if (postDetailModel.getHas_next() == 0 && nextPage == 2) {
            hasLoadAllData = true;
        } else if (postDetailModel.getHas_next() != 0 && nextPage > 2) {
            hasLoadAllData = false;
        }
        nextPage = 2;
        mAdapter.setData(postDetailModel);
        canLoadingMore = true;
    }

    @Override
    public void refreshEmpty() {
        canLoadingMore = true;
    }

    @Override
    public void loadMoreData(PostDetailModel postDetailModel) {
        if (postDetailModel.getHas_next() == 0
                && (postDetailModel.getList() == null || postDetailModel.getList().size() == 0)) {
            hasLoadAllData = true;
            Toast.makeText(mContext, R.string.no_more_data, Toast.LENGTH_SHORT).show();
        } else {
            nextPage++;
        }
        mAdapter.addData(postDetailModel);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPostDetailPresenter.detachView();
    }
}