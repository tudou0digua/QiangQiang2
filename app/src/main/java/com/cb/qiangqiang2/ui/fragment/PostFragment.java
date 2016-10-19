package com.cb.qiangqiang2.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cb.qiangqiang2.R;
import com.cb.qiangqiang2.common.base.BaseActivity;
import com.cb.qiangqiang2.common.base.BaseFragment;
import com.cb.qiangqiang2.data.api.ApiService;
import com.cb.qiangqiang2.data.api.HttpManager;
import com.cb.qiangqiang2.data.model.PostModel;
import com.cb.qiangqiang2.ui.adapter.PostListAdapter;
import com.cb.qiangqiang2.ui.view.SuperSwipeRefreshLayout;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;

/**
 * 帖子列表Fragment
 */
public class PostFragment extends BaseFragment {
    private static final String TYPE = "type";
    public static final int HOME_POST = 0;
    public static final int NORMAL_POST = 1;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rl_top)
    RelativeLayout mRlTop;
    @BindView(R.id.recycle_view)
    RecyclerView mRecycleView;
    @BindView(R.id.swipe_refresh_layout)
    SuperSwipeRefreshLayout mSwipeRefreshLayout;

    @Inject
    PostListAdapter mAdapter;
    @Inject
    ApiService apiService;

    private int type;

    public PostFragment() {
        // Required empty public constructor
    }

    public static PostFragment newInstance(int type) {
        PostFragment fragment = new PostFragment();
        Bundle args = new Bundle();
        args.putInt(TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getInt(TYPE);
        }
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post, container, false);
        ButterKnife.bind(this, view);
        ((BaseActivity) getActivity()).getActivityComponent().inject(this);
        initView();
        return view;
    }

    private void initView() {
        mSwipeRefreshLayout.setEnabled(false);

        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        mRecycleView.setAdapter(mAdapter);

        Map<String, String> map = HttpManager.getBaseMap(getActivity());

        HttpManager.toSubscribe(apiService.getTopicList(map), new Subscriber() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(Object o) {
                if (o != null) {
                    String gson = new Gson().toJson(o);
                    com.orhanobut.logger.Logger.json(gson);
                    PostModel postModel = (PostModel) o;
                    Logger.d(postModel.getList().get(0).getSubject());
                    mAdapter.setPostModel((PostModel) o);
                }
            }
        });
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}
