package com.cb.qiangqiang2.data;

import android.content.Context;
import android.text.TextUtils;

import com.cb.qiangqiang2.common.constant.Constants;
import com.cb.qiangqiang2.common.dagger.qualifier.ForApplication;
import com.cb.qiangqiang2.common.util.PrefUtils;
import com.cb.qiangqiang2.data.model.LoginModel;
import com.google.gson.Gson;

import javax.inject.Inject;

/**
 * Created by cb on 2016/10/24.
 */

public class UserManager {
    public static final int INVALID_USER_ID = -1;
    public static final String USER_INFO_DATA = "user_info_data";

    private Context mContext;

    private LoginModel mUserInfo;

    @Inject
    public UserManager(@ForApplication Context context) {
        mContext = context;
    }

    public void setUserInfo(LoginModel loginModel) {
        mUserInfo = loginModel;
        Gson gson = new Gson();
        String userInfoString = gson.toJson(loginModel);
        if (userInfoString != null)
            PrefUtils.putString(mContext, USER_INFO_DATA, userInfoString);
        PrefUtils.putString(mContext, Constants.ACCESS_TOKEN, loginModel.getToken());
        PrefUtils.putString(mContext, Constants.ACCESS_SECRET, loginModel.getSecret());
        PrefUtils.putInt(mContext, Constants.UID, loginModel.getUid());
    }

    public LoginModel getUserInfo() {
        if (mUserInfo == null) {
            String userInfoString = PrefUtils.getString(mContext, USER_INFO_DATA);
            if (!TextUtils.isEmpty(userInfoString)) {
                Gson gson = new Gson();
                mUserInfo = gson.fromJson(userInfoString, LoginModel.class);
            }
        }
        return mUserInfo;
    }

    public int getUserId() {
        if (getUserInfo() != null) {
            return mUserInfo.getUid();
        }
        return INVALID_USER_ID;
    }

    public String getUserName() {
        if (getUserInfo() != null) {
            return mUserInfo.getUserName();
        }
        return "";
    }
}
