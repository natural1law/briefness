package com.androidx.reduce.config;

public final class Mode {

    private Mode() {
    }

    public static final char[] MD5_HEX = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    public static final String MD5 = "MD5";

    public static final String AES = "AES";//AES 加密
    public static final String AES_HEX = "0123456789ABCDEF";
    public static final String AES_CBC = "AES/CBC/PKCS7PADDING";//AES是加密方式 CBC是工作模式 PKCS5Padding是填充模式
    public static final String AES_SHA1PRNG = "SHA1PRNG";//// SHA1PRNG 强随机种子算法, 要区别4.2以上版本的调用方法

    public static final String RSA = "RSA";//加密类型
    public static final String RSA_ECB = "RSA/ECB/PKCS1PADDING";//加密算法
    public static final String RSA_SING = "MD5withRSA";//签名算法
    public static final int RSA_ENCRYPT = 501;//RSA最大加密明文大小
    public static final int RSA_DECRYPT = 512;//RSA最大解密密文大小
    public static final int RSA_SIZE = 4096;//密钥长度/范围：512~2048（值越大执行越慢）

    public static String SHA1 = "SHA1";
}
