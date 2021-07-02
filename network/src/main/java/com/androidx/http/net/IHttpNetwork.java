package com.androidx.http.net;

import org.json.JSONObject;

import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;

interface IHttpNetwork {

    OkHttpClient getClient();

    Request getRequest(String url, Map<String, Object> map);

    Request postRequest(String url, Map<String, Object> map);

    Request postRequest(String url, JSONObject json);

    Request deleteRequest(String url, Map<String, Object> map);

    Request deleteRequest(String url, JSONObject json);

    Request formRequest(String url, JSONObject json);

    Request postRequestProto(String url, byte[] bytes);

}
