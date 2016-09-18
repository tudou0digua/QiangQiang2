package com.cb.qiangqiang2.data.api;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.cb.qiangqiang2.common.constant.Constants;
import com.cb.qiangqiang2.common.util.PrefUtils;
import com.cb.qiangqiang2.data.model.BaseModel;
import com.orhanobut.logger.Logger;

import org.json.JSONObject;

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

    public static boolean isNeedFormatDataLogger = false;

    HttpLoggingInterceptor mHttpLoggingInterceptor;

    @Inject
    HttpHeaderInterceptor mHttpHeaderInterceptor;

    @Inject
    CacheControlInterceptor mCacheControlInterceptor;

    @Inject
    public HttpManager() {
        mHttpLoggingInterceptor = new HttpLoggingInterceptor(new okhttp3.logging.HttpLoggingInterceptor.Logger() {
            @Override public void log(String message) {
                    try {
                        JSONObject jsonObject = new JSONObject( message );
                        //是否需要用Logger格式化打印原生返回的json数据
                        if (isNeedFormatDataLogger) {
                            Logger.json(jsonObject.toString());
                            isNeedFormatDataLogger = false;
                        } else {
                            Timber.tag("HttpManager-OkHttp").e(message);
                        }
                    } catch (Exception e){
                        Timber.tag("HttpManager-OkHttp").e(message);
                    }
            }
        });
        mHttpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
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

    public static void toSubscribe(Observable o, @NonNull final OnResponse onResponse){
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber() {
                    @Override
                    public void onCompleted() {
                        onResponse.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        onResponse.onError(e);
                    }

                    @Override
                    public void onNext(Object o) {
                        BaseModel baseModel = (BaseModel) o;
                        if (baseModel.getRs() == 1){
                            onResponse.onSuccess(0);
                        } else {
                            switch (baseModel.getHead().getErrCode()){
                                case 00100001:
                                    //TODO not sign in

                                    break;
                            }
                        }
                    }
                });
    }

    public static void toSub(Observable o, @NonNull final OnResponse onResponse, final Context context){
        o.compose(new DefaultTransformer(context))
                .subscribe(new Subscriber() {
                    @Override
                    public void onCompleted() {
                        onResponse.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        onResponse.onError(e);
                    }

                    @Override
                    public void onNext(Object o) {
                        BaseModel baseModel = (BaseModel) o;
                        if (baseModel.getRs() == 1){
                            onResponse.onSuccess(o);
                        } else {
                            switch (baseModel.getHead().getErrCode()){
                                case 00100001:
                                    //TODO not sign in
                                    Toast.makeText(context, baseModel.getHead().getErrInfo(), Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }
                    }
                });
    }



    public static Map<String, String> getBaseMap(Context context){
        Map<String, String> map = new HashMap<>();
        //Fixed Value
        map.put(Constants.FORUM_KEY, "FuXlu6ShCTYC2q8Ysn");
        String accessToken = PrefUtils.getString(context, Constants.ACCESS_TOKEN);
        String accessSecret = PrefUtils.getString(context, Constants.ACCESS_SECRET);
        if (accessToken != null) map.put(Constants.ACCESS_TOKEN, accessToken);
        if (accessSecret != null) map.put(Constants.ACCESS_SECRET, accessSecret);
        return map;
    }

    public interface OnResponse{
        void onSuccess(Object result);
        void onError(Throwable e);
        void onCompleted();
    }

}
