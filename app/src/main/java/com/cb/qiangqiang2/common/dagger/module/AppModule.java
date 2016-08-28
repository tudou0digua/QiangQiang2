package com.cb.qiangqiang2.common.dagger.module;

/**
 * Created by cb on 2016/8/28.
 */

import android.accounts.AccountManager;
import android.content.Context;

import com.cb.qiangqiang2.common.BaseApplication;
import com.cb.qiangqiang2.common.dagger.qualifier.ForApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Provide application-level dependencies.
 */
@Module
public class AppModule {
    private final BaseApplication app;

    public AppModule(BaseApplication app) {
        this.app = app;
    }

    @Provides
    @Singleton
    @ForApplication
    Context provideAppContext() {
        return app;
    }

//    @Provides
//    @Singleton
//    Prefser providePrefser(@ForApplication Context context) {
//        return new Prefser(context);
//    }

    @Provides
    @Singleton
    AccountManager provideAccountManager(@ForApplication Context context) {
        return AccountManager.get(context);
    }

//    @Provides
//    @Singleton
//    AuthAccountManager provideAuthAccountManager(@ForApplication Context context) {
//        return new AuthAccountManager(context);
//    }

}
