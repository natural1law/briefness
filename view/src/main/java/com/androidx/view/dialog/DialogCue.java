package com.androidx.view.dialog;

import android.content.Context;
import android.graphics.Color;
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
public final class DialogCue extends AppCompatDialog {

    private final String contentText;
    private final String affirmText;
    private final String titleText;
    private final int contentColor;
    private final int affirmColor;
    private final int titleColor;
    private final int contentColor1;
    private final int affirmColor1;
    private final int titleColor1;
    private final int contentTextSize;
    private final int affirmTextSize;
    private final int titleTextSize;
    private final int width;
    private final int height;
    private final int gravity;
    private final int animations;
    private final boolean cancel;
    private final boolean flag;
    private final AffirmListener affirmListener;

    private DialogCue(Context context, Builder builder) {
        super(context, R.style.dialogStyle);
        this.contentText = builder.contentText;
        this.affirmText = builder.affirmText;
        this.titleText = builder.titleText;
        this.contentColor = builder.contentColor;
        this.affirmColor = builder.affirmColor;
        this.titleColor = builder.titleColor;
        this.contentColor1 = builder.contentColor1;
        this.affirmColor1 = builder.affirmColor1;
        this.titleColor1 = builder.titleColor1;
        this.contentTextSize = builder.contentTextSize;
        this.affirmTextSize = builder.affirmTextSize;
        this.titleTextSize = builder.titleTextSize;
        this.width = builder.width;
        this.height = builder.height;
        this.gravity = builder.gravity;
        this.affirmListener = builder.affirmListener;
        this.animations = builder.animations;
        this.cancel = builder.cancel;
        this.flag = builder.flag;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.dialog1);
            AppCompatDialog dialog = this;
            Context context = getContext();
            AppCompatTextView contentView = findViewById(R.id.dialog_content);
            AppCompatTextView affirmView = findViewById(R.id.dialog_affirm);
            AppCompatTextView titleView = findViewById(R.id.dialog_title);
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
                    contentView.setTextColor(context.getResources().getColor(contentColor1, context.getTheme()));
                }
            }
            if (affirmView != null) {
                affirmView.setText(affirmText);
                affirmView.setTextColor(affirmColor);
                affirmView.setTextSize(affirmTextSize);
                if (affirmColor1 != 0) {
                    affirmView.setTextColor(context.getResources().getColor(affirmColor1, context.getTheme()));
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
            if (titleView != null) {
                titleView.setText(titleText);
                titleView.setTextColor(titleColor);
                titleView.setTextSize(titleTextSize);
                if (titleColor1 != 0) {
                    titleView.setTextColor(context.getResources().getColor(titleColor1, context.getTheme()));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void close() {
        cancel();
        dismiss();
    }

    public static final class Builder {

        private final Context context;
        private final Builder builder;
        private String contentText;
        private String affirmText = "确认";
        private String titleText;
        private int contentColor;
        private int affirmColor;
        private int titleColor;
        private int contentColor1;
        private int affirmColor1;
        private int titleColor1;
        private int contentTextSize = 16;
        private int affirmTextSize = 14;
        private int titleTextSize = 20;
        private int width = -1;
        private int height = -1;
        private int gravity = Gravity.CENTER;
        private AffirmListener affirmListener;
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

        public <T> Builder setTitleText(T msg) {
            this.titleText = String.valueOf(msg);
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

        public Builder setTitleTextColor(String id) {
            this.titleColor = Color.parseColor("#" + id);
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

        public Builder setTitleTextColor(@ColorRes int quitColor1) {
            this.titleColor1 = quitColor1;
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

        public Builder setTitleTextSize(@Size int id) {
            this.titleTextSize = id;
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

        public DialogCue build() {
            try {
                synchronized (DialogCue.class) {
                    return new DialogCue(context, builder);
                }
            } catch (Exception e) {
                return new DialogCue(context, builder);
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

}
