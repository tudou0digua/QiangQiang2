package com.cb.qiangqiang2.presenter;

import com.cb.qiangqiang2.common.base.BasePresenter;
import com.cb.qiangqiang2.common.constant.Constants;
import com.cb.qiangqiang2.data.api.HttpManager;
import com.cb.qiangqiang2.data.model.UserListModel;
import com.cb.qiangqiang2.mvpview.UserListMvpView;

import java.util.Map;

import javax.inject.Inject;

/**
 * Created by cb on 2016/10/26.
 */

public class UserListPresenter extends BasePresenter<UserListMvpView> {

    @Inject
    public UserListPresenter() {
    }

    public void getUserList(final boolean isLoadMore, int userId, String type, int page) {
        if (getMvpView() != null) {
            if (isLoadMore) {
                getMvpView().showLoadMoreView();
            } else {
                getMvpView().showLoading();
            }
        }
        Map<String, String> map = HttpManager.getBaseMap(mContext);
        map.put(Constants.UID, String.valueOf(userId));
        map.put(Constants.TYPE, String.valueOf(type));
        map.put(Constants.PAGE, String.valueOf(page));
        map.put(Constants.ORDER_BY, String.valueOf(Constants.USER_LIST_DATELINE));
        HttpManager.toSub(mApiService.getUserList(map), new HttpManager.OnResponse() {
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
                    getMvpView().loadMoreData((UserListModel) result);
                } else {
                    getMvpView().hideLoading();
                    getMvpView().refreshData((UserListModel) result);
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
