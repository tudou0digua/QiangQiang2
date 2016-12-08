package com.cb.qiangqiang2.ui.adapter;

import android.content.Context;
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
import com.cb.qiangqiang2.common.util.DateUtil;
import com.cb.qiangqiang2.data.model.PostDetailBean;
import com.cb.qiangqiang2.data.model.PostDetailModel;
import com.cb.qiangqiang2.ui.adapter.listener.OnItemClickListener;
import com.cb.qiangqiang2.ui.adapter.listener.OnItemLongClickListener;

import org.greenrobot.greendao.annotation.NotNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.carbs.android.avatarimageview.library.AvatarImageView;

/**
 * Created by cb on 2016/12/8.
 */

public class PostDetailAdapter extends RecyclerView.Adapter {
    public static final int TYPE_TITLE = 0;
    public static final int TYPE_TOP = 1;
    public static final int TYPE_TEXT = 2;
    public static final int TYPE_HYPERLINK = 3;
    public static final int TYPE_IMAGE = 4;
    public static final int TYPE_BOTTOM = 5;

    @Inject
    @ForActivity
    Context context;

    private String boardName;
    private List<PostDetailBean> lists;
    private OnItemClickListener<PostDetailBean> onItemClickListener;
    private OnItemLongClickListener<PostDetailBean> onItemLongClickListener;

