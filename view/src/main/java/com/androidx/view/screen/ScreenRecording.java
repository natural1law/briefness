package com.androidx.view.screen;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.media.projection.MediaProjectionManager;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.androidx.view.screen.intertaces.NotificationListener;
import com.androidx.view.screen.intertaces.ScreenCallbackListener;
import com.androidx.view.screen.intertaces.ScreenListener;
import com.androidx.view.screen.intertaces.ScreenService;

/**
 * 屏幕录制
 *
 * @date 2020/06/15
 */
public final class ScreenRecording {

    private static volatile ScreenRecording instance = Singleton.INSTANCE;
    public static final int REQUEST_CODE = 57;

    private NotificationListener notification;
    private ScreenListener screenService;
    private DisplayMetrics displayMetrics;
    private ServiceConnection serviceConnection;
    private MediaProjectionManager mediaProjectionManager;

    public static ScreenRecording build() {
        try {
            synchronized (ScreenRecording.class) {
                return instance;
            }
        } catch (Exception e) {
            return new ScreenRecording();
        }
    }

    /**
     * 启动媒体录屏服务
     */
    public void startService(AppCompatActivity aThis) {
        try {
            if (mediaProjectionManager != null) {
                return;
            }
            if (isPermission(aThis)) {
                screenService = new ScreenService();
                // 此处宽高需要获取屏幕完整宽高，否则截屏图片会有白/黑边
                displayMetrics = aThis.getResources().getDisplayMetrics();
                // 启动媒体投影服务
                mediaProjectionManager = (MediaProjectionManager) aThis.getSystemService(Context.MEDIA_PROJECTION_SERVICE);
                if (mediaProjectionManager != null) {
                    aThis.startActivityForResult(mediaProjectionManager.createScreenCaptureIntent(), REQUEST_CODE);
                }
                // 绑定服务
                serviceConnection = new ServiceConnection() {
                    @Override
                    public void onServiceConnected(ComponentName name, IBinder service) {
                        if (service instanceof ScreenService.ScreenBinder) {
                            screenService = ((ScreenService.ScreenBinder) service).builder();
                            screenService.setNotification(notification);
                        }
                    }

                    @Override
                    public void onServiceDisconnected(ComponentName name) {
                        screenService = null;
                    }
                };
                screenService.bindService(aThis, serviceConnection);
            } else {
                Toast.makeText(aThis, "请赋予权限，避免程序异常", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e("绑定服务异常", String.valueOf(e.getMessage()));
        }
    }

    /**
     * 初始化系统通知
     *
     * @param notification 通知
     */
    public void setNotification(NotificationListener notification) {
        this.notification = notification;
    }

    /**
     * 停止媒体投影服务
     *
     * @param context context
     */
    public void stopService(Context context) {
        try {
            if (serviceConnection != null) {
                screenService.unbindService(context, serviceConnection);
                serviceConnection = null;
                screenService = null;
            }
            if (displayMetrics != null) {
                displayMetrics = null;
            }
            if (mediaProjectionManager != null) {
                mediaProjectionManager = null;
            }
        } catch (Exception e) {
            Log.e("停止媒体服务异常", String.valueOf(e.getMessage()));
        }
    }

    /**
     * 创建VirtualDisplay(onActivityResult中调用)
     *
     * @param requestCode requestCode
     * @param resultCode  resultCode
     * @param data        data
     */
    public void createVirtualDisplay(int requestCode, int resultCode, Intent data) {
        try {
            if (screenService == null || requestCode != REQUEST_CODE || resultCode != Activity.RESULT_OK) {
                return;
            }
            screenService.createVirtualDisplay(resultCode, data, displayMetrics);
        } catch (Exception e) {
            Log.e("创建视图异常", String.valueOf(e.getMessage()));
        }
    }

    /**
     * 开始 屏幕录制
     *
     * @param callback callback
     */
    public void startMediaRecorder(ScreenCallbackListener callback) {
        try {
            if (screenService == null) {
                callback.onFailing("服务未初始化录制失败");
                return;
            }
            screenService.startRecording(callback);
        } catch (Exception e) {
            Log.e("开始屏幕录制异常", String.valueOf(e.getMessage()));
        }
    }

    /**
     * 停止 屏幕录制
     */
    public void stopMediaRecorder() {
        try {
            if (screenService == null) {
                return;
            }
            screenService.stopRecording();
        } catch (Exception e) {
            Log.e("停止屏幕录制异常", String.valueOf(e.getMessage()));
        }
    }

    /**
     * 显示系统通知）
     */
    public Notification notification(Context context, String title, boolean ongoing, int smallIcon, int largeIcon) {
        return NotificationBar.getInstance(context).createSystem(title, smallIcon, largeIcon)
                .setAutoCancel(true)
                .setOngoing(ongoing)// 常驻通知栏
                .setTicker(title)
                .setContentText(title)
                .setDefaults(Notification.DEFAULT_ALL)
                .build();
    }

    /**
     * 权限判断
     */
    public boolean isPermission(AppCompatActivity aThis) {
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

    private ScreenRecording() {
    }

    private static final class Singleton {
        private static final ScreenRecording INSTANCE = new ScreenRecording();
    }


}
