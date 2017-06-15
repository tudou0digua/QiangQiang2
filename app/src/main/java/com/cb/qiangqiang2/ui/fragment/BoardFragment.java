package com.cb.qiangqiang2.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
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
import com.cb.qiangqiang2.common.util.PrefUtils;
import com.cb.qiangqiang2.data.model.BoardBean;
import com.cb.qiangqiang2.data.model.BoardModel;
import com.cb.qiangqiang2.event.BoardChangeEvent;
import com.cb.qiangqiang2.event.OpenDrawLayoutEvent;
import com.cb.qiangqiang2.event.ShowExitSnackBarEvent;
import com.cb.qiangqiang2.mvpview.BoardMvpView;
import com.cb.qiangqiang2.mvpview.CheckInMvpView;
import com.cb.qiangqiang2.presenter.BoardPresenter;
import com.cb.qiangqiang2.presenter.CheckInPresenter;
import com.cb.qiangqiang2.ui.activity.BoardDragEditActivity;
import com.cb.qiangqiang2.ui.activity.SearchActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;

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
    BoardPresenter boardPresenter;
    @Inject
    CheckInPresenter mCheckInPresenter;

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
        mCheckInPresenter.attachView(this);
        initView();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
    }

    private void initView() {
        mToolbar.setTitle("");
        ((BaseActivity) getActivity()).setSupportActionBar(mToolbar);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    //签到
                    case R.id.menu_check_in:
                        mCheckInPresenter.checkIn();
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
                fragments.get(mViewPager.getCurrentItem()).scrollToTop();
            }
        });

        lists = new ArrayList<>();
        String boardSelectedStr = PrefUtils.getString(getActivity(), Constants.BOARD_LIST_SELECTED);
        if (boardSelectedStr != null) {
            Gson gson = new Gson();
            List<BoardBean> listSelected = gson.fromJson(boardSelectedStr, new TypeToken<List<BoardBean>>() {
            }.getType());
            lists = listSelected;
            setViewPager();
        } else {
            boardPresenter.loadBoardData();
        }

    }

    private void setViewPager() {
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
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getChildFragmentManager()) {
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
        mTabLayout.setupWithViewPager(mViewPager);
        AppUtils.dynamicSetTabLayoutMode(mTabLayout, getActivity());
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
        mCheckInPresenter.detachView();
    }

    @Override
    public void loadBoardData(BoardModel boardModel) {
        if (boardModel.getList() == null) return;
        lists.clear();
        try {
            for (int i = 0; i < boardModel.getList().size(); i++) {
                BoardModel.ListBean listBean = boardModel.getList().get(i);
                int categoryId = listBean.getBoard_category_id();
                String categoryName = listBean.getBoard_category_name();
                List<BoardModel.ListBean.BoardListBean> boardListBeen = listBean.getBoard_list();
                if (boardListBeen == null) continue;
                for (int j = 0; j < boardListBeen.size(); j++) {
                    BoardModel.ListBean.BoardListBean item = boardListBeen.get(j);
                    BoardBean bean = new BoardBean();
                    bean.setCategoryId(categoryId);
                    bean.setCategory(categoryName);
                    bean.setId(item.getBoard_id());
                    bean.setName(item.getBoard_name());
                    bean.setDescription(item.getDescription());
                    bean.setFavNum(item.getFavNum());
                    bean.setImage(item.getBoard_img());
                    bean.setLastPostsDate(item.getLast_posts_date());
                    bean.setPostsTotalNum(item.getPosts_total_num());
                    bean.setTodayPostsNum(item.getTd_posts_num());
                    bean.setTopicTotalNum(item.getTopic_total_num());
                    lists.add(bean);
                }
            }
            Gson gson = new Gson();
            String data = gson.toJson(lists);
            PrefUtils.putString(getActivity(), Constants.BOARD_LIST, data);
            Logger.json(PrefUtils.getString(getActivity(), Constants.BOARD_LIST));

            setViewPager();
        } catch (Exception e) {
            e.printStackTrace();
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
        e.printStackTrace();
        Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void checkInResult(String string) {

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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(BoardChangeEvent event) {
        String boardSelectedStr = PrefUtils.getString(getActivity(), Constants.BOARD_LIST_SELECTED);
        if (boardSelectedStr != null) {
            Gson gson = new Gson();
            List<BoardBean> listSelected = gson.fromJson(boardSelectedStr, new TypeToken<List<BoardBean>>() {
            }.getType());
            lists = listSelected;
            setViewPager();
        } else {
            boardPresenter.loadBoardData();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onShowExitSnackBar(ShowExitSnackBarEvent event) {
        Snackbar.make(mCoordinatorLayout, getString(R.string.exit), Snackbar.LENGTH_SHORT).show();
    }
}
