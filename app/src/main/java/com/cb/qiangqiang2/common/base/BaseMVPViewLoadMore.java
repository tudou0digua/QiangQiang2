package com.cb.qiangqiang2.common.base;

/**
 * Created by cb on 2016/10/20.
 */

public interface BaseMVPViewLoadMore<T> extends BaseMVPView{
    void refreshData(T t);
    void refreshEmpty();
    void loadMoreData(T t);
    void loadMoreEmpty();
    void loadMoreError(Throwable e);
    void showLoadMoreView();
    void hideLoadMoreView();
}
