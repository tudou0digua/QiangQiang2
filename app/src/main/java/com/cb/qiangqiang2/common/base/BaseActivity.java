package com.cb.qiangqiang2.common.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

import com.cb.qiangqiang2.common.application.BaseApplication;
import com.cb.qiangqiang2.common.dagger.component.ActivityComponent;
import com.cb.qiangqiang2.common.dagger.component.DaggerActivityComponent;
import com.cb.qiangqiang2.common.dagger.module.ActivityModule;
import com.cb.qiangqiang2.common.dagger.qualifier.ForActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Created by cb on 2016/8/28.
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Inject
    @ForActivity
    protected Context mContext;
    private ActivityComponent mActivityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //for bind butter knife
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        injectActivity();
        attachPresenter();
        restoreSavedInstanceState(savedInstanceState);
        initData();
        initView();
    }

    abstract protected @LayoutRes int getLayoutId();

    protected void restoreSavedInstanceState(Bundle savedInstanceState) {

    }

    protected void attachPresenter() {

    }

    protected void injectActivity() {

    }

    protected void initData() {

    }

    protected void initView() {

    }

    public ActivityComponent getActivityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .appComponent(BaseApplication.getApplication(this).getAppComponent())
                    .build();
        }
        return mActivityComponent;
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        EventBus.getDefault().register(this);
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        EventBus.getDefault().unregister(this);
//    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onMessageEvent() {
//
//    }
}
