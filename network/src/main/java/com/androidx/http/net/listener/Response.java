package com.androidx.http.net.listener;

import android.util.Log;

public interface Response {

    default void onFailure(String msg) {
        Log.e("请求异常", msg);
    }

    void onSuccess(String data);

}
