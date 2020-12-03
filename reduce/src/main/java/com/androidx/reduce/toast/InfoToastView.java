package com.androidx.reduce.toast;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

public final class InfoToastView extends View {

    protected RectF rectF = new RectF();
    protected ValueAnimator valueAnimator;
    private float mAnimatedValue = 0f;
    private Paint mPaint;
    private float mWidth = 0f;
    private float mEyeWidth = 0f;
    private float mPadding = 0f;
    private float endPoint = 0f;
    private boolean isEyeLeft = false;
    private boolean isEyeRight = false;
    private boolean isEyeMiddle = false;

    public InfoToastView(Context context) {
        super(context);
    }


    public InfoToastView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InfoToastView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mWidth = getMeasuredWidth();
        mPadding = dip2px(10);
        mEyeWidth = dip2px(3);
        endPoint = mPadding;
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.parseColor("#337ab7"));
        mPaint.setStrokeWidth(dip2px(2));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initPaint();
        rectF.set(mPadding, mPadding, mWidth - mPadding, mWidth - mPadding);
        mPaint.setStyle(Paint.Style.STROKE);

        canvas.drawLine(mPadding, mWidth - 3 * mPadding / 2, endPoint, mWidth - 3 * mPadding / 2, mPaint);

        mPaint.setStyle(Paint.Style.FILL);

        if (isEyeLeft) {
            canvas.drawCircle(mPadding + mEyeWidth, mWidth / 3, mEyeWidth, mPaint);
            canvas.drawCircle(mWidth - mPadding - 2 * mEyeWidth, mWidth / 3, mEyeWidth, mPaint);
        }

        if (isEyeMiddle) {
            canvas.drawCircle(mPadding + (3 * mEyeWidth / 2), mWidth / 3, mEyeWidth, mPaint);
            canvas.drawCircle(mWidth - mPadding - (5 * mEyeWidth / 2), mWidth / 3, mEyeWidth, mPaint);
        }

        if (isEyeRight) {
            canvas.drawCircle(mPadding + 2 * mEyeWidth, mWidth / 3, mEyeWidth, mPaint);
            canvas.drawCircle(mWidth - mPadding - mEyeWidth, mWidth / 3, mEyeWidth, mPaint);
        }

    }

    public int dip2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public void startAnim(long time) {
        stopAnim();
        startViewAnim(time);
    }

    public void stopAnim() {
        if (valueAnimator != null) {
            clearAnimation();
            isEyeLeft = false;
            isEyeMiddle = false;
            isEyeRight = false;
            endPoint = mPadding;
            mAnimatedValue = 0f;
            valueAnimator.end();
        }
    }

    private void startViewAnim(long time) {
        valueAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimator.setDuration(time);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(valueAnimator -> {

            mAnimatedValue = (float) valueAnimator.getAnimatedValue();

            if (mAnimatedValue < 0.90) {
                endPoint = ((2 * (mWidth) - (4 * mPadding)) * (mAnimatedValue / 2)) + mPadding;
            } else {
                endPoint = mWidth - 5 * mPadding / 4;
            }

            if (mAnimatedValue < 0.16) {
                isEyeRight = true;
                isEyeLeft = false;
            } else if (mAnimatedValue < 0.32) {
                isEyeRight = false;
                isEyeLeft = true;
            } else if (mAnimatedValue < 0.48) {
                isEyeRight = true;
                isEyeLeft = false;
            } else if (mAnimatedValue < 0.64) {
                isEyeRight = false;
                isEyeLeft = true;
            } else if (mAnimatedValue < 0.80) {
                isEyeRight = true;
                isEyeLeft = false;
            } else if (mAnimatedValue < 0.96) {
                isEyeRight = false;
                isEyeLeft = true;
            } else {
                isEyeLeft = false;
                isEyeMiddle = true;
                isEyeRight = false;
            }

            postInvalidate();
        });

        if (!valueAnimator.isRunning()) {
            valueAnimator.start();

        }
    }

}
