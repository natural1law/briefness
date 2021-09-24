package com.androidx.view.dialog;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.IdRes;
import androidx.annotation.Size;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckedTextView;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatMultiAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.androidx.reduce.tools.Convert;
import com.androidx.view.R;
import com.androidx.view.dialog.listener.OnClickTriggerListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class DialogServlet extends AppCompatDialog {

    private View layout;
    private final Resources r;
    private final RequestManager g;
    private final Resources.Theme t;
    private final Convert.Pixel pixel;
    private final SparseArray<View> views;

    protected DialogServlet(Context context, DialogModule module) {
        this(context, R.style.dialogStyle, module);
    }

    private DialogServlet(Context context, int theme, DialogModule module) {
        super(context, theme);
        pixel = Convert.Pixel.get(context);
        r = context.getResources();
        t = context.getTheme();
        g = Glide.with(context);
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

    public DialogServlet setMatrix(@IdRes int id, int... v) {
        if (getView(id) instanceof AppCompatTextView) {
            AppCompatTextView textView = getView(id);
            textView.setWidth(v[0] == 0 ? MATCH_PARENT : pixel.dp(v[0]));
            textView.setHeight(v[1] == 0 ? WRAP_CONTENT : pixel.dp(v[1]));
        } else if (getView(id) instanceof TextView) {
            TextView textView = getView(id);
            textView.setWidth(v[0] == 0 ? MATCH_PARENT : pixel.dp(v[0]));
            textView.setHeight(v[1] == 0 ? WRAP_CONTENT : pixel.dp(v[1]));
        } else if (getView(id) instanceof AppCompatImageView) {
            AppCompatImageView imageView = getView(id);
            ViewGroup.LayoutParams params = imageView.getLayoutParams();
            params.width = v[0] == 0 ? MATCH_PARENT : pixel.dp(v[0]);
            params.height = v[1] == 0 ? WRAP_CONTENT : pixel.dp(v[1]);
            imageView.setLayoutParams(params);
        } else if (getView(id) instanceof ImageView) {
            ImageView imageView = getView(id);
            ViewGroup.LayoutParams params = imageView.getLayoutParams();
            params.width = v[0] == 0 ? MATCH_PARENT : pixel.dp(v[0]);
            params.height = v[1] == 0 ? WRAP_CONTENT : pixel.dp(v[1]);
            imageView.setLayoutParams(params);
        } else if (getView(id) instanceof AppCompatEditText) {
            AppCompatEditText editText = getView(id);
            editText.setWidth(v[0] == 0 ? MATCH_PARENT : pixel.dp(v[0]));
            editText.setHeight(v[1] == 0 ? WRAP_CONTENT : pixel.dp(v[1]));
        } else if (getView(id) instanceof EditText) {
            EditText editText = getView(id);
            editText.setWidth(v[0] == 0 ? MATCH_PARENT : pixel.dp(v[0]));
            editText.setHeight(v[1] == 0 ? WRAP_CONTENT : pixel.dp(v[1]));
        } else if (getView(id) instanceof AppCompatAutoCompleteTextView) {
            AppCompatAutoCompleteTextView textView = getView(id);
            textView.setWidth(v[0] == 0 ? MATCH_PARENT : pixel.dp(v[0]));
            textView.setHeight(v[1] == 0 ? WRAP_CONTENT : pixel.dp(v[1]));
        } else if (getView(id) instanceof AppCompatMultiAutoCompleteTextView) {
            AppCompatMultiAutoCompleteTextView textView = getView(id);
            textView.setWidth(v[0] == 0 ? MATCH_PARENT : pixel.dp(v[0]));
            textView.setHeight(v[1] == 0 ? WRAP_CONTENT : pixel.dp(v[1]));
        } else if (getView(id) instanceof AppCompatImageButton) {
            AppCompatImageButton imageButton = getView(id);
            ViewGroup.LayoutParams params = imageButton.getLayoutParams();
            params.width = v[0] == 0 ? MATCH_PARENT : pixel.dp(v[0]);
            params.height = v[1] == 0 ? WRAP_CONTENT : pixel.dp(v[1]);
            imageButton.setLayoutParams(params);
        } else if (getView(id) instanceof ImageButton) {
            ImageButton imageButton = getView(id);
            ViewGroup.LayoutParams params = imageButton.getLayoutParams();
            params.width = v[0] == 0 ? MATCH_PARENT : pixel.dp(v[0]);
            params.height = v[1] == 0 ? WRAP_CONTENT : pixel.dp(v[1]);
            imageButton.setLayoutParams(params);
        } else if (getView(id) instanceof AppCompatCheckedTextView) {
            AppCompatCheckedTextView textView = getView(id);
            textView.setWidth(v[0] == 0 ? MATCH_PARENT : pixel.dp(v[0]));
            textView.setHeight(v[1] == 0 ? WRAP_CONTENT : pixel.dp(v[1]));
        } else if (getView(id) instanceof LinearLayoutCompat) {
            LinearLayoutCompat layout = getView(id);
            ViewGroup.LayoutParams params = layout.getLayoutParams();
            params.width = v[0] == 0 ? MATCH_PARENT : pixel.dp(v[0]);
            params.height = v[1] == 0 ? WRAP_CONTENT : pixel.dp(v[1]);
            layout.setLayoutParams(params);
        } else if (getView(id) instanceof LinearLayout) {
            LinearLayout layout = getView(id);
            ViewGroup.LayoutParams params = layout.getLayoutParams();
            params.width = v[0] == 0 ? MATCH_PARENT : pixel.dp(v[0]);
            params.height = v[1] == 0 ? WRAP_CONTENT : pixel.dp(v[1]);
            layout.setLayoutParams(params);
        } else if (getView(id) instanceof ConstraintLayout) {
            ConstraintLayout layout = getView(id);
            ViewGroup.LayoutParams params = layout.getLayoutParams();
            params.width = v[0] == 0 ? MATCH_PARENT : pixel.dp(v[0]);
            params.height = v[1] == 0 ? WRAP_CONTENT : pixel.dp(v[1]);
            layout.setLayoutParams(params);
        } else if (getView(id) instanceof FrameLayout) {
            FrameLayout layout = getView(id);
            ViewGroup.LayoutParams params = layout.getLayoutParams();
            params.width = v[0] == 0 ? MATCH_PARENT : pixel.dp(v[0]);
            params.height = v[1] == 0 ? WRAP_CONTENT : pixel.dp(v[1]);
            layout.setLayoutParams(params);
        } else if (getView(id) instanceof RelativeLayout) {
            RelativeLayout layout = getView(id);
            ViewGroup.LayoutParams params = layout.getLayoutParams();
            params.width = v[0] == 0 ? MATCH_PARENT : pixel.dp(v[0]);
            params.height = v[1] == 0 ? WRAP_CONTENT : pixel.dp(v[1]);
            layout.setLayoutParams(params);
        } else if (getView(id) instanceof GridView) {
            GridView view = getView(id);
            ViewGroup.LayoutParams params = view.getLayoutParams();
            params.width = v[0] == 0 ? MATCH_PARENT : pixel.dp(v[0]);
            params.height = v[1] == 0 ? WRAP_CONTENT : pixel.dp(v[1]);
            view.setLayoutParams(params);
        } else if (getView(id) instanceof RecyclerView) {
            RecyclerView recyclerView = getView(id);
            ViewGroup.LayoutParams params = recyclerView.getLayoutParams();
            params.width = v[0] == 0 ? MATCH_PARENT : pixel.dp(v[0]);
            params.height = v[1] == 0 ? WRAP_CONTENT : pixel.dp(v[1]);
            recyclerView.setLayoutParams(params);
        }
        return this;
    }

    /**
     * 设置文本
     */
    public <Text> DialogServlet setText(@IdRes int id, Text text) {
        if (getView(id) instanceof AppCompatTextView) {
            AppCompatTextView textView = getView(id);
            textView.setText(String.valueOf(text));
        } else if (getView(id) instanceof AppCompatCheckedTextView) {
            AppCompatCheckedTextView textView = getView(id);
            textView.setText(String.valueOf(text));
        } else if (getView(id) instanceof AppCompatAutoCompleteTextView) {
            AppCompatAutoCompleteTextView textView = getView(id);
            textView.setText(String.valueOf(text));
        } else if (getView(id) instanceof AppCompatEditText) {
            AppCompatEditText textView = getView(id);
            textView.setText(String.valueOf(text));
        } else if (getView(id) instanceof EditText) {
            EditText textView = getView(id);
            textView.setText(String.valueOf(text));
        } else if (getView(id) instanceof TextView) {
            TextView textView = getView(id);
            textView.setText(String.valueOf(text));
        } else if (getView(id) instanceof Button) {
            Button textView = getView(id);
            textView.setText(String.valueOf(text));
        } else if (getView(id) instanceof AppCompatButton) {
            AppCompatButton textView = getView(id);
            textView.setText(String.valueOf(text));
        } else {
            Log.w("HolderView", "未匹配到有效对象");
        }
        return this;
    }

    /**
     * 设置提示
     */
    public <Text> DialogServlet setHint(@IdRes int id, Text text) {
        if (getView(id) instanceof AppCompatTextView) {
            AppCompatTextView textView = getView(id);
            textView.setHint(String.valueOf(text));
        } else if (getView(id) instanceof AppCompatCheckedTextView) {
            AppCompatCheckedTextView textView = getView(id);
            textView.setHint(String.valueOf(text));
        } else if (getView(id) instanceof AppCompatAutoCompleteTextView) {
            AppCompatAutoCompleteTextView textView = getView(id);
            textView.setHint(String.valueOf(text));
        } else if (getView(id) instanceof AppCompatEditText) {
            AppCompatEditText textView = getView(id);
            textView.setHint(String.valueOf(text));
        } else if (getView(id) instanceof EditText) {
            EditText textView = getView(id);
            textView.setHint(String.valueOf(text));
        } else if (getView(id) instanceof TextView) {
            TextView textView = getView(id);
            textView.setHint(String.valueOf(text));
        } else if (getView(id) instanceof Button) {
            Button textView = getView(id);
            textView.setHint(String.valueOf(text));
        } else if (getView(id) instanceof AppCompatButton) {
            AppCompatButton textView = getView(id);
            textView.setHint(String.valueOf(text));
        } else {
            Log.w("HolderView", "未匹配到有效对象");
        }
        return this;
    }

    /**
     * 设置字体颜色
     */
    public DialogServlet setTextColor(@IdRes int id, @ColorRes int color) {
        if (getView(id) instanceof AppCompatTextView) {
            AppCompatTextView textView = getView(id);
            textView.setTextColor(r.getColor(color, t));
        } else if (getView(id) instanceof AppCompatCheckedTextView) {
            AppCompatCheckedTextView textView = getView(id);
            textView.setTextColor(r.getColor(color, t));
        } else if (getView(id) instanceof AppCompatAutoCompleteTextView) {
            AppCompatAutoCompleteTextView textView = getView(id);
            textView.setTextColor(r.getColor(color, t));
        } else if (getView(id) instanceof AppCompatEditText) {
            AppCompatEditText textView = getView(id);
            textView.setTextColor(r.getColor(color, t));
        } else if (getView(id) instanceof EditText) {
            EditText textView = getView(id);
            textView.setTextColor(r.getColor(color, t));
        } else if (getView(id) instanceof TextView) {
            TextView textView = getView(id);
            textView.setTextColor(r.getColor(color, t));
        } else if (getView(id) instanceof Button) {
            Button textView = getView(id);
            textView.setTextColor(r.getColor(color, t));
        } else if (getView(id) instanceof AppCompatButton) {
            AppCompatButton textView = getView(id);
            textView.setTextColor(r.getColor(color, t));
        } else {
            Log.w("HolderView", "未匹配到有效对象");
        }
        return this;
    }

    /**
     * 设置提示颜色
     */
    public DialogServlet setHintColor(@IdRes int id, @ColorRes int color) {
        if (getView(id) instanceof AppCompatTextView) {
            AppCompatTextView textView = getView(id);
            textView.setHintTextColor(r.getColor(color, t));
        } else if (getView(id) instanceof AppCompatCheckedTextView) {
            AppCompatCheckedTextView textView = getView(id);
            textView.setHintTextColor(r.getColor(color, t));
        } else if (getView(id) instanceof AppCompatAutoCompleteTextView) {
            AppCompatAutoCompleteTextView textView = getView(id);
            textView.setHintTextColor(r.getColor(color, t));
        } else if (getView(id) instanceof AppCompatEditText) {
            AppCompatEditText textView = getView(id);
            textView.setHintTextColor(r.getColor(color, t));
        } else if (getView(id) instanceof EditText) {
            EditText textView = getView(id);
            textView.setHintTextColor(r.getColor(color, t));
        } else if (getView(id) instanceof TextView) {
            TextView textView = getView(id);
            textView.setHintTextColor(r.getColor(color, t));
        } else if (getView(id) instanceof Button) {
            Button textView = getView(id);
            textView.setHintTextColor(r.getColor(color, t));
        } else if (getView(id) instanceof AppCompatButton) {
            AppCompatButton textView = getView(id);
            textView.setHintTextColor(r.getColor(color, t));
        } else {
            Log.w("HolderView", "未匹配到有效对象");
        }
        return this;
    }

    /**
     * 设置字体大小
     */
    public DialogServlet setTextSize(@IdRes int id, @Size float size) {
        if (getView(id) instanceof AppCompatTextView) {
            AppCompatTextView textView = getView(id);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        } else if (getView(id) instanceof AppCompatCheckedTextView) {
            AppCompatCheckedTextView textView = getView(id);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        } else if (getView(id) instanceof AppCompatAutoCompleteTextView) {
            AppCompatAutoCompleteTextView textView = getView(id);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        } else if (getView(id) instanceof AppCompatEditText) {
            AppCompatEditText textView = getView(id);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        } else if (getView(id) instanceof EditText) {
            EditText textView = getView(id);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        } else if (getView(id) instanceof TextView) {
            TextView textView = getView(id);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        } else if (getView(id) instanceof Button) {
            Button textView = getView(id);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        } else if (getView(id) instanceof AppCompatButton) {
            AppCompatButton textView = getView(id);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        } else {
            Log.w("HolderView", "未匹配到有效对象");
        }
        return this;
    }

    /**
     * 设置图片
     */
    public <Image> DialogServlet setImageView(@IdRes int id, @NotNull Image image) {
        if (getView(id) instanceof AppCompatImageView) {
            AppCompatImageView imageView = getView(id);
            g.load(image).into(imageView);
        } else if (getView(id) instanceof ImageView) {
            ImageView imageView = getView(id);
            g.load(image).into(imageView);
        } else if (getView(id) instanceof ImageButton) {
            ImageButton imageView = getView(id);
            g.load(image).into(imageView);
        } else if (getView(id) instanceof AppCompatImageButton) {
            AppCompatImageButton imageView = getView(id);
            g.load(image).into(imageView);
        } else {
            Log.w("HolderView", "未匹配到有效对象");
        }
        return this;
    }

    /**
     * 设置图片颜色
     */
    public DialogServlet setImageColor(@IdRes int id, @ColorRes int color) {
        if (getView(id) instanceof AppCompatImageView) {
            AppCompatImageView imageView = getView(id);
            imageView.setColorFilter(r.getColor(color, t));
        } else if (getView(id) instanceof ImageView) {
            ImageView imageView = getView(id);
            imageView.setColorFilter(r.getColor(color, t));
        } else if (getView(id) instanceof ImageButton) {
            ImageButton imageView = getView(id);
            imageView.setColorFilter(r.getColor(color, t));
        } else if (getView(id) instanceof AppCompatImageButton) {
            AppCompatImageButton imageView = getView(id);
            imageView.setColorFilter(r.getColor(color, t));
        } else {
            Log.w("HolderView", "未匹配到有效对象");
        }
        return this;
    }

    /**
     * 设置图片颜色
     */
    public DialogServlet setImageColor(@IdRes int id, @ColorRes int color, PorterDuff.Mode mode) {
        if (getView(id) instanceof AppCompatImageView) {
            AppCompatImageView imageView = getView(id);
            imageView.setColorFilter(r.getColor(color, t), mode);
        } else if (getView(id) instanceof ImageView) {
            ImageView imageView = getView(id);
            imageView.setColorFilter(r.getColor(color, t), mode);
        } else if (getView(id) instanceof ImageButton) {
            ImageButton imageView = getView(id);
            imageView.setColorFilter(r.getColor(color, t), mode);
        } else if (getView(id) instanceof AppCompatImageButton) {
            AppCompatImageButton imageView = getView(id);
            imageView.setColorFilter(r.getColor(color, t), mode);
        } else {
            Log.w("HolderView", "未匹配到有效对象");
        }
        return this;
    }

    public DialogServlet setOnClickListener(@IdRes int id, OnClickTriggerListener listener) {
        getView(id).setOnClickListener(v -> listener.ok(this));
        return this;
    }

    public DialogServlet setOnLongClickListener(@IdRes int id, OnClickTriggerListener listener) {
        getView(id).setOnLongClickListener(v -> {
            listener.ok(this);
            return false;
        });
        return this;
    }

    @SuppressLint("ClickableViewAccessibility")
    public DialogServlet setOnTouchListener(@IdRes int id, OnClickTriggerListener listener) {
        getView(id).setOnTouchListener((v, e) -> {
            listener.ok(this);
            return false;
        });
        return this;
    }

}
