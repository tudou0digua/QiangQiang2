package com.cb.qiangqiang2.ui.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;

import com.cb.qiangqiang2.R;
import com.cb.qiangqiang2.common.base.BaseAutoLayoutActivity;
import com.cb.qiangqiang2.common.constant.Constants;
import com.cb.qiangqiang2.event.PostScrollEvent;
import com.cb.qiangqiang2.test.activity.MainTestActivity;
import com.cb.qiangqiang2.ui.fragment.BlankFragment;
import com.cb.qiangqiang2.ui.fragment.BoardFragment;
import com.cb.qiangqiang2.ui.fragment.PostFragment;
import com.cb.qiangqiang2.ui.view.CustomViewPager;
import com.cb.qiangqiang2.ui.view.TabLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.cb.qiangqiang2.common.constant.Constants.POST_NEW;

public class MainActivity extends BaseAutoLayoutActivity {
    private static final int EXIT_INTERVAL = 2000;

    @BindView(R.id.tl_tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    CustomViewPager viewpager;
    @BindView(R.id.toolbar_main)
    Toolbar mToolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    private List<Fragment> fragments;
    private long lastBackClickTime = Constants.DEFAULT_INVALIDE_TIME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //主页面不可以侧滑返回
        getSwipeBackLayout().setEnableGesture(false);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void injectActivity() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initData() {
        fragments = new ArrayList<>();
        fragments.add(PostFragment.newInstance(0, POST_NEW));
        fragments.add(BoardFragment.newInstance());
        fragments.add(BlankFragment.newInstance("fragment 2", null));
        fragments.add(BlankFragment.newInstance("fragment 3", null));
    }

    @Override
    protected void initView() {
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        };
        viewpager.setAdapter(adapter);
        viewpager.setOffscreenPageLimit(fragments.size());
        viewpager.setCurrentItem(0);
        viewpager.setScrollingEnabled(true);

        TabLayout.bindViewPager(tabLayout, viewpager, fragments.size());

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                viewpager.setCurrentItem(position, false);
                if (position == fragments.size() - 1)
                    startActivity(new Intent(MainActivity.this, MainTestActivity.class));
                Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                intent.putExtra(WebViewActivity.TITLE, "签到");
                intent.putExtra(WebViewActivity.URL, "http://www.qiangqiang5.com/plugin.php?id=dsu_paulsign:sign");
                if (position == fragments.size() - 2) startActivity(intent);
            }
        });
        tabLayout.setOnTabDoubleClickListener(new TabLayout.OnTabDoubleClickListener() {
            @Override
            public void onTabDoubleClick(int position) {
                if (position == 0 && viewpager.getCurrentItem() == 0) {
                    ((PostFragment) fragments.get(0)).scrollToTop();
                }
            }
        });
    }

    /**
     * 菜单显示隐藏动画
     *
     * @param showOrHide true:show
     */
    private void startAnimation(boolean showOrHide) {
        final ViewGroup.LayoutParams layoutParams = tabLayout.getLayoutParams();
        ValueAnimator valueAnimator;
        ObjectAnimator alpha;
        int tabHeight = getResources().getDimensionPixelOffset(R.dimen.tab_height);
        if (!showOrHide) {
            valueAnimator = ValueAnimator.ofInt(tabHeight, 0);
            alpha = ObjectAnimator.ofFloat(tabLayout, "alpha", 1, 0);
        } else {
            valueAnimator = ValueAnimator.ofInt(0, tabHeight);
            alpha = ObjectAnimator.ofFloat(tabLayout, "alpha", 0, 1);
        }
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                layoutParams.height = (int) valueAnimator.getAnimatedValue();
                tabLayout.setLayoutParams(layoutParams);
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(500);
        animatorSet.playTogether(valueAnimator, alpha);
        animatorSet.start();
    }

    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if (lastBackClickTime == Constants.DEFAULT_INVALIDE_TIME) {
            lastBackClickTime = currentTime;
            showSnackBar();
            return;
        } else if ((currentTime - lastBackClickTime) <= EXIT_INTERVAL) {
            super.onBackPressed();
        } else if ((currentTime - lastBackClickTime) > EXIT_INTERVAL) {
            lastBackClickTime = currentTime;
            showSnackBar();
        }
    }

    private void showSnackBar() {
        Snackbar.make(tabLayout, getString(R.string.exit), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(PostScrollEvent event) {
        startAnimation(event.isShowTabLayout());
    }


}
