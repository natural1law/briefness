package com.androidx.view.screen;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import java.lang.ref.WeakReference;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * 通知栏
 *
 * @date 2020/06/17
 */
public final class NotificationBar {

    private static final String CHANNEL_ID_OTHER = "other";
    private static final String CHANNEL_NAME_OTHER = "其他消息";
    @TargetApi(Build.VERSION_CODES.O)
    private static final int CHANNEL_IMPORTANCE_OTHER = NotificationManager.IMPORTANCE_MIN;

    private static final String CHANNEL_ID_SYSTEM = "system";
    private static final String CHANNEL_NAME_SYSTEM = "系统通知";
    @TargetApi(Build.VERSION_CODES.O)
    private static final int CHANNEL_IMPORTANCE_SYSTEM = NotificationManager.IMPORTANCE_HIGH;

    private static volatile NotificationBar instance;
    private static volatile WeakReference<Context> wrContext;

    public static NotificationBar getInstance(Context context) {
        try {
            if (instance == null) {
                synchronized (NotificationBar.class) {
                    if (instance == null) {
                        instance = new NotificationBar(context);
                    }
                }
            }
            return instance;
        } catch (Exception e) {
            return new NotificationBar(context);
        }
    }

    private NotificationBar(Context context) {
        wrContext = new WeakReference<>(context);
        createChannel();
    }

    /**
     * 创建通知渠道
     */
    private void createChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return;
        }
        createChannel(CHANNEL_ID_OTHER, CHANNEL_NAME_OTHER, CHANNEL_IMPORTANCE_OTHER, false);
        createChannel(CHANNEL_ID_SYSTEM, CHANNEL_NAME_SYSTEM, CHANNEL_IMPORTANCE_SYSTEM, true);
    }

    /**
     * 创建通知渠道
     *
     * @param channelId   channelId
     * @param channelName channelName
     * @param importance  importance
     * @param isShowBadge 是否显示角标
     */
    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel(String channelId, String channelName, int importance, boolean isShowBadge) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        channel.setShowBadge(isShowBadge);
        NotificationManager notificationManager = (NotificationManager) wrContext.get().getSystemService(NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(channel);
        }
    }

    /**
     * 创建通知栏 Builder
     *
     * @return NotificationCompat.Builder
     */
    public NotificationCompat.Builder create(String channelId, String title, int smallIcon, int largeIcon) {
        return new NotificationCompat.Builder(wrContext.get(), channelId)
                .setContentTitle(title)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(smallIcon)
                .setLargeIcon(BitmapFactory.decodeResource(wrContext.get().getResources(), largeIcon));
    }

    /**
     * 创建通知栏 Builder
     *
     * @return NotificationCompat.Builder
     */
    public NotificationCompat.Builder createOther(String title, int smallIcon, int largeIcon) {
        return create(CHANNEL_ID_OTHER, title, smallIcon, largeIcon);
    }

    /**
     * 创建通知栏 Builder
     *
     * @return NotificationCompat.Builder
     */
    public NotificationCompat.Builder createSystem(String title, int smallIcon, int largeIcon) {
        return create(CHANNEL_ID_SYSTEM, title, smallIcon, largeIcon);
    }

    /**
     * 创建通知栏 Builder
     *
     * @return NotificationCompat.Builder
     */
    public NotificationCompat.Builder createSystem(String title, int smallIcon) {
        return create(CHANNEL_ID_SYSTEM, title, smallIcon, 0);
    }

    /**
     * 创建通知栏 Builder
     *
     * @return NotificationCompat.Builder
     */
    public NotificationCompat.Builder createSystem(String title) {
        return create(CHANNEL_ID_SYSTEM, title, 0, 0);
    }
}
