package com.androidx.view.dialog;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PorterDuff;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.Size;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.androidx.reduce.tools.Convert;
import com.androidx.view.R;
import com.androidx.view.dialog.listener.OnClickTriggerListener;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

public class DialogServlet extends AppCompatDialog {

    private View layout;
    private final SparseArray<View> views;

    protected DialogServlet(Context context, DialogModule module) {
        this(context, R.style.dialogStyle, module);
    }

    private DialogServlet(Context context, int theme, DialogModule module) {
        super(context, theme);
        views = new SparseArray<>();
        try {
            initView(module);
        } catch (Exception e) {
            Log.e("弹窗异常", Log.getStackTraceString(e));
        }
    }

    /**
     * 初始化视图
     */
    @SuppressLint("WrongConstant")
    private void initView(DialogModule module) {
        setCancelable(module.isCancelable());
        setCanceledOnTouchOutside(module.isCanceled());
        setContentView(module.getLayoutView());
        Window window = this.getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(android.R.color.transparent);
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = MATCH_PARENT;
            params.height = WRAP_CONTENT;
            params.gravity = module.getLayoutGravity();

        }
        if (module.getLayoutViewId() != 0) {
            layout = findViewById(module.getLayoutViewId());
        }
        if (layout != null) {
            if (module.getLayoutViewBackgroundColor() != 0) {
                layout.setBackgroundColor(module.getLayoutViewBackgroundColor());
            }
            if (module.getLayoutViewBackgroundDrawable() != 0) {
                layout.setBackgroundResource(module.getLayoutViewBackgroundDrawable());
            }
            ViewGroup.LayoutParams lp = layout.getLayoutParams();
            lp.width = module.getLayoutWidth();
            lp.height = module.getLayoutHeight();
            layout.setLayoutParams(lp);
        }
    }

    @SuppressWarnings("unchecked")
    public <V extends View> V getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = layout.findViewById(viewId);
            views.put(viewId, view);
        }
        return (V) view;
    }

    /**
     * 设置文本
     */
    public <T> DialogServlet setTextView(@IdRes int viewId, @NotNull T text) {
        AppCompatTextView view = getView(viewId);
        view.setText(String.valueOf(text));
        return this;
    }

    public DialogServlet setTextColor(@IdRes int viewId, @ColorRes int color) {
        AppCompatTextView view = getView(viewId);
        view.setTextColor(getContext().getResources().getColor(color, getContext().getTheme()));
        return this;
    }

    public DialogServlet setTextSize(@IdRes int viewId, @Size int size) {
        AppCompatTextView view = getView(viewId);
        view.setTextSize(size);
        return this;
    }

    public DialogServlet setTextWidth(@IdRes int viewId, @Size int size) {
        AppCompatTextView view = getView(viewId);
        view.setWidth(Convert.Pixel.get(getContext()).dp(size));
        return this;
    }

    public DialogServlet setTextHeight(@IdRes int viewId, @Size int size) {
        AppCompatTextView view = getView(viewId);
        view.setHeight(Convert.Pixel.get(getContext()).dp(size));
        return this;
    }

    /**
     * 设置图片
     */
    public <T> DialogServlet setImageView(@IdRes int viewId, @NotNull T resId) {
        AppCompatImageView view = getView(viewId);
        view.setScaleType(ImageView.ScaleType.FIT_CENTER);
        Glide.with(getContext()).load(resId).into(view);
        return this;
    }

    public DialogServlet setImageColor(@IdRes int viewId, @ColorRes int color) {
        AppCompatImageView view = getView(viewId);
        view.setScaleType(ImageView.ScaleType.FIT_CENTER);
        view.setColorFilter(getContext().getResources().getColor(color, getContext().getTheme()));
        return this;
    }

    public DialogServlet setImageColorMode(@IdRes int viewId, @ColorRes int color, PorterDuff.Mode mode) {
        AppCompatImageView view = getView(viewId);
        view.setScaleType(ImageView.ScaleType.FIT_CENTER);
        view.setColorFilter(getContext().getResources().getColor(color, getContext().getTheme()), mode);
        return this;
    }

    /**
     * 设置输入文本
     */
    public String getEditText(@IdRes int viewId) {
        AppCompatAutoCompleteTextView view = getView(viewId);
        return String.valueOf(view.getText()).trim();
    }

    public DialogServlet setEditColor(@IdRes int viewId, @ColorRes int color) {
        AppCompatAutoCompleteTextView view = getView(viewId);
        view.setTextColor(getContext().getResources().getColor(color, getContext().getTheme()));
        return this;
    }

    public DialogServlet setEditSize(@IdRes int viewId, @Size int size) {
        AppCompatAutoCompleteTextView view = getView(viewId);
        view.setTextSize(size);
        return this;
    }

    public DialogServlet setEditHint(@IdRes int viewId, @NotNull String text) {
        AppCompatAutoCompleteTextView view = getView(viewId);
        view.setHint(text);
        return this;
    }

    public DialogServlet setEditHintSize(@IdRes int viewId, @Size int size) {
        AppCompatAutoCompleteTextView view = getView(viewId);
        view.setTextSize(size);
        return this;
    }

    public DialogServlet setEditHintColor(@IdRes int viewId, @ColorRes int color) {
        AppCompatAutoCompleteTextView view = getView(viewId);
        view.setHintTextColor(getContext().getResources().getColor(color, getContext().getTheme()));
        return this;
    }

    public DialogServlet setEditWidth(@IdRes int viewId, @Size int size) {
        AppCompatAutoCompleteTextView view = getView(viewId);
        view.setWidth(Convert.Pixel.get(getContext()).dp(size));
        return this;
    }

    public DialogServlet setEditHeight(@IdRes int viewId, @Size int size) {
        AppCompatAutoCompleteTextView view = getView(viewId);
        view.setHeight(Convert.Pixel.get(getContext()).dp(size));
        return this;
    }

    /**
     * 图片按钮
     */
    public <T> DialogServlet setImageButton(@IdRes int viewId, @NotNull T resId) {
        AppCompatImageButton view = getView(viewId);
        view.setScaleType(ImageView.ScaleType.FIT_CENTER);
        Glide.with(getContext()).load(resId).into(view);
        return this;
    }

    public DialogServlet setImageButtonColor(@IdRes int viewId, @ColorRes int color) {
        AppCompatImageButton view = getView(viewId);
        view.setColorFilter(getContext().getResources().getColor(color, getContext().getTheme()));
        return this;
    }

    public DialogServlet setBackgroundColor(@IdRes int viewId, @ColorRes int color) {
        View view = getView(viewId);
        view.setBackgroundColor(getContext().getResources().getColor(color, getContext().getTheme()));
        return this;
    }

    public DialogServlet setBackgroundResource(@IdRes int viewId, @DrawableRes int drawable) {
        View view = getView(viewId);
        view.setBackgroundResource(drawable);
        return this;
    }

    public DialogServlet setVisible(@IdRes int viewId, boolean visible) {
        ViewGroup view = getView(viewId);
        if (visible) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
        return this;
    }

    public DialogServlet setOnClickListener(@IdRes int viewId, OnClickTriggerListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(view1 -> listener.ok(this));
        return this;
    }

    @SuppressLint("ClickableViewAccessibility")
    public DialogServlet setOnTouchListener(@IdRes int viewId, OnClickTriggerListener listener) {
        View view = getView(viewId);
        view.setOnTouchListener((view1, motionEvent) -> {
            listener.ok(this);
            return false;
        });
        return this;
    }

    public DialogServlet setOnLongClickListener(@IdRes int viewId, OnClickTriggerListener listener) {
        View view = getView(viewId);
        view.setOnLongClickListener(view1 -> {
            listener.ok(this);
            return false;
        });
        return this;
    }

}
