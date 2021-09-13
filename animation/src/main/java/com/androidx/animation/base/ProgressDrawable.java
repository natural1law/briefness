package com.androidx.animation.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @createDate 2021/09/10
 */
@SuppressLint("CustomViewStyleable")
public final class ProgressDrawable extends AnimationDrawable {

    private final BaseAnimator base;

    public void initParams(Context context) {
        if (base != null) {
            base.init(context);
            base.initParams(context);
        }
    }

    public ProgressDrawable(BaseAnimator base) {
        this.base = base;
        this.base.setCallback(new Callback() {
            @Override
            public void invalidateDrawable(@NonNull Drawable drawable) {
                invalidateSelf();
            }

            @Override
            public void scheduleDrawable(@NonNull Drawable drawable, @NonNull Runnable runnable, long l) {
                scheduleSelf(runnable, l);
            }

            @Override
            public void unscheduleDrawable(@NonNull Drawable drawable, @NonNull Runnable runnable) {
                unscheduleSelf(runnable);
            }
        });
    }

    @Override
    public void start() {
        this.base.start();
    }

    @Override
    public void stop() {
        this.base.stop();
    }

    @Override
    public boolean isRunning() {
        return this.base.isRunning();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        if (!getBounds().isEmpty()) this.base.draw(canvas);
    }

    @Override
    public void setAlpha(int i) {
        this.base.setAlpha(i);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        this.base.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
