package com.androidx.reduce.toast;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

public final class DefaultToastView extends View {

    private ValueAnimator valueAnimator;
    private float mAnimatedValue = 0f;
    private Paint mPaint, mSpikePaint;
    private float mWidth = 0f;
    private float mSpikeLength;


    public DefaultToastView(Context context) {
        super(context);
    }

    public DefaultToastView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DefaultToastView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.parseColor("#222222"));
        mPaint.setStrokeWidth(dip2px(2));

        mSpikePaint = new Paint();
        mSpikePaint.setAntiAlias(true);
        mSpikePaint.setStyle(Paint.Style.STROKE);
        mSpikePaint.setColor(Color.parseColor("#222222"));
        mSpikePaint.setStrokeWidth(dip2px(4));

        mSpikeLength = dip2px(4);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initPaint();
        canvas.save();
        canvas.drawCircle(mWidth / 2, mWidth / 2, mWidth / 4, mPaint);

        for (int i = 0; i < 360; i += 40) {
            int angle = (int) (mAnimatedValue * 40 + i);
            float initialX = (float) ((mWidth / 4) * Math.cos(angle * Math.PI / 180));
            float initialY = (float) ((mWidth / 4) * Math.sin(angle * Math.PI / 180));
            float finalX = (float) ((mWidth / 4 + mSpikeLength) * Math.cos(angle * Math.PI / 180));
            float finalY = (float) ((mWidth / 4 + mSpikeLength) * Math.sin(angle * Math.PI / 180));
            canvas.drawLine(mWidth / 2 - initialX, mWidth / 2 - initialY, mWidth / 2 - finalX, mWidth / 2 - finalY, mSpikePaint);
        }

        canvas.restore();
    }


    public void startAnim(long time) {
        stopAnim();
        startViewAnim(time);
    }

    public void stopAnim() {
        if (valueAnimator != null) {
            clearAnimation();

            valueAnimator.end();
            postInvalidate();
        }
    }

    private void startViewAnim(long time) {
        valueAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimator.setDuration(time);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.addUpdateListener(valueAnimator -> {
            mAnimatedValue = (float) valueAnimator.getAnimatedValue();
            postInvalidate();
        });

        if (!valueAnimator.isRunning()) {
            valueAnimator.start();

        }
    }

    public int dip2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale);
    }

}
