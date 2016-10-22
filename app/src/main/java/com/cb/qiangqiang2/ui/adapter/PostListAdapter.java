package com.cb.qiangqiang2.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cb.qiangqiang2.R;
import com.cb.qiangqiang2.common.dagger.qualifier.ForActivity;
import com.cb.qiangqiang2.common.glide.GlideCircleTransform;
import com.cb.qiangqiang2.common.util.DateUtil;
import com.cb.qiangqiang2.data.model.PostModel;
import com.cb.qiangqiang2.ui.adapter.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cb on 2016/10/19.
 */

public class PostListAdapter extends RecyclerView.Adapter {

    @Inject
    @ForActivity
    Context mContext;

    private PostModel postModel;
    private List<PostModel.ListBean> mLists;
    private OnItemClickListener<PostModel.ListBean> mOnItemClickListener;

    @Inject
    public PostListAdapter() {

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setPostModel(PostModel postModel) {
        this.postModel = postModel;
        List<PostModel.ListBean> list = postModel.getList();
        if (list == null) {
            mLists = new ArrayList<>();
        } else {
            mLists = this.postModel.getList();
        }
        notifyDataSetChanged();
    }

    public void addData(PostModel postModel) {
        List<PostModel.ListBean> list = postModel.getList();
        if (list == null) return;
        int start = mLists.size();
        mLists.addAll(list);
        notifyItemRangeInserted(start, list.size());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.post_item, parent, false);
        return new PostListItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        PostListItemViewHolder viewHolder = (PostListItemViewHolder) holder;
        final PostModel.ListBean bean = mLists.get(position);
        Glide.with(mContext)
                .load(bean.getUserAvatar())
                .bitmapTransform(new GlideCircleTransform(mContext))
                .crossFade(300)
                .into(viewHolder.mIvAvatar);
        viewHolder.mTvName.setText(bean.getUser_nick_name());
        viewHolder.mTvTime.setText(DateUtil.getPassedTime(bean.getLast_reply_date()));
        viewHolder.mTvTitle.setText(bean.getTitle());
        viewHolder.mTvTitleDetail.setText(bean.getSubject());
        viewHolder.mTvRead.setText(String.valueOf(bean.getHits()));
        viewHolder.mTvComment.setText(String.valueOf(bean.getReplies()));
        viewHolder.mLlPostItemContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) mOnItemClickListener.onItemClick(position, v, bean);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mLists == null ? 0 : mLists.size();
    }

    public class PostListItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_avatar)
        ImageView mIvAvatar;
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.tv_time)
        TextView mTvTime;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_title_detail)
        TextView mTvTitleDetail;
        @BindView(R.id.tv_comment)
        TextView mTvComment;
        @BindView(R.id.tv_read)
        TextView mTvRead;
        @BindView(R.id.ll_post_item_container)
        LinearLayout mLlPostItemContainer;

        public PostListItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
