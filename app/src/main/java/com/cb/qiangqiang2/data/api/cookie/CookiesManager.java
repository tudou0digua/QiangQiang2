package com.cb.qiangqiang2.data.api.cookie;

import java.util.List;

import javax.inject.Inject;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Created by cb on 2016/10/24.
 */

public class CookiesManager implements CookieJar {
    @Inject
    PersistentCookieStore cookieStore;

    @Inject
    public CookiesManager() {

    }

    public PersistentCookieStore getCookieStore() {
        return cookieStore;
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (cookies != null && cookies.size() > 0) {
//            Logger.json(new Gson().toJson(cookies));
            for (Cookie item : cookies) {
                cookieStore.add(url, item);
            }
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookies = cookieStore.get(url);
//        Logger.json(new Gson().toJson(cookies));
        return cookies;
    }

    public void clearCookie() {
        cookieStore.removeAll();
    }
}
