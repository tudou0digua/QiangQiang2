package com.cb.qiangqiang2.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by cb on 2016/8/28.
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //for bind butter knife
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        mContext = this;
    }

    protected abstract int getLayoutId();
}
