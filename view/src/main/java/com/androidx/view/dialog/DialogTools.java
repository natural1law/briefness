package com.androidx.view.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Size;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatTextView;

import com.androidx.view.R;
import com.zyao89.view.zloading.ZLoadingTextView;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * @author 李玄道
 * @date 2021/03/09
 * @see DialogTools 提示窗口工具
 */
public final class DialogTools extends AppCompatDialog {

    /**
     * 基础参数
     */
    private final int bdr;
    private final int width;
    private final int height;
    private final int layout;
    private final int gravity;
    private final int animations;
    private final boolean canceled;
    private final boolean cancelable;
    private final OnEventTriggerListener listener;
    /**
     * 内容模块参数
     */
    private final String contentText;
    private final int contentSize;
    private final int contentColor;
    private final int contentColorId;
    private final int contentTextStyle;
    /**
     * 标题模块参数
     */
    private final String titleText;
    private final int titleSize;
    private final int titleColor;
    private final int titleColorId;
    private final int titleTextStyle;
    /**
     * 确认按钮模块参数
     */
    private final String affirmText;
    private final int affirmSize;
    private final int affirmColor;
    private final int affirmColorId;
    private final int affirmTextStyle;
    private final int backDrawableAffirm;
    /**
     * 取消按钮模块参数
     */
    private final String quitText;
    private final int quitSize;
    private final int quitColor;
    private final int quitColorId;
    private final int quitTextStyle;
    private final int backDrawableQuit;
    /**
     * 倒计时模块参数
     */
    private final int totalTime;
    private final String cdPrefix;
    private final String cdSuffix;

