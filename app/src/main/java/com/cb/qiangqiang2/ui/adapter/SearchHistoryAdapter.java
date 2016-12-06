package com.cb.qiangqiang2.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cb.qiangqiang2.R;
import com.cb.qiangqiang2.common.dagger.qualifier.ForActivity;
import com.cb.qiangqiang2.data.db.greendao.bean.SearchHistory;
import com.cb.qiangqiang2.ui.adapter.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cb on 2016/12/6.
 */

public class SearchHistoryAdapter extends RecyclerView.Adapter {
    @Inject
    @ForActivity
    Context context;

    private List<SearchHistory> lists;
    private OnItemClickListener<SearchHistory> onItemClickListener;

    @Inject
    public SearchHistoryAdapter() {
        lists = new ArrayList<>();
    }

    public List<SearchHistory> getLists() {
        return lists;
    }

    public void setData(@NonNull List<SearchHistory> list) {
        lists.clear();
        lists.addAll(list);
        notifyDataSetChanged();
    }

    public void deleteSearchHistory(SearchHistory searchHistory) {
        int pos = lists.indexOf(searchHistory);
        if (pos != -1) {
            lists.remove(pos);
            notifyDataSetChanged();
//            notifyItemRemoved(pos);
        }
    }

    public void setOnItemClickListener(OnItemClickListener<SearchHistory> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search_history, parent, false);
        return new SearchHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final SearchHistoryViewHolder viewHolder = (SearchHistoryViewHolder) holder;
        final SearchHistory searchHistory = lists.get(position);

        viewHolder.tvText.setText(searchHistory.getContent());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position, viewHolder.itemView, searchHistory);
                }
            }
        });

        viewHolder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position, viewHolder.ivDelete, searchHistory);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class SearchHistoryViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_text)
        TextView tvText;
        @BindView(R.id.iv_delete)
        ImageView ivDelete;

        public SearchHistoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
