package com.androidx.view.screen.service;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.Image;
import android.media.ImageReader;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.androidx.view.screen.config.ScreenConfig;
import com.androidx.view.screen.intertaces.ScreenCallbackListener;
import com.androidx.view.screen.intertaces.ScreenServiceListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Boolean.TRUE;

public class ScreenService extends Service implements ScreenServiceListener {

    private MediaRecorder mr;//音频
    private MediaProjection mp;//视频
    private VirtualDisplay dmr;//视频虚拟图形
    private VirtualDisplay dir;//静态虚拟图形
    private ScreenCallbackListener callback;//状态回调
    private Image image;

    private static final String SC = "ScreenCapture";
    private static final String SR = "MediaRecorder";

    private final ExecutorService executor = Executors.newWorkStealingPool();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new ScreenBinder();
    }

    @Override
    public void onDestroy() {
        destroy();
    }

    @Override
    public void startRecording(ScreenCallbackListener callback, ScreenConfig config) {
        try {
            this.callback = callback;
            mr = createMediaRecorder(config);
            mr.start();
        } catch (Exception e) {
            callback.onFailing(Log.getStackTraceString(e));
        }
    }

    @Override
    public void startCapture(ScreenCallbackListener callback, ScreenConfig config) {
        try {
            this.callback = callback;
            createImageReader(config);
        } catch (Exception e) {
            callback.onFailing(Log.getStackTraceString(e));
        }
    }

    @Override
    public void stopRecording() {
        try {
            if (dmr != null) dmr.release();
            if (dir != null) dir.release();
            if (mr != null) mr.reset();
        } catch (Exception e) {
            Log.e("停止录制异常", Log.getStackTraceString(e));
        }
    }

    /**
     * 销毁
     */
    @Override
    public void destroy() {
        try {
            if (mp != null) mp.stop();
            if (mr != null) mr.release();
            if (executor.isShutdown()) executor.shutdown();
            stopForeground(true);
        } catch (Exception e) {
            Log.e("销毁异常", Log.getStackTraceString(e));
        }
    }

    @Override
    public void createVirtualDisplay(int resultCode, Intent data, Notification notification) {
        try {
            startForeground(1, notification);
            //媒体管理
            MediaProjectionManager mpm = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
            mp = mpm.getMediaProjection(resultCode, data);
        } catch (Exception e) {
            stopSelf();
            Log.e("创建虚拟视图异常", Log.getStackTraceString(e));
        }
    }

    /**
     * 创建 屏幕截图
     */
    private void createImageReader(ScreenConfig config) {
        int w = config.getBasicsWidth();
        int h = config.getBasicsHeight();
        int f = config.getCaptureFormat();
        int i = config.getCaptureMaxImages();
        ImageReader imageReader = ImageReader.newInstance(w, h, f, i);
        dir = mp.createVirtualDisplay(SC, w, h, f, DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                imageReader.getSurface(), new VirtualDisplay.Callback() {
                }, null);
        imageReader.setOnImageAvailableListener(reader -> executor.execute(() -> {
            try {
                String url = config.getCaptureUrl();
                // 获取数据
                image = reader.acquireLatestImage();
                System.out.println(image);
                int width = image.getWidth();
                int height = image.getHeight();
                Image.Plane[] plane = image.getPlanes();
                ByteBuffer buffer = plane[0].getBuffer();
                // 重新计算Bitmap宽度，防止Bitmap显示错位
                int pixelStride = plane[0].getPixelStride();
                int rowStride = plane[0].getRowStride();
                int rowPadding = rowStride - pixelStride * width;
                int bitmapWidth = width + rowPadding / pixelStride;
                // 创建Bitmap
                Bitmap bitmap = Bitmap.createBitmap(bitmapWidth, height, Bitmap.Config.ARGB_8888);
                bitmap.copyPixelsFromBuffer(buffer);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 80, bos);
                bos.flush();
                bos.close();
                buffer.clear();
                bitmap.recycle();
                if (image != null) image.close();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    Path path = Paths.get(url);
                    if (Files.notExists(path)) {
                        File file = Files.write(Files.createFile(path), bos.toByteArray()).toFile();
                        callback.onSuccess(file.getPath(), file.exists());
                    }
                } else {
                    File file = new File(url);
                    if (!file.exists()) if (!file.mkdirs()) if (file.setWritable(TRUE)) {
                        FileOutputStream fos = new FileOutputStream(file, true);
                        fos.write(bos.toByteArray());
                        fos.flush();
                        fos.close();
                        callback.onSuccess(file.getPath(), file.exists());
                    }
                }
                if (dir != null) dir.release();
            } catch (Exception e) {
                Log.e("创建屏幕截图异常", Log.getStackTraceString(e));
            }
        }), null);
    }

    /**
     * 创建 媒体录制
     */
    private MediaRecorder createMediaRecorder(ScreenConfig config) throws IOException {
        // 调用顺序不能乱
        MediaRecorder mediaRecorder = new MediaRecorder();
        //设置声音来源
        mediaRecorder.setAudioSource(config.getVideoAudioSource());
        //设置视频来源
        mediaRecorder.setVideoSource(config.getVideoSource());
        //设置视频格式
        mediaRecorder.setOutputFormat(config.getVideoOutputFormat());
        //设置声音编码
        mediaRecorder.setAudioEncoder(config.getVideoAudioEncoder());
        //设置视频储存地址
        mediaRecorder.setOutputFile(config.getVideoOutputFile());
        //设置视频编码
        mediaRecorder.setVideoEncoder(config.getVideoEncoder());
        //设置视频大小
        mediaRecorder.setVideoSize(config.getBasicsWidth(), config.getBasicsHeight());
        //设置录制视频的捕获帧速率
        mediaRecorder.setVideoFrameRate(config.getVideoFrameRate());
        //设置码率
        int rate = config.getVideoEncodingBitRate() * config.getBasicsWidth() * config.getBasicsHeight();
        mediaRecorder.setVideoEncodingBitRate(rate);
        //视频错误回调
        mediaRecorder.setOnErrorListener((mr, what, extra) -> callback.onFailing(mr.toString()));
        //视频准备
        mediaRecorder.prepare();
        //视频虚拟视图创建
        dmr = mp.createVirtualDisplay(SR, config.getBasicsWidth(), config.getBasicsHeight(), config.getBasicsDpi()
                , DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR, mediaRecorder.getSurface(), new VirtualDisplay.Callback() {
                    @Override
                    public void onStopped() {
                        if (callback != null) {
                            boolean exists;
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                exists = Paths.get(config.getVideoOutputFile()).toFile().exists();
                            } else exists = new File(config.getVideoOutputFile()).exists();
                            callback.onSuccess(config.getVideoOutputFile(), exists);
                        }
                    }
                }, null);
        return mediaRecorder;
    }

    public final class ScreenBinder extends Binder {

        private ScreenBinder() {

        }

        public ScreenService builder() {
            return ScreenService.this;
        }
    }


}
