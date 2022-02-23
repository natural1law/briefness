package com.androidx.view.list;

import java.util.List;

public interface OnAdapterListener<T> {

    /**
     * 添加数据总条数
     */
    void addTotalItem(int size);

    /**
     * 添加数据
     */
    void addItem(List<T> datas);

    /**
     * 加载数据
     */
    void loadItem(List<T> datas);

    /**
     * 清空数据
     */
    void clear();

    /**
     * 移除数据
     */
    void remove(int position);

}
