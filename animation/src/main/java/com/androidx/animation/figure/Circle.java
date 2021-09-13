package com.androidx.animation.figure;

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

/**
 * @createDate 2021/09/13
 */
public class Circle extends BaseAnimator {
    private static final float DEFAULT_ANGLE = -90;
    private RectF rectF;
    private Paint paint;
    private float startAngle;
    private float endAngle;
    private boolean isFirstState = true;

    private void initValues(Context context) {
        float allSize = getAllSize();
        float innerRadius = allSize - dip2px(context, 3);
        rectF = new RectF();

        startAngle = DEFAULT_ANGLE;
        endAngle = DEFAULT_ANGLE;

        float viewCenterX = getViewCenterX();
        float viewCenterY = getViewCenterY();

        rectF.set(viewCenterX - innerRadius,
                viewCenterY - innerRadius,
                viewCenterX + innerRadius,
                viewCenterY + innerRadius);
    }

    @Override
    protected void initParams(Context context) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        initValues(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.drawArc(rectF, startAngle, endAngle - startAngle, true, paint);
        canvas.restore();
    }

    @Override
    protected void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    @Override
    protected void prepareStart(ValueAnimator floatValueAnimator) {

    }

    @Override
    protected void prepareEnd() {

    }

    @Override
    protected void computeUpdateValue(ValueAnimator animation, @FloatRange(from = 0.0, to = 1.0) float animatedValue) {
        if (isFirstState) endAngle = animatedValue * 360 + DEFAULT_ANGLE;
        else startAngle = animatedValue * 360 + DEFAULT_ANGLE;
    }

    @Override
    protected void setColorFilter(ColorFilter colorFilter) {
        paint.setColorFilter(colorFilter);
    }

    @Override
    public void onAnimationStart(Animator animation) {
        startAngle = DEFAULT_ANGLE;
        endAngle = DEFAULT_ANGLE;
    }

    @Override
    public void onAnimationEnd(Animator animation) {
        startAngle = DEFAULT_ANGLE;
        endAngle = DEFAULT_ANGLE;
    }

    @Override
    public void onAnimationCancel(Animator animation) {
        startAngle = DEFAULT_ANGLE;
        endAngle = DEFAULT_ANGLE;
    }

    @Override
    public void onAnimationRepeat(Animator animation) {
        isFirstState = !isFirstState;
        if (isFirstState) {
            startAngle = DEFAULT_ANGLE;
            endAngle = DEFAULT_ANGLE;
        } else {
            startAngle = DEFAULT_ANGLE;
            endAngle = 360 + DEFAULT_ANGLE;
        }
    }

}
