package com.androidx.http.net.listener;

import android.util.Log;

public interface Callback {

    void onSuccess(byte[] data) throws Exception;

    default void onFailure(String msg) {
        Log.e("请求异常", msg);
    }

}
