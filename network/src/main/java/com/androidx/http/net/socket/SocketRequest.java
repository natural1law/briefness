package com.androidx.http.net.socket;

import static com.androidx.http.net.socket.WebConfiguration.getConnect;
import static com.androidx.http.net.socket.WebConfiguration.getDisconnect;
import static com.androidx.http.net.socket.WebConfiguration.getException;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.androidx.http.net.HttpNetwork;
import com.androidx.http.net.listener.ActionListener;
import com.androidx.http.net.listener.Enqueue;
import com.androidx.http.net.listener.LoginCallback;
import com.androidx.http.net.listener.MsgCallback;
import com.androidx.http.net.module.DataModule;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.module.protobuf.MessageModule;

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

  public final static int NORMAL_CLOSE = 1000;//正常关闭
  public final static int ABNORMAL_CLOSE = 1001;//非正常关闭
  private static final Runnable run = SocketRequest::init;
  private static Lock lock;
  private static WebSocket okWebSocket;
  private static int reconnectCount = 0;//重连次数
  private static LoginCallback loginCallback;//登录回调
  public static ActionListener actionListener;//用户状态
  private static MsgCallback msgCallback;//消息回调
  public static final Handler handler = new Handler(Looper.getMainLooper(), message -> {
    DataModule data = (DataModule) message.obj;
    if (message.what == -1) {
      if (loginCallback != null) data.getLoginCallback().onFailure(data.getMsg());
      return false;
    } else if (message.what == -2) {
      if (loginCallback != null) data.getLoginCallback().onFailure(data.getMsg());
      if (actionListener != null) data.getActionListener().online(data.getUser());
      return false;
    } else if (message.what == -3) {
      if (loginCallback != null) data.getLoginCallback().onFailure(data.getMsg());
      if (actionListener != null) data.getActionListener().online(data.getUser());
      return false;
    } else if (message.what == 0) {
      if (loginCallback != null && actionListener != null) {
        data.getLoginCallback().onSuccess();
        data.getActionListener().online(data.getUser());
      } else if (loginCallback != null) data.getLoginCallback().onSuccess();
      else if (actionListener != null) data.getActionListener().online(data.getUser());
      return false;
    } else if (message.what == 1) {
      try {
        MessageModule.Response response = MessageModule.Response.parseFrom(data.getBytes().toByteArray());
        data.getMsgCallback().message(response.getCode(), response.getMsg(), response.getData());
      } catch (Exception e) {
        Log.e("MessageModule异常", Log.getStackTraceString(e));
      }
      return false;
    } else {
      return false;
    }
  });
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
      Log.e("socket连接异常", Log.getStackTraceString(t));
    }

    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
      JsonObject res = new Gson().fromJson(text, JsonObject.class);
      if (res.get("msg").getAsString().equals(getConnect())) {
        Message message1 = handler.obtainMessage();
        message1.what = 0;
        if (loginCallback != null && actionListener != null) {
          message1.obj = new DataModule(res.get("user").getAsString(), res.get("msg").getAsString(), loginCallback, actionListener);
        } else if (actionListener != null) {
          message1.obj = new DataModule(res.get("user").getAsString(), actionListener);
        } else if (loginCallback != null) {
          message1.obj = new DataModule(res.get("msg").getAsString(), loginCallback);
        }
        handler.sendMessage(message1);
      } else if (res.get("msg").getAsString().equals(getException())) {
        Message message3 = handler.obtainMessage();
        message3.what = -3;
        if (loginCallback != null && actionListener != null) {
          message3.obj = new DataModule(res.get("user").getAsString(), res.get("msg").getAsString(), loginCallback, actionListener);
        } else if (loginCallback == null && actionListener != null) {
          message3.obj = new DataModule(res.get("user").getAsString(), actionListener);
        } else if (loginCallback != null) {
          message3.obj = new DataModule(res.get("msg").getAsString(), loginCallback);
        }
        handler.sendMessage(message3);
      } else if (res.get("msg").getAsString().equals(getDisconnect())) {
        Message message4 = handler.obtainMessage();
        message4.what = -2;
        if (loginCallback != null && actionListener != null) {
          message4.obj = new DataModule(res.get("user").getAsString(), res.get("msg").getAsString(), loginCallback, actionListener);
        } else if (loginCallback == null && actionListener != null) {
          message4.obj = new DataModule(res.get("user").getAsString(), actionListener);
        } else if (loginCallback != null) {
          message4.obj = new DataModule(res.get("msg").getAsString(), loginCallback);
        }
        handler.sendMessage(message4);
      } else {
        Message message2 = handler.obtainMessage();
        message2.what = -1;
        message2.obj = new DataModule(res.get("msg").getAsString(), loginCallback);
        handler.sendMessage(message2);
        webSocket.close(NORMAL_CLOSE, "close");
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

  public SocketRequest() {
    lock = new ReentrantLock();
    handler.post(run);
  }

  @SuppressLint("LongLogTag")
  private static void init() {
    try {
      OkHttpClient client = HttpNetwork.builder().getClient();
      ExecutorService server = client.dispatcher().executorService();
      if (server.isShutdown()) server.shutdown();
      lock.lockInterruptibly();
      okWebSocket = client.newWebSocket(WebConfiguration.getRequest(), webSocketListener);
    } catch (Exception e) {
      Log.e("SocketRequest异常", Log.getStackTraceString(e));
    } finally {
      lock.unlock();
    }
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

  @Override
  public boolean send(int type, byte[] msg) {
    MessageModule.Request request = MessageModule.Request.newBuilder()
        .setType(type).setData(com.google.protobuf.ByteString.copyFrom(msg)).build();
    if (okWebSocket == null) return false;
    else return okWebSocket.send(ByteString.of(request.toByteArray()));
  }

  @Override
  public boolean send(byte[] msg) {
    if (okWebSocket == null) return false;
    else return okWebSocket.send(ByteString.of(msg));
  }

  @Override
  public boolean send(ByteString msg) {
    if (okWebSocket == null) return false;
    else return okWebSocket.send(msg);
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
  public Enqueue setActionListener(ActionListener listener) {
    actionListener = listener;
    return this;
  }

  @Override
  public Enqueue setMsgCallback(MsgCallback callback) {
    msgCallback = callback;
    return this;
  }

  @Override
  public Enqueue setLoginCallback(LoginCallback callback) {
    loginCallback = callback;
    return this;
  }

}
