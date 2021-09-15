package com.androidx.animation.figure.rect;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.animation.DecelerateInterpolator;

import com.androidx.animation.base.BaseStateBuilder;

public class StairsRectBuilder extends BaseStateBuilder {
    /**
     * 台阶总数
     */
    private final int FLOOR_NUM = 5;
    /**
     * 动画间隔时长
     */
    private volatile long mAnimateDurationTime = 500;
    /**
     * 当前台阶数
     */
    private volatile int mCurrFloorNum = 0;
    private volatile float mCurrAnimatedValue = 0;
    private Paint mPaint;
    private float mR;
    private RectF mStairRectF;

    @Override
    protected int getStateCount() {
        return FLOOR_NUM;
    }

    @Override
    protected void initParams(Context context, Paint paint) {
        mPaint = paint;
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mR = getAllSize();
        mStairRectF = new RectF();
    }

    @Override
    protected void onComputeUpdateValue(ValueAnimator animation, float animatedValue, int state) {
        mCurrFloorNum = state;
        mCurrAnimatedValue = animatedValue;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 台阶高度
        float floorHeight = mR * 2 / FLOOR_NUM;
        float space = floorHeight * 0.5f;
        // 起点
        float startXP = getViewCenterX() - mR;
        float startYP = getViewCenterY() + mR;

        // 清
        mStairRectF.setEmpty();
        for (int i = 0; i < FLOOR_NUM; i++) {
            if (i > mCurrFloorNum) {// 限制层
                break;
            }
            float top = startYP - (i + 1) * floorHeight + space;
            if (i == mCurrFloorNum) {// 当前台阶
                mStairRectF.set(startXP, top, (startXP + (i + 1) * floorHeight) * mCurrAnimatedValue, startYP - i * floorHeight);
            } else {// 非当前台阶
                mStairRectF.set(startXP, top, startXP + (i + 1) * floorHeight, startYP - i * floorHeight);
            }
            canvas.drawRect(mStairRectF, mPaint);
        }
    }

    @Override
    protected void prepareStart(ValueAnimator animation) {
        mAnimateDurationTime = ceil(getAnimationDuration() * 0.5);
        // 动画间隔
        animation.setDuration(mAnimateDurationTime);
        animation.setInterpolator(new DecelerateInterpolator());
    }

    @Override
    protected void prepareEnd() {
        mCurrFloorNum = 0;
        mCurrAnimatedValue = 0;
    }
}
