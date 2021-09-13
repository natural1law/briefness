package com.androidx.animation.figure;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;

import androidx.annotation.FloatRange;

import com.androidx.animation.base.BaseAnimator;

/**
 * @createDate 2021/09/13
 */
public class RotateCircle extends BaseAnimator {
    private final static int CIRCLE_NUM = 10;
    private Paint paint;
    private float outerRadius;
    private float interRadius;
    private float defaultAngle;

    @Override
    protected void initParams(Context context) {
        initPaint();
        outerRadius = getAllSize();
        interRadius = dip2px(context, 2);
    }

    private void initPaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        paint.setDither(true);
        paint.setFilterBitmap(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
    }

    private void drawCircles(Canvas canvas) {
        int all = CIRCLE_NUM;
        for (int i = 0; i < all; i++) {
            int angle = (int) (360 / all * i + defaultAngle);
            float centerX = getViewCenterX() + (outerRadius * cos(angle));
            float centerY = getViewCenterY() + (outerRadius * sin(angle));
            paint.setAlpha(255 / all * i);
            canvas.drawCircle(centerX, centerY, i + interRadius, paint);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawCircles(canvas);
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
        defaultAngle = 360 * animatedValue;
    }

    @Override
    protected void setColorFilter(ColorFilter colorFilter) {
        paint.setColorFilter(colorFilter);
    }

    protected final float cos(int num) {
        return (float) Math.cos(num * Math.PI / 180);
    }

    protected final float sin(int num) {
        return (float) Math.sin(num * Math.PI / 180);
    }
}
