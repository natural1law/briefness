package com.androidx.reduce.tools;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.util.Map;
import java.util.Objects;
import java.util.WeakHashMap;
import java.util.function.BiConsumer;

/**
 * 微程序缓冲储存器
 *
 * @date 2021/02/20
 */
@SuppressLint("ApplySharedPref")
@SuppressWarnings({"unused", "RedundantSuppression"})
public final class MicroCache {

    private SharedPreferences sp;
    private static volatile MicroCache mc;
    private final Message msg = new Handler(Looper.getMainLooper()).obtainMessage();

    private MicroCache(Context context) {
        if (context != null) sp = context.getSharedPreferences("MicroCache", Activity.MODE_PRIVATE);
    }

    public static MicroCache builder(Context context) {
        if (mc == null) synchronized (MicroCache.class) {
            if (mc == null) mc = new MicroCache(context);
        }
        return mc;
    }

    public void setApply(String key, String value) {
        sp.edit().putString(key, value).apply();
    }

    public void setApply(String key, int value) {
        sp.edit().putInt(key, value).apply();
    }

    public void setApply(String key, float value) {
        sp.edit().putFloat(key, value).apply();
    }

    public void setApply(String key, long value) {
        sp.edit().putLong(key, value).apply();
    }

    public void setApply(String key, boolean value) {
        sp.edit().putBoolean(key, value).apply();
    }

    public void setCommit(String key, String value) {
        sp.edit().putString(key, value).commit();
    }

    public void setCommit(String key, int value) {
        sp.edit().putInt(key, value).commit();
    }

    public void setCommit(String key, float value) {
        sp.edit().putFloat(key, value).commit();
    }

    public void setCommit(String key, long value) {
        sp.edit().putLong(key, value).commit();
    }

    public void setCommit(String key, boolean value) {
        sp.edit().putBoolean(key, value).commit();
    }

    public MicroCache getValue(String key) {
        try {
            if (getAll().isEmpty()) return this;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                getAll().forEach((key1, value) -> {
                    if (Objects.equals(key, key1)) msg.obj = value;
                });
            } else {
                for (String key1 : getAll().keySet()) {
                    if (Objects.equals(key, key1)) msg.obj = getAll().get(key1);
                }
            }
            return this;
        } catch (Exception e) {
            Log.e("MicroCache异常", String.valueOf(e.getMessage()), e);
            return this;
        }
    }

    public String toString() {
        return msg.obj == null ? "" : String.valueOf(msg.obj);
    }

    public int toInt() {
        if (msg.obj == null) return 0;
        return msg.obj instanceof Integer ? Integer.parseInt(msg.obj.toString()) : 0;
    }

    public long toLong() {
        if (msg.obj == null) return 0;
        return msg.obj instanceof Long ? Long.parseLong(msg.obj.toString()) : 0;
    }

    public float toFloat() {
        if (msg.obj == null) return 0;
        return msg.obj instanceof Float ? Float.parseFloat(msg.obj.toString()) : 0.0f;
    }

    public boolean toBoolean() {
        if (msg.obj == null) return false;
        return msg.obj instanceof Boolean && Boolean.parseBoolean(msg.obj.toString());
    }

    public Map<String, ?> getAll() {
        return sp.getAll() == null ? new WeakHashMap<>() : sp.getAll();
    }

    public void clearApply(String key) {
        sp.edit().remove(key).apply();
    }

    public void clearCommit(String key) {
        sp.edit().remove(key).commit();
    }

    public void clearAll() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            getAll().forEach((BiConsumer<String, Object>) (s, o) -> sp.edit().remove(s).commit());
        } else {
            for (String key : getAll().keySet()) {
                sp.edit().remove(key).commit();
            }
        }
    }

}


