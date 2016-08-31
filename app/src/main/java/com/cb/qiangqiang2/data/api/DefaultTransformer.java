package com.cb.qiangqiang2.data.api;

import android.content.Context;

import com.cb.qiangqiang2.data.model.BaseModel;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by cb on 2016/8/31.
 */
public class DefaultTransformer<T extends Response<R>, R extends BaseModel>
        implements Observable.Transformer<T, R> {

    private Context mContext;

    public DefaultTransformer(final Context context) {
        mContext = context;
    }

    @Override
    public Observable<R> call(Observable<T> observable) {
        return observable
                .compose(new SchedulerTransformer<T>())
                .compose(new ErrorCheckerTransformer<T, R>(mContext));
    }
}