package com.androidx.reduce.tools;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import java.util.Map;
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

    public String getApplyStr(String key) {
        return sp != null ? sp.getString(key, "") : "";
    }

    public Integer getApplyInt(String key) {
        return sp != null ? sp.getInt(key, 0) : 0;
    }

    public Float getApplyFloat(String key) {
        return sp != null ? sp.getFloat(key, 0f) : 0f;
    }

    public Long getApplyLong(String key) {
        return sp != null ? sp.getLong(key, 0) : 0;
    }

    public Boolean getApplyBoolean(String key) {
        return sp != null && sp.getBoolean(key, false);
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

    public String getCommitStr(String key) {
        return sp != null ? sp.getString(key, "") : "";
    }

    public Integer getCommitInt(String key) {
        return sp != null ? sp.getInt(key, 0) : 0;
    }

    public Float getCommitFloat(String key) {
        return sp != null ? sp.getFloat(key, 0f) : 0f;
    }

    public Long getCommitLong(String key) {
        return sp != null ? sp.getLong(key, 0) : 0;
    }

    public Boolean getCommitBoolean(String key) {
        return sp != null && sp.getBoolean(key, false);
    }

    public void clearApply(String key) {
        sp.edit().remove(key).apply();
    }

    public void clearCommit(String key) {
        sp.edit().remove(key).commit();
    }

    public Map<String, ?> getAll() {
        return sp.getAll();
    }

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


