package com.cb.qiangqiang2.common.dagger.component;

import android.app.Application;
import android.content.Context;

import com.cb.qiangqiang2.common.dagger.module.AppModule;
import com.cb.qiangqiang2.common.dagger.qualifier.ForApplication;
import com.cb.qiangqiang2.data.UserManager;
import com.cb.qiangqiang2.data.api.ApiService;
import com.cb.qiangqiang2.data.db.DbManager;

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

    ApiService apiService();

    UserManager userManger();

    DbManager dbManager();
}
