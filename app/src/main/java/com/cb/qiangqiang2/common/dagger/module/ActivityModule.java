package com.cb.qiangqiang2.common.dagger.module;

import android.app.Activity;
import android.content.Context;

import com.cb.qiangqiang2.common.dagger.qualifier.ForActivity;
import com.cb.qiangqiang2.common.dagger.scope.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by cb on 2016/8/28.
 */
@Module
public class ActivityModule {
    private final Activity mActivity;

    public ActivityModule(Activity activity) {
        this.mActivity = activity;
    }

    @Provides
    @PerActivity
    Activity provideActivity() {
        return mActivity;
    }

    @Provides
    @ForActivity
    Context provideActivityContext() {
        return mActivity;
    }
}
