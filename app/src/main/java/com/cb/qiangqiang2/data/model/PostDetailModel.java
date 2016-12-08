package com.cb.qiangqiang2.data.model;

import java.util.List;

/**
 * Created by cb on 2016/12/8.
 */

public class PostDetailModel extends BaseModel {

    /**
     * externInfo : {"padding":""}
     */

    private BodyBean body;
    /**
     * topic_id : 81077
     * title : [线报]【天猫★苏宁店】【大米】条形码（2楼非米码）
     * type : normal_complex
     * sortId : 0
     * user_id : 71413
     * user_nick_name : meloaoi
     * replies : 65
     * hits : 6091
     * essence : 1
     * vote : 0
     * hot : 0
     * top : 0
     * is_favor : 0
     * create_date : 1457586262000
     * icon : http://www.qiangqiang5.com/uc_server/avatar.php?uid=71413&size=middle
     * level : 6
     * userTitle : 六级抢友
     * isFollow : 0
     * zanList : [{"tid":"81077","recommenduid":"51275","dateline":"1457586969","username":"布小熊"}]
     * content : [{"infor":"http://www.qiangqiang5.com/data/appbyme/thumb/14/5/0/xgsize_314ce3f5478175fae2563fee36e8c352.jpg","type":1,"originalInfo":"http://ww2.sinaimg.cn/large/005L3YWtgw1f4tlbmkqmvj30jq0lj4bk.jpg","aid":0,"url":"http://ww4.sinaimg.cn/large/005L3YWtgw1f46fsln0e2j30jq0lfdky.jpg"}]
     * poll_info : null
     * activityInfo : null
     * location :
     * delThread : false
     * managePanel : []
     * extraPanel : [{"action":"http://www.qiangqiang5.com/mobcent/app/web/index.php?r=forum/topicrate&sdkVersion=2.6.0.1&accessToken=b459663e3080de87270c849fbdb74&accessSecret=99c732f3e3887983103a6ceb1a07a&apphash=276399a6&tid=81077&pid=1250066&type=view","title":"评分","extParams":{"beforeAction":"http://www.qiangqiang5.com/mobcent/app/web/index.php?r=forum/topicrate&sdkVersion=2.6.0.1&accessToken=b459663e3080de87270c849fbdb74&accessSecret=99c732f3e3887983103a6ceb1a07a&apphash=276399a6&tid=81077&pid=1250066&type=check"},"type":"rate"},{"action":"http://www.qiangqiang5.com/mobcent/app/web/index.php?r=forum/support&sdkVersion=2.6.0.1&accessToken=b459663e3080de87270c849fbdb74&accessSecret=99c732f3e3887983103a6ceb1a07a&apphash=276399a6&tid=81077&pid=1250066&type=thread","title":"赞一下","extParams":{"beforeAction":"","recommendAdd":6,"isHasRecommendAdd":0},"type":"support"}]
     * mobileSign :
     * status : 1
     * reply_status : 1
     * flag : 0
     * gender : 1
     * reply_posts_id : 1250066
     * rateList : {"head":{"field1":"参与人数","field2":"银币","field3":"金币"},"total":{"field1":"14","field2":"20","field3":"1"},"body":[{"field1":"亭亭","field2":"+2","field3":""}],"showAllUrl":"http://www.qiangqiang5.com/mobcent/app/web/index.php?r=forum/ratelistview&sdkVersion=2.6.0.1&accessToken=b459663e3080de87270c849fbdb74&accessSecret=99c732f3e3887983103a6ceb1a07a&apphash=276399a6&tid=&pid=1250066"}
     * reward : {"score":[{"info":"银币","value":20},{"info":"金币","value":1}],"userList":[{"uid":54887,"userName":"亭亭","userIcon":"http://www.qiangqiang5.com/uc_server/avatar.php?uid=54887&size=middle"}],"userNumber":14,"showAllUrl":"http://www.qiangqiang5.com/mobcent/app/web/index.php?r=forum/ratelistview&sdkVersion=2.6.0.1&accessToken=b459663e3080de87270c849fbdb74&accessSecret=99c732f3e3887983103a6ceb1a07a&apphash=276399a6&tid=&pid=1250066"}
     * relateItem : []
     */

