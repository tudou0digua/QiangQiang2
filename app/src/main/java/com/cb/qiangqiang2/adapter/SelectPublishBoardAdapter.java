package com.cb.qiangqiang2.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cb.qiangqiang2.R;
import com.cb.qiangqiang2.adapter.listener.OnItemClickListener;
import com.cb.qiangqiang2.common.dagger.qualifier.ForActivity;
import com.cb.qiangqiang2.data.model.BoardBean;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenbin on 2018/3/20.
 */
public class SelectPublishBoardAdapter extends RecyclerView.Adapter {

    @Inject
    @ForActivity
    Context mContext;
    private List<BoardBean> data;

    private OnItemClickListener<BoardBean> listener;

    public void setListener(OnItemClickListener<BoardBean> listener) {
        this.listener = listener;
    }

    public void setData(List<BoardBean> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    @Inject
    public SelectPublishBoardAdapter() {
        this.data = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_select_publish_board, parent, false);
        return new SelectPublishBoardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        SelectPublishBoardViewHolder viewHolder = (SelectPublishBoardViewHolder) holder;
        viewHolder.tvName.setText(data.get(position).getName());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemClick(position, view, data.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    public class SelectPublishBoardViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;

        public SelectPublishBoardViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
