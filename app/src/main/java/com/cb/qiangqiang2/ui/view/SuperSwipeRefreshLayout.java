package com.cb.qiangqiang2.ui.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

/**
 * Created by cb on 2016/10/19.
 * ref: https://github.com/nuptboyzhb/SuperSwipeRefreshLayout/blob/master/SuperSwipeRefreshLayout-Demo-AS/lib/src/main/java/com/github/nuptboyzhb/lib/SuperSwipeRefreshLayout.java
 * ref: http://blog.csdn.net/bboyfeiyu/article/details/39935329
 * ref: http://www.jianshu.com/p/d23b42b6360b
 */

public class SuperSwipeRefreshLayout extends SwipeRefreshLayout {
    private MaterialProgressBar mLoadMoreProgressBar;
    private int mLoadMoreProgressDiameter;

    public SuperSwipeRefreshLayout(Context context) {
        this(context, null);
    }

    public SuperSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        createLoadMoreProgressBar(context);
    }

    private void createLoadMoreProgressBar(Context context) {
        mLoadMoreProgressBar = new MaterialProgressBar(context);
        addView(mLoadMoreProgressBar);

        final DisplayMetrics metrics = getResources().getDisplayMetrics();
        mLoadMoreProgressDiameter = (int) (40 * metrics.density);

        mLoadMoreProgressBar.setVisibility(GONE);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mLoadMoreProgressBar.measure(MeasureSpec.makeMeasureSpec(mLoadMoreProgressDiameter, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(mLoadMoreProgressDiameter, MeasureSpec.EXACTLY));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        final int width = getMeasuredWidth();
        final int height = getMeasuredHeight();
        final int childBottom = getPaddingBottom();
        final int loadMoreProgressWidth = mLoadMoreProgressBar.getMeasuredWidth();
        final int loadMoreProgressHeight = mLoadMoreProgressBar.getMeasuredHeight();
        mLoadMoreProgressBar.layout((width / 2 - loadMoreProgressWidth / 2),
                height - loadMoreProgressHeight - childBottom,
                (width / 2 + loadMoreProgressWidth / 2), height - childBottom);
    }
}
