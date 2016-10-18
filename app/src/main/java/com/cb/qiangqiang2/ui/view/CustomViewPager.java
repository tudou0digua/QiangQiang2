package com.cb.qiangqiang2.ui.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 可禁用左右滑动的viewpager
 * Created by cb on 2016/10/18.
 */
public class CustomViewPager extends ViewPager {
    private boolean isScrollingEnabled = true;

    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.isScrollingEnabled && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return this.isScrollingEnabled && super.onInterceptTouchEvent(event);
    }

    /**
     * 是否禁用viewpager的左右滑动
     * @param scrollingEnabled false：禁用
     */
    public void setScrollingEnabled(boolean scrollingEnabled) {
        this.isScrollingEnabled = scrollingEnabled;
    }
}
