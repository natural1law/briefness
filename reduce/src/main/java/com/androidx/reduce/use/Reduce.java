package com.androidx.reduce.use;

import android.content.Context;

import com.androidx.reduce.tools.Astrict;
import com.androidx.reduce.tools.Captcha;
import com.androidx.reduce.tools.CountDown;
import com.androidx.reduce.tools.Idle;
import com.androidx.reduce.tools.MicroCache;
import com.androidx.reduce.tools.NetworkConfiguration;
import com.androidx.reduce.tools.Proxys;
import com.androidx.reduce.tools.Secure;
import com.androidx.reduce.tools.Toasts;

import java.security.KeyPair;

public final class Reduce {

    private Reduce() {
    }

    /**
     * 输入和屏幕适配限制
     */
    public static Astrict astrict() {
        return Astrict.get();
    }

    /**
     * 随机生成验证码
     */
    public static Captcha captcha() {
        return Captcha.build();
    }

    /**
     * 倒计时工具类
     */
    public static CountDown cd() {
        return CountDown.builder().build();
    }

    /**
     * 点击延时事件
     */
    public static boolean isClick(long... time) {
        if (time == null || time.length == 0) return Idle.isClick();
        else return Idle.isClick(time[0]);
    }

    /**
     * 微程序缓冲储存器
     */
    public static MicroCache mc(Context c) {
        return MicroCache.builder(c);
    }

    /**
     * 网络配置（支持android7.0以上）
     */
    public static NetworkConfiguration nc(Context c) {
        return NetworkConfiguration.build(c);
    }

    /**
     * 动态代理
     */
    public static <T> T proxys(T cls) {
        return Proxys.build(cls).getProxy();
    }

    /**
     * md5加密
     *
     * @param cleartext 明文
     */
    public static String md5(String cleartext) {
        return Secure.MD5.encrypt(cleartext);
    }

    /**
     * md5数据验证
     *
     * @param cleartext  明文
     * @param ciphertext 密文
     */
    public static boolean md5verify(String cleartext, String ciphertext) {
        return Secure.MD5.verify(cleartext, ciphertext);
    }

    /**
     * AES钥匙
     */
    public static String aesKey() {
        return Secure.AES.key();
    }

    /**
     * AES加密
     *
     * @param key       AES钥匙
     * @param cleartext 明文
     */
    public static String aesEncrypt(String key, String cleartext) {
        return Secure.AES.encrypt(key, cleartext);
    }

    /**
     * AES加密
     *
     * @param key        AES钥匙
     * @param ciphertext 密文
     */
    public static String aesDecrypt(String key, String ciphertext) {
        return Secure.AES.decrypt(key, ciphertext);
    }

    /**
     * 密钥对
     */
    public static KeyPair rsaKey() {
        return Secure.RSA.initKeyPair();
    }

    /**
     * 获取私钥
     *
     * @param keyPair 密钥对
     */
    public static String rsaPrivateKey(KeyPair keyPair) {
        return Secure.RSA.privateKey(keyPair);
    }

    /**
     * 获取公钥
     *
     * @param keyPair 密钥对
     */
    public static String rsaPublicKey(KeyPair keyPair) {
        return Secure.RSA.publicKey(keyPair);
    }

    /**
     * RSA公钥加密
     *
     * @param publicKey 公钥
     * @param cleartext 明文文
     */
    public static String rsaPublicEncrypt(String publicKey, String cleartext) {
        return Secure.RSA.encryptByPublicKey(cleartext, publicKey);
    }

    /**
     * RSA公钥解密
     *
     * @param publicKey  公钥
     * @param ciphertext 密文
     */
    public static String rsaPublicDecrypt(String publicKey, String ciphertext) {
        return Secure.RSA.decryptByPublicKey(ciphertext, publicKey);
    }

    /**
     * RSA私钥加密
     *
     * @param privateKey 私钥
     * @param cleartext  明文
     */
    public static String rsaPrivateEncrypt(String privateKey, String cleartext) {
        return Secure.RSA.encryptByPrivateKey(cleartext, privateKey);
    }

    /**
     * RSA私钥解密
     *
     * @param privateKey 私钥
     * @param ciphertext 密文
     */
    public static String rsaPrivateDecrypt(String privateKey, String ciphertext) {
        return Secure.RSA.decryptByPrivateKey(ciphertext, privateKey);
    }

    /**
     * RSA私钥签名
     *
     * @param privateKey 私钥
     * @param ciphertext 密文
     */
    public static String rsaSign(String privateKey, String ciphertext) {
        return Secure.RSA.sign(ciphertext, privateKey);
    }

    /**
     * RSA公钥验证
     *
     * @param publicKey  公钥
     * @param ciphertext 密文
     * @param sign       签名
     */
    public static boolean rsaVerify(String publicKey, String ciphertext, String sign) {
        return Secure.RSA.verify(ciphertext, publicKey, sign);
    }

    /**
     * SHA1加密
     */
    public static String sha1app(Context context) {
        return Secure.SHA1.app(context);
    }

    /**
     * SHA1加密
     *
     * @param cleartext 明文
     */
    public static String sha1encrypt(String cleartext) {
        return Secure.SHA1.encrypt(cleartext);
    }

    /**
     * Base64加密
     *
     * @param cleartext 明文
     */
    public static String base64Encrypt(String cleartext) {
        return Secure.Base64.encode(cleartext);
    }

    /**
     * Base64解密
     *
     * @param ciphertext 密文
     */
    public static byte[] base64Decode(String ciphertext) {
        return Secure.Base64.decode(ciphertext);
    }

    /**
     * 提示框
     */
    public static Toasts toasts(Context c) {
        return Toasts.builder(c);
    }
}
