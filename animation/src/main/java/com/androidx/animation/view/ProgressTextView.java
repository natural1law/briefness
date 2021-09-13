package com.androidx.animation.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.androidx.animation.R;
import com.androidx.animation.figure.Text;

public class ProgressTextView extends ProgressView {

    private String text;

    public ProgressTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    @SuppressLint("CustomViewStyleable")
    protected ProgressTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.progressText);
        String text = ta.getString(R.styleable.progressText_textContent);
        ta.recycle();
        if (!TextUtils.isEmpty(text)) this.text = text;
    }

    @Override
    protected void onAttachedToWindow() {
        if (baseAnimator instanceof Text) ((Text) baseAnimator).setText(text);
        super.onAttachedToWindow();
    }

    public void setText(String text) {
        this.text = text;
    }
}
