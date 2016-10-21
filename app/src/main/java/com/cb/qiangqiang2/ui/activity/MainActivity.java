package com.cb.qiangqiang2.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.cb.qiangqiang2.R;
import com.cb.qiangqiang2.common.base.BaseAutoLayoutActivity;
import com.cb.qiangqiang2.common.constant.Constants;
import com.cb.qiangqiang2.test.activity.MainTestActivity;
import com.cb.qiangqiang2.ui.fragment.BlankFragment;
import com.cb.qiangqiang2.ui.fragment.BoardFragment;
import com.cb.qiangqiang2.ui.fragment.PostFragment;
import com.cb.qiangqiang2.ui.view.CustomViewPager;
import com.cb.qiangqiang2.ui.view.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseAutoLayoutActivity {
    private static final int EXIT_INTERVAL = 2000;
    
    @BindView(R.id.tl_tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    CustomViewPager viewpager;

    private List<Fragment> fragments;
    private long lastBackClickTime = Constants.DEFAULT_INVALIDE_TIME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        //主页面不可以侧滑返回
        getSwipeBackLayout().setEnableGesture(false);

        initData();
        initView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    private void initData() {
        fragments = new ArrayList<>();
//        fragments.add(new BlankFragment2());
        fragments.add(PostFragment.newInstance(0, "new"));
        fragments.add(BoardFragment.newInstance("", ""));
//        fragments.add(BlankFragment.newInstance("fragment 1", null));
        fragments.add(BlankFragment.newInstance("fragment 2", null));
        fragments.add(BlankFragment.newInstance("fragment 3", null));
    }

    private void initView() {
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
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                Timber.e("position:" + position + " | positionOffset: " + positionOffset + " | positionOffsetPixels" + positionOffsetPixels);
                if ((position + 1) == fragments.size()) {
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
        viewpager.setCurrentItem(0);
        viewpager.setScrollingEnabled(true);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                viewpager.setCurrentItem(position, false);
                if (position == fragments.size() - 1) startActivity(new Intent(MainActivity.this, MainTestActivity.class));
            }
        });
        tabLayout.setOnTabDoubleClickListener(new TabLayout.OnTabDoubleClickListener() {
            @Override
            public void onTabDoubleClick(int position) {
                if (position == 0 && viewpager.getCurrentItem() == 0){
                    ((PostFragment)fragments.get(0)).scrollToTop();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if (lastBackClickTime == Constants.DEFAULT_INVALIDE_TIME) {
            lastBackClickTime = currentTime;
            showSnackBar();
            return;
        } else if ((currentTime - lastBackClickTime) <= EXIT_INTERVAL){
            super.onBackPressed();
        } else if ((currentTime - lastBackClickTime) > EXIT_INTERVAL) {
            lastBackClickTime = currentTime;
            showSnackBar();
        }
    }

    private void showSnackBar() {
        Snackbar.make(tabLayout, getString(R.string.exit), Snackbar.LENGTH_SHORT).show();
    }
}
