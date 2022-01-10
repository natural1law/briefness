package com.androidx.http.use;

import static com.androidx.http.api.NetHttp.DEL_JSON;
import static com.androidx.http.api.NetHttp.DEL_MAP;
import static com.androidx.http.api.NetHttp.DOWNLOAD;
import static com.androidx.http.api.NetHttp.FROM_JSON;
import static com.androidx.http.api.NetHttp.GET_MAP;
import static com.androidx.http.api.NetHttp.POST_BYTES;
import static com.androidx.http.api.NetHttp.POST_JSON;
import static com.androidx.http.api.NetHttp.POST_MAP;
import static com.androidx.http.api.NetHttp.UPLOAD;
import static com.androidx.http.net.Configuration.ssl;

import android.util.Log;

import com.androidx.http.api.NetHttp;
import com.androidx.http.net.Configuration;
import com.androidx.http.net.listener.Callback;
import com.androidx.http.net.listener.DownloadListener;
import com.androidx.http.net.listener.Enqueue;
import com.androidx.http.net.listener.Response;
import com.androidx.http.net.listener.ResponseType;
import com.androidx.http.net.socket.Proxys;
import com.androidx.http.net.socket.SocketRequest;
import com.androidx.http.net.socket.WebConfiguration;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 请求网络
 * request network
 */
@SuppressWarnings({"unused", "FieldMayBeFinal"})
public final class Rn {

    private static volatile Executor executor = Executors.newWorkStealingPool();

    /**
     * 设置SSL证书
     */
    public static void setSsl(String certification) {
        ssl = certification;
    }

    /**
     * 显示请求头信息
     */
    public static void show() {
        Configuration.listener = rh -> Log.i("请求头信息", rh);
    }

    /**
     * 设置重连次数
     */
    public static void setAnewCount(int count) {
        Configuration.count = count;
    }

    /**
     * @param timeout 超时时间
     */
    public static void setTimeout(int timeout) {
        Configuration.timeout = timeout;
    }

    /**
     * @param interval ping帧心跳间隔
     */
    public static void setInterval(int interval) {
        Configuration.interval = interval;
    }

    /**
     * @param maxConnCount 最大连接池
     */
    public static void setMaxConnCount(int maxConnCount) {
        Configuration.maxConnCount = maxConnCount;
    }

    /**
     * @param alive 连接池的连接活跃时间（默认设置半小时）
     */
    public static void setAlive(int alive) {
        Configuration.alive = alive;
    }

    /**
     * @param compress 0-所有消息压缩(默认1024)
     */
    public static void setCompress(int compress) {
        Configuration.compress = compress;
    }

    /**
     * @param reconnection 异常重新连接
     */
    public static void setReconnection(boolean reconnection) {
        Configuration.reconnection = reconnection;
    }

