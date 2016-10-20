package com.cb.qiangqiang2.data.model;

import java.util.List;

/**
 * 帖子列表数据
 * Created by cb on 2016/10/19.
 */

public class PostModel extends BaseModel{
    /**
     * {
     "rs": 1,
     "errcode": "",
     "head": {
     "errCode": "00000000",
     "errInfo": "调用成功,没有任何错误",
     "version": "2.6.1.0",
     "alert": 0
     },
     "body": {
     "externInfo": {
     "padding": ""
     }
     },
     "newTopicPanel": [{
     "type": "normal",
     "action": "",
     "title": "发表帖子"
     }, {
     "type": "vote",
     "action": "",
     "title": "发起投票"
     }],
     "classificationTop_list": [],
     "classificationType_list": [{
     "classificationType_id": 250,
     "classificationType_name": "线报"
     }, {
     "classificationType_id": 251,
     "classificationType_name": "聊聊"
     }, {
     "classificationType_id": 252,
     "classificationType_name": "求助"
     }, {
     "classificationType_id": 339,
     "classificationType_name": "双11活动"
     }],
     "isOnlyTopicType": 1,
     "anno_list": [],
     "forumInfo": {
     "id": 95,
     "title": "抢友交流",
     "description": "有啥抱怨的、线报、求助，都发这里啦，抢友大讨论区域",
     "icon": "http:\/\/www.qiangqiang5.com\/data\/attachment\/common\/81\/common_95_icon.gif",
     "td_posts_num": "4937",
     "topic_total_num": "81406",
     "posts_total_num": "2602697",
     "is_focus": 1
     },
     "topTopicList": [{
     "id": 117534,
     "title": "抢抢双11期间，对于线报的抢友奖励方案！请留意"
     }, {
     "id": 66646,
     "title": "关于拉人类帖子删除和违规判定处罚的规定"
     }],
     "list": [{
     "board_id": 95,
     "board_name": "抢友交流",
     "topic_id": 121759,
     "type": "normal",
     "title": "【飞牛网】周年庆狂欢，送您50元抵用券",
     "user_id": 78244,
     "user_nick_name": "苏州2809",
     "userAvatar": "http:\/\/www.qiangqiang5.com\/uc_server\/avatar.php?uid=78244&size=middle",
     "last_reply_date": "1476877125000",
     "vote": 0,
     "hot": 0,
     "hits": 1192,
     "replies": 26,
     "essence": 0,
     "top": 0,
     "status": 0,
     "subject": "【飞牛网】周年庆狂欢，送您50元抵用券，10\/16前点击 dwz.cn\/sms_cz ，登录后输入券码XXXXXXXXXXXX充值！ 回TD退订",
     "pic_path": "",
     "ratio": "1",
     "gender": 0,
     "userTitle": "六级抢友",
     "recommendAdd": 0,
     "special": 0,
     "isHasRecommendAdd": 0,
     "imageList": [],
     "sourceWebUrl": "http:\/\/www.qiangqiang5.com\/mobcent\/app\/web\/index.php?r=webapp\/share&tid=121759&forumKey=FuXlu6ShCTYC2q8Ysn",
     "verify": []
     }],
     "page": 1,
     "has_next": 1,
     "total_num": 37978
     }
     */

    /**
     * externInfo : {"padding":""}
     */

    private BodyBean body;
    private int isOnlyTopicType;
    /**
     * id : 95
     * title : 抢友交流
     * description : 有啥抱怨的、线报、求助，都发这里啦，抢友大讨论区域
     * icon : http://www.qiangqiang5.com/data/attachment/common/81/common_95_icon.gif
     * td_posts_num : 4937
     * topic_total_num : 81406
     * posts_total_num : 2602697
     * is_focus : 1
     */

    private ForumInfoBean forumInfo;
    private int page;
    private int has_next;
    private int total_num;
    /**
     * type : normal
     * action :
     * title : 发表帖子
     */

    private List<NewTopicPanelBean> newTopicPanel;
    private List<?> classificationTop_list;
    /**
     * classificationType_id : 250
     * classificationType_name : 线报
     */

    private List<ClassificationTypeListBean> classificationType_list;
    private List<?> anno_list;
    /**
     * id : 117534
     * title : 抢抢双11期间，对于线报的抢友奖励方案！请留意
     */

