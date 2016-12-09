package com.cb.qiangqiang2.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cb.qiangqiang2.R;
import com.cb.qiangqiang2.common.dagger.qualifier.ForActivity;
import com.cb.qiangqiang2.common.glide.GlideCircleTransform;
import com.cb.qiangqiang2.data.model.SearchUserResultModel;
import com.cb.qiangqiang2.adapter.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cb on 2016/12/6.
 */

public class SearchUserAdapter extends RecyclerView.Adapter {
    @Inject
    @ForActivity
    Context mContext;

    private List<SearchUserResultModel.BodyBean.ListBean> mLists;
    private OnItemClickListener<SearchUserResultModel.BodyBean.ListBean> mOnItemClickListener;

    public void setSearchUserResultModel(@NonNull SearchUserResultModel searchUserResultModel) {
        mLists.clear();
        List<SearchUserResultModel.BodyBean.ListBean> list = searchUserResultModel.getBody().getList();
        if (list != null) {
            mLists.addAll(list);
        }
        notifyDataSetChanged();
    }

    public void addData(@NonNull SearchUserResultModel searchUserResultModel) {
        List<SearchUserResultModel.BodyBean.ListBean> lists = searchUserResultModel.getBody().getList();
        if (lists == null || lists.size() <= 0) return;
        mLists.addAll(lists);
        notifyItemRangeChanged(mLists.size(), lists.size());
    }

    public void setOnItemClickListener(OnItemClickListener<SearchUserResultModel.BodyBean.ListBean> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Inject
    public SearchUserAdapter() {
        mLists = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_search_user, parent, false);
        return new SearchUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        SearchUserViewHolder viewHolder = (SearchUserViewHolder) holder;
        final SearchUserResultModel.BodyBean.ListBean bean = mLists.get(position);
        Glide.with(mContext)
                .load(bean.getIcon())
                .bitmapTransform(new GlideCircleTransform(mContext))
                .crossFade(300)
                .into(viewHolder.mIvAvatar);
        viewHolder.mTvName.setText(bean.getName());
        if (TextUtils.isEmpty(bean.getUserTitle())) {
            viewHolder.mTvLevel.setVisibility(View.GONE);
        } else {
            viewHolder.mTvLevel.setVisibility(View.VISIBLE);
            viewHolder.mTvLevel.setText(bean.getUserTitle());
        }
        String signature = bean.getSignture();
        if (TextUtils.isEmpty(signature)) {
            viewHolder.mTvSignature.setText(R.string.user_list_no_signature);
        } else {
            viewHolder.mTvSignature.setText(signature);
        }
        if (bean.getIsFollow() == 0) {
            viewHolder.mTvFollow.setText(R.string.user_list_not_followed);
        } else {
            viewHolder.mTvFollow.setText(R.string.user_list_followed);
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(position, view, bean);
                }
            }
        });
        viewHolder.mTvFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(position, view, bean);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mLists.size();
    }

    public class SearchUserViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_avatar)
        ImageView mIvAvatar;
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.tv_level)
        TextView mTvLevel;
        @BindView(R.id.tv_signature)
        TextView mTvSignature;
        @BindView(R.id.tv_follow)
        TextView mTvFollow;

        public SearchUserViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
