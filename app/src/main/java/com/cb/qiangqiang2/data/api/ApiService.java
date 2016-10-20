package com.cb.qiangqiang2.data.api;

import com.cb.qiangqiang2.data.model.BoardModel;
import com.cb.qiangqiang2.data.model.LoginModel;
import com.cb.qiangqiang2.data.model.PostModel;

import java.util.Map;

import retrofit2.Response;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by cb on 2016/8/29.
 */
public interface ApiService {
    String IP = "http://www.qiangqiang5.com/";

    String BASE_URL = new StringBuilder(IP).append("mobcent/app/web/").toString();

    public static final String UID = "uid";

    @POST("index.php?r=user/login")
    Observable<LoginModel> login(@QueryMap Map<String, String> options);

    @POST("index.php?r=forum/topiclist")
    Observable<PostModel> getTopicList(@QueryMap Map<String, String> options);

    @POST("index.php?r=forum/forumlist")
    Observable<BoardModel> getBoardList(@QueryMap Map<String, String> options);

    @POST("index.php?r=user/topiclist")
    Observable<PostModel> getCollectionList(@QueryMap Map<String, String> options, @Query(UID) int uid);

    @POST("index.php?r=user/topiclist")
    Observable<Response<PostModel>> getCollectionList2(@QueryMap Map<String, String> options, @Query(UID) int uid);

}
