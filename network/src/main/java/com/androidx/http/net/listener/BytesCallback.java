package com.androidx.http.net.listener;

import android.util.Log;

public interface BytesCallback {

    void onSuccess(byte[] data);

    default void onFailure(String msg) {
        Log.e("请求异常", msg);
    }

}
