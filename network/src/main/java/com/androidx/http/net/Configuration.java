package com.androidx.http.net;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class Configuration implements Serializable {

    private Configuration() {
    }

    private static String ssl = "";
    private static volatile Map<String, String> headers = new ConcurrentHashMap<>();

    public static String getSsl() {
        return ssl;
    }

    public static Map<String, String> getHeaders() {
        return headers;
    }

    public static void setHeaders(Map<String, String> header) {
        headers = header;
    }

    public static void setSsl(String certification) {
        Configuration.ssl = ssl;
    }
}
