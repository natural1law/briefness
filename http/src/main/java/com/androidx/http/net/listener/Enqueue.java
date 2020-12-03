package com.androidx.http.net.listener;

import okhttp3.WebSocket;
import okio.ByteString;

public interface Enqueue {

    boolean send(int type, byte[] msg);

    boolean send(ByteString bs);

    void close();

    int reconnectCount();

    WebSocket getWebSocket();

}
