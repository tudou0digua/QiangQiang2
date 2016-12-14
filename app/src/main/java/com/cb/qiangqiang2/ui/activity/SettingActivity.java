package com.cb.qiangqiang2.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.widget.CompoundButton;

import com.cb.qiangqiang2.R;
import com.cb.qiangqiang2.common.base.BaseSwipeBackActivity;
import com.cb.qiangqiang2.common.constant.Constants;
import com.cb.qiangqiang2.common.util.PrefUtils;
import com.cb.qiangqiang2.event.NightThemeEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

public class SettingActivity extends BaseSwipeBackActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.switch_night)
    SwitchCompat switchNight;

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
    }
}
