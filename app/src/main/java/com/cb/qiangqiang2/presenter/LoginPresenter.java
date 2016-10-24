package com.cb.qiangqiang2.presenter;

import android.content.Context;

import com.cb.qiangqiang2.common.base.BasePresenter;
import com.cb.qiangqiang2.common.constant.Constants;
import com.cb.qiangqiang2.common.dagger.qualifier.ForActivity;
import com.cb.qiangqiang2.data.api.ApiService;
import com.cb.qiangqiang2.data.api.HttpManager;
import com.cb.qiangqiang2.data.model.LoginModel;
import com.cb.qiangqiang2.mvpview.LoginMvpView;

import java.util.Map;

import javax.inject.Inject;

/**
 * Created by cb on 2016/10/24.
 */

public class LoginPresenter extends BasePresenter<LoginMvpView> {

    @ForActivity
    @Inject
    Context mContext;

    @Inject
    ApiService mApiService;

    @Inject
    public LoginPresenter() {
    }

    public void login(String username, String password) {
        Map<String, String> map = HttpManager.getBaseMap(mContext);
        map.put(Constants.USERNAME, username);
        map.put(Constants.PASSWORD, password);
        HttpManager.toSub(mApiService.login(map), new HttpManager.OnResponse() {
            @Override
            public void onSuccess(Object result) {
                if (getMvpView() == null) return;
                getMvpView().hideLoading();
                if (result != null) {
                    getMvpView().loginResult((LoginModel) result);
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
        }, mContext);
    }
}
