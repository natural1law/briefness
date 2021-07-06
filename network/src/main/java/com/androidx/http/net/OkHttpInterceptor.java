package com.androidx.http.net;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public final class OkHttpInterceptor implements Interceptor {

    private static volatile OkHttpInterceptor okHttpInterceptor;
    private final Map<String, String> headers = new ConcurrentHashMap<>();

    private OkHttpInterceptor() {
        headers.clear();
        headers.putAll(Configuration.getHeaders());
    }

    private static final class SingletonHolder {
        private SingletonHolder() {
        }

        private static final OkHttpInterceptor INSTANCE = new OkHttpInterceptor();
    }

    @SuppressWarnings("WeakerAccess")
    protected static OkHttpInterceptor newInstance() {
        if (okHttpInterceptor == null) {
            synchronized (OkHttpInterceptor.class) {
                if (okHttpInterceptor == null) {
                    okHttpInterceptor = SingletonHolder.INSTANCE;
                }
            }
        }
        return okHttpInterceptor;
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        //请求
        Request request = chain.request();
        Request.Builder requestBuilder = request.newBuilder();
        headers.forEach(requestBuilder::addHeader);
        //响应
        return chain.proceed(requestBuilder.build());
    }
}
