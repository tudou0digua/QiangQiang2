package com.cb.qiangqiang2.presenter;

import android.content.Context;

import com.cb.qiangqiang2.common.base.BasePresenter;
import com.cb.qiangqiang2.common.constant.Constants;
import com.cb.qiangqiang2.common.dagger.qualifier.ForActivity;
import com.cb.qiangqiang2.data.api.ApiService;
import com.cb.qiangqiang2.data.api.HttpManager;
import com.cb.qiangqiang2.data.model.PostModel;
import com.cb.qiangqiang2.mvpview.PostMvpView;

import java.util.Map;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by cb on 2016/10/20.
 */

public class PostPresenter extends BasePresenter<PostMvpView> {

    @Inject
    ApiService apiService;

    @ForActivity
    @Inject
    Context mContext;

    @Inject
    public PostPresenter() {
    }

    public void refreshPostListData(boolean isFromUser, String sortBy, int page, int boardId, String type, int userId){
        if (getMvpView() != null) getMvpView().showLoading();
        Map<String, String> map = HttpManager.getBaseMap(mContext);
        Observable<PostModel> observable;
        if (isFromUser) {
            map.put(Constants.PAGE, String.valueOf(page));
            map.put(Constants.UID, String.valueOf(userId));
            map.put(Constants.TYPE, type);
            observable = apiService.getUserTopicList(map);
        } else {
            map.put(Constants.PAGE_SIZE, Constants.DEFAULT_PAGE_SIZE);
            map.put(Constants.PAGE, String.valueOf(page));
            map.put(Constants.BOARD_ID, String.valueOf(boardId));
            map.put(Constants.SORT_BY, sortBy);
            observable = apiService.getTopicList(map);
        }
        HttpManager.isNeedFormatDataLogger = true;
        HttpManager.toSub(observable, new HttpManager.OnResponse() {
            @Override
            public void onSuccess(Object result) {
                if (getMvpView() == null) return;
                if (result != null) {
                    getMvpView().hideLoading();
                    getMvpView().refreshData((PostModel) result);
                } else {
                    getMvpView().hideLoading();
                    getMvpView().refreshEmpty();
                }
            }

            @Override
            public void onError(Throwable e) {
                if (getMvpView() == null) return;
                getMvpView().hideLoading();
                getMvpView().loadError(e);
            }
        }, mContext);
    }

    public void loadMorePostListData(boolean isFromUser, String sortBy, int page, int boardId, String type, int userId){
        Map<String, String> map = HttpManager.getBaseMap(mContext);
        Observable<PostModel> observable;
        if (isFromUser) {
            map.put(Constants.PAGE, String.valueOf(page));
            map.put(Constants.UID, String.valueOf(userId));
            map.put(Constants.TYPE, type);
            observable = apiService.getUserTopicList(map);
        } else {
            map.put(Constants.PAGE_SIZE, Constants.DEFAULT_PAGE_SIZE);
            map.put(Constants.PAGE, String.valueOf(page));
            map.put(Constants.BOARD_ID, String.valueOf(boardId));
            map.put(Constants.SORT_BY, sortBy);
            observable = apiService.getTopicList(map);
        }
        HttpManager.toSub(observable, new HttpManager.OnResponse() {
            @Override
            public void onSuccess(Object result) {
                if (getMvpView() == null) return;
                if (result != null) {
                    getMvpView().hideLoadMoreView();
                    getMvpView().loadMoreData((PostModel) result);
                } else {
                    getMvpView().hideLoadMoreView();
                    getMvpView().loadMoreEmpty();
                }
            }

            @Override
            public void onError(Throwable e) {
                if (getMvpView() == null) return;
                getMvpView().hideLoadMoreView();
                getMvpView().loadMoreError(e);
            }
        }, mContext);
    }
}
