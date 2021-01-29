package com.androidx.reduce.tools;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Process;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.androidx.reduce.intertaces.PermissionRequestListener;
import com.androidx.reduce.permission.GrantResult;
import com.androidx.reduce.permission.NextAction;
import com.androidx.reduce.permission.NextActionType;
import com.androidx.reduce.permission.PermissionRequestFragment;
import com.androidx.reduce.permission.PermissionSettingPage;
import com.androidx.reduce.permission.PermissionUtils;
import com.androidx.reduce.permission.RequestPermissionRationalListener;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static android.Manifest.permission.REQUEST_INSTALL_PACKAGES;
import static android.Manifest.permission.SYSTEM_ALERT_WINDOW;
import static android.os.Build.VERSION_CODES.M;

/**
 * 授权权限
 */
public class Permission implements NextAction {
    private final AppCompatActivity mActivity;
    private final LinkedList<String> mPermissionList = new LinkedList<>();
    private PermissionRequestListener mPermissionRequestListener;
    private String mCurPermission;

    private final HashMap<String, RequestPermissionRationalListener> mRequestPermissionRationalListenerMap = new HashMap<>();
    private final HashMap<String, GrantResult> mPermissionGrantMap = new HashMap<>();

    public Permission(AppCompatActivity activity) {
        mActivity = activity;
    }

    /**
     * 创建一个EasyPermission实例，一切从这里开始
     */
    public static Permission with(AppCompatActivity activity) {
        return new Permission(activity);
    }

    /**
     * 添加一个需要获取的权限
     */
    public Permission addPermission(String permission) {
        if (TextUtils.isEmpty(permission)) {
            return this;
        }
        mPermissionList.add(permission);
        return this;
    }


    /**
     * 添加一组需要获取的权限
     */
    public Permission addPermissions(String... permission) {
        if (permission == null || permission.length <= 0) {
            return this;
        }
        mPermissionList.addAll(Arrays.asList(permission));
        return this;
    }

    /**
     * 添加一组需要获取的权限
     */
    public Permission addPermissions(String[]... permission) {
        if (permission == null || permission.length <= 0) {
            return this;
        }
        for (String[] group : permission) {
            mPermissionList.addAll(Arrays.asList(group));
        }
        return this;
    }


    /**
     * 添加一组需要获取的权限
     */
    public Permission addPermissions(List<String> permission) {
        if (permission == null || permission.isEmpty()) {
            return this;
        }
        mPermissionList.addAll(permission);
        return this;
    }

    /**
     * 添加一个权限的Rational的处理
     */
    public Permission addRequestPermissionRationaleHandler(String permission, RequestPermissionRationalListener listener) {
        if (TextUtils.isEmpty(permission) || listener == null) {
            return this;
        }
        mRequestPermissionRationalListenerMap.put(permission, listener);
        return this;
    }

    /**
     * 判断是否已经该权限
     */
    public static boolean isPermissionGrant(Context context, String... permissions) {
        for (String permission : permissions) {
            if (context.checkPermission(permission, Process.myPid(), Process.myUid()) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    /**
     * 打开权限设置页面
     *
     * @param context 上下文
     */
    public static void openSettingPage(Context context) {
        PermissionSettingPage.start(context, false);
    }

    /**
     * 打开权限设置页面
     *
     * @param context 上下文
     * @param newTask 是否开启新的堆栈打开
     */
    public static void openSettingPage(Context context, boolean newTask) {
        PermissionSettingPage.start(context, newTask);
    }

    /**
     * 开始申请权限
     */
    public void request(PermissionRequestListener listener) {
        if (listener == null) {
            return;
        }
        if (mPermissionList.isEmpty()) {
            throw new RuntimeException("must add some permission to request!!");
        }
        if (Build.VERSION.SDK_INT < M) {
            HashMap<String, GrantResult> grantMap = new HashMap<>();
            for (String permission : mPermissionList) {
                grantMap.put(permission, GrantResult.GRANT);
            }
            listener.onGrant(grantMap);
            return;
        }
        PermissionUtils.checkPermissions(mActivity, mPermissionList);
        mPermissionRequestListener = listener;
        pollPermission();
    }


    @TargetApi(23)
    private void pollPermission() {
        if (mPermissionList.isEmpty()) {
            PermissionRequestFragment.build(mPermissionGrantMap, mPermissionRequestListener).go(mActivity);
            return;
        }
        String permission = mPermissionList.pollFirst();

        if (REQUEST_INSTALL_PACKAGES.equals(permission)) {
            if (PermissionUtils.isHasInstallPermission(mActivity)) {
                mPermissionGrantMap.put(permission, GrantResult.GRANT);
                pollPermission();
            } else {
                mPermissionGrantMap.put(permission, GrantResult.DENIED);
                pollPermission();
            }

        } else if (SYSTEM_ALERT_WINDOW.equals(permission)) {
            if (PermissionUtils.isHasOverlaysPermission(mActivity)) {
                mPermissionGrantMap.put(permission, GrantResult.GRANT);
                pollPermission();
            } else {
                mPermissionGrantMap.put(permission, GrantResult.DENIED);
                pollPermission();
            }
        } else if (mActivity.checkPermission(permission, Process.myPid(), Process.myUid()) == PackageManager.PERMISSION_GRANTED) {
            mPermissionGrantMap.put(permission, GrantResult.GRANT);
            pollPermission();
        } else {
            mPermissionGrantMap.put(permission, GrantResult.DENIED);
            if (mRequestPermissionRationalListenerMap.get(permission) != null) {
                mCurPermission = permission;
                Objects.requireNonNull(mRequestPermissionRationalListenerMap.get(permission)).onRequestPermissionRational(permission, mActivity.shouldShowRequestPermissionRationale(permission), this);
            } else {
                pollPermission();
            }
        }
    }

    @Override
    public void next(NextActionType next) {
        if (next == null) {
            mPermissionGrantMap.put(mCurPermission, GrantResult.IGNORE);
            pollPermission();
            return;
        }
        switch (next) {
            case NEXT:
                pollPermission();
                break;
            case IGNORE:
                mPermissionGrantMap.put(mCurPermission, GrantResult.IGNORE);
                pollPermission();
                break;
            case STOP:
                mPermissionRequestListener.onCancel(mCurPermission);
                break;
        }
    }
}
