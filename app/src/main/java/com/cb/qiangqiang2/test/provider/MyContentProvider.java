package com.cb.qiangqiang2.test.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by cb on 2016/10/14.
 */

public class MyContentProvider extends ContentProvider {
    private static final String TAG = "MyContentProvider";

    public static final String AUTHORITY = "com.cb.qiangqiang2.provider";

    private static final int TABLE1_DIR = 0;
    private static final int TABLE1_ITEM = 1;
    private static final int TABLE2_DIR = 2;
    private static final int TABLE2_ITEM = 3;

    private static UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "table1", TABLE1_DIR);
        uriMatcher.addURI(AUTHORITY, "table1/#", TABLE1_ITEM);
        uriMatcher.addURI(AUTHORITY, "table2", TABLE2_DIR);
        uriMatcher.addURI(AUTHORITY, "table2/#", TABLE2_ITEM);
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        switch (uriMatcher.match(uri)) {
            case TABLE1_DIR:

                break;
            case TABLE1_ITEM:

                break;
            case TABLE2_DIR:

                break;
            case TABLE2_ITEM:

                break;
        }

        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case TABLE1_ITEM:

                return "vnd.android.cursor.dir/vnd." + AUTHORITY + ".table1";
            case TABLE1_DIR:

                return "vnd.android.cursor.item/vnd." + AUTHORITY + ".table1";
            case TABLE2_ITEM:

                return "vnd.android.cursor.dir/vnd." + AUTHORITY + ".table2";
            case TABLE2_DIR:

                return "vnd.android.cursor.item/vnd." + AUTHORITY + ".table2";
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
