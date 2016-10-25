package com.cb.qiangqiang2.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cb.qiangqiang2.R;
import com.cb.qiangqiang2.common.base.BaseSwipeBackActivity;
import com.cb.qiangqiang2.common.glide.GlideCircleTransform;
import com.cb.qiangqiang2.data.UserManager;
import com.cb.qiangqiang2.data.model.UserInfoModel;
import com.cb.qiangqiang2.mvpview.UserInfoMvpView;
import com.cb.qiangqiang2.presenter.UserInfoPresenter;

import javax.inject.Inject;

import butterknife.BindView;

public class UserInfoActivity extends BaseSwipeBackActivity implements UserInfoMvpView{
    public static final String USER_ID = "userId";
    public static final String USER_NAME = "userName";
    public static final int INVALIDE_UID = -1;

    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.activity_person_info)
    CoordinatorLayout mActivityUserInfo;
    @BindView(R.id.iv_top_bg)
    ImageView mIvTopBg;
    @BindView(R.id.iv_avatar)
    ImageView mIvAvatar;

    @Inject
    UserManager mUserManager;
    @Inject
    UserInfoPresenter mUserInfoPresenter;

    private int userId = INVALIDE_UID;
    private String userName;

    public static void startUserInfoActivity(Context context, int userId, String userName) {
        Intent intent = new Intent(context, UserInfoActivity.class);
        intent.putExtra(USER_ID, userId);
        intent.putExtra(USER_NAME, userName);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void injectActivity() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void attachPresenter() {
        mUserInfoPresenter.attachView(this);
    }

    @Override
    protected void initData() {
        if (getIntent() != null) {
            userId = getIntent().getIntExtra(USER_ID, INVALIDE_UID);
            userName = getIntent().getStringExtra(USER_NAME);
        }
    }

    @Override
    protected void initView() {
        mCollapsingToolbarLayout.setTitle(userName);

        if (userId != INVALIDE_UID) {
            mUserInfoPresenter.getUserInfo(userId);
        } else {
            //TODO
        }
    }

    @Override
    public void showUserInfoData(UserInfoModel userInfoModel) {
        Glide.with(mContext)
                .load(userInfoModel.getIcon())
                .placeholder(R.drawable.default_icon)
                .error(R.drawable.default_icon)
                .bitmapTransform(new GlideCircleTransform(mContext))
                .crossFade(300)
                .into(mIvAvatar);
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
        Toast.makeText(mContext, R.string.request_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUserInfoPresenter.detachView();
    }
}
