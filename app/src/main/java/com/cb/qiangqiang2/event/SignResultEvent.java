package com.cb.qiangqiang2.event;

import com.cb.qiangqiang2.common.event.BaseEvent;

/**
 * Created by chenbin on 2018/3/5.
 */
public class SignResultEvent extends BaseEvent {
    public static final int TYPE_UN_LOGIN = 1;
    public static final int TYPE_SIGN_SUCESS = 2;
    public static final int TYPE_SIGN_FAIL = 3;
    public static final int TYPE_ALREADY_SIGN = 4;

    private int type;

    public SignResultEvent(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
