package com.cb.qiangqiang2.data.model;

/**
 * Created by cb on 2016/8/31.
 */
public class BaseModel {
    /**
     * rs : 0
     * errcode : 50000000
     * head : {"alert":1,"errCode":"00100001","errInfo":"您需要先登录才能继续本操作","version":"2.6.0.1"}
     */

    protected int rs;
    protected String errcode;

    /**
     * alert : 1
     * errCode : 00100001
     * errInfo : 您需要先登录才能继续本操作
     * version : 2.6.0.1
     */
    protected HeadBean head;

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

    public static class HeadBean {
        private int alert;
        private String errCode;
        private String errInfo;
        private String version;

        public int getAlert() {
            return alert;
        }

        public void setAlert(int alert) {
            this.alert = alert;
        }

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
    }
}
