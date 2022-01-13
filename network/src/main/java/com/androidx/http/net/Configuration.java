package com.androidx.http.net;

import java.io.InputStream;
import java.io.Serializable;

import okhttp3.Interceptor;

public final class Configuration implements Serializable {

    private Configuration() {
    }

    public static InputStream ssl;//HTTPS 加密套接字协议
    public static drhListener listener;//显示请求头信息
    public static Interceptor interceptor;//拦截器
    public static int count = 3;//重连次数
    public static int timeout = 180;//超时时间
    public static int interval = 15;//ping帧心跳间隔
    public static int maxConnCount = 1024;//最大连接池
    public static int alive = 30;//连接池的连接活跃时间（默认设置半小时）
    public static int compress = 0;//0-所有消息压缩(默认1024)
    public static boolean reconnection = true;//异常重新连接

    public interface drhListener {
        void value(String rh);
    }

}
