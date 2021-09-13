package com.androidx.animation.figure;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.androidx.animation.base.BaseAnimatorState;

/**
 * @createDate 2021/09/13
 */
public class ChartRect extends BaseAnimatorState {
    /**
     * 总数
     */
    private final int SUM_NUM = 5;
    /**
     * 当前
     */
    private volatile int stateNum = 0;
    private volatile float animatedValue = 0;
    private Paint paint;
    private float r;
    private RectF rectF;

    @Override
    protected int getStateCount() {
        return SUM_NUM + 1;
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
        this.stateNum = state;
        this.animatedValue = animatedValue;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 高度
        float floorHeight = r * 2 / SUM_NUM;
        float space = floorHeight * 0.5f;
        // 起点
        float startXP = getViewCenterX() - r;
        float startYP = getViewCenterY() + r;

        // 清
        rectF.setEmpty();
        for (int i = 0; i < SUM_NUM; i++) {
            if (i > stateNum) break;// 限制层
            float offsetHV = (0.5f - Math.abs(animatedValue - 0.5f)) * floorHeight;
            int j = i % 3;
            // 当前
            float right = startXP + (i + 1) * floorHeight - space;
            if (i == stateNum) {
                rectF.set(
                        startXP + i * floorHeight,
                        startYP - (j + 1) * floorHeight * animatedValue,
                        right,
                        startYP);
            } else {
                rectF.set(
                        startXP + i * floorHeight,
                        startYP - (j + 1) * floorHeight - offsetHV,
                        right,
                        startYP);
            }
            canvas.drawRect(rectF, paint);
        }
    }

    @Override
    protected void prepareStart(ValueAnimator animation) {
        /* 动画间隔时长 */
        animation.setDuration(ceil(getAnimationDuration() * 0.4));
    }

    @Override
    protected void prepareEnd() {
        stateNum = 0;
        animatedValue = 0;
    }
}
