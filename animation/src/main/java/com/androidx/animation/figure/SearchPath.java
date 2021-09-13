package com.androidx.animation.figure;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.FloatRange;

import com.androidx.animation.base.BaseAnimator;

/**
 * @createDate 2021/09/13
 */
public class SearchPath extends BaseAnimator {
    //最终阶段
    private static final int FINAL_STATE = 3;
    //当前动画阶段
    private int animatorState = 0;
    private float r;
    private Paint paint;
    private Path path;
    private Path pathZoom;
    private PathMeasure pathMeasure;
    private Path drawPath;

    @Override
    protected void initParams(Context context) {
        r = getAllSize();
        initPaint();
        initPathMeasure();
        initPaths();
    }

    private void initPaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(15);
        paint.setColor(Color.BLACK);
        paint.setDither(true);
        paint.setFilterBitmap(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
    }

    private void initPaths() {
        float r1 = r * 0.4f;

        path = new Path();
        path.addArc(new RectF(
                getViewCenterX() - r1,
                getViewCenterY() - r1,
                getViewCenterX() + r1,
                getViewCenterY() + r1), 45, 359.9f);
        pathMeasure.setPath(path, false);
        float[] pos = new float[2];
        pathMeasure.getPosTan(0, pos, null);

        pathZoom = new Path();
        pathZoom.addArc(new RectF(
                getViewCenterX() - r1
                , getViewCenterY() - r1,
                getViewCenterX() + r1,
                getViewCenterY() + r1), 45, 359.9f);
        pathZoom.lineTo(pos[0], pos[1]);
    }

    private void initPathMeasure() {
        drawPath = new Path();
        pathMeasure = new PathMeasure();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(drawPath, paint);
    }

    @Override
    protected void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    @Override
    protected void prepareStart(ValueAnimator floatValueAnimator) {
        floatValueAnimator.setInterpolator(new DecelerateInterpolator());
    }

    @Override
    protected void prepareEnd() {

    }

    @Override
    protected void computeUpdateValue(ValueAnimator animation, @FloatRange(from = 0.0, to = 1.0) float animatedValue) {
        switch (animatorState) {
            case 0:
            case 1:
                resetDrawPath();
                pathMeasure.setPath(path, false);
                float stop = pathMeasure.getLength() * animatedValue;
                float start = (float) (stop - ((0.5 - Math.abs(animatedValue - 0.5)) * 200f));
                pathMeasure.getSegment(start, stop, drawPath, true);
                break;
            case 2:
                resetDrawPath();
                pathMeasure.setPath(path, false);
                stop = pathMeasure.getLength() * animatedValue;
                start = 0;
                pathMeasure.getSegment(start, stop, drawPath, true);
                break;
            case 3:
                pathMeasure.setPath(pathZoom, false);
                stop = pathMeasure.getLength();
                start = stop * (1 - animatedValue);
                pathMeasure.getSegment(start, stop, drawPath, true);
                break;
            default:
                break;
        }
    }

    @Override
    public void onAnimationRepeat(Animator animation) {
        //还原到第一阶段
        if (++animatorState > FINAL_STATE) animatorState = 0;
    }

    @Override
    protected void setColorFilter(ColorFilter colorFilter) {
        paint.setColorFilter(colorFilter);
    }

    private void resetDrawPath() {
        drawPath.reset();
        drawPath.lineTo(0, 0);//不知道什么坑
    }
}
