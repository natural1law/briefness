package com.androidx.reduce.utils;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

/**
 * @date 2020/07/10
 */
public final class This {

    private static volatile This instance = Singleton.INSTANCE;
    private static final String TAG = "ThisException";
    private static volatile Handler handler = new Handler(Looper.getMainLooper());

    private This() {
    }

    public static This builder() {
        try {
            synchronized (This.class) {
                return instance;
            }
        } catch (Exception e) {
            return new This();
        }
    }

    /**
     * 延时关闭
     */
    public void delayClose(FragmentActivity aThis, long time) {
        try {
            handler.postDelayed(aThis::finish, time);
        } catch (Exception e) {
            Log.e(TAG, String.valueOf(e.getMessage()));
        }
    }

    /**
     * 延时关闭
     */
    public void delayClose(AppCompatActivity aThis, long time) {
        try {
            handler.postDelayed(aThis::finish, time);
        } catch (Exception e) {
            Log.e(TAG, String.valueOf(e.getMessage()));
        }

    }

    /**
     * 延时关闭
     */
    public <T> void delayClose(AppCompatActivity aThis, Class<T> cls, long time) {
        try {
            handler.postDelayed(() -> {
                aThis.startActivity(new Intent(aThis, cls), ActivityOptions.makeSceneTransitionAnimation(aThis).toBundle());
                aThis.finishAfterTransition();
            }, time);

        } catch (Exception e) {
            Log.e(TAG, String.valueOf(e.getMessage()));
        }
    }

    /**
     * 延时加载
     */
    public void delayLoading(Runnable run, long time) {
        try {
            handler.postDelayed(run, time);
        } catch (Exception e) {
            Log.e(TAG, String.valueOf(e.getMessage()));
        }

    }

    /**
     * 启动activity（无参）
     */
    public void startActivity(AppCompatActivity aThis, Class<?> cls) {
        try {
            handler.post(() -> aThis.startActivity(new Intent(aThis, cls)));
        } catch (Exception e) {
            Log.e(TAG, String.valueOf(e.getMessage()));
        }

    }

    /**
     * 启动activity（无参）
     */
    public void startActivityFinish(AppCompatActivity aThis, Class<?> cls) {
        try {
            handler.post(() -> {
                aThis.startActivity(new Intent(aThis, cls));
                aThis.finish();
            });
        } catch (Exception e) {
            Log.e(TAG, String.valueOf(e.getMessage()));
        }

    }

    /**
     * 启动activity（无参）
     */
    public void startActivity(FragmentActivity aThis, Class<?> cls) {
        try {
            handler.post(() -> aThis.startActivity(new Intent(aThis, cls)));
        } catch (Exception e) {
            Log.e(TAG, String.valueOf(e.getMessage()));
        }
    }

    /**
     * 启动activity（无参）
     */
    public void startActivityFinish(FragmentActivity aThis, Class<?> cls) {
        try {
            handler.post(() -> {
                aThis.startActivity(new Intent(aThis, cls));
                aThis.finish();
            });
        } catch (Exception e) {
            Log.e(TAG, String.valueOf(e.getMessage()));
        }
    }

