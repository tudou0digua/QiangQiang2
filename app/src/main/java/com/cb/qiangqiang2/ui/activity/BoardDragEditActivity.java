package com.cb.qiangqiang2.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cb.qiangqiang2.R;
import com.cb.qiangqiang2.common.base.BaseSwipeBackActivity;
import com.cb.qiangqiang2.common.constant.Constants;
import com.cb.qiangqiang2.common.util.PrefUtils;
import com.cb.qiangqiang2.data.model.BoardBean;
import com.cb.qiangqiang2.ui.view.dragview.ItemDragHelperCallback;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class BoardDragEditActivity extends BaseSwipeBackActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rl_top)
    RelativeLayout mRlTop;
    @BindView(R.id.recycle_view)
    RecyclerView mRecycleView;
    @BindView(R.id.activity_board_drag_edit)
    RelativeLayout mActivityBoardDragEdit;

    private List<BoardBean> mListData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        initView();
    }

    private void initView() {
        //获得板块列表数据
        String boards = PrefUtils.getString(mContext, Constants.BOARD_LIST);
        if (TextUtils.isEmpty(boards)) return;
        Gson gson = new Gson();
        mListData = gson.fromJson(boards, new TypeToken<List<BoardBean>>(){}.getType());
        if (mListData == null || mListData.size() < 1) return;

        GridLayoutManager manager = new GridLayoutManager(this, 3);
        mRecycleView.setLayoutManager(manager);

        ItemDragHelperCallback callback = new ItemDragHelperCallback();
        final ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(mRecycleView);

        final BoardEditAdapter adapter = new BoardEditAdapter(this, helper, mListData, new ArrayList<BoardBean>());
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int viewType = adapter.getItemViewType(position);
                return viewType == BoardEditAdapter.TYPE_MY || viewType == BoardEditAdapter.TYPE_OTHER ? 1 : 3;
            }
        });
        mRecycleView.setAdapter(adapter);

        adapter.setOnMyChannelItemClickListener(new BoardEditAdapter.OnMyChannelItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Toast.makeText(BoardDragEditActivity.this, mListData.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_board_drag_edit;
    }

    @OnClick({})
    public void onClicked(View view) {
        switch (view.getId()) {

        }
    }
}