    @Inject
    public PostDetailAdapter() {
        lists = new ArrayList<>();
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public void setOnItemClickListener(OnItemClickListener<PostDetailBean> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener<PostDetailBean> onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public void setData(@NotNull PostDetailModel postDetailModel) {
        lists.clear();
        PostDetailModel.TopicBean topicBean = postDetailModel.getTopic();
        //帖子信息
        if (topicBean != null) {
            //楼主信息
            PostDetailBean bean = new PostDetailBean();
            bean.setTopicBean(topicBean);
            bean.setType(TYPE_TOP);
            lists.add(bean);
            //标题
            bean = new PostDetailBean();
            bean.setType(TYPE_TITLE);
            bean.setTopicBean(topicBean);
            lists.add(bean);
            //楼主发布内容
            List<PostDetailModel.TopicBean.ContentBean> contentBeanList = topicBean.getContent();
            if (contentBeanList != null && contentBeanList.size() > 0) {
                setContentList(contentBeanList, lists);
            }
            //楼主发布内容底部
            bean = new PostDetailBean();
            bean.setType(TYPE_BOTTOM);
            bean.setTopicBean(topicBean);
            lists.add(bean);
        }
        //回复列表信息
        setReplyList(postDetailModel);
        notifyDataSetChanged();
    }

    public void addData(@NotNull PostDetailModel postDetailModel) {
        int sizeBefore = lists.size();
        //回复列表信息
        setReplyList(postDetailModel);
        int sizeLater = lists.size();
        notifyItemRangeChanged(sizeBefore, sizeLater);
    }

    private void setContentList(List<PostDetailModel.TopicBean.ContentBean> contentBeanList, List<PostDetailBean> list) {
        for (PostDetailModel.TopicBean.ContentBean contentBean : contentBeanList) {
            PostDetailBean detailBean = new PostDetailBean();
            switch (contentBean.getType()) {
                //文本
                case 0:
                    detailBean.setType(TYPE_TEXT);
                    break;
                //图片
                case 1:
                    detailBean.setType(TYPE_IMAGE);
                    break;
                //超链接
                case 4:
                    detailBean.setType(TYPE_HYPERLINK);
                    break;
                default:
                    detailBean.setType(TYPE_TEXT);
            }
            detailBean.setContentBean(contentBean);
            list.add(detailBean);
        }
    }

    private void setReplyList(@NotNull PostDetailModel postDetailModel) {
        List<PostDetailModel.ListBean> replyBeanList = postDetailModel.getList();
        if (replyBeanList != null) {
            //回复列表
            for (PostDetailModel.ListBean listBean : replyBeanList) {
                //层主信息
                PostDetailBean replyBean = new PostDetailBean();
                replyBean.setType(TYPE_TOP);
                replyBean.setListBean(listBean);
                lists.add(replyBean);
                List<PostDetailModel.TopicBean.ContentBean> replyContentList = listBean.getReply_content();
                //层主回复内容
                if (replyContentList != null) {
                    setContentList(replyContentList, lists);
                }
                //回复层主
                replyBean = new PostDetailBean();
                replyBean.setListBean(listBean);
                replyBean.setType(TYPE_BOTTOM);
                lists.add(replyBean);
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case TYPE_TITLE:
                view = LayoutInflater.from(context).inflate(R.layout.item_post_detail_title, parent, false);
                return new TitleViewHolder(view);
            case TYPE_TOP:
                view = LayoutInflater.from(context).inflate(R.layout.item_post_detail_top, parent, false);
                return new TopViewHolder(view);
            case TYPE_TEXT:
                view = LayoutInflater.from(context).inflate(R.layout.item_post_detail_text, parent, false);
                return new TextViewHolder(view);
            case TYPE_IMAGE:
                view = LayoutInflater.from(context).inflate(R.layout.item_post_detail_image, parent, false);
                return new ImageViewHolder(view);
            case TYPE_HYPERLINK:
                view = LayoutInflater.from(context).inflate(R.layout.item_post_detail_text, parent, false);
                return new HyperlinkViewHolder(view);
            case TYPE_BOTTOM:
                view = LayoutInflater.from(context).inflate(R.layout.item_post_detail_bottom, parent, false);
                return new BottomViewHolder(view);
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    @Override
    public int getItemViewType(int position) {
        return lists.get(position).getType();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PostDetailBean bean = lists.get(position);
        if (holder instanceof TitleViewHolder) {
            onBindTitleViewHolder(bean, (TitleViewHolder) holder, position);
        } else if (holder instanceof TopViewHolder) {
            onBindTopViewHolder(bean, (TopViewHolder) holder, position);
        } else if (holder instanceof TextViewHolder) {
            onBindTextViewHolder(bean, (TextViewHolder) holder, position);
        } else if (holder instanceof ImageViewHolder) {
            onBindImageViewHolder(bean, (ImageViewHolder) holder, position);
        } else if (holder instanceof HyperlinkViewHolder) {
            onBindHyperlinkViewHolder(bean, (HyperlinkViewHolder) holder, position);
        } else if (holder instanceof BottomViewHolder) {
            onBindBottomViewHolder(bean, (BottomViewHolder)holder, position);
        }
    }

    private void onBindTitleViewHolder(PostDetailBean bean, TitleViewHolder holder, int position) {
        PostDetailModel.TopicBean topicBean = bean.getTopicBean();
        if (topicBean != null) {
            holder.tvTitle.setText(topicBean.getTitle());
            holder.tvReadNum.setText(String.valueOf(topicBean.getHits()));
            holder.tvReplyNum.setText(String.valueOf(topicBean.getReplies()));
            if (!TextUtils.isEmpty(boardName)) {
                holder.tvBoardName.setVisibility(View.VISIBLE);
                holder.tvBoardName.setText(boardName);
            } else {
                holder.tvBoardName.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void onBindTopViewHolder(final PostDetailBean bean, TopViewHolder holder, final int position) {
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) holder.itemView.getLayoutParams();
        int marginTop;
        if (position == 0) {
            marginTop = 0;
        } else {
            marginTop = context.getResources().getDimensionPixelOffset(R.dimen.post_detail_item_top_margin_top);
        }
        layoutParams.setMargins(layoutParams.leftMargin, marginTop, layoutParams.rightMargin, layoutParams.bottomMargin);

        PostDetailModel.TopicBean topicBean = bean.getTopicBean();
        PostDetailModel.ListBean listBean = bean.getListBean();
        if (listBean != null) {
            Glide.with(context)
                    .load(listBean.getIcon())
                    .crossFade(300)
                    .into(holder.ivAvatar);
            holder.tvName.setText(listBean.getReply_name());
            holder.tvLevel.setText(listBean.getUserTitle());
            holder.tvTime.setText(DateUtil.getPassedTime(listBean.getPosts_date()));
            holder.tvFloor.setText(context.getString(R.string.post_detail_floor, listBean.getPosition()));
        } else if (topicBean != null) {
            Glide.with(context)
                    .load(topicBean.getIcon())
                    .crossFade(300)
                    .into(holder.ivAvatar);
            holder.tvName.setText(topicBean.getUser_nick_name());
            holder.tvLevel.setText(topicBean.getUserTitle());
            holder.tvTime.setText(DateUtil.getPassedTime(topicBean.getCreate_date()));
            holder.tvFloor.setText(context.getString(R.string.post_detail_publisher));
        }
    }

    private void onBindTextViewHolder(PostDetailBean bean, TextViewHolder holder, int position) {
        PostDetailModel.TopicBean.ContentBean contentBean = bean.getContentBean();
        if (contentBean != null) {
            holder.tvContent.setTextIsSelectable(true);
            holder.tvContent.setText(contentBean.getInfor());
        }
    }

    private void onBindImageViewHolder(PostDetailBean bean, ImageViewHolder holder, int position) {
        PostDetailModel.TopicBean.ContentBean contentBean = bean.getContentBean();
        if (contentBean != null) {
            Glide.with(context)
                    .load(contentBean.getOriginalInfo())
                    .into(holder.ivImage);
        }
    }

    private void onBindHyperlinkViewHolder(PostDetailBean bean, HyperlinkViewHolder holder, int position) {
        PostDetailModel.TopicBean.ContentBean contentBean = bean.getContentBean();
        if (contentBean != null) {
            holder.tvContent.setText(contentBean.getInfor());
        }
    }

    private void onBindBottomViewHolder(PostDetailBean bean, BottomViewHolder holder, int position) {

    }

    public class TitleViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_read_num)
        TextView tvReadNum;
        @BindView(R.id.tv_reply_num)
        TextView tvReplyNum;
        @BindView(R.id.tv_board_name)
        TextView tvBoardName;

        public TitleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class TopViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_avatar)
        AvatarImageView ivAvatar;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_level)
        TextView tvLevel;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_floor)
        TextView tvFloor;

        public TopViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class BottomViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_reply)
        ImageView ivReply;

        public BottomViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class TextViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_content)
        TextView tvContent;

        public TextViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class HyperlinkViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_content)
        TextView tvContent;

        public HyperlinkViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_image)
        ImageView ivImage;

        public ImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
