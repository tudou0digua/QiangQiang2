package com.cb.qiangqiang2.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.cb.qiangqiang2.R;
import com.cb.qiangqiang2.common.base.BaseSwipeBackActivity;
import com.cb.qiangqiang2.data.db.DbManager;
import com.cb.qiangqiang2.data.db.greendao.bean.SearchResult;
import com.cb.qiangqiang2.data.db.greendao.gen.SearchResultDao;
import com.cb.qiangqiang2.presenter.SearchPostPresenter;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class SearchActivity extends BaseSwipeBackActivity {

    @BindView(R.id.search_bar)
    MaterialSearchBar searchBar;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;

    @Inject
    SearchPostPresenter searchPostPresenter;
    @Inject
    DbManager dbManager;

    private List<String> lastSearches;
    private SearchResultDao searchResultDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void injectActivity() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initData() {
        searchResultDao = dbManager.getSearhResultDao();
        lastSearches = new ArrayList<>();
        List<SearchResult> list = searchResultDao.queryBuilder().orderDesc(SearchResultDao.Properties.Time).build().list();
        if (list != null && list.size() > 0) {
            for (SearchResult searchResult : list) {
                lastSearches.add(searchResult.getContent());
            }
        }
    }

    @Override
    protected void initView() {
        searchBar.setLastSuggestions(lastSearches);
        searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean b) {

            }

            @Override
            public void onSearchConfirmed(CharSequence charSequence) {

            }

            @Override
            public void onButtonClicked(int i) {
                Toast.makeText(mContext, i + " clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        List<String> list = searchBar.getLastSuggestions();
        List<SearchResult> searchResults;
        searchResultDao.deleteAll();
        for (int i = list.size() - 1; i >= 0; i--) {
//            searchResults = searchResultDao.queryBuilder().where(SearchResultDao.Properties.Content.eq(list.get(imageView))).build().list();
//            if (searchResults != null && searchResults.size() > 0) {
//                SearchResult result = searchResults.get(0);
//                result.setTime(new Date());
//                searchResultDao.update(result);
//            } else {
//                SearchResult result = new SearchResult(null, list.get(imageView), new Date());
//                searchResultDao.insert(result);
//            }
            SearchResult result = new SearchResult(null, list.get(i), new Date());
            searchResultDao.insert(result);
        }
    }
}