    private TopicBean topic;
    /**
     * body : {"externInfo":{"padding":""}}
     * topic : {"topic_id":81077,"title":"[线报]【天猫★苏宁店】【大米】条形码（2楼非米码）","type":"normal_complex","sortId":0,"user_id":71413,"user_nick_name":"meloaoi","replies":65,"hits":6091,"essence":1,"vote":0,"hot":0,"top":0,"is_favor":0,"create_date":"1457586262000","icon":"http://www.qiangqiang5.com/uc_server/avatar.php?uid=71413&size=middle","level":6,"userTitle":"六级抢友","isFollow":0,"zanList":[{"tid":"81077","recommenduid":"51275","dateline":"1457586969","username":"布小熊"}],"content":[{"infor":"http://www.qiangqiang5.com/data/appbyme/thumb/14/5/0/xgsize_314ce3f5478175fae2563fee36e8c352.jpg","type":1,"originalInfo":"http://ww2.sinaimg.cn/large/005L3YWtgw1f4tlbmkqmvj30jq0lj4bk.jpg","aid":0,"url":"http://ww4.sinaimg.cn/large/005L3YWtgw1f46fsln0e2j30jq0lfdky.jpg"}],"poll_info":null,"activityInfo":null,"location":"","delThread":false,"managePanel":[],"extraPanel":[{"action":"http://www.qiangqiang5.com/mobcent/app/web/index.php?r=forum/topicrate&sdkVersion=2.6.0.1&accessToken=b459663e3080de87270c849fbdb74&accessSecret=99c732f3e3887983103a6ceb1a07a&apphash=276399a6&tid=81077&pid=1250066&type=view","title":"评分","extParams":{"beforeAction":"http://www.qiangqiang5.com/mobcent/app/web/index.php?r=forum/topicrate&sdkVersion=2.6.0.1&accessToken=b459663e3080de87270c849fbdb74&accessSecret=99c732f3e3887983103a6ceb1a07a&apphash=276399a6&tid=81077&pid=1250066&type=check"},"type":"rate"},{"action":"http://www.qiangqiang5.com/mobcent/app/web/index.php?r=forum/support&sdkVersion=2.6.0.1&accessToken=b459663e3080de87270c849fbdb74&accessSecret=99c732f3e3887983103a6ceb1a07a&apphash=276399a6&tid=81077&pid=1250066&type=thread","title":"赞一下","extParams":{"beforeAction":"","recommendAdd":6,"isHasRecommendAdd":0},"type":"support"}],"mobileSign":"","status":1,"reply_status":1,"flag":0,"gender":1,"reply_posts_id":1250066,"rateList":{"head":{"field1":"参与人数","field2":"银币","field3":"金币"},"total":{"field1":"14","field2":"20","field3":"1"},"body":[{"field1":"亭亭","field2":"+2","field3":""}],"showAllUrl":"http://www.qiangqiang5.com/mobcent/app/web/index.php?r=forum/ratelistview&sdkVersion=2.6.0.1&accessToken=b459663e3080de87270c849fbdb74&accessSecret=99c732f3e3887983103a6ceb1a07a&apphash=276399a6&tid=&pid=1250066"},"reward":{"score":[{"info":"银币","value":20},{"info":"金币","value":1}],"userList":[{"uid":54887,"userName":"亭亭","userIcon":"http://www.qiangqiang5.com/uc_server/avatar.php?uid=54887&size=middle"}],"userNumber":14,"showAllUrl":"http://www.qiangqiang5.com/mobcent/app/web/index.php?r=forum/ratelistview&sdkVersion=2.6.0.1&accessToken=b459663e3080de87270c849fbdb74&accessSecret=99c732f3e3887983103a6ceb1a07a&apphash=276399a6&tid=&pid=1250066"},"relateItem":[]}
     * page : 1
     * has_next : 1
     * total_num : 65
     * list : [{"reply_id":70726,"reply_content":[{"infor":"干啥呢[mobcent_phiz=http://www.qiangqiang5.com/static/image/smiley/ppbbq/14.gif] 昨天不放，钱都花光了[mobcent_phiz=http://www.qiangqiang5.com/static/image/smiley/yctbq/1.gif]","type":0,"url":"https://ju.taobao.com/search.htm?spm=608.6895169.123.5.d71fCi&words=%CB%D5%C4%FE&stype=activityPrice&reverse=up&ali_trackid=2:mm_34134526_11842117_41732117:1453044666_253_803646494","originalInfo":"http://ww1.sinaimg.cn/large/005L3YWtgw1f209o2ve25j307a04jt8i.jpg","aid":0}],"reply_type":"normal","reply_name":"枪林弹雨","reply_posts_id":1250087,"position":3,"posts_date":"1457586785000","icon":"http://www.qiangqiang5.com/uc_server/avatar.php?uid=70726&size=middle","level":5,"userTitle":"五级抢友","location":"","mobileSign":"","reply_status":1,"status":1,"role_num":1,"title":"","is_quote":0,"quote_pid":0,"quote_content":"","quote_user_name":"","delThread":false,"managePanel":[],"extraPanel":[{"action":"http://www.qiangqiang5.com/mobcent/app/web/index.php?r=forum/support&sdkVersion=2.6.0.1&accessToken=b459663e3080de87270c849fbdb74&accessSecret=99c732f3e3887983103a6ceb1a07a&apphash=276399a6&tid=81077&pid=1250087&type=post","title":"支持","recommendAdd":"","extParams":{"beforeAction":"","recommendAdd":0,"isHasRecommendAdd":0},"type":"support"}]}]
     * forumName : 抢友交流
     * boardId : 95
     * forumTopicUrl : http://www.qiangqiang5.com/mobcent/app/web/index.php?r=webapp/share&tid=81077&forumKey=FuXlu6ShCTYC2q8Ysn
     * img_url :
     * icon_url :
     */

