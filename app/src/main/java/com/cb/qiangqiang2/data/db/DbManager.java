package com.cb.qiangqiang2.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.cb.qiangqiang2.common.dagger.qualifier.ForApplication;
import com.cb.qiangqiang2.data.db.greendao.gen.DaoMaster;
import com.cb.qiangqiang2.data.db.greendao.gen.DaoSession;
import com.cb.qiangqiang2.data.db.greendao.gen.SearchResultDao;

/**
 * Created by cb on 2016/8/29.
 */
public class DbManager {
    public static final String DB_NAME = "qq_db";
    private Context mContext;
    private DaoSession daoSession;

    public DbManager(@ForApplication Context context) {
        mContext = context;
        initData();
    }

    private void initData() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(mContext, DB_NAME, null);
        SQLiteDatabase writableDB = devOpenHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(writableDB);
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public SearchResultDao getSearhResultDao() {
        return daoSession.getSearchResultDao();
    }
}
