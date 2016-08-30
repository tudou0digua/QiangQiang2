package com.cb.qiangqiang2.data.api;

import android.content.Context;

import com.cb.qiangqiang2.common.dagger.qualifier.ForApplication;

import java.io.IOException;
import java.util.Locale;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by cb on 2016/8/29.
 */
public class HttpHeaderInterceptor implements Interceptor {
    @Inject
    public HttpHeaderInterceptor() {
    }

    @Inject
    @ForApplication
    Context mContext;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        //set header
        Locale locale = mContext.getResources().getConfiguration().locale;
        Request.Builder builder = request.newBuilder();
        builder.addHeader("Accept-Encoding", "gzip, deflate")
                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
//                .addHeader("Accept-Encoding", "gzip")
                .addHeader("Connection", "Keep-Alive")
//                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.72 Safari/537.36")
                .addHeader("Accept-Language", locale.getLanguage() + "-" + locale.getCountry());
        builder.build();
        return chain.proceed(request);
    }


}
