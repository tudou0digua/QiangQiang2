package com.cb.qiangqiang2.mvpview;

import com.cb.qiangqiang2.common.base.BaseMVPView;
import com.cb.qiangqiang2.data.model.UserInfoModel;

/**
 * Created by cb on 2016/10/25.
 */

public interface UserInfoMvpView extends BaseMVPView {
    void showUserInfoData(UserInfoModel userInfoModel);
}
