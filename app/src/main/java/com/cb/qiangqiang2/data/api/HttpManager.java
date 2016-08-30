package com.cb.qiangqiang2.data.api;

import android.content.Context;

import com.cb.qiangqiang2.common.constant.Constants;
import com.cb.qiangqiang2.common.util.PreferencesUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by cb on 2016/8/29.
 */
@Singleton
public class HttpManager {
    public static long READ_TIME_OUT = 10;
    public static long CONNECT_TIME_OUT = 10;

    HttpLoggingInterceptor mHttpLoggingInterceptor;

    @Inject
    HttpHeaderInterceptor mHttpHeaderInterceptor;

    @Inject
    CacheControlInterceptor mCacheControlInterceptor;

    @Inject
    public HttpManager() {
        mHttpLoggingInterceptor = new HttpLoggingInterceptor(new okhttp3.logging.HttpLoggingInterceptor.Logger() {
            @Override public void log(String message) {
                Timber.tag("HttpManager-OkHttp").e(message);
            }
        });
        mHttpLoggingInterceptor.setLevel(okhttp3.logging.HttpLoggingInterceptor.Level.HEADERS);
    }

    public ApiService getService() {
        //httpClient request config
        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(mHttpHeaderInterceptor)
                .addInterceptor(mHttpLoggingInterceptor)
//                .addInterceptor(mCacheControlInterceptor)
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

    public static void toSubscribe(Observable o, Subscriber s){
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

    public static Map<String, String> getBaseMap(Context context){
        Map<String, String> map = new HashMap<>();

        if (PreferencesUtils.getString(context, Constants.FORUM_KEY) != null) {

        }

        return map;
    }

}
