package com.androidx.view.screen.intertaces;

import android.app.Notification;
import android.content.Intent;

import com.androidx.view.screen.config.ScreenConfig;

public interface ScreenServiceListener {

    void startRecording(ScreenCallbackListener callback, ScreenConfig config);

    void startCapture(ScreenCallbackListener callback, ScreenConfig config);

    void stopRecording();

    void destroy();

    void createVirtualDisplay(int resultCode, Intent data, Notification notification);
}
