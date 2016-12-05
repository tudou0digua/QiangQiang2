package com.cb.qiangqiang2.event;

import com.cb.qiangqiang2.common.event.BaseEvent;

/**
 * Created by cb on 2016/12/5.
 */

public class SearchEvent extends BaseEvent {
    private String searchContent;

    public SearchEvent(String searchContent) {
        this.searchContent = searchContent;
    }

    public String getSearchContent() {
        return searchContent;
    }
}
