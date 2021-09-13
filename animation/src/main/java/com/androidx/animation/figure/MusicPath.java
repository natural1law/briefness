package com.androidx.animation.figure;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.graphics.RectF;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.androidx.animation.base.BaseAnimatorState;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @createDate 2021/09/13
 */
public class MusicPath extends BaseAnimatorState {
    private final int MUSIC_LINE_COUNT = 5;
    private Paint paint;
    private float r;

    private LinkedList<Path> musicPaths;
    private LinkedList<Path> musicDrawPaths;
    private PathMeasure pathMeasure;
    private LinkedList<MusicParam> musicParams;
    private boolean openJump = false;

    private DecelerateInterpolator decelerateInterpolator;
    private BounceInterpolator bounceInterpolator;

    @Override
    protected int getStateCount() {
        return 3;
    }

    @Override
    protected void initParams(Context context, Paint paint) {
        this.paint = paint;
        paint.setStrokeWidth(2);
        r = getAllSize();
        initPaths();
        initPathMeasure();
        initMusicParams();
        initInterpolator();
    }

    private void initPaths() {
        musicPaths = new LinkedList<>();
        // 线长
        float lineW = r * 2;
        // 线间距
        float space = r * 2 / MUSIC_LINE_COUNT;
        // 起点
        float startXP = getViewCenterX() - r;
        float startYP = getViewCenterY() + r;

        // 五线谱
        for (int i = 0; i < MUSIC_LINE_COUNT; i++) {
            Path path = new Path();
            path.moveTo(startXP, startYP - (i * space));
            path.lineTo(startXP + lineW, startYP - (i * space));
            musicPaths.add(path);
        }
    }

    private void initPathMeasure() {
        musicDrawPaths = new LinkedList<>();
        // 五线谱
        for (int i = 0; i < MUSIC_LINE_COUNT; i++) musicDrawPaths.add(new Path());
        pathMeasure = new PathMeasure();
    }

    private void initMusicParams() {
        float musicWidth = r * 0.2f;
        float musicHeight = r;
        musicParams = new LinkedList<>();

        float musicPointHeight = r * 2 / MUSIC_LINE_COUNT;
        float left = getViewCenterX() - musicWidth / 2;
        float right = getViewCenterX() + musicWidth / 2;
        float top = getViewCenterY() + musicHeight - musicPointHeight * 1.5f;
        float bottom = getViewCenterY() + musicHeight - musicPointHeight * 0.5f;

        RectF rectF = new RectF(left - r * 0.5f, top, right - r * 0.5f, bottom);
        float offsetX = (float) (musicWidth * 0.5 * Math.cos(75));
        PointF sPointF = new PointF(rectF.right + offsetX, rectF.centerY());
        PointF ePointF = new PointF(rectF.right + offsetX, rectF.centerY() - musicHeight);
        MusicParam musicParam = new MusicParam(rectF, sPointF, ePointF);
        musicParams.add(musicParam);

        rectF = new RectF(left + r * 0.5f, top - musicPointHeight, right + r * 0.5f, bottom - musicPointHeight);
        offsetX = (float) (musicWidth * 0.5 * Math.cos(75));
        sPointF = new PointF(rectF.right + offsetX, rectF.centerY());
        ePointF = new PointF(rectF.right + offsetX, rectF.centerY() - musicHeight);
        musicParam = new MusicParam(rectF, sPointF, ePointF);
        musicParams.add(musicParam);
    }

    private void initInterpolator() {
        decelerateInterpolator = new DecelerateInterpolator();
        bounceInterpolator = new BounceInterpolator();
    }

