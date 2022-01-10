package com.androidx.http.net;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public final class OkHttpInterceptor implements Interceptor {

    private OkHttpInterceptor() {
    }

    protected static OkHttpInterceptor getInstance(){
        return SingletonHolder.newInstance();
    }

    private static final class SingletonHolder {

        private static volatile OkHttpInterceptor okHttpInterceptor;

        private SingletonHolder() {
        }

        private static final OkHttpInterceptor INSTANCE = new OkHttpInterceptor();

        private static OkHttpInterceptor newInstance() {
            if (okHttpInterceptor == null) {
                synchronized (OkHttpInterceptor.class) {
                    if (okHttpInterceptor == null) {
                        okHttpInterceptor = INSTANCE;
                    }
                }
            }
            return okHttpInterceptor;
        }
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        //请求
        Request request = chain.request();
        Request.Builder rb = request.newBuilder();
        rb.addHeader("Connection", "Upgrade, HTTP2-Settings")
                .addHeader("Upgrade", "h2c");
        //响应
        return chain.proceed(rb.build());
    }
}
