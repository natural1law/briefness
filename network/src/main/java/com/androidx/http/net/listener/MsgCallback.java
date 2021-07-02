package com.androidx.http.net.listener;

import com.google.protobuf.ByteString;

public interface MsgCallback {
    void message(int type, String msg, ByteString bs);
}
