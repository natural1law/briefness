package com.androidx.view.pages;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.androidx.view.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class PaginationIndicatorTop extends FrameLayout implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private AppCompatSpinner mPerPageCountSpinner;
    private ArrayAdapter<String> mPerPageCountAdapter;

    private OnChangedListener mListener;

    private int[] mPerPageCountChoices = {5, 10, 20, 30, 50, 100};
    private int mCurrentPagePos = 1;
    private int mLastPagePos = 0;
    private int mTotalPageCount;
    private int mTotalCount;
    private int mPerPageCount = 10;
    private int mNumberTipShowCount;  // 奇数: 数字指示器的数量

    private LinearLayoutCompat mNumberLlt;
    private AppCompatTextView[] mNumberTipTextViewArray;

    protected static int sWidth;
    protected static int sColor_selected;
    protected static int sColor_unselected;
    protected static float sTextSize;
    private GradientDrawable mDrawableSelected;
    private GradientDrawable mDrawableUnselected;
    private LayerDrawable mSpinnerDrawable;

    /**
     * 设置分页控件中间的数字显示个数
     */
    public void setNumberTipShowCount(int numberTipShowCount) {
        mNumberTipShowCount = numberTipShowCount;
        updateNumberLlt();
    }

    /**
     * 设置"x条/页"的spinner的选项源
     */
    public void setPerPageCountChoices(int[] perPageCountChoices) {
        this.mPerPageCountChoices = perPageCountChoices;
        initSpinner();
    }

    public void setListener(OnChangedListener mListener) {
        this.mListener = mListener;
    }

    public PaginationIndicatorTop(Context context) {
        this(context, null);
    }

    public PaginationIndicatorTop(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PaginationIndicatorTop(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        @SuppressLint("CustomViewStyleable")
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PaginationIndicator);
        sColor_selected = a.getColor(R.styleable.PaginationIndicator_selected_color, getContext().getResources().getColor(R.color.indicator_rect_selected, null));
        sColor_unselected = a.getColor(R.styleable.PaginationIndicator_unselected_color, getContext().getResources().getColor(R.color.indicator_rect_unselected, null));
        mNumberTipShowCount = a.getInteger(R.styleable.PaginationIndicator_number_tip_count, 9);
        sTextSize = a.getDimensionPixelSize(R.styleable.PaginationIndicator_text_size, sp2px(getContext(), 16));
        sWidth = a.getDimensionPixelSize(R.styleable.PaginationIndicator_rect_size, 0);

        if (sWidth == 0) {
            sWidth = dp2px(getContext(), 32);
        }
        a.recycle();

        init();
    }

    @SuppressLint("InflateParams")
    private void init() {
        LinearLayoutCompat mControllerView = (LinearLayoutCompat) LayoutInflater.from(getContext()).inflate(R.layout.pagination_indicator_top, null);
        mNumberLlt = mControllerView.findViewById(R.id.number_llt);
        mPerPageCountSpinner = mControllerView.findViewById(R.id.per_page_count_spinner);
        initSpinner();

        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addView(mControllerView, layoutParams);

        refreshView();
    }

    /**
     * 刷新分页器子组件相关字体颜色属性等
     */
    @SuppressWarnings("SuspiciousNameCombination")
    @SuppressLint("UseCompatLoadingForDrawables")
    void refreshView() {
        if (mSpinnerDrawable == null) {
            mSpinnerDrawable = (LayerDrawable) getResources().getDrawable(R.drawable.pagination_spinner, null);
        }
        GradientDrawable drawable = (GradientDrawable) mSpinnerDrawable.findDrawableByLayerId(R.id.layer1);
        drawable.setStroke(2, sColor_selected);

        if (mDrawableSelected == null) {
            mDrawableSelected = (GradientDrawable) getResources().getDrawable(R.drawable.pagination_shape_round_rect_selected, null);
            mDrawableUnselected = (GradientDrawable) getResources().getDrawable(R.drawable.pagination_shape_round_rect_unselected, null);
        }
        mDrawableSelected.setStroke(2, sColor_selected);
        mDrawableUnselected.setStroke(2, sColor_unselected);

        StateListDrawable enableSelectorDrawable1 = new StateListDrawable();
        GradientDrawable drawableSelected1 = (GradientDrawable) getResources().getDrawable(R.drawable.pagination_shape_round_rect_selected, null);
        drawableSelected1.setStroke(2, sColor_selected);
        GradientDrawable drawableUnselected1 = (GradientDrawable) getResources().getDrawable(R.drawable.pagination_shape_round_rect_unselected, null);
        drawableUnselected1.setStroke(2, sColor_unselected);
        enableSelectorDrawable1.addState(new int[]{android.R.attr.state_enabled}, drawableSelected1);
        enableSelectorDrawable1.addState(new int[]{-android.R.attr.state_enabled}, drawableUnselected1);

        StateListDrawable enableSelectorDrawable2 = new StateListDrawable();
        GradientDrawable drawableSelected2 = (GradientDrawable) getResources().getDrawable(R.drawable.pagination_shape_round_rect_selected, null);
        drawableSelected2.setStroke(2, sColor_selected);
        GradientDrawable drawableUnselected2 = (GradientDrawable) getResources().getDrawable(R.drawable.pagination_shape_round_rect_unselected, null);
        drawableUnselected2.setStroke(2, sColor_unselected);
        enableSelectorDrawable2.addState(new int[]{android.R.attr.state_enabled}, drawableSelected2);
        enableSelectorDrawable2.addState(new int[]{-android.R.attr.state_enabled}, drawableUnselected2);

        mPerPageCountSpinner.setBackground(mSpinnerDrawable);
        mPerPageCountSpinner.getLayoutParams().height = sWidth;
    }

    private void initSpinner() {
        if (mPerPageCountAdapter == null) {
            mPerPageCountAdapter = new CustomArrayAdapter(getContext());
            mPerPageCountSpinner.setAdapter(mPerPageCountAdapter);
            mPerPageCountSpinner.setOnItemSelectedListener(this);
        } else {
            mPerPageCountAdapter.clear();
        }
        for (int perPageCountChoice : mPerPageCountChoices) {
            mPerPageCountAdapter.add(perPageCountChoice + "条/页");
        }
        mPerPageCountSpinner.setSelection(0);
    }

    /**
     * 设置数据源总数量
     */
    @SuppressLint("SetTextI18n")
    public void setTotalCount(int totalCount) {
        this.mTotalCount = totalCount;
        notifyChange();
    }


    private void notifyChange() {
        initIndicator();
        updateNumberLlt();
    }

    private void initIndicator() {
        mCurrentPagePos = 1;
        mLastPagePos = 0;
        if (mTotalCount == 0) {
            mTotalPageCount = 0;
        } else {
            mTotalPageCount = mTotalCount % mPerPageCount > 0 ? mTotalCount / mPerPageCount + 1 : mTotalCount / mPerPageCount;
            if (mListener != null) {
                mListener.onPerPageCountChanged(mPerPageCount);
                mListener.onPageSelectedChanged(mCurrentPagePos, mLastPagePos, mTotalPageCount, mTotalCount);
            }
        }

    }

    /**
     * 选中页发生变化时调用 更新按钮、数字指示器状态即回调监听器
     */
    private void updateState(int lastPos) {
        if (mListener != null) {
            mListener.onPageSelectedChanged(mCurrentPagePos, lastPos, mTotalPageCount, mTotalCount);
        }
        updateNumberLlt();
    }

    /**
     * 生成数字指示器view及计算需要渲染的数字起始到结束
     */
    private void updateNumberLlt() {
        if (mTotalCount == 0) {
            mNumberLlt.removeAllViews();
            return;
        }
        geneNumberTextView();
        if (mTotalPageCount > mNumberTipShowCount) {
            int start, end;
            int half = mNumberTipShowCount / 2;
            start = mCurrentPagePos - half;
            end = mCurrentPagePos + half;
            if (start <= 0) {
                // 越过"数字1"的位置了  把超出部分补偿给end
                end = end + Math.abs(start) + 1;
                start = 1;
            } else if (end > mTotalPageCount) {
                // 越过"总页数数字"的位置了  把超出部分补偿给start
                start = start - Math.abs(mTotalPageCount - end);
                end = mTotalPageCount;
            }
            updateNumberText(start, end);
        } else {
            // 总页数小于数字指示器数量，则直接以总页数的大小来刷新
            updateNumberText(1, mNumberTipTextViewArray.length);
        }
    }

    /**
     * 根据起始数字和结束数字填充数字指示器的textview
     */
    @SuppressLint("SetTextI18n")
    private void updateNumberText(int start, int end) {
        for (int i = 0; i < end - start + 1; i++) {
            TextView textView = mNumberTipTextViewArray[i];
            textView.setText((start + i) + "");
            if (start + i == mCurrentPagePos) {
                textView.setSelected(true);
                textView.setTextColor(sColor_selected);
            } else {
                textView.setSelected(false);
                textView.setTextColor(sColor_unselected);
            }
        }
    }


    private void geneNumberTextView() {
        int count = Math.min(mNumberTipShowCount, mTotalPageCount);
        if (mNumberTipTextViewArray == null) {
            mNumberTipTextViewArray = new AppCompatTextView[count];
            mNumberLlt.removeAllViews();
        } else if (mNumberTipTextViewArray.length != count) {
            mNumberTipTextViewArray = new AppCompatTextView[count];
            mNumberLlt.removeAllViews();
        } else {
            return;
        }
        for (int i = 0; i < mNumberTipTextViewArray.length; i++) {
            AppCompatTextView textView = new AppCompatTextView(getContext());
            StateListDrawable selectSelectorDrawable = new StateListDrawable();
            selectSelectorDrawable.addState(new int[]{android.R.attr.state_selected}, mDrawableSelected);
            selectSelectorDrawable.addState(new int[]{-android.R.attr.state_selected}, mDrawableUnselected);
            textView.setBackgroundDrawable(selectSelectorDrawable);
            mNumberTipTextViewArray[i] = textView;
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, sTextSize);
            //noinspection SuspiciousNameCombination
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(sWidth, sWidth);
            if (i > 0 && i < mNumberTipTextViewArray.length)
                params.leftMargin = 2;
            textView.setOnClickListener(this);
            mNumberLlt.addView(textView, params);
        }
    }

    @Override
    public void onClick(View v) {
        int lastPos = mCurrentPagePos;

        if (v.getId() == R.id.next_btn) {
            if (mCurrentPagePos == mTotalPageCount)  // 已经是最后一页了
                return;
            mLastPagePos = mCurrentPagePos;
            mCurrentPagePos++;
        } else if (v.getId() == R.id.last_btn) {
            if (mCurrentPagePos == 1)  // 已经是第一页了
                return;
            mLastPagePos = mCurrentPagePos;
            mCurrentPagePos--;
        } else {
            // 点击了中间的数字指示器
            int clickNumber = Integer.parseInt(((TextView) v).getText().toString());
            if (clickNumber == mCurrentPagePos) {
                return;
            }
            mLastPagePos = mCurrentPagePos;
            mCurrentPagePos = clickNumber;
        }
        updateState(lastPos);
    }

    /**
     * "x条/每页"Spinner选中值改变时触发
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mPerPageCount = mPerPageCountChoices[position];
        if (this.mListener != null) {
            mListener.onPerPageCountChanged(mPerPageCount);
        }
        notifyChange();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * 跳到指定页码
     */
    public void skip2Pos(int position) {
        mLastPagePos = mCurrentPagePos;
        mCurrentPagePos = position;
        updateState(mLastPagePos);
    }


    public interface OnChangedListener {
        /**
         * 选中页改变时回调
         *
         * @param currentPagePos 当前选中的页码
         * @param lastPagePos    上一个选中的页码
         * @param totalPageCount 总页数
         * @param total          数据源总量
         */
        void onPageSelectedChanged(int currentPagePos, int lastPagePos, int totalPageCount, int total);

        /**
         * "x条/页"选中值改变时触发的回调
         */
        void onPerPageCountChanged(int perPageCount);
    }

    // 为了自定义Spinner字体颜色等
    static class CustomArrayAdapter extends ArrayAdapter<String> {
        private final Context mContext;
        private List<String> mStringArray = new ArrayList<>();

        public CustomArrayAdapter(Context context) {
            super(context, android.R.layout.simple_spinner_item);
            mContext = context;
        }

        @SuppressWarnings({"unused", "RedundantSuppression"})
        public CustomArrayAdapter(Context context, List<String> stringArray) {
            super(context, android.R.layout.simple_spinner_item, stringArray);
            mContext = context;
            mStringArray = stringArray;
        }

        @Override
        public void add(String object) {
            super.add(object);
            mStringArray.add(object);
        }

        @Override
        public void addAll(String... items) {
            super.addAll(items);
            mStringArray.addAll(Arrays.asList(items));
        }

        @Override
        public void addAll(Collection<? extends String> collection) {
            super.addAll(collection);
            mStringArray.addAll(collection);
        }

        @Override
        public View getDropDownView(int position, View convertView, @NotNull ViewGroup parent) {
            //修改Spinner展开后的字体颜色
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(mContext);
                convertView = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
            }

            //此处text1是Spinner默认的用来显示文字的TextView
            TextView tv = convertView.findViewById(android.R.id.text1);
            tv.setText(mStringArray.get(position));
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, sTextSize);
            tv.setTextColor(sColor_selected);

            return convertView;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // 修改Spinner选择后结果的字体颜色
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(mContext);
                convertView = inflater.inflate(android.R.layout.simple_spinner_item, parent, false);
            }

            //此处text1是Spinner默认的用来显示文字的TextView
            TextView tv = convertView.findViewById(android.R.id.text1);
            tv.setText(mStringArray.get(position));
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, sTextSize);
            tv.setTextColor(sColor_selected);
            return convertView;
        }

    }

    /**
     * dp转换成px
     */
    public static int dp2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
