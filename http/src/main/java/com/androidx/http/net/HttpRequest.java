package com.androidx.http.net;

import android.os.Handler;
import android.os.Looper;

import com.androidx.http.net.listener.Callback;
import com.androidx.http.net.listener.HttpRequestListener;
import com.androidx.http.net.module.MsgModule;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Response;

public final class HttpRequest implements HttpRequestListener {

    private int currentConnect;
    private static final IHttpNetwork httpNetwork = HttpNetwork.builder();
    private static final Handler handler = new Handler(Looper.getMainLooper(), message -> {
        MsgModule msg = (MsgModule) message.obj;
        switch (message.what) {
            case -1:
                msg.getCallback().onFailure(msg.getMsg());
                return false;
            case 0:
                msg.getCallback().onSuccess(msg.getMsg());
                return false;
            case 1:
                msg.getCallback().onSuccess(msg.getMsg1());
                return false;
            default:
                return false;
        }
    });

    @Override
    public void getRequest(String url, Map<String, Object> map, int maxAnewCount, Callback callBack) {
        httpNetwork.getClient().newCall(httpNetwork.getRequest(url, map)).enqueue(new okhttp3.Callback() {
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
    public void postRequestProto(String url, byte[] bytes, int maxAnewCount, Callback callBack) {
        httpNetwork.getClient().newCall(httpNetwork.postRequestProto(url, bytes)).enqueue(new okhttp3.Callback() {
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
                handler.sendMessage(handler.obtainMessage(1, new MsgModule(Objects.requireNonNull(response.body()).bytes(), callBack)));
            }
        });
    }

    @Override
    public void postRequest(String url, Map<String, Object> map, int maxAnewCount, Callback callBack) {
        httpNetwork.getClient().newCall(httpNetwork.postRequest(url, map)).enqueue(new okhttp3.Callback() {
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
    public void postRequest(String url, JSONObject json, int maxAnewCount, Callback callBack) {
        httpNetwork.getClient().newCall(httpNetwork.postRequest(url, json)).enqueue(new okhttp3.Callback() {
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
    public void deleteRequest(String url, JSONObject json, int maxAnewCount, Callback callBack) {
        httpNetwork.getClient().newCall(httpNetwork.deleteRequest(url, json)).enqueue(new okhttp3.Callback() {
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
    public void deleteRequest(String url, Map<String, Object> map, int maxAnewCount, Callback callBack) {
        httpNetwork.getClient().newCall(httpNetwork.deleteRequest(url, map)).enqueue(new okhttp3.Callback() {
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
    public void formRequest(String url, JSONObject json, int maxAnewCount, Callback callBack) {
        httpNetwork.getClient().newCall(httpNetwork.formRequest(url, json)).enqueue(new okhttp3.Callback() {
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

}
