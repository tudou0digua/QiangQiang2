package com.cb.qiangqiang2.ui.activity;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cb.qiangqiang2.R;
import com.cb.qiangqiang2.common.base.BaseSwipeBackActivity;
import com.cb.qiangqiang2.common.util.AppUtils;
import com.cb.qiangqiang2.data.db.greendao.bean.SearchHistory;
import com.cb.qiangqiang2.data.db.greendao.daohelper.SearchHistoryDaoHelper;
import com.cb.qiangqiang2.event.HideSearchHistoryEvent;
import com.cb.qiangqiang2.event.SearchEvent;
import com.cb.qiangqiang2.ui.adapter.SearchHistoryAdapter;
import com.cb.qiangqiang2.ui.adapter.listener.OnItemClickListener;
import com.cb.qiangqiang2.ui.fragment.SearchPostFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchActivity extends BaseSwipeBackActivity {

    @Inject
    SearchHistoryDaoHelper searchHistoryDaoHelper;
    @Inject
    SearchHistoryAdapter searchHistoryAdapter;

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
    @BindView(R.id.card_view)
    CardView cardView;

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
        titles = new ArrayList<>();
        titles.add("帖子");
        titles.add("用户");
        fragments = new ArrayList<>();
        fragments.add(new SearchPostFragment());
        fragments.add(new SearchPostFragment());
    }

    @Override
    protected void initView() {
        //设置搜索历史列表
        rvHistory.setLayoutManager(new LinearLayoutManager(mContext));
        rvHistory.setItemAnimator(new DefaultItemAnimator());
        rvHistory.setAdapter(searchHistoryAdapter);
        searchHistoryAdapter.setOnItemClickListener(new OnItemClickListener<SearchHistory>() {
            @Override
            public void onItemClick(int position, View view, SearchHistory searchHistory) {
                if (view.getId() == R.id.iv_delete) {
                    setRvHistoryHeight(searchHistoryAdapter.getLists().size() - 1);
                    searchHistoryAdapter.deleteSearchHistory(searchHistory);
                    searchHistoryDaoHelper.deleteSearchHistory(searchHistory);
                } else {
                    editText.setText(searchHistory.getContent());
                    cardView.requestFocus();
                    AppUtils.closeSoftKeybord(editText, mContext);
                    dismissHistoryRecycleView();
                    search(searchHistory.getContent());
                    searchHistoryDaoHelper.addSearchHistory(searchHistory);
                }
            }
        });
        //设置EditText
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
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    cardView.requestFocus();
                    AppUtils.closeSoftKeybord(editText, mContext);
                    dismissHistoryRecycleView();
                    String searchContent = editText.getText().toString().trim();
                    search(searchContent);
                    searchHistoryDaoHelper.addSearchHistory(searchContent);
                    return true;
                }
                return false;
            }
        });
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    String str = editText.getText().toString().trim();
                    if (TextUtils.isEmpty(str)) {
                        ivClear.setVisibility(View.GONE);
                    } else {
                        ivClear.setVisibility(View.VISIBLE);
                    }
                    showHistoryRecycleView();
                }
            }
        });
        editText.postDelayed(new Runnable() {
            @Override
            public void run() {
                editText.requestFocus();
                AppUtils.showSoftKeyboard(editText, mContext);
            }
        }, 300);
        //设置viewpager And TabLayout
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

    private void setSearchHistoryData() {
        List<SearchHistory> list = searchHistoryDaoHelper.queryAll();
        if (list != null) {
            setRvHistoryHeight(list.size());
            searchHistoryAdapter.setData(list);
        }
    }

    private void setRvHistoryHeight(int size) {
        if (size >= 5) {
            rvHistory.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen.search_history_item_height) * 5;
        } else {
            rvHistory.getLayoutParams().height = RecyclerView.LayoutParams.WRAP_CONTENT;
        }
    }

    private void dismissHistoryRecycleView() {
        ivClear.setVisibility(View.GONE);
        int from = rvHistory.getHeight();
        int to = 0;
        rvHistoryAnimator(from, to);
    }

    private void showHistoryRecycleView() {
        setSearchHistoryData();
        int size = searchHistoryAdapter.getLists().size();
        if (size == 0) return;
        int from = 0;
        int to;
        if (size >= 5) {
            to = getResources().getDimensionPixelOffset(R.dimen.search_history_item_height) * 5;
        } else {
            to = getResources().getDimensionPixelOffset(R.dimen.search_history_item_height) * size;
        }
        rvHistoryAnimator(from, to);
    }

    private void rvHistoryAnimator(int from, int to) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(new int[]{from, to});
        valueAnimator.setDuration(300);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        final ViewGroup.LayoutParams layoutParams = rvHistory.getLayoutParams();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                layoutParams.height = (Integer) valueAnimator.getAnimatedValue();
                rvHistory.setLayoutParams(layoutParams);
            }
        });
        if (searchHistoryAdapter.getLists().size() > 0) {
            valueAnimator.start();
        }
    }

    private void search(String searchContent) {
        EventBus.getDefault().post(new SearchEvent(searchContent));
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(HideSearchHistoryEvent event) {
        cardView.requestFocus();
        AppUtils.closeSoftKeybord(editText, mContext);
        dismissHistoryRecycleView();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

}
