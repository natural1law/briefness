package com.androidx.view.scan;

import static com.androidx.view.scan.ScanActivity.RESULT_KEY;

import android.content.Context;
import android.content.Intent;

import androidx.activity.result.ActivityResultLauncher;

import com.androidx.reduce.tools.This;
import com.androidx.reduce.tools.Toasts;

/**
 * 调用扫描二维码或条形码
 *
 * @date 2021/02/21
 */
public final class ScanTools {
    private static ActivityResultLauncher<Intent> launcher;

    private ScanTools() {
    }

    public static void callback(Context context, LauncherCallbackListener resultListener) {
        launcher = This.initLauncher(context, (resultCode, intent) -> {
            try {
                resultListener.callback(resultCode, intent.getStringExtra(RESULT_KEY));
            } catch (Exception e) {
                Toasts.e(ScanTools.class.getName(), e);
            }
        });
    }

    public static void start() {
        if (launcher != null) This.build().startLauncher(ScanActivity.class, launcher).execute();
    }

    public interface LauncherCallbackListener {
        void callback(int resultCode, String data);
    }
}
