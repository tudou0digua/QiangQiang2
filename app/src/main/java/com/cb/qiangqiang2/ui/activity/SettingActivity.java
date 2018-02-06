package com.cb.qiangqiang2.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.cb.qiangqiang2.R;
import com.cb.qiangqiang2.common.base.BaseSwipeBackActivity;
import com.cb.qiangqiang2.common.constant.Constants;
import com.cb.qiangqiang2.common.util.CacheUtil;
import com.cb.qiangqiang2.common.util.PrefUtils;
import com.cb.qiangqiang2.common.util.ToastUtil;
import com.cb.qiangqiang2.event.NightThemeEvent;
import com.cb.qiangqiang2.ui.factory.DialogFactory;
import com.cb.qiangqiang2.ui.factory.OnDialogClickListener;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class SettingActivity extends BaseSwipeBackActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.switch_night)
    SwitchCompat switchNight;
    @BindView(R.id.tv_cache_size)
    TextView tvCacheSize;

    public static void startSettingActivity(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void injectActivity() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initView() {
        toolbar.setTitle(getString(R.string.setting_title));

        boolean isNightTheme = PrefUtils.getBoolean(this, Constants.IS_NIGHT_THEME, false);
        switchNight.setChecked(isNightTheme);
        AppCompatDelegate.setDefaultNightMode(isNightTheme ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);

        switchNight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                PrefUtils.putBoolean(mContext, Constants.IS_NIGHT_THEME, b);
                EventBus.getDefault().post(new NightThemeEvent(b));
                AppCompatDelegate.setDefaultNightMode(b ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
                getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
                recreate();
            }
        });

        Observable.just("")
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        try {
                            return CacheUtil.getTotalCacheSize(SettingActivity.this);
                        } catch (Exception e) {
                            e.printStackTrace();
                            return "0KB";
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        tvCacheSize.setText(s);
                    }
                });
    }

    @OnClick({R.id.rl_clear_cache})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_clear_cache:
                //清除缓存
                DialogFactory.showConfirmDialog(SettingActivity.this,
                        getString(R.string.setting_clear_cache_dialog_title),
                        getString(R.string.setting_clear_cache_dialog_content),
                        "",
                        "",
                        new OnDialogClickListener(){
                            @Override
                            public void onConfirmClick() {
                                CacheUtil.clearAllCache(SettingActivity.this);
                                ToastUtil.show(SettingActivity.this, R.string.setting_clear_cache_success);
                                tvCacheSize.setText("0KB");
                            }

                            @Override
                            public void onCancelClick() {

                            }
                        });
                break;
        }
    }
}