    private List<TopTopicListBean> topTopicList;
    /**
     * board_id : 95
     * board_name : 抢友交流
     * topic_id : 121759
     * type : normal
     * title : 【飞牛网】周年庆狂欢，送您50元抵用券
     * user_id : 78244
     * user_nick_name : 苏州2809
     * userAvatar : http://www.qiangqiang5.com/uc_server/avatar.php?uid=78244&size=middle
     * last_reply_date : 1476877125000
     * vote : 0
     * hot : 0
     * hits : 1192
     * replies : 26
     * essence : 0
     * top : 0
     * status : 0
     * subject : 【飞牛网】周年庆狂欢，送您50元抵用券，10/16前点击 dwz.cn/sms_cz ，登录后输入券码XXXXXXXXXXXX充值！ 回TD退订
     * pic_path :
     * ratio : 1
     * gender : 0
     * userTitle : 六级抢友
     * recommendAdd : 0
     * special : 0
     * isHasRecommendAdd : 0
     * imageList : []
     * sourceWebUrl : http://www.qiangqiang5.com/mobcent/app/web/index.php?r=webapp/share&tid=121759&forumKey=FuXlu6ShCTYC2q8Ysn
     * verify : []
     */

    private List<ListBean> list;

    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }

    public int getIsOnlyTopicType() {
        return isOnlyTopicType;
    }

    public void setIsOnlyTopicType(int isOnlyTopicType) {
        this.isOnlyTopicType = isOnlyTopicType;
    }

    public ForumInfoBean getForumInfo() {
        return forumInfo;
    }

    public void setForumInfo(ForumInfoBean forumInfo) {
        this.forumInfo = forumInfo;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getHas_next() {
        return has_next;
    }

    public void setHas_next(int has_next) {
        this.has_next = has_next;
    }

    public int getTotal_num() {
        return total_num;
    }

    public void setTotal_num(int total_num) {
        this.total_num = total_num;
    }

    public List<NewTopicPanelBean> getNewTopicPanel() {
        return newTopicPanel;
    }

    public void setNewTopicPanel(List<NewTopicPanelBean> newTopicPanel) {
        this.newTopicPanel = newTopicPanel;
    }

    public List<?> getClassificationTop_list() {
        return classificationTop_list;
    }

    public void setClassificationTop_list(List<?> classificationTop_list) {
        this.classificationTop_list = classificationTop_list;
    }

    public List<ClassificationTypeListBean> getClassificationType_list() {
        return classificationType_list;
    }

    public void setClassificationType_list(List<ClassificationTypeListBean> classificationType_list) {
        this.classificationType_list = classificationType_list;
    }

    public List<?> getAnno_list() {
        return anno_list;
    }

    public void setAnno_list(List<?> anno_list) {
        this.anno_list = anno_list;
    }

    public List<TopTopicListBean> getTopTopicList() {
        return topTopicList;
    }

    public void setTopTopicList(List<TopTopicListBean> topTopicList) {
        this.topTopicList = topTopicList;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class BodyBean {
        /**
         * padding :
         */

        private ExternInfoBean externInfo;

        public ExternInfoBean getExternInfo() {
            return externInfo;
        }

        public void setExternInfo(ExternInfoBean externInfo) {
            this.externInfo = externInfo;
        }

        public static class ExternInfoBean {
            private String padding;

            public String getPadding() {
                return padding;
            }

            public void setPadding(String padding) {
                this.padding = padding;
            }
        }
    }

    public static class ForumInfoBean {
        private int id;
        private String title;
        private String description;
        private String icon;
        private String td_posts_num;
        private String topic_total_num;
        private String posts_total_num;
        private int is_focus;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getTd_posts_num() {
            return td_posts_num;
        }

        public void setTd_posts_num(String td_posts_num) {
            this.td_posts_num = td_posts_num;
        }

        public String getTopic_total_num() {
            return topic_total_num;
        }

        public void setTopic_total_num(String topic_total_num) {
            this.topic_total_num = topic_total_num;
        }

        public String getPosts_total_num() {
            return posts_total_num;
        }

        public void setPosts_total_num(String posts_total_num) {
            this.posts_total_num = posts_total_num;
        }

        public int getIs_focus() {
            return is_focus;
        }

        public void setIs_focus(int is_focus) {
            this.is_focus = is_focus;
        }
    }

    public static class NewTopicPanelBean {
        private String type;
        private String action;
        private String title;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class ClassificationTypeListBean {
        private int classificationType_id;
        private String classificationType_name;

        public int getClassificationType_id() {
            return classificationType_id;
        }

        public void setClassificationType_id(int classificationType_id) {
            this.classificationType_id = classificationType_id;
        }

        public String getClassificationType_name() {
            return classificationType_name;
        }

        public void setClassificationType_name(String classificationType_name) {
            this.classificationType_name = classificationType_name;
        }
    }

    public static class TopTopicListBean {
        private int id;
        private String title;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class ListBean {
        private int board_id;
        private String board_name;
        private int topic_id;
        private String type;
        private String title;
        private int user_id;
        private String user_nick_name;
        private String userAvatar;
        private long last_reply_date;
        private int vote;
        private int hot;
        private int hits;
        private int replies;
        private int essence;
        private int top;
        private int status;
        private String subject;
        private String pic_path;
        private String ratio;
        private int gender;
        private String userTitle;
        private int recommendAdd;
        private int special;
        private int isHasRecommendAdd;
        private String sourceWebUrl;
        private List<?> imageList;
        private List<?> verify;

        public int getBoard_id() {
            return board_id;
        }

        public void setBoard_id(int board_id) {
            this.board_id = board_id;
        }

        public String getBoard_name() {
            return board_name;
        }

        public void setBoard_name(String board_name) {
            this.board_name = board_name;
        }

        public int getTopic_id() {
            return topic_id;
        }

        public void setTopic_id(int topic_id) {
            this.topic_id = topic_id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getUser_nick_name() {
            return user_nick_name;
        }

        public void setUser_nick_name(String user_nick_name) {
            this.user_nick_name = user_nick_name;
        }

        public String getUserAvatar() {
            return userAvatar;
        }

        public void setUserAvatar(String userAvatar) {
            this.userAvatar = userAvatar;
        }

        public long getLast_reply_date() {
            return last_reply_date;
        }

        public void setLast_reply_date(long last_reply_date) {
            this.last_reply_date = last_reply_date;
        }

        public int getVote() {
            return vote;
        }

        public void setVote(int vote) {
            this.vote = vote;
        }

        public int getHot() {
            return hot;
        }

        public void setHot(int hot) {
            this.hot = hot;
        }

        public int getHits() {
            return hits;
        }

        public void setHits(int hits) {
            this.hits = hits;
        }

        public int getReplies() {
            return replies;
        }

        public void setReplies(int replies) {
            this.replies = replies;
        }

        public int getEssence() {
            return essence;
        }

        public void setEssence(int essence) {
            this.essence = essence;
        }

        public int getTop() {
            return top;
        }

        public void setTop(int top) {
            this.top = top;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getPic_path() {
            return pic_path;
        }

        public void setPic_path(String pic_path) {
            this.pic_path = pic_path;
        }

        public String getRatio() {
            return ratio;
        }

        public void setRatio(String ratio) {
            this.ratio = ratio;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getUserTitle() {
            return userTitle;
        }

        public void setUserTitle(String userTitle) {
            this.userTitle = userTitle;
        }

        public int getRecommendAdd() {
            return recommendAdd;
        }

        public void setRecommendAdd(int recommendAdd) {
            this.recommendAdd = recommendAdd;
        }

        public int getSpecial() {
            return special;
        }

        public void setSpecial(int special) {
            this.special = special;
        }

        public int getIsHasRecommendAdd() {
            return isHasRecommendAdd;
        }

        public void setIsHasRecommendAdd(int isHasRecommendAdd) {
            this.isHasRecommendAdd = isHasRecommendAdd;
        }

        public String getSourceWebUrl() {
            return sourceWebUrl;
        }

        public void setSourceWebUrl(String sourceWebUrl) {
            this.sourceWebUrl = sourceWebUrl;
        }

        public List<?> getImageList() {
            return imageList;
        }

        public void setImageList(List<?> imageList) {
            this.imageList = imageList;
        }

        public List<?> getVerify() {
            return verify;
        }

        public void setVerify(List<?> verify) {
            this.verify = verify;
        }
    }
}
