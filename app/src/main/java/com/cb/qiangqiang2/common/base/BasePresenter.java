package com.cb.qiangqiang2.common.base;

import android.content.Context;

import com.cb.qiangqiang2.common.dagger.qualifier.ForActivity;
import com.cb.qiangqiang2.data.api.ApiService;

import javax.inject.Inject;

/**
 * Created by cb on 2016/8/29.
 */
public class BasePresenter<T extends BaseMVPView> implements Presenter<T> {

    @Inject
    @ForActivity
    public Context mContext;

    @Inject
    public ApiService mApiService;

    private T mMvpView;

    @Override
    public void attachView(T mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public void detachView() {
        if (mMvpView != null) {
            mMvpView = null;
        }
    }

    public boolean isViewAttached() {
        return mMvpView != null && mMvpView != null;
    }

    public T getMvpView() {
        return mMvpView;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.attachView(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }
}
