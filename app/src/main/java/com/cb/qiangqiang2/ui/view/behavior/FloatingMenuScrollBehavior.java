package com.cb.qiangqiang2.ui.view.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

import com.cb.qiangqiang2.event.SendViewEvent;
import com.github.clans.fab.FloatingActionMenu;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by cb on 2016/12/8.
 */

public class FloatingMenuScrollBehavior extends CoordinatorLayout.Behavior<FloatingActionMenu> {

    public FloatingMenuScrollBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionMenu child, View directTargetChild, View target, int nestedScrollAxes) {
        // Ensure we react to vertical scrolling
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL
                || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionMenu child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        EventBus.getDefault().post(new SendViewEvent(false));
        if ((dyUnconsumed > 0 || dyConsumed > 0) && child.getVisibility() == View.VISIBLE) {
            child.hideMenu(true);
        } else if ((dyUnconsumed < 0 || dyConsumed < 0) && child.getVisibility() != View.VISIBLE) {
            child.showMenu(true);
        }
    }
}
