package com.cb.qiangqiang2.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cb.qiangqiang2.R;
import com.cb.qiangqiang2.common.base.BaseAutoLayoutActivity;

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

    @OnClick({R.id.btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn:
                Intent intent = new Intent(mContext, Activity2.class);
                startActivity(intent);
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
