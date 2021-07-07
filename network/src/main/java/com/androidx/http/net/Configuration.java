package com.androidx.http.net;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class Configuration implements Serializable {

    private Configuration() {
    }

    private static Map<String, String> headers = new ConcurrentHashMap<>();


    public static Map<String, String> getHeaders() {
        return headers;
    }

    public static void setHeaders(Map<String, String> header) {
        headers = header;
    }
}
