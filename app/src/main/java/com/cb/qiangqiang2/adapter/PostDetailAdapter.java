package com.cb.qiangqiang2.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.cb.qiangqiang2.R;
import com.cb.qiangqiang2.common.dagger.qualifier.ForActivity;
import com.cb.qiangqiang2.common.glide.GlideCircleTransform;
import com.cb.qiangqiang2.common.util.DateUtil;
import com.cb.qiangqiang2.common.util.EmojiUtils;
import com.cb.qiangqiang2.common.util.ToastUtil;
import com.cb.qiangqiang2.data.model.PostDetailBean;
import com.cb.qiangqiang2.data.model.PostDetailModel;
import com.cb.qiangqiang2.ui.activity.ImageListActivity;
import com.cb.qiangqiang2.ui.activity.PostDetailActivity;

import org.greenrobot.greendao.annotation.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    private ArrayList<String> imageUrls;
    private OnPostDetailClickListener onPostDetailClickListener;

    public void setOnPostDetailClickListener(OnPostDetailClickListener onPostDetailClickListener) {
        this.onPostDetailClickListener = onPostDetailClickListener;
    }

    @Inject
    public PostDetailAdapter() {
        lists = new ArrayList<>();
        imageUrls = new ArrayList<>();
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public void setData(@NotNull PostDetailModel postDetailModel) {
        lists.clear();
        imageUrls.clear();
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
                    if (!TextUtils.isEmpty(contentBean.getOriginalInfo())) {
                        imageUrls.add(contentBean.getOriginalInfo());
                    }
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

        final int userId;
        final String nickName;
        PostDetailModel.TopicBean topicBean = bean.getTopicBean();
        PostDetailModel.ListBean listBean = bean.getListBean();
        String content = null;
        if (listBean != null) {
            if (listBean.getReply_content() != null && listBean.getReply_content().size() > 0) {
                content = listBean.getReply_content().get(0).getInfor();
            }
            userId = listBean.getReply_id();
            nickName = listBean.getReply_name();
            Glide.with(context)
                    .load(listBean.getIcon())
                    .placeholder(R.drawable.default_icon)
                    .error(R.drawable.default_icon)
                    .bitmapTransform(new GlideCircleTransform(context))
                    .dontAnimate()
                    .into(holder.ivAvatar);
            holder.tvName.setText(listBean.getReply_name());
            holder.tvLevel.setText(listBean.getUserTitle());
            holder.tvTime.setText(DateUtil.getPassedTime(listBean.getPosts_date()));
            holder.tvFloor.setText(context.getString(R.string.post_detail_floor, listBean.getPosition() - 1));
        } else if (topicBean != null) {
            if (topicBean.getContent() != null && topicBean.getContent().size() > 0) {
                content = topicBean.getContent().get(0).getInfor();
            }
            userId = topicBean.getUser_id();
            nickName = topicBean.getUser_nick_name();
            Glide.with(context)
                    .load(topicBean.getIcon())
                    .placeholder(R.drawable.default_icon)
                    .error(R.drawable.default_icon)
                    .bitmapTransform(new GlideCircleTransform(context))
                    .dontAnimate()
                    .into(holder.ivAvatar);
            holder.tvName.setText(topicBean.getUser_nick_name());
            holder.tvLevel.setText(topicBean.getUserTitle());
            holder.tvTime.setText(DateUtil.getPassedTime(topicBean.getCreate_date()));
            holder.tvFloor.setText(context.getString(R.string.post_detail_publisher));
        } else {
            userId = 0;
            nickName = "";
        }
        holder.ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onPostDetailClickListener != null && userId != 0) {
                    onPostDetailClickListener.onAvatarClick(userId, nickName);
                }
            }
        });
        if (!TextUtils.isEmpty(content)) {
            final String finalContent = content;
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    showLongClickDialog(finalContent);
                    return true;
                }
            });
        }
    }

    private void showLongClickDialog(final String content) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog
                .Builder(context);

        final String[] list = new String[]{"复制"};

        builder.setTitle(content);
        builder.setItems(list, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0:
                        //复制
                        try {
                            ClipData clipData = ClipData.newPlainText("", content);
                            ((ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(clipData);
                            ToastUtil.show("复制成功");
                        } catch (Exception e) {
                            e.printStackTrace();
                            ToastUtil.show("复制失败");
                        }
                        break;
                }
            }
        });

        AlertDialog dialog = builder.create();
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        if (lp != null) {
            lp.gravity = Gravity.CENTER;
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(lp);
        }
