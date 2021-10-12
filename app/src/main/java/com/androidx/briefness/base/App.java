package com.androidx.briefness.base;

import android.app.Application;
import android.content.Context;

import com.androidx.reduce.tools.MicroCache;
import com.androidx.reduce.tools.This;
import com.androidx.reduce.tools.Toasts;

public class App extends Application {

    public static volatile Toasts toasts;
    public static volatile This appThis;
    public static volatile MicroCache mc;

    static {
        Runtime.getRuntime().loadLibrary("url");
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        toasts = Toasts.builder(base).setDebug(true);
        mc = MicroCache.getInstance(base);
        appThis = This.build();
    }
}
