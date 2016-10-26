package com.cb.qiangqiang2.ui.adapter.listener;

import android.view.View;

/**
 * Created by cb on 2016/10/26.
 */

public interface OnItemLongClickListener<T> {
    void onItemLongClick(int position, View view, T t);
}
