package com.cb.qiangqiang2.common.dagger.component;

/**
 * Created by cb on 2016/8/28.
 */

import android.app.Activity;

import com.cb.qiangqiang2.common.dagger.module.ActivityModule;
import com.cb.qiangqiang2.common.dagger.scope.PerActivity;

import dagger.Component;

@PerActivity
@Component(
        dependencies = AppComponent.class,
        modules = ActivityModule.class
)
public interface ActivityComponent {
    Activity activity();
}
