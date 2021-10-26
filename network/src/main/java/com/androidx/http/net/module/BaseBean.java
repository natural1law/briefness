package com.androidx.http.net.module;

import android.os.Parcelable;

import java.io.Externalizable;

public abstract class BaseBean implements Parcelable, Externalizable {

    @Override
    public int describeContents() {
        return 0;
    }

}
