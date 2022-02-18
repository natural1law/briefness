package com.androidx.view.drag;

import android.content.Context;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.androidx.view.R;
import com.huantansheng.easyphotos.ui.widget.PressedImageView;

public class DragView extends ViewGroup {

    private static final int COLUMNS = 2;
    private static final int ROWS = 3;
    private final DragListener listener = new DragListener();
    private View draggedView;

    public DragView(Context context) {
        this(context, null);
    }

    public DragView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public DragView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setChildrenDrawingOrderEnabled(true);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        int childLeft;
        int childTop;
        int childWidth = getWidth() / COLUMNS;
        int childHeight = getHeight() / ROWS;
        // 先把childView摆放在同一个位置，然后再一个个进行偏移摆放为Grid
        // 这样处理主要是方便再拖拽时实现所有childView重新排列为Grid
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            childLeft = i % 2 * childWidth;
            childTop = i / 2 * childHeight;
            child.layout(0, 0, childWidth, childHeight);
            child.setTranslationX(childLeft);
            child.setTranslationY(childTop);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            view.setOnLongClickListener(v -> {
                draggedView = v;
                v.startDragAndDrop(null, new DragShadowBuilder(v), v, 0);
                return false;
            });
            view.setOnDragListener(listener);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int specWidth = MeasureSpec.getSize(widthMeasureSpec);
        int specHeight = MeasureSpec.getSize(heightMeasureSpec);
        int childWidth = specWidth / COLUMNS;
        int childHeight = specHeight / ROWS;
        measureChildren(MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY));
        setMeasuredDimension(specWidth, specHeight);
    }

    private final class DragListener implements OnDragListener {

        @Override
        public boolean onDrag(View view, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    if (event.getLocalState() != view) {
                        view.setBackgroundColor(getResources().getColor(R.color.code, getContext().getTheme()));
                    }
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    AppCompatImageView imageView = new PressedImageView(getContext());
                    imageView.setImageResource(R.mipmap.ic_controller_easy_photos);
                    LinearLayoutCompat linearLayoutCompat = new LinearLayoutCompat(getContext());
                    linearLayoutCompat.addView(imageView);
                    break;
            }
            return true;
        }

    }
}
