package com.androidx.reduce.config;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Locale;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import static android.util.Base64.DEFAULT;

public final class Control {

    private Control() {
    }

    public static final class MD5 {

        private static final long timestamp = System.currentTimeMillis();

        private MD5() {
        }

        /**
         * 生成含有随机盐的MD5密码
         */
        public static String encrypt(String cleartext) {
            Random r = new Random();
            StringBuilder sb = new StringBuilder(64);
            sb.append(r.nextInt(99999999)).append(r.nextInt(99999999));
            int len = sb.length();
            if (len < 16) {
                for (int i = 0; i < 16 - len; i++) {
                    sb.append("0");
                }
            }
            String salt = sb.toString() + timestamp;
            cleartext = String.valueOf(md5Hex(cleartext + salt));
            char[] cs = new char[48];
            for (int i = 0; i < 48; i += 3) {
                cs[i] = cleartext.charAt(i / 3 * 2);
                char c = salt.charAt(i / 3);
                cs[i + 1] = c;
                cs[i + 2] = cleartext.charAt(i / 3 * 2 + 1);
            }
            return new String(cs);
        }

        /**
         * 获取十六进制字符串形式的MD5摘要
         */
        private static String md5Hex(String src) {
            try {
                MessageDigest md5 = MessageDigest.getInstance(Mode.MD5);
                byte[] bs = md5.digest(src.getBytes());
                // 把密文转换成十六进制的字符串形式
                int j = bs.length;
                char[] str = new char[j * 2];
                int k = 0;
                for (byte byte0 : bs) {
                    str[k++] = Mode.MD5_HEX[byte0 >>> 4 & 0xf];
                    str[k++] = Mode.MD5_HEX[byte0 & 0xf];
                }
                return new String(str);
            } catch (Exception e) {
                Log.e("MD5加密异常", Log.getStackTraceString(e));
                return null;
            }

        }

        /**
         * 校验密码是否正确
         */
        public static boolean verify(String cleartext, String ciphertext) {
            try {
                char[] cs1 = new char[32];
                char[] cs2 = new char[16];
                for (int i = 0; i < 48; i += 3) {
                    cs1[i / 3 * 2] = ciphertext.charAt(i);
                    cs1[i / 3 * 2 + 1] = ciphertext.charAt(i + 2);
                    cs2[i / 3] = ciphertext.charAt(i + 1);
                }
                String salt = new String(cs2) + timestamp;
                return String.valueOf(md5Hex(cleartext + salt)).equals(new String(cs1));
            } catch (Exception e) {
                Log.e("校验MD5密码异常", String.valueOf(e.getMessage()));
                return false;
            }
        }

    }

    public static final class AES {

        private AES() {
        }

        /**
         * 生成随机数，可以当做动态的密钥 加密和解密的密钥必须一致，不然将不能解密
         */
        public static String key() {
            try {
                SecureRandom localSecureRandom = SecureRandom.getInstance(Mode.AES_SHA1PRNG);
                byte[] bytes_key = new byte[16];
                localSecureRandom.nextBytes(bytes_key);
                return toHex(bytes_key);
            } catch (Exception e) {
                Log.e("AES生成key异常", Log.getStackTraceString(e));
                return "";
            }
        }

        /*
         * AES加密
         */
        public static String encrypt(String key, String cleartext) {
            if (TextUtils.isEmpty(cleartext)) {
                return cleartext;
            }
            try {
                byte[] result = encrypt(key, cleartext.getBytes());
                return parseBytes2Hex(result);
            } catch (Exception e) {
                Log.e("AES加密异常", Log.getStackTraceString(e));
                return "";
            }
        }

        /*
         * AES解密
         */
        public static String decrypt(String key, String ciphertext) {
            if (TextUtils.isEmpty(ciphertext)) return ciphertext;
            try {
                byte[] enc = parseHexStr2bytes(ciphertext);
                byte[] result = decrypt(key, enc);
                return new String(result);
            } catch (Exception e) {
                Log.e("AES解密异常", Log.getStackTraceString(e));
                return "";
            }
        }

