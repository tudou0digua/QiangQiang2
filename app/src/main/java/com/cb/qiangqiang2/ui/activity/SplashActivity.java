package com.cb.qiangqiang2.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.signature.StringSignature;
import com.cb.qiangqiang2.R;
import com.cb.qiangqiang2.common.base.BaseActivity;
import com.cb.qiangqiang2.common.util.DateUtil;
import com.cb.qiangqiang2.data.UserManager;
import com.cb.qiangqiang2.data.model.AccountInfoBean;
import com.cb.qiangqiang2.data.model.LoginModel;
import com.cb.qiangqiang2.mvpview.LoginMvpView;
import com.cb.qiangqiang2.presenter.LoginPresenter;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import butterknife.BindView;

public class SplashActivity extends BaseActivity implements LoginMvpView {

    private static final int FINISH_SPLASH = 0;
    private static final int GOTO_LOGIN = 1;
    private static final int GOTO_MAIN = 2;

    private static final int SPLASH_TIME = 2000;

    @Inject
    LoginPresenter mLoginPresenter;
    @Inject
    UserManager mUserManager;

    @BindView(R.id.iv_bg)
    ImageView ivBg;

    private long startLoginTime;
    private boolean isStartLogin;
    private boolean isFinishLogin;
    private boolean isStartAnimator;
    private boolean isAnimatorEnd;
    private boolean isLoadingPicFinish;

    private SplashHandler handler;

    private static class SplashHandler extends Handler {

        private WeakReference<SplashActivity> weakReference;
        public SplashHandler(SplashActivity splashActivity) {
            weakReference = new WeakReference<SplashActivity>(splashActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (weakReference.get() != null) {
                weakReference.get().handleMessage(msg);
            }
        }

    }
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case FINISH_SPLASH:
                int type = (int) msg.obj;
                if (type == GOTO_MAIN) {
                    goToMainActivity();
                } else if (type == GOTO_LOGIN) {
                    goToLoginActivity();
                }
                break;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }


    @Override
    protected void injectActivity() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void attachPresenter() {
        mLoginPresenter.attachView(this);
    }

    @Override
    protected void initData() {
        handler = new SplashHandler(this);

        //加载bing每日一图作为背景图
        Glide.with(ivBg.getContext())
                .load("https://bing.ioliu.cn/v1/rand?w=720&h=1120")
                .placeholder(R.color.white)
                .error(R.color.white)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .dontAnimate()
                .signature(new StringSignature(DateUtil.getDay(System.currentTimeMillis())))
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        isLoadingPicFinish = true;
                        ivBg.setImageDrawable(resource.getCurrent());
                        startAnimator();
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        isLoadingPicFinish = true;
                        launch();
                    }
                });

        //开始登陆
        startLogin();
    }

    private void startLogin() {
        AccountInfoBean accountInfoBean = mUserManager.getAccountInfo();
        if (accountInfoBean != null
                && !TextUtils.isEmpty(accountInfoBean.getAccount())
                && !TextUtils.isEmpty(accountInfoBean.getPassword())) {
            if (!mUserManager.isTodayHadLoginSuccess()) {
                startLoginTime = System.currentTimeMillis();
                isStartLogin = true;
                mLoginPresenter.login(accountInfoBean.getAccount(), accountInfoBean.getPassword());
            }
        }

    }

    private void startAnimator() {
        isStartAnimator = true;
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(ivBg, "scaleX", 1.0f, 1.05f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(ivBg, "scaleY", 1.0f, 1.05f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(SPLASH_TIME);
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.play(scaleX).with(scaleY);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimatorEnd = true;
                launch();
            }
        });
        animatorSet.start();
    }

    private void launch() {
        if (isStartLogin && !isFinishLogin) {
            return;
        }

        Message msg = Message.obtain();
        msg.what = FINISH_SPLASH;
        msg.obj = GOTO_LOGIN;
        AccountInfoBean accountInfoBean = mUserManager.getAccountInfo();
        if (accountInfoBean != null
                && !TextUtils.isEmpty(accountInfoBean.getAccount())
                && !TextUtils.isEmpty(accountInfoBean.getPassword())) {
            if (mUserManager.isTodayHadLoginSuccess()) {
                //今天成功登录过，跳过登录
                msg.obj = GOTO_MAIN;
            }
        }
        handler.sendMessage(msg);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void loadError(Throwable e) {
        isFinishLogin = true;
        if (e != null) {
            e.printStackTrace();
        }
        processLoginResult(GOTO_LOGIN);
    }

    @Override
    public void loginSuccess(LoginModel loginModel) {
        isFinishLogin = true;
        processLoginResult(GOTO_MAIN);
    }

    @Override
    public void loginFail(String errMsg) {
        isFinishLogin = true;
        mUserManager.clearAccountInfo();
        processLoginResult(GOTO_LOGIN);
    }

    /**
     * 登录结果跳转页面处理
     * @param obj
     */
    private void processLoginResult(int obj) {
        if (!isLoadingPicFinish || (isStartAnimator && !isAnimatorEnd)) {
            return;
        }

        Message msg = Message.obtain();
        msg.what = FINISH_SPLASH;
        msg.obj = obj;
        handler.sendMessage(msg);
    }

    private void goToLoginActivity() {
        forceNotFullScreen();
        LoginActivity.startLoginActivity(this, true);
        finish();
    }

    private void goToMainActivity() {
        forceNotFullScreen();
        MainActivity.startActivity(this);
        finish();
    }

    @Override
    public void finish() {
        super.finish();
    }

    private void forceNotFullScreen() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLoginPresenter != null) {
            mLoginPresenter.detachView();
        }
    }
}
