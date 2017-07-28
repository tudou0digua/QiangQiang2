package com.cb.qiangqiang2.data.api;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.cb.qiangqiang2.common.constant.Constants;
import com.cb.qiangqiang2.common.dagger.qualifier.ForApplication;
import com.cb.qiangqiang2.common.util.PrefUtils;
import com.cb.qiangqiang2.data.api.cookie.CookiesManager;
import com.cb.qiangqiang2.data.model.BaseModel;
import com.cb.qiangqiang2.ui.activity.LoginActivity;
import com.orhanobut.logger.Logger;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.Cookie;
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

    @Inject
    HttpHeaderInterceptor mHttpHeaderInterceptor;
    @Inject
    CacheControlInterceptor mCacheControlInterceptor;
    @Inject
    CookiesManager cookiesManager;

    private HttpLoggingInterceptor mHttpLoggingInterceptor;
    private Context context;
    private static HttpManager httpManager;

    @Inject
    public HttpManager(@ForApplication Context context) {
        this.context = context;
        httpManager = this;
        mHttpLoggingInterceptor = new HttpLoggingInterceptor(new okhttp3.logging.HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                try {
                    JSONObject jsonObject = new JSONObject(message);
                    //是否需要用Logger格式化打印原生返回的json数据
                    if (isNeedFormatDataLogger) {
                        Logger.json(jsonObject.toString());
                        isNeedFormatDataLogger = false;
                    } else {
                        Timber.tag("HttpManager-OkHttp").e(message);
                    }
                } catch (Exception e) {
                    Timber.tag("HttpManager-OkHttp").e(message);
                }
            }
        });
        mHttpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    public static HttpManager getInstance() {
        return httpManager;
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
                .cookieJar(cookiesManager)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();
        return retrofit.create(ApiService.class);
    }

    public static void toSubscribe(Observable o, Subscriber s) {
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

    public static void toSub(Observable o, @NonNull final OnResponse onResponse, final Context context) {
        o.compose(SchedulerTransformer.create())
                .subscribe(new Subscriber() {
                    @Override
                    public void onCompleted() {
                        if (onResponse instanceof OnResponseWithComplete) {
                            ((OnResponseWithComplete) onResponse).onComplete();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        onResponse.onError(e);
                    }

                    @Override
                    public void onNext(Object o) {
                        try {
                            if (o instanceof BaseModel) {
                                BaseModel baseModel = (BaseModel) o;
                                if (baseModel.getRs() == 1) {
                                    onResponse.onSuccess(o);
                                } else {
                                    switch (baseModel.getHead().getErrCode()) {
                                        case 100001:
                                            //TODO 未登陆 可进行登陆等操作
//                                        Toast.makeText(context, baseModel.getHead().getErrInfo(), Toast.LENGTH_SHORT).show();
                                            LoginActivity.startLoginActivity(context);
                                            break;
                                        default:
                                            onResponse.onSuccess(o);
                                    }
                                }
                            } else {
                                onResponse.onSuccess(o);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            onResponse.onSuccess(o);
                        }
                    }
                });
    }

    public static Map<String, String> getBaseMap(Context context) {
        Map<String, String> map = new HashMap<>();
        //Fixed Value
        map.put(Constants.FORUM_KEY, "FuXlu6ShCTYC2q8Ysn");
        String accessToken = PrefUtils.getString(context, Constants.ACCESS_TOKEN);
        String accessSecret = PrefUtils.getString(context, Constants.ACCESS_SECRET);
        if (accessToken != null) {
            map.put(Constants.ACCESS_TOKEN, accessToken);
        } else {
            //论坛关闭游客模式之后，无法登录，需要传历史ACCESS_TOKEN和ACCESS_SECRET
            //d0b81b5b42c9543193cfa22673f10
            map.put(Constants.ACCESS_TOKEN, "b459663e3080de87270c849fbdb74");
        }
        if (accessSecret != null) {
            map.put(Constants.ACCESS_SECRET, accessSecret);
        } else {
            //fd238ec1c3a674c38f3aef139b91c
            map.put(Constants.ACCESS_SECRET, "99c732f3e3887983103a6ceb1a07a");
        }
        return map;
    }

    public interface OnResponse {
        void onSuccess(Object result);

        void onError(Throwable e);
    }

    public interface OnResponseWithComplete extends OnResponse {
        void onComplete();
    }

    /**
     * 给webview请求设置cookie
     *
     * @param url
     */
    public void syncWebViewCookie(String url) {
        try {
            CookieSyncManager cookieSyncManager = CookieSyncManager.createInstance(context);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            cookieManager.removeSessionCookie();// 移除
            cookieManager.removeAllCookie();
            Uri uri = Uri.parse(url);
            if (uri != null) {
                Logger.e(uri.getHost());
                List<Cookie> cookies = cookiesManager.getCookieStore().get(uri.getHost());
                for (int i = 0; i < cookies.size(); i++) {
                    Cookie cookie = cookies.get(i);
                    StringBuilder builder = new StringBuilder();
                    builder.append(cookie.name())
                            .append("=")
                            //URLEncoder.encode(auth,"utf-8") auth可能需要转义下
                            .append(cookie.value())
                            .append("; ")
                            //Android 4.2.2 url要是domaim, setCookie才会全部设置，不漏
                            .append("domian=qiangqiang5.com; path=/");
                    String cookieValue = builder.toString();
                    cookieManager.setCookie(url, cookieValue);
                }
            }
            cookieSyncManager.sync();
        } catch (Exception e) {
            Log.e("Nat: syncCookie failed", e.toString());
        }
    }

    public void clearCookie() {
        cookiesManager.clearCookie();
    }

}
