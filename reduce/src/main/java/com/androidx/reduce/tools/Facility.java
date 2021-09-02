package com.androidx.reduce.tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;

import java.util.Objects;
import java.util.UUID;

public final class Facility {

    /**
     * 获取android设备ID
     */
    private static String getBuild() {
        //noinspection deprecation
        String dev = "@%&" +
                Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +

                Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 +

                Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +

                Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +

                Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +

                Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +

                Build.USER.length() % 10; //13 位

        String serial;
        try {
            serial = Objects.requireNonNull(Build.class.getField("SERIAL").get(null)).toString();
            //API>=9 使用serial号
            return new UUID(dev.hashCode(), serial.hashCode()).toString();
        } catch (Exception exception) {
            //serial需要一个初始化
            serial = "ab123456"; // 随便一个初始化
            //使用硬件信息拼凑出来的15位号码
            return new UUID(dev.hashCode(), serial.hashCode()).toString();
        }
    }

    /**
     * 获取手机cpu架构，支持的指令集
     */
    private static String getCpuAbi() {
        String[] abis;
        abis = Build.SUPPORTED_ABIS;
        StringBuilder abiStr = new StringBuilder();
        for (String abi : abis) {
            abiStr.append(abi);
            abiStr.append(',');
        }
        return abiStr.toString();
    }

    @SuppressLint("HardwareIds")
    public static String imei(Context context) {
        String deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        String cleartext = Build.BRAND + Build.MODEL + getCpuAbi() + getBuild();
        String md5 = Secure.MD5.encrypt(cleartext, deviceId);
        return md5.substring(8, 23);
    }
}
