package com.cb.qiangqiang2.data;

import android.content.Context;
import android.text.TextUtils;

import com.cb.qiangqiang2.common.constant.Constants;
import com.cb.qiangqiang2.common.util.DateUtil;
import com.cb.qiangqiang2.common.util.PrefUtils;
import com.cb.qiangqiang2.data.api.HttpManager;
import com.cb.qiangqiang2.data.model.AccountInfoBean;
import com.cb.qiangqiang2.data.model.BoardBean;
import com.cb.qiangqiang2.data.model.LoginModel;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import static com.cb.qiangqiang2.common.util.PrefUtils.getString;

/**
 * Created by cb on 2016/10/24.
 */

public class UserManager {
    public static final int INVALID_USER_ID = -1;
    public static final String USER_INFO_DATA = "user_info_data";
    public static final String ACCOUNT_INFO = "account_info";
    public static final String LAST_LOGIN_SUCCESS_TIME = "last_login_success_time";
    public static final String LAST_SIGN_SUCCESS_TIME = "last_sign_success_time";
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

    public String getSPPrefix() {
        return (getUserId() == INVALID_USER_ID ? 0 : getUserId()) + "_";
    }

    public void saveAccountInfoToLocal(String account, String password) {
        AccountInfoBean accountInfoBean = new AccountInfoBean();
//        account = new String(EncryptUtils.encryptDES2Base64(account.getBytes(), ENCRYPT_ACCOUNT_INFO_PASSWORD.getBytes()));
        accountInfoBean.setAccount(account);
//        password = new String(EncryptUtils.encryptDES2Base64(password.getBytes(), ENCRYPT_ACCOUNT_INFO_PASSWORD.getBytes()));
        accountInfoBean.setPassword(password);
        PrefUtils.putString(mContext, getSPPrefix() + ACCOUNT_INFO, new Gson().toJson(accountInfoBean));
    }

    public void clearAccountInfo() {
        PrefUtils.delete(mContext, getSPPrefix() + ACCOUNT_INFO);
    }

    public AccountInfoBean getAccountInfo() {
        AccountInfoBean accountInfoBean = null;
        try {
            String accountInfo = PrefUtils.getString(mContext, getSPPrefix() + ACCOUNT_INFO);
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
        clearAccountInfo();
        mUserInfo = null;
        PrefUtils.delete(mContext, USER_INFO_DATA);
        PrefUtils.delete(mContext, Constants.UID);
        PrefUtils.delete(mContext, Constants.ACCESS_TOKEN);
        PrefUtils.delete(mContext, Constants.ACCESS_SECRET);
        clearLastLoginSuccessSuccessTime();
        HttpManager.getInstance().clearCookie();
    }

    public void saveSelectBoardListToLocal(List<BoardBean> selectList) {
        PrefUtils.putString(mContext, getSPPrefix() + Constants.BOARD_LIST_SELECTED, new Gson().toJson(selectList));
    }

    public List<BoardBean> getSelectBoardListFromLocal() {
        String boardSelectedStr = PrefUtils.getString(mContext, getSPPrefix() + Constants.BOARD_LIST_SELECTED);
        try {
            if (!TextUtils.isEmpty(boardSelectedStr)) {
                List<BoardBean> listSelected = new Gson().fromJson(boardSelectedStr, new TypeToken<List<BoardBean>>(){}.getType());
                return listSelected;
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveUnSelectBoardListToLocal(List<BoardBean> unSelectList) {
        PrefUtils.putString(mContext, getSPPrefix() + Constants.BOARD_LIST_UNSELETED, new Gson().toJson(unSelectList));
    }

    public List<BoardBean> getUnSelectBoardListFromLocal() {
        String boardSelectedStr = PrefUtils.getString(mContext, getSPPrefix() + Constants.BOARD_LIST_UNSELETED);
        try {
            if (!TextUtils.isEmpty(boardSelectedStr)) {
                List<BoardBean> listUnSelected = new Gson().fromJson(boardSelectedStr, new TypeToken<List<BoardBean>>(){}.getType());
                return listUnSelected;
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isTodayHadLoginSuccess() {
        long lastLoginSuccessTime = PrefUtils.getLong(mContext, getSPPrefix() +  LAST_LOGIN_SUCCESS_TIME);
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
        PrefUtils.putLong(mContext, getSPPrefix() + LAST_LOGIN_SUCCESS_TIME, System.currentTimeMillis());
    }

    public void clearLastLoginSuccessSuccessTime() {
        PrefUtils.delete(mContext, getSPPrefix() + LAST_LOGIN_SUCCESS_TIME);
    }

    public boolean isTodaySignSuccess() {
        if (getUserId() == INVALID_USER_ID) {
            return false;
        }
        long lastSignSuccessTime = PrefUtils.getLong(mContext, getSPPrefix() +  LAST_SIGN_SUCCESS_TIME);
        if (lastSignSuccessTime > 0) {
            if (DateUtil.isToday(lastSignSuccessTime)) {
                return true;
            } else {
                clearLastSingSuccessSuccessTime();
            }
        }
        return false;
    }

    public void saveLastSignSuccessTime() {
        PrefUtils.putLong(mContext, getSPPrefix() + LAST_SIGN_SUCCESS_TIME, System.currentTimeMillis());
    }

    public void clearLastSingSuccessSuccessTime() {
        PrefUtils.delete(mContext, getSPPrefix() + LAST_SIGN_SUCCESS_TIME);
    }

    public boolean isNightTheme() {
        return PrefUtils.getBoolean(mContext, Constants.IS_NIGHT_THEME, false);
    }

    public void saveNightThemeStatus(boolean isNightTheme) {
        PrefUtils.putBoolean(mContext, Constants.IS_NIGHT_THEME, isNightTheme);
    }

    public boolean isAutoSign() {
        return PrefUtils.getBoolean(mContext, getSPPrefix() + Constants.IS_AUTO_SIGN, false);
    }

    public void saveAutoSignStatus(boolean isAutoSign) {
        PrefUtils.putBoolean(mContext, getSPPrefix() + Constants.IS_AUTO_SIGN, isAutoSign);
    }

}
