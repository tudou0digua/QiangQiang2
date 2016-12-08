package com.cb.qiangqiang2.data.model;

/**
 * Created by cb on 2016/12/8.
 */

public class PostDetailBean {
    private int type;
    private PostDetailModel.TopicBean.ContentBean contentBean;
    PostDetailModel.TopicBean topicBean;
    PostDetailModel.ListBean listBean;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public PostDetailModel.TopicBean.ContentBean getContentBean() {
        return contentBean;
    }

    public void setContentBean(PostDetailModel.TopicBean.ContentBean contentBean) {
        this.contentBean = contentBean;
    }

    public PostDetailModel.TopicBean getTopicBean() {
        return topicBean;
    }

    public void setTopicBean(PostDetailModel.TopicBean topicBean) {
        this.topicBean = topicBean;
    }

    public PostDetailModel.ListBean getListBean() {
        return listBean;
    }

    public void setListBean(PostDetailModel.ListBean listBean) {
        this.listBean = listBean;
    }
}
