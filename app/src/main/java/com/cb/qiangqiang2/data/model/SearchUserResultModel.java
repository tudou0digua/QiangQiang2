package com.cb.qiangqiang2.data.model;

import java.util.List;

/**
 * Created by cb on 2016/12/6.
 */

public class SearchUserResultModel extends BaseModel {

    /**
     * externInfo : {"padding":""}
     * list : [{"uid":11012,"icon":"http://www.qiangqiang5.com/uc_server/avatar.php?uid=11012&size=middle","isFriend":1,"is_black":0,"gender":1,"name":"齐齐格的世界","status":0,"level":4,"credits":655,"isFollow":0,"dateline":"1403080031000","signture":"今天才周2","location":"","distance":"","userTitle":"四级抢友"}]
     */

    private BodyBean body;
    /**
     * body : {"externInfo":{"padding":""},"list":[{"uid":11012,"icon":"http://www.qiangqiang5.com/uc_server/avatar.php?uid=11012&size=middle","isFriend":1,"is_black":0,"gender":1,"name":"齐齐格的世界","status":0,"level":4,"credits":655,"isFollow":0,"dateline":"1403080031000","signture":"今天才周2","location":"","distance":"","userTitle":"四级抢友"}]}
     * searchid : 1026
     * page : 1
     * has_next : 0
     * total_num : 1
     */

    private int searchid;
    private int page;
    private int has_next;
    private int total_num;

    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }

    public int getSearchid() {
        return searchid;
    }

    public void setSearchid(int searchid) {
        this.searchid = searchid;
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

    public static class BodyBean {
        /**
         * padding :
         */

        private ExternInfoBean externInfo;
        /**
         * uid : 11012
         * icon : http://www.qiangqiang5.com/uc_server/avatar.php?uid=11012&size=middle
         * isFriend : 1
         * is_black : 0
         * gender : 1
         * name : 齐齐格的世界
         * status : 0
         * level : 4
         * credits : 655
         * isFollow : 0
         * dateline : 1403080031000
         * signture : 今天才周2
         * location :
         * distance :
         * userTitle : 四级抢友
         */

        private List<ListBean> list;

        public ExternInfoBean getExternInfo() {
            return externInfo;
        }

        public void setExternInfo(ExternInfoBean externInfo) {
            this.externInfo = externInfo;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
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

        public static class ListBean {
            private int uid;
            private String icon;
            private int isFriend;
            private int is_black;
            private int gender;
            private String name;
            private int status;
            private int level;
            private int credits;
            private int isFollow;
            private long dateline;
            private String signture;
            private String location;
            private String distance;
            private String userTitle;

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public int getIsFriend() {
                return isFriend;
            }

            public void setIsFriend(int isFriend) {
                this.isFriend = isFriend;
            }

            public int getIs_black() {
                return is_black;
            }

            public void setIs_black(int is_black) {
                this.is_black = is_black;
            }

            public int getGender() {
                return gender;
            }

            public void setGender(int gender) {
                this.gender = gender;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public int getCredits() {
                return credits;
            }

            public void setCredits(int credits) {
                this.credits = credits;
            }

            public int getIsFollow() {
                return isFollow;
            }

            public void setIsFollow(int isFollow) {
                this.isFollow = isFollow;
            }

            public long getDateline() {
                return dateline;
            }

            public void setDateline(long dateline) {
                this.dateline = dateline;
            }

            public String getSignture() {
                return signture;
            }

            public void setSignture(String signture) {
                this.signture = signture;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
            }

            public String getUserTitle() {
                return userTitle;
            }

            public void setUserTitle(String userTitle) {
                this.userTitle = userTitle;
            }
        }
    }
}
