package com.cb.qiangqiang2.data.model;

/**
 * 整理后的板块列表
 * Created by cb on 2016/10/20.
 */

public class BoardBean {
    private String category;
    private int categoryId;
    private int id;
    private String description;
    private String image;
    private long lastPostsDate;
    private int favNum;
    private int todayPostsNum;
    private int topicTotalNum;
    private int postsTotalNum;
    private String name;

    public BoardBean() {
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getPostsTotalNum() {
        return postsTotalNum;
    }

    public void setPostsTotalNum(int postsTotalNum) {
        this.postsTotalNum = postsTotalNum;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getLastPostsDate() {
        return lastPostsDate;
    }

    public void setLastPostsDate(long lastPostsDate) {
        this.lastPostsDate = lastPostsDate;
    }

    public int getFavNum() {
        return favNum;
    }

    public void setFavNum(int favNum) {
        this.favNum = favNum;
    }

    public int getTodayPostsNum() {
        return todayPostsNum;
    }

    public void setTodayPostsNum(int todayPostsNum) {
        this.todayPostsNum = todayPostsNum;
    }

    public int getTopicTotalNum() {
        return topicTotalNum;
    }

    public void setTopicTotalNum(int topicTotalNum) {
        this.topicTotalNum = topicTotalNum;
    }
}