//        window.setWindowAnimations(R.style.StyleBottomInOut);
        dialog.show();
    }

    private void onBindTextViewHolder(PostDetailBean bean, TextViewHolder holder, int position) {
        PostDetailModel.TopicBean.ContentBean contentBean = bean.getContentBean();
        if (contentBean != null) {
            holder.tvContent.setTextIsSelectable(true);
//            holder.tvContent.setText(contentBean.getInfor());
            String info = contentBean.getInfor();
            if (!TextUtils.isEmpty(info)) {
                holder.tvContent.setText(EmojiUtils.getInstance(context).parseText(info, holder.tvContent, context));
            } else {
                holder.tvContent.setText("");
            }
        }
    }

    private void onBindImageViewHolder(final PostDetailBean bean, final ImageViewHolder holder, int position) {
        final PostDetailModel.TopicBean.ContentBean contentBean = bean.getContentBean();
        if (contentBean != null) {
            int imageWidth;
            final int imageHeight;
            if (contentBean.getImageWidth() > 0 && contentBean.getImageHeight() > 0) {
                imageWidth = ScreenUtils.getScreenWidth() -
                        context.getResources().getDimensionPixelOffset(R.dimen.padding_left) * 2;
                imageHeight = imageWidth * contentBean.getImageHeight() / contentBean.getImageWidth();
            } else {
                imageWidth = ViewGroup.LayoutParams.MATCH_PARENT;
                imageHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
            }
            ViewGroup.LayoutParams layoutParams = holder.ivImage.getLayoutParams();
            layoutParams.width = imageWidth;
            layoutParams.height = imageHeight;

            Glide.with(context)
                    .load(contentBean.getOriginalInfo())
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            contentBean.setImageWidth(resource.getIntrinsicWidth());
                            contentBean.setImageHeight(resource.getIntrinsicHeight());
                            return false;
                        }
                    })
                    .into(holder.ivImage);
            holder.ivImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageListActivity.startActivity(context, contentBean.getOriginalInfo(), imageUrls);
//                    BigImageActivity.startBigImageActivity(contentBean.getOriginalInfo(), (Activity) context, holder.ivImage);
                }
            });
        }
    }

    private void onBindHyperlinkViewHolder(PostDetailBean bean, HyperlinkViewHolder holder, int position) {
        PostDetailModel.TopicBean.ContentBean contentBean = bean.getContentBean();
        if (contentBean != null) {
            final String info = contentBean.getInfor();
            if (!TextUtils.isEmpty(info)) {
                SpannableStringBuilder builder = new SpannableStringBuilder(info);
                builder.setSpan(new NoLineClickableSpan(contentBean), 0, info.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                holder.tvContent.setText(builder);
                holder.tvContent.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        showLongClickDialog(info);
                        return true;
                    }
                });
            }
        }
    }

    private void onBindBottomViewHolder(final PostDetailBean bean, BottomViewHolder holder, int position) {
        holder.ivReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onPostDetailClickListener != null) {
                    onPostDetailClickListener.onReplayClick(bean.getListBean());
                }
            }
        });
    }

    private class NoLineClickableSpan extends ClickableSpan {
        private PostDetailModel.TopicBean.ContentBean contentBean;

        public NoLineClickableSpan(PostDetailModel.TopicBean.ContentBean contentBean) {
            this.contentBean = contentBean;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(ds.linkColor);
            ds.setUnderlineText(false);
        }

        @Override
        public void onClick(View view) {
            String url = contentBean.getUrl();
            if (!TextUtils.isEmpty(url)) {
                if (url.contains("www.qiangqiang5.com/thread")) {
                    Pattern pattern = Pattern.compile("(?<=-)\\d+");
                    Matcher matcher = pattern.matcher(url);
                    if (matcher.find()) {
                        String topicId = matcher.group();
                        PostDetailActivity.startPostDetailActivity(context,
                                0, Integer.parseInt(topicId), "", "");
                        return;
                    }
                }

                //链接处理(浏览器打开)
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                context.startActivity(intent);
            }
        }
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
        ImageView ivAvatar;
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

    public interface OnPostDetailClickListener {
        void onAvatarClick(int userId, String nickName);
        void onReplayClick(PostDetailModel.ListBean listBean);
    }

}
