package com.androidx.animation.figure;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.animation.AccelerateInterpolator;

import androidx.annotation.FloatRange;

import com.androidx.animation.base.BaseAnimator;

/**
 * @createDate 2021/09/13
 */
public class Text extends BaseAnimator {
    private static final int BASE_ALPHA = 100;
    private static final String DEFAULT_TEXT = "正在加载...";
    private Paint paint;
    private String content;
    private int drawTextCount = 0;

    @Override
    protected void initParams(Context context) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        paint.setDither(true);
        paint.setFilterBitmap(true);
        paint.setTextSize(getAllSize());
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.LEFT);

        //默认值
        content = DEFAULT_TEXT;//新增多种动画（根据第三方zyao89框架重构）
    }

    @Override
    @SuppressLint("DrawAllocation")
    protected void onDraw(Canvas canvas) {
        if (isNotEmpty()) {
            int length = content.toCharArray().length;
            float measureText = paint.measureText(content, 0, length);

            Paint p = new Paint(paint);
            p.setAlpha(BASE_ALPHA);
            canvas.drawText(content, 0, length, getViewCenterX() - measureText / 2, getViewCenterY(), p);
            canvas.drawText(content, 0, drawTextCount, getViewCenterX() - measureText / 2, getViewCenterY(), p);
        }
    }

    @Override
    protected void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    @Override
    protected void prepareStart(ValueAnimator floatValueAnimator) {
        floatValueAnimator.setDuration(ceil(getAnimationDuration() * 0.3f));
        floatValueAnimator.setInterpolator(new AccelerateInterpolator());
    }

    @Override
    protected void prepareEnd() {

    }

    @Override
    protected void computeUpdateValue(ValueAnimator animation, @FloatRange(from = 0.0, to = 1.0) float animatedValue) {
        paint.setAlpha((int) (animatedValue * 155) + BASE_ALPHA);
    }

    @Override
    public void onAnimationRepeat(Animator animation) {
        if (isNotEmpty()) {
            //还原到第一阶段
            if (++drawTextCount > content.toCharArray().length) drawTextCount = 0;
        }
    }

    @Override
    protected void setColorFilter(ColorFilter colorFilter) {
        paint.setColorFilter(colorFilter);
    }

    public void setText(String text) {
        if (TextUtils.isEmpty(text)) return;
        float measureText = paint.measureText(text);
        if (measureText >= getWidth()) {
            float v = measureText / getAllSize();
            paint.setTextSize(getWidth() / v);
        }
        content = text;
    }

    private boolean isNotEmpty() {
        return content != null && !content.isEmpty();
    }
}
