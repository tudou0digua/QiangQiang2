package com.cb.qiangqiang2.event;

import com.cb.qiangqiang2.common.event.BaseEvent;

/**
 * Created by cb on 2016/10/22.
 */

public class PostScrollEvent extends BaseEvent {
    private boolean isShowTabLayout;

    public PostScrollEvent(boolean isShowTabLayout) {
        this.isShowTabLayout = isShowTabLayout;
    }

    public boolean isShowTabLayout() {
        return isShowTabLayout;
    }
}
