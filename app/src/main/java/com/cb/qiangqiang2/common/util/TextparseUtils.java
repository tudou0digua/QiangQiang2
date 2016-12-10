package com.cb.qiangqiang2.common.util;

import android.content.Context;

/**
 * Created by cb on 2016/12/10.
 */

public class TextParseUtils {
    private static TextParseUtils textParseUtils;

    public static TextParseUtils getInstance(Context context) {
        if (textParseUtils == null) {
            synchronized (TextParseUtils.class) {
                textParseUtils = new TextParseUtils(context);
            }
        }
        return textParseUtils;
    }

    public TextParseUtils(Context context) {

    }


}
