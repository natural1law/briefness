package com.androidx.reduce.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;

import com.androidx.reduce.intertaces.NotificationListener;
import com.androidx.reduce.intertaces.ScreenCallbackListener;
import com.androidx.reduce.mix_interfaces.ScreenListener;
import com.androidx.reduce.utils.Storage;

import org.jetbrains.annotations.NotNull;

import static com.androidx.reduce.views.ScreenRecording.REQUEST_CODE;

/**
 * 录屏服务
 *
 * @date 2020/06/15
 */
public final class ScreenService extends Service implements ScreenListener {

    private DisplayMetrics displayMetrics;//屏幕尺寸
    private MediaRecorder mediaRecorder;//音频
    private MediaProjection mediaProjection;//视频
    private VirtualDisplay virtualDisplayMediaRecorder;//虚拟图形
    private MediaProjectionManager mediaProjectionManager;//媒体管理
    private ScreenCallbackListener callback;//失败回调
    private String fileUrl;//视频地址
    private NotificationListener notification;//通知栏
    private static final int TIMES = 2;

    @NotNull
    @Override
    public IBinder onBind(Intent intent) {
        return new ScreenBinder();
    }

    @Override
    public void onDestroy() {
        destroy();
        super.onDestroy();
    }

    /**
     * 绑定Service
     *
     * @param context           context
     * @param serviceConnection serviceConnection
     */
    @Override
    public void bindService(Context context, ServiceConnection serviceConnection) {
        Intent intent = new Intent(context, ScreenService.class);
        context.bindService(intent, serviceConnection, Service.BIND_AUTO_CREATE);
    }

    /**
     * 解绑Service
     *
     * @param context           context
     * @param serviceConnection serviceConnection
     */
    @Override
    public void unbindService(Context context, ServiceConnection serviceConnection) {
        context.unbindService(serviceConnection);
    }

    /**
     * 开始 媒体录制
     *
     * @param callback callback
     */
    @Override
    public void startRecording(ScreenCallbackListener callback) {
        try {
            this.callback = callback;
            if (displayMetrics != null) {
                createMediaRecorder();
                mediaRecorder.start();
            }
        } catch (Exception e) {
            Log.e("开始媒体录制异常", String.valueOf(e.getMessage()));
        }
    }

    /**
     * 停止 媒体录制
     */
    @Override
    public void stopRecording() {
        try {
            if (mediaRecorder != null) {
                mediaRecorder.stop();
                mediaRecorder.reset();
                mediaRecorder.release();
                mediaRecorder = null;
            }
            if (callback != null) {
                callback.onSuccess(fileUrl);
                fileUrl = null;
                callback = null;
            }
        } catch (Exception e) {
            Log.e("停止媒体录制异常", String.valueOf(e.getMessage()));
        }
    }

    /**
     * 销毁
     */
    private void destroy() {
        stopMediaRecorder();
        if (mediaProjection != null) {
            mediaProjection.stop();
            mediaProjection = null;
        }
        if (mediaProjectionManager != null) {
            mediaProjectionManager = null;
        }
        stopForeground(true);
    }

    /**
     * 结束 媒体录制
     */
    private void stopMediaRecorder() {
        this.stopRecording();
        if (virtualDisplayMediaRecorder != null) {
            virtualDisplayMediaRecorder.release();
            virtualDisplayMediaRecorder = null;
        }
    }


    /**
     * 创建 媒体录制
     */
    private void createMediaRecorder() {
        //获取屏幕宽度
        int width = displayMetrics.widthPixels;
        //获取屏幕高度
        int height = displayMetrics.heightPixels;
        //获取屏幕密度
        int densityDpi = displayMetrics.densityDpi;
        // 调用顺序不能乱
        mediaRecorder = new MediaRecorder();
        //设置声音来源
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        //设置视频来源
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
        //设置视频格式
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        //设置声音编码
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        //设置视频储存地址
        fileUrl = Storage.getSaveDirectory("cmfVideo", ".mp4");
        mediaRecorder.setOutputFile(fileUrl);
        //设置视频编码
        mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
        //设置视频大小
        mediaRecorder.setVideoSize(480, 800);
        //设置录制视频的捕获帧速率
        mediaRecorder.setVideoFrameRate(10);
        //设置码率
        mediaRecorder.setVideoEncodingBitRate((width * height) / TIMES);
        //视频错误回调
        mediaRecorder.setOnErrorListener((mr, what, extra) -> {
            if (callback != null) {
                callback.onFailing(mr.toString());
            }
        });

        try {
            mediaRecorder.prepare();
            if (virtualDisplayMediaRecorder == null) {
                virtualDisplayMediaRecorder = mediaProjection.createVirtualDisplay("MediaRecorder", 480, 800, densityDpi / 2,
                        DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR, mediaRecorder.getSurface(), new VirtualDisplay.Callback() {
                        }, new Handler(Looper.getMainLooper()));
            } else {
                virtualDisplayMediaRecorder.setSurface(mediaRecorder.getSurface());
            }
        } catch (Exception e) {
            Log.e("准备异常", String.valueOf(e.getMessage()));
        }
    }

    /**
     * 创建VirtualDisplay
     *
     * @param resultCode     resultCode
     * @param data           data
     * @param displayMetrics displayMetrics
     */
    @Override
    public void createVirtualDisplay(int resultCode, Intent data, DisplayMetrics displayMetrics) {
        this.displayMetrics = displayMetrics;
        if (data == null) {
            stopSelf();
            return;
        }

        showNotification();

        mediaProjectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        if (mediaProjectionManager == null) {
            stopSelf();
            return;
        }

        mediaProjection = mediaProjectionManager.getMediaProjection(resultCode, data);
        if (mediaProjection == null) {
            stopSelf();
        }
    }

    /**
     * 设置通知引擎
     */
    @Override
    public void setNotification(NotificationListener notification) {
        this.notification = notification;
    }

    /**
     * 显示通知栏
     */
    private void showNotification() {
        if (notification == null) {
            return;
        }
        startForeground(REQUEST_CODE, notification.getNotification());
    }

    public final class ScreenBinder extends Binder {
        private ScreenBinder() {
        }

        public ScreenService builder() {
            return ScreenService.this;
        }
    }

}
