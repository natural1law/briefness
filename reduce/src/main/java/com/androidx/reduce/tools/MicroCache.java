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

    private MicroCache(Context context) {
        if (context != null) {
            sp = context.getSharedPreferences("MicroCache", Activity.MODE_PRIVATE);
        }
    }

    public static MicroCache builder(Context context) {
        if (mc == null) {
            synchronized (MicroCache.class) {
                if (mc == null) {
                    mc = new MicroCache(context);
                }
            }
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

    public Object getValue(String key) {
        try {
            Message message = new Handler(Looper.getMainLooper()).obtainMessage();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                sp.getAll().forEach((key1, value) -> {
                    if (Objects.equals(key, key1)) message.obj = value;
                });
            } else {
                for (String key1 : sp.getAll().keySet()) {
                    if (Objects.equals(key, key1)) message.obj = sp.getAll().get(key1);
                }
            }
            return message.obj;
        } catch (Exception e) {
            Log.e("MicroCache异常", String.valueOf(e.getMessage()));
            return "";
        }
    }

    public Map<String, ?> getAll() { return sp.getAll(); }

    public void clearApply(String key) { sp.edit().remove(key).apply(); }

    public void clearCommit(String key) { sp.edit().remove(key).commit(); }

    public void clearAll() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            sp.getAll().forEach((BiConsumer<String, Object>) (s, o) -> sp.edit().remove(s).commit());
        } else {
            for (String key : sp.getAll().keySet()) {
                sp.edit().remove(key).commit();
            }
        }
    }

}