        /*
         * 加密
         */
        private static byte[] encrypt(String key, byte[] cleartext) throws Exception {
            byte[] rawKey = InsecureSHA1PRNGKey.deriveInsecureKey(key.getBytes(), key.getBytes().length);
            SecretKeySpec keySpec = new SecretKeySpec(rawKey, Mode.AES);
            Cipher cipher = Cipher.getInstance(Mode.AES_CBC);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
            return cipher.doFinal(cleartext);
        }

        /*
         * 解密
         */
        private static byte[] decrypt(String key, byte[] ciphertext) throws Exception {
            byte[] rawKey = InsecureSHA1PRNGKey.deriveInsecureKey(key.getBytes(), key.getBytes().length);
            SecretKeySpec keySpec = new SecretKeySpec(rawKey, Mode.AES);
            Cipher cipher = Cipher.getInstance(Mode.AES_CBC);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
            return cipher.doFinal(ciphertext);
        }

        /**
         * 将二进制转成16进制，加密时用(方式之一)
         *
         * @param bytes 加密得到的二进制字节数组
         */
        private static String parseBytes2Hex(byte[] bytes) {
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                String hex = Integer.toHexString(aByte & 0xFF);
                if (hex.length() == 1) {
                    hex = "0" + hex;
                }
                sb.append(hex.toUpperCase());
            }
            return sb.toString();
        }

        /**
         * 将16进制串转成二进制数组，用于解密(方式1)
         */
        private static byte[] parseHexStr2bytes(String hexStr) {
            if (hexStr.length() < 1) {
                return null;
            }
            byte[] result = new byte[hexStr.length() / 2];
            for (int i = 0; i < result.length; i++) {
                int high = Integer.parseInt(hexStr.substring(2 * i, 2 * i + 1), 16);
                int low = Integer.parseInt(hexStr.substring(2 * i + 1, 2 * i + 2), 16);
                result[i] = (byte) (high * 16 + low);
            }
            return result;
        }

        /**
         * 二进制转字符
         */
        private static String toHex(byte[] buf) {
            if (buf == null)
                return "";
            StringBuffer result = new StringBuffer(2 * buf.length);
            for (byte aBuf : buf) {
                appendHex(result, aBuf);
            }
            return result.toString();
        }

