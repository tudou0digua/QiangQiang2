package com.cb.qiangqiang2.presenter;

import android.content.Context;

import com.cb.qiangqiang2.common.base.BasePresenter;
import com.cb.qiangqiang2.common.constant.Constants;
import com.cb.qiangqiang2.data.UserManager;
import com.cb.qiangqiang2.data.api.HttpManager;
import com.cb.qiangqiang2.data.model.LoginModel;
import com.cb.qiangqiang2.mvpview.LoginMvpView;

import java.util.Map;

import javax.inject.Inject;

/**
 * Created by cb on 2016/10/24.
 */

public class LoginPresenter extends BasePresenter<LoginMvpView> {

    @Inject
    UserManager mUserManager;

    @Inject
    public LoginPresenter() {
    }

    public void login(final String username, final String password) {
        Map<String, String> map = HttpManager.getBaseMap(mContext);
        map.put(Constants.USERNAME, username);
        map.put(Constants.PASSWORD, password);
        HttpManager.isNeedFormatDataLogger = true;
        HttpManager.toSub(mApiService.login(map), new HttpManager.OnResponse() {
            @Override
            public void onSuccess(Object result) {
                if (getMvpView() == null) return;
                getMvpView().hideLoading();
                if (result != null) {
                    LoginModel loginModel = (LoginModel) result;
                    switch (loginModel.getHead().getErrCode()) {
                        //登录成功
                        case "00000000":
                            //保存用户信息
                            mUserManager.setUserInfo(loginModel);
                            //保存账号密码
                            mUserManager.saveAccountInfoToLocal(username, password);
                            mUserManager.saveLastLoginSuccessTime();
                            getMvpView().loginSuccess(loginModel);
                            break;
                        default:
                            getMvpView().loginFail(loginModel.getHead().getErrInfo());
                            break;
                    }
                } else {
                    getMvpView().loadError(null);
                }
            }

            @Override
            public void onError(Throwable e) {
                if (getMvpView() == null) return;
                getMvpView().hideLoading();
                getMvpView().loadError(e);
            }

            @Override
            protected void onUnLogin(Context context, Object result) {
                if (getMvpView() == null) return;
                getMvpView().loginFail(((LoginModel) result).getHead().getErrInfo());
            }
        }, mContext);
    }
}
