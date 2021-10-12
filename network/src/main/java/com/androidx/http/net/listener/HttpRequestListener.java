package com.androidx.http.net.listener;

import com.google.gson.JsonObject;

import java.util.Map;

public interface HttpRequestListener {

    void getRequest(String url, Map<String, Object> map, int maxAnewCount, StringCallback stringCallback);

    void postRequestProto(String url, byte[] bytes, int maxAnewCount, BytesCallback callBack);

    void postRequest(String url, Map<String, Object> map, int maxAnewCount, StringCallback callBack);

    void postRequest(String url, JsonObject json, int maxAnewCount, StringCallback callBack);

    void deleteRequest(String url, JsonObject json, int maxAnewCount, StringCallback callBack);

    void deleteRequest(String url, Map<String, Object> map, int maxAnewCount, StringCallback callBack);

    void forrequest(String url, String key, JsonObject json, int maxAnewCount, StringCallback callBack);

}
