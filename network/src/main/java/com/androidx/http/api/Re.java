package com.androidx.http.api;

import android.util.Log;

import com.androidx.http.use.Rn;
import com.androidx.reduce.tools.Regular;

import java.util.List;

/**
 * 网络请求框架异常处理
 *
 * @date 2022/02/23
 */
public final class Re extends Exception {

    private Re(String message) {
        Log.e(getClass().getName(), message);
    }

    public static boolean isUrl(String url) {
        try {
            if (url == null || url.equals("")) {
                throw new Re("请输入url");
            } else if (!Regular.isHttp(url)) {
                throw new Re("请输入有效url");
            } else return false;
        } catch (Re e) {
            Log.e(Rn.class.getName(), Log.getStackTraceString(e));
            return true;
        }
    }

    public static boolean isPath(String path) {
        try {
            if (path == null || path.equals("")) {
                throw new Re("请输入path");
            } else return false;
        } catch (Re e) {
            Log.e(Rn.class.getName(), Log.getStackTraceString(e));
            return true;
        }
    }

    public static boolean isPaths(List<String> path) {
        try {
            if (path == null || path.isEmpty()) {
                throw new Re("请输入path");
            } else return false;
        } catch (Re e) {
            Log.e(Rn.class.getName(), Log.getStackTraceString(e));
            return true;
        }
    }

}
