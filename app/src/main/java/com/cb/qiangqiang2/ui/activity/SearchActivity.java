package com.cb.qiangqiang2.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.cb.qiangqiang2.R;
import com.cb.qiangqiang2.common.base.BaseSwipeBackActivity;
import com.cb.qiangqiang2.common.util.AppUtils;
import com.cb.qiangqiang2.data.db.DbManager;
import com.cb.qiangqiang2.data.db.greendao.bean.SearchResult;
import com.cb.qiangqiang2.data.db.greendao.gen.SearchResultDao;
import com.cb.qiangqiang2.data.model.SearchPostResultModel;
import com.cb.qiangqiang2.mvpview.SearchPostMvpView;
import com.cb.qiangqiang2.presenter.SearchPostPresenter;
import com.cb.qiangqiang2.ui.adapter.SearchPostListAdapter;
import com.cb.qiangqiang2.ui.adapter.listener.OnItemClickListener;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class SearchActivity extends BaseSwipeBackActivity implements SearchPostMvpView {

    @Inject
    SearchPostPresenter searchPostPresenter;
    @Inject
    DbManager dbManager;
    @Inject
    SearchPostListAdapter mAdapter;

    @BindView(R.id.search_bar)
    MaterialSearchBar searchBar;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    @BindView(R.id.progress_bar)
    MaterialProgressBar mProgressBar;
    @BindView(R.id.progress_bar_search)
    MaterialProgressBar mProgressBarSearch;

    private List<String> lastSearches;
    private SearchResultDao searchResultDao;
    private boolean canLoadingMore = false;
    private int currentPage = 1;

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
    protected void attachPresenter() {
        searchPostPresenter.attachView(this);
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
        mAdapter.setOnItemClickListener(new OnItemClickListener<SearchPostResultModel.ListBean>() {
            @Override
            public void onItemClick(int position, View view, SearchPostResultModel.ListBean listBean) {
                String url =
                        "http://www.qiangqiang5.com/api/mobile/index.php?module=%s&page=%s&charset=%s&image=%s&ppp=%s&debug=%s&tid=%s&mobile=%s&version=%s";
                url = String.format(url,
                        "viewthread",
                        "1",
                        "utf-8",
                        "1",
                        "10",
                        "1",
                        String.valueOf(listBean.getTopic_id()),
                        "no",
                        "3");
                Intent intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra(WebViewActivity.TITLE, String.valueOf(listBean.getBoard_id()));
                intent.putExtra(WebViewActivity.URL, url);
                mContext.startActivity(intent);
            }
        });
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
                currentPage = 1;
                searchPostPresenter.searchPost(String.valueOf(charSequence), currentPage, 10);
                mProgressBarSearch.setVisibility(View.VISIBLE);
                canLoadingMore = false;
            }

            @Override
            public void onButtonClicked(int i) {
                Toast.makeText(mContext, i + " clicked", Toast.LENGTH_SHORT).show();
            }
        });

        recycleView.setLayoutManager(new LinearLayoutManager(mContext));
        recycleView.setItemAnimator(new DefaultItemAnimator());
        recycleView.setAdapter(mAdapter);
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                boolean isScrollToBottom = AppUtils.isScrollToBottom(recyclerView);
                if (canLoadingMore && isScrollToBottom) {
                    currentPage++;
                    canLoadingMore = false;
                    searchPostPresenter.searchPost(searchBar.getText(), currentPage, 10);
                    mProgressBar.setVisibility(View.VISIBLE);
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        searchPostPresenter.detachView();
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

    @Override
    public void searchPostResult(SearchPostResultModel model) {
        if (currentPage == 1) {
            mProgressBarSearch.setVisibility(View.GONE);
            canLoadingMore = true;
        } else {
            mProgressBar.setVisibility(View.GONE);
            if (model.getHas_next() == 1) {
                canLoadingMore = true;
            } else {
                canLoadingMore = false;
                Toast.makeText(mContext, "到底了。。。", Toast.LENGTH_SHORT).show();
            }
        }
        if (currentPage == 1) {
            mAdapter.setData(model);
        } else {
            mAdapter.addData(model);
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void loadError(Throwable e) {

    }
}
