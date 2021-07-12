package com.androidx.reduce.tools;

import com.androidx.reduce.config.Control;

import java.security.KeyPair;

public final class Secure {
    private Secure() {
    }

    public static final class MD5 {
        private MD5() {
        }

        /**
         * 使用MD5加密（含随机盐）
         *
         * @param cleartext 明文
         * @return 加密后的字符串
         */
        public static String encrypt(String cleartext) {
            return Control.MD5.encrypt(cleartext);
        }

        /**
         * 获取MD5数据校验
         *
         * @param original   原始数据（未加密）
         * @param ciphertext md5加密后的密文
         * @return 校验结果
         */
        public static boolean verify(String original, String ciphertext) {
            return Control.MD5.verify(original, ciphertext);
        }
    }

    public static final class AES {
        private AES() {
        }

        /**
         * 获取AES key
         *
         * @return key
         */
        public static String key() {
            return Control.AES.key();
        }

        /**
         * 使用AES加密
         *
         * @param key       明文加密key
         * @param cleartext 明文
         * @return 加密后的字符串
         */
        public static String encrypt(String key, String cleartext) {
            return Control.AES.encrypt(key, cleartext);
        }

        /**
         * 使用AES解密
         *
         * @param key        密文解密key
         * @param ciphertext 密文
         * @return 解密后的字符串
         */
        public static String decrypt(String key, String ciphertext) {
            return Control.AES.decrypt(key, ciphertext);
        }
    }

    public static final class SHA1 {
        private SHA1() {
        }

        /**
         * 使用SHA1加密
         *
         * @param cleartext 明文
         * @return 加密后的字符串
         */
        public static String encrypt(String cleartext) {
            return Control.SHA1.encrypt(cleartext);
        }
    }

    public static final class Base64 {
        private Base64() {
        }

        /**
         * Base64加密
         *
         * @param cleartext 明文
         * @return 加密后的字符串
         */
        public static String encode(String cleartext) {
            return Control.Base_64.encode(cleartext);
        }

        /**
         * Base64解密
         *
         * @param cleartext 密文
         * @return 解密后的字符串
         */
        public static byte[] decode(String cleartext) {
            return Control.Base_64.decode(cleartext);
        }
    }

    public static final class RSA {
        private RSA() {
        }

        /**
         * RSA加密钥匙对
         */
        public static KeyPair keyPair() {
            return Control.RSA.initKeyPair();
        }

        /**
         * 获取RSA公钥
         *
         * @param publicKey 公钥
         * @return 公钥值
         */
        public static String publicKey(KeyPair publicKey) {
            return Control.RSA.publicKey(publicKey);
        }

        /**
         * 获取RSA私钥
         *
         * @param privateKey 私钥
         * @return 私钥值
         */
        public static String privateKey(KeyPair privateKey) {
            return Control.RSA.privateKey(privateKey);
        }

        /**
         * 使用公钥加密
         *
         * @param publicKey 公钥
         * @param cleartext 明文
         */
        public static String encryptPublic(String publicKey, String cleartext) {
            return Control.RSA.encryptByPublicKey(publicKey, cleartext);
        }

        /**
         * 使用私钥加密
         *
         * @param privateKey 私钥
         * @param cleartext  明文
         */
        public static String encryptPrivate(String privateKey, String cleartext) {
            return Control.RSA.encryptByPrivateKey(privateKey, cleartext);
        }

        /**
         * 使用RSA公钥解密
         *
         * @param publicKey  公钥
         * @param ciphertext 密文
         */
        public static String decryptPublic(String publicKey, String ciphertext) {
            return Control.RSA.decryptByPublicKey(publicKey, ciphertext);
        }

        /**
         * 使用RSA私钥解密
         *
         * @param privateKey 私钥
         * @param ciphertext 密文
         */
        public static String decryptPrivate(String privateKey, String ciphertext) {
            return Control.RSA.decryptByPrivateKey(privateKey, ciphertext);
        }

        /**
         * 使用RSA私钥签名
         *
         * @param privateKey 私钥
         * @param ciphertext 密文
         */
        public static String sign(String privateKey, String ciphertext) {
            return Control.RSA.sign(ciphertext, privateKey);
        }

        /**
         * 使用RSA数据校验
         *
         * @param publicKey  公钥
         * @param ciphertext 密文
         * @param sign       RSA签名
         */
        public static boolean verify(String publicKey, String ciphertext, String sign) {
            return Control.RSA.verify(ciphertext, publicKey, sign);
        }

    }


}
