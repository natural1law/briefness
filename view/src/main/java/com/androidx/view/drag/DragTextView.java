package com.androidx.view.drag;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;

/**
 * 可拖拽的按钮布局
 *
 * @date 2022/02/09
 */
public class DragTextView extends AppCompatAutoCompleteTextView {

    private float moveX;
    private float moveY;

    public DragTextView(@NonNull Context context) {
        super(context);
    }

    public DragTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DragTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    @SuppressLint("ClickableViewAccessibility")
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                moveX = event.getX();
                moveY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                setTranslationX(getX() + (event.getX() - moveX));
                setTranslationY(getY() + (event.getY() - moveY));
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
    }

}
