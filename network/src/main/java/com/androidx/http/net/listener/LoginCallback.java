package com.androidx.http.net.listener;

import android.util.Log;

public interface LoginCallback {

    void onSuccess();

    default void onFailure(String msg) {
        Log.e("请求异常", msg);
    }

}
