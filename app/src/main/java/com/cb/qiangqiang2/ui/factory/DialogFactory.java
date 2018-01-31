package com.cb.qiangqiang2.ui.factory;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

import com.cb.qiangqiang2.R;

/**
 * Created by chenbin on 2018/1/31.
 */
public class DialogFactory {


    /**
     *
     * @param context
     * @param title
     * @param content
     * @param confirmStr：null: 不显示 | ""：使用默认值
     * @param cancelStr ：null: 不显示 | ""：使用默认值
     * @param listener
     */
    public static void showConfirmDialog(Context context, String title, String content, String confirmStr, String cancelStr,
                                         final OnDialogClickListener listener ) {
        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        if (!TextUtils.isEmpty(content)) {
            builder.setMessage(content);
        }
        if (confirmStr != null) {
            confirmStr = confirmStr.equals("") ? context.getString(R.string.dialog_confirm) : confirmStr;
            builder.setPositiveButton(confirmStr, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (listener != null) {
                        listener.onConfirmClick();
                    }
                }
            });
        }
        if (cancelStr != null) {
            cancelStr = cancelStr.equals("") ? context.getString(R.string.dialog_cancel) : cancelStr;
            builder.setNegativeButton(cancelStr, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (listener != null) {
                        listener.onCancelClick();
                    }
                }
            });
        }
        builder.show();
    }
}
