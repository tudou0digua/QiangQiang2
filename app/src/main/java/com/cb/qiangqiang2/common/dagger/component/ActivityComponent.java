package com.cb.qiangqiang2.common.dagger.component;

/**
 * Created by cb on 2016/8/28.
 */

import android.app.Activity;

import com.cb.qiangqiang2.common.dagger.module.ActivityModule;
import com.cb.qiangqiang2.common.dagger.scope.PerActivity;
import com.cb.qiangqiang2.test.activity.Activity2;
import com.cb.qiangqiang2.test.activity.MainTestActivity;
import com.cb.qiangqiang2.ui.activity.BigImageActivity;
import com.cb.qiangqiang2.ui.activity.ImageListActivity;
import com.cb.qiangqiang2.ui.activity.LoginActivity;
import com.cb.qiangqiang2.ui.activity.PostDetailActivity;
import com.cb.qiangqiang2.ui.activity.SearchActivity;
import com.cb.qiangqiang2.ui.activity.SelectPublishBoardActivity;
import com.cb.qiangqiang2.ui.activity.SettingActivity;
import com.cb.qiangqiang2.ui.activity.SplashActivity;
import com.cb.qiangqiang2.ui.activity.UserInfoActivity;
import com.cb.qiangqiang2.ui.activity.BoardDragEditActivity;
import com.cb.qiangqiang2.ui.activity.MainActivity;
import com.cb.qiangqiang2.ui.activity.WebViewActivity;
import com.cb.qiangqiang2.ui.fragment.BlankFragment;
import com.cb.qiangqiang2.ui.fragment.BoardFragment;
import com.cb.qiangqiang2.ui.fragment.PostFragment;
import com.cb.qiangqiang2.ui.fragment.SearchPostFragment;
import com.cb.qiangqiang2.ui.fragment.SearchUserFragment;
import com.cb.qiangqiang2.ui.fragment.UserListFragment;

import dagger.Component;

@PerActivity
@Component(
        dependencies = AppComponent.class,
        modules = ActivityModule.class
)
public interface ActivityComponent {

    Activity activity();

    void inject(SplashActivity splashActivity);

    void inject(MainActivity mainActivity);

    void inject(BoardDragEditActivity boardDragEditActivity);

    void inject(WebViewActivity webViewActivity);

    void inject(UserInfoActivity userInfoActivity);

    void inject(BoardFragment boardFragment);

    void inject(PostFragment postFragment);

    void inject(LoginActivity loginActivity);

    void inject(UserListFragment userListFragment);

    void inject(SearchActivity searchActivity);

    void inject(BigImageActivity bigImageActivity);

    void inject(SearchPostFragment searchPostFragment);

    void inject(SearchUserFragment searchUserFragment);

    void inject(PostDetailActivity postDetailActivity);

    void inject(SettingActivity settingActivity);

    void inject(ImageListActivity imageListActivity);

    void inject(SelectPublishBoardActivity selectPublishBoardActivity);


    void inject(MainTestActivity mainTestActivity);

    void inject(Activity2 activity2);

    void inject(BlankFragment blankFragment);
}
