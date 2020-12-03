package com.androidx.http.net;

import android.text.TextUtils;

import org.jetbrains.annotations.NotNull;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import okhttp3.Dns;

public final class HttpDns implements Dns {

    @NotNull
    @Override
    public List<InetAddress> lookup(@NotNull String hostname) throws UnknownHostException {
        if (TextUtils.isEmpty(hostname)) {
            //返回自己解析的地址列表
            return Arrays.asList(InetAddress.getAllByName(hostname));
        } else {
            // 解析失败，使用系统解析
            return Dns.SYSTEM.lookup(hostname);
        }
    }

}
