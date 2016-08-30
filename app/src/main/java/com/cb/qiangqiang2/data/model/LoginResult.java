package com.cb.qiangqiang2.data.model;

import java.util.List;

/**
 * Created by cb on 2016/8/30.
 */
public class LoginResult {

    /**
     * rs : 1
     * errcode :
     * head : {"errCode":"00000000","errInfo":"调用成功,没有任何错误","version":"2.6.0.1","alert":0}
     * body : {"externInfo":{"padding":""}}
     * isValidation : 0
     * token : b459663e3080de87270c849fbdb74
     * secret : 99c732f3e3887983103a6ceb1a07a
     * score : 2870
     * uid : 44752
     * userName :
     * avatar : http://www.qiangqiang5.com/uc_server/avatar.php?uid=44752&size=middle
     * gender : 0
     * userTitle : 六级抢友
     * repeatList : []
     * verify : []
     * creditShowList : [{"type":"credits","title":"积分","data":2870},{"type":"extcredits2","title":"银币","data":2859}]
     * mobile :
     * groupid : 15
     */

    private int rs;
    private String errcode;
    /**
     * errCode : 00000000
     * errInfo : 调用成功,没有任何错误
     * version : 2.6.0.1
     * alert : 0
     */

    private HeadBean head;
    /**
     * externInfo : {"padding":""}
     */

    private BodyBean body;
    private int isValidation;
    private String token;
    private String secret;
    private int score;
    private int uid;
    private String userName;
    private String avatar;
    private int gender;
    private String userTitle;
    private String mobile;
    private int groupid;
    private List<?> repeatList;
    private List<?> verify;

    /**
     * type : credits
     * title : 积分
     * data : 2870
     */

    private List<CreditShowListBean> creditShowList;

    public int getRs() {
        return rs;
    }

    public void setRs(int rs) {
        this.rs = rs;
    }

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public HeadBean getHead() {
        return head;
    }

    public void setHead(HeadBean head) {
        this.head = head;
    }

    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }

    public int getIsValidation() {
        return isValidation;
    }

    public void setIsValidation(int isValidation) {
        this.isValidation = isValidation;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getGroupid() {
        return groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }

    public List<?> getRepeatList() {
        return repeatList;
    }

    public void setRepeatList(List<?> repeatList) {
        this.repeatList = repeatList;
    }

    public List<?> getVerify() {
        return verify;
    }

    public void setVerify(List<?> verify) {
        this.verify = verify;
    }

    public List<CreditShowListBean> getCreditShowList() {
        return creditShowList;
    }

    public void setCreditShowList(List<CreditShowListBean> creditShowList) {
        this.creditShowList = creditShowList;
    }

    public static class HeadBean {
        private String errCode;
        private String errInfo;
        private String version;
        private int alert;

        public String getErrCode() {
            return errCode;
        }

        public void setErrCode(String errCode) {
            this.errCode = errCode;
        }

        public String getErrInfo() {
            return errInfo;
        }

        public void setErrInfo(String errInfo) {
            this.errInfo = errInfo;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public int getAlert() {
            return alert;
        }

        public void setAlert(int alert) {
            this.alert = alert;
        }
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
