package com.androidx.http.net;

import android.net.Uri;

import com.google.gson.JsonObject;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
import okhttp3.ConnectionSpec;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.TlsVersion;

public final class HttpNetwork implements IHttpNetwork {

    private static final MediaType JSON = MediaType.parse("application/json;charset=utf-8");
    private static final MediaType PROTO = MediaType.parse("application/x-protobuf");
    private static final int TIMEOUT = 180;// 超时时间
    private static final int INTERVAL = 10;// ping帧心跳间隔
    private static final int MAX_CONN_COUNT = 50;// 最大连接池
    private static final int ALIVE = 30;// 接池的连接活跃时间（默认设置半小时）
    private final StringBuffer param = new StringBuffer();
    private static final HttpNetwork instance = Singleton.INSTANCE;
    private final Request.Builder request;

    private HttpNetwork() {
        request = new Request.Builder();
    }

    @Contract(pure = true)
    public static HttpNetwork builder() {
        return instance;
    }

    @Override
    public @NotNull
    OkHttpClient getClient() {
        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .dns(new HttpDns())
                .retryOnConnectionFailure(true)//错误重连
                .minWebSocketMessageToCompress(0)//压缩消息
                .pingInterval(INTERVAL, TimeUnit.SECONDS)//ping帧心跳
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)//设置超时时间(单位秒)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间(单位秒)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)//设置写入超时时间(单位秒)
                .addNetworkInterceptor(OkHttpInterceptor.newInstance())//网络拦截器
                .connectionPool(new ConnectionPool(MAX_CONN_COUNT, ALIVE, TimeUnit.MINUTES))//创建连接池
                .hostnameVerifier((hostname, session) -> hostname.equalsIgnoreCase(session.getPeerHost()))//IP主机校验
                .sslSocketFactory(Objects.requireNonNull(TrustManagers.newInstance().createSSLSocketFactory()), TrustManagers.newInstance());//内置证书校验
        ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.COMPATIBLE_TLS)
                .tlsVersions(TlsVersion.TLS_1_3, TlsVersion.TLS_1_2, TlsVersion.TLS_1_1, TlsVersion.TLS_1_0)
                .allEnabledCipherSuites()
                .build();
        // 兼容http接口
        ConnectionSpec spec1 = new ConnectionSpec.Builder(ConnectionSpec.CLEARTEXT).build();
        client.connectionSpecs(Arrays.asList(spec, spec1));
        return client.build();
    }

    /**
     * 异步/发起get请求(map接收方式)
     */
    @Override
    public @NotNull
    Request getRequest(Uri uri, Map<String, Object> map) throws MalformedURLException {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            final int[] p = {0};
            param.setLength(p[0]);
            map.forEach((key, o) -> {
                if (p[0] == 0) {
                    param.append("?");
                } else {
                    param.append("&");
                }
                param.append(key).append("=").append(map.get(key));
                p[0]++;
            });
        } else {
            int i = 0;
            param.setLength(i);
            for (String key : map.keySet()) {
                if (i == 0) {
                    param.append("?");
                } else {
                    param.append("&");
                }
                param.append(key).append("=").append(map.get(key));
                i++;
            }
        }
        return request.get()
                .url(new URL(Uri.decode(uri.toString()) + param))
                .build();
    }

    /**
     * 异步/发起post请求(map接收方式)
     */
    @Override
    public @NotNull
    Request postRequest(Uri uri, Map<String, Object> map) throws MalformedURLException {
        FormBody.Builder formBody = new FormBody.Builder();
        for (String key : map.keySet()) {
            formBody.add(key, String.valueOf(map.get(key)));
        }
        return request.post(formBody.build())
                .url(new URL(Uri.decode(uri.toString())))
                .build();
    }

    /**
     * 异步/发起post请求(json接收方式)
     */
    @Override
    public @NotNull
    Request postRequest(Uri uri, JsonObject json) throws MalformedURLException {
        return request.post(RequestBody.create(json.toString(), JSON))
                .url(new URL(Uri.decode(uri.toString())))
                .build();
    }

    /**
     * 异步/发起delete请求(map接收方式)
     */
    @Override
    public @NotNull
    Request deleteRequest(Uri uri, Map<String, Object> map) throws MalformedURLException {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            final int[] p = {0};
            param.setLength(p[0]);
            map.forEach((key, o) -> {
                if (p[0] == 0) {
                    param.append("?");
                } else {
                    param.append("&");
                }
                param.append(key).append("=").append(map.get(key));
                p[0]++;
            });
        } else {
            int i = 0;
            param.setLength(i);
            for (String key : map.keySet()) {
                if (i == 0) {
                    param.append("?");
                } else {
                    param.append("&");
                }
                param.append(key).append("=").append(map.get(key));
                i++;
            }
        }
        return request.delete()
                .url(new URL(Uri.decode(uri.toString()) + param))
                .build();
    }

    /**
     * 异步/发起delete请求(json接收方式)
     */
    @Override
    public @NotNull
    Request deleteRequest(Uri uri, JsonObject json) throws MalformedURLException {
        return request.delete(RequestBody.create(json.toString(), null))
                .url(new URL(Uri.decode(uri.toString())))
                .build();
    }

    /**
     * 异步/发起post请求(json接收方式)
     * 表单形式
     */
    @Override
    public @NotNull
    Request formRequest(Uri uri, String key, JsonObject json) throws MalformedURLException {
        return request.post(new FormBody.Builder().add(key, json.toString()).build())
                .url(new URL(Uri.decode(uri.toString())))
                .build();
    }

    /**
     * protobuf 数据传输
     */
    @Override
    public @NotNull
    Request postRequestProto(Uri uri, byte[] bytes) throws MalformedURLException {
        return request.post(RequestBody.create(bytes, PROTO))
                .url(new URL(Uri.decode(uri.toString())))
                .build();
    }

    private static final class Singleton {
        private Singleton() {
        }

        private static final HttpNetwork INSTANCE = new HttpNetwork();
    }
}
