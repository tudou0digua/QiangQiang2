package com.cb.qiangqiang2.common.dagger.module;

import android.app.Activity;

import com.cb.qiangqiang2.common.dagger.scope.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by cb on 2016/8/28.
 */
@Module
public class ActivityModule {
    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    Activity provideActivity() {
        return activity;
    }
}
