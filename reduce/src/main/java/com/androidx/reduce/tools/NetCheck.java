package com.androidx.reduce.tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;

import java.lang.ref.WeakReference;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Objects;

/**
 * 检查网络连接
 */
public final class NetCheck {

    @SuppressWarnings("FieldCanBeLocal")
    private static volatile NetCheck instance;

    /**
     * 没有连接网络
     */
    private static final int NETWORK_NONE = -1;
    /**
     * 移动网络
     */
    private static final int NETWORK_MOBILE = 0;
    /**
     * 无线网络
     */
    private static final int NETWORK_WIFI = 1;

    private final WeakReference<Context> wrContext;

    /**
     * 检查网络连接
     *
     * @return 连接状态
     */
    public boolean isConnected() {
        try {
            return type() != NETWORK_NONE;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 检查WiFi网络状态
     *
     * @return WiFi 连接状态
     */
    public boolean isWifiConnected() {
        try {
            return type() == NETWORK_WIFI;
        } catch (Exception e) {
            return false;
        }

    }

    /**
     * 检查移动网络状态
     *
     * @return Mobile 连接状态
     */
    public boolean isMobileConnected() {
        try {
            return type() == NETWORK_MOBILE;
        } catch (Exception e) {
            return false;
        }
    }

    public String getIpAddress() {
        try {
            if (type() == NETWORK_MOBILE) {
                return phoneIp();
            } else if (type() == NETWORK_MOBILE) {
                return phoneIp();
            } else if (type() == NETWORK_WIFI) {
                return wifiIp();
            } else if (type() == NETWORK_WIFI) {
                return wifiIp();
            } else {
                return "";
            }
        } catch (Exception e) {
            return e.getMessage();
        }

    }

    private String phoneIp() {
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

    private String wifiIp() {
        try {
            WifiManager wifiManager = (WifiManager) wrContext.get().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = Objects.requireNonNull(wifiManager).getConnectionInfo();
            int ip = wifiInfo.getIpAddress();
            return (ip & 0xFF) + "." + ((ip >> 8) & 0xFF) + "." + ((ip >> 16) & 0xFF) + "." + (ip >> 24 & 0xFF);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private int type() {
        try {
            ConnectivityManager manager = (ConnectivityManager) wrContext.get().getSystemService(Context.CONNECTIVITY_SERVICE);
            if (manager != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    NetworkInfo networkInfo = manager.getNetworkInfo(manager.getActiveNetwork());
                    return networkInfo != null ? networkInfo.getType() : -1;
                } else {
                    NetworkInfo info = manager.getActiveNetworkInfo();
                    return info != null ? info.getType() : -1;
                }
            } else {
                return -1;
            }
        } catch (Exception e) {
            return -1;
        }
    }

    private NetCheck(Context context) {
        this.wrContext = new WeakReference<>(context);
    }

    public static NetCheck builder(Context context) {
        try {
            synchronized (NetCheck.class) {
                instance = new NetCheck(context);
            }
            return instance;
        } catch (Exception e) {
            return instance;
        }
    }

}
