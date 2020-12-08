package com.androidx.http.use;

import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.androidx.http.api.LoginServer;
import com.androidx.http.api.NetHttp;
import com.androidx.http.module.MsgModule;
import com.androidx.http.net.listener.Callback;
import com.androidx.http.net.listener.Enqueue;
import com.androidx.http.net.listener.LoginCallback;
import com.androidx.http.net.listener.MsgCallback;

import org.json.JSONObject;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Map;
import java.util.Objects;

import okio.ByteString;

import static android.provider.Settings.AUTHORITY;
import static android.provider.Telephony.Carriers.PASSWORD;
import static android.provider.Telephony.Carriers.PORT;
import static android.provider.Telephony.Carriers.USER;
import static android.provider.Telephony.Mms.Addr.ADDRESS;
import static android.provider.Telephony.Mms.Addr.TYPE;
import static android.provider.Telephony.Mms.Part.NAME;
import static com.androidx.http.use.IMApp.appThis;
//import static com.androidx.http.use.IMApp.appThis;

public final class NetRequest {

    private static final String TAG = "NetRequest--";

    private final AppCompatActivity aThis;
    private final String ip;//ip地址
    private final String port;//端口号
    private final String username;//用户名
    private final String password;//用户密码
    private final String project;//项目名称
    private final boolean isHttps;//开启https
    private final long reconnectInterval;//重连间隔时间
    public static volatile Enqueue enqueue;

    private NetRequest(WebSocket builder) {
        this.ip = builder.ip;
        this.port = builder.port;
        this.username = builder.username;
        this.password = builder.password;
        this.project = builder.project;
        this.aThis = builder.activity;
        this.isHttps = builder.isHttps;
        this.reconnectInterval = builder.reconnectInterval;
        WebSocketRequest.msgCallback = builder.msgCallback;
        WebSocketRequest.loginCallback = builder.loginCallback;
    }

    public static final class WebSocket implements Parcelable, Externalizable {

        private WebSocket builder;
        private String ip;//ip地址
        private String port;//端口号
        private String username;//用户名
        private String password;//用户密码
        private String project;//项目名称
        private long reconnectInterval = 15000;//重连间隔时间(毫秒)
        private AppCompatActivity activity;
        private MsgCallback msgCallback;
        private LoginCallback loginCallback;
        private boolean isHttps;

        public static final Creator<WebSocket> CREATOR = new Creator<WebSocket>() {
            @Override
            public WebSocket createFromParcel(Parcel in) {
                return new WebSocket(in);
            }

            @Override
            public WebSocket[] newArray(int size) {
                return new WebSocket[size];
            }
        };

        public WebSocket() {
            this.builder = this;
        }

        /**
         * @param activity 活动对象
         */
        public WebSocket setActivity(AppCompatActivity activity) {
            this.activity = activity;
            return builder;
        }

        /**
         * @param ip ip地址
         */
        public WebSocket setIp(String ip) {
            this.ip = ip;
            return builder;
        }

        /**
         * @param port 端口号
         */
        public WebSocket setPort(String port) {
            this.port = port;
            return builder;
        }

        /**
         * @param username 用户名
         */
        public WebSocket setUsername(String username) {
            this.username = username;
            return builder;
        }

        /**
         * @param password 用户密码
         */
        public WebSocket setPassword(String password) {
            this.password = password;
            return builder;
        }

        /**
         * @param project 项目名称
         */
        public WebSocket setProject(String project) {
            this.project = project;
            return builder;
        }

        /**
         * @param reconnectInterval 重连间隔时间
         */
        public WebSocket setReconnectInterval(long reconnectInterval) {
            this.reconnectInterval = reconnectInterval;
            return builder;
        }

        /**
         * 消息回调
         */
        public WebSocket setMsgCallback(MsgCallback callback) {
            msgCallback = callback;
            return builder;
        }

        /**
         * 登录回调
         */
        public WebSocket setLoginCallback(LoginCallback callback) {
            loginCallback = callback;
            return builder;
        }

