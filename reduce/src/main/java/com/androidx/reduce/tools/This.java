package com.androidx.reduce.tools;

import static android.content.Context.BIND_AUTO_CREATE;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.androidx.reduce.listener.LauncherListener;
import com.androidx.reduce.listener.LauncherResultListener;
import com.androidx.reduce.listener.LauncherStartListener;
import com.androidx.reduce.listener.ThisListener;

import java.lang.ref.WeakReference;

/**
 * @date 2021/06/09
 */
@SuppressWarnings("unused")
public final class This implements ThisListener, LauncherStartListener {

    private static WeakReference<Context> launcherContext;
    private static volatile Runnable run;
    private Handler handler;

    public static This build() {
        return Singleton.builder();
    }

    private This() {
        handler = new Handler(Looper.myLooper());
    }

    /**
     * 初始化回调方法
     */
    public static ActivityResultLauncher<Intent> initLauncher(@NonNull Context context, LauncherResultListener resultListener) {
        launcherContext = new WeakReference<>(context);
        ActivityResultLauncher<Intent> launcher = null;
        if (resultListener != null) {
            ActivityResultContracts.StartActivityForResult forResult = new ActivityResultContracts.StartActivityForResult();
            if (context instanceof FragmentActivity) {
                FragmentActivity activity = (FragmentActivity) context;
                launcher = activity.registerForActivityResult(forResult, result -> {
                    if (result != null && result.getData() != null) {
                        resultListener.callback(result.getResultCode(), result.getData());
                    }
                });
            }
        }
        return launcher;
    }

    public void delay(Runnable run, long t) {
        handler.postDelayed(run, t);
    }

    public void atTime(Runnable run, long t) {
        handler.postAtTime(run, t);
    }

    @Override
    public void start() {
        if (run != null && handler != null) handler.post(run);
    }

    @Override
    public void reset() {
        if (handler == null) {
            handler = new Handler(Looper.getMainLooper());
            if (run != null) handler.post(run);
        }
    }

    @Override
    public void stop() {
        if (run != null) handler.removeCallbacks(run);
        handler.removeCallbacksAndMessages(null);
        handler = null;
    }

