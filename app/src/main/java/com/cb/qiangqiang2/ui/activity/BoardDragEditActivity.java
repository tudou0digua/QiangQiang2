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
import com.cb.qiangqiang2.adapter.BoardEditAdapter;
import com.cb.qiangqiang2.common.base.BaseSwipeBackActivity;
import com.cb.qiangqiang2.common.constant.Constants;
import com.cb.qiangqiang2.common.util.PrefUtils;
import com.cb.qiangqiang2.data.model.BoardBean;
import com.cb.qiangqiang2.event.BoardChangeEvent;
import com.cb.qiangqiang2.ui.view.dragview.ItemDragHelperCallback;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

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

    private List<BoardBean> mSelectedListOriginal;
    private List<BoardBean> mSelectedListData;
    private List<BoardBean> mUnSelectedListData;
    private BoardEditAdapter adapter;
    private ItemTouchHelper itemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void injectActivity() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initView() {
        initToolbar();
        //获得板块列表数据
        mSelectedListOriginal = new ArrayList<>();
        String boardSelectedStr = PrefUtils.getString(mContext, Constants.BOARD_LIST_SELECTED);
        if (boardSelectedStr != null) {
            Gson gson = new Gson();
            List<BoardBean> listSelected = gson.fromJson(boardSelectedStr, new TypeToken<List<BoardBean>>(){}.getType());
            mSelectedListData = listSelected;
            mSelectedListOriginal.addAll(mSelectedListData);
        } else {
            String boards = PrefUtils.getString(mContext, Constants.BOARD_LIST);
            if (TextUtils.isEmpty(boards)) return;
            Gson gson = new Gson();
            mSelectedListData = gson.fromJson(boards, new TypeToken<List<BoardBean>>(){}.getType());
            mSelectedListOriginal.addAll(mSelectedListData);
        }
        String boardUnSelectedStr = PrefUtils.getString(mContext, Constants.BOARD_LIST_UNSELETED);
        if (boardUnSelectedStr != null) {
            Gson gson = new Gson();
            List<BoardBean> listUnSelected = gson.fromJson(boardUnSelectedStr, new TypeToken<List<BoardBean>>(){}.getType());
            mUnSelectedListData = listUnSelected;
        } else {
            mUnSelectedListData = new ArrayList<>();
        }
        if (mSelectedListData == null) return;

        GridLayoutManager manager = new GridLayoutManager(this, 3);
        mRecycleView.setLayoutManager(manager);

        ItemDragHelperCallback itemDragHelperCallback = new ItemDragHelperCallback();
        itemTouchHelper = new ItemTouchHelper(itemDragHelperCallback);
        itemTouchHelper.attachToRecyclerView(mRecycleView);

        adapter = new BoardEditAdapter(this, itemTouchHelper, mSelectedListData, mUnSelectedListData);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int viewType = adapter.getItemViewType(position);
                return viewType == BoardEditAdapter.TYPE_MY || viewType == BoardEditAdapter.TYPE_OTHER ? 1 : 3;
            }
        });
        mRecycleView.setAdapter(adapter);

        adapter.setOnMyBoardItemClickListener(new BoardEditAdapter.OnMyBoardItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Toast.makeText(BoardDragEditActivity.this, mSelectedListData.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initToolbar() {
        mToolbar.setTitle(R.string.title_activity_board_edit);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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

    @Override
    public void finish() {
        setBoardDataChange();
        super.finish();
    }

    private void setBoardDataChange() {
        if (hasBoardDataChanged()) {
            Gson gson = new Gson();
            PrefUtils.putString(mContext, Constants.BOARD_LIST_SELECTED, gson.toJson(adapter.getSelectedBoardItems()));
            PrefUtils.putString(mContext, Constants.BOARD_LIST_UNSELETED, gson.toJson(adapter.getOtherBoardItems()));
            EventBus.getDefault().post(new BoardChangeEvent());
        }
    }

    private boolean hasBoardDataChanged() {
        if (mSelectedListOriginal.size() == mSelectedListData.size()) {
            for (int i = 0; i < mSelectedListOriginal.size(); i++) {
                BoardBean originalBoardBean = mSelectedListOriginal.get(i);
                BoardBean modifyBoardBean = mSelectedListData.get(i);
                if (originalBoardBean.getId() == modifyBoardBean.getId()) {
                    if (i == mSelectedListOriginal.size() - 1) {
                        return false;
                    }
                } else {
                    return true;
                }
            }
        }
        return true;
    }
}
