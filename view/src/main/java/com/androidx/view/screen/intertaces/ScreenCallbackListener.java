package com.androidx.view.screen.intertaces;

import android.util.Log;

public interface ScreenCallbackListener {
    void onSuccess(String fileUrl, boolean exists);

    default void onFailing(String error) {
        Log.e("截屏/录屏异常", error);
    }

}
