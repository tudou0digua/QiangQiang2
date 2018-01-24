package com.cb.qiangqiang2.ui.activity;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cb.qiangqiang2.R;
import com.cb.qiangqiang2.common.base.BaseActivity;
import com.cb.qiangqiang2.data.UserManager;
import com.cb.qiangqiang2.data.model.AccountInfoBean;
import com.cb.qiangqiang2.data.model.LoginModel;
import com.cb.qiangqiang2.mvpview.LoginMvpView;
import com.cb.qiangqiang2.presenter.LoginPresenter;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import butterknife.BindView;

public class SplashActivity extends BaseActivity implements LoginMvpView{

    private static final int FINISH_SPLSH = 0;
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

    private SplashHandler handler;

    private static class SplashHandler extends Handler{
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
            case FINISH_SPLSH:
                int type = (int) msg.obj;
                if (type == GOTO_MAIN) {
                    goToMainActivity();
                } else if (type == GOTO_LOGIN) {
                    goToMainActivity();
//                        goToLoginActivity();
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
                .load("https://bing.ioliu.cn/v1/rand?w=720&h=1280")
                .asBitmap()
                .dontAnimate()
                .into(ivBg);

        AccountInfoBean accountInfoBean = mUserManager.getAccountInfo();
        if (accountInfoBean != null && !TextUtils.isEmpty(accountInfoBean.getAccount())
                && !TextUtils.isEmpty(accountInfoBean.getPassword())) {
            startLoginTime = System.currentTimeMillis();
            mLoginPresenter.login(accountInfoBean.getAccount(), accountInfoBean.getPassword());
        } else {
            Message msg = Message.obtain();
            msg.what = FINISH_SPLSH;
            msg.obj = GOTO_LOGIN;
            handler.sendMessageDelayed(msg, SPLASH_TIME);
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
        if (e != null) {
            e.printStackTrace();
        }
        processLoginResult(GOTO_LOGIN);
    }

    private void processLoginResult(int obj) {
        Message msg = Message.obtain();
        msg.what = FINISH_SPLSH;
        msg.obj = obj;
        long delayMillis = SPLASH_TIME - (System.currentTimeMillis() - startLoginTime) / 1000;
        delayMillis = delayMillis < 0 ? 0 : delayMillis;
        handler.sendMessageDelayed(msg, delayMillis);
    }

    @Override
    public void loginResult(LoginModel loginModel) {
        int msgObj =  GOTO_LOGIN;
        switch (loginModel.getHead().getErrCode()) {
            //登录成功
            case "00000000":
                //保存用户信息
                mUserManager.setUserInfo(loginModel);
                msgObj = GOTO_MAIN;
                break;
            //登录失败
            default:
                mUserManager.clearAccountInfo();
                goToLoginActivity();
                break;
        }
        processLoginResult(msgObj);
    }

    private void goToLoginActivity() {
        LoginActivity.startLoginActivity(this);
        finish();
    }

    private void goToMainActivity() {
        MainActivity.startActivity(this);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLoginPresenter != null) {
            mLoginPresenter.detachView();
        }
    }
}
