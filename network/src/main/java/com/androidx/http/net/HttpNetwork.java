package com.androidx.http.net;

import static com.androidx.http.net.Configuration.alive;
import static com.androidx.http.net.Configuration.compress;
import static com.androidx.http.net.Configuration.interceptor;
import static com.androidx.http.net.Configuration.interval;
import static com.androidx.http.net.Configuration.listener;
import static com.androidx.http.net.Configuration.maxConnCount;
import static com.androidx.http.net.Configuration.reconnection;
import static com.androidx.http.net.Configuration.ssl;
import static com.androidx.http.net.Configuration.timeout;

import androidx.annotation.NonNull;

import com.androidx.http.net.listener.DownloadListener;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.CipherSuite;
import okhttp3.ConnectionPool;
import okhttp3.ConnectionSpec;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.TlsVersion;

public final class HttpNetwork implements HttpNetworkListener {

    private static final MediaType FILE = MediaType.parse("application/octet-stream");
    private static final MediaType JSON = MediaType.parse("application/json;charset=utf-8");
    private static final MediaType PROTO = MediaType.parse("application/x-protobuf");
    private final StringBuffer param = new StringBuffer();

    private final Request.Builder request;

    private HttpNetwork() {
        request = new Request.Builder();
    }

    @Contract(pure = true)
    public static HttpNetwork builder() {
        return Singleton.getInstance();
    }

    public OkHttpClient getClient() {
        return client().build();
    }

    private OkHttpClient.Builder client() {
        ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                .tlsVersions(TlsVersion.TLS_1_3, TlsVersion.TLS_1_2)
                .cipherSuites(
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256)
                .allEnabledCipherSuites()
                .build();
        // 兼容http接口
        ConnectionSpec spec1 = new ConnectionSpec.Builder(ConnectionSpec.COMPATIBLE_TLS).build();
        ConnectionSpec spec2 = new ConnectionSpec.Builder(ConnectionSpec.CLEARTEXT).build();
        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .dns(new HttpDns())
                .retryOnConnectionFailure(reconnection)//连接重连
                .minWebSocketMessageToCompress(compress)//压缩消息
                .pingInterval(interval, TimeUnit.SECONDS)//ping帧心跳
                .connectTimeout(timeout, TimeUnit.SECONDS)//设置超时时间(单位秒)
                .readTimeout(timeout, TimeUnit.SECONDS)//设置读取超时时间(单位秒)
                .writeTimeout(timeout, TimeUnit.SECONDS)//设置写入超时时间(单位秒)
                .addInterceptor(interceptor == null ? OkHttpInterceptor.getInstance() : interceptor)//拦截器
                .addNetworkInterceptor(chain -> {//网络拦截器
                    Request req = chain.request().newBuilder().build();
                    if (listener != null) listener.value(req.toString());
                    return chain.proceed(req);
                })
                .connectionSpecs(Arrays.asList(spec, spec1, spec2))
                .protocols(Arrays.asList(Protocol.HTTP_2, Protocol.HTTP_1_1))
                .connectionPool(new ConnectionPool(maxConnCount, alive, TimeUnit.MINUTES))//创建连接池
                .hostnameVerifier((hostname, session) -> hostname.equalsIgnoreCase(session.getPeerHost()));//IP主机校验
        if (ssl != null) TrustManagers.createSSL(client, ssl);
        return client;
    }

    @NonNull
    @Override
    public Call getCall(@NonNull Request request) {
        return client().build().newCall(request);
    }

    /**
     * 异步/发起get请求(map接收方式)
     */
    @Override
    public @NotNull Call getRequest(@NonNull String url, Map<String, ?> map) {
        final int[] p = {0};
        param.setLength(p[0]);
        map.forEach((k, v) -> {
            if (p[0] == 0) param.append("?");
            else param.append("&");
            param.append(k).append("=").append(map.get(k));
            p[0]++;
        });
        return client().build().newCall(request.get().url(url + param).build());
    }

    /**
     * 异步/发起post请求(map接收方式)
     */
    @Override
    public @NotNull Call postRequest(@NonNull String url, Map<String, ?> map) {
        FormBody.Builder formBody = new FormBody.Builder();
        map.forEach((key, value) -> formBody.addEncoded(key, String.valueOf(value)));
        return client().build().newCall(request.post(formBody.build()).url(url).build());
    }

