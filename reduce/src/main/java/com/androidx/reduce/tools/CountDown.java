package com.androidx.reduce.tools;

import android.annotation.SuppressLint;
import android.os.CountDownTimer;
import android.os.Parcel;
import android.os.Parcelable;
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

    private CountDown(Builder builder) {
        super(builder.millisInFuture, 1000);
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
        wrTextView.get().setText(prefixWordage + (millisUntilFinished / 1000) + suffixWordage);
    }

    @Override
    public void onFinish() {
        wrTextView.get().setClickable(true);
        wrTextView.get().setText(finishWordage);
        cancel();
    }

    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static final class Builder implements Parcelable {

        private Builder builder = this;
        private TextView view;//文本布局
        private int millisInFuture = 60000;//总时长(默认一分钟)
        private String finishWordage = "重新获取";//设置倒计时完成文字
        private String prefixWordage = "";//设置倒计时执行时前缀文字
        private String suffixWordage = " s";//设置倒计时执行时后缀文字

        private Builder() {
        }

        private Builder(Parcel in) {
            builder = in.readParcelable(Builder.class.getClassLoader());
            millisInFuture = in.readInt();
            finishWordage = in.readString();
            prefixWordage = in.readString();
            suffixWordage = in.readString();
        }

        protected static final Creator<Builder> CREATOR = new Creator<Builder>() {
            @Override
            public Builder createFromParcel(Parcel in) {
                return new Builder(in);
            }

            @Override
            public Builder[] newArray(int size) {
                return new Builder[size];
            }
        };

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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(builder, flags);
            dest.writeInt(millisInFuture);
            dest.writeString(finishWordage);
            dest.writeString(prefixWordage);
            dest.writeString(suffixWordage);
        }
    }

}
