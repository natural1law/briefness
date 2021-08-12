package com.androidx.view.dialog;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public final class DialogModule implements Parcelable {

    private boolean canceled;
    private boolean cancelable;
    private int layoutView;
    private int layoutViewId;
    private int layoutViewBackgroundColor;
    private int layoutViewBackgroundDrawable;
    private int layoutViewMarginsStart;
    private int layoutViewMarginsEnd;
    private int layoutViewMarginsTop;
    private int layoutViewMarginsBottom;
    private int layoutWidth;
    private int layoutHeight;
    private int layoutGravity;

    protected DialogModule(Parcel in) {
        canceled = in.readByte() != 0;
        cancelable = in.readByte() != 0;
        layoutView = in.readInt();
        layoutViewId = in.readInt();
        layoutViewBackgroundColor = in.readInt();
        layoutViewBackgroundDrawable = in.readInt();
        layoutViewMarginsStart = in.readInt();
        layoutViewMarginsEnd = in.readInt();
        layoutViewMarginsTop = in.readInt();
        layoutViewMarginsBottom = in.readInt();
        layoutWidth = in.readInt();
        layoutHeight = in.readInt();
        layoutGravity = in.readInt();
    }

    protected static final Creator<DialogModule> CREATOR = new Creator<DialogModule>() {
        @Override
        public DialogModule createFromParcel(Parcel in) {
            return new DialogModule(in);
        }

        @Override
        public DialogModule[] newArray(int size) {
            return new DialogModule[size];
        }
    };

    protected void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    protected void setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
    }

    protected void setLayoutView(int layoutView) {
        this.layoutView = layoutView;
    }

    protected void setLayoutViewId(int layoutViewId) {
        this.layoutViewId = layoutViewId;
    }

    protected void setLayoutViewBackgroundColor(int layoutViewBackgroundColor) {
        this.layoutViewBackgroundColor = layoutViewBackgroundColor;
    }

    protected void setLayoutViewBackgroundDrawable(int layoutViewBackgroundDrawable) {
        this.layoutViewBackgroundDrawable = layoutViewBackgroundDrawable;
    }

    protected void setLayoutViewMarginsStart(int layoutViewMarginsStart) {
        this.layoutViewMarginsStart = layoutViewMarginsStart;
    }

    protected void setLayoutViewMarginsEnd(int layoutViewMarginsEnd) {
        this.layoutViewMarginsEnd = layoutViewMarginsEnd;
    }

    protected void setLayoutViewMarginsTop(int layoutViewMarginsTop) {
        this.layoutViewMarginsTop = layoutViewMarginsTop;
    }

    protected void setLayoutViewMarginsBottom(int layoutViewMarginsBottom) {
        this.layoutViewMarginsBottom = layoutViewMarginsBottom;
    }

    protected void setLayoutWidth(int layoutWidth) {
        this.layoutWidth = layoutWidth;
    }

    protected void setLayoutHeight(int layoutHeight) {
        this.layoutHeight = layoutHeight;
    }

    protected void setLayoutGravity(int layoutGravity) {
        this.layoutGravity = layoutGravity;
    }

    protected boolean isCanceled() {
        return canceled;
    }

    protected boolean isCancelable() {
        return cancelable;
    }

    protected int getLayoutView() {
        return layoutView;
    }

    protected int getLayoutViewId() {
        return layoutViewId;
    }

    protected int getLayoutViewBackgroundColor() {
        return layoutViewBackgroundColor;
    }

    protected int getLayoutViewBackgroundDrawable() {
        return layoutViewBackgroundDrawable;
    }

    protected int getLayoutViewMarginsStart() {
        return layoutViewMarginsStart;
    }

    protected int getLayoutViewMarginsEnd() {
        return layoutViewMarginsEnd;
    }

    protected int getLayoutViewMarginsTop() {
        return layoutViewMarginsTop;
    }

    protected int getLayoutViewMarginsBottom() {
        return layoutViewMarginsBottom;
    }

    protected int getLayoutWidth() {
        return layoutWidth;
    }

    protected int getLayoutHeight() {
        return layoutHeight;
    }

    protected int getLayoutGravity() {
        return layoutGravity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (canceled ? 1 : 0));
        parcel.writeByte((byte) (cancelable ? 1 : 0));
        parcel.writeInt(layoutView);
        parcel.writeInt(layoutViewId);
        parcel.writeInt(layoutViewBackgroundColor);
        parcel.writeInt(layoutViewBackgroundDrawable);
        parcel.writeInt(layoutViewMarginsStart);
        parcel.writeInt(layoutViewMarginsEnd);
        parcel.writeInt(layoutViewMarginsTop);
        parcel.writeInt(layoutViewMarginsBottom);
        parcel.writeInt(layoutWidth);
        parcel.writeInt(layoutHeight);
        parcel.writeInt(layoutGravity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DialogModule)) return false;
        DialogModule that = (DialogModule) o;
        return isCanceled() == that.isCanceled() && isCancelable() == that.isCancelable() && getLayoutView() == that.getLayoutView() && getLayoutViewId() == that.getLayoutViewId() && getLayoutViewBackgroundColor() == that.getLayoutViewBackgroundColor() && getLayoutViewBackgroundDrawable() == that.getLayoutViewBackgroundDrawable() && getLayoutViewMarginsStart() == that.getLayoutViewMarginsStart() && getLayoutViewMarginsEnd() == that.getLayoutViewMarginsEnd() && getLayoutViewMarginsTop() == that.getLayoutViewMarginsTop() && getLayoutViewMarginsBottom() == that.getLayoutViewMarginsBottom() && getLayoutWidth() == that.getLayoutWidth() && getLayoutHeight() == that.getLayoutHeight() && getLayoutGravity() == that.getLayoutGravity();
    }

    @Override
    public int hashCode() {
        return Objects.hash(isCanceled(), isCancelable(), getLayoutView(), getLayoutViewId(), getLayoutViewBackgroundColor(), getLayoutViewBackgroundDrawable(), getLayoutViewMarginsStart(), getLayoutViewMarginsEnd(), getLayoutViewMarginsTop(), getLayoutViewMarginsBottom(), getLayoutWidth(), getLayoutHeight(), getLayoutGravity());
    }
}
