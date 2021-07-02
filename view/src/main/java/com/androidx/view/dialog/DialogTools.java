package com.androidx.view.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Size;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidx.reduce.tools.Captcha;
import com.androidx.view.R;
import com.androidx.view.dialog.adapter.CameraAdapter;
import com.zyao89.view.zloading.ZLoadingTextView;
import com.zyao89.view.zloading.ZLoadingView;
import com.zyao89.view.zloading.Z_TYPE;

import org.jetbrains.annotations.NotNull;

import static com.androidx.reduce.tools.Captcha.TYPE.CHARS;
import static com.zyao89.view.zloading.Z_TYPE.CIRCLE;

/**
 * @author 李玄道
 * @date 2021/03/09
 * @see DialogTools 提示窗口工具
 */
public final class DialogTools extends AppCompatDialog {

    /**
     * 基础参数
     */
    int i = 0;
    private final int bdr;
    private final String[] datas;
    private final int left;
    private final int right;
    private final int top;
    private final int bottom;
    private final int width;
    private final int height;
    private final int layout;
    private final int gravity;
    private final int windowColor;
    private final int windowDrawable;
    private final int animations;
    private final boolean canceled;
    private final boolean cancelable;
    private final OnEventTriggerListener listener;
    private final OnClickParamListener paramListener;
    private final OnClickCodeListener codeListener;
    private final OnClickQrListener qrListener;
    private final OnClickRadioListener radioListener;
    private final CameraAdapter.OnClickCameraAdapterListener adapterListener;
    /**
     * 标题模块参数
     */
    private final int titleId;
    private final String titleText;
    private final int titleSize;
    private final int titleColor;
    private final int titleColorId;
    private final int titleTextStyle;
    /**
     * 内容模块参数
     */
    private final int contentId;
    private final String contentText;
    private final int contentSize;
    private final int contentColor;
    private final int contentColorId;
    private final int contentTextStyle;
    /**
     * 确认按钮模块参数
     */
    private final int affirmId;
    private final String affirmText;
    private final int affirmSize;
    private final int affirmColor;
    private final int affirmColorId;
    private final int affirmTextStyle;
    private final int backDrawableAffirm;
    /**
     * 取消按钮模块参数
     */
    private final int quitId;
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
     * 加载动画参数
     */
    private final Z_TYPE loadingType;
    private final int loadingTime;
    private final int loadingColor;
    private final String loadingText;
    private final int loadingTextColor;

    private final String hintText1;
    private final String hintText2;
    private final String hintText3;
    private final String hintText4;

    private final int paramId;
    private final int nameId;
    private final int codeId;
    private final int addressId;
    private final int verificationCodeId;
    private final Captcha captcha = Captcha.build().type(CHARS).backColor(171);
    private String code;
    private AppCompatImageView verificationView;

    /**
     * 布局文件
     */
    public static final class LayoutResId {

        private LayoutResId() {
        }

