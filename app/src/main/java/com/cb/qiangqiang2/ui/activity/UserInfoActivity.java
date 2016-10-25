package com.cb.qiangqiang2.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cb.qiangqiang2.R;
import com.cb.qiangqiang2.common.base.BaseSwipeBackActivity;
import com.cb.qiangqiang2.common.glide.GlideCircleTransform;
import com.cb.qiangqiang2.common.util.AppUtils;
import com.cb.qiangqiang2.data.UserManager;
import com.cb.qiangqiang2.data.model.UserInfoModel;
import com.cb.qiangqiang2.mvpview.UserInfoMvpView;
import com.cb.qiangqiang2.presenter.UserInfoPresenter;
import com.cb.qiangqiang2.ui.fragment.PostFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class UserInfoActivity extends BaseSwipeBackActivity implements UserInfoMvpView {
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
    @BindView(R.id.iv_sexy)
    ImageView mIvSexy;
    @BindView(R.id.tv_level)
    TextView mTvLevel;
    @BindView(R.id.tv_credit)
    TextView mTvCredit;
    @BindView(R.id.tv_gold_coin)
    TextView mTvGoldCoin;
    @BindView(R.id.tv_silver_coin)
    TextView mTvSilverCoin;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @Inject
    UserManager mUserManager;
    @Inject
    UserInfoPresenter mUserInfoPresenter;

    private int userId = INVALIDE_UID;
    private String userName;
    private List<Fragment> fragments;
    private List<String> datas;

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

        datas = new ArrayList<>();
        datas.add("title1");
        datas.add("title2");
        datas.add("title3");

        fragments = new ArrayList<>();
        for (String data : datas) {
            fragments.add(PostFragment.newInstance(0, "new"));
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

        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return datas.get(position);
            }
        };

        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        AppUtils.dynamicSetTabLayoutMode(mTabLayout, UserInfoActivity.this);
    }

    @Override
    public void showUserInfoData(UserInfoModel userInfoModel) {
        Glide.with(mContext)
                .load(userInfoModel.getIcon())
                .error(R.drawable.default_icon)
                .bitmapTransform(new GlideCircleTransform(mContext))
                .crossFade(300)
                .into(mIvAvatar);
        mTvLevel.setText(userInfoModel.getUserTitle());
        mTvCredit.setText(String.format("积分：%d", userInfoModel.getCredits()));
        mTvSilverCoin.setText(String.format("银币：%d", userInfoModel.getScore()));
        mTvGoldCoin.setText(String.format("金币：%d", userInfoModel.getScore() - userInfoModel.getGold_num()));
        if (userInfoModel.getGender() == 0) {
            mIvSexy.setImageDrawable(getResources().getDrawable(R.drawable.user_info_head_icon2));
        } else {
            mIvSexy.setImageDrawable(getResources().getDrawable(R.drawable.user_info_head_icon1));
        }
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
