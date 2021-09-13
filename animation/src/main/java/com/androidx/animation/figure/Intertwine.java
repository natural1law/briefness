package com.androidx.animation.figure;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.animation.AccelerateInterpolator;

import androidx.annotation.FloatRange;

import com.androidx.animation.base.BaseAnimatorBall;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @createDate 2021/09/13
 */
public class Intertwine extends BaseAnimatorBall {
    //最终阶段
    private static final int FINAL_STATE = 1;
    private float ballR;
    private Path path;
    //当前动画阶段
    private int animatorState = 0;

    @Override
    protected void initParams(Context context) {
        ballR = getAllSize();
        path = new Path();
        initPaint(5);
        initPoints(ballR);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawBall(canvas);
    }

    /**
     * 绘制小球
     */
    private void drawBall(Canvas canvas) {
        canvas.save();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        super.drawBall(canvas, path, paint);
        canvas.restore();
    }

    @Override
    protected void prepareStart(ValueAnimator floatValueAnimator) {

    }

    @Override
    protected void prepareEnd() {

    }

    @Override
    protected void computeUpdateValue(ValueAnimator animation, @FloatRange(from = 0.0, to = 1.0) float animatedValue) {
        switch (animatorState) {
            case 1:
                animation.setInterpolator(new AccelerateInterpolator());
                AtomicInteger i1 = new AtomicInteger();
                ballPoints.forEach(point -> {
                    if (2 <= i1.get() && i1.get() <= 7) {
                        point.setOffsetX(-ballR * (1 - animatedValue));
                        point.setOffsetY(-ballR * (1 - animatedValue));
                    } else {
                        point.setOffsetX(ballR * (1 - animatedValue));
                        point.setOffsetY(ballR * (1 - animatedValue));
                    }
                    i1.getAndIncrement();
                });
                break;
            case 0:
                animation.setInterpolator(new AccelerateInterpolator());
                AtomicInteger i2 = new AtomicInteger();
                ballPoints.forEach(point -> {
                    if (2 <= i2.get() && i2.get() <= 7) {
                        point.setOffsetX(-ballR * (animatedValue));
                        point.setOffsetY(-ballR * (animatedValue));
                    } else {
                        point.setOffsetX(ballR * (animatedValue));
                        point.setOffsetY(ballR * (animatedValue));
                    }
                    i2.getAndIncrement();
                });
                break;
        }
    }

    @Override
    public void onAnimationRepeat(Animator animation) {
        if (++animatorState > FINAL_STATE) {//还原到第一阶段
            animatorState = 0;
            ballPoints.forEach(point -> {
                point.setOffsetY(0);
                point.setOffsetX(0);
            });
        }
    }

}
