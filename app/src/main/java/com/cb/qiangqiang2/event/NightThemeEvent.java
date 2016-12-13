package com.cb.qiangqiang2.event;

import com.cb.qiangqiang2.common.event.BaseEvent;

/**
 * Created by cb on 2016/12/13.
 */

public class NightThemeEvent extends BaseEvent {
    boolean isNightTheme;

    public NightThemeEvent(boolean isNightTheme) {
        this.isNightTheme = isNightTheme;
    }

    public boolean isNightTheme() {
        return isNightTheme;
    }
}
