package com.androidx.animation.figure;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import androidx.annotation.FloatRange;

import com.androidx.animation.base.BaseAnimatorBall;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @createDate 2021/09/13
 */
public class InfectionBall extends BaseAnimatorBall {
    //动画间隔时间
    private long durationTime = 888;
    private long durationTime_1 = 222;
    private long durationTime_2 = 333;
    private long durationTime_3 = 1333;
    private long durationTime_4 = 1333;
    //最终阶段
    private static final int FINAL_STATE = 4;
    private static final int SUM_POINT_POS = 3;
    private Path path;
    //当前动画阶段
    private int animatorState = 0;
    //每个小球的偏移量
    private float canvasTranslateOffset;

    @Override
    protected void initParams(Context context) {
        float ballR = getAllSize() / SUM_POINT_POS;
        canvasTranslateOffset = getWidth() / SUM_POINT_POS;
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
        canvas.translate(0, -canvasTranslateOffset);
        super.drawBall(canvas, path, paint);
        canvas.restore();
    }

    @Override
    protected void prepareStart(ValueAnimator floatValueAnimator) {
        durationTime = ceil(getAnimationDuration() * 0.7);
        durationTime_1 = ceil(getAnimationDuration() * 0.2);
        durationTime_2 = ceil(getAnimationDuration() * 0.3);
        durationTime_3 = getAnimationDuration();
        durationTime_4 = getAnimationDuration();
    }

    @Override
    protected void prepareEnd() {

    }

    @Override
    protected void computeUpdateValue(ValueAnimator animation, @FloatRange(from = 0.0, to = 1.0) float animatedValue) {
        float offset = canvasTranslateOffset;
        switch (animatorState) {
            case 0:
                animation.setDuration(durationTime);
                animation.setInterpolator(new AccelerateInterpolator());
                ballPoints.get(2).setOffsetY(animatedValue * offset);
                ballPoints.get(3).setOffsetY(animatedValue * offset);
                ballPoints.get(4).setOffsetY(animatedValue * offset);
                break;
            case 1:
                animation.setDuration(durationTime_1);
                animation.setInterpolator(new LinearInterpolator());
                ballPoints.get(5).setOffsetY(animatedValue * offset);
                ballPoints.get(6).setOffsetY(animatedValue * offset);
                ballPoints.get(7).setOffsetY(animatedValue * offset);
                ballPoints.get(1).setOffsetY(animatedValue * offset);
                ballPoints.get(0).setOffsetY(animatedValue * offset);
                ballPoints.get(11).setOffsetY(animatedValue * offset);
                break;
            case 2:
                animation.setDuration(durationTime_2);
                animation.setInterpolator(new AccelerateInterpolator());
                AtomicInteger integer = new AtomicInteger();
                ballPoints.forEach(point -> {
                    if (integer.get() > 10 || integer.get() < 8) {
                        ballPoints.get(integer.get()).setOffsetY(animatedValue * offset + offset);
                    } else {
                        ballPoints.get(integer.get()).setOffsetY(animatedValue * offset);
                    }
                    integer.getAndIncrement();
                });
                break;
            case 3:
                animation.setDuration(durationTime_3);
                animation.setInterpolator(new DecelerateInterpolator());

                ballPoints.get(8).setOffsetY(animatedValue * offset + offset);
                ballPoints.get(9).setOffsetY(animatedValue * offset + offset);
                ballPoints.get(10).setOffsetY(animatedValue * offset + offset);

                ballPoints.get(5).setOffsetX(animatedValue * offset);
                ballPoints.get(6).setOffsetX(animatedValue * offset);
                ballPoints.get(7).setOffsetX(animatedValue * offset);
                ballPoints.get(1).setOffsetX(-animatedValue * offset);
                ballPoints.get(0).setOffsetX(-animatedValue * offset);
                ballPoints.get(11).setOffsetX(-animatedValue * offset);
                break;
            case 4:
                animation.setDuration(durationTime_4);
                paint.setAlpha((int) ((1 - animatedValue) * 255));
                break;
            default:
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
            paint.setAlpha(255);
        }
    }

}
