package com.androidx.reduce.tools;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.androidx.reduce.listener.ToastListener;

import java.lang.ref.WeakReference;

import es.dmoral.toasty.Toasty;

@SuppressWarnings("ALL")
public final class Toasts implements ToastListener {

    private static volatile Toasts instance;

    private static boolean debug = true;
    private String msg = "";
    private int duration;
    private Drawable icon;
    private boolean withIcon;
    private final WeakReference<Context> wrc;

    private Toasts(Context context) {
        this.wrc = new WeakReference<>(context);
    }

    public static Toasts builder(Context context) {
        if (instance == null) synchronized (Toasts.class) {
            if (instance == null) instance = new Toasts(context);
        }
        return instance;
    }

    public static boolean isDebug() {
        return debug;
    }

    /**
     * debug调试
     *
     * @param state true--启动debug，false--关闭debug
     */
    public static void setDebug(boolean state) {
        debug = state;
    }

    /**
     * 默认标记
     *
     * @param msg 内容
     */
    public static <M> void d(M msg) {
        d("SystemLog", msg);
    }

    /**
     * 模式D
     *
     * @param mark 标记
     * @param msg  内容
     */
    public static <V extends String, M> void d(V mark, M msg) {
        if (debug) if (msg instanceof Throwable) {
            Log.d(String.valueOf(mark), Log.getStackTraceString((Throwable) msg));
        } else Log.d(String.valueOf(mark), String.valueOf(msg));
    }

    public static <M> void i(M msg) {
        i("SystemLog", msg);
    }

    /**
     * 模式I
     *
     * @param mark 标记
     * @param msg  内容
     */
    public static <V extends String, M> void i(V mark, M msg) {
        if (debug) if (msg instanceof Throwable) {
            Log.i(String.valueOf(mark), Log.getStackTraceString((Throwable) msg));
        } else Log.i(String.valueOf(mark), String.valueOf(msg));
    }

    public static <M> void w(M msg) {
        w("SystemLog", msg);
    }

    /**
     * 模式W
     *
     * @param mark 标记
     * @param msg  内容
     */
    public static <V extends String, M> void w(V mark, M msg) {
        if (debug) if (msg instanceof Throwable) {
            Log.w(String.valueOf(mark), Log.getStackTraceString((Throwable) msg));
        } else Log.w(String.valueOf(mark), String.valueOf(msg));
    }

    public static <M> void e(M msg) {
        e("SystemLog", msg);
    }

    /**
     * 模式E
     *
     * @param mark 标记
     * @param msg  内容
     */
    public static <V extends String, M> void e(V mark, M msg) {
        if (msg instanceof Throwable) {
            Log.e(String.valueOf(mark), Log.getStackTraceString((Throwable) msg));
        } else Log.e(String.valueOf(mark), String.valueOf(msg));
    }

    public static <M> void v(M msg) {
        v("SystemLog", msg);
    }

    /**
     * 模式V
     *
     * @param mark 标记
     * @param msg  内容
     */
    public static <V extends String, M> void v(V mark, M msg) {
        if (debug) if (msg instanceof Throwable) {
            Log.v(String.valueOf(mark), Log.getStackTraceString((Throwable) msg));
        } else Log.v(String.valueOf(mark), String.valueOf(msg));
    }

    public static <M> void wtf(M msg) {
        wtf("SystemLog", msg);
    }

    /**
     * 模式WTF
     *
     * @param mark 标记
     * @param msg  内容
     */
    public static <V extends String, M> void wtf(V mark, M msg) {
        if (debug) if (msg instanceof Throwable) {
            Log.wtf(String.valueOf(mark), Log.getStackTraceString((Throwable) msg));
        } else Log.wtf(String.valueOf(mark), String.valueOf(msg));
    }

    /**
     * 添加消息
     */
    public <M> ToastListener setMsg(M msg) {
        return this.setMsg(msg, Toast.LENGTH_SHORT, null, true);
    }

    /**
     * 添加消息
     */
    public <M> ToastListener setMsg(M msg, int duration) {
        return this.setMsg(msg, duration, null, true);
    }

    /**
     * 添加消息
     */
    public <M> ToastListener setMsg(M msg, boolean withIcon) {
        return this.setMsg(msg, Toast.LENGTH_SHORT, null, withIcon);
    }

    /**
     * 添加消息
     */
    public <M> ToastListener setMsg(M msg, Drawable icon) {
        return this.setMsg(msg, Toast.LENGTH_SHORT, icon, true);
    }

    /**
     * 添加消息
     */
    public <M> ToastListener setMsg(M msg, int duration, Drawable icon) {
        return this.setMsg(msg, duration, icon, true);
    }

    /**
     * 添加消息
     */
    public <M> ToastListener setMsg(M msg, int duration, boolean withIcon) {
        return this.setMsg(msg, duration, null, withIcon);
    }

    /**
     * 添加消息
     */
    public <M> ToastListener setMsg(M msg, Drawable icon, boolean withIcon) {
        return this.setMsg(msg, Toast.LENGTH_SHORT, icon, withIcon);
    }

    /**
     * 添加消息
     */
    public <M> ToastListener setMsg(M msg, int duration, Drawable icon, boolean withIcon) {
        String value = msg == null ? "" : String.valueOf(msg).equals("null") ? "" : String.valueOf(msg);
        this.msg = value.replace("\"", "");
        this.duration = duration;
        this.icon = icon;
        this.withIcon = withIcon;
        return this;
    }

    /**
     * 默认提示
     */
    @Override
    public void showNormal() {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            Toasty.normal(wrc.get(), msg, duration, icon, withIcon).show();
        } else {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(() -> Toasty.normal(wrc.get(), msg, duration, icon, withIcon).show());
        }
    }

    /**
     * 成功提示
     */
    @Override
    public void showSuccess() {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            Toasty.success(wrc.get(), msg, duration, withIcon).show();
        } else {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(() -> Toasty.success(wrc.get(), msg, duration, withIcon).show());
        }
    }

    /**
     * 错误提示
     */
    @Override
    public void showError() {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            Toasty.error(wrc.get(), msg, duration, withIcon).show();
        } else {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(() -> Toasty.error(wrc.get(), msg, duration, withIcon).show());
        }
    }

    /**
     * 警告提示
     */
    @Override
    public void showWarning() {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            Toasty.warning(wrc.get(), msg, duration, withIcon).show();
        } else {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(() -> Toasty.warning(wrc.get(), msg, duration, withIcon).show());
        }
    }

    /**
     * 信息提示
     */
    @Override
    public void showInfo() {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            Toasty.info(wrc.get(), msg, duration, withIcon).show();
        } else {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(() -> Toasty.info(wrc.get(), msg, duration, withIcon).show());
        }
    }

    /**
     * 系统原始提示
     */
    @Override
    public void showOriginal() {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            Toast.makeText(wrc.get(), msg, duration).show();
        } else {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(() -> Toast.makeText(wrc.get(), msg, duration).show());
        }
    }

}
