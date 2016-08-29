package com.cb.qiangqiang2.data.api;

import android.content.Context;

import com.cb.qiangqiang2.common.dagger.qualifier.ForApplication;

import java.io.IOException;

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
        Request.Builder builder = request.newBuilder();
        builder.addHeader("Accept-Encoding", "gzip, deflate")
                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .addHeader("Accept-Encoding", "gzip, deflate")
                .addHeader("Accept", "*/*")
                .addHeader("Cookie", "add cookies here");
//        if (!TextUtils.isEmpty(AppUtils.getUserTokenString(mContext))) {
//            builder.addHeader("authorization", AppUtils.getUserTokenString(mContext));
//            Timber.d("token is " + AppUtils.getUserTokenString(mContext));
//        }
        builder.build();
        return chain.proceed(request);
    }


}
