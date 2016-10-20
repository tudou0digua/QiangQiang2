package com.cb.qiangqiang2.ui.view;

import android.content.Context;
import android.os.Build;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ScrollView;

import timber.log.Timber;

/**
 * Created by cb on 2016/10/19.
 * ref: https://github.com/nuptboyzhb/SuperSwipeRefreshLayout/blob/master/SuperSwipeRefreshLayout-Demo-AS/lib/src/main/java/com/github/nuptboyzhb/lib/SuperSwipeRefreshLayout.java
 * ref: http://blog.csdn.net/bboyfeiyu/article/details/39935329
 * ref: http://www.jianshu.com/p/d23b42b6360b
 */

public class SuperSwipeRefreshLayout2 extends SwipeRefreshLayout {
    private static final int INVALID_POINTER = -1;

    private View mTarget;
    private boolean isLoadingMore = false;
    private OnRefreshAndLoadMoreListener mOnRefreshAndLoadMoreListener;
    private int mActivePointerId = INVALID_POINTER;
    private float mInitialMotionY;
    private float mInitialDownY;

    public SuperSwipeRefreshLayout2(Context context) {
        super(context);
        init(context, null);
    }

    public SuperSwipeRefreshLayout2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        ensureTarget();
    }

    private void ensureTarget() {
        // Don't bother getting the parent height if the parent hasn't been laid
        // out yet.
        if (mTarget == null) {
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                if (child instanceof RecyclerView
                        || child instanceof AbsListView
                        || child instanceof ScrollView
                        || child instanceof NestedScrollView) {
                    mTarget = child;
                    break;
                }
            }
        }
    }

    public void setOnLoadMoreListener(final OnRefreshAndLoadMoreListener mOnRefreshAndLoadMoreListener) {
        this.mOnRefreshAndLoadMoreListener = mOnRefreshAndLoadMoreListener;
        if (mOnRefreshAndLoadMoreListener != null) {
            setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh() {
                    mOnRefreshAndLoadMoreListener.onRefresh();
                }
            });
        }
    }

    public void setLoadingMore(boolean loadingMore) {
        isLoadingMore = loadingMore;
    }

    private float getMotionEventY(MotionEvent ev, int activePointerId) {
        final int index = MotionEventCompat.findPointerIndex(ev, activePointerId);
        if (index < 0) {
            return -1;
        }
        return MotionEventCompat.getY(ev, index);
    }

    public boolean canChildScrollDown() {
        ensureTarget();
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (mTarget instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) mTarget;
                return absListView.getCount() > 0
                        && (absListView.getLastVisiblePosition() < absListView.getCount() - 1 || absListView
                        .getChildAt(absListView.getLastVisiblePosition() - absListView.getFirstVisiblePosition())
                        .getBottom() > absListView.getPaddingBottom() + getMeasuredHeight());
            } else if (mTarget instanceof ScrollView) { // ScrollView
                return mTarget.getScrollY() + mTarget.getMeasuredHeight() < ((ScrollView)mTarget).getChildAt(0).getMeasuredHeight();
            } else {
                return true;
            }
        } else {
            return ViewCompat.canScrollVertically(mTarget, 1);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isRefreshing() || isLoadingMore) return false;
        final int action = MotionEventCompat.getActionMasked(ev);

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
                final float initialDownY = getMotionEventY(ev, mActivePointerId);
                if (initialDownY == -1) {
                    return false;
                }
                mInitialDownY = initialDownY;
                break;
            case MotionEvent.ACTION_MOVE:
                if (mActivePointerId == INVALID_POINTER) {
                    Timber.e("Got ACTION_MOVE event but don't have an active pointer id.");
                    return false;
                }

                final float y = getMotionEventY(ev, mActivePointerId);
                if (y == -1) {
                    return false;
                }
                final float yDiff = y - mInitialDownY;

//                if (yDiff > 0) {
//                    if(canChildScrollUp() || mListener == null){
//                        return false;
//                    }else {
//                        if (yDiff > mTouchSlop && !mIsBeingDragged && mListener.canRefresh()) {
//                            mInitialMotionY = mInitialDownY + mTouchSlop;
//                            mIsBeingDragged = true;
//                            mProgress.setAlpha(STARTING_PROGRESS_ALPHA);
//                        }
//                    }
//                }
//                else
//                {
//                    if(canChildScrollDown() || mListener == null){
//                        return false;
//                    }else {
//                        if (yDiff < 0 && mListener.canLoadMore()){
//                            mLoading = true;
//                            return true;
//                        }
//                    }
//                }
                break;
            case MotionEventCompat.ACTION_POINTER_UP:
                onSecondaryPointerUp(ev);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mActivePointerId = INVALID_POINTER;
                break;
        }

