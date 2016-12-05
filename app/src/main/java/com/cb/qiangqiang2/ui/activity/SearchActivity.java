package com.cb.qiangqiang2.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cb.qiangqiang2.R;
import com.cb.qiangqiang2.common.base.BaseSwipeBackActivity;
import com.cb.qiangqiang2.data.db.DbManager;
import com.cb.qiangqiang2.data.db.greendao.bean.SearchResult;
import com.cb.qiangqiang2.data.db.greendao.gen.SearchResultDao;
import com.cb.qiangqiang2.event.SearchEvent;
import com.cb.qiangqiang2.ui.fragment.SearchPostFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchActivity extends BaseSwipeBackActivity {

    @Inject
    DbManager dbManager;

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.edit_text)
    EditText editText;
    @BindView(R.id.iv_clear)
    ImageView ivClear;
    @BindView(R.id.rv_history)
    RecyclerView rvHistory;

    private List<String> lastSearches;
    private SearchResultDao searchResultDao;
    private List<String> titles;
    private List<Fragment> fragments;

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
        titles = new ArrayList<>();
        titles.add("帖子");
        titles.add("用户");
        fragments = new ArrayList<>();
        fragments.add(new SearchPostFragment());
        fragments.add(new SearchPostFragment());
    }

    @Override
    protected void initView() {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String str = editable.toString();
                if (TextUtils.isEmpty(str)) {
                    ivClear.setVisibility(View.GONE);
                } else {
                    ivClear.setVisibility(View.VISIBLE);
                }
            }
        });
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH){
                    EventBus.getDefault().post(new SearchEvent(editText.getText().toString().trim()));
                    return true;
                }
                return false;
            }
        });

        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles.get(position);
            }
        };

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @OnClick({R.id.iv_clear, R.id.iv_back})
    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_clear:
                editText.setText("");
                break;
            case R.id.iv_back:
                finishActivity();
                break;
        }
    }

    private void finishActivity() {
        finish();
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onMessageEvent(SearchEvent event) {
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        EventBus.getDefault().register(this);
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        EventBus.getDefault().unregister(this);
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        List<String> list = searchBar.getLastSuggestions();
//        List<SearchResult> searchResults;
//        searchResultDao.deleteAll();
//        for (int i = list.size() - 1; i >= 0; i--) {
//            searchResults = searchResultDao.queryBuilder().where(SearchResultDao.Properties.Content.eq(list.get(imageView))).build().list();
//            if (searchResults != null && searchResults.size() > 0) {
//                SearchResult result = searchResults.get(0);
//                result.setTime(new Date());
//                searchResultDao.update(result);
//            } else {
//                SearchResult result = new SearchResult(null, list.get(imageView), new Date());
//                searchResultDao.insert(result);
//            }
//            SearchResult result = new SearchResult(null, list.get(i), new Date());
//            searchResultDao.insert(result);
//        }
    }


}
