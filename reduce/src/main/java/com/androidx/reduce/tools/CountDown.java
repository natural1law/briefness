package com.androidx.reduce.tools;

import android.annotation.SuppressLint;
import android.os.CountDownTimer;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * @date 2021/02/19
 * @see CountDownTimer 倒计时工具类
 */
public final class CountDown extends CountDownTimer {

    private static String finishWordage;//设置倒计时完成文字
    private static String prefixWordage;//设置倒计时执行时前缀文字
    private static String suffixWordage;//设置倒计时执行时后缀文字
    private static Object view;//文本布局

    private CountDown(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    @Contract("_ -> new")
    private static @NotNull CountDown builds(Builder builder) {
        view = builder.view;
        finishWordage = builder.finishWordage;
        prefixWordage = builder.prefixWordage;
        suffixWordage = builder.suffixWordage;
        return new CountDown(builder.millisInFuture, builder.countDownInterval);
    }

    @Contract(" -> new")
    public static @NotNull Builder builder() {
        try {
            synchronized (Builder.class) {
                return new Builder();
            }
        } catch (Exception e) {
            return new Builder();
        }
    }

    @Override
    @SuppressLint("SetTextI18n")
    public void onTick(long millisUntilFinished) {
        //防止计时过程中重复点击
        if (view instanceof Button) {
            ((Button) view).setClickable(false);
            ((Button) view).setText(prefixWordage + (millisUntilFinished / 1000) + suffixWordage);
        } else if (view instanceof TextView) {
            ((TextView) view).setClickable(false);
            ((TextView) view).setText(prefixWordage + (millisUntilFinished / 1000) + suffixWordage);
        } else if (view instanceof AppCompatButton) {
            ((AppCompatButton) view).setClickable(false);
            ((AppCompatButton) view).setText(prefixWordage + (millisUntilFinished / 1000) + suffixWordage);
        } else if (view instanceof AppCompatTextView) {
            ((AppCompatTextView) view).setClickable(false);
            ((AppCompatTextView) view).setText(prefixWordage + (millisUntilFinished / 1000) + suffixWordage);
        } else {
            Log.e("倒计时工具类", "布局控件不匹配");
        }
    }

    @Override
    public void onFinish() {
        if (view instanceof Button) {
            ((Button) view).setClickable(true);
            ((Button) view).setText(finishWordage);
        } else if (view instanceof TextView) {
            ((TextView) view).setClickable(true);
            ((TextView) view).setText(finishWordage);
        } else if (view instanceof AppCompatButton) {
            ((AppCompatButton) view).setClickable(true);
            ((AppCompatButton) view).setText(finishWordage);
        } else if (view instanceof AppCompatTextView) {
            ((AppCompatTextView) view).setClickable(true);
            ((AppCompatTextView) view).setText(finishWordage);
        } else {
            Log.e("倒计时工具类", "布局控件不匹配");
        }
        cancel();
    }

    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static final class Builder implements Parcelable {

        private Builder builder = this;
        private Object view;//文本布局
        private int millisInFuture = 60000;//总时长(默认一分钟)
        private int countDownInterval = 1000;//间隔时间（毫秒/默认一秒间隔）
        private String finishWordage = "重新获取";//设置倒计时完成文字
        private String prefixWordage = "";//设置倒计时执行时前缀文字
        private String suffixWordage = " s";//设置倒计时执行时后缀文字

        private Builder() {
        }

        private Builder(Parcel in) {
            builder = in.readParcelable(Builder.class.getClassLoader());
            millisInFuture = in.readInt();
            countDownInterval = in.readInt();
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
        public Builder setView(@NonNull Object view) {
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
         * 设置倒计时间隔时间
         *
         * @param countDownInterval 间隔时间
         */
        public Builder setCountDownInterval(int countDownInterval) {
            this.countDownInterval = countDownInterval;
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
            try {
                synchronized (CountDown.class) {
                    return builds(builder);
                }
            } catch (Exception e) {
                return builds(builder);
            }
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(builder, flags);
            dest.writeInt(millisInFuture);
            dest.writeInt(countDownInterval);
            dest.writeString(finishWordage);
            dest.writeString(prefixWordage);
            dest.writeString(suffixWordage);
        }
    }

}
