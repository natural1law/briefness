package com.androidx.http.net;

import android.net.Uri;

import com.google.gson.JsonObject;

import java.net.MalformedURLException;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;

interface IHttpNetwork {

    OkHttpClient getClient();

    Request getRequest(Uri url, Map<String, Object> map) throws MalformedURLException;

    Request postRequest(Uri url, Map<String, Object> map) throws MalformedURLException;

    Request postRequest(Uri url, JsonObject json) throws MalformedURLException;

    Request deleteRequest(Uri url, Map<String, Object> map) throws MalformedURLException;

    Request deleteRequest(Uri url, JsonObject json) throws MalformedURLException;

    Request forrequest(Uri url, String key, JsonObject json) throws MalformedURLException;

    Request postRequestProto(Uri url, byte[] bytes) throws MalformedURLException;

}
