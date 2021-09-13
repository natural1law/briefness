package com.androidx.animation.figure;

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
public class DoubleCircle extends BaseAnimator {
    private static final int OUTER_CIRCLE_ANGLE = 270;
    private static final int INTER_CIRCLE_ANGLE = 90;
    private Paint strokePaint;
    private RectF outerCircleRectF;
    private RectF innerCircleRectF;
    //旋转角度
    private int rotateAngle;

    @Override
    protected void initParams(Context context) {
        //最大尺寸
        float outR = getAllSize();
        //小圆尺寸
        float inR = outR * 0.6f;
        //初始化画笔
        initPaint(inR * 0.4f);
        //旋转角度
        rotateAngle = 0;
        //圆范围
        outerCircleRectF = new RectF();
        outerCircleRectF.set(getViewCenterX() - outR, getViewCenterY() - outR, getViewCenterX() + outR, getViewCenterY() + outR);
        innerCircleRectF = new RectF();
        innerCircleRectF.set(getViewCenterX() - inR, getViewCenterY() - inR, getViewCenterX() + inR, getViewCenterY() + inR);

    }

    /**
     * 初始化画笔
     */
    private void initPaint(float lineWidth) {
        strokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(lineWidth);
        strokePaint.setColor(Color.WHITE);
        strokePaint.setDither(true);
        strokePaint.setFilterBitmap(true);
        strokePaint.setStrokeCap(Paint.Cap.ROUND);
        strokePaint.setStrokeJoin(Paint.Join.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        //外圆
        canvas.drawArc(outerCircleRectF, rotateAngle % 360, OUTER_CIRCLE_ANGLE, false, strokePaint);
        //内圆
        canvas.drawArc(innerCircleRectF, 270 - rotateAngle % 360, INTER_CIRCLE_ANGLE, false, strokePaint);
        canvas.restore();
    }

    @Override
    protected void setAlpha(int alpha) {
        strokePaint.setAlpha(alpha);
    }

    @Override
    protected void prepareStart(ValueAnimator floatValueAnimator) {

    }

    @Override
    protected void prepareEnd() {

    }

    @Override
    protected void computeUpdateValue(ValueAnimator animation, @FloatRange(from = 0.0, to = 1.0) float animatedValue) {
        rotateAngle = (int) (360 * animatedValue);
    }

    @Override
    protected void setColorFilter(ColorFilter colorFilter) {
        strokePaint.setColorFilter(colorFilter);
    }
}