        public WebSocket isHttps(boolean https) {
            isHttps = https;
            return builder;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        protected WebSocket(Parcel in) {
            ip = in.readString();
            port = in.readString();
            username = in.readString();
            password = in.readString();
            project = in.readString();
            reconnectInterval = in.readLong();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                isHttps = in.readBoolean();
            }
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(ip);
            dest.writeString(port);
            dest.writeString(username);
            dest.writeString(password);
            dest.writeString(project);
            dest.writeLong(reconnectInterval);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                dest.writeBoolean(isHttps);
            }
        }

        @Override
        public void writeExternal(ObjectOutput out) throws IOException {
            out.writeUTF(ip);
            out.writeUTF(port);
            out.writeUTF(username);
            out.writeUTF(password);
            out.writeUTF(project);
            out.writeLong(reconnectInterval);
            out.writeBoolean(isHttps);
        }

        @Override
        public void readExternal(ObjectInput in) throws IOException {
            ip = in.readUTF();
            port = in.readUTF();
            username = in.readUTF();
            password = in.readUTF();
            project = in.readUTF();
            reconnectInterval = in.readLong();
            isHttps = in.readBoolean();
        }

        public NetRequest init() {
            try {
                synchronized (NetRequest.class) {
                    return new NetRequest(builder);
                }
            } catch (Exception e) {
                return new NetRequest(builder);
            }
        }
    }

    public static WebSocket webSocket() {
        try {
            synchronized (WebSocket.class) {
                return new WebSocket();
            }
        } catch (Exception e) {
            return new WebSocket();
        }
    }

    /**
     * 启动webSocket服务
     */
    public void start() {
        try {
            Bundle bundle = new Bundle();
            bundle.putString(ADDRESS, ip);
            bundle.putString(PORT, port);
            bundle.putString(USER, username);
            bundle.putString(PASSWORD, password);
            bundle.putString(NAME, project);
            bundle.putLong(TYPE, reconnectInterval);
            bundle.putBoolean(AUTHORITY, isHttps);
            appThis.startService(aThis, LoginServer.class, bundle);
        } catch (Exception e) {
            Log.e(TAG + "启动异常：", String.valueOf(e.getMessage()));
        }
    }

    /**
     * 停止webSocket服务
     */
    public void stop() {
        try {
            appThis.stopService(aThis, LoginServer.class);
        } catch (Exception e) {
            Log.e(TAG + "销毁异常：", String.valueOf(e.getMessage()));
        }
    }

    /**
     * 发送消息
     */
    public void send(ByteString data) {
        try {
            enqueue.send(data);
        } catch (Exception e) {
            Log.e(TAG + "发送消息异常1：", String.valueOf(e.getMessage()));
        }
    }

    public void send(int type, byte[] data) {
        try {
            enqueue.send(type, data);
        } catch (Exception e) {
            Log.e(TAG + "发送消息异常2：", String.valueOf(e.getMessage()));
        }
    }

    public void send(int type, JSONObject data) {
        try {
            send(type, data.toString().getBytes());
        } catch (Exception e) {
            Log.e(TAG + "发送消息异常3：", String.valueOf(e.getMessage()));
        }
    }

    public void send(int type, Map<String, Object> data) {
        try {
            send(type, data.toString().getBytes());
        } catch (Exception e) {
            Log.e(TAG + "发送消息异常5：", String.valueOf(e.getMessage()));
        }
    }

    public void send(MsgModule.Msg msg) {
        try {
            send(msg.getCode(), msg.toByteArray());
        } catch (Exception e) {
            Log.e(TAG + "发送消息异常4：", String.valueOf(e.getMessage()));
        }
    }

    /**
     * 发送请求
     *
     * @param host     服务器IP地址
     * @param port     服务器端口
     * @param suffix   路径后缀
     * @param data     发送数据
     * @param isHttps  开启https
     * @param callback 结果回调
     */
    public static void sendMapGet(String host, String port, String suffix, Map<String, Object> data, boolean isHttps, Callback callback) {
        Objects.requireNonNull(NetHttp.Companion.builder())
                .setHost(host, port, isHttps)
                .setUrlSuffix(suffix)
                .setMode(1)
                .setMap(data)
                .setCallback(callback)
                .build();
    }

    /**
     * 发送请求
     *
     * @param host     服务器IP地址
     * @param port     服务器端口
     * @param suffix   路径后缀
     * @param data     发送数据
     * @param isHttps  开启https
     * @param callback 结果回调
     */
    public static void sendJsonPost(String host, String port, String suffix, JSONObject data, boolean isHttps, Callback callback) {
        Objects.requireNonNull(NetHttp.Companion.builder())
                .setHost(host, port, isHttps)
                .setUrlSuffix(suffix)
                .setMode(2)
                .setJson(data)
                .setCallback(callback)
                .build();
    }

    /**
     * 发送请求
     *
     * @param host     服务器IP地址
     * @param port     服务器端口
     * @param suffix   路径后缀
     * @param data     发送数据
     * @param isHttps  开启https
     * @param callback 结果回调
     */
    public static void sendMapPost(String host, String port, String suffix, Map<String, Object> data, boolean isHttps, Callback callback) {
        Objects.requireNonNull(NetHttp.Companion.builder())
                .setHost(host, port, isHttps)
                .setUrlSuffix(suffix)
                .setMode(3)
                .setMap(data)
                .setCallback(callback)
                .build();
    }

    /**
     * 发送请求
     *
     * @param host     服务器IP地址
     * @param port     服务器端口
     * @param suffix   路径后缀
     * @param data     发送数据
     * @param isHttps  开启https
     * @param callback 结果回调
     */
    public static void sendBytes(String host, String port, String suffix, byte[] data, boolean isHttps, Callback callback) {
        Objects.requireNonNull(NetHttp.Companion.builder())
                .setHost(host, port, isHttps)
                .setUrlSuffix(suffix)
                .setMode(4)
                .setBytes(data)
                .setCallback(callback)
                .build();
    }

    /**
     * 发送请求
     *
     * @param host     服务器IP地址
     * @param port     服务器端口
     * @param suffix   路径后缀
     * @param data     发送数据
     * @param isHttps  开启https
     * @param callback 结果回调
     */
    public static void sendMapDelete(String host, String port, String suffix, Map<String, Object> data, boolean isHttps, Callback callback) {
        Objects.requireNonNull(NetHttp.Companion.builder())
                .setHost(host, port, isHttps)
                .setUrlSuffix(suffix)
                .setMode(5)
                .setMap(data)
                .setCallback(callback)
                .build();
    }

    /**
     * 发送请求
     *
     * @param host     服务器IP地址
     * @param port     服务器端口
     * @param suffix   路径后缀
     * @param data     发送数据
     * @param isHttps  开启https
     * @param callback 结果回调
     */
    public static void sendJsonDelete(String host, String port, String suffix, JSONObject data, boolean isHttps, Callback callback) {
        Objects.requireNonNull(NetHttp.Companion.builder())
                .setHost(host, port, isHttps)
                .setUrlSuffix(suffix)
                .setMode(6)
                .setJson(data)
                .setCallback(callback)
                .build();
    }

    /**
     * 发送请求
     *
     * @param host     服务器IP地址
     * @param port     服务器端口
     * @param suffix   路径后缀
     * @param data     发送数据
     * @param isHttps  开启https
     * @param callback 结果回调
     */
    public static void sendJsonFrom(String host, String port, String suffix, JSONObject data, boolean isHttps, Callback callback) {
        Objects.requireNonNull(NetHttp.Companion.builder())
                .setHost(host, port, isHttps)
                .setUrlSuffix(suffix)
                .setMode(7)
                .setJson(data)
                .setCallback(callback)
                .build();
    }


}
