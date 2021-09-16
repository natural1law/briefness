package com.androidx.view.page;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.content.Context;
import android.graphics.PorterDuff;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
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
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Size;
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

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

public final class HolderView extends RecyclerView.ViewHolder {

    private final Context c;
    private final View convertView;
    private final SparseArray<View> views;

    private HolderView(@NonNull View itemView) {
        super(itemView);
        convertView = itemView;
        c = itemView.getContext();
        views = new SparseArray<>();
    }

    public static HolderView createHolderView(ViewGroup parent, @LayoutRes int layoutId) {
        return createHolderView(parent, layoutId, false);
    }

    public static HolderView createHolderView(ViewGroup parent, @LayoutRes int layoutId, boolean root) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, root);
        return new HolderView(itemView);
    }

    /**
     * 通过viewId获取控件
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = convertView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    public HolderView setMatrix(@IdRes int id, int... v) {
        if (getView(id) instanceof AppCompatTextView) {
            AppCompatTextView textView = getView(id);
            textView.setWidth(v[0] == 0 ? MATCH_PARENT : dp(c, v[0]));
            textView.setHeight(v[1] == 0 ? WRAP_CONTENT : dp(c, v[1]));
        } else if (getView(id) instanceof TextView) {
            TextView textView = getView(id);
            textView.setWidth(v[0] == 0 ? MATCH_PARENT : dp(c, v[0]));
            textView.setHeight(v[1] == 0 ? WRAP_CONTENT : dp(c, v[1]));
        } else if (getView(id) instanceof AppCompatImageView) {
            AppCompatImageView imageView = getView(id);
            ViewGroup.LayoutParams params = imageView.getLayoutParams();
            params.width = v[0] == 0 ? MATCH_PARENT : dp(c, v[0]);
            params.height = v[1] == 0 ? WRAP_CONTENT : dp(c, v[1]);
            imageView.setLayoutParams(params);
        } else if (getView(id) instanceof ImageView) {
            ImageView imageView = getView(id);
            ViewGroup.LayoutParams params = imageView.getLayoutParams();
            params.width = v[0] == 0 ? MATCH_PARENT : dp(c, v[0]);
            params.height = v[1] == 0 ? WRAP_CONTENT : dp(c, v[1]);
            imageView.setLayoutParams(params);
        } else if (getView(id) instanceof AppCompatEditText) {
            AppCompatEditText editText = getView(id);
            editText.setWidth(v[0] == 0 ? MATCH_PARENT : dp(c, v[0]));
            editText.setHeight(v[1] == 0 ? WRAP_CONTENT : dp(c, v[1]));
        } else if (getView(id) instanceof EditText) {
            EditText editText = getView(id);
            editText.setWidth(v[0] == 0 ? MATCH_PARENT : dp(c, v[0]));
            editText.setHeight(v[1] == 0 ? WRAP_CONTENT : dp(c, v[1]));
        } else if (getView(id) instanceof AppCompatAutoCompleteTextView) {
            AppCompatAutoCompleteTextView textView = getView(id);
            textView.setWidth(v[0] == 0 ? MATCH_PARENT : dp(c, v[0]));
            textView.setHeight(v[1] == 0 ? WRAP_CONTENT : dp(c, v[1]));
        } else if (getView(id) instanceof AppCompatMultiAutoCompleteTextView) {
            AppCompatMultiAutoCompleteTextView textView = getView(id);
            textView.setWidth(v[0] == 0 ? MATCH_PARENT : dp(c, v[0]));
            textView.setHeight(v[1] == 0 ? WRAP_CONTENT : dp(c, v[1]));
        } else if (getView(id) instanceof AppCompatImageButton) {
            AppCompatImageButton imageButton = getView(id);
            ViewGroup.LayoutParams params = imageButton.getLayoutParams();
            params.width = v[0] == 0 ? MATCH_PARENT : dp(c, v[0]);
            params.height = v[1] == 0 ? WRAP_CONTENT : dp(c, v[1]);
            imageButton.setLayoutParams(params);
        } else if (getView(id) instanceof ImageButton) {
            ImageButton imageButton = getView(id);
            ViewGroup.LayoutParams params = imageButton.getLayoutParams();
            params.width = v[0] == 0 ? MATCH_PARENT : dp(c, v[0]);
            params.height = v[1] == 0 ? WRAP_CONTENT : dp(c, v[1]);
            imageButton.setLayoutParams(params);
        } else if (getView(id) instanceof AppCompatCheckedTextView) {
            AppCompatCheckedTextView textView = getView(id);
            textView.setWidth(v[0] == 0 ? MATCH_PARENT : dp(c, v[0]));
            textView.setHeight(v[1] == 0 ? WRAP_CONTENT : dp(c, v[1]));
        } else if (getView(id) instanceof LinearLayoutCompat) {
            LinearLayoutCompat layout = getView(id);
            ViewGroup.LayoutParams params = layout.getLayoutParams();
            params.width = v[0] == 0 ? MATCH_PARENT : dp(c, v[0]);
            params.height = v[1] == 0 ? WRAP_CONTENT : dp(c, v[1]);
            layout.setLayoutParams(params);
        } else if (getView(id) instanceof LinearLayout) {
            LinearLayout layout = getView(id);
            ViewGroup.LayoutParams params = layout.getLayoutParams();
            params.width = v[0] == 0 ? MATCH_PARENT : dp(c, v[0]);
            params.height = v[1] == 0 ? WRAP_CONTENT : dp(c, v[1]);
            layout.setLayoutParams(params);
        } else if (getView(id) instanceof ConstraintLayout) {
            ConstraintLayout layout = getView(id);
            ViewGroup.LayoutParams params = layout.getLayoutParams();
            params.width = v[0] == 0 ? MATCH_PARENT : dp(c, v[0]);
            params.height = v[1] == 0 ? WRAP_CONTENT : dp(c, v[1]);
            layout.setLayoutParams(params);
        } else if (getView(id) instanceof FrameLayout) {
            FrameLayout layout = getView(id);
            ViewGroup.LayoutParams params = layout.getLayoutParams();
            params.width = v[0] == 0 ? MATCH_PARENT : dp(c, v[0]);
            params.height = v[1] == 0 ? WRAP_CONTENT : dp(c, v[1]);
            layout.setLayoutParams(params);
        } else if (getView(id) instanceof RelativeLayout) {
            RelativeLayout layout = getView(id);
            ViewGroup.LayoutParams params = layout.getLayoutParams();
            params.width = v[0] == 0 ? MATCH_PARENT : dp(c, v[0]);
            params.height = v[1] == 0 ? WRAP_CONTENT : dp(c, v[1]);
            layout.setLayoutParams(params);
        } else if (getView(id) instanceof GridView) {
            GridView view = getView(id);
            ViewGroup.LayoutParams params = view.getLayoutParams();
            params.width = v[0] == 0 ? MATCH_PARENT : dp(c, v[0]);
            params.height = v[1] == 0 ? WRAP_CONTENT : dp(c, v[1]);
            view.setLayoutParams(params);
        } else if (getView(id) instanceof RecyclerView) {
            RecyclerView recyclerView = getView(id);
            ViewGroup.LayoutParams params = recyclerView.getLayoutParams();
            params.width = v[0] == 0 ? MATCH_PARENT : dp(c, v[0]);
            params.height = v[1] == 0 ? WRAP_CONTENT : dp(c, v[1]);
            recyclerView.setLayoutParams(params);
        }
        return this;
    }

    public <Text> HolderView setText(@IdRes int id, Text text) {
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

    public <Text> HolderView setHint(@IdRes int id, Text text) {
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

    public HolderView setTextColor(@IdRes int id, @ColorRes int color) {
        if (getView(id) instanceof AppCompatTextView) {
            AppCompatTextView textView = getView(id);
            textView.setTextColor(c.getResources().getColor(color, c.getTheme()));
        } else if (getView(id) instanceof AppCompatCheckedTextView) {
            AppCompatCheckedTextView textView = getView(id);
            textView.setTextColor(c.getResources().getColor(color, c.getTheme()));
        } else if (getView(id) instanceof AppCompatAutoCompleteTextView) {
            AppCompatAutoCompleteTextView textView = getView(id);
            textView.setTextColor(c.getResources().getColor(color, c.getTheme()));
        } else if (getView(id) instanceof AppCompatEditText) {
            AppCompatEditText textView = getView(id);
            textView.setTextColor(c.getResources().getColor(color, c.getTheme()));
        } else if (getView(id) instanceof EditText) {
            EditText textView = getView(id);
            textView.setTextColor(c.getResources().getColor(color, c.getTheme()));
        } else if (getView(id) instanceof TextView) {
            TextView textView = getView(id);
            textView.setTextColor(c.getResources().getColor(color, c.getTheme()));
        } else if (getView(id) instanceof Button) {
            Button textView = getView(id);
            textView.setTextColor(c.getResources().getColor(color, c.getTheme()));
        } else if (getView(id) instanceof AppCompatButton) {
            AppCompatButton textView = getView(id);
            textView.setTextColor(c.getResources().getColor(color, c.getTheme()));
        } else {
            Log.w("HolderView", "未匹配到有效对象");
        }
        return this;
    }

    public HolderView setHintColor(@IdRes int id, @ColorRes int color) {
        if (getView(id) instanceof AppCompatTextView) {
            AppCompatTextView textView = getView(id);
            textView.setHintTextColor(c.getResources().getColor(color, c.getTheme()));
        } else if (getView(id) instanceof AppCompatCheckedTextView) {
            AppCompatCheckedTextView textView = getView(id);
            textView.setHintTextColor(c.getResources().getColor(color, c.getTheme()));
        } else if (getView(id) instanceof AppCompatAutoCompleteTextView) {
            AppCompatAutoCompleteTextView textView = getView(id);
            textView.setHintTextColor(c.getResources().getColor(color, c.getTheme()));
        } else if (getView(id) instanceof AppCompatEditText) {
            AppCompatEditText textView = getView(id);
            textView.setHintTextColor(c.getResources().getColor(color, c.getTheme()));
        } else if (getView(id) instanceof EditText) {
            EditText textView = getView(id);
            textView.setHintTextColor(c.getResources().getColor(color, c.getTheme()));
        } else if (getView(id) instanceof TextView) {
            TextView textView = getView(id);
            textView.setHintTextColor(c.getResources().getColor(color, c.getTheme()));
        } else if (getView(id) instanceof Button) {
            Button textView = getView(id);
            textView.setHintTextColor(c.getResources().getColor(color, c.getTheme()));
        } else if (getView(id) instanceof AppCompatButton) {
            AppCompatButton textView = getView(id);
            textView.setHintTextColor(c.getResources().getColor(color, c.getTheme()));
        } else {
            Log.w("HolderView", "未匹配到有效对象");
        }
        return this;
    }

    public HolderView setTextSize(@IdRes int id, @Size float size) {
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

    public <Image> HolderView setImageView(@IdRes int id, @NotNull Image image) {
        if (getView(id) instanceof AppCompatImageView) {
            AppCompatImageView imageView = getView(id);
            Glide.with(c).load(image).into(imageView);
        } else if (getView(id) instanceof ImageView) {
            ImageView imageView = getView(id);
            Glide.with(c).load(image).into(imageView);
        } else if (getView(id) instanceof ImageButton) {
            ImageButton imageView = getView(id);
            Glide.with(c).load(image).into(imageView);
        } else if (getView(id) instanceof AppCompatImageButton) {
            AppCompatImageButton imageView = getView(id);
            Glide.with(c).load(image).into(imageView);
        } else {
            Log.w("HolderView", "未匹配到有效对象");
        }
        return this;
    }

    public HolderView setImageColor(@IdRes int id, @ColorRes int color) {
        if (getView(id) instanceof AppCompatImageView) {
            AppCompatImageView imageView = getView(id);
            imageView.setColorFilter(c.getResources().getColor(color, c.getTheme()));
        } else if (getView(id) instanceof ImageView) {
            ImageView imageView = getView(id);
            imageView.setColorFilter(c.getResources().getColor(color, c.getTheme()));
        } else if (getView(id) instanceof ImageButton) {
            ImageButton imageView = getView(id);
            imageView.setColorFilter(c.getResources().getColor(color, c.getTheme()));
        } else if (getView(id) instanceof AppCompatImageButton) {
            AppCompatImageButton imageView = getView(id);
            imageView.setColorFilter(c.getResources().getColor(color, c.getTheme()));
        } else {
            Log.w("HolderView", "未匹配到有效对象");
        }
        return this;
    }

    public HolderView setImageColor(@IdRes int id, @ColorRes int color, PorterDuff.Mode mode) {
        if (getView(id) instanceof AppCompatImageView) {
            AppCompatImageView imageView = getView(id);
            imageView.setColorFilter(c.getResources().getColor(color, c.getTheme()), mode);
        } else if (getView(id) instanceof ImageView) {
            ImageView imageView = getView(id);
            imageView.setColorFilter(c.getResources().getColor(color, c.getTheme()), mode);
        } else if (getView(id) instanceof ImageButton) {
            ImageButton imageView = getView(id);
            imageView.setColorFilter(c.getResources().getColor(color, c.getTheme()), mode);
        } else if (getView(id) instanceof AppCompatImageButton) {
            AppCompatImageButton imageView = getView(id);
            imageView.setColorFilter(c.getResources().getColor(color, c.getTheme()), mode);
        } else {
            Log.w("HolderView", "未匹配到有效对象");
        }
        return this;
    }

    public void setFlexible(@IdRes int id) {
        View view = getView(id);
        if (view.getVisibility() == View.GONE) {
            AnimUtil.expand(view);
        } else {
            AnimUtil.collapse(view);
        }
    }

    public HolderView setOnClickListener(@IdRes int id, View.OnClickListener listener) {
        getView(id).setOnClickListener(listener);
        return this;
    }

    public HolderView setOnLongClickListener(@IdRes int id, View.OnLongClickListener listener) {
        getView(id).setOnLongClickListener(listener);
        return this;
    }

    public HolderView setOnTouchListener(@IdRes int id, View.OnTouchListener listener) {
        getView(id).setOnTouchListener(listener);
        return this;
    }

    /**
     * px转换成dp
     */
    private int dp(Context context, float px) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px * scale);
    }

    /**
     * 动画工具类
     */
    private static class AnimUtil {

        /**
         * 展开
         */
        private static void expand(View v) {
            int exactly = View.MeasureSpec.makeMeasureSpec(((View) v.getParent()).getWidth(), View.MeasureSpec.EXACTLY);
            int unspecified = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            v.measure(exactly, unspecified);
            int targetHeight = v.getMeasuredHeight();
            v.getLayoutParams().height = 1;
            v.setVisibility(View.VISIBLE);

            Animation animation = new Animation() {
                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {
                    v.getLayoutParams().height = interpolatedTime == 1
                            ? ViewGroup.LayoutParams.WRAP_CONTENT
                            : (int) (targetHeight * interpolatedTime);
                    v.requestLayout();
                }

                @Override
                public boolean willChangeBounds() {
                    return true;
                }
            };

            animation.setInterpolator(new LinearInterpolator());
            animation.setRepeatMode(AnimationSet.REVERSE);
            animation.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density));
            animation.cancel();
            animation.reset();
            v.clearAnimation();
            v.startAnimation(animation);
        }

        /**
         * 收起
         */
        private static void collapse(View v) {
            int initialHeight = v.getMeasuredHeight();
            Animation animation = new Animation() {
                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {
                    if (interpolatedTime == 1) {
                        v.setVisibility(View.GONE);
                    } else {
                        v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                        v.requestLayout();
                    }
                }

                @Override
                public boolean willChangeBounds() {
                    return true;
                }
            };
            animation.setInterpolator(new LinearInterpolator());
            animation.setRepeatMode(AnimationSet.REVERSE);
            animation.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
            animation.cancel();
            animation.reset();
            v.clearAnimation();
            v.startAnimation(animation);
        }

    }

}
