package com.cb.qiangqiang2.data.model;

import java.util.List;

/**
 * Created by cb on 2016/10/25.
 */

public class UserInfoModel extends BaseModel {

    /**
     * externInfo : {"padding":""}
     * repeatList : []
     * profileList : [{"type":"realname","title":"真实姓名","data":""},{"type":"address","title":"邮寄地址","data":""},{"type":"zipcode","title":"邮编","data":""}]
     * creditList : [{"type":"credits","title":"积分","data":2871},{"type":"extcredits2","title":"银币","data":2860},{"type":"extcredits3","title":"金币","data":11}]
     * creditShowList : [{"type":"credits","title":"积分","data":2871},{"type":"extcredits2","title":"银币","data":2860}]
     */

    private BodyBean body;
    /**
     * body : {"externInfo":{"padding":""},"repeatList":[],"profileList":[{"type":"realname","title":"真实姓名","data":""},{"type":"address","title":"邮寄地址","data":""},{"type":"zipcode","title":"邮编","data":""}],"creditList":[{"type":"credits","title":"积分","data":2871},{"type":"extcredits2","title":"银币","data":2860},{"type":"extcredits3","title":"金币","data":11}],"creditShowList":[{"type":"credits","title":"积分","data":2871},{"type":"extcredits2","title":"银币","data":2860}]}
     * flag : 1
     * is_black : 0
     * is_follow : 0
     * isFriend : 0
     * icon : http://www.qiangqiang5.com/uc_server/avatar.php?uid=44752&size=middle
     * level_url :
     * name : test1234
     * email : 111111111@qq.com
     * status : 2
     * gender : 0
     * score : 2871
     * credits : 2871
     * gold_num : 2860
     * topic_num : 13
     * photo_num : 0
     * reply_posts_num : 7
     * essence_num : 0
     * friend_num : 0
     * follow_num : 2
     * level : 2
     * sign : 我来啦，抢抢加油
     * userTitle : 六级抢友
     * verify : [{"icon":"","vid":1,"verifyName":"抢友认证"}]
     * mobile :
     * info : []
     */

    private int flag;
    private int is_black;
    private int is_follow;
    private int isFriend;
    private String icon;
    private String level_url;
    private String name;
    private String email;
    private int status;
    private int gender;
    private int score;
    private int credits;
    private int gold_num;
    private int topic_num;
    private int photo_num;
    private int reply_posts_num;
    private int essence_num;
    private int friend_num;
    private int follow_num;
    private int level;
    private String sign;
    private String userTitle;
    private String mobile;
    /**
     * icon :
     * vid : 1
     * verifyName : 抢友认证
     */

    private List<VerifyBean> verify;
    private List<?> info;

    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getIs_black() {
        return is_black;
    }

    public void setIs_black(int is_black) {
        this.is_black = is_black;
    }

    public int getIs_follow() {
        return is_follow;
    }

    public void setIs_follow(int is_follow) {
        this.is_follow = is_follow;
    }

    public int getIsFriend() {
        return isFriend;
    }

    public void setIsFriend(int isFriend) {
        this.isFriend = isFriend;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getLevel_url() {
        return level_url;
    }

    public void setLevel_url(String level_url) {
        this.level_url = level_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public int getGold_num() {
        return gold_num;
    }

    public void setGold_num(int gold_num) {
        this.gold_num = gold_num;
    }

    public int getTopic_num() {
        return topic_num;
    }

    public void setTopic_num(int topic_num) {
        this.topic_num = topic_num;
    }

    public int getPhoto_num() {
        return photo_num;
    }

    public void setPhoto_num(int photo_num) {
        this.photo_num = photo_num;
    }

    public int getReply_posts_num() {
        return reply_posts_num;
    }

    public void setReply_posts_num(int reply_posts_num) {
        this.reply_posts_num = reply_posts_num;
    }

    public int getEssence_num() {
        return essence_num;
    }

    public void setEssence_num(int essence_num) {
        this.essence_num = essence_num;
    }

    public int getFriend_num() {
        return friend_num;
    }

    public void setFriend_num(int friend_num) {
        this.friend_num = friend_num;
    }

    public int getFollow_num() {
        return follow_num;
    }

    public void setFollow_num(int follow_num) {
        this.follow_num = follow_num;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getUserTitle() {
        return userTitle;
    }

    public void setUserTitle(String userTitle) {
        this.userTitle = userTitle;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public List<VerifyBean> getVerify() {
        return verify;
    }

    public void setVerify(List<VerifyBean> verify) {
        this.verify = verify;
    }

    public List<?> getInfo() {
        return info;
    }

    public void setInfo(List<?> info) {
        this.info = info;
    }

    public static class BodyBean {
        /**
         * padding :
         */

        private ExternInfoBean externInfo;
        private List<?> repeatList;
        /**
         * type : realname
         * title : 真实姓名
         * data :
         */

        private List<ProfileListBean> profileList;
        /**
         * type : credits
         * title : 积分
         * data : 2871
         */

        private List<CreditListBean> creditList;
        /**
         * type : credits
         * title : 积分
         * data : 2871
         */

        private List<CreditShowListBean> creditShowList;

        public ExternInfoBean getExternInfo() {
            return externInfo;
        }

        public void setExternInfo(ExternInfoBean externInfo) {
            this.externInfo = externInfo;
        }

        public List<?> getRepeatList() {
            return repeatList;
        }

        public void setRepeatList(List<?> repeatList) {
            this.repeatList = repeatList;
        }

        public List<ProfileListBean> getProfileList() {
            return profileList;
        }

        public void setProfileList(List<ProfileListBean> profileList) {
            this.profileList = profileList;
        }

        public List<CreditListBean> getCreditList() {
            return creditList;
        }

        public void setCreditList(List<CreditListBean> creditList) {
            this.creditList = creditList;
        }

        public List<CreditShowListBean> getCreditShowList() {
            return creditShowList;
        }

        public void setCreditShowList(List<CreditShowListBean> creditShowList) {
            this.creditShowList = creditShowList;
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

        public static class ProfileListBean {
            private String type;
            private String title;
            private String data;

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

            public String getData() {
                return data;
            }

            public void setData(String data) {
                this.data = data;
            }
        }

        public static class CreditListBean {
            private String type;
            private String title;
            private int data;

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

            public int getData() {
                return data;
            }

            public void setData(int data) {
                this.data = data;
            }
        }

        public static class CreditShowListBean {
            private String type;
            private String title;
            private int data;

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

            public int getData() {
                return data;
            }

            public void setData(int data) {
                this.data = data;
            }
        }
    }

    public static class VerifyBean {
        private String icon;
        private int vid;
        private String verifyName;

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getVid() {
            return vid;
        }

        public void setVid(int vid) {
            this.vid = vid;
        }

        public String getVerifyName() {
            return verifyName;
        }

        public void setVerifyName(String verifyName) {
            this.verifyName = verifyName;
        }
    }
}
