package com.cb.qiangqiang2.data.model;

import java.util.List;

/**
 *  板块列表
 * Created by cb on 2016/10/20.
 */

public class BoardModel extends BaseModel {

    /**
     * externInfo : {"padding":""}
     */

    private BodyBean body;
    /**
     * body : {"externInfo":{"padding":""}}
     * list : [{"board_category_id":94,"board_category_name":"抢友交流区","board_category_type":2,"board_list":[{"board_id":95,"board_name":"抢友交流","description":"有啥抱怨的、线报、求助，都发这里啦，抢友大讨论区域","board_child":0,"board_img":"http://www.qiangqiang5.com/data/attachment/common/81/common_95_icon.gif","last_posts_date":"1476881763000","board_content":1,"forumRedirect":"","favNum":793,"td_posts_num":4936,"topic_total_num":81406,"posts_total_num":2602696,"is_focus":1}]}]
     * online_user_num : 0
     * td_visitors : 0
     */

    private int online_user_num;
    private int td_visitors;
    /**
     * board_category_id : 94
     * board_category_name : 抢友交流区
     * board_category_type : 2
     * board_list : [{"board_id":95,"board_name":"抢友交流","description":"有啥抱怨的、线报、求助，都发这里啦，抢友大讨论区域","board_child":0,"board_img":"http://www.qiangqiang5.com/data/attachment/common/81/common_95_icon.gif","last_posts_date":"1476881763000","board_content":1,"forumRedirect":"","favNum":793,"td_posts_num":4936,"topic_total_num":81406,"posts_total_num":2602696,"is_focus":1}]
     */

    private List<ListBean> list;

    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }

    public int getOnline_user_num() {
        return online_user_num;
    }

    public void setOnline_user_num(int online_user_num) {
        this.online_user_num = online_user_num;
    }

    public int getTd_visitors() {
        return td_visitors;
    }

    public void setTd_visitors(int td_visitors) {
        this.td_visitors = td_visitors;
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
        private int board_category_id;
        private String board_category_name;
        private int board_category_type;
        /**
         * board_id : 95
         * board_name : 抢友交流
         * description : 有啥抱怨的、线报、求助，都发这里啦，抢友大讨论区域
         * board_child : 0
         * board_img : http://www.qiangqiang5.com/data/attachment/common/81/common_95_icon.gif
         * last_posts_date : 1476881763000
         * board_content : 1
         * forumRedirect :
         * favNum : 793
         * td_posts_num : 4936
         * topic_total_num : 81406
         * posts_total_num : 2602696
         * is_focus : 1
         */

        private List<BoardListBean> board_list;

        public int getBoard_category_id() {
            return board_category_id;
        }

        public void setBoard_category_id(int board_category_id) {
            this.board_category_id = board_category_id;
        }

        public String getBoard_category_name() {
            return board_category_name;
        }

        public void setBoard_category_name(String board_category_name) {
            this.board_category_name = board_category_name;
        }

        public int getBoard_category_type() {
            return board_category_type;
        }

        public void setBoard_category_type(int board_category_type) {
            this.board_category_type = board_category_type;
        }

        public List<BoardListBean> getBoard_list() {
            return board_list;
        }

        public void setBoard_list(List<BoardListBean> board_list) {
            this.board_list = board_list;
        }

        public static class BoardListBean {
            private int board_id;
            private String board_name;
            private String description;
            private int board_child;
            private String board_img;
            private long last_posts_date;
            private int board_content;
            private String forumRedirect;
            private int favNum;
            private int td_posts_num;
            private int topic_total_num;
            private int posts_total_num;
            private int is_focus;

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

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public int getBoard_child() {
                return board_child;
            }

            public void setBoard_child(int board_child) {
                this.board_child = board_child;
            }

            public String getBoard_img() {
                return board_img;
            }

            public void setBoard_img(String board_img) {
                this.board_img = board_img;
            }

            public long getLast_posts_date() {
                return last_posts_date;
            }

            public void setLast_posts_date(long last_posts_date) {
                this.last_posts_date = last_posts_date;
            }

            public int getBoard_content() {
                return board_content;
            }

            public void setBoard_content(int board_content) {
                this.board_content = board_content;
            }

            public String getForumRedirect() {
                return forumRedirect;
            }

            public void setForumRedirect(String forumRedirect) {
                this.forumRedirect = forumRedirect;
            }

            public int getFavNum() {
                return favNum;
            }

            public void setFavNum(int favNum) {
                this.favNum = favNum;
            }

            public int getTd_posts_num() {
                return td_posts_num;
            }

            public void setTd_posts_num(int td_posts_num) {
                this.td_posts_num = td_posts_num;
            }

            public int getTopic_total_num() {
                return topic_total_num;
            }

            public void setTopic_total_num(int topic_total_num) {
                this.topic_total_num = topic_total_num;
            }

            public int getPosts_total_num() {
                return posts_total_num;
            }

            public void setPosts_total_num(int posts_total_num) {
                this.posts_total_num = posts_total_num;
            }

            public int getIs_focus() {
                return is_focus;
            }

            public void setIs_focus(int is_focus) {
                this.is_focus = is_focus;
            }
        }
    }
}
