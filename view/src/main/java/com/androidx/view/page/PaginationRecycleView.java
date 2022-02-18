package com.androidx.view.page;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import androidx.annotation.ColorRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.androidx.animation.base.ProgressType;
import com.androidx.animation.view.ProgressView;
import com.androidx.view.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PaginationRecycleView<T> extends LinearLayoutCompat {

    private final RecyclerView rv;
    private final PaginationIndicator pi;
    private final ProgressView progress;

    private Adapter<T> adapter;
    private OnChangedListener listener;

    public PaginationRecycleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private PaginationRecycleView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View layoutView = inflate(context, R.layout.layout_recycler, this);
        rv = layoutView.findViewById(R.id.pagination);
        pi = layoutView.findViewById(R.id.indicator);
        progress = layoutView.findViewById(R.id.progress);

        int selectDef = context.getResources().getColor(R.color.irs, context.getTheme());
        int unselectedDef = context.getResources().getColor(R.color.iru, context.getTheme());
        @SuppressLint("CustomViewStyleable")
        TypedArray typed = context.obtainStyledAttributes(attrs, R.styleable.pagination);
        pi.selectColor = typed.getColor(R.styleable.pagination_selectedColor, selectDef);
        pi.unselectedColor = typed.getColor(R.styleable.pagination_unselectedColor, unselectedDef);

        pi.roundCorner = typed.getInteger(R.styleable.pagination_roundCorner, 10);
        pi.textWidth = typed.getInteger(R.styleable.pagination_textWidth, 35);
        pi.textHeight = typed.getInteger(R.styleable.pagination_textHeight, 35);
        pi.textStart = typed.getInteger(R.styleable.pagination_textStart, 15);
        pi.textAlpha = typed.getInteger(R.styleable.pagination_textAlpha, 100);
        pi.textShape = typed.getInteger(R.styleable.pagination_textShape, GradientDrawable.RECTANGLE);
        pi.rim = typed.getInteger(R.styleable.pagination_rim, 5);
        pi.count = typed.getInteger(R.styleable.pagination_count, 7);

        pi.textSize = typed.getDimensionPixelSize(R.styleable.pagination_textSize, Utils.sp2px(getContext(), 18));
        pi.width = typed.getDimensionPixelSize(R.styleable.pagination_rectSize, 0);
        if (pi.width == 0) pi.width = Utils.dp2px(getContext(), 30);

        int progressColor = typed.getColor(R.styleable.pagination_progressColor, unselectedDef);
        int progressAnimation = typed.getInt(R.styleable.pagination_progressAnimation, 0);
        float duration = typed.getFloat(R.styleable.pagination_progressDuration, 0);
        progress.setColorFilter(progressColor);
        if (duration == 0) progress.setBuilder(ProgressType.values()[progressAnimation]);
        else progress.setBuilder(ProgressType.values()[progressAnimation], duration);
        typed.recycle();
    }

    /* START ----------------------------------SET--GET------------------------------------------- */

    public void setAdapterAndManager(Adapter<T> adapter, RecyclerView.LayoutManager manager) {
        this.adapter = adapter;
        adapter.pi = pi;
        rv.setLayoutManager(manager);
        rv.setAdapter(adapter);
        rv.setHasFixedSize(true);
        rv.setAnimation(null);
        pi.setOnChangedListener(c -> listener.onPageSelectedChanged(c));
    }

    public void addItem(int assign, List<T> datas) {
        adapter.addItem(assign, datas);
    }

    public void addItem(int assign, List<T> datas, int total) {
        if (total == 0) total = 1;
        adapter.addItem(assign, datas, total);
    }

    public void clear() {
        adapter.clear();
    }

    /**
     * 设置自动对齐
     */
    public void setSnapHelper() {
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(rv);
    }

    /**
     * 设置适配器优化
     */
    public void setHasFixedSize(boolean hasFixedSize) {
        rv.setHasFixedSize(hasFixedSize);
    }

    /**
     * 设置适配器动画
     */
    public void setAnimation(Animation animation) {
        rv.setAnimation(animation);
    }

    /**
     * 设置指示器数量
     */
    public void setCount(int count) {
        pi.setCount(count);
    }

    /**
     * 设置最大页数
     */
    public void setPageCount(int count) {
        pi.setPageCount(count);
    }

    /**
     * 设置事件回调
     */
    public void setListener(OnChangedListener listener) {
        this.listener = listener;
    }

    /**
     * 设置加载动画样式
     */
    public void setLoadingBuilder(ProgressType type) {
        progress.setBuilder(type);
    }

    /**
     * 设置加载动画样式（延时）
     */
    public void setLoadingBuilder(ProgressType type, float duration) {
        progress.setBuilder(type, duration);
    }

    /**
     * 设置加载动画颜色
     */
    public void setLoadingColor(@ColorRes int color) {
        int c = getContext().getResources().getColor(color, getContext().getTheme());
        progress.setColorFilter(c);
    }

    /**
     * 设置加载动画宽高
     */
    public void setLoading(int width, int height) {
        LinearLayoutCompat.LayoutParams params = new LayoutParams(MATCH_PARENT, MATCH_PARENT);
        params.width = width;
        params.height = height;
        progress.setLayoutParams(params);
    }

    /**
     * 加载完成
     */
    public void loadingFinish() {
        if (progress != null) progress.setVisibility(GONE);
    }

    /**
     * 加载未完成
     */
    public void loadingUnfinished() {
        progress.setVisibility(VISIBLE);
    }

    /**
     * 移除
     */
    public void getRemoveListener() {
        pi.getRemoveListener();
    }


    /* END ------------------------------------SET--GET------------------------------------------- */

    /* START ----------------------------------ADAPTER-------------------------------------------- */

    public static abstract class Adapter<T> extends InnerAdapter<T> {

        private final Map<Integer, List<T>> dataMap = new ConcurrentHashMap<>();
        private final List<T> dataList = new ArrayList<>();
        private PaginationIndicator pi;

        private void addItem(int assign, List<T> datas) {
            addItem(assign, datas, datas.size());
        }

        @SuppressLint("NotifyDataSetChanged")
        private void addItem(int assign, List<T> datas, int total) {
            try {
                dataList.clear();
                dataMap.put(assign <= 0 ? 1 : assign, datas);
                if (dataMap.get(assign) != null) {
                    List<T> data = dataMap.get(assign);
                    if (data != null) dataList.addAll(data);
                    pi.setTotalCount(total);
                }
                notifyDataSetChanged();
            } catch (Exception e) {
                Log.e("PaginationRecycleView.Adapter异常", Log.getStackTraceString(e));
            }
        }

        /**
         * 清空数据
         */
        @SuppressLint("NotifyDataSetChanged")
        private void clear() {
            dataMap.clear();
            notifyDataSetChanged();
        }

        @Override
        protected T data(int position) {
            return dataList.get(position);
        }

        @Override
        protected int size() {
            return dataList.isEmpty() ? 0 : dataList.size();
        }

    }

    private static abstract class InnerAdapter<T> extends RecyclerView.Adapter<HolderView> {

        @LayoutRes
        protected abstract int onLayoutId();

        protected abstract void onBindHolderView(HolderView holder, T data);

        protected abstract T data(int position);

        protected abstract int size();

        @NonNull
        @Override
        public HolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return HolderView.createHolderView(parent, onLayoutId());
        }

        @Override
        public void onBindViewHolder(@NonNull HolderView holder, int position) {
            this.onBindHolderView(holder, data(position));
        }

        @Override
        public int getItemCount() {
            return size();
        }
    }

    /* END ------------------------------------ADAPTER-------------------------------------------- */

}
