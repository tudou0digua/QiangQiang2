package com.cb.qiangqiang2.event;

import com.cb.qiangqiang2.common.event.BaseEvent;

/**
 * Created by cb on 2016/10/27.
 */

public class TotalNumEvent extends BaseEvent {
    private int totalNum = -1;
    private String type;

    public TotalNumEvent(int totalNum, String type) {
        this.totalNum = totalNum;
        this.type = type;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public String getType() {
        return type;
    }
}
