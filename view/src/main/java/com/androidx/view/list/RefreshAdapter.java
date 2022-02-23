package com.androidx.view.list;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidx.reduce.tools.Toasts;

import java.util.ArrayList;
import java.util.List;

public abstract class RefreshAdapter<M> extends RecyclerView.Adapter<HolderView> implements OnAdapterListener<M> {

    private final List<M> dataSources = new ArrayList<>();
    protected Context context;
    protected OnClickItemListener<M> itemListener;
    protected OnTotalSizeListener sizeListener;

    /**
     * 布局ID
     */
    @LayoutRes
    protected abstract int layoutId();

    /**
     * 业务处理
     */
    protected abstract void dispose(@NonNull HolderView holder, int position, M model);

    /**
     * 添加数据总条数
     */
    @Override
    public void addTotalItem(int size) {
        if (sizeListener != null) sizeListener.totalSize(size);
    }

    @Override
    @SuppressLint("NotifyDataSetChanged")
    public void addItem(List<M> datas) {
        dataSources.clear();
        dataSources.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    @SuppressLint("NotifyDataSetChanged")
    public void loadItem(List<M> datas) {
        dataSources.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    @SuppressLint("NotifyDataSetChanged")
    public void clear() {
        if (!dataSources.isEmpty()) {
            dataSources.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    @SuppressLint("NotifyDataSetChanged")
    public void remove(int position) {
        if (!dataSources.isEmpty()) {
            dataSources.remove(position);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public HolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        return HolderView.createHolderView(parent, layoutId());
    }

    @Override
    public void onBindViewHolder(@NonNull HolderView holder, int position) {
        try {
            dispose(holder, position, dataSources.get(position));
        } catch (Exception e) {
            Toasts.e(getClass().getName(), e);
        }
    }

    @Override
    public int getItemCount() {
        return dataSources.isEmpty() ? 0 : dataSources.size();
    }

    /**
     * 条目点击事件
     */
    protected void setOnClickItemListener(int position) {
        if (itemListener != null) itemListener.onClick(dataSources.get(position));
    }

}
