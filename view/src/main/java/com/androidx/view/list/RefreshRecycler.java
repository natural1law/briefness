package com.androidx.view.list;

import static com.androidx.view.list.Mode.ALL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.androidx.view.R;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

public final class RefreshRecycler {

    private static SmartRefreshLayout refresh;
    private static RecyclerView recyclerList;
    private static int pageCode = 1;
    private static int totalPage = 1;

    public static <T extends RefreshAdapter<M>, M> void execute(AppCompatActivity context, T adapter, OnChangeInitListener<T> initListener, OnClickItemListener<M> listener) {
        execute(context, adapter, ALL, 10, true, initListener, listener);
    }

    public static <T extends RefreshAdapter<M>, M> void execute(AppCompatActivity context, T adapter, Mode mode, int pageCount, boolean auto, OnChangeInitListener<T> initListener, OnClickItemListener<M> listener) {
        refresh = context.findViewById(R.id.recycler_refresh);
        if (auto) refresh.autoRefresh();
        recyclerList = context.findViewById(R.id.recycler_list);
        recyclerList.setLayoutManager(new LinearLayoutManager(context));
        recyclerList.setHasFixedSize(true);
        recyclerList.setItemAnimator(new DefaultItemAnimator());
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerList);
        recyclerList.setAdapter(adapter);
        adapter.itemListener = listener;
        switch (mode) {
            case REFRESH:
                refresh.setNoMoreData(false);
                refresh.setEnableLoadMore(false);
                refresh.setEnableAutoLoadMore(false);
                refresh.setOnRefreshListener(refreshLayout -> {
                    pageCode = 1;
                    initListener.onChange(refreshLayout, adapter, pageCode, true);
                });
                break;
            case MORE:
                refresh.setEnableRefresh(false);
                refresh.setOnLoadMoreListener(refreshLayout -> {
                    pageCode++;
                    if (pageCode > totalPage) refreshLayout.finishLoadMoreWithNoMoreData();
                    else initListener.onChange(refreshLayout, adapter, pageCode, false);
                });
                break;
            case ALL:
            default:
                refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
                    @Override
                    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                        pageCode++;
                        if (pageCode > totalPage) refreshLayout.finishLoadMoreWithNoMoreData();
                        else initListener.onChange(refreshLayout, adapter, pageCode, false);
                    }

                    @Override
                    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                        pageCode = 1;
                        adapter.sizeListener = size -> totalPage = totalPage(size, pageCount);
                        initListener.onChange(refreshLayout, adapter, pageCode, true);
                    }
                });
                break;
        }

    }

    public static <T extends RefreshAdapter<M>, M> void executeGrid(AppCompatActivity context, T adapter, Mode mode, int count, int pageCount, boolean auto, OnChangeInitListener<T> initListener, OnClickItemListener<M> listener) {
        refresh = context.findViewById(R.id.recycler_refresh);
        if (auto) refresh.autoRefresh();
        recyclerList = context.findViewById(R.id.recycler_list);
        recyclerList.setLayoutManager(new GridLayoutManager(context, count));
        recyclerList.setHasFixedSize(true);
        recyclerList.setItemAnimator(new DefaultItemAnimator());
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerList);
        recyclerList.setAdapter(adapter);
        adapter.itemListener = listener;
        switch (mode) {
            case REFRESH:
                refresh.setNoMoreData(false);
                refresh.setEnableLoadMore(false);
                refresh.setEnableAutoLoadMore(false);
                refresh.setOnRefreshListener(refreshLayout -> {
                    pageCode = 1;
                    initListener.onChange(refreshLayout, adapter, pageCode, true);
                });
                break;
            case MORE:
                refresh.setEnableRefresh(false);
                refresh.setOnLoadMoreListener(refreshLayout -> {
                    pageCode++;
                    if (pageCode > totalPage) refreshLayout.finishLoadMoreWithNoMoreData();
                    else initListener.onChange(refreshLayout, adapter, pageCode, false);
                });
                break;
            case ALL:
            default:
                refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
                    @Override
                    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                        pageCode++;
                        if (pageCode > totalPage) refreshLayout.finishLoadMoreWithNoMoreData();
                        else initListener.onChange(refreshLayout, adapter, pageCode, false);
                    }

                    @Override
                    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                        pageCode = 1;
                        adapter.sizeListener = size -> totalPage = totalPage(size, pageCount);
                        initListener.onChange(refreshLayout, adapter, pageCode, true);
                    }
                });
                break;
        }
    }

    public static <T extends RefreshAdapter<M>, M> void executeStaggered(AppCompatActivity context, T adapter, Mode mode, int spanCount, int orientation, int pageCount, boolean auto, OnChangeInitListener<T> initListener, OnClickItemListener<M> listener) {
        refresh = context.findViewById(R.id.recycler_refresh);
        if (auto) refresh.autoRefresh();
        recyclerList = context.findViewById(R.id.recycler_list);
        recyclerList.setLayoutManager(new StaggeredGridLayoutManager(spanCount, orientation));
        recyclerList.setHasFixedSize(true);
        recyclerList.setItemAnimator(new DefaultItemAnimator());
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerList);
        recyclerList.setAdapter(adapter);
        adapter.itemListener = listener;
        switch (mode) {
            case REFRESH:
                refresh.setNoMoreData(false);
                refresh.setEnableLoadMore(false);
                refresh.setEnableAutoLoadMore(false);
                refresh.setOnRefreshListener(refreshLayout -> {
                    pageCode = 1;
                    initListener.onChange(refreshLayout, adapter, pageCode, true);
                });
                break;
            case MORE:
                refresh.setEnableRefresh(false);
                refresh.setOnLoadMoreListener(refreshLayout -> {
                    pageCode++;
                    if (pageCode > totalPage) refreshLayout.finishLoadMoreWithNoMoreData();
                    else initListener.onChange(refreshLayout, adapter, pageCode, false);
                });
                break;
            case ALL:
            default:
                refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
                    @Override
                    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                        pageCode++;
                        if (pageCode > totalPage) refreshLayout.finishLoadMoreWithNoMoreData();
                        else initListener.onChange(refreshLayout, adapter, pageCode, false);
                    }

                    @Override
                    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                        pageCode = 1;
                        adapter.sizeListener = size -> totalPage = totalPage(size, pageCount);
                        initListener.onChange(refreshLayout, adapter, pageCode, true);
                    }
                });
                break;
        }
    }

    /**
     * @param totalSize 总条数
     * @param pageCount 页数
     * @return 总页数
     */
    private static int totalPage(int totalSize, int pageCount) {
        return totalSize == 0 ? 1 : (int) Math.ceil(totalSize / (pageCount * 1.0));
    }

}
