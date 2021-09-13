package com.androidx.animation.figure;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * @createDate 2021/09/13
 */
public class Clock extends Circle {
    private float outerRadius;
    private float circleSpace;
    private Paint paint;
    private Paint fillPaint;
    private RectF btnRectF;
    private RectF bottorectF;

    @Override
    protected void initParams(Context context) {
        super.initParams(context);
        initOptions(context);
        createStrokePaint();
        createFillPaint();
    }

    private void initOptions(Context context) {
        float allSize = getAllSize();
        circleSpace = 4;
        outerRadius = allSize - circleSpace;
        float btnWidth = dip2px(context, 8);
        float btnHeight = dip2px(context, 3);
        float bottomBtnWidth = dip2px(context, 3);
        float bottomBtnHeight = dip2px(context, 2);
        btnRectF = new RectF(getViewCenterX() - btnWidth / 2, getViewCenterY() - allSize - bottomBtnHeight - btnHeight, getViewCenterX() + btnWidth / 2, getViewCenterY() - allSize - bottomBtnHeight);
        bottorectF = new RectF(getViewCenterX() - bottomBtnWidth / 2, getViewCenterY() - allSize - bottomBtnHeight, getViewCenterX() + bottomBtnWidth / 2, getViewCenterY() - allSize);
    }

    private void createStrokePaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(circleSpace);
        paint.setColor(Color.BLACK);
    }

    private void createFillPaint() {
        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        fillPaint.setColor(Color.BLACK);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //外圆
        canvas.drawCircle(getViewCenterX(), getViewCenterY(), outerRadius, paint);

        //上方按钮
        canvas.drawRect(btnRectF, fillPaint);
        canvas.drawRect(bottorectF, fillPaint);

        //右侧按钮
        canvas.save();
        canvas.rotate(45, getViewCenterX(), getViewCenterY());
        canvas.drawRect(bottorectF, fillPaint);
        canvas.restore();

        canvas.translate(0, 20);
    }

    @Override
    protected void setAlpha(int alpha) {
        super.setAlpha(alpha);
        paint.setAlpha(alpha);
        fillPaint.setAlpha(alpha);
    }

    @Override
    protected void setColorFilter(ColorFilter colorFilter) {
        super.setColorFilter(colorFilter);
        paint.setColorFilter(colorFilter);
        fillPaint.setColorFilter(colorFilter);
    }
}