        private static void appendHex(StringBuffer sb, byte b) {
            sb.append(Mode.AES_HEX.charAt((b >> 4) & 0x0f)).append(Mode.AES_HEX.charAt(b & 0x0f));
        }
    }

    public static final class RSA {

        private RSA() {
        }

        /**
         * 生成RSA密钥对
         * 密钥长度，范围：512～2048
         */
        public static KeyPair initKeyPair() {
            try {
                final KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(Mode.RSA);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    keyPairGen.initialize(Mode.RSA_SIZE, SecureRandom.getInstanceStrong());
                } else {
                    keyPairGen.initialize(Mode.RSA_SIZE, new SecureRandom());
                }
                return keyPairGen.generateKeyPair();
            } catch (Exception e) {
                Log.e("生成RSA密钥对异常", String.valueOf(e.getMessage()), e);
                return null;
            }
        }

        /**
         * 用私钥对信息生成数字签名
         *
         * @param ciphertext 明文
         * @param privateKey 私钥(BASE64编码)
         */
        public static String sign(String ciphertext, String privateKey) {
            final byte[] dataBytes = String.valueOf(ciphertext).getBytes(StandardCharsets.UTF_8);
            return encode(sign(dataBytes, getPrivateKey(decode(privateKey))));
        }

        /**
         * 校验数字签名
         *
         * @param ciphertext 已加密的数据
         * @param publicKey  公钥(BASE64编码)
         * @param sign       数字签名(BASE64编码)
         */
        public static boolean verify(String ciphertext, String publicKey, String sign) {
            try {
                final byte[] dataBytes = ciphertext.getBytes(StandardCharsets.UTF_8);
                final PublicKey key = getPublicKey(decode(publicKey));
                return verify(dataBytes, key, decode(sign));
            } catch (Exception e) {
                Log.e("RSA签名异常", Log.getStackTraceString(e));
                return false;
            }
        }


        /**
         * 校验数字签名
         *
         * @param ciphertext 已加密数据
         * @param publicKey  公钥
         * @param signBytes  数字签名
         */
        private static boolean verify(byte[] ciphertext, PublicKey publicKey, byte[] signBytes) throws Exception {
            final Signature signature = Signature.getInstance(Mode.RSA_SING);
            signature.initVerify(publicKey);
            signature.update(ciphertext);
            return signature.verify(signBytes);
        }

        /**
         * 公钥加密
         *
         * @param cleartext 源数据
         * @param publicKey 公钥(BASE64编码)
         * @return BASE64编码的加密数据
         */
        public static String encryptByPublicKey(String cleartext, String publicKey) {
            try {
                final byte[] dataBytes = cleartext.getBytes(StandardCharsets.UTF_8);
                PublicKey key = getPublicKey(decode(publicKey));
                final byte[] encryptDataBytes = encryptByPublicKey(dataBytes, key);
                return encode(encryptDataBytes);
            } catch (Exception e) {
                Log.e("RSA公钥加密异常", String.valueOf(e.getMessage()), e);
                return "";
            }
        }

        /**
         * 私钥加密
         *
         * @param cleartext  源数据
         * @param privateKey 私钥(BASE64编码)
         * @return BASE64编码的加密数据
         */
        public static String encryptByPrivateKey(String cleartext, String privateKey) {
            try {
                final byte[] dataBytes = cleartext.getBytes(StandardCharsets.UTF_8);
                PrivateKey key = getPrivateKey(decode(privateKey));
                final byte[] encryptDataBytes = encryptByPrivateKey(dataBytes, key);
                return encode(encryptDataBytes);
            } catch (Exception e) {
                Log.e("RSA私钥加密异常", String.valueOf(e.getMessage()), e);
                return "";
            }
        }

        /**
         * 公钥解密
         *
         * @param ciphertext 私钥加密的数据(BASE64编码)
         * @param publicKey  公钥(BASE64编码)
         * @return 私钥加密前的数据
         */
        public static String decryptByPublicKey(String ciphertext, String publicKey) {
            try {
                final byte[] dataBytes = decode(ciphertext);
                PublicKey key = getPublicKey(decode(publicKey));
                final byte[] decryptDataBytes = decryptByPublicKey(dataBytes, key);
                return new String(decryptDataBytes, StandardCharsets.UTF_8);
            } catch (Exception e) {
                Log.e("RSA公钥解密异常", String.valueOf(e.getMessage()), e);
                return "";
            }
        }

        /**
         * 私钥解密
         *
         * @param ciphertext 公钥加密的数据(BASE64编码)
         * @param privateKey 私钥(BASE64编码)
         * @return 公钥加密前的数据
         */
        public static String decryptByPrivateKey(String ciphertext, String privateKey) {
            try {
                final byte[] dataBytes = decode(ciphertext);
                PrivateKey key = getPrivateKey(decode(privateKey));
                final byte[] decryptDataBytes = decryptByPrivateKey(dataBytes, key);
                return new String(decryptDataBytes, StandardCharsets.UTF_8);
            } catch (Exception e) {
                Log.e("RSA私钥解密异常", String.valueOf(e.getMessage()), e);
                return "";
            }
        }

        /**
         * 获取公钥
         */
        private static PublicKey getPublicKey(byte[] keyBytes) throws Exception {
            final X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
            final KeyFactory keyFactory = KeyFactory.getInstance(Mode.RSA);
            return keyFactory.generatePublic(x509EncodedKeySpec);
        }

        /**
         * 获取私钥
         */
        private static PrivateKey getPrivateKey(byte[] keyBytes) {
            try {
                final KeyFactory keyFactory;
                final PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
                keyFactory = KeyFactory.getInstance(Mode.RSA);
                return keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            } catch (Exception e) {
                Log.e("RSA获取私钥异常", Log.getStackTraceString(e));
                return null;
            }
        }

        /**
         * 用私钥对信息生成数字签名
         *
         * @param data       数据
         * @param privateKey 私钥
         */
        private static byte[] sign(byte[] data, PrivateKey privateKey) {
            try {
                final Signature signature;
                signature = Signature.getInstance(Mode.RSA_SING);
                signature.initSign(privateKey);
                signature.update(data);
                return signature.sign();
            } catch (Exception e) {
                Log.e("RSA私钥签名异常", Log.getStackTraceString(e));
                return null;
            }
        }

        /**
         * 公钥加密
         *
         * @param data      源数据
         * @param publicKey 公钥
         */
        private static byte[] encryptByPublicKey(byte[] data, PublicKey publicKey) throws Exception {
            final Cipher cipher = Cipher.getInstance(Mode.RSA_ECB);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptedData;
            try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                int offSet = 0;
                byte[] cache;
                int i = 0;
                final int inputLen = data.length;
                // 对数据分段加密
                while (inputLen - offSet > 0) {
                    if (inputLen - offSet > Mode.RSA_ENCRYPT) {
                        cache = cipher.doFinal(data, offSet, Mode.RSA_ENCRYPT);
                    } else {
                        cache = cipher.doFinal(data, offSet, inputLen - offSet);
                    }
                    out.write(cache, 0, cache.length);
                    i++;
                    offSet = i * Mode.RSA_ENCRYPT;
                }
                encryptedData = out.toByteArray();
            }
            return encryptedData;
        }

        /**
         * 私钥加密
         *
         * @param data       源数据
         * @param privateKey 私钥
         */
        private static byte[] encryptByPrivateKey(byte[] data, PrivateKey privateKey) throws Exception {
            final Cipher cipher = Cipher.getInstance(Mode.RSA_ECB);
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            byte[] encryptedData;
            try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                int offSet = 0;
                byte[] cache;
                int i = 0;
                final int inputLen = data.length;
                // 对数据分段加密
                while (inputLen - offSet > 0) {
                    if (inputLen - offSet > Mode.RSA_ENCRYPT) {
                        cache = cipher.doFinal(data, offSet, Mode.RSA_ENCRYPT);
                    } else {
                        cache = cipher.doFinal(data, offSet, inputLen - offSet);
                    }
                    out.write(cache, 0, cache.length);
                    i++;
                    offSet = i * Mode.RSA_ENCRYPT;
                }
                encryptedData = out.toByteArray();
            }
            return encryptedData;
        }

        /**
         * 公钥解密
         *
         * @param encryptedData 公钥加密的数据
         * @param publicKey     公钥
         */
        private static byte[] decryptByPublicKey(byte[] encryptedData, PublicKey publicKey) throws Exception {
            final Cipher cipher = Cipher.getInstance(Mode.RSA_ECB);
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            byte[] decryptedData;
            try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                int offSet = 0;
                byte[] cache;
                int i = 0;
                final int inputLen = encryptedData.length;
                // 对数据分段解密
                while (inputLen - offSet > 0) {
                    if (inputLen - offSet > Mode.RSA_DECRYPT) {
                        cache = cipher.doFinal(encryptedData, offSet, Mode.RSA_DECRYPT);
                    } else {
                        cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
                    }
                    out.write(cache, 0, cache.length);
                    i++;
                    offSet = i * Mode.RSA_DECRYPT;
                }
                decryptedData = out.toByteArray();
            }
            return decryptedData;
        }

        /**
         * 私钥解密
         *
         * @param encryptedData 公钥加密的数据
         * @param privateKey    私钥
         */
        private static byte[] decryptByPrivateKey(byte[] encryptedData, PrivateKey privateKey) throws Exception {
            final Cipher cipher = Cipher.getInstance(Mode.RSA_ECB);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptedData;
            try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                int offSet = 0;
                byte[] cache;
                int i = 0;
                final int inputLen = encryptedData.length;
                // 对数据分段解密
                while (inputLen - offSet > 0) {
                    if (inputLen - offSet > Mode.RSA_DECRYPT) {
                        cache = cipher.doFinal(encryptedData, offSet, Mode.RSA_DECRYPT);
                    } else {
                        cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
                    }
                    out.write(cache, 0, cache.length);
                    i++;
                    offSet = i * Mode.RSA_DECRYPT;
                }
                decryptedData = out.toByteArray();
            }
            return decryptedData;
        }

        /**
         * Base64编码数据
         */
        private static String encode(byte[] binaryData) {
            return Base_64.encode(binaryData);
        }

        /**
         * Base64解码数据
         *
         * @param encoded (BASE64编码)
         */
        private static byte[] decode(String encoded) {
            return Base_64.decode(encoded);
        }

        /**
         * 获取公钥
         *
         * @return 公钥
         */
        public static String publicKey(KeyPair keyPair) {
            return encode(keyPair.getPublic().getEncoded());
        }

        /**
         * 获取私钥
         *
         * @return 私钥
         */
        public static String privateKey(KeyPair keyPair) {
            return encode(keyPair.getPrivate().getEncoded());
        }

    }

    public static final class Base_64 {

        /**
         * Base64编码
         *
         * @param data 保存地址
         */
        public static String encode(byte[] data) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return java.util.Base64.getMimeEncoder().encodeToString(data);
            } else {
                return Base64.encodeToString(data, DEFAULT);
            }
        }

        /**
         * base64解码
         *
         * @param data 密文
         */
        public static byte[] decode(String data) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return java.util.Base64.getMimeDecoder().decode(data);
            } else {
                return Base64.decode(data, DEFAULT);
            }
        }
    }

    public static final class SHA1 {

        public static String app(Context context) {
            try {
                @SuppressLint("PackageManagerGetSignatures")
                PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
                byte[] cert = info.signatures[0].toByteArray();
                MessageDigest md = MessageDigest.getInstance(Mode.SHA1);
                byte[] publicKey = md.digest(cert);
                StringBuilder hexString = new StringBuilder();
                for (byte aPublicKey : publicKey) {
                    String appendString = Integer.toHexString(0xFF & aPublicKey).toUpperCase(Locale.US);
                    if (appendString.length() == 1)
                        hexString.append("0");
                    hexString.append(appendString);
                    hexString.append(":");
                }
                String result = hexString.toString();
                return result.substring(0, result.length() - 1);
            } catch (Exception e) {
                Log.e("SHA1编码异常", Log.getStackTraceString(e));
                return "";
            }
        }

        public static String encrypt(String cleartext) {
            try {
                MessageDigest alga = MessageDigest.getInstance(Mode.SHA1);
                alga.update(cleartext.getBytes());
                return byte2hex(alga.digest());
            } catch (Exception e) {
                Log.e("SHA1加密异常", Log.getStackTraceString(e));
                return "";
            }
        }

        public static String encrypt224(String cleartext) {
            try {
                MessageDigest alga = MessageDigest.getInstance(Mode.SHA224);
                alga.update(cleartext.getBytes());
                return byte2hex(alga.digest());
            } catch (Exception e) {
                System.err.println("SHA1加密异常: " + e.getMessage());
                return "";
            }
        }

        public static String encrypt256(String cleartext) {
            try {
                MessageDigest alga = MessageDigest.getInstance(Mode.SHA256);
                alga.update(cleartext.getBytes());
                return byte2hex(alga.digest());
            } catch (Exception e) {
                System.err.println("SHA1加密异常: " + e.getMessage());
                return "";
            }
        }

        public static String encrypt384(String cleartext) {
            try {
                MessageDigest alga = MessageDigest.getInstance(Mode.SHA384);
                alga.update(cleartext.getBytes());
                return byte2hex(alga.digest());
            } catch (Exception e) {
                System.err.println("SHA1加密异常: " + e.getMessage());
                return "";
            }
        }

        public static String encrypt512(String cleartext) {
            try {
                MessageDigest alga = MessageDigest.getInstance(Mode.SHA512);
                alga.update(cleartext.getBytes());
                return byte2hex(alga.digest());
            } catch (Exception e) {
                System.err.println("SHA1加密异常: " + e.getMessage());
                return "";
            }
        }

        private static String byte2hex(byte[] b) {
            StringBuilder hs = new StringBuilder();
            int i = 0, bLength = b.length;
            while (i < bLength) {
                byte value = b[i];
                String s = (Integer.toHexString(value & 0XFF));
                if (s.length() == 1) hs.append("0").append(s);
                else hs.append(s);
                i++;
            }
            return hs.toString();
        }

    }

}
