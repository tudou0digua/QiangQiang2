package com.cb.qiangqiang2.event;

import com.cb.qiangqiang2.common.event.BaseEvent;

/**
 * Created by cb on 2016/12/13.
 */

public class SendViewEvent extends BaseEvent {
    private boolean showSendView;

    public SendViewEvent(boolean showSendView) {
        this.showSendView = showSendView;
    }

    public boolean isShowSendView() {
        return showSendView;
    }
}
