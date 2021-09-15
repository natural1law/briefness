package com.androidx.animation.figure.ball;

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

import java.util.LinkedList;

public class ElasticBallBuilder extends BaseBallBuilder {
    //最终阶段
    private static final int FINAL_STATE = 2;
    //小球共5个位置
    private static final int SUM_POINT_POS = 5;
    //背景圆集合
    private final LinkedList<CirclePoint> mBGCircles = new LinkedList<>();
    //动画间隔时间
    private long mDurationTime = 333;
    private float mBallR;
    private Path mPath;
    //当前动画阶段
    private int mCurrAnimatorState = 0;
    //每个小球的偏移量
    private float mCanvasTranslateOffset;
    //当前状态是否翻转
    private boolean mIsReverse = false;
    //当前小球的位置
    private int mCurrPointPos = 0;

    @Override
    public void initParams(Context context) {
        mBallR = getAllSize() / SUM_POINT_POS;
        mCanvasTranslateOffset = getIntrinsicWidth() / SUM_POINT_POS;
        mPath = new Path();

        initPaint(5);
        initPoints(mBallR);
        initBGPoints();
    }

    /**
     * 背景圆点初始化
     */
    private void initBGPoints() {
        float centerX = getViewCenterX();
        float centerY = getViewCenterY();
        CirclePoint p_0 = new CirclePoint(centerX - mCanvasTranslateOffset * 2, centerY);
        CirclePoint p_1 = new CirclePoint(centerX - mCanvasTranslateOffset, centerY);
        CirclePoint p_2 = new CirclePoint(centerX, centerY);
        CirclePoint p_3 = new CirclePoint(centerX + mCanvasTranslateOffset, centerY);
        CirclePoint p_4 = new CirclePoint(centerX + mCanvasTranslateOffset * 2, centerY);

        p_0.setEnabled(false);//默认第一个圆不显示
        mBGCircles.add(p_0);
        mBGCircles.add(p_1);
        mBGCircles.add(p_2);
        mBGCircles.add(p_3);
        mBGCircles.add(p_4);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawBG(canvas);
        drawBall(canvas);
    }

    /**
     * 绘制小球
     */
    private void drawBall(Canvas canvas) {
        canvas.save();
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        float offsetX = mBGCircles.size() * 0.5f * mCanvasTranslateOffset;
        canvas.translate(-offsetX + mCanvasTranslateOffset * mCurrPointPos, 0);
        super.drawBall(canvas, mPath, mPaint);
        canvas.restore();
    }

    /**
     * 绘制背景圆
     */
    private void drawBG(Canvas canvas) {
        canvas.save();
        mPaint.setStyle(Paint.Style.STROKE);
        mBGCircles.forEach(point -> point.draw(canvas, mBallR, mPaint));
        canvas.restore();
    }

    @Override
    protected void prepareStart(ValueAnimator floatValueAnimator) {
        mDurationTime = ceil(getAnimationDuration() * 0.3f);
        floatValueAnimator.setDuration(mDurationTime);
    }

    @Override
    protected void prepareEnd() {

    }

    @Override
    protected void computeUpdateValue(ValueAnimator animation, @FloatRange(from = 0.0, to = 1.0) float animatedValue) {
        float offset = mCanvasTranslateOffset;
        int currState = mIsReverse ? mCurrAnimatorState + 3 : mCurrAnimatorState;
        switch (currState) {
            case 0:
                animation.setDuration(mDurationTime);
                animation.setInterpolator(new AccelerateInterpolator());
                mBallPoints.get(5).setOffsetX(animatedValue * offset);
                mBallPoints.get(6).setOffsetX(animatedValue * offset);
                mBallPoints.get(7).setOffsetX(animatedValue * offset);
                break;
            case 1:
                animation.setDuration(mDurationTime + 111);
                animation.setInterpolator(new DecelerateInterpolator());
                mBallPoints.get(2).setOffsetX(animatedValue * offset);
                mBallPoints.get(3).setOffsetX(animatedValue * offset);
                mBallPoints.get(4).setOffsetX(animatedValue * offset);
                mBallPoints.get(8).setOffsetX(animatedValue * offset);
                mBallPoints.get(9).setOffsetX(animatedValue * offset);
                mBallPoints.get(10).setOffsetX(animatedValue * offset);
                break;
            case 2:
                animation.setDuration(mDurationTime + 333);
                animation.setInterpolator(new BounceInterpolator());
                mBallPoints.get(0).setOffsetX(animatedValue * offset);
                mBallPoints.get(1).setOffsetX(animatedValue * offset);
                mBallPoints.get(11).setOffsetX(animatedValue * offset);
                break;
            case 3:
                animation.setDuration(mDurationTime);
                animation.setInterpolator(new AccelerateInterpolator());
                mBallPoints.get(0).setOffsetX((1 - animatedValue) * offset);
                mBallPoints.get(1).setOffsetX((1 - animatedValue) * offset);
                mBallPoints.get(11).setOffsetX((1 - animatedValue) * offset);
                break;
            case 4:
                animation.setDuration(mDurationTime + 111);
                animation.setInterpolator(new DecelerateInterpolator());
                mBallPoints.get(2).setOffsetX((1 - animatedValue) * offset);
                mBallPoints.get(3).setOffsetX((1 - animatedValue) * offset);
                mBallPoints.get(4).setOffsetX((1 - animatedValue) * offset);
                mBallPoints.get(8).setOffsetX((1 - animatedValue) * offset);
                mBallPoints.get(9).setOffsetX((1 - animatedValue) * offset);
                mBallPoints.get(10).setOffsetX((1 - animatedValue) * offset);
                break;
            case 5:
                animation.setDuration(mDurationTime + 333);
                animation.setInterpolator(new BounceInterpolator());
                mBallPoints.get(5).setOffsetX((1 - animatedValue) * offset);
                mBallPoints.get(6).setOffsetX((1 - animatedValue) * offset);
                mBallPoints.get(7).setOffsetX((1 - animatedValue) * offset);
                break;
            default:
                break;
        }
    }

    @Override
    public void onAnimationRepeat(Animator animation) {
        if (++mCurrAnimatorState > FINAL_STATE) {//还原到第一阶段
            mCurrAnimatorState = 0;

            /* 小球位置改变 */
            if (mIsReverse) {//倒序
                mCurrPointPos--;
            } else {//顺序
                mCurrPointPos++;
            }

            /* 重置并翻转动画过程 */
            if (mCurrPointPos >= SUM_POINT_POS - 1) {//倒序
                mIsReverse = true;
                mCurrPointPos = SUM_POINT_POS - 2;//I Don't Know
                for (int i = 0; i < mBGCircles.size(); i++) {
                    CirclePoint point = mBGCircles.get(i);
                    point.setEnabled(i == mBGCircles.size() - 1);
                }
            } else if (mCurrPointPos < 0) {//顺序
                mIsReverse = false;
                mCurrPointPos = 0;
                for (int i = 0; i < mBGCircles.size(); i++) {
                    CirclePoint point = mBGCircles.get(i);
                    point.setEnabled(i != 0);
                }
            }

            //每个阶段恢复状态，以及对背景圆的控制
            if (mIsReverse) {//倒序
                //恢复状态
                mBallPoints.forEach(point -> point.setOffsetX(mCanvasTranslateOffset));
                mBGCircles.get(mCurrPointPos + 1).setEnabled(true);
            } else {//顺序
                //恢复状态
                mBallPoints.forEach(point -> point.setOffsetX(0));
                mBGCircles.get(mCurrPointPos).setEnabled(false);
            }
        }
    }

}
