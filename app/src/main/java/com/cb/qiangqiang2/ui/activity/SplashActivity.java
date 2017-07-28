package com.cb.qiangqiang2.ui.activity;

import com.cb.qiangqiang2.R;
import com.cb.qiangqiang2.common.base.BaseActivity;
import com.cb.qiangqiang2.data.UserManager;
import com.cb.qiangqiang2.data.model.LoginModel;
import com.cb.qiangqiang2.mvpview.LoginMvpView;
import com.cb.qiangqiang2.presenter.LoginPresenter;

import javax.inject.Inject;

public class SplashActivity extends BaseActivity implements LoginMvpView{

    @Inject
    LoginPresenter mLoginPresenter;
    @Inject
    UserManager mUserManager;

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
//        AccountInfoBean accountInfoBean = mUserManager.getAccountInfo();
//        if (accountInfoBean != null && !TextUtils.isEmpty(accountInfoBean.getAccount())
//                && !TextUtils.isEmpty(accountInfoBean.getPassword())) {
//            mLoginPresenter.login(accountInfoBean.getAccount(), accountInfoBean.getPassword());
//        } else {
//            goToLoginActivity();
//        }
        goToMainActivity();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void loadError(Throwable e) {

    }

    @Override
    public void loginResult(LoginModel loginModel) {
        switch (loginModel.getHead().getErrCode()) {
            //登录成功
            case "00000000":
                //保存用户信息
                mUserManager.setUserInfo(loginModel);
                goToMainActivity();
                break;
            //登录失败
            default:
                mUserManager.clearAccountInfo();
                goToLoginActivity();
                break;
        }
    }

    private void goToLoginActivity() {
        LoginActivity.startLoginActivity(this);
        finish();
    }

    private void goToMainActivity() {
        MainActivity.startActivity(this);
        finish();
    }
}
