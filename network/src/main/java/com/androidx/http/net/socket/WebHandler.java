package com.androidx.http.net.socket;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.androidx.http.module.MessageModule;
import com.androidx.http.net.module.DataModule;
import com.androidx.http.net.listener.ActionListener;
import com.google.protobuf.InvalidProtocolBufferException;

public final class WebHandler {

    private WebHandler() {
    }

    public static ActionListener actionListener;

    public static final Handler handler = new Handler(Looper.getMainLooper(), message -> {
        try {
            DataModule data = (DataModule) message.obj;
            if (message.what ==  -1) {
                try {
                    data.getLoginCallback().onFailure(data.getMsg());
                    return false;
                } catch (Exception e) {
                    return false;
                }
            } else if (message.what ==  -2) {
                try {
                    data.getLoginCallback().onFailure(data.getMsg());
                    return false;
                } catch (Exception e) {
                    return false;
                }
            } else if (message.what == 0) {
                try {
                    data.getLoginCallback().onSuccess();
                    return false;
                } catch (Exception e) {
                    return false;
                }
            } else if (message.what == 1) {
                try {
                    MessageModule.MsgResponse response = MessageModule.MsgResponse.parseFrom(data.getBytes().toByteArray());
                    data.getMsgCallback().message(response.getCode(), response.getMsg(), response.getData());
                } catch (InvalidProtocolBufferException e) {
                    Log.e("callbackException", String.valueOf(e.getMessage()));
                }
                return false;
            } else if (message.what == 2) {
                actionListener.online(String.valueOf(message.obj));
                return false;
            } else if (message.what == 3) {
                actionListener.offline(String.valueOf(message.obj));
                return false;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    });

}
