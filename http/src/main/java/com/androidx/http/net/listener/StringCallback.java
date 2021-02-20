package com.androidx.http.net.listener;

public interface StringCallback {

    void onFailure(String msg);

    void onSuccess(String data);

}
