package com.androidx.http.net;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;

import com.androidx.http.net.listener.BytesCallback;
import com.androidx.http.net.listener.HttpRequestListener;
import com.androidx.http.net.listener.StringCallback;
import com.androidx.http.net.module.MsgModule;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Response;

@Deprecated
public final class HttpRequest1 implements HttpRequestListener {

    private int currentConnect;
    private static final IHttpNetwork httpNetwork = HttpNetwork.builder();
    private final Handler handler = new Handler(Looper.getMainLooper(), message -> {
        MsgModule msg = (MsgModule) message.obj;
        switch (message.what) {
            case -2:
                msg.getBytesCallback().onFailure(new String(msg.getMsg1()));
                return false;
            case -1:
                msg.getStringCallback().onFailure(msg.getMsg());
                return false;
            case 0:
                msg.getStringCallback().onSuccess(msg.getMsg());
                return false;
            case 1:
                msg.getBytesCallback().onSuccess(msg.getMsg1());
                return false;
            default:
                return false;
        }
    });

    @Override
    public void getRequest(Uri uri, Map<String, Object> map, int maxAnewCount, StringCallback callBack) throws MalformedURLException {
        httpNetwork.getClient().newCall(httpNetwork.getRequest(uri, map)).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                handler.sendMessage(handler.obtainMessage(-1, new MsgModule(e.getMessage() == null ? "" : e.getMessage(), callBack)));
                // 如果超时并未超过指定次数，则重新连接
                if (e instanceof SocketTimeoutException && currentConnect < maxAnewCount) {
                    currentConnect++;
                    httpNetwork.getClient().newCall(call.request()).enqueue(this);
                }
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                handler.sendMessage(handler.obtainMessage(0, new MsgModule(Objects.requireNonNull(response.body()).string(), callBack)));
            }
        });
    }

    @Override
    public void postRequestProto(Uri uri, byte[] bytes, int maxAnewCount, BytesCallback callBack) throws MalformedURLException {
        httpNetwork.getClient().newCall(httpNetwork.postRequestProto(uri, bytes)).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                handler.sendMessage(handler.obtainMessage(-2, new MsgModule(Objects.requireNonNull(e.getMessage()).getBytes(), callBack)));
                // 如果超时并未超过指定次数，则重新连接
                if (e instanceof SocketTimeoutException && currentConnect < maxAnewCount) {
                    currentConnect++;
                    httpNetwork.getClient().newCall(call.request()).enqueue(this);
                }
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                handler.sendMessage(handler.obtainMessage(1, new MsgModule(Objects.requireNonNull(response.body()).bytes(), callBack)));
            }
        });
    }

    @Override
    public void postRequest(Uri uri, Map<String, Object> map, int maxAnewCount, StringCallback callBack) throws MalformedURLException {
        httpNetwork.getClient().newCall(httpNetwork.postRequest(uri, map)).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                handler.sendMessage(handler.obtainMessage(-1, new MsgModule(e.getMessage() == null ? "" : e.getMessage(), callBack)));
                // 如果超时并未超过指定次数，则重新连接
                if (e instanceof SocketTimeoutException && currentConnect < maxAnewCount) {
                    currentConnect++;
                    httpNetwork.getClient().newCall(call.request()).enqueue(this);
                }
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                handler.sendMessage(handler.obtainMessage(0, new MsgModule(Objects.requireNonNull(response.body()).string(), callBack)));
            }
        });
    }

    @Override
    public void postRequest(Uri uri, JsonObject json, int maxAnewCount, StringCallback callBack) throws MalformedURLException {
        httpNetwork.getClient().newCall(httpNetwork.postRequest(uri, json)).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                handler.sendMessage(handler.obtainMessage(-1, new MsgModule(e.getMessage() == null ? "" : e.getMessage(), callBack)));
                // 如果超时并未超过指定次数，则重新连接
                if (e instanceof SocketTimeoutException && currentConnect < maxAnewCount) {
                    currentConnect++;
                    httpNetwork.getClient().newCall(call.request()).enqueue(this);
                }
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                handler.sendMessage(handler.obtainMessage(0, new MsgModule(Objects.requireNonNull(response.body()).string(), callBack)));
            }
        });
    }

    @Override
    public void deleteRequest(Uri uri, JsonObject json, int maxAnewCount, StringCallback callBack) throws MalformedURLException {
        httpNetwork.getClient().newCall(httpNetwork.deleteRequest(uri, json)).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                handler.sendMessage(handler.obtainMessage(-1, new MsgModule(e.getMessage() == null ? "" : e.getMessage(), callBack)));
                // 如果超时并未超过指定次数，则重新连接
                if (e instanceof SocketTimeoutException && currentConnect < maxAnewCount) {
                    currentConnect++;
                    httpNetwork.getClient().newCall(call.request()).enqueue(this);
                }
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                handler.sendMessage(handler.obtainMessage(0, new MsgModule(Objects.requireNonNull(response.body()).string(), callBack)));
            }
        });
    }

    @Override
    public void deleteRequest(Uri uri, Map<String, Object> map, int maxAnewCount, StringCallback callBack) throws MalformedURLException {
        httpNetwork.getClient().newCall(httpNetwork.deleteRequest(uri, map)).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                handler.sendMessage(handler.obtainMessage(-1, new MsgModule(e.getMessage() == null ? "" : e.getMessage(), callBack)));
                // 如果超时并未超过指定次数，则重新连接
                if (e instanceof SocketTimeoutException && currentConnect < maxAnewCount) {
                    currentConnect++;
                    httpNetwork.getClient().newCall(call.request()).enqueue(this);
                }
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                handler.sendMessage(handler.obtainMessage(0, new MsgModule(Objects.requireNonNull(response.body()).string(), callBack)));
            }
        });
    }

    @Override
    public void formRequest(Uri uri, String key, JsonObject json, int maxAnewCount, StringCallback callBack) throws MalformedURLException {
        httpNetwork.getClient().newCall(httpNetwork.formRequest(uri, key, json)).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                handler.sendMessage(handler.obtainMessage(-1, new MsgModule(e.getMessage() == null ? "" : e.getMessage(), callBack)));
                // 如果超时并未超过指定次数，则重新连接
                if (e instanceof SocketTimeoutException && currentConnect < maxAnewCount) {
                    currentConnect++;
                    httpNetwork.getClient().newCall(call.request()).enqueue(this);
                }
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                handler.sendMessage(handler.obtainMessage(0, new MsgModule(Objects.requireNonNull(response.body()).string(), callBack)));
            }
        });
    }

    @Override
    public void setHeader(Map<String, String> header) {
        httpNetwork.setHeader(header);
    }

}
