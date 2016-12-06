package com.cb.qiangqiang2.data.db.greendao.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;

/**
 * Created by cb on 2016/10/31.
 */
@Entity
public class SearchHistory {
    @Id(autoincrement = true)
    private Long id;
    private String content;
    private Date time;
    @Generated(hash = 635393117)
    public SearchHistory(Long id, String content, Date time) {
        this.id = id;
        this.content = content;
        this.time = time;
    }
    @Generated(hash = 1905904755)
    public SearchHistory() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public Date getTime() {
        return this.time;
    }
    public void setTime(Date time) {
        this.time = time;
    }
}
