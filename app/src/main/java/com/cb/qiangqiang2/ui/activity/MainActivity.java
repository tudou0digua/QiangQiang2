package com.cb.qiangqiang2.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatDelegate;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.cb.qiangqiang2.R;
import com.cb.qiangqiang2.common.base.BaseSwipeBackActivity;
import com.cb.qiangqiang2.common.constant.Constants;
import com.cb.qiangqiang2.common.util.DateUtil;
import com.cb.qiangqiang2.data.UserManager;
import com.cb.qiangqiang2.event.NightThemeEvent;
import com.cb.qiangqiang2.event.OpenDrawLayoutEvent;
import com.cb.qiangqiang2.event.ShowExitSnackBarEvent;
import com.cb.qiangqiang2.ui.factory.DialogFactory;
import com.cb.qiangqiang2.ui.factory.OnDialogClickListener;
import com.cb.qiangqiang2.ui.fragment.BoardFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.BindView;
import cn.carbs.android.avatarimageview.library.AvatarImageView;

public class MainActivity extends BaseSwipeBackActivity {
    private static final int EXIT_INTERVAL = 2000;

    @Inject
    UserManager mUserManager;

    @BindView(R.id.navigation_left)
    NavigationView mNavigationViewLeft;
    @BindView(R.id.activity_main)
    DrawerLayout mDrawerLayout;

    AvatarImageView avatarImageView;
    TextView tvName;
    TextView tvUnlogin;
    TextView tvLevel;
    ImageView ivLogout;
    ImageView ivBg;
    View leftDrawerHeadContainer;


    private long lastBackClickTime = Constants.DEFAULT_INVALIDE_TIME;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //主页面不可以侧滑返回
        getSwipeBackLayout().setEnableGesture(false);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void injectActivity() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) return;
            BoardFragment boardFragment = new BoardFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, boardFragment).commit();
        }

        initNavigationView();
    }

    private void initNavigationView() {
//        mNavigationViewLeft.setItemIconTintList(null);

        //设置mNavigationViewLeft宽度
        int width = (int) (ScreenUtils.getScreenWidth() * 0.7);
        DrawerLayout.LayoutParams layoutParams = (DrawerLayout.LayoutParams) mNavigationViewLeft.getLayoutParams();
        layoutParams.width = width;

        //NavigationView Header
        leftDrawerHeadContainer= mNavigationViewLeft.getHeaderView(0);
        avatarImageView = (AvatarImageView) leftDrawerHeadContainer.findViewById(R.id.iv_avatar);
        tvName = (TextView) leftDrawerHeadContainer.findViewById(R.id.tv_name);
        tvUnlogin = leftDrawerHeadContainer.findViewById(R.id.tv_un_login);
        tvLevel = (TextView) leftDrawerHeadContainer.findViewById(R.id.tv_level);
        ivLogout = (ImageView) leftDrawerHeadContainer.findViewById(R.id.iv_logout);
        ivBg = (ImageView) leftDrawerHeadContainer.findViewById(R.id.iv_bg);

        Glide.with(mContext)
                .load("https://bing.ioliu.cn/v1/rand?w=480&h=320")
//                    .skipMemoryCache(true)
//                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                .signature(new StringSignature(DateUtil.getDay(System.currentTimeMillis())))
                .centerCrop()
                .into(ivBg);

        if (mUserManager.getUserInfo() == null) {
            ivLogout.setVisibility(View.GONE);
            tvUnlogin.setVisibility(View.VISIBLE);
            tvName.setText("");
        } else {
            ivLogout.setVisibility(View.VISIBLE);
            tvUnlogin.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(mUserManager.getAvatarUrl())) {
                Glide.with(mContext)
                        .load(mUserManager.getAvatarUrl())
                        .asBitmap()
                        .into(avatarImageView);
            }
            tvName.setText(mUserManager.getUserName());
            tvLevel.setText(mUserManager.getLevel());
            ivLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDrawerLayout.closeDrawer(Gravity.LEFT);
                    showLogoutDialog();
                }
            });
        }

        leftDrawerHeadContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mUserManager.getUserInfo() == null) {
                    gotoLogin();
                    finishActivity();
                } else {
                    mDrawerLayout.closeDrawer(Gravity.LEFT);
                    UserInfoActivity.startUserInfoActivity(mContext, mUserManager.getUserId(), mUserManager.getUserName());
                }
            }
        });

        mNavigationViewLeft.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_feedback:
//                        LoginActivity.startLoginActivity(mContext);
                        break;
                    case R.id.nav_publish:
//                        TestRxJavaActivity.startActivity(mContext);
                        Intent intent = new Intent(mContext, WebViewActivity.class);
                        intent.putExtra(WebViewActivity.TITLE, "发表帖子");
                        intent.putExtra(WebViewActivity.URL, Constants.PUBLISH_POST);
                        mContext.startActivity(intent);
                        break;
                    case R.id.nav_setting:
                        SettingActivity.startSettingActivity(mContext);
                        break;
                }
                mDrawerLayout.closeDrawer(Gravity.LEFT);
//                item.setChecked(true);
//                Toast.makeText(mContext, item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    private void showLogoutDialog() {
        DialogFactory.showConfirmDialog(MainActivity.this,
                getString(R.string.logout_dialog_title),
                getString(R.string.logout_dialog_content),
                "",
                "",
                new OnDialogClickListener() {
                    @Override
                    public void onConfirmClick() {
                        // 登出
                        mUserManager.logout(mContext);
                        gotoLogin();
                        finishActivity();
                    }

                    @Override
                    public void onCancelClick() {

                    }
                });
    }

    private void gotoLogin() {
        LoginActivity.startLoginActivity(MainActivity.this, true);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //避免切换主题recreate之后，NavigationView各类初始化失效（recreate只重新inflate布局，没有走initNavigationView）
        initNavigationView();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            mDrawerLayout.closeDrawer(Gravity.LEFT);
            return;
        }
        if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
            mDrawerLayout.closeDrawer(Gravity.RIGHT);
            return;
        }
        long currentTime = System.currentTimeMillis();
        if (lastBackClickTime == Constants.DEFAULT_INVALIDE_TIME) {
            lastBackClickTime = currentTime;
            showSnackBar();
            return;
        } else if ((currentTime - lastBackClickTime) <= EXIT_INTERVAL) {
            super.onBackPressed();
        } else if ((currentTime - lastBackClickTime) > EXIT_INTERVAL) {
            lastBackClickTime = currentTime;
            showSnackBar();
        }
    }

    private void showSnackBar() {
        EventBus.getDefault().post(new ShowExitSnackBarEvent());
    }

    private void finishActivity() {
        finish();
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
    public void onOpenDrawLayout(OpenDrawLayoutEvent event) {
        mDrawerLayout.openDrawer(Gravity.LEFT);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changeTheme(NightThemeEvent event) {
        AppCompatDelegate.setDefaultNightMode(event.isNightTheme() ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
        getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
        recreate();
    }

}
