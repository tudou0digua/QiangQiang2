package com.cb.qiangqiang2.common.application;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatDelegate;

import com.blankj.utilcode.util.Utils;
import com.cb.qiangqiang2.BuildConfig;
import com.cb.qiangqiang2.common.constant.Constants;
import com.cb.qiangqiang2.common.dagger.component.AppComponent;
import com.cb.qiangqiang2.common.dagger.component.DaggerAppComponent;
import com.cb.qiangqiang2.common.dagger.module.AppModule;
import com.cb.qiangqiang2.common.util.PrefUtils;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

import timber.log.Timber;

/**
 * Created by cb on 2016/8/28.
 */
public class BaseApplication extends Application {
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        //设置主题，夜间模式用到
        boolean isNightTheme = PrefUtils.getBoolean(this, Constants.IS_NIGHT_THEME, false);
        AppCompatDelegate.setDefaultNightMode(isNightTheme ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
        Utils.init(this);
        initLog();
        initAppComponent();
    }

    /**
     * 日志打印初始化 Timber结合Logger
     */
    private void initLog() {

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree(){
                @Override
                protected void log(int priority, String tag, String message, Throwable t) {
                    super.log(priority, tag, message, t);
                    //could bind Logger with Timber
//                    Logger.log(priority, tag, message, t);
                }
            });
        } else {
            //Release version do not log
            Logger.init().logLevel(LogLevel.NONE);
            //TODO deal with crash log or sth else
        }
    }

    private void initAppComponent() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    //Dagger2: getAppComponent
    public AppComponent getAppComponent() {
        if (appComponent == null){
            initAppComponent();
        }
        return appComponent;
    }

    public static BaseApplication getApplication(Context context) {
        return (BaseApplication) context.getApplicationContext();
    }

}
