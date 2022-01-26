package com.androidx.view.list;

import android.annotation.SuppressLint;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class RefreshAdapter<M> extends RecyclerView.Adapter<HolderView> implements OnAdapterListener<M> {

    private final List<M> dataSources = new ArrayList<>();
    protected OnClickItemListener<M> itemListener;
    protected OnTotalSizeListener sizeListener;

    /**
     * 布局ID
     */
    protected abstract @LayoutRes
    int layoutId();

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

    @NonNull
    @Override
    public HolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return HolderView.createHolderView(parent, layoutId());
    }

    @Override
    public void onBindViewHolder(@NonNull HolderView holder, int position) {
        dispose(holder, position, dataSources.get(position));
    }

    @Override
    public int getItemCount() {
        return dataSources.isEmpty() ? 0 : dataSources.size();
    }

    /**
     * 条目点击事件
     */
    public void setOnClickItemListener(int position) {
        if (itemListener != null) itemListener.onClick(dataSources.get(position));
    }

}
