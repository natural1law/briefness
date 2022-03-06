package com.androidx.view.list;

import static com.androidx.view.list.Mode.ALL;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.androidx.reduce.tools.Toasts;
import com.androidx.view.R;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public final class RefreshRecycler {

    private static SmartRefreshLayout refresh;
    private static RecyclerView recyclerList;
    private static int pageCode = 1;
    private static int totalPage = 1;
    private static final Executor EXECUTOR = Executors.newCachedThreadPool();

    public static <T extends RefreshAdapter<M>, M, C extends FragmentActivity> void execute(C context, T adapter
            , OnChangeInitListener<T> initListener, OnClickItemListener<M> listener) {
        execute(context, adapter, ALL, new LinearLayoutManager(context), 10, true, initListener, listener);
    }

    public static <T extends RefreshAdapter<M>, M, C extends FragmentActivity> void execute(C context, T adapter
            , int count, OnChangeInitListener<T> initListener, OnClickItemListener<M> listener) {
        execute(context, adapter, ALL, new GridLayoutManager(context, count), 10, true, initListener, listener);
    }

    public static <T extends RefreshAdapter<M>, M, C extends FragmentActivity> void execute(C context, T adapter, Mode mode
            , RecyclerView.LayoutManager layoutManager, int pageCount, boolean auto, OnChangeInitListener<T> initListener
            , OnClickItemListener<M> listener) {
        EXECUTOR.execute(() -> {
            try {
                refresh = context.findViewById(R.id.recycler_refresh);
                if (auto) refresh.autoRefresh();
                recyclerList = context.findViewById(R.id.recycler_list);
                recyclerList.setLayoutManager(layoutManager);
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
                        refresh.setOnRefreshListener(refreshLayout -> EXECUTOR.execute(() -> {
                            pageCode = 1;
                            initListener.onChange(refreshLayout, adapter, pageCode, true);
                        }));
                        break;
                    case MORE:
                        refresh.setEnableRefresh(false);
                        refresh.setOnLoadMoreListener(refreshLayout -> EXECUTOR.execute(() -> {
                            pageCode++;
                            if (pageCode > totalPage) refreshLayout.finishLoadMoreWithNoMoreData();
                            else initListener.onChange(refreshLayout, adapter, pageCode, false);
                        }));
                        break;
                    case ALL:
                    default:
                        refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
                            @Override
                            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                                EXECUTOR.execute(() -> {
                                    pageCode++;
                                    if (pageCode > totalPage)
                                        refreshLayout.finishLoadMoreWithNoMoreData();
                                    else
                                        initListener.onChange(refreshLayout, adapter, pageCode, false);
                                });
                            }

                            @Override
                            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                                EXECUTOR.execute(() -> {
                                    pageCode = 1;
                                    adapter.sizeListener = size -> EXECUTOR.execute(() -> {
                                        totalPage = totalPage(size, pageCount);
                                        if (pageCode == totalPage)
                                            refreshLayout.finishLoadMoreWithNoMoreData();
                                    });
                                    initListener.onChange(refreshLayout, adapter, pageCode, true);
                                });
                            }
                        });
                        break;
                }
            } catch (Exception e) {
                Toasts.i(RefreshRecycler.class.getName(), e);
            }
        });
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
