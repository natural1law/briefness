package com.androidx.http.net.listener;

public interface LoginCallback {

    void onSuccess();

    void onFailure(String msg);
}
