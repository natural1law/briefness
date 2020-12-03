package com.androidx.reduce.utils;

public final class Idle {

    private static long lastClickTime;

    /**
     * 默认延时加载
     */
    public static boolean isClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= 800) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }

    /**
     * 设置延时加载时间
     */
    public static boolean isClick(long time) {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= time) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }

}
