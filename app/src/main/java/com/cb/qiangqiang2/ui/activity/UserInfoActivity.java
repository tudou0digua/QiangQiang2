package com.cb.qiangqiang2.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
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
import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.cb.qiangqiang2.common.constant.Constants.INVALIDE_UID;
import static com.cb.qiangqiang2.common.constant.Constants.USER_POST_FAVORITE;
import static com.cb.qiangqiang2.common.constant.Constants.USER_POST_REPLY;
import static com.cb.qiangqiang2.common.constant.Constants.USER_POST_TOPIC;

public class UserInfoActivity extends BaseSwipeBackActivity implements UserInfoMvpView {
    public static final String USER_ID = "userId";
    public static final String USER_NAME = "userName";

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
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;


    @Inject
    UserManager mUserManager;
    @Inject
    UserInfoPresenter mUserInfoPresenter;

    private int userId = INVALIDE_UID;
    private String userName;
    private List<Fragment> fragments;
    private List<String> datas;
    private boolean isAccountUser;//是否是用户本人

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
    protected void initStatusBar() {
        AppUtils.setStatusBarTransparent(this);
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

        if (userId != INVALIDE_UID) {
            mUserInfoPresenter.getUserInfo(userId);
        } else {
            //TODO 用户ID无效情况
        }

        datas = new ArrayList<>();
        fragments = new ArrayList<>();
        //用户本人
        int id = mUserManager.getUserId();
        if (id != INVALIDE_UID && id == this.userId) {
            isAccountUser = true;
            datas.add(getString(R.string.user_info_collection));
            datas.add(getString(R.string.user_info_post));
            datas.add(getString(R.string.user_info_participate));
            datas.add(getString(R.string.user_info_friend));
            datas.add(getString(R.string.user_info_attention));
            datas.add(getString(R.string.user_info_fans));

            fragments.add(PostFragment.newInstance(true, userId, USER_POST_FAVORITE));
            fragments.add(PostFragment.newInstance(true, userId, USER_POST_TOPIC));
            fragments.add(PostFragment.newInstance(true, userId, USER_POST_REPLY));
        } else {
            //其他用户
            isAccountUser = false;
            datas.add(getString(R.string.user_info_post));
            datas.add(getString(R.string.user_info_participate));
            datas.add(getString(R.string.user_info_attention));
            datas.add(getString(R.string.user_info_fans));

            fragments.add(PostFragment.newInstance(true, userId, USER_POST_TOPIC));
            fragments.add(PostFragment.newInstance(true, userId, USER_POST_REPLY));
        }

    }

    @Override
    protected void initView() {
        //动态设置StatusBar的marginTop等，适配5.0之前和之后的系统
        int toolBarMarginTop;
        int avatarMarginTop;
        int appBarHeight;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolBarMarginTop = AppUtils.getStatusBarHeight(mContext);
        } else {
            toolBarMarginTop = 0;
        }
        avatarMarginTop = getResources().getDimensionPixelSize(R.dimen.user_info_avatar_margin_top) + toolBarMarginTop;
        appBarHeight = getResources().getDimensionPixelSize(R.dimen.user_info_top_app_bar_layout_height) + toolBarMarginTop;
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) mToolbar.getLayoutParams();
        layoutParams.setMargins(0, toolBarMarginTop, 0, 0);
        ViewGroup.MarginLayoutParams layoutParamsAvatar = (ViewGroup.MarginLayoutParams) mIvAvatar.getLayoutParams();
        layoutParamsAvatar.setMargins(0, avatarMarginTop, 0, 0);
        CoordinatorLayout.LayoutParams layoutParamsAppBarLayout = (CoordinatorLayout.LayoutParams) mAppBarLayout.getLayoutParams();
        layoutParamsAppBarLayout.height = appBarHeight;

        mCollapsingToolbarLayout.setTitle(userName);

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
//        mTabLayout.setupWithViewPager(mViewPager);
        AppUtils.dynamicSetTabLayoutMode(mTabLayout, UserInfoActivity.this);
    }

    @Override
    public void showUserInfoData(UserInfoModel userInfoModel) {
        Glide.with(this).load(userInfoModel.getIcon())
                .error(R.drawable.default_icon)
                .bitmapTransform(new BlurTransformation(mContext))
                //设置gif播放次数为0
                .into(new GlideDrawableImageViewTarget(mIvTopBg, 0));
        Glide.with(mContext)
                .load(userInfoModel.getIcon())
                .error(R.drawable.default_icon)
//                .bitmapTransform(new CropCircleTransformation(mContext))
                .bitmapTransform(new GlideCircleTransform(mContext))
                .crossFade(300)
                .into(mIvAvatar);
        mTvLevel.setVisibility(View.VISIBLE);
        mTvLevel.setText(userInfoModel.getUserTitle());
        mTvCredit.setText(getString(R.string.user_info_credit, userInfoModel.getCredits()));
        mTvSilverCoin.setText(getString(R.string.user_info_silver, userInfoModel.getGold_num()));
        mTvGoldCoin.setText(getString(R.string.user_info_gold, userInfoModel.getScore() - userInfoModel.getGold_num()));
        if (userInfoModel.getGender() == 0) {
            mIvSexy.setImageDrawable(getResources().getDrawable(R.drawable.user_info_head_icon2));
        } else {
            mIvSexy.setImageDrawable(getResources().getDrawable(R.drawable.user_info_head_icon1));
        }
        if (isAccountUser) {

            mTabLayout.getTabAt(1).setText(mTabLayout.getTabAt(1).getText() + "\n" +userInfoModel.getTopic_num());
            mTabLayout.getTabAt(2).setText(mTabLayout.getTabAt(2).getText() + "\n" +userInfoModel.getReply_posts_num());
        } else {
            mTabLayout.getTabAt(0).setText(mTabLayout.getTabAt(0).getText() + "\n" +userInfoModel.getTopic_num());
            mTabLayout.getTabAt(1).setText(mTabLayout.getTabAt(1).getText() + "\n" +userInfoModel.getReply_posts_num());
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
