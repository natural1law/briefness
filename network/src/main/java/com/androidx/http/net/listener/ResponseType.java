package com.androidx.http.net.listener;

import android.util.Log;

public interface ResponseType<C> {

    void onSuccess(C data);

    default void onFailure(String msg) {
        Log.e(getClass().getName(), msg);
    }
}
