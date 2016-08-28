package com.cb.qiangqiang2.common;

import android.app.Application;

import com.cb.qiangqiang2.BuildConfig;
import com.cb.qiangqiang2.common.dagger.component.ApiComponent;
import com.cb.qiangqiang2.common.dagger.component.AppComponent;
import com.cb.qiangqiang2.common.dagger.component.DaggerApiComponent;
import com.cb.qiangqiang2.common.dagger.component.DaggerAppComponent;
import com.cb.qiangqiang2.common.dagger.module.AppModule;
import com.orhanobut.logger.Logger;

import timber.log.Timber;

/**
 * Created by cb on 2016/8/28.
 */
public class BaseApplication extends Application {
    private AppComponent appComponent;
    private ApiComponent apiComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initLog();
        initInjector();
        initInjectorApi();
    }

    private void initInjector() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    private void initInjectorApi() {
        apiComponent = DaggerApiComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    /**
     * 日志打印初始化 Timber结合Logger
     */
    private void initLog() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree(){
                @Override
                protected void log(int priority, String tag, String message, Throwable t) {
                    Logger.log(priority, tag, message, t);
                }
            });
        } else {
            //TODO deal with crash log or sth else
        }
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public ApiComponent getApiComponent() {
        return apiComponent;
    }
}
