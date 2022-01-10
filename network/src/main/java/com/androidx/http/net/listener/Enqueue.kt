package com.androidx.http.net.listener

import okhttp3.WebSocket
import okio.ByteString

interface Enqueue {
    /**
     * 发送
     *
     * @return 成功/失败
     */
    fun send(type: Int, msg: ByteArray): Boolean

    /**
     * 发送
     *
     * @return 成功/失败
     */
    fun send(msg: ByteArray): Boolean

    /**
     * 发送
     *
     * @return 成功/失败
     */
    fun send(bs: ByteString): Boolean

    /**
     * 关闭
     */
    fun close()

    /**
     * 获取连接次数
     */
    fun reconnectCount(): Int

    /**
     * 获取webSocket对象
     */
    fun getWebSocket(): WebSocket

    /**
     * 在线/离线
     */
    fun setActionListener(listener: ActionListener): Enqueue

    /**
     * 消息回调
     */
    fun setMsgCallback(callback: MsgCallback): Enqueue

    /**
     * 登录回调
     */
    fun setLoginCallback(callback: LoginCallback): Enqueue
}