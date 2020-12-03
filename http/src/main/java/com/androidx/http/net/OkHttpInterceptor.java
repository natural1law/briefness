package com.androidx.http.net;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public final class OkHttpInterceptor implements Interceptor {

    private static volatile OkHttpInterceptor okHttpInterceptor;

    private OkHttpInterceptor() {
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
        requestBuilder.addHeader("Content-Type", "application/x-protobuf");
        Log.e("请求URL", request.url().toString());
        Log.e("请求头", request.headers().toString());
        //响应
        Response response = chain.proceed(requestBuilder.build());
        Log.e("响应头", response.headers().toString());
        return response;
    }
}
