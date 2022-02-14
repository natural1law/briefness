package com.androidx.view.screen;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.Manifest;
import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.media.projection.MediaProjectionManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.androidx.view.screen.config.ScreenConfig;
import com.androidx.view.screen.intertaces.ScreenCallbackListener;
import com.androidx.view.screen.intertaces.ScreenServiceListener;
import com.androidx.view.screen.service.ScreenService;

import java.lang.ref.WeakReference;

/**
 * 屏幕录制
 *
 * @date 2020/06/15
 */
public final class ScreenRecording {

    private final WeakReference<AppCompatActivity> wrThis;

    private Notification notification;
    private ScreenServiceListener serviceListener;

    public static ScreenRecording build(AppCompatActivity activity) {
        return new ScreenRecording(activity);
    }

    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            if (service instanceof ScreenService.ScreenBinder) {
                serviceListener = ((ScreenService.ScreenBinder) service).builder();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceListener = null;
        }
    };

    /**
     * 启用屏幕截图
     */
    public void onStartCapture(ScreenCallbackListener callback) {
        onStartCapture(ScreenConfig.builder().setActivity(wrThis.get()).build(), callback);
    }

    /**
     * 启用屏幕截图
     */
    public void onStartCapture(ScreenConfig config, ScreenCallbackListener callback) {
        if (serviceListener != null) {
            serviceListener.startCapture(callback, config);
        } else {
            Log.e("开始屏幕录制", "服务已关闭");
        }
    }

    /**
     * 启用媒体录屏服务
     * 开始屏幕录制
     */
    public void onStartRecording(ScreenCallbackListener callback) {
        onStartRecording(ScreenConfig.builder().setActivity(wrThis.get()).build(), callback);
    }

    /**
     * 启用媒体录屏服务
     * 开始屏幕录制
     */
    public void onStartRecording(ScreenConfig config, ScreenCallbackListener callback) {
        if (serviceListener != null) {
            serviceListener.startRecording(callback, config);
        } else {
            Log.e("开始屏幕录制", "服务已关闭");
        }
    }

    /**
     * 初始化系统通知
     *
     * @param notification 通知
     */
    public ScreenRecording setNotification(Notification notification) {
        this.notification = notification;
        return this;
    }

    /**
     * 停止录制
     */
    public void onStopRecording() {
        try {
            if (serviceListener != null) serviceListener.stopRecording();
        } catch (Exception e) {
            Log.e("停止媒体服务异常", Log.getStackTraceString(e));
        }
    }

    /**
     * 销毁服务
     */
    public void onDestroy() {
        try {
            if (serviceListener != null) serviceListener.destroy();
            if (serviceListener != null) wrThis.get().unbindService(serviceConnection);
            if (serviceListener != null) serviceListener = null;
        } catch (Exception e) {
            Log.e("销毁媒体服务异常", Log.getStackTraceString(e));
        }
    }

    /**
     * 设置虚拟图像
     */
    private void setVirtualDisplay(int resCode, @NonNull Intent intent) {
        try {
            serviceListener.createVirtualDisplay(resCode, intent, notification);
        } catch (Exception e) {
            Log.e("设置虚拟图像异常", Log.getStackTraceString(e));
        }
    }

    /**
     * 权限判断
     */
    private boolean isPermission(AppCompatActivity aThis) {
        try {
            if ((ContextCompat.checkSelfPermission(aThis, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                    && (ContextCompat.checkSelfPermission(aThis, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)
                    && (ContextCompat.checkSelfPermission(aThis, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                    && (ContextCompat.checkSelfPermission(aThis, Manifest.permission.SYSTEM_ALERT_WINDOW) != PackageManager.PERMISSION_GRANTED)) {
                // 检查该权限是否已经获取
                String camera = Manifest.permission.CAMERA;
                String recordAudio = Manifest.permission.RECORD_AUDIO;
                String storage = Manifest.permission.WRITE_EXTERNAL_STORAGE;
                String suspension = Manifest.permission.SYSTEM_ALERT_WINDOW;
                ActivityCompat.requestPermissions(aThis, new String[]{camera, recordAudio, storage, suspension}, 888);
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

    private ScreenRecording(AppCompatActivity aThis) {
        wrThis = new WeakReference<>(aThis);
        try {
            if (isPermission(aThis)) {
                serviceListener = new ScreenService();
                Intent intent = new Intent(wrThis.get(), ScreenService.class);
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                wrThis.get().bindService(intent, serviceConnection, Service.BIND_AUTO_CREATE);
                // 启动媒体投影服务
                MediaProjectionManager mpm = (MediaProjectionManager) aThis.getSystemService(Context.MEDIA_PROJECTION_SERVICE);
                aThis.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                    if (result == null || result.getData() == null) return;
                    setVirtualDisplay(result.getResultCode(), result.getData());
                }).launch(mpm.createScreenCaptureIntent());
            } else Toast.makeText(aThis, "请赋予权限，避免程序异常", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("初始化服务异常", Log.getStackTraceString(e));
        }
    }

}
