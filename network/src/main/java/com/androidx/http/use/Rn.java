package com.androidx.http.use;

import static com.androidx.http.api.NetHttp.DEL_JSON;
import static com.androidx.http.api.NetHttp.DEL_MAP;
import static com.androidx.http.api.NetHttp.DOWNLOAD;
import static com.androidx.http.api.NetHttp.FROM_JSON;
import static com.androidx.http.api.NetHttp.GET_MAP;
import static com.androidx.http.api.NetHttp.POST_BYTES;
import static com.androidx.http.api.NetHttp.POST_JSON;
import static com.androidx.http.api.NetHttp.POST_MAP;
import static com.androidx.http.api.NetHttp.UPLOAD_JSON;
import static com.androidx.http.api.NetHttp.UPLOAD_MANY_JSON;
import static com.androidx.http.api.NetHttp.UPLOAD_MANY_MAP;
import static com.androidx.http.api.NetHttp.UPLOAD_MAP;
import static com.androidx.http.net.Configuration.ssl;

import android.util.Log;

import com.androidx.http.api.NetHttp;
import com.androidx.http.api.Re;
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
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import okhttp3.Interceptor;
import okio.Buffer;

/**
 * 请求网络框架
 * request network
 */
@SuppressWarnings({"unused", "FieldMayBeFinal"})
public final class Rn {

    private static volatile Executor executor = Executors.newWorkStealingPool();

    /**
     * 设置SSL证书
     */
    public static void setSsl(String rsaCertification, String entrustRootCertificate) {
        ssl = new Buffer()
                .writeUtf8(rsaCertification)
                .writeUtf8(entrustRootCertificate)
                .inputStream();
    }

    public static void setSsl(String cerStr) {
        ssl = new ByteArrayInputStream(cerStr.getBytes());
    }

    /**
     * 设置拦截器
     */
    public static void setInterceptor(Interceptor interceptor) {
        Configuration.interceptor = interceptor;
    }

    /**
     * 显示请求头信息
     */
    public static void show() {
        Configuration.listener = rh -> Log.i("请求头信息", rh);
    }

    /**
     * 设置重连次数 (-1~无限次 0不重连)
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

    private static <C> Response response(Response response) {
        return new Response() {
            @Override
            public void onSuccess(String data) {
                try {
                    if (response != null) response.onSuccess(data);
                } catch (Exception e) {
                    Log.e(Rn.class.getName(), Log.getStackTraceString(e));
                }
            }

            @Override
            public void onFailure(String msg) {
                Response.super.onFailure(msg);
                try {
                    if (response != null) response.onFailure(msg);
                } catch (Exception e) {
                    Log.e(Rn.class.getName(), Log.getStackTraceString(e));
                }
            }
        };
    }

    private static <C> Response response(Class<C> type, ResponseType<C> rt) {
        return new Response() {
            @Override
            public void onSuccess(String data) {
                try {
                    if (rt != null) rt.onSuccess(new Gson().fromJson(data, type));
                } catch (Exception e) {
                    Log.e(Rn.class.getName(), Log.getStackTraceString(e));
                }
            }

            @Override
            public void onFailure(String msg) {
                Response.super.onFailure(msg);
                try {
                    if (rt != null) rt.onFailure(msg);
                } catch (Exception e) {
                    Log.e(Rn.class.getName(), Log.getStackTraceString(e));
                }
            }
        };
    }

    private static <C> Response response(TypeToken<C> type, ResponseType<C> rt) {
        return new Response() {
            @Override
            public void onSuccess(String data) {
                try {
                    if (rt != null) rt.onSuccess(new Gson().fromJson(data, type.getType()));
                } catch (Exception e) {
                    Log.e(Rn.class.getName(), Log.getStackTraceString(e));
                }
            }

            @Override
            public void onFailure(String msg) {
                Response.super.onFailure(msg);
                try {
                    if (rt != null) rt.onFailure(msg);
                } catch (Exception e) {
                    Log.e(Rn.class.getName(), Log.getStackTraceString(e));
                }
            }
        };
    }

    public static Enqueue initWebSocket(String url, Map<String, Object> param) {
        return initWebSocket(url, param, 10);//默认重连十秒间隔
    }

    public static Enqueue initWebSocket(String url, Map<String, Object> param, long time) {
        return initWebSocket(url, param, time, "connect", "disconnect", "exception");//默认重连十秒间隔
    }

    /**
     * 初始化webSocket
     *
     * @param url   请求地址
     * @param param 请求参数
     * @param time  重连间隔时间（秒）
     */
    public static Enqueue initWebSocket(String url, Map<String, Object> param, long time, String connect, String disconnect, String exception) {
        if (Re.isUrl(url)) return null;
        WebConfiguration.builder()
                .setUrl(url)
                .setParam(param == null ? new WeakHashMap<>() : param)
                .setReconnectInterval(time)
                .setConnect(connect)
                .setDisconnect(disconnect)
                .setException(exception)
                .build()
                .get();
        return Proxys.build(new SocketRequest()).getProxy();
    }

