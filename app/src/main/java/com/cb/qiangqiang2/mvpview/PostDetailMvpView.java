package com.cb.qiangqiang2.mvpview;

import com.cb.qiangqiang2.common.base.BaseMVPViewLoadMore;
import com.cb.qiangqiang2.data.model.PostDetailModel;

/**
 * Created by cb on 2016/12/8.
 */

public interface PostDetailMvpView extends BaseMVPViewLoadMore<PostDetailModel> {
    void operateCollectionSuccess(boolean isCollection, boolean showToast);
    void operateCollectionFail();
}
