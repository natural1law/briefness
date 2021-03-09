package com.androidx.view.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.widget.AppCompatTextView;

import com.androidx.view.R;
import com.zyao89.view.zloading.ZLoadingTextView;

/**
 * 定时跳转弹窗
 *
 * @author 李玄道
 * @date 2020/06/29
 */
@SuppressWarnings({"unused", "RedundantSuppression"})
public final class DialogTiming extends AppCompatDialog {

    private final String contentText;
    private final String countText;
    private final long count;
    private final int contentColor;
    private final int contentTextSize;
    private final int width;
    private final int height;
    private final int gravity;
    private final int animations;
    private final boolean cancel;
    private final boolean flag;
    private static ICountDown countDown;
    private static AppCompatDialog dialog;

    private DialogTiming(Context context, Builder builder) {
        super(context, R.style.dialogStyle);
        contentText = builder.contentText;
        countText = builder.countText;
        count = builder.count;
        countDown = builder.countDown;
        contentColor = builder.contentColor;
        contentTextSize = builder.contentTextSize;
        width = builder.width;
        height = builder.height;
        gravity = builder.gravity;
        animations = builder.animations;
        cancel = builder.cancel;
        flag = builder.flag;
    }

    private static final Handler handler = new Handler(Looper.getMainLooper(), message -> {
        if (message.what == -1) {
            countDown.onFinish(dialog);
        }
        return false;
    });

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog5);
        dialog = this;
        AppCompatTextView contentView = findViewById(R.id.dialog_content);
        ZLoadingTextView countDownView = findViewById(R.id.dialog_timing_animation);
        setCanceledOnTouchOutside(cancel);
        setCancelable(flag);
        Window window = this.getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(android.R.color.transparent);
            WindowManager.LayoutParams params = window.getAttributes();
            params.gravity = gravity;
            params.width = width;
            params.height = height;
            window.setWindowAnimations(animations);
        }
        if (contentView != null) {
            contentView.setText(contentText);
            contentView.setTextColor(contentColor);
            contentView.setTextSize(contentTextSize);
        }
        //倒计时显示
        new CountDownTimer(count * 1000, 1000) {
            @Override
            public void onTick(long m) {
                if (countDownView != null) {
                    countDownView.setText((m / 1000 + 1) + "秒" + countText);
                }
            }

            @Override
            public void onFinish() {
                handler.sendEmptyMessage(-1);
            }
        }.start();
    }

    public interface ICountDown {
        void onFinish(AppCompatDialog dialog);
    }

    public static final class Builder {

        private final Context context;
        private final Builder builder;
        private String contentText;
        private String countText;
        private long count;
        private ICountDown countDown;
        private int contentColor = Color.parseColor("#282222");
        private int contentTextSize = 16;
        private int width = 720;
        private int height = 450;
        private int gravity = Gravity.CENTER;
        private int animations = R.style.animation;
        private boolean cancel = false;
        private boolean flag = false;

        private Builder(Context context) {
            this.context = context;
            this.builder = this;
        }

        public <T> Builder setContentText(T msg) {
            this.contentText = String.valueOf(msg);
            return builder;
        }

        public Builder setCountText(String countText) {
            this.countText = countText;
            return builder;
        }

        public Builder setTimeDownListener(ICountDown countDown) {
            this.countDown = countDown;
            return builder;
        }

        public Builder setTimeDown(long count) {
            this.count = count;
            return builder;
        }

        public Builder setContentTextColor(String id) {
            this.contentColor = Color.parseColor("#" + id);
            return builder;
        }

        public Builder setContentTextSize(int id) {
            this.contentTextSize = id;
            return builder;
        }

        public Builder setWidth(int value) {
            this.width = value;
            return builder;
        }

        public Builder setHeight(int value) {
            this.height = value;
            return builder;
        }

        public Builder setGravity(int value) {
            this.gravity = value;
            return builder;
        }

        public Builder setScreenOut(boolean cancel) {
            this.cancel = cancel;
            return builder;
        }

        public Builder setBack(boolean flag) {
            this.flag = flag;
            return builder;
        }

        public Builder setAnimations(int id) {
            this.animations = id;
            return builder;
        }

        public DialogTiming build() {
            try {
                DialogTiming instance;
                synchronized (DialogTiming.class) {
                    instance = new DialogTiming(context, builder);
                }
                return instance;
            } catch (Exception e) {
                return new DialogTiming(context, builder);
            }
        }
    }

    public static Builder builder(Context context) {
        try {
            synchronized (Builder.class) {
                return new Builder(context);
            }
        } catch (Exception e) {
            return new Builder(context);
        }
    }
}
