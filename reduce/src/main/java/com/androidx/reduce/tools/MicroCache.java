package com.androidx.reduce.tools;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * 轻量级程序缓储存器
 *
 * @date 2021/02/20
 */
@SuppressWarnings({"unused", "RedundantSuppression"})
public final class MicroCache {

    private final SharedPreferences sp;
    private final SharedPreferences.Editor editor;

    private MicroCache(Context context, String name, int mode) {
        sp = context.getSharedPreferences(name, mode);
        editor = sp.edit();
    }

    public static MicroCache getInstance(Context context) {
        return getInstance(context, "MicroCache", Activity.MODE_PRIVATE);
    }

    public static MicroCache getInstance(Context context, String name) {
        return getInstance(context, name, Activity.MODE_PRIVATE);
    }

    public static MicroCache getInstance(Context context, String name, int mode) {
        return Singleton.newInstance(context, name, mode);
    }

    @SuppressWarnings("unchecked")
    public void setApply(String key, Object value) {
        if (value == null) return;
        if (value instanceof String) {
            editor.putString(key, String.valueOf(value)).apply();
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value).apply();
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value).apply();
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value).apply();
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value).apply();
        } else if (value instanceof Set<?>) {
            editor.putStringSet(key, (Set<String>) value).apply();
        }
    }

    @SuppressWarnings("unchecked")
    public void setCommit(String key, Object value) {
        if (value == null) return;
        if (value instanceof String) {
            editor.putString(key, String.valueOf(value)).commit();
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value).commit();
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value).commit();
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value).commit();
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value).commit();
        } else if (value instanceof Set<?>) {
            editor.putStringSet(key, (Set<String>) value).commit();
        }
    }

    public String getString(String key) {
        return sp.getString(key, "");
    }

    public int getInt(String key) {
        return sp.getInt(key, 0);
    }

    public long getLong(String key) {
        return sp.getLong(key, 0);
    }

    public float getFloat(String key) {
        return sp.getFloat(key, 0);
    }

    public boolean getBoolean(String key) {
        return sp.getBoolean(key, false);
    }

    public Set<String> getStringSet(String key) {
        return sp.getStringSet(key, new HashSet<>());
    }

    public Map<String, ?> getAll() {
        return sp.getAll() == null ? new WeakHashMap<>() : sp.getAll();
    }

    public void clear() {
        editor.clear().commit();
    }

    public void clearApply(String key) {
        editor.remove(key).apply();
    }

    public void clearCommit(String key) {
        editor.remove(key).commit();
    }

    private static final class Singleton {

        private static volatile MicroCache mc;

        private Singleton() {
        }

        private static MicroCache newInstance(Context context, String name, int mode) {
            if (mc == null) synchronized (MicroCache.class) {
                if (mc == null) mc = new MicroCache(context, name, mode);
            }
            return mc;
        }

    }
}