        /**
         * 普通单点消息提示弹窗
         */
        public static final int ALERT = R.layout.dialog1;
        /**
         * 标准消息提示弹窗（样式1）
         */
        public static final int TIGHTNESS = R.layout.dialog2;
        /**
         * 标准消息提示弹窗（样式2）
         */
        public static final int RELAX = R.layout.dialog3;
        /**
         * 消息输入提示窗
         */
        public static final int INPUT = R.layout.dialog4;
        /**
         * 倒计时提示弹窗
         */
        public static final int COUNT_DOWN = R.layout.dialog5;
        /**
         * 相机相册底部弹窗
         */
        public static final int CAMERA = R.layout.dialog6;
        /**
         * 消息输入提示窗
         */
        public static final int INPUT_CHECK = R.layout.dialog7;
        /**
         * 验证码提示窗
         */
        public static final int VERIFICATION_CODE = R.layout.dialog9;
        /**
         * 加载动画提示窗
         */
        public static final int LOADING = R.layout.dialog10;
        /**
         * 单选提示窗
         */
        public static final int RADIO = R.layout.dialog11;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout);
        setCanceledOnTouchOutside(canceled);
        setCancelable(cancelable);
        Window window = this.getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(bdr);
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = width;
            params.height = height;
            if (gravity != -1) params.gravity = gravity;
            window.setWindowAnimations(animations);
        }

        View layout = findViewById(R.id.dialog_frame);
        if (layout != null) {
            if (windowColor != -1) layout.setBackgroundColor(windowColor);
            if (windowDrawable != -1) {
                layout.setBackground(getContext().getResources().getDrawable(windowDrawable, getContext().getTheme()));
            }
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) layout.getLayoutParams();
            lp.setMargins(left, top, right, bottom);
            layout.setLayoutParams(lp);
        }

        AppCompatAutoCompleteTextView paramView = findViewById(paramId);
        AppCompatAutoCompleteTextView nameView = findViewById(nameId);
        AppCompatAutoCompleteTextView codeView = findViewById(codeId);
        AppCompatAutoCompleteTextView addressView = findViewById(addressId);
        if (paramView != null) {
            paramView.setHint(hintText1);
            paramView.setHintTextColor(getContext().getResources().getColor(R.color.hint, getContext().getTheme()));
        }
        if (nameView != null) {
            nameView.setHint(hintText2);
            nameView.setHintTextColor(getContext().getResources().getColor(R.color.hint, getContext().getTheme()));
        }
        if (codeView != null) {
            codeView.setHint(hintText3);
            codeView.setHintTextColor(getContext().getResources().getColor(R.color.hint, getContext().getTheme()));
        }
        if (addressView != null) {
            addressView.setHint(hintText4);
            addressView.setHintTextColor(getContext().getResources().getColor(R.color.hint, getContext().getTheme()));
        }

        try {
            titleView();
            contentView();
            quitView();
            affirmTestView();
            timingView();
            cameraView();
            qrView();
            loading();
        } catch (Exception e) {
            Log.e("dialogTools异常", String.valueOf(e.getMessage()), e);
        }

    }

    /**
     * 标题布局
     */
    private void titleView() {
        AppCompatTextView titleView = findViewById(titleId);
        if (titleView != null) {
            if (titleText != null) titleView.setText(titleText);
            if (titleColor != -1) titleView.setTextColor(titleColor);
            if (titleSize != -1) titleView.setTextSize(titleSize);
            if (titleTextStyle != -1) titleView.setTypeface(Typeface.SANS_SERIF, titleTextStyle);
            else titleView.setTypeface(Typeface.DEFAULT);
            if (titleColorId != -1) {
                titleView.setTextColor(getContext().getResources().getColor(titleColorId, getContext().getTheme()));
            }
        }
    }

    /**
     * 内容布局
     */
    private void contentView() {
        AppCompatTextView contentView = findViewById(contentId);
        if (contentView != null) {
            if (contentText != null) contentView.setText(contentText);
            if (contentSize != -1) contentView.setTextSize(contentSize);
            if (contentTextStyle != -1)
                contentView.setTypeface(Typeface.SANS_SERIF, contentTextStyle);
            else contentView.setTypeface(Typeface.DEFAULT);
            if (contentColorId != -1) {
                contentView.setTextColor(getContext().getResources().getColor(contentColorId, getContext().getTheme()));
            } else contentView.setTextColor(contentColor);
//            if (width != -1) contentView.setWidth(width);
//            if (height != -1) contentView.setHeight(height);
        }
    }

    /**
     * 倒计时布局
     */
    private void timingView() {
        if (layout == LayoutResId.COUNT_DOWN) {
            ZLoadingTextView countDownView = findViewById(R.id.dialog_timing_animation);
            CountDownTimer countDownTimer = new CountDownTimer(totalTime, 1000) {
                @Override
                public void onTick(long m) {
                    if (countDownView != null) {
                        countDownView.setText(cdPrefix + (m / 1000) + "秒" + cdSuffix);
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
     * 相机相册布局
     */
    private void cameraView() {
        if (layout == LayoutResId.CAMERA) {
            RecyclerView rv = findViewById(R.id.rv_content);
            CameraAdapter adapter = new CameraAdapter();
            if (rv != null) {
                rv.setLayoutManager(new LinearLayoutManager(getContext()));
                rv.setAdapter(adapter);
            }
            adapter.setData(datas);
            adapter.setTextColor(contentColor);
            adapter.setTextSize(contentSize);
            adapter.setListener(adapterListener);
        }
    }

    /**
     * 确认按钮布局
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    private void affirmTestView() {
        AppCompatTextView affirmView = findViewById(affirmId);
        AppCompatAutoCompleteTextView paramView = findViewById(paramId);
        AppCompatAutoCompleteTextView nameView = findViewById(nameId);
        AppCompatAutoCompleteTextView codeView = findViewById(codeId);
        AppCompatAutoCompleteTextView addressView = findViewById(addressId);
        verificationView = findViewById(verificationCodeId);
        if (affirmView != null) {
            if (affirmText != null) affirmView.setText(affirmText);
            if (affirmSize != -1) affirmView.setTextSize(affirmSize);
            if (backDrawableAffirm != -1) {
                affirmView.setBackgroundDrawable(getContext().getResources().getDrawable(backDrawableAffirm, getContext().getTheme()));
            }
            if (affirmTextStyle != -1) affirmView.setTypeface(Typeface.SANS_SERIF, affirmTextStyle);
            else affirmView.setTypeface(Typeface.DEFAULT);
            if (affirmColorId != -1) {
                affirmView.setTextColor(getContext().getResources().getColor(affirmColorId, getContext().getTheme()));
            } else affirmView.setTextColor(affirmColor);
            if (listener != null) affirmView.setOnClickListener(v -> listener.ok(this));

            if (qrListener != null) {
                affirmView.setOnClickListener(v -> {
                    if (paramView != null && nameView != null && codeView != null && addressView != null) {
                        String var1 = paramView.getText().toString().trim();
                        String var2 = nameView.getText().toString().trim();
                        String var3 = addressView.getText().toString().trim();
                        String var4 = codeView.getText().toString().trim();
                        qrListener.callbackValue(this, var1, var2, var3, var4);
                        paramView.setText("");
                    } else {
                        qrListener.callbackValue(this, "", "", "", "");
                    }
                });
            }

            if (codeListener != null) {
                if (verificationView != null && paramView != null) {
                    captcha.into(verificationView);
                    code = captcha.getCode();
                    verificationView.setOnClickListener(v -> {
                        captcha.into(verificationView);
                        code = captcha.getCode();
                    });
                    affirmView.setOnClickListener(v -> {
                        String param = paramView.getText().toString().trim();
                        codeListener.ok(this, param, code);
                        captcha.into(verificationView);
                        code = captcha.getCode();
                        paramView.setText("");
                    });
                }
            }

            if (paramListener != null && paramView != null) {
                affirmView.setOnClickListener(v -> {
                    String param = paramView.getText().toString().trim();
                    paramListener.ok(this, param);
                    paramView.setText("");
                });
            }

            if (layout == LayoutResId.RADIO) {
                RadioGroup groupView = findViewById(R.id.dialog_group);
                AppCompatRadioButton writeView = findViewById(R.id.dialog_write);
                AppCompatRadioButton readView = findViewById(R.id.dialog_read);
                if (groupView != null) {
                    groupView.setOnCheckedChangeListener((group, checkedId) -> {
                        if (writeView != null && checkedId == writeView.getId()) {
                            if (writeView.isChecked()) {
                                i = 1;
                            } else {
                                i = 0;
                            }
                        } else if (readView != null && checkedId == readView.getId()) {
                            if (readView.isChecked()) {
                                i = 0;
                            } else {
                                i = 1;
                            }
                        }
                    });
                }
                if (radioListener != null && paramView != null) {
                    affirmView.setOnClickListener(v -> {
                        String param = paramView.getText().toString().trim();
                        radioListener.ok(this, param, String.valueOf(i));
                        paramView.setText("");
                    });
                }
            }
        }
    }

    /**
     * 取消按钮布局
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    private void quitView() {
        AppCompatTextView quitView = findViewById(quitId);
        AppCompatAutoCompleteTextView paramView = findViewById(paramId);
        if (quitView != null) {
            if (quitText != null) quitView.setText(quitText);
            if (quitSize != -1) quitView.setTextSize(quitSize);
            if (quitTextStyle != -1) quitView.setTypeface(Typeface.SANS_SERIF, quitTextStyle);
            else quitView.setTypeface(Typeface.DEFAULT);
            if (quitColorId != -1) {
                quitView.setTextColor(getContext().getResources().getColor(quitColorId, getContext().getTheme()));
            } else quitView.setTextColor(quitColor);
            if (backDrawableQuit != -1) {
                quitView.setBackgroundDrawable(getContext().getResources().getDrawable(backDrawableQuit, getContext().getTheme()));
            }
            if (listener != null) quitView.setOnClickListener(v -> listener.no(this));
            if (qrListener != null) quitView.setOnClickListener(v -> qrListener.no(this));
            if (paramListener != null) quitView.setOnClickListener(v -> paramListener.no(this));
            if (radioListener != null) quitView.setOnClickListener(v -> radioListener.no(this));
            if (codeListener != null) {
                quitView.setOnClickListener(v -> {
                    if (verificationView != null) {
                        captcha.into(verificationView);
                        code = captcha.getCode();
                    }
                    codeListener.no(this);
                    if (paramView != null) paramView.setText("");
                });
            }
        }
    }

    /**
     * 二维码扫描
     */
    private void qrView() {
        if (layout == LayoutResId.INPUT_CHECK) {
            AppCompatAutoCompleteTextView paramView = findViewById(paramId);
            AppCompatImageView qrView = findViewById(R.id.dialog_qr);
            AppCompatTextView getCodeView = findViewById(R.id.dialog_get_code);
            assert paramView != null;
            if (qrView != null) qrView.setOnClickListener(v -> qrListener.qr(paramView));
            if (getCodeView != null) getCodeView.setOnClickListener(v -> qrListener.code());
        }
    }

    /**
     * 加载动画
     */
    private void loading() {
        ZLoadingView loadingView = findViewById(R.id.dialog_animation);
        ZLoadingTextView loadingTextView = findViewById(R.id.dialog_timing_animation);
        if (loadingView != null) {
            loadingView.setLoadingBuilder(loadingType, loadingTime);
            loadingView.setColorFilter(getContext().getResources().getColor(loadingColor, getContext().getTheme()));
        }
        if (loadingTextView != null) {
            loadingTextView.setText(loadingText);
            loadingTextView.setColorFilter(getContext().getResources().getColor(loadingTextColor, getContext().getTheme()));
        }
    }

    private DialogTools(Context context, @NotNull Builder builder) {
        super(context, builder.style);
        this.layout = builder.layout;
        this.canceled = builder.canceled;
        this.cancelable = builder.cancelable;
        this.bdr = builder.bdr;
        this.left = builder.left;
        this.right = builder.right;
        this.top = builder.top;
        this.bottom = builder.bottom;
        this.width = builder.width;
        this.height = builder.height;
        this.gravity = builder.gravity;
        this.windowColor = builder.windowColor;
        this.windowDrawable = builder.windowDrawable;
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
        this.paramListener = builder.paramListener;
        this.codeListener = builder.codeListener;
        this.qrListener = builder.qrListener;
        this.radioListener = builder.radioListener;
        this.totalTime = builder.totalTime;
        this.cdPrefix = builder.cdPrefix;
        this.cdSuffix = builder.cdSuffix;
        this.datas = builder.datas;
        this.adapterListener = builder.adapterListener;
        this.loadingType = builder.loadingType;
        this.loadingTime = builder.loadingTime;
        this.loadingColor = builder.loadingColor;
        this.loadingText = builder.loadingText;
        this.loadingTextColor = builder.loadingTextColor;
        this.hintText1 = builder.hintText1;
        this.hintText2 = builder.hintText2;
        this.hintText3 = builder.hintText3;
        this.hintText4 = builder.hintText4;
        this.titleId = builder.titleId;
        this.contentId = builder.contentId;
        this.affirmId = builder.affirmId;
        this.quitId = builder.quitId;
        this.verificationCodeId = builder.verificationCodeId;
        this.paramId = builder.paramId;
        this.nameId = builder.nameId;
        this.codeId = builder.codeId;
        this.addressId = builder.addressId;
    }

    @NotNull
    public static Builder builder(Context context) {
        return new Builder(context);
    }

    public static final class Builder {

        private final Builder newBuilder = this;
        private final Context context;
        private int layout;
        private int style = R.style.dialogStyle;
        private int animations = R.style.animation;
        private int left = 0;
        private int right = 0;
        private int top = 0;
        private int bottom = 0;
        private int width = WindowManager.LayoutParams.MATCH_PARENT;
        private int height = WindowManager.LayoutParams.WRAP_CONTENT;
        private int gravity = -1;
        private int windowColor = -1;
        private int windowDrawable = -1;
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
        private OnClickParamListener paramListener;
        private OnClickCodeListener codeListener;
        private OnClickQrListener qrListener;
        private OnClickRadioListener radioListener;
        private String[] datas;
        private CameraAdapter.OnClickCameraAdapterListener adapterListener;
        private Z_TYPE loadingType = CIRCLE;
        private int loadingTime = 2;
        private int loadingColor = R.color.code;
        private String loadingText = "正在加载...";
        private int loadingTextColor = R.color.code;
        private String hintText1;
        private String hintText2;
        private String hintText3;
        private String hintText4;
        private int titleId = R.id.dialog_title;
        private int contentId = R.id.dialog_content;
        private int affirmId = R.id.dialog_affirm;
        private int quitId = R.id.dialog_quit;
        private int verificationCodeId = R.id.dialog_verification;
        private int paramId = R.id.dialog_param;
        private int nameId = R.id.dialog_name;
        private int codeId = R.id.dialog_code;
        private int addressId = R.id.dialog_address;

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
         * 设置布局间距
         *
         * @param values 维度值
         */
        public Builder setMargin(@NotNull int... values) {
            switch (values.length) {
                case 1:
                    this.left = values[0];
                    break;
                case 2:
                    this.left = values[0];
                    this.right = values[1];
                    break;
                case 3:
                    this.left = values[0];
                    this.right = values[1];
                    this.top = values[2];
                    break;
                case 4:
                    this.left = values[0];
                    this.right = values[1];
                    this.top = values[2];
                    this.bottom = values[3];
                    break;
            }
            return newBuilder;
        }

        /**
         * 设置布局填充
         *
         * @param values 维度值
         */
        public Builder setDimension(@NotNull int... values) {
            switch (values.length) {
                case 1:
                    this.width = values[0];
                    break;
                case 2:
                    this.width = values[0];
                    this.height = values[1];
                    break;
            }
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
                this.affirmColor = Color.parseColor("#" + affirmColor);
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
                this.quitColor = Color.parseColor("#" + quitColor);
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
        public Builder setListener(OnEventTriggerListener listener) {
            this.listener = listener;
            return newBuilder;
        }

        public Builder setListener(OnClickParamListener paramListener) {
            this.paramListener = paramListener;
            return newBuilder;
        }

        /**
         * 确认按钮或取消按钮触发事件的实现
         *
         * @param listener 事件
         */
        public Builder setListener(OnClickQrListener listener) {
            this.qrListener = listener;
            return newBuilder;
        }

        /**
         * 确认按钮或取消按钮触发事件的实现
         *
         * @param listener 事件
         */
        public Builder setListener(OnClickRadioListener listener) {
            this.radioListener = listener;
            return newBuilder;
        }

        /**
         * 确认按钮或取消按钮触发事件的实现
         *
         * @param codeListener 事件
         */
        public Builder setListener(OnClickCodeListener codeListener) {
            this.codeListener = codeListener;
            return newBuilder;
        }

        /**
         * 倒计时（引入相应布局文件totalTime>0开启）
         *
         * @param totalTime 总时长
         * @statement layout等于COUNT_DOWN生效
         */
        public Builder setTotalTime(int totalTime) {
            this.totalTime = totalTime;
            return newBuilder;
        }

        /**
         * 倒计时显示前缀
         *
         * @param cdPrefix 前缀
         * @statement layout等于COUNT_DOWN生效
         */
        public Builder setCdPrefix(String cdPrefix) {
            this.cdPrefix = cdPrefix;
            return newBuilder;
        }

        /**
         * 倒计时显示后缀
         *
         * @param cdSuffix 后缀
         * @statement layout等于COUNT_DOWN生效
         */
        public Builder setCdSuffix(String cdSuffix) {
            this.cdSuffix = cdSuffix;
            return newBuilder;
        }

        /**
         * 添加底部弹窗按钮
         *
         * @param datas 按钮文字数据
         * @statement layout等于CAMERA生效
         */
        public Builder setDatas(String... datas) {
            this.datas = datas;
            return newBuilder;
        }

        /**
         * 添加底部弹窗按钮点击事件
         *
         * @param adapterListener 事件
         * @statement layout等于CAMERA生效
         */
        public Builder setAdapterListener(CameraAdapter.OnClickCameraAdapterListener adapterListener) {
            this.adapterListener = adapterListener;
            return newBuilder;
        }

        /**
         * 设置窗口颜色
         *
         * @param windowColor 窗口颜色值
         */
        public Builder setWindowColor(@ColorRes int windowColor) {
            this.windowColor = windowColor;
            return newBuilder;
        }

        /**
         * 设置加载动画类型
         *
         * @param loadingType 动画类型
         */
        public Builder setLoadingType(Z_TYPE loadingType) {
            this.loadingType = loadingType;
            return newBuilder;
        }

        /**
         * 设置加载动画时间
         *
         * @param loadingTime 动画时间
         */
        public Builder setLoadingTime(@Size int loadingTime) {
            this.loadingTime = loadingTime;
            return newBuilder;
        }

        /**
         * 设置加载动画颜色
         *
         * @param loadingColor 动画颜色
         */
        public Builder setLoadingColor(@ColorRes int loadingColor) {
            this.loadingColor = loadingColor;
            return newBuilder;
        }

        /**
         * 设置动画文字
         *
         * @param loadingText 动画文字
         */
        public Builder setLoadingText(@NotNull String loadingText) {
            this.loadingText = loadingText;
            return newBuilder;
        }

        /**
         * 设置加载文字颜色
         *
         * @param loadingTextColor 加载文字颜色
         */
        public Builder setLoadingTextColor(@ColorRes int loadingTextColor) {
            this.loadingTextColor = loadingTextColor;
            return newBuilder;
        }

        /**
         * 设置窗口可绘制的颜色
         *
         * @param windowDrawable 窗口可绘制的值
         */
        public Builder setWindowDrawable(@DrawableRes int windowDrawable) {
            this.windowDrawable = windowDrawable;
            return newBuilder;
        }

        public Builder setHintText1(String hintText1) {
            this.hintText1 = hintText1;
            return newBuilder;
        }

        public Builder setHintText2(String hintText2) {
            this.hintText2 = hintText2;
            return newBuilder;
        }

        public Builder setHintText3(String hintText3) {
            this.hintText3 = hintText3;
            return newBuilder;
        }

        public Builder setHintText4(String hintText4) {
            this.hintText4 = hintText4;
            return newBuilder;
        }

        public Builder setTitleId(@IdRes int titleId) {
            this.titleId = titleId;
            return newBuilder;
        }

        public Builder setContentId(@IdRes int contentId) {
            this.contentId = contentId;
            return newBuilder;
        }

        public Builder setAffirmId(@IdRes int affirmId) {
            this.affirmId = affirmId;
            return newBuilder;
        }

        public Builder setQuitId(@IdRes int quitId) {
            this.quitId = quitId;
            return newBuilder;
        }

        public Builder setVerificationCodeId(@IdRes int verificationCodeId) {
            this.verificationCodeId = verificationCodeId;
            return newBuilder;
        }

        public Builder setParamId(@IdRes int paramId) {
            this.paramId = paramId;
            return newBuilder;
        }

        public Builder setNameId(@IdRes int nameId) {
            this.nameId = nameId;
            return newBuilder;
        }

        public Builder setCodeId(int codeId) {
            this.codeId = codeId;
            return newBuilder;
        }

        public Builder setAddressId(int addressId) {
            this.addressId = addressId;
            return newBuilder;
        }

        @NotNull
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


        default void no(DialogTools dialog) {
            dialog.cancel();
        }
    }

    public interface OnClickQrListener {
        default void qr(AppCompatAutoCompleteTextView paramView) {
            paramView.setText("");
        }

        default void code() {
        }

        /**
         * @param var1 编码
         * @param var2 名称
         * @param var3 验证码
         * @param var4 详细地址
         */
        void callbackValue(DialogTools dialog, String var1, String var2, String var3, String var4);

        default void no(DialogTools dialog) {
            dialog.cancel();
        }
    }

    public interface OnClickCodeListener {

        void ok(DialogTools dialog, String param, String code);

        default void no(DialogTools dialog) {
            dialog.cancel();
        }

    }

    public interface OnClickParamListener {
        void ok(DialogTools dialog, String param);

        default void no(DialogTools dialog) {
            dialog.cancel();
        }
    }

    public interface OnClickRadioListener {
        void ok(DialogTools dialog, String param, String radio);

        default void no(DialogTools dialog) {
            dialog.cancel();
        }
    }

}
