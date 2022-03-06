package com.androidx.briefness.base;

import android.app.Application;
import android.content.Context;

import com.androidx.reduce.tools.MicroCache;
import com.androidx.reduce.tools.This;
import com.androidx.reduce.tools.Toasts;
import com.tencent.mmkv.MMKV;

public class App extends Application {

    public static volatile Toasts toasts;
    public static volatile This appThis;
    public static volatile MicroCache mc;
    public static volatile MMKV kv;

    static {
        Runtime.getRuntime().loadLibrary("url");
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MMKV.initialize(base);
        kv = MMKV.defaultMMKV();
        kv.close();
        toasts = Toasts.builder(base);
        mc = MicroCache.getInstance(base);
        appThis = This.build();
    }
}
