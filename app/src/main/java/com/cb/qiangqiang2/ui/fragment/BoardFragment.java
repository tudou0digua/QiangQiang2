package com.cb.qiangqiang2.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cb.qiangqiang2.R;
import com.cb.qiangqiang2.common.base.BaseActivity;
import com.cb.qiangqiang2.common.base.BaseFragment;
import com.cb.qiangqiang2.common.constant.Constants;
import com.cb.qiangqiang2.common.event.BoardChangeEvent;
import com.cb.qiangqiang2.common.util.AppUtils;
import com.cb.qiangqiang2.common.util.PrefUtils;
import com.cb.qiangqiang2.data.model.BoardBean;
import com.cb.qiangqiang2.data.model.BoardModel;
import com.cb.qiangqiang2.mvpview.BoardMvpView;
import com.cb.qiangqiang2.mvpview.CheckInMvpView;
import com.cb.qiangqiang2.presenter.BoardPresenter;
import com.cb.qiangqiang2.presenter.CheckInPresenter;
import com.cb.qiangqiang2.ui.activity.BoardDragEditActivity;
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

/**
 * 板块列表
 */
public class BoardFragment extends BaseFragment implements BoardMvpView, CheckInMvpView {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @Inject
    BoardPresenter boardPresenter;
    @Inject
    CheckInPresenter mCheckInPresenter;

    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;

    private String mParam1;
    private String mParam2;
    private List<BoardBean> lists;
    private List<Fragment> fragments;


    public BoardFragment() {
        // Required empty public constructor
    }

    public static BoardFragment newInstance(String param1, String param2) {
        BoardFragment fragment = new BoardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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

    private void initView() {
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
        for (BoardBean list : lists) {
            fragments.add(PostFragment.newInstance(list.getId(), POST_ALL));
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

    @OnClick({R.id.iv_edit_board, R.id.tv_checkIn})
    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_edit_board:
                startActivity(new Intent(getActivity(), BoardDragEditActivity.class));
                break;
            case R.id.tv_checkIn:
                mCheckInPresenter.checkIn();
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
}
