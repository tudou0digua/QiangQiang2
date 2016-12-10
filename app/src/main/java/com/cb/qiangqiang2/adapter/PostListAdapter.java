package com.cb.qiangqiang2.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.cb.qiangqiang2.common.util.EmojiUtils;
import com.cb.qiangqiang2.data.model.PostModel;
import com.cb.qiangqiang2.ui.activity.UserInfoActivity;
import com.cb.qiangqiang2.adapter.listener.OnItemClickListener;
import com.cb.qiangqiang2.adapter.listener.OnItemLongClickListener;

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
    private OnItemLongClickListener<PostModel.ListBean> mOnItemLongClickListener;

    @Inject
    public PostListAdapter() {

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener<PostModel.ListBean> onItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener;
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
        viewHolder.mIvAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserInfoActivity.startUserInfoActivity(mContext, bean.getUser_id(), bean.getUser_nick_name());
            }
        });
        viewHolder.mTvName.setText(bean.getUser_nick_name());
        viewHolder.mTvTime.setText(DateUtil.getPassedTime(bean.getLast_reply_date()));
        if (!TextUtils.isEmpty(bean.getTitle())) {
            viewHolder.mTvTitle.setText(EmojiUtils.getInstance(mContext).parseText(bean.getTitle(), viewHolder.mTvTitle, mContext));
        } else {
            viewHolder.mTvTitle.setText("");
        }
        if (!TextUtils.isEmpty(bean.getSubject())) {
            viewHolder.mTvTitleDetail.setText(EmojiUtils.getInstance(mContext).parseText(bean.getSubject(), viewHolder.mTvTitleDetail, mContext));
        } else {
            viewHolder.mTvTitleDetail.setText("");
        }
        viewHolder.mTvRead.setText(String.valueOf(bean.getHits()));
        viewHolder.mTvComment.setText(String.valueOf(bean.getReplies()));
        viewHolder.mLlPostItemContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) mOnItemClickListener.onItemClick(position, v, bean);
            }
        });
        viewHolder.mLlPostItemContainer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (mOnItemLongClickListener != null) mOnItemLongClickListener.onItemLongClick(position, view, bean);
                return true;
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
