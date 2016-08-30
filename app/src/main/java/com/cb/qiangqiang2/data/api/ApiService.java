package com.cb.qiangqiang2.data.api;

import com.cb.qiangqiang2.data.model.LoginResult;
import com.cb.qiangqiang2.data.model.TopicListResult;

import java.util.Map;

import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by cb on 2016/8/29.
 */
public interface ApiService {
    String IP = "http://www.qiangqiang5.com/";

    String BASE_URL = new StringBuilder(IP).append("mobcent/app/web/").toString();

    @POST("index.php?r=user/login")
    Observable<LoginResult> login(@QueryMap Map<String, String> options);

    @POST("index.php?r=forum/topiclist")
    Observable<TopicListResult> getTopicList(@QueryMap Map<String, String> options);

}