    private int page;
    private int has_next;
    private int total_num;
    private String forumName;
    private int boardId;
    private String forumTopicUrl;
    private String img_url;
    private String icon_url;
    /**
     * reply_id : 70726
     * reply_content : [{"infor":"干啥呢[mobcent_phiz=http://www.qiangqiang5.com/static/image/smiley/ppbbq/14.gif] 昨天不放，钱都花光了[mobcent_phiz=http://www.qiangqiang5.com/static/image/smiley/yctbq/1.gif]","type":0,"url":"https://ju.taobao.com/search.htm?spm=608.6895169.123.5.d71fCi&words=%CB%D5%C4%FE&stype=activityPrice&reverse=up&ali_trackid=2:mm_34134526_11842117_41732117:1453044666_253_803646494","originalInfo":"http://ww1.sinaimg.cn/large/005L3YWtgw1f209o2ve25j307a04jt8i.jpg","aid":0}]
     * reply_type : normal
     * reply_name : 枪林弹雨
     * reply_posts_id : 1250087
     * position : 3
     * posts_date : 1457586785000
     * icon : http://www.qiangqiang5.com/uc_server/avatar.php?uid=70726&size=middle
     * level : 5
     * userTitle : 五级抢友
     * location :
     * mobileSign :
     * reply_status : 1
     * status : 1
     * role_num : 1
     * title :
     * is_quote : 0
     * quote_pid : 0
     * quote_content :
     * quote_user_name :
     * delThread : false
     * managePanel : []
     * extraPanel : [{"action":"http://www.qiangqiang5.com/mobcent/app/web/index.php?r=forum/support&sdkVersion=2.6.0.1&accessToken=b459663e3080de87270c849fbdb74&accessSecret=99c732f3e3887983103a6ceb1a07a&apphash=276399a6&tid=81077&pid=1250087&type=post","title":"支持","recommendAdd":"","extParams":{"beforeAction":"","recommendAdd":0,"isHasRecommendAdd":0},"type":"support"}]
     */