    public static void resultListener(Context context, Intent intent, LauncherListener listener) {
        if (intent.getData() != null) {
            Uri uri = intent.getData();
            if (DocumentsContract.isDocumentUri(context, uri)) {
                String documentId = DocumentsContract.getDocumentId(uri);
                String[] selectionArgs = {documentId.split(":")[1]};
                String selection = MediaStore.Images.Media._ID + "=?";
                listener.callback(getDataColumn(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection, selectionArgs));
            } else if ("content".equalsIgnoreCase(uri.getScheme())) {
                listener.callback(getDataColumn(context, uri, null, null));
            }
        }
    }

    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        String[] projection = new String[]{MediaStore.Images.Media.DATA};
        @SuppressLint("Recycle")
        Cursor cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndexOrThrow(projection[0]);
            return cursor.getString(columnIndex);
        } else return "";
    }

    @Override
    public void delayStart(long delayTime) {
        if (run != null && handler != null) handler.postDelayed(run, delayTime);
    }

    @Override
    public void execute() {
        if (run != null && handler != null) handler.post(run);
    }

    public <T extends Activity> LauncherStartListener startLauncher(Class<T> target, ActivityResultLauncher<Intent> launcher) {
        return startLauncher(target, null, false, false, launcher);
    }

    public <T extends Activity> LauncherStartListener startLauncher(Class<T> target, boolean isFinish, ActivityResultLauncher<Intent> launcher) {
        return startLauncher(target, null, isFinish, false, launcher);
    }

    public <T extends Activity> LauncherStartListener startLauncher(Class<T> target, Bundle bundle, ActivityResultLauncher<Intent> launcher) {
        return startLauncher(target, bundle, false, false, launcher);
    }

    public <T extends Activity> LauncherStartListener startLauncher(Class<T> target, Bundle bundle, boolean isFinish, ActivityResultLauncher<Intent> launcher) {
        return startLauncher(target, bundle, isFinish, false, launcher);
    }

    public <T extends Activity> LauncherStartListener startLauncher(Class<T> target, Bundle bundle, boolean isFinish, boolean isAnimation, ActivityResultLauncher<Intent> launcher) {
        run = () -> {
            try {
                if (launcherContext.get() != null) {
                    Intent intent = new Intent(launcherContext.get(), target);
                    if (launcherContext.get() instanceof FragmentActivity) {
                        FragmentActivity activity = (FragmentActivity) launcherContext.get();
                        if (isAnimation) {
                            intent.putExtras(ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
                            if (isFinish) activity.finishAfterTransition();
                        } else {
                            if (isFinish) activity.finish();
                        }
                        if (bundle != null && !bundle.isEmpty()) {
                            if (launcher != null) launcher.launch(intent.putExtras(bundle));
                        } else {
                            if (launcher != null) launcher.launch(intent);
                        }
                    }
                }
            } catch (Exception e) {
                Log.e(getClass().getName(), Log.getStackTraceString(e));
            }
        };
        return this;
    }

    /**
     * 启动activity（无传递参数,默认无动画,默认无跳转下一页关闭上一页,默认无返回对象）
     */
    public <T extends Activity> ThisListener activity(@NonNull Context context, @NonNull Class<T> target) {
        return activity(context, target, null, false, false);
    }

    /**
     * 启动activity（默认无动画,默认无跳转下一页关闭上一页,默认无返回对象）
     */
    public <T extends Activity> ThisListener activity(@NonNull Context context, @NonNull Class<T> target, Bundle bundle) {
        return activity(context, target, bundle, false, false);
    }

    /**
     * 启动activity（无传递参数,默认无动画,默认无返回对象）
     */
    public <T extends Activity> ThisListener activity(@NonNull Context context, @NonNull Class<T> target, boolean isFinish) {
        return activity(context, target, null, isFinish, false);
    }

    /**
     * 启动activity（无传递参数,默认无返回对象）
     */
    public <T extends Activity> ThisListener activity(@NonNull Context context, @NonNull Class<T> target, boolean isFinish, boolean isAnimation) {
        return activity(context, target, null, isFinish, isAnimation);
    }

    /**
     * 启动activity（默认无动画）
     */
    public <T extends Activity> ThisListener activity(@NonNull Context context, @NonNull Class<T> target, Bundle bundle, boolean isFinish) {
        return activity(context, target, bundle, isFinish, false);
    }

    /**
     * 启动activity
     */
    public <T extends Activity> ThisListener activity(@NonNull Context context, @NonNull Class<T> target, Bundle bundle, boolean isFinish, boolean isAnimation) {
        try {
            run = () -> {
                Intent intent = new Intent(context, target);
                if (context instanceof FragmentActivity) {
                    FragmentActivity activity = (FragmentActivity) context;
                    if (isAnimation) {
                        intent.putExtras(ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
                        if (isFinish) activity.finishAfterTransition();
                    } else {
                        if (isFinish) activity.finish();
                    }
                    if (bundle != null) activity.startActivity(intent.putExtras(bundle));
                    else activity.startActivity(intent);
                } else if (context instanceof Activity) {
                    Activity activity = (Activity) context;
                    if (bundle != null) {
                        activity.startActivityForResult(intent.putExtras(bundle), bundle.getInt("requestCode", 0));
                    } else activity.startActivity(intent);
                    if (isAnimation) {
                        intent.putExtras(ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
                        if (isFinish) activity.finishAfterTransition();
                    } else {
                        if (isFinish) activity.finish();
                    }
                } else {
                    if (bundle != null) context.startActivity(intent, bundle);
                    else context.startActivity(intent);
                }
            };
            return this;
        } catch (Exception e) {
            Log.e(getClass().getName(), Log.getStackTraceString(e));
            return this;
        }
    }

    /**
     * 启动service（无参）
     */
    public <T extends Service> ThisListener service(@NonNull Context c, @NonNull Class<T> c1) {
        return service(c, c1, null);
    }

    /**
     * 停止service（无参）
     */
    public ThisListener serviceStop(@NonNull Context c, @NonNull Class<?> c1) {
        run = () -> c.stopService(new Intent(c, c1).addFlags(FLAG_ACTIVITY_NEW_TASK));
        return this;
    }

    /**
     * 启动service（带参）
     */
    public <T extends Service> ThisListener service(@NonNull Context c, @NonNull Class<T> c1, Bundle b) {
        run = () -> {
            Intent intent = new Intent(c, c1).addFlags(FLAG_ACTIVITY_NEW_TASK);
            if (b != null) intent.putExtras(b);
            c.startService(intent);
        };
        return this;
    }

    /**
     * 启动service（无参）
     */
    public ThisListener bindService(@NonNull Context c, @NonNull Class<?> c1, ServiceConnection c2) {
        return bindService(c, c1, null, c2);
    }

    /**
     * 启动service（带参）
     */
    public ThisListener bindService(@NonNull Context c, @NonNull Class<?> c1, Bundle b, ServiceConnection c2) {
        run = () -> {
            Intent intent = new Intent(c, c1);
            if (b != null) intent.putExtras(b);
            c.bindService(intent, c2, BIND_AUTO_CREATE);
        };
        return this;
    }

    /**
     * 启动sendBroadcast（无参）
     */
    public ThisListener broadcast(@NonNull Context c, String action) {
        return broadcast(c, action, null);
    }

    /**
     * 启动sendBroadcast（带参）
     */
    public ThisListener broadcast(@NonNull Context c, String action, Bundle b) {
        run = () -> c.sendBroadcast(new Intent(action).putExtras(b == null ? new Bundle() : b));

        return this;
    }

    /**
     * 调用系统活动
     */
    public ThisListener resultAction(String action, ActivityResultLauncher<Intent> launcher) {
        return resultAction(action, null, launcher);
    }

    public ThisListener resultAction(String action, String type, ActivityResultLauncher<Intent> launcher) {
        run = () -> {
            Intent intent = new Intent(action);
            if (type != null) intent.setType(type);
            launcher.launch(intent);
        };
        return this;
    }

    /**
     * 调用系统图库功能
     */
    public ThisListener resultAction(AppCompatActivity context, ActivityResultLauncher<Intent> launcher) {
        return resultAction("android.intent.action.GET_CONTENT", "image/*", launcher);
    }

    private static final class Singleton {

        private static final This INSTANCE = new This();
        private static volatile This newInstance;

        private static This builder() {
            if (newInstance == null) synchronized (This.class) {
                if (newInstance == null) newInstance = INSTANCE;
            }
            return newInstance;
        }

    }

}
