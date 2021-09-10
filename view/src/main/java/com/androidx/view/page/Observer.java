package com.androidx.view.page;

import android.os.Parcel;
import android.os.Parcelable;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * @date 2020/11/16
 */
public final class Observer implements Parcelable {

    private Observer() {
    }

    protected Observer(Parcel in) {
        info = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(info);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Observer> CREATOR = new Creator<Observer>() {
        @Override
        public Observer createFromParcel(Parcel in) {
            return new Observer(in);
        }

        @Override
        public Observer[] newArray(int size) {
            return new Observer[size];
        }
    };

    public static Observer build() {
        synchronized (Observer.class) {
            return SingletonHolder.INSTANCE;
        }
    }

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    private String info;

    public void setInfo(String info) {
        support.firePropertyChange("info", this.info, info);
        this.info = info;
    }

    public void addListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    private static final class SingletonHolder {
        private static final Observer INSTANCE = new Observer();
    }
}
