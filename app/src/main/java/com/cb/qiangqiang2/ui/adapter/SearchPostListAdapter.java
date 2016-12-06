package com.cb.qiangqiang2.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cb.qiangqiang2.R;
import com.cb.qiangqiang2.common.dagger.qualifier.ForActivity;
import com.cb.qiangqiang2.common.util.DateUtil;
import com.cb.qiangqiang2.data.model.SearchPostResultModel;
import com.cb.qiangqiang2.ui.adapter.listener.OnItemClickListener;
import com.cb.qiangqiang2.ui.adapter.listener.OnItemLongClickListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cb on 2016/12/3.
 */

public class SearchPostListAdapter extends RecyclerView.Adapter {
    @Inject
    @ForActivity
    Context mContext;

    private List<SearchPostResultModel.ListBean> mLists;
    private OnItemClickListener<SearchPostResultModel.ListBean> mOnItemClickListener;
    private OnItemLongClickListener<SearchPostResultModel.ListBean> mOnItemLongClickListener;

    @Inject
    public SearchPostListAdapter() {
        mLists = new ArrayList<>();
    }

    public void setData(@NonNull SearchPostResultModel model) {
        mLists.clear();
        List<SearchPostResultModel.ListBean> lists = model.getList();
        if (lists != null) {
            mLists.addAll(lists);
        }
        notifyDataSetChanged();
    }

    public void addData(@NonNull SearchPostResultModel model) {
        List<SearchPostResultModel.ListBean> lists = model.getList();
        if (lists == null || lists.size() <= 0) return;
        mLists.addAll(lists);
        notifyItemRangeChanged(mLists.size(), lists.size());
    }

    public void setOnItemClickListener(OnItemClickListener<SearchPostResultModel.ListBean> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener<SearchPostResultModel.ListBean> onItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_search_post, parent, false);
        return new SearchPostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final SearchPostViewHolder viewHolder = (SearchPostViewHolder) holder;
        final SearchPostResultModel.ListBean bean = mLists.get(position);
        viewHolder.mTvTitle.setText(bean.getTitle());
        viewHolder.mTvTitleDetail.setText(bean.getSubject());
        viewHolder.mTvLastReply.setText(DateUtil.getPassedTime(bean.getLast_reply_date()));
        viewHolder.mTvRead.setText(String.valueOf(bean.getHits()));
        viewHolder.mTvComment.setText(String.valueOf(bean.getReplies()));
        viewHolder.mTvAuthor.setText(bean.getUser_nick_name());

        if (mOnItemClickListener != null) {
            viewHolder.mLlPostItemContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(position, viewHolder.mLlPostItemContainer, bean);
                }
            });
        }

        if (mOnItemLongClickListener != null) {
            viewHolder.mLlPostItemContainer.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mOnItemLongClickListener.onItemLongClick(position, viewHolder.mLlPostItemContainer, bean);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mLists.size();
    }

    public class SearchPostViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_title_detail)
        TextView mTvTitleDetail;
        @BindView(R.id.tv_author)
        TextView mTvAuthor;
        @BindView(R.id.tv_last_reply)
        TextView mTvLastReply;
        @BindView(R.id.tv_comment)
        TextView mTvComment;
        @BindView(R.id.tv_read)
        TextView mTvRead;
        @BindView(R.id.ll_post_item_container)
        LinearLayout mLlPostItemContainer;

        public SearchPostViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
