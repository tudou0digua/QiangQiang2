package com.cb.qiangqiang2.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cb.qiangqiang2.R;
import com.cb.qiangqiang2.adapter.PostDetailAdapter;
import com.cb.qiangqiang2.common.base.BaseSwipeBackActivity;
import com.cb.qiangqiang2.common.constant.Constants;
import com.cb.qiangqiang2.common.util.AppUtils;
import com.cb.qiangqiang2.data.UserManager;
import com.cb.qiangqiang2.data.model.PostDetailModel;
import com.cb.qiangqiang2.data.model.ReplyPostModel;
import com.cb.qiangqiang2.event.SendViewEvent;
import com.cb.qiangqiang2.mvpview.PostDetailMvpView;
import com.cb.qiangqiang2.mvpview.ReplyPostMvpView;
import com.cb.qiangqiang2.presenter.PostDetailPresenter;
import com.cb.qiangqiang2.presenter.ReplyPostPresenter;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnLongClick;
import cn.carbs.android.avatarimageview.library.AvatarImageView;
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
    ReplyPostPresenter mReplyPostPresenter;
    @Inject
    PostDetailAdapter mAdapter;
    @Inject
    UserManager userManager;

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
    @BindView(R.id.et_reply)
    EditText etReply;
    @BindView(R.id.tv_send)
    TextView tvSend;
    @BindView(R.id.activity_post_detail)
    CoordinatorLayout activityPostDetail;
    @BindView(R.id.send_container)
    RelativeLayout sendContainer;
    @BindView(R.id.iv_avatar)
    AvatarImageView avatarImageView;

    private int nextPage = 2;
    private boolean canLoadingMore = true;
    private boolean hasLoadAllData = false;
    private int boardId;
    private int topicId;
    private String boardName;
    private String title;
    private PostDetailModel postDetailModel;
    private ObjectAnimator showSendViewAnimator;
    private ObjectAnimator hideSendViewAnimator;

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
        mReplyPostPresenter.attachView(new ReplyPostMvpView() {
            @Override
            public void replyResult(ReplyPostModel replyPostModel) {
                Toast.makeText(mContext, replyPostModel.getErrcode(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void showLoading() {

            }

            @Override
            public void hideLoading() {

            }

            @Override
            public void loadError(Throwable e) {
                if (e != null) e.printStackTrace();
            }
        });
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
        mAdapter.setOnPostDetailClickListener(new PostDetailAdapter.OnPostDetailClickListener() {
            @Override
            public void onAvatarClick(int userId, String nickName) {
                UserInfoActivity.startUserInfoActivity(mContext, userId, nickName);
            }

            @Override
            public void onReplayClick(PostDetailModel.ListBean listBean) {

            }
        });
        //设置 toolbar
        toolbar.setTitle("");
        toolbar.setLogo(null);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    //用浏览器打开
                    case R.id.menu_open_with_browser:
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        String url = String.format(Constants.WEB_PAGE_URL_PRE, topicId);
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
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
        Animation hideAnimation = new AlphaAnimation(1.0f, 0f);
        hideAnimation.setDuration(200);
        hideAnimation.setInterpolator(new AccelerateInterpolator());
        floatingActionMenu.setMenuButtonHideAnimation(hideAnimation);

        Animation showAnimation = new AlphaAnimation(0f, 1.0f);
        showAnimation.setDuration(200);
        showAnimation.setInterpolator(new DecelerateInterpolator());
        floatingActionMenu.setMenuButtonShowAnimation(showAnimation);

        //设置回复栏
        Glide.with(mContext)
                .load(userManager.getAvatarUrl())
                .asBitmap()
                .into(avatarImageView);

        mPostDetailPresenter.getPostDetail(false, topicId, boardId, 1, 10);
        canLoadingMore = false;
    }

    @OnClick({R.id.tv_send, R.id.btn_reply, R.id.btn_collection})
    public void onClicked(View view) {
        switch (view.getId()) {
            //发送回复
            case R.id.tv_send:
                if (postDetailModel != null) {
                    mReplyPostPresenter.replyPost(etReply.getText().toString().trim(),
                            postDetailModel.getBoardId(), postDetailModel.getTopic().getTopic_id());
                }
                break;
            //回复
            case R.id.btn_reply:
                floatingActionMenu.hideMenu(true);
                showSendView();
                break;
            //收藏、取消收藏
            case R.id.btn_collection:
                if (postDetailModel == null || postDetailModel.getTopic() == null || postDetailModel.getTopic().getTopic_id() <= 0) {
                    return;
                }
                floatingActionMenu.close(true);
                btnCollection.setEnabled(false);
                mPostDetailPresenter.setCollectionStatus(getString(R.string.post_detail_collection).equals(btnCollection.getLabelText()),
                        postDetailModel.getTopic().getTopic_id());
                break;
        }
    }

    @OnLongClick({R.id.btn_reply})
    public boolean onLongClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_reply:
                Toast.makeText(mContext, "butter knife long clicked", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    private void hideSendView() {
        if (hideSendViewAnimator != null && hideSendViewAnimator.isRunning()) return;
        AppUtils.closeSoftKeybord(etReply, mContext);
        int start = sendContainer.getTop();
        int end = sendContainer.getTop() + sendContainer.getHeight();
        hideSendViewAnimator = ObjectAnimator.ofFloat(sendContainer, "translationY", start, end);
        hideSendViewAnimator.setDuration(500);
        hideSendViewAnimator.setInterpolator(new DecelerateInterpolator());
        hideSendViewAnimator.start();
        hideSendViewAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                sendContainer.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void showSendView() {
        if (showSendViewAnimator != null && showSendViewAnimator.isRunning()) return;
        int start = sendContainer.getBottom();
        int end = sendContainer.getBottom() - sendContainer.getHeight();
        showSendViewAnimator = ObjectAnimator.ofFloat(sendContainer, "translationY", start, end);
        showSendViewAnimator.setDuration(500);
        showSendViewAnimator.setInterpolator(new DecelerateInterpolator());
        sendContainer.setVisibility(View.VISIBLE);
        showSendViewAnimator.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_post_detail, menu);
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
        this.postDetailModel = postDetailModel;
        initPostDetailData();
        mAdapter.setData(postDetailModel);
        canLoadingMore = true;
    }

    private void initPostDetailData() {
        if (postDetailModel == null || postDetailModel.getTopic() == null) {
            return;
        }
        PostDetailModel.TopicBean topicBean = postDetailModel.getTopic();
        //初始化帖子收藏状态
        if (topicBean.getIs_favor() != Constants.COLLECTED_POST) {
            //未收藏帖子
            btnCollection.setLabelText(getString(R.string.post_detail_collection));
        } else {
            //已收藏帖子
            btnCollection.setLabelText(getString(R.string.post_detail_cancel_collection));
        }
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
        mReplyPostPresenter.detachView();
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setSendViewVisibility(SendViewEvent event) {
        if (!event.isShowSendView()) {
            hideSendView();
        }
    }

    @Override
    public void operateCollectionSuccess(boolean isCollection) {
        btnCollection.setEnabled(true);
        if (isCollection) {
            Toast.makeText(PostDetailActivity.this, getString(R.string.post_detail_collection_success), Toast.LENGTH_SHORT).show();
            btnCollection.setLabelText(getString(R.string.post_detail_cancel_collection));
        } else {
            Toast.makeText(PostDetailActivity.this, getString(R.string.post_detail_cancel_collection_success), Toast.LENGTH_SHORT).show();
            btnCollection.setLabelText(getString(R.string.post_detail_collection));
        }
    }

    @Override
    public void operateCollectionFail() {
        btnCollection.setEnabled(true);
    }
}
