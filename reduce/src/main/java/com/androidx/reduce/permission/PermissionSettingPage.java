package com.androidx.reduce.permission;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;

/**
 * @className: PermissionSettingPage
 * @classDescription:权限设置页面的跳转工具类
 * @author: Pan_
 * @createTime: 2018/10/26
 */
public class PermissionSettingPage {

    private static final String MARK = Build.MANUFACTURER.toLowerCase();

    /**
     * 跳转到应用权限设置页面
     *
     * @param context 上下文对象
     * @param newTask 是否使用新的任务栈启动
     */
    public static void start(Context context, boolean newTask) {

        Intent intent = null;
        if (MARK.contains("huawei")) {
            intent = huawei(context);
        } else if (MARK.contains("xiaomi")) {
            intent = xiaomi(context);
        } else if (MARK.contains("oppo")) {
            intent = oppo(context);
        } else if (MARK.contains("vivo")) {
            intent = vivo(context);
        } else if (MARK.contains("meizu")) {
            intent = meizu(context);
        }

        if (intent == null || !hasIntent(context, intent)) {
            intent = google(context);
        }

        if (newTask) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        try {
            context.startActivity(intent);
        } catch (Exception ignored) {
            intent = google(context);
            context.startActivity(intent);
        }
    }

    @SuppressLint("BatteryLife")
    public static void start(Context context) {
        if (!isIgnoringBatteryOptimizations(context)) {
            Intent intent1 = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
            intent1.setData(Uri.parse("package:" + context.getPackageName()));
        }
    }

    /**
     * 判断我们的应用是否在白名单中
     */
    private static boolean isIgnoringBatteryOptimizations(Context context) {
        boolean isIgnoring = false;
        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        if (powerManager != null) {
            isIgnoring = powerManager.isIgnoringBatteryOptimizations(context.getPackageName());
        }
        return isIgnoring;
    }

    private static Intent google(Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        return intent;
    }

    private static Intent huawei(Context context) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity"));
        if (hasIntent(context, intent)) return intent;
        intent.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.addviewmonitor.AddViewMonitorActivity"));
        if (hasIntent(context, intent)) return intent;
        intent.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.notificationmanager.ui.NotificationManagmentActivity"));
        if (hasIntent(context, intent)) return intent;
        intent.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.appcontrol.activity.StartupAppControlActivity"));
        return intent;
    }

    private static Intent xiaomi(Context context) {
        Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
        intent.putExtra("extra_pkgname", context.getPackageName());
        if (hasIntent(context, intent)) return intent;
        intent.setPackage("com.miui.securitycenter");
        if (hasIntent(context, intent)) return intent;
        intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
        if (hasIntent(context, intent)) return intent;
        intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
        if (hasIntent(context, intent)) return intent;
        intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity");
        return intent;
    }

    private static Intent oppo(Context context) {
        Intent intent = new Intent();
        intent.putExtra("packageName", context.getPackageName());
        intent.setClassName("com.color.safecenter", "com.color.safecenter.permission.floatwindow.FloatWindowListActivity");
        if (hasIntent(context, intent)) return intent;
        intent.setClassName("com.coloros.safecenter", "com.coloros.safecenter.sysfloatwindow.FloatWindowListActivity");
        if (hasIntent(context, intent)) return intent;
        intent.setClassName("com.oppo.safe", "com.oppo.safe.permission.PermissionAppListActivity");
        if (hasIntent(context, intent)) return intent;
        intent.setClassName("com.oppo.safe", "com.oppo.safe.permission.startup.StartupAppListActivity");
        return intent;
    }

    private static Intent vivo(Context context) {
        Intent intent = new Intent();
        intent.setClassName("com.iqoo.secure", "com.iqoo.secure.ui.phoneoptimize.FloatWindowManager");
        intent.putExtra("packagename", context.getPackageName());
        if (hasIntent(context, intent)) return intent;
        intent.setComponent(new ComponentName("com.iqoo.secure", "com.iqoo.secure.safeguard.SoftPermissionDetailActivity"));
        if (hasIntent(context, intent)) return intent;
        intent.setComponent(new ComponentName("com.iqoo.secure", "com.iqoo.secure.safaguard.PurviewTabActivity"));
        return intent;
    }

    private static Intent meizu(Context context) {
        Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
        intent.putExtra("packageName", context.getPackageName());
        intent.setComponent(new ComponentName("com.meizu.safe", "com.meizu.safe.security.AppSecActivity"));
        if (hasIntent(context, intent)) return intent;
        intent.setComponent(new ComponentName("com.meizu.safe", "com.meizu.safe.permission.SmartBGActivity"));
        return intent;
    }

    @SuppressLint("QueryPermissionsNeeded")
    private static boolean hasIntent(Context context, Intent intent) {
        return context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY).size() > 0;
    }
}
