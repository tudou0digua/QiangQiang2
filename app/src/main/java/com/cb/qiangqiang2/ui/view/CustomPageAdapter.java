package com.cb.qiangqiang2.ui.view;

import android.support.v4.view.PagerAdapter;
import android.view.ViewGroup;

/**
 * Created by chenbin on 2018/2/28.
 */
public abstract class CustomPageAdapter extends PagerAdapter {

    private ViewGroup viewGroup;

    public ViewGroup getCurrentItemView() {
        return this.viewGroup;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (this.viewGroup != container) {
            this.viewGroup = container;
        }
        super.setPrimaryItem(container, position, object);
    }
}
