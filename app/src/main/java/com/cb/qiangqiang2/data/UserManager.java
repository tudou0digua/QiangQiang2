package com.cb.qiangqiang2.data;

import android.content.Context;
import android.text.TextUtils;

import com.cb.qiangqiang2.common.constant.Constants;
import com.cb.qiangqiang2.common.util.DateUtil;
import com.cb.qiangqiang2.common.util.PrefUtils;
import com.cb.qiangqiang2.data.api.HttpManager;
import com.cb.qiangqiang2.data.model.AccountInfoBean;
import com.cb.qiangqiang2.data.model.LoginModel;
import com.google.gson.Gson;

import static com.cb.qiangqiang2.common.util.PrefUtils.getString;

/**
 * Created by cb on 2016/10/24.
 */

public class UserManager {
    public static final int INVALID_USER_ID = -1;
    public static final String USER_INFO_DATA = "user_info_data";
    public static final String ACCOUNT_INFO = "account_info";
    public static final String LAST_LOGIN_SUCCESS_TIME = "last_login_success_time";
    public static final String ENCRYPT_ACCOUNT_INFO_PASSWORD = "qq";

    private Context mContext;

    private LoginModel mUserInfo;

    public UserManager(Context context) {
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
            String userInfoString = getString(mContext, USER_INFO_DATA);
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

    public String getAvatarUrl() {
        if (getUserInfo() != null) {
            return mUserInfo.getAvatar();
        }
        return "";
    }

    public String getLevel() {
        if (getUserInfo() != null) {
            return mUserInfo.getUserTitle();
        }
        return "";
    }

    public void saveAccountInfoToLocal(String account, String password) {
        AccountInfoBean accountInfoBean = new AccountInfoBean();
//        account = new String(EncryptUtils.encryptDES2Base64(account.getBytes(), ENCRYPT_ACCOUNT_INFO_PASSWORD.getBytes()));
        accountInfoBean.setAccount(account);
//        password = new String(EncryptUtils.encryptDES2Base64(password.getBytes(), ENCRYPT_ACCOUNT_INFO_PASSWORD.getBytes()));
        accountInfoBean.setPassword(password);
        PrefUtils.putString(mContext, ACCOUNT_INFO, new Gson().toJson(accountInfoBean));
    }

    public void clearAccountInfo() {
        PrefUtils.delete(mContext, ACCOUNT_INFO);
    }

    public AccountInfoBean getAccountInfo() {
        AccountInfoBean accountInfoBean = null;
        try {
            String accountInfo = PrefUtils.getString(mContext, ACCOUNT_INFO);
            if (!TextUtils.isEmpty(accountInfo)) {
                accountInfoBean = new Gson().fromJson(accountInfo, AccountInfoBean.class);
                String account = accountInfoBean.getAccount();
                if (!TextUtils.isEmpty(account)) {
//                    account = new String(EncryptUtils.decryptBase64DES(account.getBytes(), ENCRYPT_ACCOUNT_INFO_PASSWORD.getBytes()));
                    accountInfoBean.setAccount(account);
                }
                String password = accountInfoBean.getPassword();
                if (!TextUtils.isEmpty(password)) {
//                    password = new String(EncryptUtils.decryptBase64DES(password.getBytes(), ENCRYPT_ACCOUNT_INFO_PASSWORD.getBytes()));
                    accountInfoBean.setPassword(password);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accountInfoBean;
    }

    public void logout(Context context) {
        mUserInfo = null;
        PrefUtils.cleaAll(context);
        HttpManager.getInstance().clearCookie();
    }

    public boolean isTodayHadLoginSuccess() {
        long lastLoginSuccessTime = PrefUtils.getLong(mContext, LAST_LOGIN_SUCCESS_TIME);
        if (lastLoginSuccessTime > 0) {
            if (DateUtil.isToday(lastLoginSuccessTime)) {
                return true;
            } else {
                clearLastLoginSuccessSuccessTime();
            }
        }
        return false;
    }

    public void saveLastLoginSuccessTime() {
        PrefUtils.putLong(mContext, LAST_LOGIN_SUCCESS_TIME, System.currentTimeMillis());
    }

    public void clearLastLoginSuccessSuccessTime() {
        PrefUtils.delete(mContext, LAST_LOGIN_SUCCESS_TIME);
    }

}
