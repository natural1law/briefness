package com.androidx.http.net.socket;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.androidx.http.module.MessageModule;
import com.androidx.http.module.ResModule;
import com.androidx.http.net.HttpNetwork;
import com.androidx.http.net.listener.ActionListener;
import com.androidx.http.net.listener.Enqueue;
import com.androidx.http.net.listener.LoginCallback;
import com.androidx.http.net.listener.MsgCallback;
import com.androidx.http.net.module.DataModule;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public final class SocketRequest implements Enqueue {

    private static final String FAIL_MSG = "服务器连接已断开";//连接异常消息
    public final static int NORMAL_CLOSE = 1000;//正常关闭
    public final static int ABNORMAL_CLOSE = 1001;//非正常关闭

    private static Lock lock;
    private static WebSocket okWebSocket;
    private static int reconnectCount = 0;//重连次数
    private boolean isSend = false;//发送状态
    private static LoginCallback loginCallback;//登录回调
    private static MsgCallback msgCallback;//消息回调
    public static ActionListener actionListener;//用户状态

    public SocketRequest() {
        lock = new ReentrantLock();
        handler.post(run);
    }

    private static final Runnable run = SocketRequest::init;

    private static final WebSocketListener webSocketListener = new WebSocketListener() {

        @Override
        public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
            occlude();
            Log.e("socket断开连接", reason);
        }

        @Override
        public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
            occlude();
            Log.e("socket断开连接", reason);
        }

        @Override
        public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
            reconnect();
            handler.sendMessage(handler.obtainMessage(-2, new DataModule(FAIL_MSG, loginCallback)));
            Log.e("socket连接异常", Log.getStackTraceString(t));
        }

        @Override
        public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
            ResModule res = new Gson().fromJson(text, new TypeToken<ResModule>() {
            }.getType());
            if (String.valueOf(res.getMsg()).equals(WebConfiguration.getSuccess())) {
                Message message1 = handler.obtainMessage();
                message1.what = 0;
                message1.obj = new DataModule(loginCallback);
                handler.sendMessage(message1);
            } else if (String.valueOf(res.getMsg()).equals(WebConfiguration.getConnect())) {
                Message message3 = handler.obtainMessage();
                message3.what = 2;
                message3.obj = new DataModule(String.valueOf(res.getUser()), actionListener);
                handler.sendMessage(message3);
            } else if (String.valueOf(res.getMsg()).equals(WebConfiguration.getDisconnect())) {
                Message message4 = handler.obtainMessage();
                message4.what = 3;
                message4.obj = new DataModule(String.valueOf(res.getUser()), actionListener);
                handler.sendMessage(message4);
            } else {
                webSocket.close(NORMAL_CLOSE, "close");
                Message message2 = handler.obtainMessage();
                message2.what = -1;
                message2.obj = new DataModule(text, loginCallback);
                handler.sendMessage(message2);
            }
        }

        @Override
        public void onMessage(@NotNull WebSocket webSocket, @NotNull ByteString bytes) {
            handler.sendMessage(handler.obtainMessage(1, new DataModule(bytes, msgCallback)));
        }

        @Override
        public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {

        }
    };

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

    @Override
    public void setActionListener(ActionListener listener) {
        actionListener = listener;
    }

    @Override
    public void setMsgCallback(MsgCallback callback) {
        msgCallback = callback;
    }

    @Override
    public void setLoginCallback(LoginCallback callback) {
        loginCallback = callback;
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
            okWebSocket = client.newWebSocket(WebConfiguration.getRequest(), webSocketListener);
        } catch (Exception e) {
            Log.e("SocketRequest异常", Log.getStackTraceString(e));
        } finally {
            lock.unlock();
        }
    }

    private boolean sendMessage(Object msg) {
        if (okWebSocket != null) {
            if (msg instanceof byte[]) isSend = okWebSocket.send(ByteString.of((byte[]) msg));
            else if (msg instanceof ByteString) isSend = okWebSocket.send((ByteString) msg);
            if (!isSend) reconnect();
        }
        return isSend;
    }

    /**
     * 异常重连
     */
    private static void reconnect() {
        handler.postDelayed(run, WebConfiguration.getReconnectInterval());
        reconnectCount++;
    }

    /**
     * 连接通道关闭
     */
    private static void occlude() {
        handler.removeCallbacks(run);
    }

    public static final Handler handler = new Handler(Looper.getMainLooper(), message -> {
        try {
            DataModule data = (DataModule) message.obj;
            if (message.what == -1) {
                try {
                    data.getLoginCallback().onFailure(data.getMsg());
                    return false;
                } catch (Exception e) {
                    return false;
                }
            } else if (message.what == -2) {
                try {
                    data.getLoginCallback().onFailure(data.getMsg());
                    return false;
                } catch (Exception e) {
                    return false;
                }
            } else if (message.what == 0) {
                try {
                    data.getLoginCallback().onSuccess();
                    return false;
                } catch (Exception e) {
                    return false;
                }
            } else if (message.what == 1) {
                try {
                    MessageModule.MsgResponse response = MessageModule.MsgResponse.parseFrom(data.getBytes().toByteArray());
                    data.getMsgCallback().message(response.getCode(), response.getMsg(), response.getData());
                } catch (Exception e) {
                    Log.e("callbackException", Log.getStackTraceString(e));
                }
                return false;
            } else if (message.what == 2) {
                data.getActionListener().online(data.getUser());
                return false;
            } else if (message.what == 3) {
                data.getActionListener().offline(data.getUser());
                return false;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    });

}
