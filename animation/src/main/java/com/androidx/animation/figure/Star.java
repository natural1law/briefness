package com.androidx.animation.figure;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.FloatRange;

import com.androidx.animation.base.BaseAnimator;

/**
 * @createDate 2021/09/13
 */
public class Star extends BaseAnimator {
    private Paint paint;
    private float outR;
    private float inR;
    private float outMidR;
    private float inMidR;
    //开始偏移角度
    private int angle;
    private Path path;
    private float y;
    private RectF rectF;
    private float width;
    private ValueAnimator valueAnimator;

    private final ValueAnimator.AnimatorUpdateListener listener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            float value = (float) animation.getAnimatedValue();
            y = getViewCenterY() * 0.4f * value;
            width = (y + 10) * 0.9f;
        }
    };

    @Override
    protected void initParams(Context context) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(2);
        paint.setColor(Color.BLACK);
        paint.setDither(true);
        paint.setFilterBitmap(true);

        initValue(context);
        initAnimator();
    }

    private void initAnimator() {
        valueAnimator = ValueAnimator.ofFloat(0.0f, 1.0f, 0.0f);
        valueAnimator.setRepeatCount(Animation.INFINITE);
        valueAnimator.setDuration(getAnimationDuration());
        valueAnimator.setStartDelay(getAnimationStartDelay());
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
    }

    private void initValue(Context context) {
        float allSize = getAllSize();
        outR = allSize - dip2px(context, 5);
        outMidR = outR * 0.9f;
        inR = outMidR * 0.6f;
        inMidR = inR * 0.9f;
        angle = 0;
        y = 0;

        //星路径
        path = createStarPath();

        //阴影宽度
        width = outR;
        rectF = new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(0, y);
        canvas.rotate(angle, getViewCenterX(), getViewCenterY());
        canvas.drawPath(path, paint);
        canvas.restore();

        rectF.set(getViewCenterX() - width,
                getHeight() - 20,
                getViewCenterX() + width,
                getHeight() - 10);
        canvas.drawOval(rectF, paint);
    }

    /**
     * 绘制五角星
     */
    private Path createStarPath() {
        final Path path = new Path();
        int angle = 360 / 5;
        int roundSize = 5;//圆角弧度
        int offsetAngle = angle / 2;
        path.moveTo(getViewCenterX() + outMidR * cos(-18 - roundSize),
                getViewCenterY() + outMidR * sin(-18 - roundSize));
        for (int i = 0; i < 5; i++) {
            int value = angle * i + -18;
            path.lineTo(getViewCenterX() + outMidR * cos(value - roundSize),
                    getViewCenterY() + outMidR * sin(value - roundSize));
            //圆角
            path.quadTo(getViewCenterX() + outR * cos(value),
                    getViewCenterY() + outR * sin(value),
                    getViewCenterX() + outMidR * cos(value + roundSize),
                    getViewCenterY() + outMidR * sin(value + roundSize));

            path.lineTo(getViewCenterX() + inR * cos(value + offsetAngle - roundSize),
                    getViewCenterY() + inR * sin(value + offsetAngle - roundSize));
            //内圆角
            path.quadTo(getViewCenterX() + inMidR * cos(value + offsetAngle),
                    getViewCenterY() + inMidR * sin(value + offsetAngle),
                    getViewCenterX() + inR * cos(value + offsetAngle + roundSize),
                    getViewCenterY() + inR * sin(value + offsetAngle + roundSize));
        }
        path.close();
        return path;
    }

    @Override
    protected void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    @Override
    protected void prepareStart(ValueAnimator floatValueAnimator) {
        floatValueAnimator.setInterpolator(new DecelerateInterpolator());

        valueAnimator.setRepeatCount(Animation.INFINITE);
        valueAnimator.setDuration(getAnimationDuration());
        valueAnimator.setStartDelay(getAnimationStartDelay());
        valueAnimator.addUpdateListener(listener);
        valueAnimator.start();
    }

    @Override
    protected void prepareEnd() {
        valueAnimator.removeAllUpdateListeners();
        valueAnimator.removeAllListeners();
        valueAnimator.setRepeatCount(0);
        valueAnimator.setDuration(0);
        valueAnimator.end();
    }

    @Override
    protected void computeUpdateValue(ValueAnimator animation, @FloatRange(from = 0.0, to = 1.0) float animatedValue) {
        angle = (int) (360 * animatedValue);
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
