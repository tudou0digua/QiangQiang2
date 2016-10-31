package com.cb.qiangqiang2.common.dagger.module;

/**
 * Created by cb on 2016/8/28.
 */

import android.app.Application;
import android.content.Context;

import com.cb.qiangqiang2.common.application.BaseApplication;
import com.cb.qiangqiang2.common.dagger.qualifier.ForApplication;
import com.cb.qiangqiang2.data.UserManager;
import com.cb.qiangqiang2.data.api.ApiService;
import com.cb.qiangqiang2.data.api.HttpManager;
import com.cb.qiangqiang2.data.db.DbManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Provide application-level dependencies.
 */
@Module
public class AppModule {
    private final BaseApplication mApplication;

    public AppModule(BaseApplication application) {
        this.mApplication = application;
    }

    @Provides
    @Singleton
    @ForApplication
    Context provideAppContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    ApiService provideApiService(HttpManager httpManager) {
        return httpManager.getService();
    }

    @Provides
    @Singleton
    UserManager provideUserManager() {
        return new UserManager(mApplication);
    }

    @Provides
    @Singleton
    DbManager providerDbManager() {
        return new DbManager(mApplication);
    }
}
