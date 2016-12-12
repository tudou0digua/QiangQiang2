package com.cb.qiangqiang2.data.model;

/**
 * Created by cb on 2016/12/12.
 */

public class ReplyPostBody {

    /**
     * json : {"fid":95,"tid":110026,"location":"","aid":"","content":[{"type":0,"infor":"2楼正解"}],"longitude":"120.11682891845703","latitude":"30.274738311767578","isHidden":0,"isAnonymous":0,"isOnlyAuthor":0,"isShowPostion":0,"replyId":0,"isQuote":0}
     */

    private BodyBean body;

    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }

    public static class BodyBean {
        /**
         * fid : 95
         * tid : 110026
         * location :
         * aid :
         * content : [{"type":0,"infor":"2楼正解"}]
         * longitude : 120.11682891845703
         * latitude : 30.274738311767578
         * isHidden : 0
         * isAnonymous : 0
         * isOnlyAuthor : 0
         * isShowPostion : 0
         * replyId : 0
         * isQuote : 0
         */

        private JsonBean json;

        public JsonBean getJson() {
            return json;
        }

        public void setJson(JsonBean json) {
            this.json = json;
        }

        public static class JsonBean {
            private int fid;
            private int tid;
            private String location = "";
            private String aid = "";
            private String longitude = "";
            private String latitude = "";
            private int isHidden;
            private int isAnonymous;
            private int isOnlyAuthor;
            private int isShowPostion;
            private int replyId;
            private int isQuote;
            /**
             * type : 0
             * infor : 2楼正解
             */

            private String content;

            public int getFid() {
                return fid;
            }

            public void setFid(int fid) {
                this.fid = fid;
            }

            public int getTid() {
                return tid;
            }

            public void setTid(int tid) {
                this.tid = tid;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public String getAid() {
                return aid;
            }

            public void setAid(String aid) {
                this.aid = aid;
            }

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }

            public int getIsHidden() {
                return isHidden;
            }

            public void setIsHidden(int isHidden) {
                this.isHidden = isHidden;
            }

            public int getIsAnonymous() {
                return isAnonymous;
            }

            public void setIsAnonymous(int isAnonymous) {
                this.isAnonymous = isAnonymous;
            }

            public int getIsOnlyAuthor() {
                return isOnlyAuthor;
            }

            public void setIsOnlyAuthor(int isOnlyAuthor) {
                this.isOnlyAuthor = isOnlyAuthor;
            }

            public int getIsShowPostion() {
                return isShowPostion;
            }

            public void setIsShowPostion(int isShowPostion) {
                this.isShowPostion = isShowPostion;
            }

            public int getReplyId() {
                return replyId;
            }

            public void setReplyId(int replyId) {
                this.replyId = replyId;
            }

            public int getIsQuote() {
                return isQuote;
            }

            public void setIsQuote(int isQuote) {
                this.isQuote = isQuote;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public static class ContentBean {
                private int type;
                private String infor;

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }

                public String getInfor() {
                    return infor;
                }

                public void setInfor(String infor) {
                    this.infor = infor;
                }
            }
        }
    }
}
