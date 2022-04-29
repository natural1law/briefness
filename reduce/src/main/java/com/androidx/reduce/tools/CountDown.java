package com.androidx.reduce.tools;

import android.annotation.SuppressLint;
import android.os.CountDownTimer;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;

/**
 * @date 2021/02/19
 * @see CountDown 倒计时工具类
 */
public final class CountDown extends CountDownTimer {

    private static String finishWordage;//设置倒计时完成文字
    private static String prefixWordage;//设置倒计时执行时前缀文字
    private static String suffixWordage;//设置倒计时执行时后缀文字
    private static WeakReference<TextView> wrTextView;//文本布局
    private static final int COUNT = 1000;
    private volatile long millis;

    private CountDown(Builder builder) {
        super(builder.millisInFuture, COUNT);
        wrTextView = new WeakReference<>(builder.view);
        finishWordage = builder.finishWordage;
        prefixWordage = builder.prefixWordage;
        suffixWordage = builder.suffixWordage;
    }

    @Contract(" -> new")
    public static @NotNull Builder builder() {
        return new Builder();
    }

    @Override
    @SuppressLint("SetTextI18n")
    public void onTick(long millisUntilFinished) {
        //防止计时过程中重复点击
        wrTextView.get().setClickable(false);
        this.millis = millisUntilFinished;
        long time = (millis / COUNT);
        wrTextView.get().setText(prefixWordage + time + suffixWordage);
    }

    @Override
    public void onFinish() {
        wrTextView.get().setClickable(true);
        wrTextView.get().setText(finishWordage);
        millis = 0;
        cancel();
    }

    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static final class Builder {

        private final Builder builder = this;
        private TextView view;//文本布局
        private volatile int millisInFuture = 60000;//总时长(默认一分钟)
        private String finishWordage = "重新获取";//设置倒计时完成文字
        private String prefixWordage = "";//设置倒计时执行时前缀文字
        private String suffixWordage = " s";//设置倒计时执行时后缀文字

        private Builder() {
        }

        /**
         * 设置文本布局
         *
         * @param view 文本控件
         */
        public <V extends TextView> Builder setView(@NonNull V view) {
            this.view = view;
            return builder;
        }

        /**
         * 设置倒计时总时长
         *
         * @param millisInFuture 总时长
         */
        public Builder setMillisInFuture(int millisInFuture) {
            this.millisInFuture = millisInFuture;
            return builder;
        }

        /**
         * 设置倒计时完成文字
         *
         * @param finishWordage 完成文字
         */
        public Builder setFinishWordage(String finishWordage) {
            this.finishWordage = finishWordage;
            return builder;
        }

        /**
         * 设置倒计时执行时前缀文字
         *
         * @param prefixWordage 前缀文字
         */
        public Builder setPrefixWordage(String prefixWordage) {
            this.prefixWordage = prefixWordage;
            return builder;
        }

        /**
         * 设置倒计时执行时后缀文字
         *
         * @param suffixWordage 后缀文字
         */
        public Builder setSuffixWordage(String suffixWordage) {
            this.suffixWordage = suffixWordage;
            return builder;
        }

        public CountDown build() {
            return new CountDown(builder);
        }

    }

}
