package com.cb.qiangqiang2.presenter;

import com.cb.qiangqiang2.common.base.BasePresenter;
import com.cb.qiangqiang2.common.constant.Constants;
import com.cb.qiangqiang2.data.api.HttpManager;
import com.cb.qiangqiang2.data.model.SearchUserResultModel;
import com.cb.qiangqiang2.mvpview.SearchUserMvpView;

import java.util.Map;

import javax.inject.Inject;

/**
 * Created by cb on 2016/12/6.
 */

public class SearchUserPresenter extends BasePresenter<SearchUserMvpView> {

    @Inject
    public SearchUserPresenter() {
    }

    public void searchUser(final boolean isLoadMore, String keyword, int page, int pageSize) {
        if (getMvpView() != null) {
            if (isLoadMore) {
                getMvpView().showLoadMoreView();
            } else {
                getMvpView().showLoading();
            }
        }
        Map<String, String> map = HttpManager.getBaseMap(mContext);
        map.put(Constants.PAGE, String.valueOf(page));
        map.put(Constants.PAGE_SIZE, String.valueOf(pageSize));
        map.put(Constants.KEYWORD, keyword);
        HttpManager.toSub(mApiService.searchUser(map), new HttpManager.OnResponse() {
            @Override
            public void onSuccess(Object result) {
                if (getMvpView() == null) return;
                if (result == null) {
                    if (isLoadMore) {
                        getMvpView().hideLoadMoreView();
                        getMvpView().loadMoreEmpty();
                    } else {
                        getMvpView().hideLoading();
                        getMvpView().refreshEmpty();
                    }
                    return;
                }
                if (isLoadMore) {
                    getMvpView().hideLoadMoreView();
                    getMvpView().loadMoreData((SearchUserResultModel) result);
                } else {
                    getMvpView().hideLoading();
                    getMvpView().refreshData((SearchUserResultModel) result);
                }
            }

            @Override
            public void onError(Throwable e) {
                if (getMvpView() == null) return;
                if (isLoadMore) {
                    getMvpView().hideLoadMoreView();
                    getMvpView().loadMoreError(e);
                } else {
                    getMvpView().hideLoading();
                    getMvpView().loadError(e);
                }
            }
        }, mContext);
    }
}
