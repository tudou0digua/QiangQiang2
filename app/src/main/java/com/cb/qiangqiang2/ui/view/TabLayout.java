package com.cb.qiangqiang2.ui.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cb on 2016/10/18.
 */

public class TabLayout extends LinearLayout {
    private OnTabSelectedListener onTabSelectedListener;
    private List<TabView> tabViewList;

    public TabLayout(Context context) {
        super(context);
    }

    public TabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TabLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        tabViewList = new ArrayList<>();
        for (int i = 0; i < getChildCount(); i++) {
            final int position = i;
            TabView tabView = (TabView) getChildAt(i);
            tabViewList.add(tabView);
            tabView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    resetTabStatus();
                    tabViewList.get(position).setSelected();
                    if (onTabSelectedListener != null) {
                        onTabSelectedListener.onTabSelected(position);
                    }
                }
            });
        }
    }

    private void resetTabStatus() {
        for (TabView tab : tabViewList) {
            tab.setUnSelected();
        }
    }

    public void setSelected(int position) {
        tabViewList.get(position).setSelected();
    }

    public void setUnSelected(int position) {
        tabViewList.get(position).setUnSelected();
    }

    public void setTabViewAlpha(int position, float alpha) {
        tabViewList.get(position).setmAlpha(alpha);
    }

    public void setOnTabSelectedListener(OnTabSelectedListener onTabSelectedListener) {
        this.onTabSelectedListener = onTabSelectedListener;
    }

    public interface OnTabSelectedListener{
        void onTabSelected(int position);
    }
}
