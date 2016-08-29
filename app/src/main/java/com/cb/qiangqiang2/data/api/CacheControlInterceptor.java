package com.cb.qiangqiang2.data.api;

import android.content.Context;

import com.cb.qiangqiang2.common.dagger.qualifier.ForApplication;
import com.cb.qiangqiang2.util.AppUtils;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by cb on 2016/8/29.
 *
 * ref:https://github.com/CameloeAnthony/MVPCommon/blob/master/datingBall/src/main/java/edu/com/app/data/HttpHelper.java
 */
public class CacheControlInterceptor implements Interceptor {
    @Inject
    @ForApplication
    Context mContext;

    @Inject
    public CacheControlInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!AppUtils.isNetworkConnected(mContext)) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }

        Response response = chain.proceed(request);

        if (AppUtils.isNetworkConnected(mContext)) {
            int maxAge = 60 * 60; // read from cache for 1 minute
            response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build();
        } else {
            int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
            response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }
        return response;
    }
}
