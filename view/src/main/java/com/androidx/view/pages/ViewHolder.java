package com.androidx.view.pages;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.RatingBar;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.interpolator.view.animation.FastOutLinearInInterpolator;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.zyao89.view.zloading.ZLoadingView;
import com.zyao89.view.zloading.Z_TYPE;

public class ViewHolder extends RecyclerView.ViewHolder {

    private final SparseArray<View> mViews;
    private final View mConvertView;
    private final Context mContext;

    public ViewHolder(Context context, View itemView) {
        super(itemView);
        mContext = context;
        mConvertView = itemView;
        mViews = new SparseArray<>();
    }

    public static ViewHolder createViewHolder(Context context, View itemView) {
        return new ViewHolder(context, itemView);
    }

    public static ViewHolder createViewHolder(Context context, ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return new ViewHolder(context, itemView);
    }

    /**
     * 通过viewId获取控件
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        //noinspection unchecked
        return (T) view;
    }

    public View getConvertView() {
        return mConvertView;
    }

    /* ***以下为辅助方法*****/

    /**
     * 设置TextView的值
     */
    public <P> ViewHolder setTextView(@IdRes int viewId, P text) {
        AppCompatTextView tv = getView(viewId);
        tv.setText(String.valueOf(text));
        return this;
    }

    public <T> ViewHolder setImageView(@IdRes int viewId, T resId) {
        AppCompatImageView view = getView(viewId);
        view.setScaleType(ImageView.ScaleType.FIT_CENTER);
        Glide.with(mContext).load(resId).into(view);
        return this;
    }

    public <T> ViewHolder setImageButton(@IdRes int viewId, T resId) {
        AppCompatImageButton view = getView(viewId);
        view.setScaleType(ImageView.ScaleType.FIT_CENTER);
        Glide.with(mContext).load(resId).into(view);
        return this;
    }

    public ViewHolder setBackgroundColor(@IdRes int viewId, @ColorInt int color) {
        View view = getView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    public ViewHolder setBackgroundRes(@IdRes int viewId, int backgroundRes) {
        View view = getView(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    public ViewHolder setTextColor(@IdRes int viewId, @ColorInt int textColor) {
        AppCompatTextView view = getView(viewId);
        view.setTextColor(textColor);
        return this;
    }

    public ViewHolder setTextColorRes(int viewId, @ColorRes int textColorRes) {
        AppCompatTextView view = getView(viewId);
        view.setTextColor(mContext.getResources().getColor(textColorRes, mContext.getTheme()));
        return this;
    }

    @SuppressLint("NewApi")
    public ViewHolder setAlpha(@IdRes int viewId, float value) {
        getView(viewId).setAlpha(value);
        return this;
    }

    public ViewHolder setVisible(@IdRes int viewId, boolean visible) {
        ViewGroup view = getView(viewId);
        Handler handler = new Handler(Looper.getMainLooper());
        if (visible) {
            AnimUtil.expand(view);
//            view.setAnimation(moveToViewBottom(500));
//            handler.postDelayed(() -> view.setVisibility(View.VISIBLE), 800);
        } else {
            AnimUtil.collapse(view);
//            view.setAnimation(moveToViewLocation(500));
//            handler.postDelayed(() -> view.setVisibility(View.GONE), 200);
        }
        return this;
    }

    public ViewHolder setVisible(@IdRes int viewId, boolean visible, Integer... delay) {
        ViewGroup view = getView(viewId);
        Handler handler = new Handler(Looper.getMainLooper());
        if (visible) {
            view.setAnimation(moveToViewBottom(delay[0]));
            handler.postDelayed(() -> view.setVisibility(View.VISIBLE), delay[1]);
        } else {
            view.setAnimation(moveToViewLocation(delay[0]));
            handler.postDelayed(() -> view.setVisibility(View.GONE), delay[1]);
        }
        return this;
    }

    public ViewHolder linkIfy(@IdRes int viewId) {
        AppCompatTextView view = getView(viewId);
        Linkify.addLinks(view, Linkify.ALL);
        return this;
    }

    public ViewHolder setTypeface(Typeface typeface, @IdRes int... viewIds) {
        for (int viewId : viewIds) {
            AppCompatTextView view = getView(viewId);
            view.setTypeface(typeface);
            view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
        return this;
    }

    public ViewHolder setLoading(@IdRes int viewId, @NonNull Z_TYPE builder) {
        ZLoadingView view = getView(viewId);
        view.setLoadingBuilder(builder);
        return this;
    }

    public ViewHolder setRating(@IdRes int viewId, float rating) {
        RatingBar view = getView(viewId);
        view.setRating(rating);
        return this;
    }

    public ViewHolder setRating(@IdRes int viewId, float rating, int max) {
        RatingBar view = getView(viewId);
        view.setMax(max);
        view.setRating(rating);
        return this;
    }

    public ViewHolder setTag(@IdRes int viewId, Object tag) {
        View view = getView(viewId);
        view.setTag(tag);
        return this;
    }

    public ViewHolder setTag(@IdRes int viewId, int key, Object tag) {
        View view = getView(viewId);
        view.setTag(key, tag);
        return this;
    }

    public ViewHolder setChecked(@IdRes int viewId, boolean checked) {
        Checkable view = (Checkable) getView(viewId);
        view.setChecked(checked);
        return this;
    }

    /**
     * 关于事件的
     */
    public ViewHolder setOnClickListener(@IdRes int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public ViewHolder setOnTouchListener(@IdRes int viewId, View.OnTouchListener listener) {
        View view = getView(viewId);
        view.setOnTouchListener(listener);
        return this;
    }

    public ViewHolder setOnLongClickListener(@IdRes int viewId, View.OnLongClickListener listener) {
        View view = getView(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }

    /**
     * 从控件所在位置移动到控件的底部
     *
     * @param delay 动画时间
     */
    public static TranslateAnimation moveToViewBottom(int delay) {
        TranslateAnimation mHiddenAction = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 1.0f);
        mHiddenAction.setDuration(delay);
        return mHiddenAction;
    }

    /**
     * 从控件的底部移动到控件所在位置
     *
     * @param delay 动画时间
     */
    public static TranslateAnimation moveToViewLocation(int delay) {
        TranslateAnimation mHiddenAction = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 2.0f,
                Animation.RELATIVE_TO_SELF, 0.0f);
        mHiddenAction.setDuration(delay);
        return mHiddenAction;
    }

    public static class AnimUtil {

        public static void expand(final View view) {
            view.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            final int viewHeight = view.getMeasuredHeight();
            view.getLayoutParams().height = 0;
            view.setVisibility(View.VISIBLE);

            Animation animation = new Animation() {
                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {
                    if (interpolatedTime == 1) {
                        view.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    } else {
                        view.getLayoutParams().height = (int) (viewHeight * interpolatedTime);
                    }
                    view.requestLayout();
                }
            };
            animation.setDuration(500);
            animation.setInterpolator(new FastOutLinearInInterpolator());
            view.startAnimation(animation);
        }

        public static void collapse(final View view) {
            view.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            final int viewHeight = view.getMeasuredHeight();

            Animation animation = new Animation() {
                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {
                    if (interpolatedTime == 1) {
                        view.setVisibility(View.GONE);
                    } else {
                        view.getLayoutParams().height = viewHeight - (int) (viewHeight * interpolatedTime);
                        view.requestLayout();
                    }
                }
            };
            animation.setDuration(500);
            animation.setInterpolator(new FastOutLinearInInterpolator());
            view.startAnimation(animation);
        }
    }
}
