package com.androidx.http.net;

import com.google.gson.JsonObject;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLSocketFactory;

import okhttp3.ConnectionPool;
import okhttp3.ConnectionSpec;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.TlsVersion;

public final class HttpNetwork implements IHttpNetwork {

    private static final MediaType JSON = MediaType.parse("application/json;charset=utf-8");
    private static final MediaType PROTO = MediaType.parse("application/x-protobuf");
    private static final int TIMEOUT = 180;// 超时时间
    private static final int INTERVAL = 15;// ping帧心跳间隔
    private static final int MAX_CONN_COUNT = 1024;// 最大连接池
    private static final int ALIVE = 30;// 接池的连接活跃时间（默认设置半小时）
    private final StringBuffer param = new StringBuffer();

    private final Request.Builder request;

    private HttpNetwork() {
        request = new Request.Builder();
    }

    @Contract(pure = true)
    public static HttpNetwork builder() {
        return Singleton.getInstance();
    }

    @Override
    public @NotNull
    OkHttpClient getClient() {
        String cert = Configuration.getSsl();
        SSLSocketFactory ssl = TrustManagers.createSSLSocketFactory();
        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .dns(new HttpDns())
                .retryOnConnectionFailure(true)//连接重连
                .minWebSocketMessageToCompress(0)//压缩消息
                .pingInterval(INTERVAL, TimeUnit.SECONDS)//ping帧心跳
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)//设置超时时间(单位秒)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间(单位秒)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)//设置写入超时时间(单位秒)
                .addNetworkInterceptor(OkHttpInterceptor.getInstance())//网络拦截器
                .protocols(Arrays.asList(Protocol.HTTP_2, Protocol.HTTP_1_1))
                .connectionPool(new ConnectionPool(MAX_CONN_COUNT, ALIVE, TimeUnit.MINUTES))//创建连接池
                .hostnameVerifier((hostname, session) -> hostname.equalsIgnoreCase(session.getPeerHost()));//IP主机校验
        if (ssl != null) client.sslSocketFactory(ssl, TrustManagers.getInstance());//内置证书校验
        ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.COMPATIBLE_TLS)
                .tlsVersions(TlsVersion.TLS_1_3, TlsVersion.TLS_1_2, TlsVersion.TLS_1_1, TlsVersion.TLS_1_0)
                .allEnabledCipherSuites()
                .build();
        // 兼容http接口
        ConnectionSpec spec1 = new ConnectionSpec.Builder(ConnectionSpec.CLEARTEXT).build();
        client.connectionSpecs(Arrays.asList(spec, spec1));
        if (!cert.equals("")) TrustManagers.setCertificate(client, cert);
        return client.build();
    }

    /**
     * 异步/发起get请求(map接收方式)
     */
    @Override
    public @NotNull
    Request getRequest(String url, Map<String, Object> map) {
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
        return request.get().url(url + param).build();
    }

    /**
     * 异步/发起post请求(map接收方式)
     */
    @Override
    public @NotNull
    Request postRequest(String url, Map<String, Object> map) {
        FormBody.Builder formBody = new FormBody.Builder();
        map.forEach((key, value) -> formBody.addEncoded(key, String.valueOf(value)));
        return request.post(formBody.build()).url(url).build();
    }

    /**
     * 异步/发起post请求(json接收方式)
     */
    @Override
    public @NotNull
    Request postRequest(String url, JsonObject json) {
        return request.post(RequestBody.create(json.toString(), JSON)).url(url).build();
    }

    /**
     * 异步/发起delete请求(map接收方式)
     */
    @Override
    public @NotNull
    Request deleteRequest(String url, Map<String, Object> map) {
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
        return request.delete().url(url + param).build();
    }

    /**
     * 异步/发起delete请求(json接收方式)
     */
    @Override
    public @NotNull
    Request deleteRequest(String url, JsonObject json) {
        return request.delete(RequestBody.create(json.toString(), JSON)).url(url).build();
    }

    /**
     * 异步/发起post请求(json接收方式)
     * 表单形式
     */
    @Override
    public @NotNull
    Request forrequest(String url, String key, JsonObject json) {
        return request.post(new FormBody.Builder().addEncoded(key, json.toString()).build()).url(url).build();
    }

    /**
     * protobuf 数据传输
     */
    @Override
    public @NotNull
    Request postRequestProto(String url, byte[] bytes) {
        return request.post(RequestBody.create(bytes, PROTO)).url(url).build();
    }

    private static final class Singleton {
        private Singleton() {
        }

        private static volatile HttpNetwork instance;

        private static final HttpNetwork INSTANCE = new HttpNetwork();

        private static HttpNetwork getInstance(){
            if (instance== null) synchronized (HttpNetwork.class){
                if (instance == null) return instance = INSTANCE;
            }
            return instance;
        }
    }
}