    /**
     * 异步/发起post请求(json接收方式)
     */
    @Override
    public @NotNull Call postRequest(@NonNull String url, JsonObject json) {
        return client().build().newCall(request.post(RequestBody.create(json.toString(), JSON)).url(url).build());
    }

    /**
     * 异步/发起delete请求(map接收方式)
     */
    @Override
    public @NotNull Call deleteRequest(@NonNull String url, Map<String, ?> map) {
        final int[] p = {0};
        param.setLength(p[0]);
        map.forEach((key, o) -> {
            if (p[0] == 0) param.append("?");
            else param.append("&");
            param.append(key).append("=").append(map.get(key));
            p[0]++;
        });
        return client().build().newCall(request.delete().url(url + param).build());
    }

    /**
     * 异步/发起delete请求(json接收方式)
     */
    @Override
    public @NotNull Call deleteRequest(@NonNull String url, JsonObject json) {
        return client().build().newCall(request.delete(RequestBody.create(json.toString(), JSON)).url(url).build());
    }

    /**
     * 异步/发起post请求(json接收方式)
     * 表单形式
     */
    @Override
    public @NotNull Call forrequest(@NonNull String url, @NonNull String key, JsonObject json) {
        return client().build().newCall(request.post(new FormBody.Builder().addEncoded(key, json.toString()).build()).url(url).build());
    }

    /**
     * protobuf 数据传输
     */
    @Override
    public @NotNull Call postRequestProto(@NonNull String url, @NonNull byte[] bytes) {
        return client().build().newCall(request.post(RequestBody.create(bytes, PROTO)).url(url).build());
    }

    /**
     * 上传文件
     *
     * @param url  上传地址
     * @param path 文件
     */
    @NonNull
    @Override
    public Call uploadRequest(@NonNull String url, @NonNull Map<String, ?> map, @NonNull String key, @NonNull String path) {
        File file;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            file = Paths.get(path).toFile();
        } else file = new File(path);
        MultipartBody.Builder mb = new MultipartBody.Builder();
        RequestBody rb = RequestBody.create(file, FILE);
        mb.setType(MultipartBody.FORM).addFormDataPart(key, file.getName(), rb);
        map.forEach((k, v) -> mb.addFormDataPart(k, String.valueOf(v)));
        return client().build().newCall(request.post(mb.build()).url(url).build());
    }

    /**
     * 多文件上传
     */
    @NonNull
    @Override
    public Call uploadRequest(@NonNull String url, @NonNull Map<String, ?> map, @NonNull String key, @NonNull List<String> pathList) {
        List<File> files = new ArrayList<>();
        pathList.forEach(path -> {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                files.add(Paths.get(path).toFile());
            } else files.add(new File(path));
        });
        MultipartBody.Builder mb = new MultipartBody.Builder();
        files.forEach(file -> {
            mb.setType(MultipartBody.FORM).addFormDataPart(key, file.getName(), RequestBody.create(file, FILE));
            map.forEach((k, v) -> mb.addFormDataPart(k, String.valueOf(v)));
        });
        return client().build().newCall(request.post(mb.build()).url(url).build());
    }

    /**
     * 下载文件
     *
     * @param url 下载地址
     */
    @NonNull
    @Override
    public Call downloadRequest(@NonNull String url, @NonNull DownloadListener downloadListener) {
        OkHttpClient.Builder client = client().addInterceptor(chain -> {
            Response response = chain.proceed(chain.request());
            return response.newBuilder()
                    .body(new ProgressResponseBody(response.body(), downloadListener))
                    .build();
        });
        return client.build().newCall(request.url(url).get().build());
    }

    private static final class Singleton {
        private Singleton() {
        }

        private static volatile HttpNetwork instance;

        private static final HttpNetwork INSTANCE = new HttpNetwork();

        private static HttpNetwork getInstance() {
            if (instance == null) synchronized (HttpNetwork.class) {
                if (instance == null) return instance = INSTANCE;
            }
            return instance;
        }
    }
}