    /**
     * 布局文件
     */
    public static final class LayoutResId {
        public static final int A = R.layout.dialog1;
        public static final int B = R.layout.dialog2;
        public static final int C = R.layout.dialog3;
        public static final int D = R.layout.dialog4;
        public static final int E = R.layout.dialog5;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout);
        setCanceledOnTouchOutside(canceled);
        setCancelable(cancelable);
        Window window = this.getWindow();
        if (window != null) {
            FrameLayout layout = findViewById(R.id.dialog_frame);
            window.setBackgroundDrawableResource(bdr);
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = width;
            params.height = height;
            layout.setLayoutParams(params);
            if (gravity != -1) {
                params.gravity = gravity;
            }
            window.setWindowAnimations(animations);
        }
        try {
            titleView();
            contentView();
            affirmView();
            quitView();
            timingView();
        } catch (Exception e) {
            Log.e("dialogTools异常", String.valueOf(e.getMessage()), e);
        }

    }

    /**
     * 内容布局
     */
    private void contentView() {
        AppCompatTextView contentView = findViewById(R.id.dialog_content);
        if (contentView != null) {
            if (contentText != null) {
                contentView.setText(contentText);
            }
            if (contentSize != -1) {
                contentView.setTextSize(contentSize);
            }
            if (contentTextStyle != -1) {
                contentView.setTypeface(Typeface.SANS_SERIF, contentTextStyle);
            } else {
                contentView.setTypeface(Typeface.DEFAULT);
            }
            if (contentColorId != -1) {
                contentView.setTextColor(getContext().getResources().getColor(contentColorId, getContext().getTheme()));
            } else {
                contentView.setTextColor(contentColor);
            }
        }
    }

    /**
     * 标题布局
     */
    private void titleView() {
        AppCompatTextView titleView = findViewById(R.id.dialog_title);
        if (titleView != null) {
            if (titleText != null) {
                titleView.setText(titleText);
            }
            if (titleColor != -1) {
                titleView.setTextColor(titleColor);
            }
            if (titleSize != -1) {
                titleView.setTextSize(titleSize);
            }
            if (titleTextStyle != -1) {
                titleView.setTypeface(Typeface.SANS_SERIF, titleTextStyle);
            } else {
                titleView.setTypeface(Typeface.DEFAULT);
            }
            if (titleColorId != -1) {
                titleView.setTextColor(getContext().getResources().getColor(titleColorId, getContext().getTheme()));
            }
        }
    }

    /**
     * 确认按钮布局
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    private void affirmView() {
        AppCompatTextView affirmView = findViewById(R.id.dialog_affirm);
        AppCompatAutoCompleteTextView paramView = findViewById(R.id.dialog_param);
        if (affirmView != null) {
            if (affirmText != null) {
                affirmView.setText(affirmText);
            }
            if (affirmSize != -1) {
                affirmView.setTextSize(affirmSize);
            }
            if (backDrawableAffirm != -1) {
                affirmView.setBackgroundDrawable(getContext().getResources().getDrawable(backDrawableAffirm, getContext().getTheme()));
            }
            if (affirmTextStyle != -1) {
                affirmView.setTypeface(Typeface.SANS_SERIF, affirmTextStyle);
            } else {
                affirmView.setTypeface(Typeface.DEFAULT);
            }
            if (affirmColorId != -1) {
                affirmView.setTextColor(getContext().getResources().getColor(affirmColorId, getContext().getTheme()));
            } else {
                affirmView.setTextColor(affirmColor);
            }
            if (listener != null) {
                affirmView.setOnClickListener(v -> listener.ok(this));
            }
            if (paramView != null && !paramView.getText().toString().equals("")) {
                String param = paramView.getText().toString().trim();
                affirmView.setOnClickListener(v -> {
                    listener.ok(this, param);
                    paramView.setText("");
                });
            }
        }
    }

    /**
     * 倒计时布局
     */
    private void timingView() {
        ZLoadingTextView countDownView = findViewById(R.id.dialog_timing_animation);
        if (countDownView != null && totalTime != 0) {
            CountDownTimer countDownTimer = new CountDownTimer(totalTime, 1000) {
                @Override
                public void onTick(long m) {
                    if (countDownView != null) {
                        countDownView.setText(cdPrefix + (m / 1000 + 1) + "秒" + cdSuffix);
                    }
                }

                @Override
                public void onFinish() {
                    cancel();
                }
            };
            countDownTimer.start();
        }
    }

    /**
     * 取消按钮布局
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    private void quitView() {
        AppCompatTextView quitView = findViewById(R.id.dialog_quit);
        if (quitView != null) {
            if (quitText != null) {
                quitView.setText(quitText);
            }
            if (quitSize != -1) {
                quitView.setTextSize(quitSize);
            }
            if (quitTextStyle != -1) {
                quitView.setTypeface(Typeface.SANS_SERIF, quitTextStyle);
            } else {
                quitView.setTypeface(Typeface.DEFAULT);
            }
            if (quitColorId != -1) {
                quitView.setTextColor(getContext().getResources().getColor(quitColorId, getContext().getTheme()));
            } else {
                quitView.setTextColor(quitColor);
            }
            if (backDrawableQuit != -1) {
                quitView.setBackgroundDrawable(getContext().getResources().getDrawable(backDrawableQuit, getContext().getTheme()));
            }
            if (listener != null) {
                quitView.setOnClickListener(v -> listener.no(this));
            }
        }
    }

    private DialogTools(Context context, @NotNull Builder builder) {
        super(context, builder.style);
        this.layout = builder.layout;
        this.canceled = builder.canceled;
        this.cancelable = builder.cancelable;
        this.bdr = builder.bdr;
        this.width = builder.width;
        this.height = builder.height;
        this.gravity = builder.gravity;
        this.animations = builder.animations;
        this.contentText = builder.contentText;
        this.contentSize = builder.contentSize;
        this.contentColor = builder.contentColor;
        this.contentColorId = builder.contentColorId;
        this.contentTextStyle = builder.contentTextStyle;
        this.titleText = builder.titleText;
        this.titleSize = builder.titleSize;
        this.titleColor = builder.titleColor;
        this.titleColorId = builder.titleColorId;
        this.titleTextStyle = builder.titleTextStyle;
        this.affirmText = builder.affirmText;
        this.affirmSize = builder.affirmSize;
        this.affirmColor = builder.affirmColor;
        this.affirmColorId = builder.affirmColorId;
        this.affirmTextStyle = builder.affirmTextStyle;
        this.backDrawableAffirm = builder.backDrawableAffirm;
        this.quitText = builder.quitText;
        this.quitSize = builder.quitSize;
        this.quitColor = builder.quitColor;
        this.quitColorId = builder.quitColorId;
        this.quitTextStyle = builder.quitTextStyle;
        this.backDrawableQuit = builder.backDrawableQuit;
        this.listener = builder.listener;
        this.totalTime = builder.totalTime;
        this.cdPrefix = builder.cdPrefix;
        this.cdSuffix = builder.cdSuffix;
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static Builder builder(Context context) {
        return new Builder(context);
    }

    public static final class Builder {

        private final Builder newBuilder = this;
        private final Context context;
        private int layout = R.layout.dialog2;
        private int style = R.style.dialogStyle;
        private int animations = R.style.animation;
        private int width = -1;
        private int height = -1;
        private int gravity = -1;
        private boolean canceled = true;
        private boolean cancelable = true;
        private int bdr = android.R.color.transparent;
        private int totalTime = 0;
        private String cdPrefix;
        private String cdSuffix;
        private String contentText = null;
        private int contentSize = -1;
        private int contentColor = Color.parseColor("#292929");
        private int contentColorId = -1;
        private int contentTextStyle = -1;
        private String titleText = null;
        private int titleSize = -1;
        private int titleColor = Color.parseColor("#292929");
        private int titleColorId = -1;
        private int titleTextStyle = -1;
        private String affirmText = "确认";
        private int affirmSize = -1;
        private int affirmColor = Color.parseColor("#292929");
        private int affirmColorId = -1;
        private int affirmTextStyle = -1;
        private int backDrawableAffirm = -1;
        private String quitText = "取消";
        private int quitSize = -1;
        private int quitColor = Color.parseColor("#292929");
        private int quitColorId = -1;
        private int quitTextStyle = -1;
        private int backDrawableQuit = -1;
        private OnEventTriggerListener listener;

        private Builder(Context context) {
            this.context = context;
        }

        /**
         * dialog样式配置
         *
         * @param id 样式资源ID
         */
        public Builder setStyle(@StyleRes int id) {
            this.style = id;
            return newBuilder;
        }

        /**
         * dialog布局配置
         * 可以添加自定义布局，但是控件资源ID对应程序中调用
         *
         * @param id 布局资源ID
         */
        public Builder setLayout(@LayoutRes int id) {
            this.layout = id;
            return newBuilder;
        }

        /**
         * 设置二次元维度
         *
         * @param dimension 维度值（宽高）
         */
        public Builder setDimension(@NotNull int... dimension) {
            this.width = dimension[0];
            this.height = dimension[1];
            return newBuilder;
        }

        /**
         * 设置布局比例重力
         *
         * @param gravity 布局重力
         */
        public Builder setGravity(int gravity) {
            this.gravity = gravity;
            return newBuilder;
        }

        /**
         * 设置动画
         *
         * @param animations 动画资源ID
         */
        public Builder setAnimations(@StyleRes int animations) {
            this.animations = animations;
            return newBuilder;
        }

        /**
         * 设置该对话框在窗口外被触摸时是否被取消
         * 如果设置为true，则对话框设置为可取消
         * 默认开启
         *
         * @param flag 开关
         */
        public Builder setCanceled(boolean flag) {
            this.canceled = flag;
            return newBuilder;
        }

        /**
         * 设置是否可取消此对话框
         * 默认开启
         *
         * @param flag 开关
         */
        public Builder setCancelable(boolean flag) {
            this.cancelable = flag;
            return newBuilder;
        }

        /**
         * 将此窗口的背景更改为可绘制资源
         * 设置背景设为空将使窗口不透明
         * 使窗户透明，你可以使用一个空的drawable
         *
         * @param id 资源ID
         */
        public Builder setBdr(@DrawableRes int id) {
            this.bdr = id;
            return newBuilder;
        }

        /**
         * 设置内容显示信息
         *
         * @param contentText 内容信息
         */
        public <T> Builder setContentText(@Nullable T contentText) {
            this.contentText = String.valueOf(contentText);
            return newBuilder;
        }

        /**
         * 设置内容字体大小
         *
         * @param contentSize 字体大小
         */
        public Builder setContentSize(@Size int contentSize) {
            this.contentSize = contentSize;
            return newBuilder;
        }

        /**
         * 设置内容字体颜色
         *
         * @param contentColor 字体颜色
         */
        public Builder setContentColor(@NotNull String contentColor) {
            if (contentColor.contains("#")) {
                this.contentColor = Color.parseColor(contentColor);
            } else {
                this.contentColor = Color.parseColor("#" + contentColor);
            }
            return newBuilder;
        }

        /**
         * 设置内容字体颜色
         *
         * @param contentColorId 字体颜色
         */
        public Builder setContentColorId(@ColorRes int contentColorId) {
            this.contentColorId = contentColorId;
            return newBuilder;
        }

        /**
         * 设置内容字体风格
         *
         * @param contentTextStyle 字体风格
         */
        public Builder setContentTextStyle(int contentTextStyle) {
            this.contentTextStyle = contentTextStyle;
            return newBuilder;
        }

        /**
         * 设置标题文字内容
         *
         * @param titleText 文字内容
         */
        public <T> Builder setTitleText(@Nullable T titleText) {
            this.titleText = String.valueOf(titleText);
            return newBuilder;
        }

        /**
         * 设置标题字体大小
         *
         * @param titleSize 字体大小
         */
        public Builder setTitleSize(@Size int titleSize) {
            this.titleSize = titleSize;
            return newBuilder;
        }

        /**
         * 设置标题字体颜色
         *
         * @param titleColor 字体颜色
         */
        public Builder setTitleColor(@NotNull String titleColor) {
            if (titleColor.contains("#")) {
                this.titleColor = Color.parseColor(titleColor);
            } else {
                this.titleColor = Color.parseColor("#" + titleColor);
            }
            return newBuilder;
        }

        /**
         * 设置标题字体颜色
         *
         * @param titleColorId 字体颜色
         */
        public Builder setTitleColorId(@ColorRes int titleColorId) {
            this.titleColorId = titleColorId;
            return newBuilder;
        }

        /**
         * 设置标题字体风格
         *
         * @param titleTextStyle 字体风格
         */
        public Builder setTitleTextStyle(int titleTextStyle) {
            this.titleTextStyle = titleTextStyle;
            return newBuilder;
        }

        /**
         * 设置确认按钮的文字显示
         *
         * @param affirmText 文字内容
         */
        public <T> Builder setAffirmText(@NonNull T affirmText) {
            this.affirmText = String.valueOf(affirmText);
            return newBuilder;
        }

        /**
         * 设置确认按钮字体大小
         *
         * @param affirmSize 字体大小
         */
        public Builder setAffirmSize(@Size int affirmSize) {
            this.affirmSize = affirmSize;
            return newBuilder;
        }

        /**
         * 设置确认按钮字体颜色
         *
         * @param affirmColor 字体颜色
         */
        public Builder setAffirmColor(@NotNull String affirmColor) {
            if (affirmColor.contains("#")) {
                this.affirmColor = Color.parseColor(affirmColor);
            } else {
                this.affirmColor = Color.parseColor("#" + titleColor);
            }
            return newBuilder;
        }

        /**
         * 设置确认按钮字体颜色
         *
         * @param affirmColorId 字体颜色
         */
        public Builder setAffirmColorId(@ColorRes int affirmColorId) {
            this.affirmColorId = affirmColorId;
            return newBuilder;
        }

        /**
         * 设置确认按钮字体风格
         *
         * @param affirmTextStyle 字体风格
         */
        public Builder setAffirmTextStyle(int affirmTextStyle) {
            this.affirmTextStyle = affirmTextStyle;
            return newBuilder;
        }

        /**
         * 设置确认按钮背景样式
         *
         * @param backDrawableAffirm 样式资源ID
         */
        public Builder setBackDrawableAffirm(@DrawableRes int backDrawableAffirm) {
            this.backDrawableAffirm = backDrawableAffirm;
            return newBuilder;
        }

        /**
         * 设置取消按钮的文字显示
         *
         * @param quitText 文字内容
         */
        public <T> Builder setQuitText(@NonNull T quitText) {
            this.quitText = String.valueOf(quitText);
            return newBuilder;
        }

        /**
         * 设置取消按钮字体大小
         *
         * @param quitSize 字体大小
         */
        public Builder setQuitSize(@Size int quitSize) {
            this.quitSize = quitSize;
            return newBuilder;
        }

        /**
         * 设置取消按钮字体颜色
         *
         * @param quitColor 字体颜色
         */
        public Builder setQuitColor(@NotNull String quitColor) {
            if (quitColor.contains("#")) {
                this.quitColor = Color.parseColor(quitColor);
            } else {
                this.quitColor = Color.parseColor("#" + titleColor);
            }
            return newBuilder;
        }

        /**
         * 设置取消按钮字体颜色
         *
         * @param quitColorId 字体颜色
         */
        public Builder setQuitColorId(@ColorRes int quitColorId) {
            this.quitColorId = quitColorId;
            return newBuilder;
        }

        /**
         * 设置取消按钮字体的风格
         *
         * @param quitTextStyle 字体风格
         */
        public Builder setQuitTextStyle(int quitTextStyle) {
            this.quitTextStyle = quitTextStyle;
            return newBuilder;
        }

        /**
         * 设置取消按钮背景样式
         *
         * @param backDrawableQuit 样式资源ID
         */
        public Builder setBackDrawableQuit(@DrawableRes int backDrawableQuit) {
            this.backDrawableQuit = backDrawableQuit;
            return newBuilder;
        }

        /**
         * 确认按钮或取消按钮触发事件的实现
         *
         * @param listener 事件
         */
        public void setListener(OnEventTriggerListener listener) {
            this.listener = listener;
        }

        /**
         * 倒计时（引入相应布局文件totalTime>0开启）
         *
         * @param totalTime 总时长
         */
        public void setTotalTime(int totalTime) {
            this.totalTime = totalTime;
        }

        /**
         * 倒计时显示前缀
         *
         * @param cdPrefix 前缀
         */
        public void setCdPrefix(String cdPrefix) {
            this.cdPrefix = cdPrefix;
        }

        /**
         * 倒计时显示后缀
         *
         * @param cdSuffix 后缀
         */
        public void setCdSuffix(String cdSuffix) {
            this.cdSuffix = cdSuffix;
        }

        @NotNull
        @Contract(" -> new")
        public DialogTools build() {
            synchronized (DialogTools.class) {
                return new DialogTools(context, newBuilder);
            }
        }
    }

    /**
     * 确认按钮或取消按钮触发事件接口
     */
    public interface OnEventTriggerListener {

        void ok(DialogTools dialog);

        default void ok(DialogTools dialog, String param) {

        }

        default void no(DialogTools dialog) {
            dialog.cancel();
        }
    }
}
