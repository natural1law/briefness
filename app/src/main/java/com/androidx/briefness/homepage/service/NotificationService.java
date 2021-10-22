package com.androidx.briefness.homepage.service;

import static android.provider.Telephony.Carriers.USER;
import static com.androidx.briefness.base.App.mc;
import static com.androidx.briefness.base.App.toasts;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.androidx.briefness.R;
import com.androidx.http.net.listener.Enqueue;
import com.androidx.http.use.Rn;
import com.androidx.view.screen.NotificationBar;

import java.util.Map;
import java.util.WeakHashMap;

public class NotificationService extends Service {

    public static Enqueue enqueue;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    @SuppressLint("WrongConstant")
    public int onStartCommand(Intent intent, int flags, int startId) {
        flags = START_STICKY;
        Map<String, Object> map = new WeakHashMap<>();
        map.put(USER, mc.getValue(USER).toString());
        startForeground(1, NotificationBar.setOther(getApplicationContext(), "通知", "消息推送服务已开启", R.mipmap.radio_on));
        enqueue = Rn.initWebSocket("http://192.168.1.133:8082/cmp/push/notification", map);
        enqueue.setMsgCallback((code, msg, data) -> {
            switch (code) {
                case 999:
                    startForeground(code, NotificationBar.setOther(getApplicationContext(), "CMP通知", msg, R.mipmap.radio_on));
                    break;
                case 0:
                default:
            }
            toasts.i("收到数据" + code, msg);
        });
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        enqueue.close();
        Intent intent = new Intent(this, NotificationService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.startForegroundService(intent);
        } else {
            this.startService(intent);
        }
        super.onDestroy();
    }
}
