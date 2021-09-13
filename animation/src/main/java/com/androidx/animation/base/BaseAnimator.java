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

/**
 * @createDate 2021/09/10
 */
public abstract class BaseAnimator implements ValueAnimator.AnimatorUpdateListener, Animator.AnimatorListener {

    private float allSize;
    private float width;
    private float height;
    private double duration = 1.0;

    private Drawable.Callback callback;
    private ValueAnimator animator;

    public static float size_default = 56.0f;
    protected static final long ANIMATION_START_DELAY = 333;
    protected static final long ANIMATION_DURATION = 1333;

    protected abstract void initParams(Context context);

    protected abstract void onDraw(Canvas canvas);

    protected abstract void setAlpha(int alpha);

    protected abstract void prepareStart(ValueAnimator animation);

    protected abstract void prepareEnd();

    protected abstract void computeUpdateValue(ValueAnimator animation, @FloatRange(from = 0.0, to = 1.0) float animatedValue);

    protected abstract void setColorFilter(ColorFilter colorFilter);

    protected void init(Context context) {
        allSize = dip2px(context, size_default * 0.5f - 12);
        width = dip2px(context, size_default);
        height = dip2px(context, size_default);
        initAnimators();
    }

    private void initAnimators() {
        animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.setRepeatCount(Animation.INFINITE);
        animator.setDuration(getAnimationDuration());
        animator.setStartDelay(getAnimationStartDelay());
        animator.setInterpolator(new LinearInterpolator());
    }

    @Override
    public void onAnimationStart(Animator animation, boolean isReverse) {

    }

    @Override
    public void onAnimationEnd(Animator animation, boolean isReverse) {

    }

    @Override
    public void onAnimationStart(Animator animator) {

    }

    @Override
    public void onAnimationEnd(Animator animator) {

    }

    @Override
    public void onAnimationCancel(Animator animator) {

    }

    @Override
    public void onAnimationRepeat(Animator animator) {

    }

    @Override
    public final void onAnimationUpdate(ValueAnimator animation) {
        computeUpdateValue(animation, (float) animation.getAnimatedValue());
        if (callback != null) callback.invalidateDrawable(null);
    }

    protected static float dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return dpValue * scale;
    }
    /* START ------------------------------------FUNCTION-----------------------------------------*/

    protected void draw(Canvas canvas) {
        onDraw(canvas);
    }

    protected void start() {
        if (animator.isStarted()) {
            return;
        }
        animator.addUpdateListener(this);
        animator.setRepeatCount(Animation.INFINITE);
        animator.setDuration(getAnimationDuration());
        prepareStart(animator);
        animator.start();
    }

    protected void stop() {
        animator.removeAllUpdateListeners();
        animator.removeAllListeners();
        animator.setRepeatCount(0);
        animator.setDuration(0);
        prepareEnd();
        animator.end();
    }

    protected boolean isRunning() {
        return animator.isRunning();
    }

    /* END --------------------------------------FUNCTION-----------------------------------------*/

    /* START ------------------------------------SET----------------------------------------------*/

    public void setDuration(double duration) {
        if (duration <= 0) this.duration = 1.0f;
        else this.duration = duration;
    }

    protected void setCallback(Drawable.Callback callback) {
        this.callback = callback;
    }

    /* END --------------------------------------SET----------------------------------------------*/

    /* START ------------------------------------GET----------------------------------------------*/

    public float getAllSize() {
        return allSize;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    protected final float getViewCenterX() {
        return getWidth() * 0.5f;
    }

    protected final float getViewCenterY() {
        return getHeight() * 0.5f;
    }

    protected long getAnimationStartDelay() {
        return ANIMATION_START_DELAY;
    }

    protected long getAnimationDuration() {
        return ceil(ANIMATION_DURATION * duration);
    }

    protected static long ceil(double value) {
        return (long) Math.ceil(value);
    }

    /* END --------------------------------------GET----------------------------------------------*/
}
