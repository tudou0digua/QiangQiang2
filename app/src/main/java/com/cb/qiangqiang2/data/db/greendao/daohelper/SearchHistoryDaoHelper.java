package com.cb.qiangqiang2.data.db.greendao.daohelper;

import com.cb.qiangqiang2.data.db.DbManager;
import com.cb.qiangqiang2.data.db.greendao.bean.SearchHistory;
import com.cb.qiangqiang2.data.db.greendao.gen.SearchHistoryDao;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by cb on 2016/12/6.
 */

public class SearchHistoryDaoHelper {

    @Inject
    DbManager dbManager;

    @Inject
    public SearchHistoryDaoHelper() {
    }

    public SearchHistoryDao getSearchHistoryDao() {
        return dbManager.getSearchHistoryDao();
    }

    public List<SearchHistory> queryAll() {
        return getSearchHistoryDao().queryBuilder().orderDesc(SearchHistoryDao.Properties.Time).build().list();
    }

    public void addSearchHistory(String searchContent) {
        SearchHistory searchHistory = new SearchHistory(null, searchContent, new Date());
        addSearchHistory(searchHistory);
    }

    public void addSearchHistory(SearchHistory searchHistory) {
        List<SearchHistory> list = getSearchHistoryDao().queryBuilder().where(SearchHistoryDao.Properties.Content.eq(searchHistory.getContent())).build().list();
        if (list != null && list.size() > 0) {
            searchHistory = list.get(0);
            searchHistory.setTime(new Date());
            getSearchHistoryDao().update(searchHistory);
        } else {
            getSearchHistoryDao().insert(searchHistory);
        }
    }

    public void deleteSearchHistory(SearchHistory searchHistory) {
        getSearchHistoryDao().delete(searchHistory);
    }
}
