package com.cb.qiangqiang2.presenter;

import android.content.Context;
import android.widget.Toast;

import com.cb.qiangqiang2.common.base.BasePresenter;
import com.cb.qiangqiang2.common.constant.Constants;
import com.cb.qiangqiang2.data.api.HttpManager;
import com.cb.qiangqiang2.data.model.BaseModel;
import com.cb.qiangqiang2.data.model.PostDetailModel;
import com.cb.qiangqiang2.mvpview.PostDetailMvpView;

import java.util.Map;

import javax.inject.Inject;

import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;

/**
 * Created by cb on 2016/12/8.
 */

public class PostDetailPresenter extends BasePresenter<PostDetailMvpView> {

    @Inject
    public PostDetailPresenter() {
    }

    public void getPostDetail(final boolean isLoadMore, int topicId, int boardId, int page,
                              int pageSize) {
        if (getMvpView() != null) {
            if (isLoadMore) {
                getMvpView().showLoadMoreView();
            } else {
                getMvpView().showLoading();
            }
        }
        Map<String, String> map = HttpManager.getBaseMap(mContext);
        map.put(Constants.TOPIC_ID, String.valueOf(topicId));
        map.put(Constants.BOARD_ID, String.valueOf(boardId));
        map.put(Constants.AUTHOR_ID, String.valueOf(0));
        map.put(Constants.PAGE, String.valueOf(page));
        map.put(Constants.PAGE_SIZE, String.valueOf(pageSize));
        HttpManager.isNeedFormatDataLogger = true;
        HttpManager.toSub(mApiService.getPostDetail(map), new HttpManager.OnResponse() {
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
                    getMvpView().loadMoreData((PostDetailModel) result);
                } else {
                    getMvpView().hideLoading();
                    getMvpView().refreshData((PostDetailModel) result);
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

    public void setCollectionStatus(final boolean isCollection, int topicId) {
        Map<String, String> map = HttpManager.getBaseMap(mContext);
        Observable<BaseModel> observable;
        if (isCollection) {
            map.put(Constants.ACTION, Constants.POST_ACTION_FAVORITE);
        } else {
            map.put(Constants.ACTION, Constants.POST_ACTION_DELFAVORITE);
        }
        map.put(Constants.ID_TYPE, Constants.POST_ID_TYPE_TID);
        map.put(Constants.ID, String.valueOf(topicId));
        observable = mApiService.setCollectionStatus(map);
        HttpManager.isNeedFormatDataLogger = true;
        HttpManager.toSub(observable, new HttpManager.OnResponse() {
            @Override
            public void onSuccess(Object result) {
                BaseModel baseModel = (BaseModel) result;
                switch (baseModel.getHead().getErrCode()) {
                    //收藏成功
                    case "02000030":
                        getMvpView().operateCollectionSuccess(true, true);
                        return;
                    //取消收藏成功
                    case "00000000":
                        getMvpView().operateCollectionSuccess(false, true);
                        return;
                    //已收藏，重复收藏
                    case "02000029":
                        getMvpView().operateCollectionSuccess(true, false);
                        return;
                    //未收藏，进行取消收藏
                    case "02000031":
                        getMvpView().operateCollectionSuccess(false, false);
                        return;
                    //抱歉，您指定的信息无法收藏(传的参数不对时)
                    case "02000028":

                        break;
                }
                getMvpView().operateCollectionFail();
                Toast.makeText(mContext, baseModel.getHead().getErrInfo(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    HttpException exception = (HttpException) e;
                    int code = exception.code();
                    if (code == 500) {
                        getMvpView().operateCollectionSuccess(isCollection, true);
                        return;
                    }
                }
                getMvpView().operateCollectionFail();
            }

            @Override
            protected void onUnLogin(Context context, Object result) {
                super.onUnLogin(context, result);
                getMvpView().operateCollectionFail();
            }
        }, mContext);
    }
}
