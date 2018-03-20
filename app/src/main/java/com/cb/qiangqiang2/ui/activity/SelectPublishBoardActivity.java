package com.cb.qiangqiang2.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.cb.qiangqiang2.R;
import com.cb.qiangqiang2.adapter.SelectPublishBoardAdapter;
import com.cb.qiangqiang2.adapter.listener.OnItemClickListener;
import com.cb.qiangqiang2.common.base.BaseSwipeBackActivity;
import com.cb.qiangqiang2.common.constant.Constants;
import com.cb.qiangqiang2.common.util.PrefUtils;
import com.cb.qiangqiang2.data.UserManager;
import com.cb.qiangqiang2.data.model.BoardBean;
import com.cb.qiangqiang2.mvpview.BoardMvpView;
import com.cb.qiangqiang2.presenter.BoardPresenter;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class SelectPublishBoardActivity extends BaseSwipeBackActivity implements BoardMvpView{

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;

    @Inject
    SelectPublishBoardAdapter adapter;
    @Inject
    UserManager mUserManager;
    @Inject
    BoardPresenter boardPresenter;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SelectPublishBoardActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void injectActivity() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void attachPresenter() {
        boardPresenter.attachView(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_publish_board;
    }

    @Override
    protected void initView() {
        mToolbar.setTitle("板块选择");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        adapter.setListener(new OnItemClickListener<BoardBean>() {
            @Override
            public void onItemClick(int position, View view, BoardBean boardBean) {
                Intent intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra(WebViewActivity.TITLE, "发表帖子--" + boardBean.getName());
                intent.putExtra(WebViewActivity.URL, String.format(Constants.PUBLISH_POST, boardBean.getId()));
                mContext.startActivity(intent);
            }
        });

        String boards = PrefUtils.getString(mContext, Constants.BOARD_LIST);
        if (!TextUtils.isEmpty(boards)) {
            try {
                Gson gson = new Gson();
                List<BoardBean> selectData = gson.fromJson(boards, new TypeToken<List<BoardBean>>(){}.getType());
                if (selectData != null && selectData.size() > 0) {
                    adapter.setData(selectData);
                } else {
                    boardPresenter.loadBoardData();
                }
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
                boardPresenter.loadBoardData();
            }
        } else {
            boardPresenter.loadBoardData();
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
        if (e == null) return;
        e.printStackTrace();
        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadBoardData(List<BoardBean> list) {
        adapter.setData(list);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        boardPresenter.detachView();
    }
}