//        if (!isRefreshing() && !isLoadingMore) {
//            switch (action) {
//                case MotionEvent.ACTION_DOWN:
//
//                    break;
//                case MotionEvent.ACTION_UP:
//                case MotionEvent.ACTION_CANCEL:
//                case MotionEvent.ACTION_MOVE:
//                    if (mOnRefreshAndLoadMoreListener != null && isChildScrollToBottom()) {
//                        return true;
//                    }
//                    break;
//            }
//        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (!isRefreshing() && !isLoadingMore) {
            final int action = MotionEventCompat.getActionMasked(ev);
            switch (action) {
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    if (mOnRefreshAndLoadMoreListener != null && isChildScrollToBottom()) {
                        isLoadingMore = true;
                        mOnRefreshAndLoadMoreListener.onLoadMore();
                        return false;
                    }
                    break;
            }
        }
        return super.onTouchEvent(ev);
    }

    private void onSecondaryPointerUp(MotionEvent ev) {
        final int pointerIndex = MotionEventCompat.getActionIndex(ev);
        final int pointerId = MotionEventCompat.getPointerId(ev, pointerIndex);
        if (pointerId == mActivePointerId) {
            // This was our active pointer going up. Choose a new
            // active pointer and adjust accordingly.
            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
            mActivePointerId = MotionEventCompat.getPointerId(ev, newPointerIndex);
        }
    }

    /**
     * 判断目标View是否滑动到顶部-还能否继续滑动
     *
     * @return
     */
    public boolean isChildScrollToTop() {
        ensureTarget();
        if (Build.VERSION.SDK_INT < 14) {
            if (mTarget instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) mTarget;
                return !(absListView.getChildCount() > 0 && (absListView
                        .getFirstVisiblePosition() > 0 || absListView
                        .getChildAt(0).getTop() < absListView.getPaddingTop()));
            } else {
                return !(mTarget.getScrollY() > 0);
            }
        } else {
            return !ViewCompat.canScrollVertically(mTarget, -1);
        }
    }

    /**
     * 是否滑动到底部
     *
     * @return
     */
    public boolean isChildScrollToBottom() {
        ensureTarget();
        if (isChildScrollToTop()) {
            return false;
        }
        if (mTarget instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) mTarget;
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            int count = recyclerView.getAdapter().getItemCount();
            if (layoutManager instanceof LinearLayoutManager && count > 0) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == count - 1) {
                    return true;
                }
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                int[] lastItems = new int[2];
                staggeredGridLayoutManager
                        .findLastCompletelyVisibleItemPositions(lastItems);
                int lastItem = Math.max(lastItems[0], lastItems[1]);
                if (lastItem == count - 1) {
                    return true;
                }
            }
            return false;
        } else if (mTarget instanceof AbsListView) {
            final AbsListView absListView = (AbsListView) mTarget;
            int count = absListView.getAdapter().getCount();
            int fristPos = absListView.getFirstVisiblePosition();
            if (fristPos == 0
                    && absListView.getChildAt(0).getTop() >= absListView
                    .getPaddingTop()) {
                return false;
            }
            int lastPos = absListView.getLastVisiblePosition();
            if (lastPos > 0 && count > 0 && lastPos == count - 1) {
                return true;
            }
            return false;
        } else if (mTarget instanceof ScrollView) {
            ScrollView scrollView = (ScrollView) mTarget;
            View view = (View) scrollView
                    .getChildAt(scrollView.getChildCount() - 1);
            if (view != null) {
                int diff = (view.getBottom() - (scrollView.getHeight() + scrollView
                        .getScrollY()));
                if (diff == 0) {
                    return true;
                }
            }
        } else if (mTarget instanceof NestedScrollView) {
            NestedScrollView nestedScrollView = (NestedScrollView) mTarget;
            View view = (View) nestedScrollView.getChildAt(nestedScrollView.getChildCount() - 1);
            if (view != null) {
                int diff = (view.getBottom() - (nestedScrollView.getHeight() + nestedScrollView.getScrollY()));
                if (diff == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public interface OnRefreshAndLoadMoreListener {
        void onLoadMore();

        void onRefresh();
    }
}
