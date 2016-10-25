package com.cb.qiangqiang2.presenter;

import android.content.Context;

import com.cb.qiangqiang2.common.base.BasePresenter;
import com.cb.qiangqiang2.common.constant.Constants;
import com.cb.qiangqiang2.common.dagger.qualifier.ForActivity;
import com.cb.qiangqiang2.data.api.ApiService;
import com.cb.qiangqiang2.data.api.HttpManager;
import com.cb.qiangqiang2.data.model.UserInfoModel;
import com.cb.qiangqiang2.mvpview.UserInfoMvpView;

import java.util.Map;

import javax.inject.Inject;

/**
 * Created by cb on 2016/10/25.
 */

public class UserInfoPresenter extends BasePresenter<UserInfoMvpView> {
    @Inject
    @ForActivity
    Context mContext;

    @Inject
    ApiService mApiService;

    @Inject
    public UserInfoPresenter() {
    }

    public void getUserInfo(int userId) {
        Map<String, String> map = HttpManager.getBaseMap(mContext);
        map.put(Constants.USER_ID, String.valueOf(userId));
        HttpManager.toSub(mApiService.getUserInfo(map), new HttpManager.OnResponse() {
            @Override
            public void onSuccess(Object result) {
                if (getMvpView() == null) return;
                if (result == null) {
                    getMvpView().loadError(null);
                } else {
                    getMvpView().showUserInfoData((UserInfoModel) result);
                }
            }

            @Override
            public void onError(Throwable e) {
                if (getMvpView() == null) return;
                getMvpView().loadError(e);
            }
        }, mContext);

    }

}
