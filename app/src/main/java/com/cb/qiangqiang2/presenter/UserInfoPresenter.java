package com.cb.qiangqiang2.presenter;

import android.content.Context;

import com.cb.qiangqiang2.common.base.BasePresenter;
import com.cb.qiangqiang2.common.dagger.qualifier.ForActivity;
import com.cb.qiangqiang2.data.api.ApiService;
import com.cb.qiangqiang2.data.api.HttpManager;
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

    public void getUserInfo(int userId) {
        Map<String, String> map = HttpManager.getBaseMap(mContext);
    }

}
