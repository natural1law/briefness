package com.androidx.http.base;

import android.os.Parcelable;

import java.io.Serializable;

public abstract class BaseBean implements Parcelable , Serializable {

    @Override
    public int describeContents() {
        return 0;
    }

}
