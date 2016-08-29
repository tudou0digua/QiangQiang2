package com.cb.qiangqiang2.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cb.qiangqiang2.R;
import com.cb.qiangqiang2.common.base.BaseAutoLayoutActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class Activity2 extends BaseAutoLayoutActivity {
    @BindView(R.id.btn)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_2;
    }

    @OnClick({R.id.btn})
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.btn:
                Intent intent = new Intent(mContext, Activity2.class);
                startActivity(intent);
                break;
        }
    }
}
