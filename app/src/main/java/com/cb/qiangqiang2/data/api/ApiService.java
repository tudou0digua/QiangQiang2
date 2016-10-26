package com.cb.qiangqiang2.data.api;

import com.cb.qiangqiang2.data.model.BoardModel;
import com.cb.qiangqiang2.data.model.LoginModel;
import com.cb.qiangqiang2.data.model.PostModel;
import com.cb.qiangqiang2.data.model.UserInfoModel;
import com.cb.qiangqiang2.data.model.UserListModel;

import java.util.Map;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by cb on 2016/8/29.
 */
public interface ApiService {
    String IP = "http://www.qiangqiang5.com/";

    String BASE_URL = new StringBuilder(IP).toString();

    String URL_BASE = "mobcent/app/web/index.php?";

    //帖子列表
    @POST(URL_BASE + "r=forum/topiclist")
    Observable<PostModel> getTopicList(@QueryMap Map<String, String> options);

    //板块列表
    @POST(URL_BASE + "r=forum/forumlist")
    Observable<BoardModel> getBoardList(@QueryMap Map<String, String> options);

    //登录
    @POST(URL_BASE + "r=user/login")
    Observable<LoginModel> login(@QueryMap Map<String, String> options);

    //用户信息
    @POST(URL_BASE + "r=user/userinfo")
    Observable<UserInfoModel> getUserInfo(@QueryMap Map<String, String> options);

    //用户帖子列表（收藏、发表、参与等）
    @POST(URL_BASE + "r=user/topiclist")
    Observable<PostModel> getUserTopicList(@QueryMap Map<String, String> options);

    //用户列表
    @POST(URL_BASE + "r=user/userlist")
    Observable<UserListModel> getUserList(@QueryMap Map<String, String> options);

    //签到网页
    @GET("plugin.php?id=dsu_paulsign:sign")
    Observable<Response<okhttp3.ResponseBody>> getCheckInPage(@QueryMap Map<String, String> options);

    //签到提交
    @POST("plugin.php?id=dsu_paulsign:sign&operation=qiandao&infloat=0&inajax=0&mobile=yes")
    Observable<Response<okhttp3.ResponseBody>> checkIn(@QueryMap Map<String, String> options);
}