    /**
     * 设置状态
     *
     * @param connect    成功状态key
     * @param disconnect 连接状态key
     * @param exception  断开状态key
     */
    public static void setResState(String connect, String disconnect, String exception) {
        executor.execute(() -> WebConfiguration.statebuilder()
                .setConnect(connect)
                .setDisconnect(disconnect)
                .setException(exception)
                .build());
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
     * http发送请求(protobuf)
     *
     * @param url      请求地址
     * @param param    发送参数
     * @param callback 结果回调
     */
    public static void sendBytes(String url, byte[] param, Callback callback) {
        executor.execute(() -> NetHttp.builder()
                .setHosts(url)
                .setMode(POST_BYTES)
                .setBytes(param)
                .setMaxAnewCount(Configuration.count)
                .setCallback(callback)
                .build());
    }

    /**
     * Map发送get请求
     *
     * @param url      请求地址
     * @param param    发送参数
     * @param response 结果回调
     */
    public static void sendMapGet(String url, Map<String, Object> param, Response response) {
        executor.execute(() -> NetHttp.builder()
                .setHosts(url)
                .setMode(GET_MAP)
                .setMap(param)
                .setMaxAnewCount(Configuration.count)
                .setCallback(response)
                .build());
    }

    /**
     * @param url   请求地址
     * @param param 发送数据
     * @param type  数据返回类型
     * @param rt    结果回调
     */
    public static <C> void sendMapGet(String url, Map<String, Object> param, Class<C> type, ResponseType<C> rt) {
        executor.execute(() -> NetHttp.builder()
                .setHosts(url)
                .setMaxAnewCount(Configuration.count)
                .setMode(GET_MAP)
                .setMap(param)
                .setCallback((Response) data -> rt.onSuccess(new Gson().fromJson(data, type)))
                .build());
    }

    /**
     * json发送post请求
     *
     * @param url      请求地址
     * @param param    发送参数
     * @param response 结果回调
     */
    public static void sendJsonPost(String url, JsonObject param, Response response) {
        executor.execute(() -> NetHttp.builder()
                .setHosts(url)
                .setMode(POST_JSON)
                .setJson(param)
                .setMaxAnewCount(Configuration.count)
                .setCallback(response)
                .build());
    }

    /**
     * @param url   请求地址
     * @param param 发送数据
     * @param type  数据返回类型
     * @param rt    结果回调
     */
    public static <C> void sendJsonPost(String url, JsonObject param, Class<C> type, ResponseType<C> rt) {
        executor.execute(() -> NetHttp.builder()
                .setHosts(url)
                .setMode(POST_JSON)
                .setMaxAnewCount(Configuration.count)
                .setJson(param)
                .setCallback((Response) data -> rt.onSuccess(new Gson().fromJson(data, type)))
                .build());
    }

    /**
     * map发送post请求
     *
     * @param url      请求地址
     * @param data     发送数据
     * @param response 结果回调
     */
    public static void sendMapPost(String url, Map<String, Object> data, Response response) {
        executor.execute(() -> NetHttp.builder()
                .setHosts(url)
                .setMode(POST_MAP)
                .setMap(data)
                .setCallback(response)
                .setMaxAnewCount(Configuration.count)
                .build());
    }

    /**
     * @param url   请求地址
     * @param param 发送数据
     * @param type  数据返回类型
     * @param rt    结果回调
     */
    public static <C> void sendMapPost(String url, Map<String, Object> param, Class<C> type, ResponseType<C> rt) {
        executor.execute(() -> NetHttp.builder()
                .setHosts(url)
                .setMode(POST_MAP)
                .setMap(param)
                .setMaxAnewCount(Configuration.count)
                .setCallback((Response) data -> rt.onSuccess(new Gson().fromJson(data, type)))
                .build());
    }

    /**
     * http发送请求
     *
     * @param url      请求地址
     * @param param    发送参数
     * @param response 结果回调
     */
    public static void sendMapDelete(String url, Map<String, Object> param, Response response) {
        executor.execute(() -> NetHttp.builder()
                .setHosts(url)
                .setMode(DEL_MAP)
                .setMap(param)
                .setCallback(response)
                .setMaxAnewCount(Configuration.count)
                .build());
    }

    /**
     * @param url   请求地址
     * @param param 发送参数
     * @param type  数据返回类型
     * @param rt    结果回调
     */
    public static <C> void sendMapDelete(String url, Map<String, Object> param, Class<C> type, ResponseType<C> rt) {
        executor.execute(() -> NetHttp.builder()
                .setHosts(url)
                .setMode(DEL_MAP)
                .setMaxAnewCount(Configuration.count)
                .setMap(param)
                .setCallback((Response) data -> rt.onSuccess(new Gson().fromJson(data, type)))
                .build());
    }

    /**
     * http发送请求
     *
     * @param url      请求地址
     * @param param    发送参数
     * @param response 结果回调
     */
    public static void sendJsonDelete(String url, JsonObject param, Response response) {
        executor.execute(() -> NetHttp.builder()
                .setHosts(url)
                .setMode(DEL_JSON)
                .setJson(param)
                .setMaxAnewCount(Configuration.count)
                .setCallback(response)
                .build());
    }

    /**
     * @param url   请求地址
     * @param param 发送参数
     * @param type  数据返回类型
     * @param rt    结果回调
     */
    public static <C> void sendJsonDelete(String url, JsonObject param, Class<C> type, ResponseType<C> rt) {
        executor.execute(() -> NetHttp.builder()
                .setHosts(url)
                .setMode(DEL_JSON)
                .setJson(param)
                .setMaxAnewCount(Configuration.count)
                .setCallback((Response) data -> rt.onSuccess(new Gson().fromJson(data, type)))
                .build());
    }

    /**
     * http发送请求
     *
     * @param url      请求地址
     * @param param    发送参数
     * @param response 结果回调
     */
    public static void sendJsonFrom(String url, String key, JsonObject param, Response response) {
        executor.execute(() -> NetHttp.builder()
                .setJsonKey(key)
                .setHosts(url)
                .setMaxAnewCount(Configuration.count)
                .setMode(FROM_JSON)
                .setJson(param)
                .setCallback(response)
                .build());
    }

    /**
     * @param url   请求地址
     * @param param 发送参数
     * @param type  数据返回类型
     * @param rt    结果回调
     */
    public static <C> void sendJsonFrom(String url, String key, JsonObject param, Class<C> type, ResponseType<C> rt) {
        executor.execute(() -> NetHttp.builder()
                .setJsonKey(key)
                .setHosts(url)
                .setMode(FROM_JSON)
                .setMaxAnewCount(Configuration.count)
                .setJson(param)
                .setCallback((Response) data -> rt.onSuccess(new Gson().fromJson(data, type)))
                .build());
    }

    /**
     * 上传文件
     *
     * @param url  请求地址
     * @param path 文件地址
     */
    public static void sendUpload(String url, String path, Response response) {
        sendUpload(url, "file", path, response);
    }

    public static void sendUpload(String url, String key, String path, Response response) {
        executor.execute(() -> NetHttp.builder()
                .setHosts(url)
                .setFile(path)
                .setJsonKey(key)
                .setMode(UPLOAD)
                .setMaxAnewCount(Configuration.count)
                .setCallback(response)
                .build());
    }

    /**
     * 下载文件
     *
     * @param url     请求地址
     * @param outPath 文件保存地址
     */
    public static void sendDownload(String url, String outPath, DownloadListener listener) {
        executor.execute(() -> NetHttp.builder()
                .setHosts(url)
                .setMode(DOWNLOAD)
                .setMaxAnewCount(Configuration.count)
                .setFile(outPath)
                .setListener(listener)
                .build());
    }

}
