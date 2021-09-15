package com.androidx.animation.figure.clock;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;

import androidx.annotation.FloatRange;

import com.androidx.animation.base.BaseAnimator;

public class CircleBuilder extends BaseAnimator {
    private static final float DEFAULT_ANGLE = -90;
    private RectF mInnerCircleRectF;
    private Paint mPaint;
    private float mStartAngle;
    private float mEndAngle;
    private boolean mIsFirstState = true;

    private void initValues(Context context) {
        float allSize = getAllSize();
        float innerRadius = allSize - dip2px(context, 3);
        mInnerCircleRectF = new RectF();

        mStartAngle = DEFAULT_ANGLE;
        mEndAngle = DEFAULT_ANGLE;

        float viewCenterX = getViewCenterX();
        float viewCenterY = getViewCenterY();

        mInnerCircleRectF.set(viewCenterX - innerRadius, viewCenterY - innerRadius, viewCenterX + innerRadius, viewCenterY + innerRadius);
    }

    @Override
    public void initParams(Context context) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);
        initValues(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.drawArc(mInnerCircleRectF, mStartAngle, mEndAngle - mStartAngle, true, mPaint);
        canvas.restore();
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    protected void prepareStart(ValueAnimator floatValueAnimator) {

    }

    @Override
    protected void prepareEnd() {

    }

    @Override
    protected void computeUpdateValue(ValueAnimator animation, @FloatRange(from = 0.0, to = 1.0) float animatedValue) {
        if (mIsFirstState) mEndAngle = animatedValue * 360 + DEFAULT_ANGLE;
        else mStartAngle = animatedValue * 360 + DEFAULT_ANGLE;
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public void onAnimationStart(Animator animation) {
        mStartAngle = DEFAULT_ANGLE;
        mEndAngle = DEFAULT_ANGLE;
    }

    @Override
    public void onAnimationEnd(Animator animation) {
        mStartAngle = DEFAULT_ANGLE;
        mEndAngle = DEFAULT_ANGLE;
    }

    @Override
    public void onAnimationCancel(Animator animation) {
        mStartAngle = DEFAULT_ANGLE;
        mEndAngle = DEFAULT_ANGLE;
    }

    @Override
    public void onAnimationRepeat(Animator animation) {
        mIsFirstState = !mIsFirstState;
        if (mIsFirstState) {
            mStartAngle = DEFAULT_ANGLE;
            mEndAngle = DEFAULT_ANGLE;
        } else {
            mStartAngle = DEFAULT_ANGLE;
            mEndAngle = 360 + DEFAULT_ANGLE;
        }
    }

}
