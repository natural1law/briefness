package com.androidx.http.net.listener;

import android.util.Log;

public interface StringCallback {

    default void onFailure(String msg) {
        Log.e("接口异常", msg);
    }

    void onSuccess(String data);

}
