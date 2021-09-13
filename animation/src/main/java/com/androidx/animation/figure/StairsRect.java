package com.androidx.animation.figure;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.animation.DecelerateInterpolator;

import com.androidx.animation.base.BaseAnimatorState;

/**
 * @createDate 2021/09/13
 */
public class StairsRect extends BaseAnimatorState {
    /**
     * 台阶总数
     */
    private final int FLOOR_NUM = 5;
    /**
     * 当前台阶数
     */
    private volatile int floorNum = 0;
    private volatile float animatedValue = 0;
    private Paint paint;
    private float r;
    private RectF rectF;

    @Override
    protected int getStateCount() {
        return FLOOR_NUM;
    }

    @Override
    protected void initParams(Context context, Paint paint) {
        this.paint = paint;
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        r = getAllSize();
        rectF = new RectF();
    }

    @Override
    protected void onComputeUpdateValue(ValueAnimator animation, float animatedValue, int state) {
        floorNum = state;
        this.animatedValue = animatedValue;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 台阶高度
        float floorHeight = r * 2 / FLOOR_NUM;
        float space = floorHeight * 0.5f;
        // 起点
        float startXP = getViewCenterX() - r;
        float startYP = getViewCenterY() + r;

        // 清
        rectF.setEmpty();
        for (int i = 0; i < FLOOR_NUM; i++) {
            // 限制层
            if (i > floorNum) break;
            float top = startYP - (i + 1) * floorHeight + space;
            if (i == floorNum) {// 当前台阶
                rectF.set(
                        startXP,
                        top,
                        (startXP + (i + 1) * floorHeight) * animatedValue,
                        startYP - i * floorHeight);
            } else {// 非当前台阶
                rectF.set(
                        startXP,
                        top,
                        startXP + (i + 1) * floorHeight,
                        startYP - i * floorHeight);
            }
            canvas.drawRect(rectF, paint);
        }
    }

    @Override
    protected void prepareStart(ValueAnimator animation) {
        /* 动画间隔时长 */
        animation.setDuration(ceil(getAnimationDuration() * 0.5));
        animation.setInterpolator(new DecelerateInterpolator());
    }

    @Override
    protected void prepareEnd() {
        floorNum = 0;
        animatedValue = 0;
    }
}
