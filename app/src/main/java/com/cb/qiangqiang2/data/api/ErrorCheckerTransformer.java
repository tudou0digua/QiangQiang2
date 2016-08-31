package com.cb.qiangqiang2.data.api;

import android.content.Context;

import com.cb.qiangqiang2.data.model.BaseModel;

import retrofit2.Response;
import rx.Observable;
import rx.exceptions.Exceptions;
import rx.functions.Func1;

/**
 * Created by cb on 2016/8/31.
 */
public class ErrorCheckerTransformer<T extends Response<R>, R extends BaseModel>
        implements Observable.Transformer<T, R> {

    public static final String DEFAULT_ERROR_MESSAGE = "Oh, no";

    private Context mContext;

    public ErrorCheckerTransformer(final Context context) {
        mContext = context;
    }

    @Override
    public Observable<R> call(Observable<T> observable) {
        return observable.map(new Func1<T, R>() {
            @Override
            public R call(T t) {
                String msg = null;
                if (!t.isSuccessful() || t.body() == null) {
                    msg = DEFAULT_ERROR_MESSAGE;
                } else if (t.body().getRs() != 1) {
                    msg = t.body().getHead().getErrInfo();
                    if (msg == null) {
                        msg = DEFAULT_ERROR_MESSAGE;
                    }
                }

                if (msg != null) {
                    try {
                        throw new ApiException(msg);
                    } catch (ApiException e) {
                        throw Exceptions.propagate(e);
                    }
                }

                return t.body();
            }
        });
    }
}
