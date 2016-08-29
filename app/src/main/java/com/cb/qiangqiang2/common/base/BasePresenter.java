package com.cb.qiangqiang2.common.base;

/**
 * Created by cb on 2016/8/29.
 */
public class BasePresenter<T extends BaseMVPView> implements Presenter<T> {
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
