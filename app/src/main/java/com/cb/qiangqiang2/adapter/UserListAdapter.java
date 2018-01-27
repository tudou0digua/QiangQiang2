package com.cb.qiangqiang2.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cb.qiangqiang2.R;
import com.cb.qiangqiang2.common.constant.Constants;
import com.cb.qiangqiang2.common.dagger.qualifier.ForActivity;
import com.cb.qiangqiang2.common.glide.GlideCircleTransform;
import com.cb.qiangqiang2.data.model.UserListModel;
import com.cb.qiangqiang2.presenter.OtherPresenter;
import com.cb.qiangqiang2.adapter.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cb on 2016/10/26.
 */

public class UserListAdapter extends RecyclerView.Adapter {
    @Inject
    @ForActivity
    Context mContext;

    @Inject
    OtherPresenter mOtherPresenter;

    private UserListModel mUserListModel;
    private List<UserListModel.ListBean> mLists;
    private OnItemClickListener<UserListModel.ListBean> mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setUserListModel(UserListModel userListModel) {
        this.mUserListModel = userListModel;
        List<UserListModel.ListBean> list = userListModel.getList();
        if (list == null) {
            mLists = new ArrayList<>();
        } else {
            mLists = this.mUserListModel.getList();
        }
        notifyDataSetChanged();
    }

    public void addData(UserListModel userListModel) {
        List<UserListModel.ListBean> list = userListModel.getList();
        if (list == null) return;
        int start = mLists.size();
        mLists.addAll(list);
        notifyItemRangeInserted(start, list.size());
    }

    @Inject
    public UserListAdapter() {
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_list_item, parent, false);
        return new UserListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        UserListViewHolder viewHolder = (UserListViewHolder) holder;
        final UserListModel.ListBean bean = mLists.get(position);
        Glide.with(mContext)
                .load(bean.getIcon())
                .placeholder(R.drawable.default_icon)
                .error(R.drawable.default_icon)
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
        String signature = bean.getSignature();
        if (TextUtils.isEmpty(signature)) {
            viewHolder.mTvSignature.setText(R.string.user_list_no_signature);
        } else {
            viewHolder.mTvSignature.setText(signature);
        }
        viewHolder.mLlItemContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(position, view, bean);
                }
            }
        });
        if (bean.getIsFollow() == 0) {
            viewHolder.mTvFollow.setText(R.string.user_list_not_followed);
        } else {
            viewHolder.mTvFollow.setText(R.string.user_list_followed);
        }
        viewHolder.mTvFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
                mOtherPresenter.setFollowStatus(bean.getUid(), Constants.POST_TYPE_FOLLOW);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mLists == null ? 0 : mLists.size();
    }

    public class UserListViewHolder extends RecyclerView.ViewHolder {
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
        @BindView(R.id.ll_item_container)
        RelativeLayout mLlItemContainer;

        public UserListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

}
