package com.androidx.view.page;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.content.Context;
import android.graphics.PorterDuff;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Size;
import androidx.recyclerview.widget.RecyclerView;

import com.androidx.reduce.tools.Convert;
import com.androidx.reduce.tools.Toasts;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

public final class HolderView extends RecyclerView.ViewHolder {

    private final Context c;
    private final View convertView;
    private final Convert.Pixel pixel;
    private final SparseArray<View> views;

    private HolderView(@NonNull View itemView) {
        super(itemView);
        convertView = itemView;
        c = itemView.getContext();
        views = new SparseArray<>();
        pixel = Convert.Pixel.get(c);
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

    public void setMatrix(@IdRes int id, int... v) {
        if (getView(id) instanceof TextView) {
            TextView textView = getView(id);
            textView.setWidth(v[0] == -1 ? MATCH_PARENT : pixel.dp(v[0]));
            textView.setHeight(v[1] == -1 ? WRAP_CONTENT : pixel.dp(v[1]));
        } else if (getView(id) instanceof ImageView) {
            ImageView imageView = getView(id);
            ViewGroup.LayoutParams params = imageView.getLayoutParams();
            params.width = v[0] == -1 ? MATCH_PARENT : pixel.dp(v[0]);
            params.height = v[1] == -1 ? WRAP_CONTENT : pixel.dp(v[1]);
            imageView.setLayoutParams(params);
        } else if (getView(id) instanceof ViewGroup) {
            ViewGroup view = getView(id);
            ViewGroup.LayoutParams params = view.getLayoutParams();
            params.width = v[0] == 0 ? MATCH_PARENT : pixel.dp(v[0]);
            params.height = v[1] == 0 ? WRAP_CONTENT : pixel.dp(v[1]);
            view.setLayoutParams(params);
        } else Ve.isObject();
    }

    public <S> void setText(@IdRes int id, S text) {
        if (getView(id) instanceof TextView) {
            TextView textView = getView(id);
            textView.setText(String.valueOf(text));
        } else Ve.isObject();
    }

    public <S> void setHint(@IdRes int id, S text) {
        if (getView(id) instanceof TextView) {
            TextView textView = getView(id);
            textView.setHint(String.valueOf(text));
        } else Ve.isObject();
    }

    public void setTextColor(@IdRes int id, @ColorRes int color) {
        if (getView(id) instanceof TextView) {
            TextView textView = getView(id);
            textView.setTextColor(c.getResources().getColor(color, c.getTheme()));
        } else Ve.isObject();
    }

    public void setHintColor(@IdRes int id, @ColorRes int color) {
        if (getView(id) instanceof TextView) {
            TextView textView = getView(id);
            textView.setHintTextColor(c.getResources().getColor(color, c.getTheme()));
        } else Ve.isObject();
    }

    public void setTextSize(@IdRes int id, @Size float size) {
        if (getView(id) instanceof TextView) {
            TextView textView = getView(id);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        } else Ve.isObject();
    }

    public <I> void setImageView(@IdRes int id, @NotNull I image) {
        if (getView(id) instanceof ImageView) {
            ImageView imageView = getView(id);
            Glide.with(c).load(image).into(imageView);
        } else Ve.isObject();
    }

    public void setImageColor(@IdRes int id, @ColorRes int color) {
        if (getView(id) instanceof ImageView) {
            ImageView imageView = getView(id);
            imageView.setColorFilter(c.getResources().getColor(color, c.getTheme()));
        } else Ve.isObject();
    }

    public void setImageColor(@IdRes int id, @ColorRes int color, PorterDuff.Mode mode) {
        if (getView(id) instanceof ImageView) {
            ImageView imageView = getView(id);
            imageView.setColorFilter(c.getResources().getColor(color, c.getTheme()), mode);
        } else Ve.isObject();
    }

    public void setFlexible(@IdRes int id) {
        View view = getView(id);
        if (view.getVisibility() == View.GONE) AnimUtil.expand(view);
        else AnimUtil.collapse(view);
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

    private static final class Ve extends Exception {

        private Ve(String message) {
            Toasts.e(getClass().getName(), message);
        }

        public static void isObject() {
            try {
                throw new Ve("未匹配到有效对象");
            } catch (Exception e) {
                Toasts.e(Ve.class.getName(), e);
            }
        }
    }

}
