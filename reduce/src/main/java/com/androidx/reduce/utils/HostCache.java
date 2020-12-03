package com.androidx.reduce.utils;

import android.content.Context;

public final class HostCache {

    private static volatile HostCache instance = Singleton.INSTANCE;

    private static final class Singleton {
        private static final HostCache INSTANCE = new HostCache();
    }

    private HostCache() {
    }

    public static HostCache build() {
        return instance;
    }

    /**
     * 数据写入到本地
     *
     * @param context  上下文
     * @param fileName 文件名
     * @param data     数据
     */
    public <T> void writeFile(Context context, String fileName, T data) {
        try {
            DataCacheUtil dcu = DataCacheUtil.obj(context);
            dcu.writeFile(data, fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取本地数据
     *
     * @param context  上下文
     * @param fileName 文件名
     */
    public String readFile(Context context, String fileName) {
        try {
            DataCacheUtil dcu = DataCacheUtil.obj(context);
            return dcu.readFile(fileName);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * 删除本地数据
     *
     * @param context  上下文
     * @param fileName 文件名
     */
    public boolean deleteFile(Context context, String fileName) {
        try {
            DataCacheUtil dcu = DataCacheUtil.obj(context);
            return dcu.deleteFile(fileName);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 文件是否存在
     *
     * @param fileName 文件名
     * @return 状态
     */
    public boolean isDirectory(String fileName) {
        try {
            return !fileName.contains("No such file or directory");
        } catch (Exception e) {
            return false;
        }
    }
}
