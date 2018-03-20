package com.cb.qiangqiang2.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cb.qiangqiang2.R;
import com.cb.qiangqiang2.common.base.BaseActivity;
import com.cb.qiangqiang2.common.base.BaseFragment;
import com.cb.qiangqiang2.common.constant.Constants;
import com.cb.qiangqiang2.common.util.AppUtils;
import com.cb.qiangqiang2.data.UserManager;
import com.cb.qiangqiang2.data.model.BoardBean;
import com.cb.qiangqiang2.event.BoardChangeEvent;
import com.cb.qiangqiang2.event.OpenDrawLayoutEvent;
import com.cb.qiangqiang2.event.ShowExitSnackBarEvent;
import com.cb.qiangqiang2.event.SignResultEvent;
import com.cb.qiangqiang2.mvpview.BoardMvpView;
import com.cb.qiangqiang2.mvpview.CheckInMvpView;
import com.cb.qiangqiang2.presenter.BoardPresenter;
import com.cb.qiangqiang2.presenter.SignPresenter;
import com.cb.qiangqiang2.ui.activity.BoardDragEditActivity;
import com.cb.qiangqiang2.ui.activity.LoginActivity;
import com.cb.qiangqiang2.ui.activity.SearchActivity;
import com.cb.qiangqiang2.ui.activity.WebViewActivity;
import com.cb.qiangqiang2.ui.view.CustomFragmentStatePagerAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.cb.qiangqiang2.common.constant.Constants.POST_ALL;
import static com.cb.qiangqiang2.common.constant.Constants.POST_NEW;

/**
 * 板块列表
 */
public class BoardFragment extends BaseFragment implements BoardMvpView, CheckInMvpView {

    @Inject
    UserManager mUserManager;
    @Inject
    BoardPresenter boardPresenter;
    @Inject
    SignPresenter mSignPresenter;

    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.floating_action_button)
    FloatingActionButton floatingActionButton;

    private List<BoardBean> lists;
    private List<PostFragment> fragments;
    private CustomFragmentStatePagerAdapter adapter;

    public BoardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_board, container, false);
        ButterKnife.bind(this, view);
        ((BaseActivity) getActivity()).getActivityComponent().inject(this);
        boardPresenter.attachView(this);
        mSignPresenter.attachView(this);
        initView();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        if (mUserManager.isTodaySignSuccess()) {
            menu.findItem(R.id.menu_check_in).setTitle(R.string.main_menu_has_check_in);
        } else {
            menu.findItem(R.id.menu_check_in).setTitle(R.string.main_menu_check_in);
        }
        super.onPrepareOptionsMenu(menu);
    }

    private void initView() {
        EventBus.getDefault().register(this);

        mToolbar.setTitle("");
        ((BaseActivity) getActivity()).setSupportActionBar(mToolbar);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    //签到
                    case R.id.menu_check_in:
                        if (!mUserManager.isTodaySignSuccess()) {
                            mSignPresenter.sign();
                        } else {
                            if (getActivity() != null) {
                                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                                intent.putExtra(WebViewActivity.TITLE, "签到信息");
                                intent.putExtra(WebViewActivity.URL, Constants.SIGN_H5);
                                getActivity().startActivity(intent);
                            }
                        }
                        return true;
                    //搜索
                    case R.id.menu_search:
                        getActivity().startActivity(new Intent(getActivity(), SearchActivity.class));
                        return true;
                    default:
                        return false;
                }
            }
        });
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new OpenDrawLayoutEvent());
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //回到帖子列表顶部
                if (adapter != null && adapter.getCurrentFragment() != null
                        && adapter.getCurrentFragment() instanceof PostFragment){
                    ((PostFragment)adapter.getCurrentFragment()).scrollToTop();
                }
            }
        });

        lists = new ArrayList<>();
        List<BoardBean> selectData = mUserManager.getSelectBoardListFromLocal();
        if (selectData != null) {
            lists = selectData;
            initViewPager();
        } else {
            boardPresenter.loadBoardData();
        }

        if (mUserManager.isAutoSign() && !mUserManager.isTodaySignSuccess()) {
            //自动签到
            mSignPresenter.sign();
        }
    }

    private void initViewPager() {
        initViewPagerData();
        adapter = new CustomFragmentStatePagerAdapter(getChildFragmentManager()) {

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
                return lists.get(position).getName();
            }
        };
        mViewPager.setAdapter(adapter);
//        mViewPager.setOffscreenPageLimit(fragments.size() > 1 ? fragments.size() - 1 : 1);
        mTabLayout.setupWithViewPager(mViewPager);
        AppUtils.dynamicSetTabLayoutMode(mTabLayout, getActivity());
    }

    private void initViewPagerData() {
        fragments = new ArrayList<>();
        BoardBean boardBean = new BoardBean();
        boardBean.setId(0);
        boardBean.setName("最新");
        lists.add(0, boardBean);
        for (int i = 0; i < lists.size(); i++) {
            String sortBy;
            if (i == 0) {
                sortBy = POST_NEW;
            } else {
                sortBy = POST_ALL;
            }
            fragments.add(PostFragment.newInstance(lists.get(i).getId(), sortBy));
        }
    }

    @OnClick({R.id.iv_edit_board})
    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_edit_board:
                startActivity(new Intent(getActivity(), BoardDragEditActivity.class));
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        boardPresenter.detachView();
        mSignPresenter.detachView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void loadBoardData(List<BoardBean> list) {
        if (list == null) return;
        lists.clear();
        lists.addAll(list);
        initViewPager();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void loadError(Throwable e) {
        e.printStackTrace();
        Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void checkInResult(String string) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(BoardChangeEvent event) {
        List<BoardBean> selectData = mUserManager.getSelectBoardListFromLocal();
        if (selectData != null) {
            lists.clear();
            lists.addAll(selectData);
            initViewPager();
        } else {
            boardPresenter.loadBoardData();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onShowExitSnackBar(ShowExitSnackBarEvent event) {
        Snackbar.make(mCoordinatorLayout, getString(R.string.exit), Snackbar.LENGTH_SHORT).show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSignResult(SignResultEvent event) {
        if (getActivity() == null) {
            return;
        }
        if (event.getType() == SignResultEvent.TYPE_SIGN_SUCESS) {
            getActivity().invalidateOptionsMenu();
        } else if (event.getType() == SignResultEvent.TYPE_UN_LOGIN) {
            LoginActivity.startLoginActivity(getActivity(), true);
            getActivity().finish();
        }
    }
}
