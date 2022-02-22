package com.androidx.http.net.listener;

import android.util.Log;

public interface Response {

    void onSuccess(String data);

    default void onFailure(String msg) {
        Log.e(getClass().getName(), msg);
    }

}
