package com.cb.qiangqiang2.data.model;

import java.util.List;

/**
 * Created by cb on 2016/11/30.
 */

public class SearchPostResultModel extends BaseModel{

    /**
     * externInfo : {"padding":""}
     */

    private BodyBean body;
    /**
     * body : {"externInfo":{"padding":""}}
     * page : 1
     * has_next : 1
     * total_num : 500
     * searchid : 1024
     * list : [{"board_id":95,"topic_id":124156,"type_id":252,"sort_id":0,"vote":0,"title":"天猫密令不正常","subject":"今天直播中公布的天猫密令都有错误，不知道是否bug。没中过一个，都显示纳尼。","user_id":77281,"last_reply_date":"1476882432000","user_nick_name":"marco_hwt","hits":65,"replies":1,"top":0,"status":32800,"essence":0,"hot":0,"pic_path":""}]
     */

    private int page;
    private int has_next;
    private int total_num;
    private int searchid;
    /**
     * board_id : 95
     * topic_id : 124156
     * type_id : 252
     * sort_id : 0
     * vote : 0
     * title : 天猫密令不正常
     * subject : 今天直播中公布的天猫密令都有错误，不知道是否bug。没中过一个，都显示纳尼。
     * user_id : 77281
     * last_reply_date : 1476882432000
     * user_nick_name : marco_hwt
     * hits : 65
     * replies : 1
     * top : 0
     * status : 32800
     * essence : 0
     * hot : 0
     * pic_path :
     */

    private List<ListBean> list;

    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
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

    public int getSearchid() {
        return searchid;
    }

    public void setSearchid(int searchid) {
        this.searchid = searchid;
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

    public static class ListBean {
        private int board_id;
        private int topic_id;
        private int type_id;
        private int sort_id;
        private int vote;
        private String title;
        private String subject;
        private int user_id;
        private String last_reply_date;
        private String user_nick_name;
        private int hits;
        private int replies;
        private int top;
        private int status;
        private int essence;
        private int hot;
        private String pic_path;

        public int getBoard_id() {
            return board_id;
        }

        public void setBoard_id(int board_id) {
            this.board_id = board_id;
        }

        public int getTopic_id() {
            return topic_id;
        }

        public void setTopic_id(int topic_id) {
            this.topic_id = topic_id;
        }

        public int getType_id() {
            return type_id;
        }

        public void setType_id(int type_id) {
            this.type_id = type_id;
        }

        public int getSort_id() {
            return sort_id;
        }

        public void setSort_id(int sort_id) {
            this.sort_id = sort_id;
        }

        public int getVote() {
            return vote;
        }

        public void setVote(int vote) {
            this.vote = vote;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getLast_reply_date() {
            return last_reply_date;
        }

        public void setLast_reply_date(String last_reply_date) {
            this.last_reply_date = last_reply_date;
        }

        public String getUser_nick_name() {
            return user_nick_name;
        }

        public void setUser_nick_name(String user_nick_name) {
            this.user_nick_name = user_nick_name;
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

        public int getEssence() {
            return essence;
        }

        public void setEssence(int essence) {
            this.essence = essence;
        }

        public int getHot() {
            return hot;
        }

        public void setHot(int hot) {
            this.hot = hot;
        }

        public String getPic_path() {
            return pic_path;
        }

        public void setPic_path(String pic_path) {
            this.pic_path = pic_path;
        }
    }
}
