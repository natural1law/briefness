package com.androidx.view.dialog;

import static android.view.Gravity.CENTER;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static com.androidx.view.dialog.DialogCall.Builder.module;

import android.content.Context;
import android.os.Parcel;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;

public final class DialogCall {

    private final DialogModule dm;

    private DialogCall(DialogModule param) {
        this.dm = param;
    }

    public static Builder builder() {
        return Singleton.builder();
    }

    private static final class Singleton {

        private Singleton() {
        }

        private static volatile DialogCall dc;
        private static volatile Builder builder;

        private static final DialogCall INSTANCE = new DialogCall(module);
        private static final Builder BUILDER = new Builder();

        private static DialogCall dialogCall() {
            if (dc == null) synchronized (DialogCall.class) {
                if (dc == null) dc = INSTANCE;
            }
            return dc;
        }

        private static Builder builder() {
            if (builder == null) synchronized (DialogCall.class) {
                if (builder == null) builder = BUILDER;
            }
            return builder;
        }

    }

    public DialogServlet get(Context context) {
        return new DialogServlet(context, dm);
    }

    public static final class Builder {

        protected static final DialogModule module = new DialogModule(Parcel.obtain());

        private Builder() {
        }

        private boolean canceled = true;
        private boolean cancelable = true;
        private int layoutView;
        private int layoutViewId;
        private int layoutViewBackgroundColor;
        private int layoutViewBackgroundDrawable;
        private int layoutViewMarginsStart = 0;
        private int layoutViewMarginsEnd = 0;
        private int layoutViewMarginsTop = 0;
        private int layoutViewMarginsBottom = 0;
        private int layoutWidth = MATCH_PARENT;
        private int layoutHeight = WRAP_CONTENT;
        private int layoutGravity = CENTER;

        public Builder setCanceled(boolean canceled) {
            this.canceled = canceled;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public Builder setLayoutView(@LayoutRes int layout) {
            this.layoutView = layout;
            return this;
        }

        public Builder setLayoutViewId(@IdRes int id) {
            this.layoutViewId = id;
            return this;
        }

        public Builder setLayoutWidth(int width) {
            this.layoutWidth = width;
            return this;
        }

        public Builder setLayoutHeight(int height) {
            this.layoutHeight = height;
            return this;
        }

        public Builder setLayoutGravity(int gravity) {
            this.layoutGravity = gravity;
            return this;
        }

        public Builder setLayoutViewBackgroundColor(@ColorRes int color) {
            this.layoutViewBackgroundColor = color;
            return this;
        }

        public Builder setLayoutViewBackgroundDrawable(@DrawableRes int drawable) {
            this.layoutViewBackgroundDrawable = drawable;
            return this;
        }

        public Builder setLayoutViewMarginsStart(int start) {
            this.layoutViewMarginsStart = start;
            return this;
        }

        public Builder setLayoutViewMarginsEnd(int end) {
            this.layoutViewMarginsEnd = end;
            return this;
        }

        public Builder setLayoutViewMarginsTop(int top) {
            this.layoutViewMarginsTop = top;
            return this;
        }

        public Builder setLayoutViewMarginsBottom(int bottom) {
            this.layoutViewMarginsBottom = bottom;
            return this;
        }

        public DialogCall build() {
            module.setCanceled(canceled);
            module.setCancelable(cancelable);
            module.setLayoutView(layoutView);
            module.setLayoutViewId(layoutViewId);
            module.setLayoutWidth(layoutWidth);
            module.setLayoutHeight(layoutHeight);
            module.setLayoutGravity(layoutGravity);
            module.setLayoutViewBackgroundColor(layoutViewBackgroundColor);
            module.setLayoutViewBackgroundDrawable(layoutViewBackgroundDrawable);
            module.setLayoutViewMarginsStart(layoutViewMarginsStart);
            module.setLayoutViewMarginsEnd(layoutViewMarginsEnd);
            module.setLayoutViewMarginsTop(layoutViewMarginsTop);
            module.setLayoutViewMarginsBottom(layoutViewMarginsBottom);
            return Singleton.dialogCall();
        }

    }

}
