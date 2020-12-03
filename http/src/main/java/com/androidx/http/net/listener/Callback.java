package com.androidx.http.net.listener;

public interface Callback {

    void onFailure(String msg);

    <T>void onSuccess(T data);

}
