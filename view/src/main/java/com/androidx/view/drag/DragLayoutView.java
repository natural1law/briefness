package com.androidx.view.drag;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;

public class DragLayoutView extends LinearLayoutCompat {

    private final OnDragListener listener = (view, dragEvent) -> {
        switch (dragEvent.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:

                break;
            case DragEvent.ACTION_DRAG_ENTERED:

                break;
            case DragEvent.ACTION_DRAG_ENDED:

                break;
            default:
                Log.i("dragId", String.valueOf(view));
        }
        return true;
    };

    public DragLayoutView(@NonNull Context context) {
        super(context);
    }

    public DragLayoutView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DragLayoutView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            view.setOnDragListener(listener);
            view.setOnLongClickListener(v -> {
                v.startDragAndDrop(null, new DragShadowBuilder(v), v, 0);
                return false;
            });
            Log.i("childId", String.valueOf(view));
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        //如果长按拖动 加上这句
//            if (!isLongClicked) return false;
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_MOVE: {
//                // 1. 添加到 Window 中
//                if (!dragDeleteView.isAttachedToWindow()) {
//                    dragDeleteView.bindAnchorView(anchorView);
//                    wm.addView(dragDeleteView, params);
//                    v.getParent().requestDisallowInterceptTouchEvent(true);
//                    v.setVisibility(View.INVISIBLE);
//                }
//                // 2. 更新坐标位置
//                dragDeleteView.updateFingerPoint(event.getRawX(), event.getRawY());
//                break;
//            }
//            case MotionEvent.ACTION_CANCEL:
//            case MotionEvent.ACTION_UP: {
//                if (dragDeleteView.isOverThresholdHeight()) {
//                    wm.removeView(dragDeleteView);
//                    callback.onDelete();
//                } else {
//                    dragDeleteView.recover();
//                }
//                v.getParent().requestDisallowInterceptTouchEvent(false);
//                isLongClicked = false;
//                break;
//            }
//        }
//        return false;
//    }
//
//    /**
//     * 供 DragDeleteTouchPerformerInternal 内部调用
//     * <p>
//     * 恢复原先位置
//     */
//    private void recover() {
//        final float deltaX = mCurPoint.x - mStartPoint.x;
//        final float deltaY = mCurPoint.y - mStartPoint.y;
//        ValueAnimator anim = ValueAnimator.ofFloat(1f, 0f).setDuration(500);
//        anim.setInterpolator(new OvershootInterpolator());
//        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                mCurPoint.x = mStartPoint.x + deltaX * (float) animation.getAnimatedValue();
//                mCurPoint.y = mStartPoint.y + deltaY * (float) animation.getAnimatedValue();
//                invalidate();
//            }
//        });
//        anim.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                mCallbackInternal.onRecovered();
//            }
//        });
//        anim.start();
//    }
//
//    /**
//     * 判断是否拖拽超过了的阈值
//     */
//    public boolean isOverThresholdHeight() {
//        final int[] location = new int[2];
//        dragDeleteView.getLocationOnScreen(location);
//        //获得宽度
//        int width = dragDeleteView.getMeasuredWidth();
//        //获得高度
//        int height = dragDeleteView.getMeasuredHeight();
//        float imgY = mCurPoint.y + mAnchorBitmap.getHeight() * 0.8f;
//        float imgX = mCurPoint.x + mAnchorBitmap.getWidth() * 0.8f;
//        return imgX > (location[0]) && (imgX < location[0] + width) && imgY > (location[1]) && imgY < (location[1] + height);
//
//    }

}