    /**
     * http发送请求(protobuf)
     *
     * @param url      请求地址
     * @param param    发送参数
     * @param callback 结果回调(null不调用)
     */
    public static void sendBytes(String url, byte[] param, Callback callback) {
        if (Re.isUrl(url)) return;
        executor.execute(() -> NetHttp.builder()
                .setHosts(url)
                .setMode(POST_BYTES)
                .setBytes(param == null ? "".getBytes() : param)
                .setMaxAnewCount(Configuration.count)
                .setCallback(new Callback() {
                    @Override
                    public void onSuccess(byte[] data) {
                        try {
                            if (callback != null) callback.onSuccess(data);
                        } catch (Exception e) {
                            Log.e(Rn.class.getName(), Log.getStackTraceString(e));
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        Callback.super.onFailure(msg);
                        try {
                            if (callback != null) callback.onFailure(msg);
                        } catch (Exception e) {
                            Log.e(Rn.class.getName(), Log.getStackTraceString(e));
                        }
                    }
                })
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
        if (Re.isUrl(url)) return;
        executor.execute(() -> NetHttp.builder()
                .setHosts(url)
                .setMode(GET_MAP)
                .setMap(param == null ? new WeakHashMap<>() : param)
                .setMaxAnewCount(Configuration.count)
                .setCallback(response(response))
                .build());
    }

    /**
     * @param url   请求地址
     * @param param 发送数据
     * @param type  数据返回类型
     * @param rt    结果回调
     */
    public static <C> void sendMapGet(String url, Map<String, Object> param, Class<C> type, ResponseType<C> rt) {
        if (Re.isUrl(url)) return;
        executor.execute(() -> NetHttp.builder()
                .setHosts(url)
                .setMaxAnewCount(Configuration.count)
                .setMode(GET_MAP)
                .setMap(param == null ? new WeakHashMap<>() : param)
                .setCallback(response(type, rt))
                .build());
    }

    /**
     * @param url   请求地址
     * @param param 发送数据
     * @param type  数据返回list类型
     * @param rt    结果回调
     */
    public static <C> void sendMapGetList(String url, Map<String, Object> param, TypeToken<C> type, ResponseType<C> rt) {
        if (Re.isUrl(url)) return;
        executor.execute(() -> NetHttp.builder()
                .setHosts(url)
                .setMaxAnewCount(Configuration.count)
                .setMode(GET_MAP)
                .setMap(param == null ? new WeakHashMap<>() : param)
                .setCallback(response(type, rt))
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
        if (Re.isUrl(url)) return;
        executor.execute(() -> NetHttp.builder()
                .setHosts(url)
                .setMode(POST_JSON)
                .setJson(param == null ? new JsonObject() : param)
                .setMaxAnewCount(Configuration.count)
                .setCallback(response(response))
                .build());
    }

    /**
     * @param url   请求地址
     * @param param 发送数据
     * @param type  数据返回类型
     * @param rt    结果回调
     */
    public static <C> void sendJsonPost(String url, JsonObject param, Class<C> type, ResponseType<C> rt) {
        if (Re.isUrl(url)) return;
        executor.execute(() -> NetHttp.builder()
                .setHosts(url)
                .setMode(POST_JSON)
                .setMaxAnewCount(Configuration.count)
                .setJson(param == null ? new JsonObject() : param)
                .setCallback(response(type, rt))
                .build());
    }

    /**
     * @param url   请求地址
     * @param param 发送数据
     * @param type  数据返回list类型
     * @param rt    结果回调
     */
    public static <C> void sendJsonPostList(String url, JsonObject param, TypeToken<C> type, ResponseType<C> rt) {
        if (Re.isUrl(url)) return;
        executor.execute(() -> NetHttp.builder()
                .setHosts(url)
                .setMode(POST_JSON)
                .setMaxAnewCount(Configuration.count)
                .setJson(param == null ? new JsonObject() : param)
                .setCallback(response(type, rt))
                .build());
    }

    /**
     * map发送post请求
     *
     * @param url      请求地址
     * @param param    发送数据
     * @param response 结果回调
     */
    public static void sendMapPost(String url, Map<String, Object> param, Response response) {
        if (Re.isUrl(url)) return;
        executor.execute(() -> NetHttp.builder()
                .setHosts(url)
                .setMode(POST_MAP)
                .setMap(param == null ? new WeakHashMap<>() : param)
                .setMaxAnewCount(Configuration.count)
                .setCallback(response(response))
                .build());
    }

    /**
     * @param url   请求地址
     * @param param 发送数据
     * @param type  数据返回类型
     * @param rt    结果回调
     */
    public static <C> void sendMapPost(String url, Map<String, Object> param, Class<C> type, ResponseType<C> rt) {
        if (Re.isUrl(url)) return;
        executor.execute(() -> NetHttp.builder()
                .setHosts(url)
                .setMode(POST_MAP)
                .setMap(param == null ? new WeakHashMap<>() : param)
                .setMaxAnewCount(Configuration.count)
                .setCallback(response(type, rt))
                .build());
    }

    /**
     * @param url   请求地址
     * @param param 发送数据
     * @param type  数据返回list类型
     * @param rt    结果回调
     */
    public static <C> void sendMapPostList(String url, Map<String, Object> param, TypeToken<C> type, ResponseType<C> rt) {
        if (Re.isUrl(url)) return;
        executor.execute(() -> NetHttp.builder()
                .setHosts(url)
                .setMode(POST_MAP)
                .setMap(param == null ? new WeakHashMap<>() : param)
                .setMaxAnewCount(Configuration.count)
                .setCallback(response(type, rt))
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
        if (Re.isUrl(url)) return;
        executor.execute(() -> NetHttp.builder()
                .setHosts(url)
                .setMode(DEL_MAP)
                .setMap(param == null ? new WeakHashMap<>() : param)
                .setMaxAnewCount(Configuration.count)
                .setCallback(response(response))
                .build());
    }

    /**
     * @param url   请求地址
     * @param param 发送参数
     * @param type  数据返回类型
     * @param rt    结果回调
     */
    public static <C> void sendMapDelete(String url, Map<String, Object> param, Class<C> type, ResponseType<C> rt) {
        if (Re.isUrl(url)) return;
        executor.execute(() -> NetHttp.builder()
                .setHosts(url)
                .setMode(DEL_MAP)
                .setMaxAnewCount(Configuration.count)
                .setMap(param == null ? new WeakHashMap<>() : param)
                .setCallback(response(type, rt))
                .build());
    }

    /**
     * @param url   请求地址
     * @param param 发送数据
     * @param type  数据返回list类型
     * @param rt    结果回调
     */
    public static <C> void sendMapDeleteList(String url, Map<String, Object> param, TypeToken<C> type, ResponseType<C> rt) {
        if (Re.isUrl(url)) return;
        executor.execute(() -> NetHttp.builder()
                .setHosts(url)
                .setMode(DEL_MAP)
                .setMaxAnewCount(Configuration.count)
                .setMap(param == null ? new WeakHashMap<>() : param)
                .setCallback(response(type, rt))
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
        if (Re.isUrl(url)) return;
        executor.execute(() -> NetHttp.builder()
                .setHosts(url)
                .setMode(DEL_JSON)
                .setJson(param == null ? new JsonObject() : param)
                .setMaxAnewCount(Configuration.count)
                .setCallback(response(response))
                .build());
    }

    /**
     * @param url   请求地址
     * @param param 发送参数
     * @param type  数据返回类型
     * @param rt    结果回调
     */
    public static <C> void sendJsonDelete(String url, JsonObject param, Class<C> type, ResponseType<C> rt) {
        if (Re.isUrl(url)) return;
        executor.execute(() -> NetHttp.builder()
                .setHosts(url)
                .setMode(DEL_JSON)
                .setJson(param == null ? new JsonObject() : param)
                .setMaxAnewCount(Configuration.count)
                .setCallback(response(type, rt))
                .build());
    }

    /**
     * @param url   请求地址
     * @param param 发送数据
     * @param type  数据返回list类型
     * @param rt    结果回调
     */
    public static <C> void sendJsonDeleteList(String url, JsonObject param, TypeToken<C> type, ResponseType<C> rt) {
        if (Re.isUrl(url)) return;
        executor.execute(() -> NetHttp.builder()
                .setHosts(url)
                .setMode(DEL_JSON)
                .setJson(param == null ? new JsonObject() : param)
                .setMaxAnewCount(Configuration.count)
                .setCallback(response(type, rt))
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
        if (Re.isUrl(url)) return;
        executor.execute(() -> NetHttp.builder()
                .setJsonKey(key)
                .setHosts(url)
                .setMaxAnewCount(Configuration.count)
                .setMode(FROM_JSON)
                .setJson(param == null ? new JsonObject() : param)
                .setCallback(response(response))
                .build());
    }

    /**
     * @param url   请求地址
     * @param param 发送参数
     * @param type  数据返回类型
     * @param rt    结果回调
     */
    public static <C> void sendJsonFrom(String url, String key, JsonObject param, Class<C> type, ResponseType<C> rt) {
        if (Re.isUrl(url)) return;
        executor.execute(() -> NetHttp.builder()
                .setJsonKey(key)
                .setHosts(url)
                .setMode(FROM_JSON)
                .setMaxAnewCount(Configuration.count)
                .setJson(param == null ? new JsonObject() : param)
                .setCallback(response(type, rt))
                .build());
    }

    /**
     * @param url   请求地址
     * @param param 发送数据
     * @param type  数据返回list类型
     * @param rt    结果回调
     */
    public static <C> void sendJsonFromList(String url, String key, JsonObject param, TypeToken<C> type, ResponseType<C> rt) {
        if (Re.isUrl(url)) return;
        executor.execute(() -> NetHttp.builder()
                .setJsonKey(key)
                .setHosts(url)
                .setMode(FROM_JSON)
                .setMaxAnewCount(Configuration.count)
                .setJson(param == null ? new JsonObject() : param)
                .setCallback(response(type, rt))
                .build());
    }

    /**
     * 上传文件
     *
     * @param url  请求地址
     * @param path 文件地址
     */
    public static void sendUpload(String url, String path, Response response) {
        sendUpload(url, new JsonObject(), path, response);
    }

    public static void sendUpload(String url, Map<String, Object> param, String path, Response response) {
        sendUpload(url, param, "file", path, response);
    }

    public static <C> void sendUpload(String url, Map<String, Object> param, String path, Class<C> type, ResponseType<C> rt) {
        sendUpload(url, param, "file", path, type, rt);
    }

    public static <C> void sendUpload(String url, Map<String, Object> param, String path, TypeToken<C> type, ResponseType<C> rt) {
        sendUpload(url, param, "file", path, type, rt);
    }

    public static void sendUpload(String url, JsonObject param, String path, Response response) {
        sendUpload(url, param, "file", path, response);
    }

    public static <C> void sendUpload(String url, String path, Class<C> type, ResponseType<C> rt) {
        sendUpload(url, new JsonObject(), path, type, rt);
    }

    public static <C> void sendUpload(String url, JsonObject param, String path, Class<C> type, ResponseType<C> rt) {
        sendUpload(url, param, "file", path, type, rt);
    }

    public static <C> void sendUpload(String url, String path, TypeToken<C> type, ResponseType<C> rt) {
        sendUpload(url, new JsonObject(), path, type, rt);
    }

    public static <C> void sendUpload(String url, JsonObject param, String path, TypeToken<C> type, ResponseType<C> rt) {
        sendUpload(url, param, "file", path, type, rt);
    }

    public static void sendUpload(String url, Map<String, Object> param, String key, String path, Response response) {
        if (Re.isUrl(url)) return;
        else if (Re.isPath(path)) return;
        executor.execute(() -> NetHttp.builder()
                .setHosts(url)
                .setFile(path)
                .setMap(param == null ? new WeakHashMap<>() : param)
                .setJsonKey(key)
                .setMode(UPLOAD_MAP)
                .setMaxAnewCount(Configuration.count)
                .setCallback(response(response))
                .build());
    }

    public static <C> void sendUpload(String url, Map<String, Object> param, String key, String path, Class<C> type, ResponseType<C> rt) {
        if (Re.isUrl(url)) return;
        else if (Re.isPath(path)) return;
        executor.execute(() -> NetHttp.builder()
                .setHosts(url)
                .setFile(path)
                .setMap(param == null ? new WeakHashMap<>() : param)
                .setJsonKey(key)
                .setMode(UPLOAD_MAP)
                .setMaxAnewCount(Configuration.count)
                .setCallback(response(type, rt))
                .build());
    }

    public static <C> void sendUpload(String url, Map<String, Object> param, String key, String path, TypeToken<C> type, ResponseType<C> rt) {
        if (Re.isUrl(url)) return;
        else if (Re.isPath(path)) return;
        executor.execute(() -> NetHttp.builder()
                .setHosts(url)
                .setFile(path)
                .setMap(param == null ? new WeakHashMap<>() : param)
                .setJsonKey(key)
                .setMode(UPLOAD_MAP)
                .setMaxAnewCount(Configuration.count)
                .setCallback(response(type, rt))
                .build());
    }

    public static void sendUpload(String url, JsonObject param, String key, String path, Response response) {
        if (Re.isUrl(url)) return;
        else if (Re.isPath(path)) return;
        executor.execute(() -> NetHttp.builder()
                .setHosts(url)
                .setFile(path)
                .setJson(param == null ? new JsonObject() : param)
                .setJsonKey(key)
                .setMode(UPLOAD_JSON)
                .setMaxAnewCount(Configuration.count)
                .setCallback(response(response))
                .build());
    }

    public static <C> void sendUpload(String url, JsonObject param, String key, String path, Class<C> type, ResponseType<C> rt) {
        if (Re.isUrl(url)) return;
        else if (Re.isPath(path)) return;
        executor.execute(() -> NetHttp.builder()
                .setHosts(url)
                .setFile(path)
                .setJson(param == null ? new JsonObject() : param)
                .setJsonKey(key)
                .setMode(UPLOAD_JSON)
                .setMaxAnewCount(Configuration.count)
                .setCallback(response(type, rt))
                .build());
    }

    public static <C> void sendUpload(String url, JsonObject param, String key, String path, TypeToken<C> type, ResponseType<C> rt) {
        if (Re.isUrl(url)) return;
        else if (Re.isPath(path)) return;
        executor.execute(() -> NetHttp.builder()
                .setHosts(url)
                .setFile(path)
                .setJson(param == null ? new JsonObject() : param)
                .setJsonKey(key)
                .setMode(UPLOAD_JSON)
                .setMaxAnewCount(Configuration.count)
                .setCallback(response(type, rt))
                .build());
    }

    public static void sendUpload(String url, List<String> pathList, Response response) {
        sendUpload(url, new WeakHashMap<>(), pathList, response);
    }

    public static void sendUpload(String url, Map<String, Object> param, List<String> pathList, Response response) {
        sendUpload(url, param, "files", pathList, response);
    }

    public static <C> void sendUpload(String url, Map<String, Object> param, List<String> pathList, Class<C> type, ResponseType<C> rt) {
        sendUpload(url, param, "files", pathList, type, rt);
    }

    public static <C> void sendUpload(String url, Map<String, Object> param, List<String> pathList, TypeToken<C> type, ResponseType<C> rt) {
        sendUpload(url, param, "files", pathList, type, rt);
    }

    public static void sendUpload(String url, JsonObject param, List<String> pathList, Response response) {
        sendUpload(url, param, "files", pathList, response);
    }

    public static <C> void sendUpload(String url, List<String> pathList, Class<C> type, ResponseType<C> rt) {
        sendUpload(url, new JsonObject(), pathList, type, rt);
    }

    public static <C> void sendUpload(String url, JsonObject param, List<String> pathList, Class<C> type, ResponseType<C> rt) {
        sendUpload(url, param, "files", pathList, type, rt);
    }

    public static <C> void sendUpload(String url, List<String> pathList, TypeToken<C> type, ResponseType<C> rt) {
        sendUpload(url, new JsonObject(), pathList, type, rt);
    }

    public static <C> void sendUpload(String url, JsonObject param, List<String> pathList, TypeToken<C> type, ResponseType<C> rt) {
        sendUpload(url, param, "files", pathList, type, rt);
    }

    /**
     * 上传多个文件
     *
     * @param url      请求地址
     * @param param    参数
     * @param key      文件参数key
     * @param pathList 文件地址集合
     * @param response 数据回调
     */
    public static void sendUpload(String url, Map<String, Object> param, String key, List<String> pathList, Response response) {
        if (Re.isUrl(url)) return;
        if (Re.isPaths(pathList)) return;
        executor.execute(() -> NetHttp.builder()
                .setHosts(url)
                .setFilePathList(pathList)
                .setMap(param == null ? new WeakHashMap<>() : param)
                .setJsonKey(key)
                .setMode(UPLOAD_MANY_MAP)
                .setMaxAnewCount(Configuration.count)
                .setCallback(response(response))
                .build());
    }

    public static <C> void sendUpload(String url, Map<String, Object> param, String key, List<String> pathList, Class<C> type, ResponseType<C> rt) {
        if (Re.isUrl(url)) return;
        if (Re.isPaths(pathList)) return;
        executor.execute(() -> NetHttp.builder()
                .setHosts(url)
                .setFilePathList(pathList)
                .setMap(param == null ? new WeakHashMap<>() : param)
                .setJsonKey(key)
                .setMode(UPLOAD_MANY_MAP)
                .setMaxAnewCount(Configuration.count)
                .setCallback(response(type, rt))
                .build());
    }

    public static <C> void sendUpload(String url, Map<String, Object> param, String key, List<String> pathList, TypeToken<C> type, ResponseType<C> rt) {
        if (Re.isUrl(url)) return;
        if (Re.isPaths(pathList)) return;
        executor.execute(() -> NetHttp.builder()
                .setHosts(url)
                .setFilePathList(pathList)
                .setMap(param == null ? new WeakHashMap<>() : param)
                .setJsonKey(key)
                .setMode(UPLOAD_MANY_MAP)
                .setMaxAnewCount(Configuration.count)
                .setCallback(response(type, rt))
                .build());
    }

    public static void sendUpload(String url, JsonObject param, String key, List<String> pathList, Response response) {
        if (Re.isUrl(url)) return;
        if (Re.isPaths(pathList)) return;
        executor.execute(() -> NetHttp.builder()
                .setHosts(url)
                .setFilePathList(pathList)
                .setJson(param == null ? new JsonObject() : param)
                .setJsonKey(key)
                .setMode(UPLOAD_MANY_JSON)
                .setMaxAnewCount(Configuration.count)
                .setCallback(response(response))
                .build());
    }

    public static <C> void sendUpload(String url, JsonObject param, String key, List<String> pathList, Class<C> type, ResponseType<C> rt) {
        if (Re.isUrl(url)) return;
        if (Re.isPaths(pathList)) return;
        executor.execute(() -> NetHttp.builder()
                .setHosts(url)
                .setFilePathList(pathList)
                .setJson(param == null ? new JsonObject() : param)
                .setJsonKey(key)
                .setMode(UPLOAD_MANY_JSON)
                .setMaxAnewCount(Configuration.count)
                .setCallback(response(type, rt))
                .build());
    }

    public static <C> void sendUpload(String url, JsonObject param, String key, List<String> pathList, TypeToken<C> type, ResponseType<C> rt) {
        if (Re.isUrl(url)) return;
        if (Re.isPaths(pathList)) return;
        executor.execute(() -> NetHttp.builder()
                .setHosts(url)
                .setFilePathList(pathList)
                .setJson(param == null ? new JsonObject() : param)
                .setJsonKey(key)
                .setMode(UPLOAD_MANY_JSON)
                .setMaxAnewCount(Configuration.count)
                .setCallback(response(type, rt))
                .build());
    }

    /**
     * 下载文件
     *
     * @param url     请求地址
     * @param outPath 文件保存地址
     */
    public static void sendDownload(String url, String outPath, DownloadListener listener) {
        if (Re.isUrl(url)) return;
        else if (Re.isPath(outPath)) return;
        executor.execute(() -> NetHttp.builder()
                .setHosts(url)
                .setMode(DOWNLOAD)
                .setMaxAnewCount(Configuration.count)
                .setFile(outPath)
                .setListener((file, duration) -> {
                    try {
                        if (listener != null) listener.finish(file, duration);
                    } catch (Exception e) {
                        Log.e(Rn.class.getName(), Log.getStackTraceString(e));
                    }
                })
                .build());
    }

}
