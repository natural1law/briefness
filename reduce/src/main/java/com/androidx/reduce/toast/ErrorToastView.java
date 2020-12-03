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

public final class ErrorToastView extends View {

    protected RectF rectF = new RectF();
    protected RectF leftEyeRectF = new RectF();
    protected RectF rightEyeRectF = new RectF();
    private ValueAnimator valueAnimator;
    private float mAnimatedValue = 0f;
    private Paint mPaint;
    private float mWidth = 0f;
    private float mEyeWidth = 0f;
    private float mPadding = 0f;
    private float endAngle = 0f;
    private boolean isJustVisible = false;
    private boolean isSad = false;


    public ErrorToastView(Context context) {
        super(context);
    }


    public ErrorToastView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ErrorToastView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        mPaint.setColor(Color.parseColor("#d9534f"));
        mPaint.setStrokeWidth(dip2px(2));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initPaint();
        rectF.set(mPadding / 2, ((mWidth) / 2), mWidth - (mPadding / 2), ((3 * mWidth / 2)));

        mPaint.setStyle(Paint.Style.STROKE);


        canvas.drawArc(rectF, 210, endAngle, false, mPaint);

        mPaint.setStyle(Paint.Style.FILL);
        if (isJustVisible) {
            canvas.drawCircle(mPadding + mEyeWidth + mEyeWidth / 2, mWidth / 3, mEyeWidth, mPaint);
            canvas.drawCircle(mWidth - mPadding - mEyeWidth - mEyeWidth / 2, mWidth / 3, mEyeWidth, mPaint);
        }
        if (isSad) {
            leftEyeRectF.set(mPadding + mEyeWidth - mEyeWidth, mWidth / 3 - mEyeWidth, mPadding + mEyeWidth + mEyeWidth, mWidth / 3 + mEyeWidth);
            canvas.drawArc(leftEyeRectF, 160, -220, false, mPaint);
            rightEyeRectF.set(mWidth - mPadding - 5 * mEyeWidth / 2, mWidth / 3 - mEyeWidth, mWidth - mPadding - mEyeWidth / 2, mWidth / 3 + mEyeWidth);
            canvas.drawArc(rightEyeRectF, 20, 220, false, mPaint);
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
            isSad = false;
            endAngle = 0f;
            isJustVisible = false;
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
                isSad = false;
                isJustVisible = false;
                endAngle = 240 * (mAnimatedValue);
                isJustVisible = true;
            } else if (mAnimatedValue > 0.55 && mAnimatedValue < 0.7) {
                endAngle = 120;
                isSad = false;
                isJustVisible = true;
            } else {
                endAngle = 120;
                isSad = true;
                isJustVisible = false;
            }

            postInvalidate();
        });

        if (!valueAnimator.isRunning()) {
            valueAnimator.start();

        }
    }

}
