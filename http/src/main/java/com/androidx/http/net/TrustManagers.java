package com.androidx.http.net;

import android.annotation.SuppressLint;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

@SuppressLint("TrustAllX509TrustManager")
@SuppressWarnings("WeakerAccess")
public final class TrustManagers implements X509TrustManager {

    private static volatile TrustManagers trustManagers;

    private TrustManagers() {
    }

    private static final class SingletonHolder {
        private SingletonHolder() {
        }

        private static final TrustManagers INSTANCE = new TrustManagers();
    }

    protected static TrustManagers newInstance() {
        if (trustManagers == null) {
            synchronized (TrustManagers.class) {
                if (trustManagers == null) {
                    trustManagers = SingletonHolder.INSTANCE;
                }
            }
        }
        return trustManagers;
    }

    /**
     * 对外提供的获取支持自签名的okhttp客户端
     * <p>
     * 自签名证书的输入流
     *
     * @return 支持自签名的客户端
     */
    protected SSLSocketFactory createSSLSocketFactory() {
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{TrustManagers.newInstance()}, new SecureRandom());
            return sc.getSocketFactory();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) {

    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) {

    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }

    //只信任指定证书（传入字符串）
    public static void setCertificate(OkHttpClient.Builder okHttpClientBuilder, String cerStr) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(cerStr.getBytes());
            Certificate ca = certificateFactory.generateCertificate(byteArrayInputStream);

            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);

            byteArrayInputStream.close();

            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(keyStore);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), new SecureRandom());
            okHttpClientBuilder.sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) tmf.getTrustManagers()[0]);
        } catch (Exception e) {
            Log.e("SSLException", String.valueOf(e.getMessage()));
        }
    }

}
