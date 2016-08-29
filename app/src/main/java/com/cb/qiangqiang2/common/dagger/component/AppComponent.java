package com.cb.qiangqiang2.common.dagger.component;

import android.app.Application;
import android.content.Context;

import com.cb.qiangqiang2.common.dagger.module.AppModule;
import com.cb.qiangqiang2.common.dagger.qualifier.ForApplication;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by cb on 2016/8/28.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    @ForApplication
    Context context();

    Application application();

//    RibotsService ribotsService();

//    ApiServices apiServices();
}
