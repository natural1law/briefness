package com.androidx.http.net;

import android.annotation.SuppressLint;
import android.util.Log;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

@SuppressLint({"TrustAllX509TrustManager", "CustomX509TrustManager"})
@SuppressWarnings("WeakerAccess")
public final class TrustManagers {

    private TrustManagers() {
    }

    //只信任指定证书（传入字符串）
    public static void createSSL(OkHttpClient.Builder okHttpClient, InputStream cer) {
        try {
            CertificateFactory factory = CertificateFactory.getInstance("X.509");//设置证书类型，X.509是一种格式标准
            //证书类型
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());//KeyStore 是一个存储了证书的文件。文件包含证书的私钥，公钥和对应的数字证书的信息。
            keyStore.load(null, null);

            Certificate certificate = factory.generateCertificate(cer);//Certificate是证书信息封装的一个bean类
            keyStore.setCertificateEntry("ca", certificate);

            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);//通过keyStore得到信任管理器

            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keyStore, "password".toCharArray());//通过keyStore得到密匙管理器

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), new SecureRandom());
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();//拿到SSLSocketFactory

            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                return;
            }
            X509TrustManager trustManager = (X509TrustManager) trustManagers[0];
            okHttpClient.sslSocketFactory(sslSocketFactory, trustManager);//设置ssl证书
            okHttpClient.build();

        } catch (Exception e) {
            Log.e("SSLException", Log.getStackTraceString(e));
        }
    }

}
