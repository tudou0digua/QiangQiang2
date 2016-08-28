package com.cb.qiangqiang2.common.dagger.component;

import com.cb.qiangqiang2.common.dagger.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by cb on 2016/8/28.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
//    void inject(SettingsFragment settingsFragment);
//
//    void inject(RecommendedFragment recommendedFragment);
//
//    void inject(UserSpaceActivity userSpaceActivity);
//
//    Navigator navigator();
}
