package com.androidx.animation.base;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;

import java.util.LinkedList;
import java.util.List;

/**
 * @createDate 2021/09/13
 */
public abstract class BaseAnimatorBall extends BaseAnimator {

    //贝塞尔曲线常量
    private static final float PROP_VALUE = 0.551915024494f;
    //小球点集合
    protected final List<CirclePoint> ballPoints = new LinkedList<>();
    //画笔
    protected Paint paint;

    /**
     * 初始化画笔
     *
     * @param lineWidth 线宽
     */
    protected void initPaint(float lineWidth) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(lineWidth);
        paint.setColor(Color.BLACK);
        paint.setDither(true);
        paint.setFilterBitmap(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
    }

    /**
     * @param ballR 半径
     */
    protected final void initPoints(float ballR) {
        float centerX = getViewCenterX();
        float centerY = getViewCenterY();
        CirclePoint p_0 = new CirclePoint(centerX - ballR, centerY);
        ballPoints.add(p_0);
        CirclePoint p_1 = new CirclePoint(centerX - ballR, centerY + ballR * PROP_VALUE);
        ballPoints.add(p_1);
        CirclePoint p_2 = new CirclePoint(centerX - ballR * PROP_VALUE, centerY + ballR);
        ballPoints.add(p_2);
        CirclePoint p_3 = new CirclePoint(centerX, centerY + ballR);
        ballPoints.add(p_3);
        CirclePoint p_4 = new CirclePoint(centerX + ballR * PROP_VALUE, centerY + ballR);
        ballPoints.add(p_4);
        CirclePoint p_5 = new CirclePoint(centerX + ballR, centerY + ballR * PROP_VALUE);
        ballPoints.add(p_5);
        CirclePoint p_6 = new CirclePoint(centerX + ballR, centerY);
        ballPoints.add(p_6);
        CirclePoint p_7 = new CirclePoint(centerX + ballR, centerY - ballR * PROP_VALUE);
        ballPoints.add(p_7);
        CirclePoint p_8 = new CirclePoint(centerX + ballR * PROP_VALUE, centerY - ballR);
        ballPoints.add(p_8);
        CirclePoint p_9 = new CirclePoint(centerX, centerY - ballR);
        ballPoints.add(p_9);
        CirclePoint p_10 = new CirclePoint(centerX - ballR * PROP_VALUE, centerY - ballR);
        ballPoints.add(p_10);
        CirclePoint p_11 = new CirclePoint(centerX - ballR, centerY - ballR * PROP_VALUE);
        ballPoints.add(p_11);
    }

    protected final void drawBall(Canvas canvas, Path path, Paint paint) {
        path.reset();
        path.moveTo(ballPoints.get(0).getX(), ballPoints.get(0).getY());
        path.cubicTo(ballPoints.get(1).getX(), ballPoints.get(1).getY(), ballPoints.get(2).getX(), ballPoints.get(2).getY(), ballPoints.get(3).getX(), ballPoints.get(3).getY());
        path.cubicTo(ballPoints.get(4).getX(), ballPoints.get(4).getY(), ballPoints.get(5).getX(), ballPoints.get(5).getY(), ballPoints.get(6).getX(), ballPoints.get(6).getY());
        path.cubicTo(ballPoints.get(7).getX(), ballPoints.get(7).getY(), ballPoints.get(8).getX(), ballPoints.get(8).getY(), ballPoints.get(9).getX(), ballPoints.get(9).getY());
        path.cubicTo(ballPoints.get(10).getX(), ballPoints.get(10).getY(), ballPoints.get(11).getX(), ballPoints.get(11).getY(), ballPoints.get(0).getX(), ballPoints.get(0).getY());
        canvas.drawPath(path, paint);
    }

    @Override
    protected void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    @Override
    protected void setColorFilter(ColorFilter colorFilter) {
        paint.setColorFilter(colorFilter);
    }

    /**
     * 圆点内部类
     */
    public static final class CirclePoint {
        private final float x;
        private final float y;
        private float offsetX = 0;
        private float offsetY = 0;
        private boolean enabled = true;

        public CirclePoint(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public float getX() {
            return x + offsetX;
        }

        public float getY() {
            return y + offsetY;
        }

        public void setOffsetX(float offsetX) {
            this.offsetX = offsetX;
        }

        public void setOffsetY(float offsetY) {
            this.offsetY = offsetY;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public void draw(Canvas canvas, float r, Paint paint) {
            if (this.enabled) {
                canvas.drawCircle(this.getX(), this.getY(), r, paint);
            }
        }
    }
}
