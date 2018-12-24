package com.cb.qiangqiang2.mvpview;

import com.cb.qiangqiang2.common.base.BaseMVPView;

/**
 * Created by cb on 2016/12/12.
 */

public interface ReplyPostMvpView extends BaseMVPView {

    void replySuccess();

    void replyFail(String errorMsg);
}
