package com.cb.qiangqiang2.common.constant;

/**
 * Created by cb on 2016/8/30.
 */
public class Constants {
    //system
    public static final String IS_NIGHT_THEME = "isNightTheme";
    public static final String IS_AUTO_SIGN = "isAutoSign";
    public static final int DEFAULT_INVALIDE_TIME = -1;
    public static final String BOARD_LIST = "boardList";
    public static final String BOARD_LIST_SELECTED = "boardListSelected";
    public static final String BOARD_LIST_UNSELETED = "boardListUnselected";
    public static final String PREF_NET_DATA = "qq_net_cache";
    public static final int INVALIDE_UID = -1;

    //网络请求传参
    public static final String POST_ALL = "all";
    public static final String POST_NEW = "new";
    public static final String POST_MARROW = "marrow";
    public static final String USER_POST_FAVORITE = "favorite";
    public static final String USER_POST_TOPIC = "topic";
    public static final String USER_POST_REPLY = "reply";
    public static final String USER_LIST_FOLLOWED = "followed";
    public static final String USER_LIST_FOLLOW = "follow";
    public static final String USER_LIST_FRIEND = "friend";
    public static final String USER_LIST_DATELINE= "dateline";
    public static final String POST_ACTION_FAVORITE = "favorite";
    public static final String POST_ACTION_DELFAVORITE = "delfavorite";
    public static final String POST_ID_TYPE_TID = "tid";
    public static final String POST_TYPE_UNFOLLOW= "unfollow";
    public static final String POST_TYPE_FOLLOW= "follow";

    //api constant
    public static final String FORUM_KEY = "forumKey";
    public static final String ACCESS_TOKEN = "accessToken";
    public static final String ACCESS_SECRET = "accessSecret";
    public static final String APP_HASH = "apphash";
    private static final String PACKAGE_NAME = "packageName";
    private static final String APP_NAME = "appName";
    public static final String LONGITUDE = "longitude";
    public static final String LATITUDE = "latitude";
    public static final String FORUM_TYPE = "forumType";
    public static final String IMEI = "imei";

    //Topic
    public static final String DEFAULT_PAGE_SIZE = "20";
    public static final String PAGE_SIZE = "pageSize";
    public static final String TYPE = "type";
    public static final String PAGE = "page";
    public static final String SORT_BY = "sortby";

    //user info
    public static final String UID = "uid";

    //签到
    public static final String FORMHASH = "formhash";
    public static final String QDXQ = "qdxq";
    public static final String QD_MODE = "qdmode";
    public static final String TODAY_SAY = "todaysay";
    public static final String FAST_REPLY = "fastreply";
    public static final String SIGN_H5 = "http://www.qiangqiang5.com/plugin.php?id=dsu_paulsign:sign";

    //登录
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    //用户信息
    public static final String USER_ID = "userId";
    public static final String ORDER_BY = "orderBy";

    //收藏
    public static final String ACTION = "action";
    public static final String ID_TYPE = "idType";
    public static final String ID = "id";
    public static final String ORDER = "order";
    public static final String ORDER_ASCENDING = "0";
    public static final String ORDER_DESCENDING = "1";

    //搜索
    public static final String KEYWORD = "keyword";

    //帖子详情
    public static final String TOPIC_ID = "topicId";
    public static final String AUTHOR_ID = "authorId";
    public static final String BOARD_ID = "boardId";
    public static final int COLLECTED_POST = 1;//已收藏帖子
    public static final String WEB_PAGE_URL_PRE = "http://www.qiangqiang5.com/forum.php?mod=viewthread&tid=%d&mobile=2";

    //回复帖子
    public static final String JSON = "json";
    public static final String ACT = "act";

    //发表帖子
    public static final String PUBLISH_POST = "http://www.qiangqiang5.com/forum.php?mod=post&action=newthread&fid=%d";
}
