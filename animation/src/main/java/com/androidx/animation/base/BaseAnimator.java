package com.androidx.animation.base;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

import androidx.annotation.FloatRange;
import androidx.annotation.Size;

public abstract class BaseAnimator implements ValueAnimator.AnimatorUpdateListener, Animator.AnimatorListener {
    /**
     * 外部可以修改，但是不建议
     */
    private float size = 56.0f;
    protected static final long ANIMATION_START_DELAY = 333;
    protected static final long ANIMATION_DURATION = 1333;

    private float mAllSize;
    private float mViewWidth;
    private float mViewHeight;

    private Drawable.Callback mCallback;
    private ValueAnimator mFloatValueAnimator;

    private double mDurationTimePercent = 1.0;

    public void init(Context context) {
        mAllSize = dip2px(context, size * 0.5f - 12);
        mViewWidth = dip2px(context, size);
        mViewHeight = dip2px(context, size);
        initAnimators();
    }

    private void initAnimators() {
        mFloatValueAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        mFloatValueAnimator.setRepeatCount(Animation.INFINITE);
        mFloatValueAnimator.setDuration(getAnimationDuration());
        mFloatValueAnimator.setStartDelay(getAnimationStartDelay());
        mFloatValueAnimator.setInterpolator(new LinearInterpolator());
    }

    public abstract void initParams(Context context);

    protected abstract void onDraw(Canvas canvas);

    public abstract void setAlpha(int alpha);

    protected abstract void prepareStart(ValueAnimator animation);

    protected abstract void prepareEnd();

    protected abstract void computeUpdateValue(ValueAnimator animation, @FloatRange(from = 0.0, to = 1.0) float animatedValue);

    public abstract void setColorFilter(ColorFilter colorFilter);

    public void setCallback(Drawable.Callback callback) {
        this.mCallback = callback;
    }

    public void draw(Canvas canvas) {
        onDraw(canvas);
    }

    public void start() {
        if (mFloatValueAnimator.isStarted()) {
            return;
        }
        mFloatValueAnimator.addUpdateListener(this);
        mFloatValueAnimator.addListener(this);
        mFloatValueAnimator.setRepeatCount(Animation.INFINITE);
        mFloatValueAnimator.setDuration(getAnimationDuration());
        prepareStart(mFloatValueAnimator);
        mFloatValueAnimator.start();
    }

    public void stop() {
        mFloatValueAnimator.removeAllUpdateListeners();
        mFloatValueAnimator.removeAllListeners();
        mFloatValueAnimator.setRepeatCount(0);
        mFloatValueAnimator.setDuration(0);
        prepareEnd();
        mFloatValueAnimator.end();
    }

    public boolean isRunning() {
        return mFloatValueAnimator.isRunning();
    }

    @Override
    public final void onAnimationUpdate(ValueAnimator animation) {
        computeUpdateValue(animation, (float) animation.getAnimatedValue());
        invalidateSelf();
    }

    private void invalidateSelf() {
        if (mCallback != null) {
            mCallback.invalidateDrawable(null);
        }
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {

    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }

    public void setSize(@Size float size) {
        this.size = size;
    }

    public void setDurationTimePercent(double durationTimePercent) {
        if (durationTimePercent <= 0) mDurationTimePercent = 1.0f;
        else mDurationTimePercent = durationTimePercent;
    }

    protected long getAnimationStartDelay() {
        return ANIMATION_START_DELAY;
    }

    protected long getAnimationDuration() {
        return ceil(ANIMATION_DURATION * mDurationTimePercent);
    }

    public float getIntrinsicHeight() {
        return mViewHeight;
    }

    public float getIntrinsicWidth() {
        return mViewWidth;
    }

    protected final float getViewCenterX() {
        return getIntrinsicWidth() * 0.5f;
    }

    protected final float getViewCenterY() {
        return getIntrinsicHeight() * 0.5f;
    }

    protected final float getAllSize() {
        return mAllSize;
    }

    protected static float dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return dpValue * scale;
    }

    protected static long ceil(double value) {
        return (long) Math.ceil(value);
    }
}
