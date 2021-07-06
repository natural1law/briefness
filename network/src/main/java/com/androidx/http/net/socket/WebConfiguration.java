package com.androidx.http.net.socket;

import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Request;

/**
 * @date 2021/07/06
 */
public class WebConfiguration {

    private static final StringBuffer PARAM = new StringBuffer();
    private static final Map<String, Object> MAP = new ConcurrentHashMap<>();
    private static Request.Builder request;

    private static Uri uri;
    private static long reconnectInterval;
    private final Builder builder;

    private WebConfiguration(Builder builder) {
        this.builder = builder;
        request = new Request.Builder();
    }

    protected static long getReconnectInterval() {
        return reconnectInterval;
    }

    protected static Request getRequest() {
        final int[] p = {0};
        PARAM.setLength(p[0]);
        MAP.forEach((key, value) -> {
            if (p[0] == 0) {
                PARAM.append("?");
            } else {
                PARAM.append("&");
            }
            PARAM.append(key).append("=").append(value);
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
        uri = builder.uri;
        reconnectInterval = builder.reconnectInterval;
        MAP.putAll(builder.map);
    }

    public static final class Builder {

        private final Builder builder;

        private Builder() {
            this.builder = this;
        }

        private Uri uri;//
        private long reconnectInterval;
        private Map<String, Object> map;//

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
