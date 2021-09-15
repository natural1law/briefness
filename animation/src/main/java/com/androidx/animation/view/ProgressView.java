package com.androidx.animation.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;

import com.androidx.animation.R;
import com.androidx.animation.base.BaseAnimator;
import com.androidx.animation.base.ProgressDrawable;
import com.androidx.animation.base.ProgressType;

/**
 * @createDate 2021/09/10
 */
public class ProgressView extends AppCompatImageView {

    private ProgressDrawable drawable;
    protected BaseAnimator baseAnimator;
    private float duration;
    private float size;

    public ProgressView(Context context) {
        this(context, null);
    }

    public ProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        try {
            TypedArray typed = context.obtainStyledAttributes(attrs, R.styleable.ProgressView);
            int typeId = typed.getInt(R.styleable.ProgressView_progressType, 0);
            duration = typed.getFloat(R.styleable.ProgressView_progressDuration, 1.0f);
            size = typed.getFloat(R.styleable.ProgressView_progressSize, 56.0f);
            typed.recycle();
            this.setColorFilter(typed.getColor(R.styleable.ProgressView_progressColor, Color.GRAY));
            this.setBuilder(ProgressType.values()[typeId], duration, size);
        } catch (Exception e) {
            Log.e("ProgressView异常", Log.getStackTraceString(e));
        }
    }

    public void setBuilder(@NonNull ProgressType builder) {
        this.setBuilder(builder, duration);
    }

    public void setBuilder(@NonNull ProgressType builder, double duration) {
        this.setBuilder(builder, duration, size);

    }

    public void setBuilder(@NonNull ProgressType builder, double duration, float size) {
        try {
            baseAnimator = builder.newInstance();
            if (baseAnimator != null) {
                baseAnimator.setSize(size);
                baseAnimator.setDurationTimePercent(duration);
                drawable = new ProgressDrawable(baseAnimator);
                drawable.initParams(getContext());
                setImageDrawable(drawable);
            }
        } catch (Exception e) {
            Log.e("ProgressView异常", Log.getStackTraceString(e));
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAnimation();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAnimation();
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        final boolean visible = visibility == VISIBLE && getVisibility() == VISIBLE;
        if (visible) startAnimation();
        else stopAnimation();
    }

    private void startAnimation() {
        if (drawable != null) drawable.start();
    }

    private void stopAnimation() {
        if (drawable != null) drawable.stop();
    }
}
