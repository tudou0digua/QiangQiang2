package com.cb.qiangqiang2.common.base;

/**
 * Created by cb on 2016/8/29.
 *
 * Every presenter in the app must either implement this interface or extend BasePresenter
 * indicating the MvpView type that wants to be attached with.
 */
public interface Presenter<V extends BaseMVPView> {

    void attachView(V mvpView);

    void detachView();
}
