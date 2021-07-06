package com.androidx.http.net.listener;

import android.net.Uri;

import com.google.gson.JsonObject;

import java.net.MalformedURLException;
import java.util.Map;

public interface HttpRequestListener {

    void getRequest(Uri url, Map<String, Object> map, int maxAnewCount, StringCallback stringCallback) throws MalformedURLException;

    void postRequestProto(Uri url, byte[] bytes, int maxAnewCount, BytesCallback callBack) throws MalformedURLException;

    void postRequest(Uri url, Map<String, Object> map, int maxAnewCount, StringCallback callBack) throws MalformedURLException;

    void postRequest(Uri url, JsonObject json, int maxAnewCount, StringCallback callBack) throws MalformedURLException;

    void deleteRequest(Uri url, JsonObject json, int maxAnewCount, StringCallback callBack) throws MalformedURLException;

    void deleteRequest(Uri url, Map<String, Object> map, int maxAnewCount, StringCallback callBack) throws MalformedURLException;

    void formRequest(Uri url, String key, JsonObject json, int maxAnewCount, StringCallback callBack) throws MalformedURLException;

    void setHeader(Map<String, String> header);
}
