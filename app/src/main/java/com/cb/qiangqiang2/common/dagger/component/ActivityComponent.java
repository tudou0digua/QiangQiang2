package com.cb.qiangqiang2.common.dagger.component;

/**
 * Created by cb on 2016/8/28.
 */

import android.app.Activity;

import com.cb.qiangqiang2.common.dagger.module.ActivityModule;
import com.cb.qiangqiang2.common.dagger.scope.PerActivity;
import com.cb.qiangqiang2.test.activity.Activity2;
import com.cb.qiangqiang2.test.activity.MainTestActivity;
import com.cb.qiangqiang2.ui.activity.BoardDragEditActivity;
import com.cb.qiangqiang2.ui.activity.MainActivity;
import com.cb.qiangqiang2.ui.activity.WebViewActivity;
import com.cb.qiangqiang2.ui.fragment.BoardFragment;
import com.cb.qiangqiang2.ui.fragment.PostFragment;

import dagger.Component;

@PerActivity
@Component(
        dependencies = AppComponent.class,
        modules = ActivityModule.class
)
public interface ActivityComponent {

    Activity activity();

    void inject(MainActivity mainActivity);

    void inject(BoardDragEditActivity boardDragEditActivity);

    void inject(WebViewActivity webViewActivity);

    void inject(BoardFragment boardFragment);

    void inject(PostFragment postFragment);



    void inject(MainTestActivity mainTestActivity);

    void inject(Activity2 activity2);
}