    @Override
    protected void onComputeUpdateValue(ValueAnimator animation, float animatedValue, int state) {
        switch (state) {
            case 0:
                animation.setInterpolator(decelerateInterpolator);
                resetDrawPath();
                for (int i = 0; i < MUSIC_LINE_COUNT; i++) {
                    pathMeasure.setPath(musicPaths.get(i), false);
                    if (i % 2 == 0) {
                        float stop = pathMeasure.getLength() * animatedValue;
                        float start = (float) (stop - ((0.5 - Math.abs(animatedValue - 0.5)) * 200f));
                        pathMeasure.getSegment(start, stop, musicDrawPaths.get(i), true);
                    } else {
                        float stop = pathMeasure.getLength() * (1 - animatedValue);
                        float start = (float) (stop - ((0.5 - Math.abs((1 - animatedValue) - 0.5)) * 200f));
                        pathMeasure.getSegment(start, stop, musicDrawPaths.get(i), true);
                    }
                }
                break;
            case 1:
                resetDrawPath();
                for (int i = 0; i < MUSIC_LINE_COUNT; i++) {
                    pathMeasure.setPath(musicPaths.get(i), false);
                    float stop;
                    float start;
                    if (i % 2 == 0) {
                        stop = pathMeasure.getLength() * animatedValue;
                        start = 0;
                    } else {
                        stop = pathMeasure.getLength();
                        start = pathMeasure.getLength() * (1 - animatedValue);
                    }
                    pathMeasure.getSegment(start, stop, musicDrawPaths.get(i), true);
                }
                break;
            case 2:
                animation.setInterpolator(bounceInterpolator);
                openJump = true;// jump
                float space = r * 2 / MUSIC_LINE_COUNT;// 线间距
                AtomicInteger i1 = new AtomicInteger();
                musicParams.forEach(musicParam -> {
                    if (i1.get() % 2 == 0) musicParam.setOffsetY(animatedValue * space);
                    else musicParam.setOffsetY((1 - animatedValue) * space);
                    i1.getAndIncrement();
                });
                break;
            case 3:
                openJump = true;// jump
                space = r * 2 / MUSIC_LINE_COUNT;// 线间距
                AtomicInteger i2 = new AtomicInteger();
                musicParams.forEach(musicParam -> {
                    if (i2.get() % 2 == 0) musicParam.setOffsetY((1 - animatedValue) * space);
                    else musicParam.setOffsetY(animatedValue * space);
                    i2.getAndIncrement();
                });
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        musicDrawPaths.forEach(path -> canvas.drawPath(path, paint));
        if (openJump) drawMusic(canvas);
    }

    private void drawMusic(Canvas canvas) {
        for (MusicParam musicParam : musicParams) {
            paint.setStrokeWidth(4);
            canvas.save();
            RectF oldCircleRectF = musicParam.getCircleRectF();
            RectF circleRectF = new RectF(oldCircleRectF);
            float offsetY = musicParam.getOffsetY();
            circleRectF.set(
                    oldCircleRectF.left,
                    oldCircleRectF.top - offsetY,
                    oldCircleRectF.right,
                    oldCircleRectF.bottom - offsetY);
            canvas.rotate(75, circleRectF.centerX(), circleRectF.centerY());
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            canvas.drawOval(circleRectF, paint);
            paint.setStyle(Paint.Style.STROKE);
            canvas.restore();

            PointF startPointF = musicParam.getLineStartPointF();
            PointF endPointF = musicParam.getLineEndPointF();
            canvas.drawLine(startPointF.x, startPointF.y - offsetY, endPointF.x, endPointF.y - offsetY, paint);
            paint.setStrokeWidth(2);
        }
    }

    @Override
    protected void prepareStart(ValueAnimator animation) {
        animation.setInterpolator(decelerateInterpolator);
    }

    @Override
    protected void prepareEnd() {

    }

    private void resetDrawPath() {
        openJump = false;
        musicDrawPaths.forEach(path -> {
            path.reset();
            path.lineTo(0, 0);
        });
        musicParams.forEach(MusicParam::clear);
    }

    public static final class MusicParam {
        private final RectF rectF;
        private final PointF startPointF;
        private final PointF endPointF;
        private float offsetY = 0;

        public MusicParam(RectF rectF, PointF sPointF, PointF ePointF) {
            this.rectF = rectF;
            startPointF = sPointF;
            endPointF = ePointF;
        }

        public RectF getCircleRectF() {
            return rectF;
        }

        public PointF getLineStartPointF() {
            return startPointF;
        }

        public PointF getLineEndPointF() {
            return endPointF;
        }

        public float getOffsetY() {
            return offsetY;
        }

        public void setOffsetY(float v) {
            offsetY = v;
        }

        public void clear() {
            offsetY = 0;
        }
    }
}
