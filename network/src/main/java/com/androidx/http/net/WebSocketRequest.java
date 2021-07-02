package com.androidx.http.net;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.androidx.http.module.MessageModule;
import com.androidx.http.module.ResModule;
import com.androidx.http.net.listener.Enqueue;
import com.androidx.http.net.listener.LoginCallback;
import com.androidx.http.net.listener.MsgCallback;
import com.androidx.http.net.module.DataModule;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.protobuf.InvalidProtocolBufferException;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public final class WebSocketRequest implements Enqueue {

    static {
        System.loadLibrary("message");
    }

    private static native String host(String host, String port);

    private static native String hosts(String host, String port);

    private static native String init(String id, String user, String pass, String project);

    private static native String publicKey();

    private static native int error();

    private static native int fail();

    private static native int success();

    public static native int msg();

    public static native int offline();

    public static native int online();

    private static final String FAIL_MSG = "服务器连接已断开";//连接异常消息

    public final static int NORMAL_CLOSE = 1000;//正常关闭
    public final static int ABNORMAL_CLOSE = 1001;//非正常关闭
//    public final static int CHECK_CLOSE = 1002;//签名校验失败

    private static String host;
    private static String port;
    private static String username;
    private static String password;
    private static String project = "";
    private static boolean isHttps;

    private static Lock lock;
    private static WebSocket okWebSocket;
    private static long reconnectInterval;
    private static int reconnectCount = 0;//重连次数
    private boolean isSend = false;//发送状态
    public static volatile LoginCallback loginCallback;//登录回调
    public static volatile MsgCallback msgCallback;//消息回调

    private static final Runnable run = WebSocketRequest::init;

    private static final Handler handler = new Handler(Looper.getMainLooper(), message -> {
        try {
            DataModule data = (DataModule) message.obj;
            if (message.what == fail()) {
                try {
                    data.getLoginCallback().onFailure(data.getMsg());
                    return false;
                } catch (Exception e) {
                    return false;
                }
            } else if (message.what == error()) {
                try {
                    data.getLoginCallback().onFailure(data.getMsg());
                    return false;
                } catch (Exception e) {
                    return false;
                }
            } else if (message.what == success()) {
                try {
                    data.getLoginCallback().onSuccess();
                    return false;
                } catch (Exception e) {
                    return false;
                }
            } else if (message.what == msg()) {
                try {
                    MessageModule.MsgResponse response = MessageModule.MsgResponse.parseFrom(data.getBytes().toByteArray());
                    data.getMsgCallback().message(response.getCode(), response.getMsg(), response.getData());
                } catch (InvalidProtocolBufferException e) {
                    Log.e("callbackException", String.valueOf(e.getMessage()));
                }
                return false;
            } else if (message.what == online()) {
                Log.e("用户上线", String.valueOf(message.obj));
                return false;
            } else if (message.what == offline()) {
                Log.e("用户下线", String.valueOf(message.obj));
                return false;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    });

    private static final class WebSocketHolder extends WebSocketListener {

        private final Gson gson = new Gson();

        private static volatile WebSocketHolder instance;

        private WebSocketHolder() {
        }

        public static WebSocketHolder build() {
            try {
                if (instance == null) {
                    synchronized (WebSocketHolder.class) {
                        if (instance == null) {
                            instance = new WebSocketHolder();
                        }
                    }
                }
                return instance;
            } catch (Exception e) {
                return new WebSocketHolder();
            }
        }

        @Override
        public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
            occlude();
            Log.e("connection", "断开连接");
        }

        @Override
        public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
            occlude();
        }

        @Override
        public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
            reconnect();
            Log.e("connectionException", String.valueOf(t.getMessage()));
            handler.sendMessage(handler.obtainMessage(error(), new DataModule(FAIL_MSG, loginCallback)));
        }

        @Override
        public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
            try {
                ResModule res = gson.fromJson(text, new TypeToken<ResModule>() {
                }.getType());
//                if (!Secure.RSA.verify(username, publicKey(), res.getUser())) {
//                    Log.e("connection", "校验失败，关闭");
//                    webSocket.close(CHECK_CLOSE, "signCheckError");
//                    return;
//                }
                switch (Objects.requireNonNull(res.getMsg())) {
                    case "success":
                        try {
                            Log.i("connection", "建立连接");
                            Message message1 = handler.obtainMessage();
                            message1.what = success();
                            message1.obj = new DataModule(loginCallback);
                            handler.sendMessage(message1);
                        } catch (Exception e) {
                            Log.e("successException", String.valueOf(e.getMessage()));
                        }
                        return;
                    case "connect":
                        try {
                            Message message3 = handler.obtainMessage();
                            message3.what = online();
                            message3.obj = res.getUser();
                            handler.sendMessage(message3);
                        } catch (Exception e) {
                            Log.e("connectException", String.valueOf(e.getMessage()));
                        }
                        return;
                    case "disconnect":
                        try {
                            Message message4 = handler.obtainMessage();
                            message4.what = offline();
                            message4.obj = res.getUser();
                            handler.sendMessage(message4);
                        } catch (Exception e) {
                            Log.e("disconnectException", String.valueOf(e.getMessage()));
                        }
                        return;
                    default:
                        try {
                            webSocket.close(NORMAL_CLOSE, "close");
                            Message message2 = handler.obtainMessage();
                            message2.what = fail();
                            message2.obj = new DataModule(text, loginCallback);
                            handler.sendMessage(message2);
                        } catch (Exception e) {
                            Log.e("defaultException", String.valueOf(e.getMessage()));
                        }
                }
            } catch (Exception e) {
                Log.e("onMessageException", String.valueOf(e.getMessage()));
            }
        }

        @Override
        public void onMessage(@NotNull WebSocket webSocket, @NotNull ByteString bytes) {
            handler.sendMessage(handler.obtainMessage(msg(), new DataModule(bytes, msgCallback)));
        }

        @Override
        public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
        }

    }

    @SuppressLint("LongLogTag")
    private static void init() {
        try {
            OkHttpClient client = HttpNetwork.builder().getClient();
            ExecutorService server = client.dispatcher().executorService();
            if (server.isShutdown()) {
                server.shutdown();
            }
            lock.lockInterruptibly();
            if (isHttps) {
                okWebSocket = client.newWebSocket(new Request.Builder().url(hosts(host, port) + init(publicKey(), username, password, project)).build(), WebSocketHolder.build());
            } else {
                okWebSocket = client.newWebSocket(new Request.Builder().url(host(host, port) + init(publicKey(), username, password, project)).build(), WebSocketHolder.build());
            }
        } catch (Exception e) {
            Log.e("webSocketRequestException", String.valueOf(e.getMessage()));
        } finally {
            lock.unlock();
        }
    }

    private boolean sendMessage(Object msg) {
        if (okWebSocket != null) {
            if (msg instanceof byte[]) {
                isSend = okWebSocket.send(ByteString.of((byte[]) msg));
            } else if (msg instanceof ByteString) {
                isSend = okWebSocket.send((ByteString) msg);
            }
            if (!isSend) {
                reconnect();
            }
        }
        return isSend;
    }

    /**
     * 异常重连
     */
    private static void reconnect() {
        handler.postDelayed(run, reconnectInterval);
        reconnectCount++;
    }

    /**
     * 连接通道关闭
     */
    public static void occlude() {
        handler.removeCallbacks(run);
    }

    @Override
    public boolean send(int type, byte[] msg) {
        return sendMessage(MessageModule.MsgRequest.newBuilder()
                .setType(type)
                .setData(msg == null ? com.google.protobuf.ByteString.copyFromUtf8("") : com.google.protobuf.ByteString.copyFrom(msg))
                .build()
                .toByteArray());
    }

    @Override
    public boolean send(ByteString bs) {
        return sendMessage(bs);
    }

    @Override
    public void close() {
        occlude();
        if (okWebSocket != null) {
            okWebSocket.close(ABNORMAL_CLOSE, "leave");
            okWebSocket = null;
        }
    }

    @Override
    public int reconnectCount() {
        return reconnectCount;
    }

    @Override
    public WebSocket getWebSocket() {
        return okWebSocket;
    }

    public static final class Builder {

        private final Builder builder;
        private String host;
        private String port;
        private String user;
        private String pass;
        private String project;
        private boolean isHttps = false;
        private long reconnectInterval = 60000;//默认60秒间隔重连时间

        public Builder() {
            this.builder = this;
        }

        public Builder setHost(String host) {
            this.host = host;
            return builder;
        }

        public Builder setPort(String port) {
            this.port = port;
            return builder;
        }

        public Builder setUser(String user) {
            this.user = user;
            return builder;
        }

        public Builder setPass(String pass) {
            this.pass = pass;
            return builder;
        }

        public Builder setReconnectInterval(long reconnectInterval) {
            this.reconnectInterval = reconnectInterval;
            return builder;
        }

        public Builder setProject(String project) {
            this.project = project;
            return builder;
        }

        public Builder isHttps(boolean https) {
            isHttps = https;
            return builder;
        }

        public WebSocketRequest build() {
            try {
                synchronized (WebSocketRequest.class) {
                    return new WebSocketRequest(builder);
                }
            } catch (Exception e) {
                return new WebSocketRequest(builder);
            }
        }
    }

    @SuppressLint("LongLogTag")
    private WebSocketRequest(Builder build) {
        try {
            lock = new ReentrantLock();
            reconnectInterval = build.reconnectInterval;
            host = build.host;
            port = build.port;
            username = build.user;
            password = build.pass;
            project = build.project;
            isHttps = build.isHttps;
            handler.post(run);
        } catch (Exception ignored) {
        }
    }

    public static Builder builder() {
        try {
            synchronized (Builder.class) {
                return new Builder();
            }
        } catch (Exception e) {
            return new Builder();
        }
    }
}
