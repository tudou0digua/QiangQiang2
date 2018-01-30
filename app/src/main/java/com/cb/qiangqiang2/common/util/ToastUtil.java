package com.cb.qiangqiang2.common.util;

import android.content.Context;
import android.widget.Toast;

import com.cb.qiangqiang2.common.application.BaseApplication;

/**
 * Created by chenbin on 2018/1/30.
 */
public class ToastUtil {

    public static void show(int msgRes) {
        show(BaseApplication.getAppContext().getString(msgRes));
    }

    public static void show(String msg) {
        Toast.makeText(BaseApplication.getAppContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void show(String msg, boolean isLong) {
        Toast.makeText(BaseApplication.getAppContext(), msg,
                isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
    }

    public static void show(Context context, int msgRes) {
        show(context, context.getString(msgRes));
    }

    public static void show(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void show(Context context, String msg, boolean isLong) {
        Toast.makeText(context, msg, isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
    }
}
