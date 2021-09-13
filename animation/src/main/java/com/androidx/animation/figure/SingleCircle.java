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
public class SingleCircle extends BaseAnimator {
    private static final int OUTER_CIRCLE_ANGLE = 320;
    //最终阶段
    private static final int FINAL_STATE = 2;
    //当前动画阶段
    private int animatorState = 0;
    private Paint paint;
    private RectF rectF;
    //旋转开始角度
    private int startRotateAngle;
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
        startRotateAngle = 0;
        //圆范围
        rectF = new RectF();
        rectF.set(
                getViewCenterX() - outR,
                getViewCenterY() - outR,
                getViewCenterX() + outR,
                getViewCenterY() + outR);
    }

    /**
     * 初始化画笔
     */
    private void initPaint(float lineWidth) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(lineWidth);
        paint.setColor(Color.WHITE);
        paint.setDither(true);
        paint.setFilterBitmap(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        //外圆
        canvas.drawArc(rectF,
                startRotateAngle % 360,
                rotateAngle % 360,
                false, paint);
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
        startRotateAngle = (int) (360 * animatedValue);
        switch (animatorState) {
            case 0:
                rotateAngle = (int) (OUTER_CIRCLE_ANGLE * animatedValue);
                break;
            case 1:
                rotateAngle = OUTER_CIRCLE_ANGLE - (int) (OUTER_CIRCLE_ANGLE * animatedValue);
                break;
            default:
                break;
        }
    }

    @Override
    public void onAnimationRepeat(Animator animation) {
        //还原到第一阶段
        if (++animatorState > FINAL_STATE) animatorState = 0;
    }

    @Override
    protected void setColorFilter(ColorFilter colorFilter) {
        paint.setColorFilter(colorFilter);
    }
}
