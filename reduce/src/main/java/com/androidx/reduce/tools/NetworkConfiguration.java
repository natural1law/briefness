package com.androidx.reduce.tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.net.wifi.WifiManager;
import android.os.Build;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * 网络配置（支持android7.0以上）
 */
@SuppressWarnings("unused")
public final class NetworkConfiguration {

    private static volatile NetworkConfiguration instance;
    private final ConnectivityManager cm;
    private final WifiManager wm;
    private final NetworkCapabilities nc;

    private NetworkConfiguration(Context c) {
        wm = (WifiManager) c.getSystemService(Context.WIFI_SERVICE);
        cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        nc = cm.getNetworkCapabilities(cm.getActiveNetwork());
    }

    public static NetworkConfiguration build(Context c) {
        if (instance == null) synchronized (NetworkConfiguration.class) {
            if (instance == null) instance = new NetworkConfiguration(c);
        }
        return instance;
    }

    /**
     * 判断是否是移动网络
     */
    @SuppressLint("WrongConstant")
    public boolean isMobile() {
        if (nc == null) return false;
        if (isConnected()) return nc.hasCapability(NetworkCapabilities.TRANSPORT_CELLULAR);
        else return false;
    }

    /**
     * 判断是否是WIFI网络
     */
    @SuppressLint("WrongConstant")
    public boolean isWiFi() {
        if (nc == null) return false;
        if (isConnected()) return nc.hasCapability(NetworkCapabilities.TRANSPORT_WIFI);
        else return false;
    }

    /**
     * 判断是否是VPN网络
     */
    @SuppressLint("WrongConstant")
    public boolean isVPN() {
        if (nc == null) return false;
        if (isConnected()) return nc.hasCapability(NetworkCapabilities.TRANSPORT_VPN);
        else return false;
    }

    /**
     * 判断是否是以太网网络
     */
    @SuppressLint("WrongConstant")
    public boolean isEtherLink() {
        if (nc == null) return false;
        if (isConnected()) return nc.hasCapability(NetworkCapabilities.TRANSPORT_ETHERNET);
        else return false;
    }

    /**
     * 判断是否是移动网络
     */
    public boolean isConnected() {
        Network network = cm.getActiveNetwork();
        if (network == null) return false;
        NetworkCapabilities capabilities = cm.getNetworkCapabilities(network);
        if (capabilities == null) return false;
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
    }

    /**
     * 注册网络监听并且回调
     */
    public void registerNetworkCallback(ConnectivityManager.NetworkCallback callback) {
        NetworkRequest.Builder builder = new NetworkRequest.Builder();
        builder.addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR);
        builder.addTransportType(NetworkCapabilities.TRANSPORT_WIFI);
        builder.addTransportType(NetworkCapabilities.TRANSPORT_BLUETOOTH);
        builder.addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET);
        builder.addTransportType(NetworkCapabilities.TRANSPORT_VPN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.addTransportType(NetworkCapabilities.TRANSPORT_WIFI_AWARE);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            builder.addTransportType(NetworkCapabilities.TRANSPORT_LOWPAN);
        }
        cm.registerNetworkCallback(builder.build(), callback);
    }

    /**
     * 注销网络监听并且回调
     */
    public void unregisterNetworkCallback(ConnectivityManager.NetworkCallback callback) {
        cm.unregisterNetworkCallback(callback);
    }

    /**
     * 移动网络IP地址
     */
    public String ipMobile() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface nif = en.nextElement();
                for (Enumeration<InetAddress> enumIpAdd = nif.getInetAddresses(); enumIpAdd.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAdd.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
            return "";
        } catch (SocketException e) {
            return e.getMessage();
        }
    }

    /**
     * WifiIP地址
     */
    public String ipWifi() {
        try {
            int ip = wm.getConnectionInfo().getIpAddress();
            return (ip & 0xFF) + "." + ((ip >> 8) & 0xFF) + "." + ((ip >> 16) & 0xFF) + "." + (ip >> 24 & 0xFF);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

}
