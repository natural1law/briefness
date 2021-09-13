package com.androidx.animation.figure;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.FloatRange;

import com.androidx.animation.base.BaseAnimator;

/**
 * @createDate 2021/09/13
 */
public class Leaf extends BaseAnimator {
    //最终阶段
    private static final int FINAL_STATE = 2;
    private Paint paint;
    //属性
    private float starOutR;
    private float starInR;
    private float starOutMidR;
    private float starInMidR;
    private float centerCircleR;
    private int rotateAngle;
    //当前动画阶段
    private int animatorState = 0;
    private Path path;

    @Override
    protected void initParams(Context context) {
        initPaint();
        //最外层半径
        starOutR = getAllSize();
        //外层贝塞尔曲线中间值
        starOutMidR = starOutR * 0.9f;
        //内层半径
        starInR = starOutR * 0.7f;
        //内层贝塞尔曲线中间值
        starInMidR = starOutR * 0.3f;
        //中心圆半径
        centerCircleR = dip2px(context, 3);
        //旋转角度
        rotateAngle = 0;
        //路径
        path = new Path();
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(2);
        paint.setColor(Color.WHITE);
        paint.setDither(true);
        paint.setFilterBitmap(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        //旋转
        canvas.rotate(rotateAngle, getViewCenterX(), getViewCenterY());
        //路径
        createStarPath(path);
        //路径加入中心圆
        path.addCircle(getViewCenterX(), getViewCenterY(), centerCircleR, Path.Direction.CW);
        //这个很关键，选择路径填充方式
        path.setFillType(Path.FillType.EVEN_ODD);
        //绘制
        canvas.drawPath(path, paint);
        canvas.restore();
    }

    /**
     * 绘制五叶草
     *
     * @param path 路径
     */
    private void createStarPath(Path path) {
        path.reset();
        int angle = 360 / 5;
        int roundSize = 5;//圆角弧度
        int offsetAngle = angle / 2;
        path.moveTo(getViewCenterX() + starOutMidR * cos(-18 - roundSize), getViewCenterY() + starOutMidR * sin(-18 - roundSize));
        for (int i = 0; i < 5; i++) {
            int value = angle * i + -18;
            path.lineTo(getViewCenterX() + starOutMidR * cos(value - roundSize), getViewCenterY() + starOutMidR * sin(value - roundSize));
            //圆角
            path.quadTo(getViewCenterX() + starOutR * cos(value), getViewCenterY() + starOutR * sin(value), getViewCenterX() + starOutMidR * cos(value + roundSize), getViewCenterY() + starOutMidR * sin(value + roundSize));
            path.lineTo(getViewCenterX() + starInR * cos(value + offsetAngle - roundSize), getViewCenterY() + starInR * sin(value + offsetAngle - roundSize));
            //内圆角
            path.quadTo(getViewCenterX() + starInMidR * cos(value + offsetAngle), getViewCenterY() + starInMidR * sin(value + offsetAngle), getViewCenterX() + starInR * cos(value + offsetAngle + roundSize), getViewCenterY() + starInR * sin(value + offsetAngle + roundSize));
        }
        path.close();
    }

    @Override
    protected void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    @Override
    protected void prepareStart(ValueAnimator floatValueAnimator) {
        floatValueAnimator.setInterpolator(new DecelerateInterpolator());
    }

    @Override
    protected void prepareEnd() {

    }

    @Override
    protected void computeUpdateValue(ValueAnimator animation, @FloatRange(from = 0.0, to = 1.0) float animatedValue) {
        switch (animatorState) {//以下分为三个阶段
            case 0://第一阶段，旋转、放大
                starOutMidR = getAllSize() * animatedValue;
                rotateAngle = (int) (360 * animatedValue);
                break;
            case 1://第二阶段，逆时针旋转
                rotateAngle = (int) (360 * (1 - animatedValue));
                break;
            case 2://第三阶段，缩小
                starOutMidR = getAllSize() * (1 - animatedValue);
                break;
        }
    }

    @Override
    public void onAnimationRepeat(Animator animation) {
        if (++animatorState > FINAL_STATE) {//还原到第一阶段
            animatorState = 0;
        }
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
