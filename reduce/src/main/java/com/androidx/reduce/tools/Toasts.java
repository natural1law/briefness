package com.androidx.reduce.tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import java.lang.ref.WeakReference;

import es.dmoral.toasty.Toasty;

import static android.widget.Toast.LENGTH_SHORT;

@SuppressWarnings("ALL")
@SuppressLint("InflateParams")
public final class Toasts {

    private static volatile Toasts instance;
    private static volatile Handler handler = new Handler(Looper.getMainLooper());

    private static final boolean NO = false;
    private volatile WeakReference<Context> wrc;
    private String msg;

    private Toasts(Context context) {
        this.wrc = new WeakReference<>(context);
    }

    public static Toasts builder(Context context) {
        try {
            if (instance == null) {
                synchronized (Toasts.class) {
                    if (instance == null) {
                        instance = new Toasts(context);
                    }
                }
            }
            return instance;
        } catch (Exception e) {
            return new Toasts(context);
        }
    }

    /**
     * 添加消息
     */
    public <T> Toasts setMsg(T message) {
        msg = String.valueOf(message);
        return this;
    }

    /**
     * 错误提示
     */
    public <T> void showError() {
        handler.post(() -> Toasty.error(wrc.get(), msg, Toast.LENGTH_SHORT, true).show());
    }

    public <T> void showError(boolean flag) {
        handler.post(() -> Toasty.error(wrc.get(), msg, Toast.LENGTH_SHORT, flag).show());
    }

    /**
     * 成功提示
     */
    public <T> void showSuccess() {
        handler.post(() -> Toasty.success(wrc.get(), msg, Toast.LENGTH_SHORT, true).show());
    }

    public <T> void showSuccess(boolean flag) {
        handler.post(() -> Toasty.success(wrc.get(), msg, Toast.LENGTH_SHORT, flag).show());
    }

    /**
     * 信息提示
     */
    public <T> void showInfo() {
        handler.post(() -> Toasty.info(wrc.get(), msg, Toast.LENGTH_SHORT, true).show());
    }

    public <T> void showInfo(boolean flag) {
        handler.post(() -> Toasty.info(wrc.get(), msg, Toast.LENGTH_SHORT, flag).show());
    }

    /**
     * 警告提示
     */
    public <T> void showWarning() {
        handler.post(() -> Toasty.warning(wrc.get(), msg, Toast.LENGTH_SHORT, true).show());
    }

    public <T> void showWarning(boolean flag) {
        handler.post(() -> Toasty.warning(wrc.get(), msg, Toast.LENGTH_SHORT, flag).show());
    }

    /**
     * 默认提示
     */
    public <T> void showNormal() {
        handler.post(() -> Toasty.normal(wrc.get(), msg).show());
    }

    /**
     * 系统原始提示
     */
    public <T> void showOriginal(T msg) {
        handler.post(() -> Toast.makeText(wrc.get(), String.valueOf(msg), LENGTH_SHORT).show());
    }


}
