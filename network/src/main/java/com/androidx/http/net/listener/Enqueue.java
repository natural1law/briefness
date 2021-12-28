package com.androidx.http.net.listener;

import okhttp3.WebSocket;
import okio.ByteString;

public interface Enqueue {

    /**
     * 发送
     *
     * @return 成功/失败
     */
    boolean send(int type, byte[] msg);

    /**
     * 发送
     *
     * @return 成功/失败
     */
    boolean send(byte[] msg);

    /**
     * 发送
     *
     * @return 成功/失败
     */
    boolean send(ByteString bs);

    /**
     * 关闭
     */
    void close();

    /**
     * 获取连接次数
     */
    int reconnectCount();

    /**
     * 获取webSocket对象
     */
    WebSocket getWebSocket();

    /**
     * 在线/离线
     */
    Enqueue setActionListener(ActionListener listener);

    /**
     * 消息回调
     */
    Enqueue setMsgCallback(MsgCallback callback);

    /**
     * 登录回调
     */
    Enqueue setLoginCallback(LoginCallback callback);

}
