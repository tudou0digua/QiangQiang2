package com.cb.qiangqiang2.presenter;

import com.cb.qiangqiang2.common.base.BasePresenter;
import com.cb.qiangqiang2.common.constant.Constants;
import com.cb.qiangqiang2.data.api.HttpManager;
import com.cb.qiangqiang2.data.model.PostDetailModel;
import com.cb.qiangqiang2.mvpview.PostDetailMvpView;

import java.util.Map;

import javax.inject.Inject;

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
}
