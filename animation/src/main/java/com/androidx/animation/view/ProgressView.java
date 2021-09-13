package com.androidx.animation.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.androidx.animation.R;
import com.androidx.animation.base.BaseAnimator;
import com.androidx.animation.base.Nape;
import com.androidx.animation.base.ProgressDrawable;

/**
 * @createDate 2021/09/10
 */
@SuppressLint("CustomViewStyleable")
public class ProgressView extends AppCompatImageView {

    private ProgressDrawable progressDrawable;
    protected BaseAnimator baseAnimator;

    public ProgressView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    protected ProgressView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typed = context.obtainStyledAttributes(attrs, R.styleable.progress);
        int type = typed.getInt(R.styleable.progress_viewType, 0);
        int color = typed.getColor(R.styleable.progress_viewColor, Color.GRAY);
        float duration = typed.getFloat(R.styleable.progress_viewDuration, 1.0f);
        typed.recycle();
        this.setType(type, duration);
        this.setColorFilter(color);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.startAnimation();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.stopAnimation();
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        final boolean visible = visibility == VISIBLE && getVisibility() == VISIBLE;
        if (visible) this.startAnimation();
        else this.stopAnimation();
    }

    /* START ----------------------------------SET--GET------------------------------------------ */

    public void setType(int type) {
        this.setType(type, 1.0f);
    }

    public void setType(int type, float duration) {
        baseAnimator = Nape.newInstance(type);
        if (baseAnimator != null) {
            progressDrawable = new ProgressDrawable(baseAnimator);
            progressDrawable.initParams(getContext());
            this.setImageDrawable(progressDrawable);
            baseAnimator.setDuration(duration);
        }
    }

    private void startAnimation() {
        if (progressDrawable != null) progressDrawable.start();
    }

    private void stopAnimation() {
        if (progressDrawable != null) progressDrawable.stop();
    }

    /* END ------------------------------------SET--GET------------------------------------------ */

}
