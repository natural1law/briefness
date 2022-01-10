package com.androidx.http.net.listener;

import com.google.gson.JsonObject;

import java.util.Map;

public interface HttpRequestListener {

    void getRequest(String url, Map<String, Object> map, int maxAnewCount, Response response);

    void postRequestProto(String url, byte[] bytes, int maxAnewCount, Callback callback);

    void postRequest(String url, Map<String, Object> map, int maxAnewCount, Response response);

    void postRequest(String url, JsonObject json, int maxAnewCount, Response response);

    void deleteRequest(String url, JsonObject json, int maxAnewCount, Response response);

    void deleteRequest(String url, Map<String, Object> map, int maxAnewCount, Response response);

    void forrequest(String url, String key, JsonObject json, int maxAnewCount, Response response);

    void upload(String url, String key, String path, int maxAnewCount, Response response);

    void download(String url, String outPath, int maxAnewCount, DownloadListener listener);

}
