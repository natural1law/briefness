package com.androidx.view.page;

import static android.view.Gravity.CENTER;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页指示器
 *
 * @date 2021/09/08
 */
public class PaginationIndicator extends FrameLayout {

    protected int currentPage = 1;
    private int pageCount = 10;
    private int totalCount;
    private int totalPageCount;

    protected int rim;
    protected int textAlpha;
    protected int textShape;
    protected int textWidth;
    protected int textHeight;
    protected int textStart;
    protected int roundCorner;
    protected int selectColor;
    protected int unselectedColor;
    protected int textSize;
    protected int width;
    protected int count;//指示器数量（奇数）
    private LinearLayoutCompat pageCodeView;
    //    private AppCompatTextView[] textViews;
    private final List<AppCompatTextView> textViews = new ArrayList<>();

    private OnChangedListener onChangedListener;
    private Observer observer;

    public PaginationIndicator(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private PaginationIndicator(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private PaginationIndicator(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        try {
            pageCodeView = new LinearLayoutCompat(getContext());
            LayoutParams layoutParams = new LayoutParams(MATCH_PARENT, WRAP_CONTENT);
            pageCodeView.setGravity(CENTER);
            layoutParams.bottomMargin = Utils.dp2px(context, 10);
            this.addView(pageCodeView, layoutParams);
            observer = Observer.build();
            observer.addListener(listener);
        } catch (Exception e) {
            Log.e("PaginationIndicator异常", Log.getStackTraceString(e));
        }
    }

    private final PropertyChangeListener listener = evt -> {
        this.totalCount = Integer.parseInt(String.valueOf(evt.getNewValue()));
        notifyChange();
    };

    private void notifyChange() {
        try {
            if (onChangedListener != null) onChangedListener.onPageSelectedChanged(currentPage);
            totalPageCount = totalCount % pageCount > 0 ? totalCount / pageCount + 1 : totalCount / pageCount;
            geneNumberTextView();
            updatePageCode();
        } catch (Exception e) {
            Log.e("notifyChange异常", Log.getStackTraceString(e));
        }
    }

    /**
     * 生成数字指示器view及计算需要渲染的数字起始到结束
     */
    private void updatePageCode() {
        if (totalCount == 0) {
            pageCodeView.removeAllViews();
            return;
        }
        if (totalPageCount > count) {
            int start, end;
            int half = count / 2;
            start = currentPage - half;
            end = currentPage + half;
            if (start <= 0) {
                // 越过"数字1"的位置了  把超出部分补偿给end
                end = end + Math.abs(start) + 1;
                start = 1;
            } else if (end > totalPageCount) {
                // 越过"总页数数字"的位置了  把超出部分补偿给start
                start = start - Math.abs(totalPageCount - end);
                end = totalPageCount;
            }
            updateNumberText(start, end);
        } else {
            // 总页数小于数字指示器数量，则直接以总页数的大小来刷新
            updateNumberText(1, textViews.size());
        }
    }

    /**
     * 根据起始数字和结束数字填充数字指示器的textview
     */
    @SuppressLint("SetTextI18n")
    private void updateNumberText(int start, int end) {
        for (int i = 0; i < end - start + 1; i++) {
            AppCompatTextView textView = textViews.get(i);
            textView.setText(String.valueOf(start + i));
            if (start + i == currentPage) {
                textView.setSelected(true);
                textView.setTextColor(selectColor);
                selected(textView);
            } else {
                textView.setSelected(false);
                textView.setTextColor(unselectedColor);
                unselected(textView);
            }
            LinearLayoutCompat.LayoutParams params = (LinearLayoutCompat.LayoutParams) textView.getLayoutParams();
            if (i > 0 && i < textViews.size())
                params.setMarginStart(Utils.dp2px(getContext(), textStart));
        }

    }

    private void geneNumberTextView() {
        if (!textViews.isEmpty()) textViews.clear();
        if (pageCodeView != null) pageCodeView.removeAllViews();
        int num = Math.min(count, totalPageCount);
        for (int i = 0; i < num; i++) {
            AppCompatTextView textView = new AppCompatTextView(getContext());
            textView.setWidth(Utils.dp2px(getContext(), textWidth));
            textView.setHeight(Utils.dp2px(getContext(), textHeight));
            textView.setGravity(CENTER);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            pageCodeView.addView(textView);

            textView.setOnClickListener(view -> {
                int number = Integer.parseInt(textView.getText().toString().trim());
                if (number == currentPage) return;
                currentPage = number;
                if (onChangedListener != null)
                    onChangedListener.onPageSelectedChanged(number);
                updatePageCode();
            });
            textViews.add(textView);
        }
    }

    private void selected(AppCompatTextView textView) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(textShape);
        gradientDrawable.setColor(Color.TRANSPARENT);
        gradientDrawable.setStroke(rim, selectColor);
        gradientDrawable.setCornerRadius(Utils.dp2px(getContext(), roundCorner));
        gradientDrawable.setAlpha(textAlpha);//设置透明度
        textView.setBackground(gradientDrawable);
    }

    private void unselected(AppCompatTextView textView) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(textShape);
        gradientDrawable.setColor(Color.TRANSPARENT);
        gradientDrawable.setStroke(rim, unselectedColor);
        gradientDrawable.setCornerRadius(Utils.dp2px(getContext(), roundCorner));
        gradientDrawable.setAlpha(textAlpha);//设置透明度
        textView.setBackground(gradientDrawable);
    }

    /* START ----------------------------------SET--GET------------------------------------------- */

    /**
     * 设置数据源
     * 显示指示器长度
     *
     * @param totalCount 数据总数
     */
    protected void setTotalCount(int totalCount) {
        if (totalCount == 0) return;
        observer.setInfo(String.valueOf(totalCount));
    }

    /**
     * 设置指示器显示数量
     */
    protected void setCount(int count) {
        this.count = count;
    }

    /**
     * 设置总页数
     *
     * @param pageCount 页数
     */
    protected void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    /**
     * 事件发生改变接口
     */
    protected void setOnChangedListener(OnChangedListener onChangedListener) {
        this.onChangedListener = onChangedListener;
    }

    /**
     * 移除
     */
    protected void getRemoveListener() {
        observer.removeListener(listener);
    }

    /* END ------------------------------------SET--GET------------------------------------------- */

}
