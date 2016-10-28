package com.cb.qiangqiang2.ui.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cb on 2016/10/18.
 */

public class TabLayout extends LinearLayout {
    private OnTabSelectedListener onTabSelectedListener;
    private OnTabDoubleClickListener onTabDoubleClickListener;

    private List<TabView> tabViewList;
    private Context context;

    public TabLayout(Context context) {
        super(context);
    }

    public TabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TabLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initData(context);
    }

    private void initData(Context context) {
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        tabViewList = new ArrayList<>();
        for (int i = 0; i < getChildCount(); i++) {
            final int position = i;
            TabView tabView = (TabView) getChildAt(i);
            tabViewList.add(tabView);
//            tabView.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    resetTabStatus();
//                    tabViewList.get(position).setSelected();
//                    if (onTabSelectedListener != null) {
//                        onTabSelectedListener.onTabSelected(position);
//                    }
//                }
//            });
            tabView.setGestureDetector(new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

                @Override
                public boolean onSingleTapConfirmed(MotionEvent e) {
                    resetTabStatus();
                    tabViewList.get(position).setSelected();
                    if (onTabSelectedListener != null) {
                        onTabSelectedListener.onTabSelected(position);
                    }
                    return super.onSingleTapConfirmed(e);
                }

                @Override
                public boolean onDown(MotionEvent e) {
                    //需返回true，不然后续监听不会触发
                    return true;
                }

                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    if (onTabDoubleClickListener != null) {
                        onTabDoubleClickListener.onTabDoubleClick(position);
                    }
                    return super.onDoubleTap(e);
                }
            }));
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

    public void setOnTabDoubleClickListener(OnTabDoubleClickListener onTabDoubleClickListener) {
        this.onTabDoubleClickListener = onTabDoubleClickListener;
    }

    public void setOnTabSelectedListener(OnTabSelectedListener onTabSelectedListener) {
        this.onTabSelectedListener = onTabSelectedListener;
    }

    public TabView getTabView(int position) {
        if (tabViewList != null && tabViewList.size() > position && position >= 0) {
            return tabViewList.get(position);
        } else {
            return null;
        }
    }

    public static void bindViewPager(final TabLayout tabLayout, ViewPager viewPager, final int totalSize) {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if ((position + 1) == totalSize) {
                    tabLayout.setSelected(position);
                    tabLayout.setUnSelected(position - 1);
                } else {
                    tabLayout.setTabViewAlpha(position + 1, positionOffset);
                    tabLayout.setTabViewAlpha(position, 1.0f - positionOffset);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public interface OnTabSelectedListener {
        void onTabSelected(int position);
    }

    public interface OnTabDoubleClickListener {
        void onTabDoubleClick(int position);
    }
}
