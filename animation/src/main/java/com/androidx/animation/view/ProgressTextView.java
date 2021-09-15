package com.androidx.animation.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;

import com.androidx.animation.R;
import com.androidx.animation.base.ProgressType;
import com.androidx.animation.figure.text.TextBuilder;

public class ProgressTextView extends ProgressView {
    private String mText = "正在加载";

    public ProgressTextView(Context context) {
        this(context, null);
    }

    public ProgressTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void setText(String text) {
        this.mText = text;
        if (baseAnimator instanceof TextBuilder) ((TextBuilder) baseAnimator).setText(mText);
    }

    private void init(Context context, AttributeSet attrs) {
        super.setBuilder(ProgressType.TEXT);
        try {
            TypedArray typed = context.obtainStyledAttributes(attrs, R.styleable.ProgressTextView);
            String text = typed.getString(R.styleable.ProgressTextView_textContent);
            typed.recycle();
            if (!TextUtils.isEmpty(text)) this.mText = text;
        } catch (Exception e) {
            Log.e("ProgressTextView异常", Log.getStackTraceString(e));
        }
    }

    @Override
    protected void onAttachedToWindow() {
        setText(mText);
        super.onAttachedToWindow();
    }
}
