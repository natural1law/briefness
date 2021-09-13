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

import androidx.annotation.FloatRange;

import com.androidx.animation.base.BaseAnimator;

/**
 * @createDate 2021/09/13
 */
public class SnakeCircle extends BaseAnimator {
    //最终阶段
    private static final int FINAL_STATE = 1;
    //当前动画阶段
    private int animatorState = 0;
    private Paint paint;
    private float outerRF;
    private float interRF;
    private RectF outerRectF;
    private RectF interRectF;
    private int alpha = 255;
    //旋转角度
    private float rotateAngle;
    private float antiRotateAngle;
    //路径
    private Path path;
    private PathMeasure pathMeasure;
    private Path drawPath;

    @Override
    protected void initParams(Context context) {
        //最大尺寸
        outerRF = getAllSize();
        //小圆尺寸
        interRF = outerRF * 0.7f;
        //初始化画笔
        initPaint(interRF * 0.4f);
        //旋转角度
        rotateAngle = 0;
        //圆范围
        outerRectF = new RectF();
        outerRectF.set(
                getViewCenterX() - outerRF,
                getViewCenterY() - outerRF,
                getViewCenterX() + outerRF,
                getViewCenterY() + outerRF);
        interRectF = new RectF();
        interRectF.set(getViewCenterX() - interRF,
                getViewCenterY() - interRF,
                getViewCenterX() + interRF,
                getViewCenterY() + interRF);
        initPathMeasure();
        initPaths();
    }

    /**
     * 初始化画笔
     */
    private void initPaint(float lineWidth) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(lineWidth);
        paint.setColor(Color.WHITE);
        paint.setDither(true);
        paint.setFilterBitmap(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
    }

    private void initPathMeasure() {
        drawPath = new Path();
        pathMeasure = new PathMeasure();
    }

    private void initPaths() {
        //中心折线
        path = new Path();
        float pointOffset = outerRF * 0.3f;
        float pointOffsetHalf = outerRF * 0.3f * 0.5f;
        path.moveTo(getViewCenterX() - outerRF * 0.8f, getViewCenterY());
        path.lineTo(getViewCenterX() - pointOffset, getViewCenterY());
        path.lineTo(getViewCenterX() - pointOffsetHalf, getViewCenterY() + pointOffsetHalf);
        path.lineTo(getViewCenterX() + pointOffsetHalf, getViewCenterY() - pointOffsetHalf);
        path.lineTo(getViewCenterX() + pointOffset, getViewCenterY());
        path.lineTo(getViewCenterX() + outerRF * 0.8f, getViewCenterY());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        //外圆
        paint.setStrokeWidth(outerRF * 0.05f);
        paint.setAlpha((int) (alpha * 0.6f));
        canvas.drawCircle(getViewCenterX(), getViewCenterY(), outerRF, paint);
        canvas.drawCircle(getViewCenterX(), getViewCenterY(), interRF, paint);
        canvas.restore();

        canvas.save();
        paint.setStrokeWidth(outerRF * 0.1f);
        paint.setAlpha(alpha);
        canvas.rotate(rotateAngle, getViewCenterX(), getViewCenterY());
        canvas.drawArc(outerRectF, 0, 120, false, paint);
        canvas.drawArc(outerRectF, 180, 120, false, paint);
        canvas.restore();

        // 蛇
        canvas.save();
        paint.setAlpha((int) (alpha * 0.6f));
        canvas.drawPath(drawPath, paint);
        canvas.restore();

        canvas.save();
        paint.setStrokeWidth(outerRF * 0.1f);
        paint.setAlpha(alpha);
        canvas.rotate(antiRotateAngle, getViewCenterX(), getViewCenterY());
        canvas.drawArc(interRectF, 60, 60, false, paint);
        canvas.drawArc(interRectF, 180, 180, false, paint);
        canvas.restore();
    }

    @Override
    protected void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    @Override
    protected void prepareStart(ValueAnimator floatValueAnimator) {

    }

    @Override
    protected void prepareEnd() {

    }

    @Override
    protected void computeUpdateValue(ValueAnimator animation, @FloatRange(from = 0.0, to = 1.0) float animatedValue) {
        rotateAngle = 360 * animatedValue;
        antiRotateAngle = 360 * (1 - animatedValue);

        switch (animatorState) {
            case 0:
                resetDrawPath();
                pathMeasure.setPath(path, false);
                float stop = pathMeasure.getLength() * animatedValue;
                float start = 0;
                pathMeasure.getSegment(start, stop, drawPath, true);
                break;
            case 1:
                resetDrawPath();
                pathMeasure.setPath(path, false);
                stop = pathMeasure.getLength();
                start = pathMeasure.getLength() * animatedValue;
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
        drawPath.lineTo(0, 0);
    }
}
