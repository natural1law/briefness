package com.androidx.http.net.socket;

import android.net.Uri;

import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.Request;

/**
 * @date 2021/07/06
 */
public class WebConfiguration {

    private static final StringBuffer PARAM = new StringBuffer();
    private static final Map<String, Object> MAP = new LinkedHashMap<>();
    private static Request.Builder request;

    private static Uri uri;
    private static long reconnectInterval;
    private static WeakReference<Builder> builder2;
    private final Builder builder1;

    private WebConfiguration(Builder builder) {
        this.builder1 = builder;
        builder2 = new WeakReference<>(builder);
        request = new Request.Builder();
    }

    protected static long getReconnectInterval() {
        return reconnectInterval;
    }

    protected static String getException() {
        return builder2.get().exception;
    }

    protected static String getConnect() {
        return builder2.get().connect;
    }

    protected static String getDisconnect() {
        return builder2.get().disconnect;
    }

    protected static Request getRequest() {
        final int[] p = {0};
        PARAM.setLength(p[0]);
        MAP.forEach((key, value) -> {
            PARAM.append("/").append(value);
            p[0]++;
        });
        try {
            return request.get()
                    .url(new URL(uri.toString() + PARAM))
                    .build();
        } catch (MalformedURLException e) {
            return request.get()
                    .url(uri.toString() + PARAM)
                    .build();
        }
    }

    public void get() {
        MAP.clear();
        uri = builder1.uri;
        reconnectInterval = builder1.reconnectInterval;
        MAP.putAll(builder1.map);
    }


    public static final class Builder {

        private final Builder builder;

        private Builder() {
            this.builder = this;
        }

        private Uri uri;
        private long reconnectInterval;
        private Map<String, Object> map;
        private String connect = "connect";
        private String disconnect = "disconnect";
        private String exception = "exception";

        /**
         * 访问地址
         *
         * @param url 地址
         */
        public Builder setUrl(String url) {
            this.uri = Uri.parse(url);
            return builder;
        }

        /**
         * 重连间隔
         *
         * @param reconnectInterval 时间（秒）
         */
        public Builder setReconnectInterval(long reconnectInterval) {
            this.reconnectInterval = reconnectInterval * 1000;
            return builder;
        }

        /**
         * 访问地址参数
         *
         * @param map 参数
         */
        public Builder setParam(Map<String, Object> map) {
            this.map = map;
            return builder;
        }

        /**
         * 成功连接状态
         */
        public Builder setConnect(String connect) {
            this.connect = connect;
            return builder;
        }

        /**
         * 断开连接状态
         */
        public Builder setDisconnect(String disconnect) {
            this.disconnect = disconnect;
            return builder;
        }

        /**
         * 异常状态
         */
        public Builder setException(String exception) {
            this.exception = exception;
            return builder;
        }

        public WebConfiguration build() {
            synchronized (WebConfiguration.class) {
                return new WebConfiguration(builder);
            }
        }
    }

    public static Builder builder() {
        return new Builder();
    }

}
