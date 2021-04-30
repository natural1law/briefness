package com.androidx.briefness.base;

import android.app.Application;
import android.content.Context;

import com.androidx.reduce.tools.This;
import com.androidx.reduce.tools.Toasts;

public class App extends Application {

    public static volatile Toasts toasts;
    public static final This appThis = This.builder();

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        toasts = Toasts.builder(base);
    }
}
