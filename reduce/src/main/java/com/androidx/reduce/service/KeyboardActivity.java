package com.androidx.reduce.service;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.androidx.reduce.tools.ScreenUtils;

@SuppressWarnings({"RedundantSuppression", "unused"})
public class KeyboardActivity extends AppCompatActivity implements ViewTreeObserver.OnGlobalLayoutListener {

    public int screenHeight = 0;
    private OnGlobalLayoutListener onGlobalLayoutListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        screenHeight = ScreenUtils.getScreenHeight(getApplicationContext());
    }

    @Override
    protected void onResume() {
        super.onResume();
        addGlobal();
    }

    @Override
    protected void onStop() {
        super.onStop();
        removeGlobal();
    }

    private void addGlobal() {
        if (onGlobalLayoutListener != null)
            getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(this);

    }

    private void removeGlobal() {
        getWindow().getDecorView().getViewTreeObserver().removeOnGlobalLayoutListener(this);

    }

    @Override
    public void onGlobalLayout() {
        Rect r = new Rect();
        //获取当前界面可视部分
        getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
        //如果屏幕高度和Window可见区域高度差值大于整个屏幕高度的1/3，则表示软键盘显示中，否则软键盘为隐藏状态。
        boolean isKeyboardShowing = r.bottom < ScreenUtils.getScreenHeight(getApplicationContext());

        removeGlobal();
        if (isKeyboardShowing) {
            //会导致布局不断刷新，无限制回调当前方法,所以做延迟回调处理
            onGlobalLayoutListener.onKeyboardShowedNow(r);
        } else {
            onGlobalLayoutListener.onKeyboardHideNow();
        }
        new Handler().postDelayed(this::addGlobal, 100);

    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setOnKeyboardLayoutListener(OnGlobalLayoutListener onGlobalLayoutListener) {
        this.onGlobalLayoutListener = onGlobalLayoutListener;
        addGlobal();
    }

    public void setOnKeyboardScrollView(FrameLayout view) {
        this.onGlobalLayoutListener = new OnGlobalLayoutListener() {
            @Override
            public void onKeyboardShowedNow(Rect rect) {
                view.scrollTo(0, screenHeight - rect.bottom);
            }

            @Override
            public void onKeyboardHideNow() {
                view.scrollTo(0, 0);
            }
        };
        addGlobal();
    }

    public interface OnGlobalLayoutListener {
        void onKeyboardShowedNow(Rect rect);//键盘刚显示

        void onKeyboardHideNow();//键盘刚隐藏
    }

}
