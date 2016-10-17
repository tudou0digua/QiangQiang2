package com.cb.qiangqiang2.test.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.cb.qiangqiang2.R;

import butterknife.ButterKnife;

public class CustomViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {

    }
}
