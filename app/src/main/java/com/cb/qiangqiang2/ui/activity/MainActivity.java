package com.cb.qiangqiang2.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cb.qiangqiang2.R;
import com.cb.qiangqiang2.common.base.BaseAutoLayoutActivity;
import com.cb.qiangqiang2.common.constant.Constants;
import com.cb.qiangqiang2.common.util.PrefUtils;
import com.cb.qiangqiang2.test.activity.Activity2;
import com.cb.qiangqiang2.test.activity.ContentProviderActivity;
import com.cb.qiangqiang2.test.activity.CustomViewActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseAutoLayoutActivity {
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.tv_2)
    TextView tv2;
    @BindView(R.id.btn)
    Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        //主页面不可以侧滑返回
        getSwipeBackLayout().setEnableGesture(false);
        init();
    }

    private void init() {

        tv.setText("hello world one");
        tv2.setText("hello world two");

    }

    @OnClick({R.id.btn, R.id.btn_day, R.id.btn_night, R.id.btn_jump_to_custom_view, R.id.btn_jump_to_content_provider})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn:
                Intent intent = new Intent(mContext, Activity2.class);
                startActivity(intent);
                break;
            case R.id.btn_day:
                PrefUtils.putBoolean(mContext, Constants.IS_NIGHT_THEME, false);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                recreate();
                break;
            case R.id.btn_night:
                PrefUtils.putBoolean(mContext, Constants.IS_NIGHT_THEME, true);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                recreate();
                break;
            case R.id.btn_jump_to_custom_view:
                startActivity(new Intent(mContext, CustomViewActivity.class));
                break;
            case R.id.btn_jump_to_content_provider:
                startActivity(new Intent(mContext, ContentProviderActivity.class));
                break;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
