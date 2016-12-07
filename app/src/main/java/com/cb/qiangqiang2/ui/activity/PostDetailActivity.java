package com.cb.qiangqiang2.ui.activity;

import android.os.Bundle;

import com.cb.qiangqiang2.R;
import com.cb.qiangqiang2.common.base.BaseSwipeBackActivity;

public class PostDetailActivity extends BaseSwipeBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_post_detail;
    }

    @Override
    protected void injectActivity() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }
}
