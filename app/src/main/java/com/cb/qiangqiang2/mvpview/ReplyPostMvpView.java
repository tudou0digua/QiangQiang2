package com.cb.qiangqiang2.mvpview;

import com.cb.qiangqiang2.common.base.BaseMVPView;
import com.cb.qiangqiang2.data.model.ReplyPostModel;

/**
 * Created by cb on 2016/12/12.
 */

public interface ReplyPostMvpView extends BaseMVPView {
    void replyResult(ReplyPostModel replyPostModel);
}
