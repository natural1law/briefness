package com.androidx.animation.figure;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.FloatRange;

import com.androidx.animation.base.BaseAnimatorBall;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @createDate 2021/09/13
 */
public class ElasticBall extends BaseAnimatorBall {
    //最终阶段
    private static final int FINAL_STATE = 2;
    //小球共5个位置
    private static final int SUM_POINT_POS = 5;
    //背景圆集合
    private final LinkedList<CirclePoint> circlePoints = new LinkedList<>();
    //动画间隔时间
    private long durationTime = 333;
    private float ballR;
    private Path path;
    //当前动画阶段
    private int animatorState = 0;
    //每个小球的偏移量
    private float canvasTranslateOffset;
    //当前状态是否翻转
    private boolean isReverse = false;
    //当前小球的位置
    private int currPointPos = 0;

    @Override
    protected void initParams(Context context) {
        ballR = getAllSize() / SUM_POINT_POS;
        canvasTranslateOffset = getWidth() / SUM_POINT_POS;
        path = new Path();

        initPaint(5);
        initPoints(ballR);
        initBGPoints();
    }

    /**
     * 背景圆点初始化
     */
    private void initBGPoints() {
        float centerX = getViewCenterX();
        float centerY = getViewCenterY();
        CirclePoint p_0 = new CirclePoint(centerX - canvasTranslateOffset * 2, centerY);
        CirclePoint p_1 = new CirclePoint(centerX - canvasTranslateOffset, centerY);
        CirclePoint p_2 = new CirclePoint(centerX, centerY);
        CirclePoint p_3 = new CirclePoint(centerX + canvasTranslateOffset, centerY);
        CirclePoint p_4 = new CirclePoint(centerX + canvasTranslateOffset * 2, centerY);

        p_0.setEnabled(false);//默认第一个圆不显示
        circlePoints.add(p_0);
        circlePoints.add(p_1);
        circlePoints.add(p_2);
        circlePoints.add(p_3);
        circlePoints.add(p_4);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        draw1(canvas);
        drawBall(canvas);
    }

    /**
     * 绘制小球
     */
    private void drawBall(Canvas canvas) {
        canvas.save();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        float offsetX = ((circlePoints.size() * 0.5f) * canvasTranslateOffset);
        canvas.translate(-offsetX + canvasTranslateOffset * currPointPos, 0);
        super.drawBall(canvas, path, paint);
        canvas.restore();
    }

    /**
     * 绘制背景圆
     */
    private void draw1(Canvas canvas) {
        canvas.save();
        paint.setStyle(Paint.Style.STROKE);
        circlePoints.forEach(point -> point.draw(canvas, ballR, paint));
        canvas.restore();
    }

    @Override
    protected void prepareStart(ValueAnimator floatValueAnimator) {
        durationTime = ceil(getAnimationDuration() * 0.3f);
        floatValueAnimator.setDuration(durationTime);
    }

    @Override
    protected void prepareEnd() {

    }

    @Override
    protected void computeUpdateValue(ValueAnimator animation, @FloatRange(from = 0.0, to = 1.0) float animatedValue) {
        float offset = canvasTranslateOffset;
        int currState = isReverse ? animatorState + 3 : animatorState;
        switch (currState) {
            case 0:
                animation.setDuration(durationTime);
                animation.setInterpolator(new AccelerateInterpolator());
                ballPoints.get(5).setOffsetX(animatedValue * offset);
                ballPoints.get(6).setOffsetX(animatedValue * offset);
                ballPoints.get(7).setOffsetX(animatedValue * offset);
                break;
            case 1:
                animation.setDuration(durationTime + 111);
                animation.setInterpolator(new DecelerateInterpolator());
                ballPoints.get(2).setOffsetX(animatedValue * offset);
                ballPoints.get(3).setOffsetX(animatedValue * offset);
                ballPoints.get(4).setOffsetX(animatedValue * offset);
                ballPoints.get(8).setOffsetX(animatedValue * offset);
                ballPoints.get(9).setOffsetX(animatedValue * offset);
                ballPoints.get(10).setOffsetX(animatedValue * offset);
                break;
            case 2:
                animation.setDuration(durationTime + 333);
                animation.setInterpolator(new BounceInterpolator());
                ballPoints.get(0).setOffsetX(animatedValue * offset);
                ballPoints.get(1).setOffsetX(animatedValue * offset);
                ballPoints.get(11).setOffsetX(animatedValue * offset);
                break;
            case 3:
                animation.setDuration(durationTime);
                animation.setInterpolator(new AccelerateInterpolator());
                ballPoints.get(0).setOffsetX((1 - animatedValue) * offset);
                ballPoints.get(1).setOffsetX((1 - animatedValue) * offset);
                ballPoints.get(11).setOffsetX((1 - animatedValue) * offset);
                break;
            case 4:
                animation.setDuration(durationTime + 111);
                animation.setInterpolator(new DecelerateInterpolator());
                ballPoints.get(2).setOffsetX((1 - animatedValue) * offset);
                ballPoints.get(3).setOffsetX((1 - animatedValue) * offset);
                ballPoints.get(4).setOffsetX((1 - animatedValue) * offset);
                ballPoints.get(8).setOffsetX((1 - animatedValue) * offset);
                ballPoints.get(9).setOffsetX((1 - animatedValue) * offset);
                ballPoints.get(10).setOffsetX((1 - animatedValue) * offset);
                break;
            case 5:
                animation.setDuration(durationTime + 333);
                animation.setInterpolator(new BounceInterpolator());
                ballPoints.get(5).setOffsetX((1 - animatedValue) * offset);
                ballPoints.get(6).setOffsetX((1 - animatedValue) * offset);
                ballPoints.get(7).setOffsetX((1 - animatedValue) * offset);
                break;
            default:
                break;
        }
    }

    @Override
    public void onAnimationRepeat(Animator animation) {
        if (++animatorState > FINAL_STATE) {//还原到第一阶段
            animatorState = 0;
            /* 小球位置改变 */
            if (isReverse) currPointPos--;//倒序
            else currPointPos++;//顺序

            /* 重置并翻转动画过程 */
            if (currPointPos >= SUM_POINT_POS - 1) {//倒序
                isReverse = true;
                currPointPos = SUM_POINT_POS - 2;//I Don't Know
                AtomicInteger i = new AtomicInteger();
                circlePoints.forEach(point -> {
                    point.setEnabled(i.get() == circlePoints.size() - 1);
                    i.getAndIncrement();
                });
            } else if (currPointPos < 0) {//顺序
                isReverse = false;
                currPointPos = 0;
                AtomicInteger i = new AtomicInteger();
                circlePoints.forEach(point -> {
                    point.setEnabled(i.get() != 0);
                    i.getAndIncrement();
                });
            }

            //每个阶段恢复状态，以及对背景圆的控制
            if (isReverse) {//倒序
                //恢复状态
                ballPoints.forEach(point -> point.setOffsetX(canvasTranslateOffset));
                circlePoints.get(currPointPos + 1).setEnabled(true);
            } else {//顺序
                //恢复状态
                ballPoints.forEach(point -> point.setOffsetX(0));
                circlePoints.get(currPointPos).setEnabled(false);
            }
        }
    }

}
