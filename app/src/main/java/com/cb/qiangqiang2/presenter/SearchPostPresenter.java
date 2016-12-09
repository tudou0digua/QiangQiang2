package com.cb.qiangqiang2.presenter;

import com.cb.qiangqiang2.common.base.BasePresenter;
import com.cb.qiangqiang2.common.constant.Constants;
import com.cb.qiangqiang2.data.api.HttpManager;
import com.cb.qiangqiang2.data.model.SearchPostResultModel;
import com.cb.qiangqiang2.mvpview.SearchPostMvpView;

import java.util.Map;

import javax.inject.Inject;

/**
 * Created by cb on 2016/11/30.
 */

public class SearchPostPresenter extends BasePresenter<SearchPostMvpView> {

    @Inject
    public SearchPostPresenter() {
    }

    public void searchPost(String keyword, int page, int pageSize) {
        if (getMvpView() != null) {
            getMvpView().showLoading();
        }
        Map<String, String> map = HttpManager.getBaseMap(mContext);
        map.put(Constants.PAGE, String.valueOf(page));
        map.put(Constants.PAGE_SIZE, String.valueOf(pageSize));
        map.put(Constants.KEYWORD, keyword);
        HttpManager.isNeedFormatDataLogger = true;
        HttpManager.toSub(mApiService.searchPost(map), new HttpManager.OnResponse() {
            @Override
            public void onSuccess(Object result) {
                if (getMvpView() == null) return;
                if (result == null) {
                    getMvpView().hideLoading();
                    getMvpView().loadError(null);
                    return;
                }
                getMvpView().hideLoading();
                getMvpView().searchPostResult((SearchPostResultModel) result);
            }

            @Override
            public void onError(Throwable e) {
                if (getMvpView() == null) return;
                getMvpView().hideLoading();
                getMvpView().loadError(e);
            }
        }, mContext);
    }

}
