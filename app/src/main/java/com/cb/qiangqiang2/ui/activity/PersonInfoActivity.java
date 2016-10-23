package com.cb.qiangqiang2.ui.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cb.qiangqiang2.R;
import com.cb.qiangqiang2.common.base.BaseSwipeBackActivity;
import com.cb.qiangqiang2.common.glide.GlideCircleTransform;

import butterknife.BindView;

public class PersonInfoActivity extends BaseSwipeBackActivity {

    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.activity_person_info)
    CoordinatorLayout mActivityPersonInfo;
    @BindView(R.id.iv_top_bg)
    ImageView mIvTopBg;
    @BindView(R.id.iv_avatar)
    ImageView mIvAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_person_info;
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
        mCollapsingToolbarLayout.setTitle("个人信息");
        String avatarUrl = "http://www.qiangqiang5.com/uc_server/avatar.php?uid=44752&size=middle";
        Glide.with(mContext)
                .load("http://www.qiangqiang5.com/uc_server/avatar.php?uid=44752&size=middle")
                .placeholder(R.drawable.default_icon)
                .bitmapTransform(new GlideCircleTransform(mContext))
                .crossFade(300)
                .into(mIvAvatar);
    }
}
