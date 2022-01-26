package com.androidx.view.list;

import com.scwang.smart.refresh.layout.api.RefreshLayout;

public interface OnChangeInitListener<T> {

    /**
     * 初始化
     *
     * @param refresh  刷新布局
     * @param adapter  适配器
     * @param pageCode 当前页码
     * @param status   状态(true=刷新/false=加载)
     */
    void onChange(RefreshLayout refresh, T adapter, int pageCode, boolean status);

}
