package com.cb.qiangqiang2.data.api;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by cb on 2016/8/29.
 */
@Singleton
public class HttpManager {
    public static long READ_TIME_OUT = 10;
    public static long CONNECT_TIME_OUT = 10;

    @Inject
    HttpLoggingInterceptor mHttpLoggingInterceptor;

    @Inject
    HttpHeaderInterceptor mHttpHeaderInterceptor;

    @Inject
    CacheControlInterceptor mCacheControlInterceptor;

    @Inject
    public HttpManager() {
    }

    public ApiService getService() {
        //httpClient request config
        mHttpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(mHttpHeaderInterceptor)
                .addInterceptor(mHttpLoggingInterceptor)
                .addInterceptor(mCacheControlInterceptor)
                //time out
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();
        return retrofit.create(ApiService.class);
    }

    public void toSubscribe(Observable o, Subscriber s){
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

}
