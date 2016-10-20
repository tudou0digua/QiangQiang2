package com.cb.qiangqiang2.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.cb.qiangqiang2.R;
import com.cb.qiangqiang2.common.base.BaseAutoLayoutActivity;
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

    @BindView(R.id.tl_tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    CustomViewPager viewpager;

    private List<Fragment> fragments;

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
    }

}