    /**
     * 启动service（无参）
     */
    public void start(Context context, Class<?> cls) {
        try {
            context.startActivity(new Intent(context, cls).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        } catch (Exception e) {
            Log.e(TAG, String.valueOf(e.getMessage()));
        }
    }

    /**
     * 启动service（无参）
     */
    public void startService(Context context, Class<?> cls) {
        try {
            context.startService(new Intent(context, cls));
        } catch (Exception e) {
            Log.e(TAG, String.valueOf(e.getMessage()));
        }

    }

    /**
     * 启动service（带参）
     */
    public void startService(Context context, Class<?> cls, Bundle bundle) {
        try {
            context.startService(new Intent(context, cls).putExtras(bundle));
        } catch (Exception e) {
            Log.e(TAG, String.valueOf(e.getMessage()));
        }

    }

    /**
     * 停止service（无参）
     */
    public void stopService(Context context, Class<?> cls) {
        try {
            context.stopService(new Intent(context, cls));
        } catch (Exception e) {
            Log.e(TAG, String.valueOf(e.getMessage()));
        }

    }

    /**
     * 启动service（无参）
     */
    public void bindService(Context context, Class<?> cls, ServiceConnection connection) {
        try {
            context.bindService(new Intent(context, cls), connection, Context.BIND_AUTO_CREATE);
        } catch (Exception e) {
            Log.e(TAG, String.valueOf(e.getMessage()));
        }

    }

    /**
     * 启动service（带参）
     */
    public void bindService(Context context, Class<?> cls, Bundle bundle, ServiceConnection connection) {
        try {
            context.bindService(new Intent(context, cls).putExtras(bundle), connection, Context.BIND_AUTO_CREATE);
        } catch (Exception e) {
            Log.e(TAG, String.valueOf(e.getMessage()));
        }

    }

    /**
     * 启动sendBroadcast（无参）
     */
    public void startBroadcast(Context context, String action) {
        try {
            context.sendBroadcast(new Intent(action));
        } catch (Exception e) {
            Log.e(TAG, String.valueOf(e.getMessage()));
        }

    }

    /**
     * 启动sendBroadcast（带参）
     */
    public void startBroadcast(Context context, String action, Bundle bundle) {
        try {
            context.sendBroadcast(new Intent(action).putExtras(bundle));
        } catch (Exception e) {
            Log.e(TAG, String.valueOf(e.getMessage()));
        }

    }

    /**
     * 启动activity（带参）
     */
    public void startContext(Context context, Class<?> cls, Bundle bundle) {
        try {
            context.startActivity(new Intent(context, cls).putExtras(bundle));
        } catch (Exception e) {
            Log.e(TAG, String.valueOf(e.getMessage()));
        }

    }

    /**
     * 启动activity（带参）
     */
    public void startActivity(AppCompatActivity aThis, Class<?> cls, Bundle bundle) {
        try {
            aThis.startActivity(new Intent(aThis, cls).putExtras(bundle), ActivityOptions.makeSceneTransitionAnimation(aThis).toBundle());
        } catch (Exception e) {
            Log.e(TAG, String.valueOf(e.getMessage()));
        }

    }

    /**
     * 启动activity（带参）
     */
    public void startActivity(FragmentActivity aThis, Class<?> cls, Bundle bundle) {
        try {
            aThis.startActivity(new Intent(aThis, cls).putExtras(bundle), ActivityOptions.makeSceneTransitionAnimation(aThis).toBundle());
        } catch (Exception e) {
            Log.e(TAG, String.valueOf(e.getMessage()));
        }

    }

    /**
     * 启动activity（无参带返回）
     */
    public void startResult(AppCompatActivity aThis, Class<?> cla, int code) {
        try {
            aThis.startActivityForResult(new Intent(aThis, cla), code, ActivityOptions.makeSceneTransitionAnimation(aThis).toBundle());
        } catch (Exception e) {
            Log.e(TAG, String.valueOf(e.getMessage()));
        }

    }

    /**
     * 启动activity（带参带返回）
     */
    public void startResult(AppCompatActivity aThis, Class<?> cla, Bundle bundle, int code) {
        try {
            aThis.startActivityForResult(new Intent(aThis, cla).putExtras(bundle), code, ActivityOptions.makeSceneTransitionAnimation(aThis).toBundle());
        } catch (Exception e) {
            Log.e(TAG, String.valueOf(e.getMessage()));
        }

    }

    /**
     * 启动activity（无参带返回）
     */
    public void startResult(FragmentActivity aThis, Class<?> cla, int code) {
        try {
            aThis.startActivityForResult(new Intent(aThis, cla), code, ActivityOptions.makeSceneTransitionAnimation(aThis).toBundle());
        } catch (Exception e) {
            Log.e(TAG, String.valueOf(e.getMessage()));
        }

    }

    /**
     * 启动activity（带参带返回）
     */
    public void startResult(FragmentActivity aThis, Class<?> cla, Bundle bundle, int code) {
        try {
            aThis.startActivityForResult(new Intent(aThis, cla).putExtras(bundle), code, ActivityOptions.makeSceneTransitionAnimation(aThis).toBundle());
        } catch (Exception e) {
            Log.e(TAG, String.valueOf(e.getMessage()));
        }

    }

    private static final class Singleton {
        private Singleton() {
        }

        private static final This INSTANCE = new This();
    }

}
