package com.cb.qiangqiang2.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cb.qiangqiang2.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BlankFragment2 extends Fragment {
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    private List<Fragment> fragments;

    public BlankFragment2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank_fragment2, container, false);
        ButterKnife.bind(this, view);

        fragments = new ArrayList<>();
        fragments.add(BlankFragment.newInstance("inside fragment 0", null));
        fragments.add(BlankFragment.newInstance("inside fragment 1", null));

        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getChildFragmentManager()) {
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

        return view;
    }

}
