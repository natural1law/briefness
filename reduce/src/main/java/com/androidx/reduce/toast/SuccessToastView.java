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

public final class SuccessToastView extends View {

    protected RectF rectF = new RectF();
    protected ValueAnimator valueAnimator;
    private float mAnimatedValue = 0f;
    private Paint mPaint;
    private float mWidth = 0f;
    private float mEyeWidth = 0f;
    private float mPadding = 0f;
    private float endAngle = 0f;
    private boolean isSmileLeft = false;
    private boolean isSmileRight = false;

    public SuccessToastView(Context context) {
        super(context);
    }


    public SuccessToastView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SuccessToastView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mWidth = getMeasuredWidth();
        mPadding = dip2px(10);
        mEyeWidth = dip2px(3);
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.parseColor("#5cb85c"));
        mPaint.setStrokeWidth(dip2px(2));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initPaint();
        rectF.set(mPadding, mPadding, mWidth - mPadding, mWidth - mPadding);
        mPaint.setStyle(Paint.Style.STROKE);

        canvas.drawArc(rectF, 180, endAngle, false, mPaint);

        mPaint.setStyle(Paint.Style.FILL);
        if (isSmileLeft) {
            canvas.drawCircle(mPadding + mEyeWidth + mEyeWidth / 2, mWidth / 3, mEyeWidth, mPaint);

        }
        if (isSmileRight) {
            canvas.drawCircle(mWidth - mPadding - mEyeWidth - mEyeWidth / 2, mWidth / 3, mEyeWidth, mPaint);
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
            isSmileLeft = false;
            isSmileRight = false;
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
            if (mAnimatedValue < 0.5) {
                isSmileLeft = false;
                isSmileRight = false;
                endAngle = -360 * (mAnimatedValue);
            } else if (mAnimatedValue > 0.55 && mAnimatedValue < 0.7) {
                endAngle = -180;
                isSmileLeft = true;
                isSmileRight = false;
            } else {
                endAngle = -180;
                isSmileLeft = true;
                isSmileRight = true;
            }

            postInvalidate();
        });

        if (!valueAnimator.isRunning()) {
            valueAnimator.start();

        }
    }
}
