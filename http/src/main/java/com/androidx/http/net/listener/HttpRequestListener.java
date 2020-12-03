package com.androidx.http.net.listener;

import org.json.JSONObject;

import java.util.Map;

public interface HttpRequestListener {

    void getRequest(String url, Map<String, Object> map, int maxAnewCount, Callback callback);

    void postRequestProto(String url, byte[] bytes, int maxAnewCount, Callback callBack);

    void postRequest(String url, Map<String, Object> map, int maxAnewCount, Callback callBack);

    void postRequest(String url, JSONObject json, int maxAnewCount, Callback callBack);

    void deleteRequest(String url, JSONObject json, int maxAnewCount, Callback callBack);

    void deleteRequest(String url, Map<String, Object> map, int maxAnewCount, Callback callBack);

    void formRequest(String url, JSONObject json, int maxAnewCount, Callback callBack);

}
