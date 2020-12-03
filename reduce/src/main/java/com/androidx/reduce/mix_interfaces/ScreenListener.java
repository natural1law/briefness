package com.androidx.reduce.mix_interfaces;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.util.DisplayMetrics;

import com.androidx.reduce.intertaces.NotificationListener;
import com.androidx.reduce.intertaces.ScreenCallbackListener;

/**
 * @date 2020/06/15
 */
public interface ScreenListener {

    /* 绑定Service */
    void bindService(Context context, ServiceConnection serviceConnection);

    /* 解绑Service */
    void unbindService(Context context, ServiceConnection serviceConnection);

    /* 开始 媒体录制 */
    void startRecording(ScreenCallbackListener callback);

    /* 停止 媒体录制 */
    void stopRecording();

    /* 创建显示比例 */
    void createVirtualDisplay(int resultCode, Intent data, DisplayMetrics displayMetrics);

    /* 设置通知引擎 */
    void setNotification(NotificationListener NotificationListener);
}
