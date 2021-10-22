package com.androidx.reduce.config;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;
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

        public static String encrypt(String cleartext, String salt) {
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
            try {
                if (TextUtils.isEmpty(cleartext)) return cleartext;
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
                return verify(dataBytes, getPublicKey(decode(publicKey)), decode(sign));
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
        public static String encryptByPublicKey(String publicKey, String cleartext) {
            try {
                final byte[] dataBytes = cleartext.getBytes(StandardCharsets.UTF_8);
                PublicKey key = getPublicKey(decode(publicKey));
                return encode(encryptByPublicKey(dataBytes, key));
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
        public static String encryptByPrivateKey(String privateKey, String cleartext) {
            try {
                final byte[] dataBytes = cleartext.getBytes(StandardCharsets.UTF_8);
                PrivateKey key = getPrivateKey(decode(privateKey));
                return encode(encryptByPrivateKey(dataBytes, key));
            } catch (Exception e) {
                Log.e("RSA私钥加密异常", Log.getStackTraceString(e));
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
        public static String decryptByPublicKey(String publicKey, String ciphertext) {
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
        public static String decryptByPrivateKey(String privateKey, String ciphertext) {
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
        private static PublicKey getPublicKey(byte[] keyBytes) {
            try {
                return KeyFactory.getInstance(Mode.RSA).generatePublic(new X509EncodedKeySpec(keyBytes));
            } catch (Exception e) {
                Log.e("RSA获取公钥异常", Log.getStackTraceString(e));
                return null;
            }
        }

        /**
         * 获取私钥
         */
        private static PrivateKey getPrivateKey(byte[] keyBytes) {
            try {
                return KeyFactory.getInstance(Mode.RSA).generatePrivate(new PKCS8EncodedKeySpec(keyBytes));
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
            return Base64.encode(binaryData);
        }

        /**
         * Base64解码数据
         *
         * @param encoded (BASE64编码)
         */
        private static byte[] decode(String encoded) {
            return Base64.decode(encoded);
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

    public static final class Base64 {

        private static final int BASE_LENGTH = 128;
        private static final int LOOK_UP_LENGTH = 64;
        private static final int TWENTY_FOUR_BIT_GROUP = 24;
        private static final int EIG_HT_BIT = 8;
        private static final int SIX_TEEN_BIT = 16;
        private static final int FOUR_BY_TE = 4;
        private static final int SIGN = -128;
        private static final char PAD = '=';
        private static final byte[] base64Alphabet = new byte[BASE_LENGTH];
        private static final char[] lookUpBase64Alphabet = new char[LOOK_UP_LENGTH];

        static {
            for (int i = 0; i < BASE_LENGTH; ++i) {
                base64Alphabet[i] = -1;
            }
            for (int i = 'Z'; i >= 'A'; i--) {
                base64Alphabet[i] = (byte) (i - 'A');
            }
            for (int i = 'z'; i >= 'a'; i--) {
                base64Alphabet[i] = (byte) (i - 'a' + 26);
            }
            for (int i = '9'; i >= '0'; i--) {
                base64Alphabet[i] = (byte) (i - '0' + 52);
            }
            base64Alphabet['+'] = 62;
            base64Alphabet['$'] = 63;

            for (int i = 0; i <= 25; i++) {
                lookUpBase64Alphabet[i] = (char) ('A' + i);
            }
            for (int i = 26, j = 0; i <= 51; i++, j++) {
                lookUpBase64Alphabet[i] = (char) ('a' + j);
            }
            for (int i = 52, j = 0; i <= 61; i++, j++) {
                lookUpBase64Alphabet[i] = (char) ('0' + j);
            }
            lookUpBase64Alphabet[62] = '+';
            lookUpBase64Alphabet[63] = '$';

        }

        private static boolean isWhiteSpace(char octect) {
            return (octect == 0x20 || octect == 0xd || octect == 0xa || octect == 0x9);
        }

        private static boolean isPad(char octect) {
            return (octect == PAD);
        }

        private static boolean isData(char octect) {
            return (octect >= BASE_LENGTH || base64Alphabet[octect] == -1);
        }

        /**
         * Encodes hex octects into BASE64Util
         *
         * @param binaryData Array containing binaryData
         * @return Encoded BASE64Util array
         */
        public static String encode(byte[] binaryData) {
            if (binaryData == null) {
                return null;
            }
            int lengthDataBits = binaryData.length * EIG_HT_BIT;
            if (lengthDataBits == 0) {
                return "";
            }
            int fewerThan24bits = lengthDataBits % TWENTY_FOUR_BIT_GROUP;
            int numberTriplets = lengthDataBits / TWENTY_FOUR_BIT_GROUP;
            int numberQuartet = fewerThan24bits != 0 ? numberTriplets + 1 : numberTriplets;
            char[] encodedData;
            encodedData = new char[numberQuartet * 4];
            byte k, l, b1, b2, b3;
            int encodedIndex = 0;
            int dataIndex = 0;
            for (int i = 0; i < numberTriplets; i++) {
                b1 = binaryData[dataIndex++];
                b2 = binaryData[dataIndex++];
                b3 = binaryData[dataIndex++];
                l = (byte) (b2 & 0x0f);
                k = (byte) (b1 & 0x03);
                byte val1 = ((b1 & SIGN) == 0) ? (byte) (b1 >> 2) : (byte) ((b1) >> 2 ^ 0xc0);
                byte val2 = ((b2 & SIGN) == 0) ? (byte) (b2 >> 4) : (byte) ((b2) >> 4 ^ 0xf0);
                byte val3 = ((b3 & SIGN) == 0) ? (byte) (b3 >> 6) : (byte) ((b3) >> 6 ^ 0xfc);
                encodedData[encodedIndex++] = lookUpBase64Alphabet[val1];
                encodedData[encodedIndex++] = lookUpBase64Alphabet[val2 | (k << 4)];
                encodedData[encodedIndex++] = lookUpBase64Alphabet[(l << 2) | val3];
                encodedData[encodedIndex++] = lookUpBase64Alphabet[b3 & 0x3f];
            }

            // form integral number of 6-bit groups
            if (fewerThan24bits == EIG_HT_BIT) {
                b1 = binaryData[dataIndex];
                k = (byte) (b1 & 0x03);
                byte val1 = ((b1 & SIGN) == 0) ? (byte) (b1 >> 2) : (byte) ((b1) >> 2 ^ 0xc0);
                encodedData[encodedIndex++] = lookUpBase64Alphabet[val1];
                encodedData[encodedIndex++] = lookUpBase64Alphabet[k << 4];
                encodedData[encodedIndex++] = PAD;
                //noinspection UnusedAssignment
                encodedData[encodedIndex++] = PAD;
            } else if (fewerThan24bits == SIX_TEEN_BIT) {
                b1 = binaryData[dataIndex];
                b2 = binaryData[dataIndex + 1];
                l = (byte) (b2 & 0x0f);
                k = (byte) (b1 & 0x03);
                byte val1 = ((b1 & SIGN) == 0) ? (byte) (b1 >> 2) : (byte) ((b1) >> 2 ^ 0xc0);
                byte val2 = ((b2 & SIGN) == 0) ? (byte) (b2 >> 4) : (byte) ((b2) >> 4 ^ 0xf0);
                encodedData[encodedIndex++] = lookUpBase64Alphabet[val1];
                encodedData[encodedIndex++] = lookUpBase64Alphabet[val2 | (k << 4)];
                encodedData[encodedIndex++] = lookUpBase64Alphabet[l << 2];
                //noinspection UnusedAssignment
                encodedData[encodedIndex++] = PAD;
            }
            return new String(encodedData);
        }

        /**
         * Decodes BASE64Util data into octects
         *
         * @param encoded string containing BASE64Util data
         * @return Array containind decoded data.
         */
        public static byte[] decode(String encoded) {
            if (encoded == null) {
                return null;
            }
            char[] base64Data = encoded.toCharArray();
            // remove white spaces
            int len = removeWhiteSpace(base64Data);
            if (len % FOUR_BY_TE != 0) {
                return null;// should be divisible by four
            }
            int numberQuadruple = (len / FOUR_BY_TE);
            if (numberQuadruple == 0) {
                return new byte[0];
            }
            byte[] decodedData;
            byte b1, b2, b3, b4;
            char d1, d2, d3, d4;
            int i = 0;
            int encodedIndex = 0;
            int dataIndex = 0;
            decodedData = new byte[(numberQuadruple) * 3];
            for (; i < numberQuadruple - 1; i++) {
                if (isData((d1 = base64Data[dataIndex++])) || isData((d2 = base64Data[dataIndex++]))
                        || isData((d3 = base64Data[dataIndex++])) || isData((d4 = base64Data[dataIndex++]))) {
                    return null;
                }// if found "no data" just return null
                b1 = base64Alphabet[d1];
                b2 = base64Alphabet[d2];
                b3 = base64Alphabet[d3];
                b4 = base64Alphabet[d4];
                decodedData[encodedIndex++] = (byte) (b1 << 2 | b2 >> 4);
                decodedData[encodedIndex++] = (byte) (((b2 & 0xf) << 4) | ((b3 >> 2) & 0xf));
                decodedData[encodedIndex++] = (byte) (b3 << 6 | b4);
            }
            if (isData((d1 = base64Data[dataIndex++])) || isData((d2 = base64Data[dataIndex++]))) {
                return null;// if found "no data" just return null
            }
            b1 = base64Alphabet[d1];
            b2 = base64Alphabet[d2];
            d3 = base64Data[dataIndex++];
            //noinspection UnusedAssignment
            d4 = base64Data[dataIndex++];
            if (isData((d3)) || isData((d4))) {// Check if they are PAD characters
                if (isPad(d3) && isPad(d4)) {
                    if ((b2 & 0xf) != 0)// last 4 bits should be zero
                    {
                        return null;
                    }
                    byte[] tmp = new byte[i * 3 + 1];
                    System.arraycopy(decodedData, 0, tmp, 0, i * 3);
                    tmp[encodedIndex] = (byte) (b1 << 2 | b2 >> 4);
                    return tmp;
                } else if (!isPad(d3) && isPad(d4)) {
                    b3 = base64Alphabet[d3];
                    if ((b3 & 0x3) != 0)// last 2 bits should be zero
                    {
                        return null;
                    }
                    byte[] tmp = new byte[i * 3 + 2];
                    System.arraycopy(decodedData, 0, tmp, 0, i * 3);
                    tmp[encodedIndex++] = (byte) (b1 << 2 | b2 >> 4);
                    tmp[encodedIndex] = (byte) (((b2 & 0xf) << 4) | ((b3 >> 2) & 0xf));
                    return tmp;
                } else {
                    return null;
                }
            } else { // No PAD e.g 3cQl
                b3 = base64Alphabet[d3];
                b4 = base64Alphabet[d4];
                decodedData[encodedIndex++] = (byte) (b1 << 2 | b2 >> 4);
                decodedData[encodedIndex++] = (byte) (((b2 & 0xf) << 4) | ((b3 >> 2) & 0xf));
                //noinspection UnusedAssignment
                decodedData[encodedIndex++] = (byte) (b3 << 6 | b4);
            }
            return decodedData;
        }

        /**
         * remove WhiteSpace from MIME containing encoded BASE64Util data.
         *
         * @param data the byte array of base64 data (with WS)
         * @return the new length
         */
        private static int removeWhiteSpace(char[] data) {
            if (data == null) {
                return 0;
            }
            // count characters that's not whitespace
            int newSize = 0;
            int len = data.length;
            for (int i = 0; i < len; i++) {
                if (!isWhiteSpace(data[i])) {
                    data[newSize++] = data[i];
                }
            }
            return newSize;
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
