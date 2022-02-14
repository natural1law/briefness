package com.androidx.reduce.listener;

public interface ThisListener {

    void start();

    void reset();

    void stop();

    /**
     * @param delayTime 毫秒
     */
    void delayStart(long delayTime);
}
