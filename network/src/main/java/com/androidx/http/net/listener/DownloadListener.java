package com.androidx.http.net.listener;

import android.icu.math.BigDecimal;
import android.util.Log;

import java.io.File;

public interface DownloadListener {
    /**
     * 下载开始
     */
    default void start() {
    }

    /**
     * 下载中
     */
    default void running(BigDecimal process) {
    }

    /**
     * 下载完成
     */
    void finish(File file, double duration);

    /**
     * 下载错误
     */
    default void error(String error) {
        Log.e(getClass().getName(), error);
    }

    /**
     * 下载失败
     */
    default void fail(String fail) {
        Log.e(getClass().getName(), fail);
    }

}
