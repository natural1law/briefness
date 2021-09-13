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
public class PacMan extends BaseAnimator {
    //最终阶段
    private static final int FINAL_STATE = 9;
    private static final int MAX_MOUTH_ANGLE = 45;
    private Paint paint;
    private RectF rectF;
    private int mouthAngle;
    private float moveDistance;
    //当前动画阶段
    private int animatorState = 0;
    private int horizontalAngle;
    private float maxMoveRange;
    private float lastMoveDistance;
    private float defaultStartMoveX;

    @Override
    protected void initParams(Context context) {
        float outR = getAllSize();
        float inR = outR * 0.7f;
        maxMoveRange = getWidth() + 2 * inR; //移动距离范围
        initPaint();//圆范围
        mouthAngle = MAX_MOUTH_ANGLE;//嘴度数
        horizontalAngle = 0;//水平翻转度数
        defaultStartMoveX = -maxMoveRange * 0.5f;//默认偏移量
        moveDistance = 0;//移动距离
        rectF = new RectF(
                getViewCenterX() - inR,
                getViewCenterY() - inR,
                getViewCenterX() + inR,
                getViewCenterY() + inR);
    }

    private void initPaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        paint.setDither(true);
        paint.setFilterBitmap(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(defaultStartMoveX + moveDistance, 0);
        canvas.rotate(horizontalAngle, getViewCenterX(), getViewCenterY());
        canvas.drawArc(rectF, mouthAngle, 360 - mouthAngle * 2, true, paint);
        canvas.restore();
    }

    @Override
    protected void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    @Override
    protected void prepareStart(ValueAnimator floatValueAnimator) {
        floatValueAnimator.setDuration(ceil(getAnimationDuration() * 0.3));
    }

    @Override
    protected void prepareEnd() {

    }

    @Override
    protected void computeUpdateValue(ValueAnimator animation, @FloatRange(from = 0.0, to = 1.0) float animatedValue) {
        int half = FINAL_STATE / 2 + 1;
        float step = maxMoveRange / half;
        if (animatorState < half)//以下分为两个阶段
        {//向右
            horizontalAngle = 0;
            moveDistance = lastMoveDistance + step * animatedValue;
        } else {//向左
            horizontalAngle = 180;
            moveDistance = lastMoveDistance - step * animatedValue;
        }
        //嘴张开度数
        if (animatorState % 2 == 0) mouthAngle = (int) (MAX_MOUTH_ANGLE * (animatedValue)) + 5;
        else mouthAngle = (int) (MAX_MOUTH_ANGLE * (1 - animatedValue)) + 5;
    }

    @Override
    public void onAnimationRepeat(Animator animation) {
        if (++animatorState > FINAL_STATE) {//还原到第一阶段
            animatorState = 0;
        }
        //矫正
        int half = FINAL_STATE / 2 + 1;
        float stepRange = maxMoveRange / half;
        if (animatorState < half) lastMoveDistance = stepRange * animatorState;
        else lastMoveDistance = stepRange * (half - animatorState % half);
    }

    @Override
    protected void setColorFilter(ColorFilter colorFilter) {
        paint.setColorFilter(colorFilter);
    }
}
