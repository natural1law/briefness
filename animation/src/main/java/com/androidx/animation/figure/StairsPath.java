package com.androidx.animation.figure;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.view.animation.DecelerateInterpolator;

import com.androidx.animation.base.BaseAnimatorState;

/**
 * @createDate 2021/09/13
 */
public class StairsPath extends BaseAnimatorState {
    private static final int FLOOR_NUM = 6;
    private Paint paint;
    private float r;
    private Path path;
    private Path pathFinal;
    private PathMeasure pathMeasure;
    private Path drawPath;

    @Override
    protected int getStateCount() {
        return 3;
    }

    @Override
    protected void initParams(Context context, Paint paint) {
        this.paint = paint;
        r = getAllSize();
        initPathMeasure();
        initPaths();
    }

    private void initPaths() {
        path = new Path();
        float space = r * 2 / FLOOR_NUM;
        float startXP = getViewCenterX() - r;
        float startYP = getViewCenterY() + r;

        path.moveTo(startXP, startYP);
        for (int i = 0; i < FLOOR_NUM; i++) {
            path.lineTo((i) * space + startXP, startYP - space * (i + 1));
            path.lineTo((i + 1) * space + startXP, startYP - space * (i + 1));
        }

        pathFinal = new Path(path);
        pathFinal.lineTo((FLOOR_NUM) * space + startXP, startYP);
        pathFinal.lineTo(startXP, startYP);
    }

    private void initPathMeasure() {
        drawPath = new Path();
        pathMeasure = new PathMeasure();
    }

    @Override
    protected void onComputeUpdateValue(ValueAnimator animation, float animatedValue, int state) {
        switch (state) {
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
                pathMeasure.setPath(pathFinal, false);
                stop = pathMeasure.getLength() * animatedValue;
                start = 0;
                pathMeasure.getSegment(start, stop, drawPath, true);
                break;
            case 3:
                resetDrawPath();
                pathMeasure.setPath(pathFinal, false);
                stop = pathMeasure.getLength() * (1 - animatedValue);
                start = 0;
                pathMeasure.getSegment(start, stop, drawPath, true);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(drawPath, paint);
    }

    @Override
    protected void prepareStart(ValueAnimator valueAnimator) {
        valueAnimator.setInterpolator(new DecelerateInterpolator());
    }

    @Override
    protected void prepareEnd() {

    }

    private void resetDrawPath() {
        drawPath.reset();
        drawPath.lineTo(0, 0);
    }
}
