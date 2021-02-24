package com.androidx.view.dialog;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.ColorRes;
import androidx.annotation.Size;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.widget.AppCompatTextView;

import com.androidx.view.R;

/**
 * 信息提示窗
 *
 * @author 李玄道
 * @date 2020/06/29
 */
@SuppressWarnings({"unused", "RedundantSuppression"})
public final class DialogExpress extends AppCompatDialog {

    private final String contentText;
    private final String affirmText;
    private final String quitText;
    private final int contentColor;
    private final int affirmColor;
    private final int quitColor;
    private final int contentColor1;
    private final int affirmColor1;
    private final int quitColor1;
    private final int contentTextSize;
    private final int affirmTextSize;
    private final int quitTextSize;
    private final int width;
    private final int height;
    private final int gravity;
    private final int animations;
    private final boolean cancel;
    private final boolean flag;
    private final AffirmListener affirmListener;
    private final QuitListener quitListener;

    private DialogExpress(Context context, Builder builder) {
        super(context, R.style.dialogStyle);
        this.contentText = builder.contentText;
        this.affirmText = builder.affirmText;
        this.quitText = builder.quitText;
        this.contentColor = builder.contentColor;
        this.affirmColor = builder.affirmColor;
        this.quitColor = builder.quitColor;
        this.contentColor1 = builder.contentColor1;
        this.affirmColor1 = builder.affirmColor1;
        this.quitColor1 = builder.quitColor1;
        this.contentTextSize = builder.contentTextSize;
        this.affirmTextSize = builder.affirmTextSize;
        this.quitTextSize = builder.quitTextSize;
        this.width = builder.width;
        this.height = builder.height;
        this.gravity = builder.gravity;
        this.affirmListener = builder.affirmListener;
        this.quitListener = builder.quitListener;
        this.animations = builder.animations;
        this.cancel = builder.cancel;
        this.flag = builder.flag;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.dialog_express);
            AppCompatDialog dialog = this;
            Context context = getContext();
            AppCompatTextView contentView = findViewById(R.id.dialog_content);
            AppCompatTextView affirmView = findViewById(R.id.dialog_affirm);
            AppCompatTextView quitView = findViewById(R.id.dialog_quit);
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
                if (contentColor1 != 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        contentView.setTextColor(context.getResources().getColor(contentColor1, context.getTheme()));
                    } else {
                        //noinspection deprecation
                        contentView.setTextColor(context.getResources().getColor(contentColor1));
                    }
                }
            }
            if (affirmView != null) {
                affirmView.setText(affirmText);
                affirmView.setTextColor(affirmColor);
                affirmView.setTextSize(affirmTextSize);
                if (affirmColor1 != 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        affirmView.setTextColor(context.getResources().getColor(affirmColor1, context.getTheme()));
                    } else {
                        //noinspection deprecation
                        affirmView.setTextColor(context.getResources().getColor(affirmColor1));
                    }
                }
                if (affirmListener != null) {
                    affirmView.setOnClickListener(v -> affirmListener.ok(dialog));
                } else {
                    affirmView.setOnClickListener(v -> {
                        dialog.hide();
                        dialog.cancel();
                        dialog.dismiss();
                    });
                }
            }
            if (quitView != null) {
                quitView.setText(quitText);
                quitView.setTextColor(quitColor);
                quitView.setTextSize(quitTextSize);
                if (quitColor1 != 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        quitView.setTextColor(context.getResources().getColor(quitColor1, context.getTheme()));
                    } else {
                        //noinspection deprecation
                        quitView.setTextColor(context.getResources().getColor(quitColor1));
                    }
                }
                if (quitListener != null) {
                    quitView.setOnClickListener(v -> quitListener.no(dialog));
                } else {
                    quitView.setOnClickListener(view -> {
                        dialog.hide();
                        dialog.cancel();
                        dialog.dismiss();
                    });
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static final class Builder {

        private final Context context;
        private final Builder builder;
        private String contentText;
        private String affirmText = "确认";
        private String quitText = "取消";
        private int contentColor = Color.parseColor("#282222");
        private int affirmColor = Color.parseColor("#2B2828");
        private int quitColor = Color.parseColor("#2B2828");
        private int contentColor1;
        private int affirmColor1;
        private int quitColor1;
        private int contentTextSize = 16;
        private int affirmTextSize = 14;
        private int quitTextSize = 14;
        private int width = 720;
        private int height = 450;
        private int gravity = Gravity.CENTER;
        private AffirmListener affirmListener;
        private QuitListener quitListener;
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

        public <T> Builder setAffirmText(T msg) {
            this.affirmText = String.valueOf(msg);
            return builder;
        }

        public <T> Builder setQuitText(T msg) {
            this.quitText = String.valueOf(msg);
            return builder;
        }

        public Builder setContentTextColor(String id) {
            this.contentColor = Color.parseColor("#" + id);
            return builder;
        }

        public Builder setAffirmTextColor(String id) {
            this.affirmColor = Color.parseColor("#" + id);
            return builder;
        }

        public Builder setQuitTextColor(String id) {
            this.quitColor = Color.parseColor("#" + id);
            return builder;
        }

        public Builder setContentTextColor(@ColorRes int contentColor1) {
            this.contentColor1 = contentColor1;
            return builder;
        }

        public Builder setAffirmTextColor(@ColorRes int affirmColor1) {
            this.affirmColor1 = affirmColor1;
            return builder;
        }

        public Builder setQuitTextColor(@ColorRes int quitColor1) {
            this.quitColor1 = quitColor1;
            return builder;
        }

        public Builder setContentTextSize(@Size int id) {
            this.contentTextSize = id;
            return builder;
        }

        public Builder setAffirmTextSize(@Size int id) {
            this.affirmTextSize = id;
            return builder;
        }

        public Builder setQuitTextSize(@Size int id) {
            this.quitTextSize = id;
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

        public Builder setAffirmListener(AffirmListener affirmListener) {
            this.affirmListener = affirmListener;
            return builder;
        }

        public Builder setQuitListener(QuitListener quitListener) {
            this.quitListener = quitListener;
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

        public Builder setAnimations(@StyleRes int id) {
            this.animations = id;
            return builder;
        }

        public DialogExpress build() {
            try {
                DialogExpress instance;
                synchronized (DialogExpress.class) {
                    instance = new DialogExpress(context, builder);
                }
                return instance;
            } catch (Exception e) {
                return new DialogExpress(context, builder);
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

    public interface AffirmListener {
        void ok(AppCompatDialog dialog);
    }

    public interface QuitListener {
        void no(AppCompatDialog dialog);
    }

}
