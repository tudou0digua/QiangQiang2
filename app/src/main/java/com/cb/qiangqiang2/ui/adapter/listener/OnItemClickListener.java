package com.cb.qiangqiang2.ui.adapter.listener;

import android.view.View;

/**
 * Created by cb on 2016/10/22.
 */

public interface OnItemClickListener<T extends View> {
    void onItemClick(int position, T t);
}
