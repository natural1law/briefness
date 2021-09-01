package com.androidx.http.use;

import static com.androidx.http.api.NetHttp.DEL_JSON;
import static com.androidx.http.api.NetHttp.DEL_MAP;
import static com.androidx.http.api.NetHttp.FROM_JSON;
import static com.androidx.http.api.NetHttp.GET_MAP;
import static com.androidx.http.api.NetHttp.POST_BYTES;
import static com.androidx.http.api.NetHttp.POST_JSON;
import static com.androidx.http.api.NetHttp.POST_MAP;

import android.net.Uri;

import com.androidx.http.api.NetHttp;
import com.androidx.http.net.Configuration;
import com.androidx.http.net.listener.BytesCallback;
import com.androidx.http.net.listener.Enqueue;
import com.androidx.http.net.listener.StringCallback;
import com.androidx.http.net.socket.Proxys;
import com.androidx.http.net.socket.SocketRequest;
import com.androidx.http.net.socket.WebConfiguration;
import com.google.gson.JsonObject;

import java.util.Map;

public final class NetRequest {

    /**
     * 设置请求头
     */
    public static void setHeader(Map<String, String> header) {
        Configuration.setHeaders(header);
    }

    /**
     * 设置SSL证书
     */
    public static void setSsl(String certification) {
        Configuration.setSsl(certification);
    }

    /**
     * 设置状态
     *
     * @param connect    成功状态key
     * @param disconnect 连接状态key
     * @param exception  断开状态key
     */
    public static void setResState(String connect, String disconnect, String exception) {
        WebConfiguration.statebuilder()
                .setConnect(connect)
                .setDisconnect(disconnect)
                .setException(exception)
                .build();
    }

    /**
     * 初始化webSocket
     *
     * @param url   请求地址
     * @param param 请求参数
     */
    public static Enqueue initWebSocket(String url, Map<String, Object> param) {
        return initWebSocket(url, param, 10);//默认重连十秒间隔
    }

    /**
     * 初始化webSocket
     *
     * @param url   请求地址
     * @param param 请求参数
     * @param time  重连间隔时间（秒）
     */
    public static Enqueue initWebSocket(String url, Map<String, Object> param, long time) {
        WebConfiguration.statebuilder().build();
        WebConfiguration.builder()
                .setUrl(url)
                .setParam(param)
                .setReconnectInterval(time)
                .build()
                .get();
        return Proxys.build(new SocketRequest()).getProxy();
    }

    /**
     * Map发送get请求
     *
     * @param url            请求地址
     * @param data           发送数据
     * @param stringCallback 结果回调
     */
    public static void sendMapGet(String url, Map<String, Object> data, StringCallback stringCallback) {
        NetHttp.Companion.builder()
                .setHosts(Uri.parse(Uri.encode(url)))
                .setMode(GET_MAP)
                .setMap(data)
                .setCallback(stringCallback)
                .build();
    }

    /**
     * json发送post请求
     *
     * @param url            请求地址
     * @param data           发送数据
     * @param stringCallback 结果回调
     */
    public static void sendJsonPost(String url, JsonObject data, StringCallback stringCallback) {
        NetHttp.Companion.builder()
                .setHosts(Uri.parse(Uri.encode(url)))
                .setMode(POST_JSON)
                .setJson(data)
                .setCallback(stringCallback)
                .build();
    }

    /**
     * map发送post请求
     *
     * @param url            请求地址
     * @param data           发送数据
     * @param stringCallback 结果回调
     */
    public static void sendMapPost(String url, Map<String, Object> data, StringCallback stringCallback) {
        NetHttp.Companion.builder()
                .setHosts(Uri.parse(Uri.encode(url)))
                .setMode(POST_MAP)
                .setMap(data)
                .setCallback(stringCallback)
                .build();
    }

    /**
     * http发送请求
     *
     * @param url           请求地址
     * @param data          发送数据
     * @param bytesCallback 结果回调
     */
    public static void sendBytes(String url, byte[] data, BytesCallback bytesCallback) {
        NetHttp.Companion.builder()
                .setHosts(Uri.parse(Uri.encode(url)))
                .setMode(POST_BYTES)
                .setBytes(data)
                .setCallback(bytesCallback)
                .build();
    }

    /**
     * http发送请求
     *
     * @param url            请求地址
     * @param data           发送数据
     * @param stringCallback 结果回调
     */
    public static void sendMapDelete(String url, Map<String, Object> data, StringCallback stringCallback) {
        NetHttp.Companion.builder()
                .setHosts(Uri.parse(Uri.encode(url)))
                .setMode(DEL_MAP)
                .setMap(data)
                .setCallback(stringCallback)
                .build();
    }

    /**
     * http发送请求
     *
     * @param url            请求地址
     * @param data           发送数据
     * @param stringCallback 结果回调
     */
    public static void sendJsonDelete(String url, JsonObject data, StringCallback stringCallback) {
        NetHttp.Companion.builder()
                .setHosts(Uri.parse(Uri.encode(url)))
                .setMode(DEL_JSON)
                .setJson(data)
                .setCallback(stringCallback)
                .build();
    }

    /**
     * http发送请求
     *
     * @param url            请求地址
     * @param data           发送数据
     * @param stringCallback 结果回调
     */
    public static void sendJsonFrom(String url, JsonObject data, StringCallback stringCallback) {
        NetHttp.Companion.builder()
                .setHosts(Uri.parse(Uri.encode(url)))
                .setMode(FROM_JSON)
                .setJson(data)
                .setCallback(stringCallback)
                .build();
    }

}
