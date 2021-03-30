package com.androidx.reduce.tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatTextView;

import com.androidx.reduce.R;
import com.androidx.reduce.toast.ConfusingToastView;
import com.androidx.reduce.toast.DefaultToastView;
import com.androidx.reduce.toast.ErrorToastView;
import com.androidx.reduce.toast.InfoToastView;
import com.androidx.reduce.toast.SuccessToastView;

import java.lang.ref.WeakReference;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;

@SuppressWarnings("ALL")
@SuppressLint("InflateParams")
public final class Toasts {

    private static volatile Toasts instance;
    private static volatile Handler handler = new Handler(Looper.getMainLooper());

    public static final int ERROR = -1;
    public static final int DEFAULT = 0;
    public static final int SUCCESS = 1;
    public static final int INFO = 2;
    public static final int CONFUSING = 3;
    private static final boolean NO = false;
    private volatile WeakReference<Context> wrc;

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
     * 成功提示
     */
    private void success(Toast toast, String msg) {
        try {
            View layout = LayoutInflater.from(wrc.get()).inflate(R.layout.success_toast_layout, null, NO);
            AppCompatTextView text = layout.findViewById(R.id.toastMessage);
            SuccessToastView successToastView = layout.findViewById(R.id.successView);
            successToastView.startAnim(2000);
            text.setText(msg);
            text.setBackgroundResource(R.drawable.success_toast);
            text.setTextColor(Color.parseColor("#FFFFFF"));
            toast.setView(layout);
        } catch (Exception e) {
            Log.e("ToastSuccessException", String.valueOf(e.getMessage()));
        }
    }

    /**
     * 信息提示
     */
    private void info(Toast toast, String msg) {
        try {
            View layout = LayoutInflater.from(wrc.get()).inflate(R.layout.info_toast_layout, null, false);
            AppCompatTextView text = layout.findViewById(R.id.toastMessage);
            InfoToastView infoToastView = layout.findViewById(R.id.infoView);
            infoToastView.startAnim(2000);
            text.setText(msg);
            text.setBackgroundResource(R.drawable.info_toast);
            text.setTextColor(Color.parseColor("#FFFFFF"));
            toast.setView(layout);
        } catch (Exception e) {
            Log.e("ToastInfoException", String.valueOf(e.getMessage()));
        }
    }

    /**
     * 错误提示
     */
    private void error(Toast toast, String msg) {
        try {
            View layout = LayoutInflater.from(wrc.get()).inflate(R.layout.error_toast_layout, null, false);
            AppCompatTextView text = layout.findViewById(R.id.toastMessage);
            ErrorToastView errorToastView = layout.findViewById(R.id.errorView);
            errorToastView.startAnim(2000);
            text.setText(msg);
            text.setBackgroundResource(R.drawable.error_toast);
            text.setTextColor(Color.parseColor("#FFFFFF"));
            toast.setView(layout);
        } catch (Exception e) {
            Log.e("ToastErrorException", String.valueOf(e.getMessage()));
        }
    }

    /**
     * 默认提示
     */
    private void defaults(Toast toast, String msg) {
        try {
            View layout = LayoutInflater.from(wrc.get()).inflate(R.layout.default_toast_layout, null, false);
            AppCompatTextView text = layout.findViewById(R.id.toastMessage);
            DefaultToastView defaultToastView = layout.findViewById(R.id.defaultView);
            defaultToastView.startAnim(2000);
            text.setText(msg);
            text.setBackgroundResource(R.drawable.default_toast);
            text.setTextColor(Color.parseColor("#FFFFFF"));
            toast.setView(layout);
        } catch (Exception e) {
            Log.e("ToastDefaultsException", String.valueOf(e.getMessage()));
        }
    }

    /**
     * 不知道提示
     */
    private void confusing(Toast toast, String msg) {
        try {
            View layout = LayoutInflater.from(wrc.get()).inflate(R.layout.confusing_toast_layout, null, false);
            AppCompatTextView text = layout.findViewById(R.id.toastMessage);
            ConfusingToastView confusingToastView = layout.findViewById(R.id.confusingView);
            confusingToastView.startAnim(2000);
            text.setText(msg);
            text.setBackgroundResource(R.drawable.confusing_toast);
            text.setTextColor(Color.parseColor("#FFFFFF"));
            toast.setView(layout);
        } catch (Exception e) {
            Log.e("ToastConfusingException", String.valueOf(e.getMessage()));
        }
    }

    public <T> void show(T message, int type) {
        String msg = String.valueOf(message);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            Toast toast = new Toast(wrc.get());
            handler.post(() -> {
                switch (type) {
                    case ERROR:
                        error(toast, msg);
                        break;
                    case DEFAULT:
                        defaults(toast, msg);
                        break;
                    case SUCCESS:
                        success(toast, msg);
                        break;
                    case INFO:
                        info(toast, msg);
                        break;
                    case CONFUSING:
                        confusing(toast, msg);
                        break;
                    default:
                        toast.setDuration(LENGTH_LONG);
                        toast.show();
                }
            });
        } else {
            Toast.makeText(wrc.get(), msg, LENGTH_SHORT).show();
        }
    }

    public <T> void show(T msg) {
        handler.post(() -> Toast.makeText(wrc.get(), String.valueOf(msg), LENGTH_SHORT).show());
    }

}
