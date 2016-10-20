package com.cb.qiangqiang2.ui.fragment;


import android.content.Context;
import android.os.Bundle;
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
import com.cb.qiangqiang2.data.model.BoardBean;
import com.cb.qiangqiang2.data.model.BoardModel;
import com.cb.qiangqiang2.mvpview.BoardMvpView;
import com.cb.qiangqiang2.presenter.BoardPresenter;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 板块列表
 * A simple {@link Fragment} subclass.
 * Use the {@link BoardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BoardFragment extends BaseFragment implements BoardMvpView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @Inject
    BoardPresenter boardPresenter;
    @BindView(R.id.magic_indicator)
    MagicIndicator mMagicIndicator;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<BoardBean> lists;
    private List<Fragment> fragments;


    public BoardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BoardFragment.
     */
    // TODO: Rename and change types and number of parameters
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_board, container, false);
        ButterKnife.bind(this, view);
        ((BaseActivity) getActivity()).getActivityComponent().inject(this);
        boardPresenter.attachView(this);
        initView();
        return view;
    }

    private void initView() {
        lists = new ArrayList<>();
        boardPresenter.loadBoardData();


    }

    private void setIndicator() {
        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return lists == null ? 0 : lists.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(getResources().getColor(R.color.text_second_primary));
                colorTransitionPagerTitleView.setSelectedColor(getResources().getColor(R.color.colorPrimary));
                colorTransitionPagerTitleView.setText(lists.get(index).getName());
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setColors(getResources().getColor(R.color.colorPrimary));
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                return indicator;
            }
        });
        mMagicIndicator.setNavigator(commonNavigator);

        setViewPager();


    }

    private void setViewPager() {

        fragments = new ArrayList<>();
        for (BoardBean list : lists) {
            fragments.add(PostFragment.newInstance(list.getId(), "all"));
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
        };

        mViewPager.setAdapter(adapter);

        ViewPagerHelper.bind(mMagicIndicator, mViewPager);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        boardPresenter.detachView();
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
            Logger.json(gson.toJson(lists));
            setIndicator();
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
}
