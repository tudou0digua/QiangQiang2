package com.cb.qiangqiang2.mvpview;

import com.cb.qiangqiang2.common.base.BaseMVPView;
import com.cb.qiangqiang2.data.model.SearchPostResultModel;

/**
 * Created by cb on 2016/11/30.
 */

public interface SearchPostMvpView extends BaseMVPView {
    void searchPostResult(SearchPostResultModel model);
}