    private List<ListBean> list;

    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }

    public TopicBean getTopic() {
        return topic;
    }

    public void setTopic(TopicBean topic) {
        this.topic = topic;
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

    public String getForumName() {
        return forumName;
    }

    public void setForumName(String forumName) {
        this.forumName = forumName;
    }

    public int getBoardId() {
        return boardId;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }

    public String getForumTopicUrl() {
        return forumTopicUrl;
    }

    public void setForumTopicUrl(String forumTopicUrl) {
        this.forumTopicUrl = forumTopicUrl;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
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

    public static class TopicBean {
        private int topic_id;
        private String title;
        private String type;
        private int sortId;
        private int user_id;
        private String user_nick_name;
        private int replies;
        private int hits;
        private int essence;
        private int vote;
        private int hot;
        private int top;
        private int is_favor;
        private long create_date;
        private String icon;
        private int level;
        private String userTitle;
        private int isFollow;
        private Object poll_info;
        private Object activityInfo;
        private String location;
        private boolean delThread;
        private String mobileSign;
        private int status;
        private int reply_status;
        private int flag;
        private int gender;
        private long reply_posts_id;
        /**
         * head : {"field1":"参与人数","field2":"银币","field3":"金币"}
         * total : {"field1":"14","field2":"20","field3":"1"}
         * body : [{"field1":"亭亭","field2":"+2","field3":""}]
         * showAllUrl : http://www.qiangqiang5.com/mobcent/app/web/index.php?r=forum/ratelistview&sdkVersion=2.6.0.1&accessToken=b459663e3080de87270c849fbdb74&accessSecret=99c732f3e3887983103a6ceb1a07a&apphash=276399a6&tid=&pid=1250066
         */

        private RateListBean rateList;
        /**
         * score : [{"info":"银币","value":20},{"info":"金币","value":1}]
         * userList : [{"uid":54887,"userName":"亭亭","userIcon":"http://www.qiangqiang5.com/uc_server/avatar.php?uid=54887&size=middle"}]
         * userNumber : 14
         * showAllUrl : http://www.qiangqiang5.com/mobcent/app/web/index.php?r=forum/ratelistview&sdkVersion=2.6.0.1&accessToken=b459663e3080de87270c849fbdb74&accessSecret=99c732f3e3887983103a6ceb1a07a&apphash=276399a6&tid=&pid=1250066
         */

        private RewardBean reward;
        /**
         * tid : 81077
         * recommenduid : 51275
         * dateline : 1457586969
         * username : 布小熊
         */

        private List<ZanListBean> zanList;
        /**
         * infor : http://www.qiangqiang5.com/data/appbyme/thumb/14/5/0/xgsize_314ce3f5478175fae2563fee36e8c352.jpg
         * type : 1
         * originalInfo : http://ww2.sinaimg.cn/large/005L3YWtgw1f4tlbmkqmvj30jq0lj4bk.jpg
         * aid : 0
         * url : http://ww4.sinaimg.cn/large/005L3YWtgw1f46fsln0e2j30jq0lfdky.jpg
         */

        private List<ContentBean> content;
        private List<?> managePanel;
        /**
         * action : http://www.qiangqiang5.com/mobcent/app/web/index.php?r=forum/topicrate&sdkVersion=2.6.0.1&accessToken=b459663e3080de87270c849fbdb74&accessSecret=99c732f3e3887983103a6ceb1a07a&apphash=276399a6&tid=81077&pid=1250066&type=view
         * title : 评分
         * extParams : {"beforeAction":"http://www.qiangqiang5.com/mobcent/app/web/index.php?r=forum/topicrate&sdkVersion=2.6.0.1&accessToken=b459663e3080de87270c849fbdb74&accessSecret=99c732f3e3887983103a6ceb1a07a&apphash=276399a6&tid=81077&pid=1250066&type=check"}
         * type : rate
         */

        private List<ExtraPanelBean> extraPanel;
        private List<?> relateItem;

        public int getTopic_id() {
            return topic_id;
        }

        public void setTopic_id(int topic_id) {
            this.topic_id = topic_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getSortId() {
            return sortId;
        }

        public void setSortId(int sortId) {
            this.sortId = sortId;
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

        public int getReplies() {
            return replies;
        }

        public void setReplies(int replies) {
            this.replies = replies;
        }

        public int getHits() {
            return hits;
        }

        public void setHits(int hits) {
            this.hits = hits;
        }

        public int getEssence() {
            return essence;
        }

        public void setEssence(int essence) {
            this.essence = essence;
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

        public int getTop() {
            return top;
        }

        public void setTop(int top) {
            this.top = top;
        }

        public int getIs_favor() {
            return is_favor;
        }

        public void setIs_favor(int is_favor) {
            this.is_favor = is_favor;
        }

        public long getCreate_date() {
            return create_date;
        }

        public void setCreate_date(long create_date) {
            this.create_date = create_date;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getUserTitle() {
            return userTitle;
        }

        public void setUserTitle(String userTitle) {
            this.userTitle = userTitle;
        }

        public int getIsFollow() {
            return isFollow;
        }

        public void setIsFollow(int isFollow) {
            this.isFollow = isFollow;
        }

        public Object getPoll_info() {
            return poll_info;
        }

        public void setPoll_info(Object poll_info) {
            this.poll_info = poll_info;
        }

        public Object getActivityInfo() {
            return activityInfo;
        }

        public void setActivityInfo(Object activityInfo) {
            this.activityInfo = activityInfo;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public boolean isDelThread() {
            return delThread;
        }

        public void setDelThread(boolean delThread) {
            this.delThread = delThread;
        }

        public String getMobileSign() {
            return mobileSign;
        }

        public void setMobileSign(String mobileSign) {
            this.mobileSign = mobileSign;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getReply_status() {
            return reply_status;
        }

        public void setReply_status(int reply_status) {
            this.reply_status = reply_status;
        }

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public long getReply_posts_id() {
            return reply_posts_id;
        }

        public void setReply_posts_id(long reply_posts_id) {
            this.reply_posts_id = reply_posts_id;
        }

        public RateListBean getRateList() {
            return rateList;
        }

        public void setRateList(RateListBean rateList) {
            this.rateList = rateList;
        }

        public RewardBean getReward() {
            return reward;
        }

        public void setReward(RewardBean reward) {
            this.reward = reward;
        }

        public List<ZanListBean> getZanList() {
            return zanList;
        }

        public void setZanList(List<ZanListBean> zanList) {
            this.zanList = zanList;
        }

        public List<ContentBean> getContent() {
            return content;
        }

        public void setContent(List<ContentBean> content) {
            this.content = content;
        }

        public List<?> getManagePanel() {
            return managePanel;
        }

        public void setManagePanel(List<?> managePanel) {
            this.managePanel = managePanel;
        }

        public List<ExtraPanelBean> getExtraPanel() {
            return extraPanel;
        }

        public void setExtraPanel(List<ExtraPanelBean> extraPanel) {
            this.extraPanel = extraPanel;
        }

        public List<?> getRelateItem() {
            return relateItem;
        }

        public void setRelateItem(List<?> relateItem) {
            this.relateItem = relateItem;
        }

        public static class RateListBean {
            /**
             * field1 : 参与人数
             * field2 : 银币
             * field3 : 金币
             */

            private HeadBean head;
            /**
             * field1 : 14
             * field2 : 20
             * field3 : 1
             */

            private TotalBean total;
            private String showAllUrl;
            /**
             * field1 : 亭亭
             * field2 : +2
             * field3 :
             */

            private List<BodyBean> body;

            public HeadBean getHead() {
                return head;
            }

            public void setHead(HeadBean head) {
                this.head = head;
            }

            public TotalBean getTotal() {
                return total;
            }

            public void setTotal(TotalBean total) {
                this.total = total;
            }

            public String getShowAllUrl() {
                return showAllUrl;
            }

            public void setShowAllUrl(String showAllUrl) {
                this.showAllUrl = showAllUrl;
            }

            public List<BodyBean> getBody() {
                return body;
            }

            public void setBody(List<BodyBean> body) {
                this.body = body;
            }

            public static class HeadBean {
                private String field1;
                private String field2;
                private String field3;

                public String getField1() {
                    return field1;
                }

                public void setField1(String field1) {
                    this.field1 = field1;
                }

                public String getField2() {
                    return field2;
                }

                public void setField2(String field2) {
                    this.field2 = field2;
                }

                public String getField3() {
                    return field3;
                }

                public void setField3(String field3) {
                    this.field3 = field3;
                }
            }

            public static class TotalBean {
                private String field1;
                private String field2;
                private String field3;

                public String getField1() {
                    return field1;
                }

                public void setField1(String field1) {
                    this.field1 = field1;
                }

                public String getField2() {
                    return field2;
                }

                public void setField2(String field2) {
                    this.field2 = field2;
                }

                public String getField3() {
                    return field3;
                }

                public void setField3(String field3) {
                    this.field3 = field3;
                }
            }

            public static class BodyBean {
                private String field1;
                private String field2;
                private String field3;

                public String getField1() {
                    return field1;
                }

                public void setField1(String field1) {
                    this.field1 = field1;
                }

                public String getField2() {
                    return field2;
                }

                public void setField2(String field2) {
                    this.field2 = field2;
                }

                public String getField3() {
                    return field3;
                }

                public void setField3(String field3) {
                    this.field3 = field3;
                }
            }
        }

        public static class RewardBean {
            private int userNumber;
            private String showAllUrl;
            /**
             * info : 银币
             * value : 20
             */

            private List<ScoreBean> score;
            /**
             * uid : 54887
             * userName : 亭亭
             * userIcon : http://www.qiangqiang5.com/uc_server/avatar.php?uid=54887&size=middle
             */

            private List<UserListBean> userList;

            public int getUserNumber() {
                return userNumber;
            }

            public void setUserNumber(int userNumber) {
                this.userNumber = userNumber;
            }

            public String getShowAllUrl() {
                return showAllUrl;
            }

            public void setShowAllUrl(String showAllUrl) {
                this.showAllUrl = showAllUrl;
            }

            public List<ScoreBean> getScore() {
                return score;
            }

            public void setScore(List<ScoreBean> score) {
                this.score = score;
            }

            public List<UserListBean> getUserList() {
                return userList;
            }

            public void setUserList(List<UserListBean> userList) {
                this.userList = userList;
            }

            public static class ScoreBean {
                private String info;
                private int value;

                public String getInfo() {
                    return info;
                }

                public void setInfo(String info) {
                    this.info = info;
                }

                public int getValue() {
                    return value;
                }

                public void setValue(int value) {
                    this.value = value;
                }
            }

            public static class UserListBean {
                private int uid;
                private String userName;
                private String userIcon;

                public int getUid() {
                    return uid;
                }

                public void setUid(int uid) {
                    this.uid = uid;
                }

                public String getUserName() {
                    return userName;
                }

                public void setUserName(String userName) {
                    this.userName = userName;
                }

                public String getUserIcon() {
                    return userIcon;
                }

                public void setUserIcon(String userIcon) {
                    this.userIcon = userIcon;
                }
            }
        }

        public static class ZanListBean {
            private String tid;
            private String recommenduid;
            private long dateline;
            private String username;

            public String getTid() {
                return tid;
            }

            public void setTid(String tid) {
                this.tid = tid;
            }

            public String getRecommenduid() {
                return recommenduid;
            }

            public void setRecommenduid(String recommenduid) {
                this.recommenduid = recommenduid;
            }

            public long getDateline() {
                return dateline;
            }

            public void setDateline(long dateline) {
                this.dateline = dateline;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }
        }

        public static class ContentBean {
            private String infor;
            private int type;
            private String originalInfo;
            private int aid;
            private String url;

            public String getInfor() {
                return infor;
            }

            public void setInfor(String infor) {
                this.infor = infor;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getOriginalInfo() {
                return originalInfo;
            }

            public void setOriginalInfo(String originalInfo) {
                this.originalInfo = originalInfo;
            }

            public int getAid() {
                return aid;
            }

            public void setAid(int aid) {
                this.aid = aid;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class ExtraPanelBean {
            private String action;
            private String title;
            /**
             * beforeAction : http://www.qiangqiang5.com/mobcent/app/web/index.php?r=forum/topicrate&sdkVersion=2.6.0.1&accessToken=b459663e3080de87270c849fbdb74&accessSecret=99c732f3e3887983103a6ceb1a07a&apphash=276399a6&tid=81077&pid=1250066&type=check
             */

            private ExtParamsBean extParams;
            private String type;

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

            public ExtParamsBean getExtParams() {
                return extParams;
            }

            public void setExtParams(ExtParamsBean extParams) {
                this.extParams = extParams;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public static class ExtParamsBean {
                private String beforeAction;

                public String getBeforeAction() {
                    return beforeAction;
                }

                public void setBeforeAction(String beforeAction) {
                    this.beforeAction = beforeAction;
                }
            }
        }
    }

    public static class ListBean {
        private int reply_id;
        private String reply_type;
        private String reply_name;
        private long reply_posts_id;
        private int position;
        private long posts_date;
        private String icon;
        private int level;
        private String userTitle;
        private String location;
        private String mobileSign;
        private int reply_status;
        private int status;
        private int role_num;
        private String title;
        private int is_quote;
        private int quote_pid;
        private String quote_content;
        private String quote_user_name;
        private boolean delThread;
        /**
         * infor : 干啥呢[mobcent_phiz=http://www.qiangqiang5.com/static/image/smiley/ppbbq/14.gif] 昨天不放，钱都花光了[mobcent_phiz=http://www.qiangqiang5.com/static/image/smiley/yctbq/1.gif]
         * type : 0
         * url : https://ju.taobao.com/search.htm?spm=608.6895169.123.5.d71fCi&words=%CB%D5%C4%FE&stype=activityPrice&reverse=up&ali_trackid=2:mm_34134526_11842117_41732117:1453044666_253_803646494
         * originalInfo : http://ww1.sinaimg.cn/large/005L3YWtgw1f209o2ve25j307a04jt8i.jpg
         * aid : 0
         */

        private List<TopicBean.ContentBean> reply_content;
        private List<?> managePanel;
        /**
         * action : http://www.qiangqiang5.com/mobcent/app/web/index.php?r=forum/support&sdkVersion=2.6.0.1&accessToken=b459663e3080de87270c849fbdb74&accessSecret=99c732f3e3887983103a6ceb1a07a&apphash=276399a6&tid=81077&pid=1250087&type=post
         * title : 支持
         * recommendAdd :
         * extParams : {"beforeAction":"","recommendAdd":0,"isHasRecommendAdd":0}
         * type : support
         */

        private List<ExtraPanelBean> extraPanel;

        public int getReply_id() {
            return reply_id;
        }

        public void setReply_id(int reply_id) {
            this.reply_id = reply_id;
        }

        public String getReply_type() {
            return reply_type;
        }

        public void setReply_type(String reply_type) {
            this.reply_type = reply_type;
        }

        public String getReply_name() {
            return reply_name;
        }

        public void setReply_name(String reply_name) {
            this.reply_name = reply_name;
        }

        public long getReply_posts_id() {
            return reply_posts_id;
        }

        public void setReply_posts_id(long reply_posts_id) {
            this.reply_posts_id = reply_posts_id;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public long getPosts_date() {
            return posts_date;
        }

        public void setPosts_date(long posts_date) {
            this.posts_date = posts_date;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getUserTitle() {
            return userTitle;
        }

        public void setUserTitle(String userTitle) {
            this.userTitle = userTitle;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getMobileSign() {
            return mobileSign;
        }

        public void setMobileSign(String mobileSign) {
            this.mobileSign = mobileSign;
        }

        public int getReply_status() {
            return reply_status;
        }

        public void setReply_status(int reply_status) {
            this.reply_status = reply_status;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getRole_num() {
            return role_num;
        }

        public void setRole_num(int role_num) {
            this.role_num = role_num;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getIs_quote() {
            return is_quote;
        }

        public void setIs_quote(int is_quote) {
            this.is_quote = is_quote;
        }

        public int getQuote_pid() {
            return quote_pid;
        }

        public void setQuote_pid(int quote_pid) {
            this.quote_pid = quote_pid;
        }

        public String getQuote_content() {
            return quote_content;
        }

        public void setQuote_content(String quote_content) {
            this.quote_content = quote_content;
        }

        public String getQuote_user_name() {
            return quote_user_name;
        }

        public void setQuote_user_name(String quote_user_name) {
            this.quote_user_name = quote_user_name;
        }

        public boolean isDelThread() {
            return delThread;
        }

        public void setDelThread(boolean delThread) {
            this.delThread = delThread;
        }

        public List<TopicBean.ContentBean> getReply_content() {
            return reply_content;
        }

        public void setReply_content(List<TopicBean.ContentBean> reply_content) {
            this.reply_content = reply_content;
        }

        public List<?> getManagePanel() {
            return managePanel;
        }

        public void setManagePanel(List<?> managePanel) {
            this.managePanel = managePanel;
        }

        public List<ExtraPanelBean> getExtraPanel() {
            return extraPanel;
        }

        public void setExtraPanel(List<ExtraPanelBean> extraPanel) {
            this.extraPanel = extraPanel;
        }

        public static class ExtraPanelBean {
            private String action;
            private String title;
            private String recommendAdd;
            /**
             * beforeAction :
             * recommendAdd : 0
             * isHasRecommendAdd : 0
             */

            private ExtParamsBean extParams;
            private String type;

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

            public String getRecommendAdd() {
                return recommendAdd;
            }

            public void setRecommendAdd(String recommendAdd) {
                this.recommendAdd = recommendAdd;
            }

            public ExtParamsBean getExtParams() {
                return extParams;
            }

            public void setExtParams(ExtParamsBean extParams) {
                this.extParams = extParams;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public static class ExtParamsBean {
                private String beforeAction;
                private int recommendAdd;
                private int isHasRecommendAdd;

                public String getBeforeAction() {
                    return beforeAction;
                }

                public void setBeforeAction(String beforeAction) {
                    this.beforeAction = beforeAction;
                }

                public int getRecommendAdd() {
                    return recommendAdd;
                }

                public void setRecommendAdd(int recommendAdd) {
                    this.recommendAdd = recommendAdd;
                }

                public int getIsHasRecommendAdd() {
                    return isHasRecommendAdd;
                }

                public void setIsHasRecommendAdd(int isHasRecommendAdd) {
                    this.isHasRecommendAdd = isHasRecommendAdd;
                }
            }
        }
    }
}
