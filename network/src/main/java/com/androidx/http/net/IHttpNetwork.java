package com.androidx.http.net;

import com.google.gson.JsonObject;

import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;

interface IHttpNetwork {

    OkHttpClient getClient();

    Request getRequest(String url, Map<String, Object> map);

    Request postRequest(String url, Map<String, Object> map);

    Request postRequest(String url, JsonObject json);

    Request deleteRequest(String url, Map<String, Object> map);

    Request deleteRequest(String url, JsonObject json);

    Request formRequest(String url, JsonObject json);

    Request postRequestProto(String url, byte[] bytes);

}
