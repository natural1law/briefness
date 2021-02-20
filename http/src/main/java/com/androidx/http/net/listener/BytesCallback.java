package com.androidx.http.net.listener;

public interface BytesCallback {

    void onFailure(String msg);

    void onSuccess(byte[] data);

}
