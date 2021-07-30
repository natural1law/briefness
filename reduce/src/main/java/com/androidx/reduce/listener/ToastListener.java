package com.androidx.reduce.listener;

public interface ToastListener {

    /**
     * 默认提示
     */
    void showNormal();

    /**
     * 成功提示
     */
    void showSuccess();

    /**
     * 错误提示
     */
    void showError();

    /**
     * 警告提示
     */
    void showWarning();

    /**
     * 信息提示
     */
    void showInfo();

    /**
     * 系统原始提示
     */
    void showOriginal();
}
