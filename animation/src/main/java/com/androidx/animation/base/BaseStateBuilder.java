package com.androidx.animation.base;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;

public abstract class BaseStateBuilder extends BaseAnimator {
    /**
     * 当前动画阶段
     */
    private int mCurrAnimatorState = 0;
    private Paint mPaint;

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
    public final void initParams(Context context) {
        initPaint();
        this.initParams(context, mPaint);
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(6);
        mPaint.setColor(Color.BLACK);
        mPaint.setDither(true);
        mPaint.setFilterBitmap(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    protected void computeUpdateValue(ValueAnimator animation, float animatedValue) {
        this.onComputeUpdateValue(animation, animatedValue, mCurrAnimatorState);
    }

    @Override
    public void onAnimationRepeat(Animator animation) {
        int iFinalState = getStateCount();
        if (++mCurrAnimatorState > iFinalState) {//还原到第一阶段
            mCurrAnimatorState = 0;
        }
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

}
