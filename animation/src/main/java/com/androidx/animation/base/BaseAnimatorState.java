package com.androidx.animation.base;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;

/**
 * @createDate 2021/09/13
 */
public abstract class BaseAnimatorState extends BaseAnimator {
    /**
     * 当前动画阶段
     */
    private int animatorState = 0;
    private Paint paint;

    /**
     * 总阶段数
     *
     * @return 数字
     */
    protected abstract int getStateCount();

    /**
     * 初始化画笔
     */
    protected abstract void initParams(Context context, Paint paint);

    /**
     * 动画数值更新
     *
     * @param state 当前状态
     */
    protected abstract void onComputeUpdateValue(ValueAnimator animation, float animatedValue, int state);

    @Override
    protected final void initParams(Context context) {
        initPaint();
        this.initParams(context, paint);
    }

    private void initPaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(6);
        paint.setColor(Color.BLACK);
        paint.setDither(true);
        paint.setFilterBitmap(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
    }

    @Override
    protected void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    @Override
    protected void computeUpdateValue(ValueAnimator animation, float animatedValue) {
        this.onComputeUpdateValue(animation, animatedValue, animatorState);
    }

    @Override
    public void onAnimationRepeat(Animator animation) {
        int iFinalState = getStateCount();
        //还原到第一阶段
        if (++animatorState > iFinalState) animatorState = 0;
    }

    @Override
    protected void setColorFilter(ColorFilter colorFilter) {
        paint.setColorFilter(colorFilter);
    }

}
