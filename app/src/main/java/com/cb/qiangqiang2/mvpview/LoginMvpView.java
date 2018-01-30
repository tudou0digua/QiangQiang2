package com.cb.qiangqiang2.mvpview;

import com.cb.qiangqiang2.common.base.BaseMVPView;
import com.cb.qiangqiang2.data.model.LoginModel;

/**
 * Created by cb on 2016/10/24.
 */

public interface LoginMvpView extends BaseMVPView {

    void loginSuccess(LoginModel loginModel);

    void loginFail(String errMsg);
}
