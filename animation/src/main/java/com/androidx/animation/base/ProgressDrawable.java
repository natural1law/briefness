package com.androidx.animation.base;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;

public final class ProgressDrawable extends AnimationDrawable {

    private final BaseAnimator baseAnimator;

    public ProgressDrawable(BaseAnimator builder) {
        this.baseAnimator = builder;
        this.baseAnimator.setCallback(new Callback() {
            @Override
            public void invalidateDrawable(Drawable d) {
                invalidateSelf();
            }

            @Override
            public void scheduleDrawable(Drawable d, Runnable what, long when) {
                scheduleSelf(what, when);
            }

            @Override
            public void unscheduleDrawable(Drawable d, Runnable what) {
                unscheduleSelf(what);
            }
        });
    }

    public void initParams(Context context) {
        if (baseAnimator != null) {
            baseAnimator.init(context);
            baseAnimator.initParams(context);
        }
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        if (!getBounds().isEmpty()) {
            this.baseAnimator.draw(canvas);
        }
    }

    @Override
    public void setAlpha(int alpha) {
        this.baseAnimator.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        this.baseAnimator.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public void start() {
        this.baseAnimator.start();
    }

    @Override
    public void stop() {
        this.baseAnimator.stop();
    }

    @Override
    public boolean isRunning() {
        return this.baseAnimator.isRunning();
    }

    @Override
    public int getIntrinsicHeight() {
        return (int) this.baseAnimator.getIntrinsicHeight();
    }

    @Override
    public int getIntrinsicWidth() {
        return (int) this.baseAnimator.getIntrinsicWidth();
    }
}
