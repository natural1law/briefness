package com.androidx.view.pages;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.RatingBar;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
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
    private <T extends View> T getView(int viewId) {
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
    public ViewHolder setTextView(@IdRes int viewId, String text) {
        AppCompatTextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public <T> ViewHolder setImageView(@IdRes int viewId, T resId, @Nullable ImageView.ScaleType... types) {
        AppCompatImageView view = getView(viewId);
        if (types != null) {
            view.setScaleType(types[0]);
        }
        Glide.with(mContext).load(resId).into(view);
        return this;
    }

    public <T> ViewHolder setImageButton(@IdRes int viewId, T resId, @Nullable ImageView.ScaleType... types) {
        AppCompatImageButton view = getView(viewId);
        if (types != null) {
            view.setScaleType(types[0]);
        }
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
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
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

}
