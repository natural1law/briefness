package com.androidx.reduce.tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import static android.util.Base64.DEFAULT;

@SuppressWarnings("ALL")
public final class Secure {

    public static final class MD5 {

        private static char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

        private MD5() {
        }

        /**
         * 生成含有随机盐的MD5密码
         */
        public static String encrypt(String password) {
            Random r = new Random();
            StringBuilder sb = new StringBuilder(64);
            sb.append(r.nextInt(99999999)).append(r.nextInt(99999999));
            int len = sb.length();
            if (len < 16) {
                for (int i = 0; i < 16 - len; i++) {
                    sb.append("0");
                }
            }
            String salt = sb.toString();
            password = md5Hex(password + salt);
            char[] cs = new char[48];
            for (int i = 0; i < 48; i += 3) {
                cs[i] = Objects.requireNonNull(password).charAt(i / 3 * 2);
                char c = salt.charAt(i / 3);
                cs[i + 1] = c;
                cs[i + 2] = password.charAt(i / 3 * 2 + 1);
            }
            return new String(cs);
        }

        /**
         * 获取十六进制字符串形式的MD5摘要
         */
        private static String md5Hex(String src) {
            try {
                MessageDigest md5 = MessageDigest.getInstance("MD5");
                byte[] bs = md5.digest(src.getBytes());
                // 把密文转换成十六进制的字符串形式
                int j = bs.length;
                char[] str = new char[j * 2];
                int k = 0;
                for (byte byte0 : bs) {
                    str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                    str[k++] = hexDigits[byte0 & 0xf];
                }
                return new String(str);
            } catch (Exception e) {
                return null;
            }

        }

        /**
         * 校验密码是否正确
         */
        public static boolean verify(String password, String md5) {
            try {
                char[] cs1 = new char[32];
                char[] cs2 = new char[16];
                for (int i = 0; i < 48; i += 3) {
                    cs1[i / 3 * 2] = md5.charAt(i);
                    cs1[i / 3 * 2 + 1] = md5.charAt(i + 2);
                    cs2[i / 3] = md5.charAt(i + 1);
                }
                String salt = new String(cs2);
                return Objects.requireNonNull(md5Hex(password + salt)).equals(new String(cs1));
            } catch (Exception e) {
                Log.e("校验密码异常", String.valueOf(e.getMessage()));
                return false;
            }
        }

    }

    public static final class AES {

        private static final String HEX = "0123456789ABCDEF";
        private static final String CBC = "AES/CBC/PKCS7PADDING";//AES是加密方式 CBC是工作模式 PKCS5Padding是填充模式
        private static final String AES = "AES";//AES 加密
        private static final String SHA1PRNG = "SHA1PRNG";//// SHA1PRNG 强随机种子算法, 要区别4.2以上版本的调用方法


        private AES() {
        }

        /**
         * 生成随机数，可以当做动态的密钥 加密和解密的密钥必须一致，不然将不能解密
         */
        public static String key() {
            try {
                SecureRandom localSecureRandom = SecureRandom.getInstance(SHA1PRNG);
                byte[] bytes_key = new byte[16];
                localSecureRandom.nextBytes(bytes_key);
                return toHex(bytes_key);
            } catch (Exception e) {
                return e.getMessage();
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
                return e.getMessage();
            }
        }

        /*
         * AES解密
         */
        public static String decrypt(String key, String encrypted) {
            if (TextUtils.isEmpty(encrypted)) {
                return encrypted;
            }
            try {
                byte[] enc = parseHexStr2bytes(encrypted);
                byte[] result = decrypt(key, enc);
                return new String(result);
            } catch (Exception e) {
                return e.getMessage();
            }
        }

        /*
         * 加密
         */
        private static byte[] encrypt(String key, byte[] clear) throws Exception {
            byte[] rawKey = RawSha1PRNGKey.deriveInsecureKey(key.getBytes());
            SecretKeySpec keySpec = new SecretKeySpec(rawKey, AES);
            Cipher cipher = Cipher.getInstance(CBC);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
            return cipher.doFinal(clear);
        }

        /*
         * 解密
         */
        private static byte[] decrypt(String key, byte[] encrypted) throws Exception {
            byte[] rawKey = RawSha1PRNGKey.deriveInsecureKey(key.getBytes());
            SecretKeySpec keySpec = new SecretKeySpec(rawKey, AES);
            Cipher cipher = Cipher.getInstance(CBC);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
            return cipher.doFinal(encrypted);
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
            sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
        }

        /**
         * Stripped-down version of the SHA1PRNG provided by the Crypto provider.
         * <p>
         * The Crypto provider that offers this functionality was deprecated on Android.
         * <p>
         * Use this class only to retrieve encrypted data that couldn't be retrieved otherwise.
         *
         * @classname InsecureSHA1PRNGKeyDerivator
         */
        private static final class RawSha1PRNGKey {
            // HASHBYTES_TO_USE defines # of bytes returned by "computeHash(byte[])"
            // to use to form byte array returning by the "nextBytes(byte[])" method
            // Note, that this implementation uses more bytes than it is defined
            // in the above specification.
            private static final int HASHBYTES_TO_USE = 20;
            // value of 16 defined in the "SECURE HASH STANDARD", FIPS PUB 180-2
            private static final int FRAME_LENGTH = 16;
            // miscellaneous constants defined in this implementation:
            // COUNTER_BASE - initial value to set to "counter" before computing "nextBytes(..)";
            //                note, that the exact value is not defined in STANDARD
            // HASHCOPY_OFFSET   - offset for copy of current hash in "copies" array
            // EXTRAFRAME_OFFSET - offset for extra frame in "copies" array;
            //                     as the extra frame follows the current hash frame,
            //                     EXTRAFRAME_OFFSET is equal to length of current hash frame
            // FRAME_OFFSET      - offset for frame in "copies" array
            // MAX_BYTES - maximum # of seed bytes processing which doesn't require extra frame
            //             see (1) comments on usage of "seed" array below and
            //             (2) comments in "engineNextBytes(byte[])" method
            //
            // UNDEFINED  - three states of engine; initially its state is "UNDEFINED"
            // SET_SEED     call to "engineSetSeed"  sets up "SET_SEED" state,
            // NEXT_BYTES   call to "engineNextByte" sets up "NEXT_BYTES" state
            private static final int COUNTER_BASE = 0;
            private static final int HASHCOPY_OFFSET = 0;
            private static final int EXTRAFRAME_OFFSET = 5;
            private static final int FRAME_OFFSET = 21;
            private static final int MAX_BYTES = 48;
            private static final int UNDEFINED = 0;
            private static final int SET_SEED = 1;
            private static final int NEXT_BYTES = 2;
            /**
             * constant defined in "SECURE HASH STANDARD"
             */
            private static final int H0 = 0x67452301;
            /**
             * constant defined in "SECURE HASH STANDARD"
             */
            private static final int H1 = 0xEFCDAB89;
            /**
             * constant defined in "SECURE HASH STANDARD"
             */
            private static final int H2 = 0x98BADCFE;
            /**
             * constant defined in "SECURE HASH STANDARD"
             */
            private static final int H3 = 0x10325476;
            /**
             * constant defined in "SECURE HASH STANDARD"
             */
            private static final int H4 = 0xC3D2E1F0;
            /**
             * offset in buffer to store number of bytes in 0-15 word frame
             */
            private static final int BYTES_OFFSET = 81;
            /**
             * offset in buffer to store current hash value
             */
            private static final int HASH_OFFSET = 82;
            /**
             * # of bytes in H0-H4 words; <BR>
             * in this implementation # is set to 20 (in general # varies from 1 to 20)
             */
            private static final int DIGEST_LENGTH = 20;
            // constants to use in expressions operating on bytes in int and long variables:
            // END_FLAGS - final bytes in words to append to message;
            //             see "ch.5.1 Padding the Message, FIPS 180-2"
            // RIGHT1    - shifts to right for left half of long
            // RIGHT2    - shifts to right for right half of long
            // LEFT      - shifts to left for bytes
            // MASK      - mask to select counter's bytes after shift to right
            private final int[] END_FLAGS = {0x80000000, 0x800000, 0x8000, 0x80};
            private final int[] RIGHT1 = {0, 40, 48, 56};
            private final int[] RIGHT2 = {0, 8, 16, 24};
            private final int[] LEFT = {0, 24, 16, 8};
            private final int[] MASK = {0xFFFFFFFF, 0x00FFFFFF, 0x0000FFFF,
                    0x000000FF};
            // Structure of "seed" array:
            // -  0-79 - words for computing hash
            // - 80    - unused
            // - 81    - # of seed bytes in current seed frame
            // - 82-86 - 5 words, current seed hash
            private transient int[] seed;
            // total length of seed bytes, including all processed
            private transient long seedLength;
            // Structure of "copies" array
            // -  0-4  - 5 words, copy of current seed hash
            // -  5-20 - extra 16 words frame;
            //           is used if final padding exceeds 512-bit length
            // - 21-36 - 16 word frame to store a copy of remaining bytes
            private transient int[] copies;
            // ready "next" bytes; needed because words are returned
            private transient byte[] nextBytes;
            // index of used bytes in "nextBytes" array
            private transient int nextBIndex;
            // variable required according to "SECURE HASH STANDARD"
            private transient long counter;
            // contains int value corresponding to engine's current state
            private transient int state;

            // The "seed" array is used to compute both "current seed hash" and "next bytes".
            //
            // As the "SHA1" algorithm computes a hash of entire seed by splitting it into
            // a number of the 512-bit length frames (512 bits = 64 bytes = 16 words),
            // "current seed hash" is a hash (5 words, 20 bytes) for all previous full frames;
            // remaining bytes are stored in the 0-15 word frame of the "seed" array.
            //
            // As for calculating "next bytes",
            // both remaining bytes and "current seed hash" are used,
            // to preserve the latter for following "setSeed(..)" commands,
            // the following technique is used:
            // - upon getting "nextBytes(byte[])" invoked, single or first in row,
            //   which requires computing new hash, that is,
            //   there is no more bytes remaining from previous "next bytes" computation,
            //   remaining bytes are copied into the 21-36 word frame of the "copies" array;
            // - upon getting "setSeed(byte[])" invoked, single or first in row,
            //   remaining bytes are copied back.
            private RawSha1PRNGKey() {
                seed = new int[HASH_OFFSET + EXTRAFRAME_OFFSET];
                seed[HASH_OFFSET] = H0;
                seed[HASH_OFFSET + 1] = H1;
                seed[HASH_OFFSET + 2] = H2;
                seed[HASH_OFFSET + 3] = H3;
                seed[HASH_OFFSET + 4] = H4;
                seedLength = 0;
                copies = new int[2 * FRAME_LENGTH + EXTRAFRAME_OFFSET];
                nextBytes = new byte[DIGEST_LENGTH];
                nextBIndex = HASHBYTES_TO_USE;
                counter = COUNTER_BASE;
                state = UNDEFINED;
            }

            /**
             * Only public method. Derive a key from the given seed.
             * <p>
             * Use this method only to retrieve encrypted data that couldn't be retrieved otherwise.
             *
             * @param seed seed used for the random generator, usually coming from a password
             */
            static byte[] deriveInsecureKey(byte[] seed) {
                RawSha1PRNGKey raw = new RawSha1PRNGKey();
                raw.setSeed(seed);
                byte[] key = new byte[32];
                raw.nextBytes(key);
                return key;
            }

            /*
             * The method invokes the SHA1Impl's "updateHash(..)" method
             * to update current seed frame and
             * to compute new intermediate hash value if the frame is full.
             *
             * After that it computes a length of whole seed.
             */
            private void updateSeed(byte[] bytes) {
                // on call:   "seed" contains current bytes and current hash;
                // on return: "seed" contains new current bytes and possibly new current hash
                //            if after adding, seed bytes overfill its buffer
                updateHash(seed, bytes, bytes.length - 1);
                seedLength += bytes.length;
            }

            /**
             * Changes current seed by supplementing a seed argument to the current seed,
             * if this already set;
             * the argument is used as first seed otherwise. <BR>
             * <p>
             * The method overrides "engineSetSeed(byte[])" in class SecureRandomSpi.
             *
             * @param seed - byte array
             */
            private void setSeed(byte[] seed) {
                if (seed == null) {
                    throw new NullPointerException("seed == null");
                }
                if (state == NEXT_BYTES) { // first setSeed after NextBytes; restoring hash
                    System.arraycopy(copies, HASHCOPY_OFFSET, this.seed, HASH_OFFSET,
                            EXTRAFRAME_OFFSET);
                }
                state = SET_SEED;
                if (seed.length != 0) {
                    updateSeed(seed);
                }
            }

            /**
             * Writes random bytes into an array supplied.
             * Bits in a byte are from left to right. <BR>
             * <p>
             * To generate random bytes, the "expansion of source bits" method is used,
             * that is,
             * the current seed with a 64-bit counter appended is used to compute new bits.
             * The counter is incremented by 1 for each 20-byte output. <BR>
             * <p>
             * The method overrides engineNextBytes in class SecureRandomSpi.
             *
             * @param bytes - byte array to be filled in with bytes
             */
            synchronized void nextBytes(byte[] bytes) {
                int i, n;
                long bits; // number of bits required by Secure Hash Standard
                int nextByteToReturn; // index of ready bytes in "bytes" array
                int lastWord; // index of last word in frame containing bytes
                // This is a bug since words are 4 bytes. Android used to keep it this way for backward
                // compatibility.
                final int extrabytes = 7;// # of bytes to add in order to computer # of 8 byte words
                if (bytes == null) {
                    throw new NullPointerException("bytes == null");
                }
                // This is a bug since extraBytes == 7 instead of 3. Android used to keep it this way for
                // backward compatibility.
                lastWord = seed[BYTES_OFFSET] == 0 ? 0
                        : (seed[BYTES_OFFSET] + extrabytes) >> 3 - 1;
                if (state == UNDEFINED) {
                    throw new IllegalStateException("No seed supplied!");
                } else if (state == SET_SEED) {
                    System.arraycopy(seed, HASH_OFFSET, copies, HASHCOPY_OFFSET,
                            EXTRAFRAME_OFFSET);
                    // possible cases for 64-byte frame:
                    //
                    // seed bytes < 48      - remaining bytes are enough for all, 8 counter bytes,
                    //                        0x80, and 8 seedLength bytes; no extra frame required
                    // 48 < seed bytes < 56 - remaining 9 bytes are for 0x80 and 8 counter bytes
                    //                        extra frame contains only seedLength value at the end
                    // seed bytes > 55      - extra frame contains both counter's bytes
                    //                        at the beginning and seedLength value at the end;
                    //                        note, that beginning extra bytes are not more than 8,
                    //                        that is, only 2 extra words may be used
                    // no need to set to "0" 3 words after "lastWord" and
                    // more than two words behind frame
                    for (i = lastWord + 3; i < FRAME_LENGTH + 2; i++) {
                        seed[i] = 0;
                    }
                    bits = (seedLength << 3) + 64; // transforming # of bytes into # of bits
                    // putting # of bits into two last words (14,15) of 16 word frame in
                    // seed or copies array depending on total length after padding
                    if (seed[BYTES_OFFSET] < MAX_BYTES) {
                        seed[14] = (int) (bits >>> 32);
                        seed[15] = (int) (bits);
                    } else {
                        copies[EXTRAFRAME_OFFSET + 14] = (int) (bits >>> 32);
                        copies[EXTRAFRAME_OFFSET + 15] = (int) (bits);
                    }
                    nextBIndex = HASHBYTES_TO_USE; // skipping remaining random bits
                }
                state = NEXT_BYTES;
                if (bytes.length == 0) {
                    return;
                }
                nextByteToReturn = 0;
                // possibly not all of HASHBYTES_TO_USE bytes were used previous time
                n = Math.min((HASHBYTES_TO_USE - nextBIndex), (bytes.length - nextByteToReturn));
                if (n > 0) {
                    System.arraycopy(nextBytes, nextBIndex, bytes, nextByteToReturn, n);
                    nextBIndex += n;
                    nextByteToReturn += n;
                }
                if (nextByteToReturn >= bytes.length) {
                    return; // return because "bytes[]" are filled in
                }
                n = seed[BYTES_OFFSET] & 0x03;
                do {
                    if (n == 0) {
                        seed[lastWord] = (int) (counter >>> 32);
                        seed[lastWord + 1] = (int) (counter);
                        seed[lastWord + 2] = END_FLAGS[0];
                    } else {
                        seed[lastWord] |= (int) ((counter >>> RIGHT1[n]) & MASK[n]);
                        seed[lastWord + 1] = (int) ((counter >>> RIGHT2[n]));
                        seed[lastWord + 2] = (int) ((counter << LEFT[n]) | END_FLAGS[n]);
                    }
                    if (seed[BYTES_OFFSET] > MAX_BYTES) {
                        copies[EXTRAFRAME_OFFSET] = seed[FRAME_LENGTH];
                        copies[EXTRAFRAME_OFFSET + 1] = seed[FRAME_LENGTH + 1];
                    }
                    computeHash(seed);
                    if (seed[BYTES_OFFSET] > MAX_BYTES) {
                        System.arraycopy(seed, 0, copies, FRAME_OFFSET, FRAME_LENGTH);
                        System.arraycopy(copies, EXTRAFRAME_OFFSET, seed, 0,
                                FRAME_LENGTH);
                        computeHash(seed);
                        System.arraycopy(copies, FRAME_OFFSET, seed, 0, FRAME_LENGTH);
                    }
                    counter++;
                    int j = 0;
                    for (i = 0; i < EXTRAFRAME_OFFSET; i++) {
                        int k = seed[HASH_OFFSET + i];
                        nextBytes[j] = (byte) (k >>> 24); // getting first  byte from left
                        nextBytes[j + 1] = (byte) (k >>> 16); // getting second byte from left
                        nextBytes[j + 2] = (byte) (k >>> 8); // getting third  byte from left
                        nextBytes[j + 3] = (byte) (k); // getting fourth byte from left
                        j += 4;
                    }
                    nextBIndex = 0;
                    j = Math.min(HASHBYTES_TO_USE, (bytes.length - nextByteToReturn));
                    if (j > 0) {
                        System.arraycopy(nextBytes, 0, bytes, nextByteToReturn, j);
                        nextByteToReturn += j;
                        nextBIndex += j;
                    }
                } while (nextByteToReturn < bytes.length);
            }

            /**
             * The method generates a 160 bit hash value using
             * a 512 bit message stored in first 16 words of int[] array argument and
             * current hash value stored in five words, beginning OFFSET+1, of the array argument.
             * Computation is done according to SHA-1 algorithm.
             * <p>
             * The resulting hash value replaces the previous hash value in the array;
             * original bits of the message are not preserved.
             * <p>
             * No checks on argument supplied, that is,
             * a calling method is responsible for such checks.
             * In case of incorrect array passed to the method
             * either NPE or IndexOutOfBoundException gets thrown by JVM.
             * <p>
             * only first (BYTES_OFFSET+6) words are used
             */
            private void computeHash(int[] arrW) {
                int a = arrW[HASH_OFFSET];
                int b = arrW[HASH_OFFSET + 1];
                int c = arrW[HASH_OFFSET + 2];
                int d = arrW[HASH_OFFSET + 3];
                int e = arrW[HASH_OFFSET + 4];
                int temp;
                // In this implementation the "d. For t = 0 to 79 do" loop
                // is split into four loops. The following constants:
                //     K = 5A827999   0 <= t <= 19
                //     K = 6ED9EBA1  20 <= t <= 39
                //     K = 8F1BBCDC  40 <= t <= 59
                //     K = CA62C1D6  60 <= t <= 79
                // are hex literals in the loops.
                for (int t = 16; t < 80; t++) {
                    temp = arrW[t - 3] ^ arrW[t - 8] ^ arrW[t - 14] ^ arrW[t - 16];
                    arrW[t] = (temp << 1) | (temp >>> 31);
                }
                for (int t = 0; t < 20; t++) {
                    temp = ((a << 5) | (a >>> 27)) +
                            ((b & c) | ((~b) & d)) +
                            (e + arrW[t] + 0x5A827999);
                    e = d;
                    d = c;
                    c = (b << 30) | (b >>> 2);
                    b = a;
                    a = temp;
                }
                for (int t = 20; t < 40; t++) {
                    temp = (((a << 5) | (a >>> 27))) + (b ^ c ^ d) + (e + arrW[t] + 0x6ED9EBA1);
                    e = d;
                    d = c;
                    c = (b << 30) | (b >>> 2);
                    b = a;
                    a = temp;
                }
                for (int t = 40; t < 60; t++) {
                    temp = ((a << 5) | (a >>> 27)) + ((b & c) | (b & d) | (c & d)) +
                            (e + arrW[t] + 0x8F1BBCDC);
                    e = d;
                    d = c;
                    c = (b << 30) | (b >>> 2);
                    b = a;
                    a = temp;
                }
                for (int t = 60; t < 80; t++) {
                    temp = (((a << 5) | (a >>> 27))) + (b ^ c ^ d) + (e + arrW[t] + 0xCA62C1D6);
                    e = d;
                    d = c;
                    c = (b << 30) | (b >>> 2);
                    b = a;
                    a = temp;
                }
                arrW[HASH_OFFSET] += a;
                arrW[HASH_OFFSET + 1] += b;
                arrW[HASH_OFFSET + 2] += c;
                arrW[HASH_OFFSET + 3] += d;
                arrW[HASH_OFFSET + 4] += e;
            }

            /**
             * The method appends new bytes to existing ones
             * within limit of a frame of 64 bytes (16 words).
             * <p>
             * Once a length of accumulated bytes reaches the limit
             * the "computeHash(int[])" method is invoked on the array to compute updated hash,
             * and the number of bytes in the frame is set to 0.
             * Thus, after appending all bytes, the array contain only those bytes
             * that were not used in computing final hash value yet.
             * <p>
             * No checks on arguments passed to the method, that is,
             * a calling method is responsible for such checks.
             * <p>
             * that is, for first byte "to"==0, for last byte "to"==input.length-1
             */
            private void updateHash(int[] intArray, byte[] byteInput, int toByte) {
                // As intArray contains a packed bytes
                // the buffer's index is in the intArray[BYTES_OFFSET] element
                int index = intArray[BYTES_OFFSET];
                int i = 0;
                int maxWord;
                int nBytes;
                int wordIndex = index >> 2;
                int byteIndex = index & 0x03;
                intArray[BYTES_OFFSET] = (index + toByte + 1) & 63;
                // In general case there are 3 stages :
                // - appending bytes to non-full word,
                // - writing 4 bytes into empty words,
                // - writing less than 4 bytes in last word
                if (byteIndex != 0) {       // appending bytes in non-full word (as if)
                    for (; (i <= toByte) && (byteIndex < 4); i++) {
                        intArray[wordIndex] |= (byteInput[i] & 0xFF) << ((3 - byteIndex) << 3);
                        byteIndex++;
                    }
                    if (byteIndex == 4) {
                        wordIndex++;
                        if (wordIndex == 16) {          // intArray is full, computing hash
                            computeHash(intArray);
                            wordIndex = 0;
                        }
                    }
                    if (i > toByte) {                 // all input bytes appended
                        return;
                    }
                }
                // writing full words
                maxWord = (toByte - i + 1) >> 2;           // # of remaining full words, may be "0"
                for (int k = 0; k < maxWord; k++) {
                    intArray[wordIndex] = (((int) byteInput[i] & 0xFF) << 24) |
                            (((int) byteInput[i + 1] & 0xFF) << 16) |
                            (((int) byteInput[i + 2] & 0xFF) << 8) |
                            (((int) byteInput[i + 3] & 0xFF));
                    i += 4;
                    wordIndex++;
                    if (wordIndex < 16) {     // buffer is not full yet
                        continue;
                    }
                    computeHash(intArray);      // buffer is full, computing hash
                    wordIndex = 0;
                }
                // writing last incomplete word
                // after writing free byte positions are set to "0"s
                nBytes = toByte - i + 1;
                if (nBytes != 0) {
                    int w = ((int) byteInput[i] & 0xFF) << 24;
                    if (nBytes != 1) {
                        w |= ((int) byteInput[i + 1] & 0xFF) << 16;
                        if (nBytes != 2) {
                            w |= ((int) byteInput[i + 2] & 0xFF) << 8;
                        }
                    }
                    intArray[wordIndex] = w;
                }
            }
        }
    }

    public static final class RSA {
        private RSA() {
        }

        private static final String RSA = "RSA";//加密类型
        private static final String ECB = "RSA/ECB/PKCS1PADDING";//加密算法
        private static final String SING = "MD5withRSA";//签名算法
        private static final int ENCRYPT = 245;//RSA最大加密明文大小
        private static final int DECRYPT = 256;//RSA最大解密密文大小
        private static final int SIZE = 2048;//密钥长度/范围：512~2048（值越大执行越慢）

        /**
         * 生成RSA密钥对
         * 密钥长度，范围：512～2048
         */
        public static KeyPair initKeyPair() {
            KeyPair keyPair = null;
            try {
                final KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(RSA);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    keyPairGen.initialize(SIZE, SecureRandom.getInstanceStrong());
                } else {
                    keyPairGen.initialize(SIZE, new SecureRandom());
                }
                keyPair = keyPairGen.generateKeyPair();
            } catch (Exception e) {
                Log.e("生成RSA密钥对异常", String.valueOf(e.getMessage()));
            }
            return keyPair;
        }

        /**
         * 用私钥对信息生成数字签名
         *
         * @param data       数据
         * @param privateKey 私钥(BASE64编码)
         */
        public static String sign(String data, String privateKey) {
            String sign = null;
            try {
                final byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
                final PrivateKey key = getPrivateKey(decode(privateKey));
                final byte[] signBytes = sign(dataBytes, key);
                sign = encode(signBytes);
            } catch (Exception e) {
                Log.e("数字签名异常", String.valueOf(e.getMessage()));
            }
            return sign;
        }

        /**
         * 校验数字签名
         *
         * @param data      数据
         * @param publicKey 公钥(BASE64编码)
         * @param sign      数字签名(BASE64编码)
         */
        public static boolean verify(String data, String publicKey, String sign) {
            boolean verify = false;
            try {
                final byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
                final PublicKey key = getPublicKey(decode(publicKey));
                final byte[] signBytes = decode(sign);
                final Signature signature = Signature.getInstance(SING);
                signature.initVerify(key);
                signature.update(dataBytes);
                verify = signature.verify(signBytes);
            } catch (Exception e) {
                Log.e("校验数字签名异常", String.valueOf(e.getMessage()));
            }
            return verify;
        }


        /**
         * 校验数字签名
         *
         * @param data      已加密数据
         * @param publicKey 公钥
         * @param signBytes 数字签名
         */
        private static boolean verify(byte[] data, PublicKey publicKey, byte[] signBytes) throws Exception {
            final Signature signature = Signature.getInstance(SING);
            signature.initVerify(publicKey);
            signature.update(data);
            return signature.verify(signBytes);
        }

        /**
         * 公钥加密
         *
         * @param data      源数据
         * @param publicKey 公钥(BASE64编码)
         * @return BASE64编码的加密数据
         */
        public static String encryptByPublicKey(String data, String publicKey) {
            String encryptData = null;
            try {
                final byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
                PublicKey key = getPublicKey(decode(publicKey));
                final byte[] encryptDataBytes = encryptByPublicKey(dataBytes, key);
                encryptData = encode(encryptDataBytes);
            } catch (Exception e) {
                Log.e("公钥加密异常", String.valueOf(e.getMessage()));
            }
            return encryptData;
        }

        /**
         * 私钥加密
         *
         * @param data       源数据
         * @param privateKey 私钥(BASE64编码)
         * @return BASE64编码的加密数据
         */
        public static String encryptByPrivateKey(String data, String privateKey) {
            String encryptData = null;
            try {
                final byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
                PrivateKey key = getPrivateKey(decode(privateKey));
                final byte[] encryptDataBytes = encryptByPrivateKey(dataBytes, key);
                encryptData = encode(encryptDataBytes);
            } catch (Exception e) {
                Log.e("私钥加密异常", String.valueOf(e.getMessage()));
            }
            return encryptData;
        }

        /**
         * 公钥解密
         *
         * @param encryptedData 私钥加密的数据(BASE64编码)
         * @param publicKey     公钥(BASE64编码)
         * @return 私钥加密前的数据
         */
        public static String decryptByPublicKey(String encryptedData, String publicKey) {
            String data = null;
            try {
                final byte[] dataBytes = decode(encryptedData);
                PublicKey key = getPublicKey(decode(publicKey));
                final byte[] decryptDataBytes = decryptByPublicKey(dataBytes, key);
                data = new String(decryptDataBytes, StandardCharsets.UTF_8);
            } catch (Exception e) {
                Log.e("公钥解密异常", String.valueOf(e.getMessage()));
            }
            return data;
        }

        /**
         * 私钥解密
         *
         * @param encryptedData 公钥加密的数据(BASE64编码)
         * @param privateKey    私钥(BASE64编码)
         * @return 公钥加密前的数据
         */
        public static String decryptByPrivateKey(String encryptedData, String privateKey) {
            String data = null;
            try {
                final byte[] dataBytes = decode(encryptedData);
                PrivateKey key = getPrivateKey(decode(privateKey));
                final byte[] decryptDataBytes = decryptByPrivateKey(dataBytes, key);
                data = new String(decryptDataBytes, StandardCharsets.UTF_8);
            } catch (Exception e) {
                Log.e("私钥解密异常", String.valueOf(e.getMessage()));
            }
            return data;
        }

        /**
         * 获取公钥
         */
        private static PublicKey getPublicKey(byte[] keyBytes) throws Exception {
            final X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
            final KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            return keyFactory.generatePublic(x509EncodedKeySpec);
        }

        /**
         * 获取私钥
         */
        private static PrivateKey getPrivateKey(byte[] keyBytes) throws Exception {
            final PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
            final KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            return keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        }

        /**
         * 用私钥对信息生成数字签名
         *
         * @param data       数据
         * @param privateKey 私钥
         */
        private static byte[] sign(byte[] data, PrivateKey privateKey) throws Exception {
            final Signature signature = Signature.getInstance(SING);
            signature.initSign(privateKey);
            signature.update(data);
            return signature.sign();
        }

        /**
         * 公钥加密
         *
         * @param data      源数据
         * @param publicKey 公钥
         */
        private static byte[] encryptByPublicKey(byte[] data, PublicKey publicKey) throws Exception {
            final Cipher cipher = Cipher.getInstance(ECB);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptedData;
            try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                int offSet = 0;
                byte[] cache;
                int i = 0;
                final int inputLen = data.length;
                // 对数据分段加密
                while (inputLen - offSet > 0) {
                    if (inputLen - offSet > ENCRYPT) {
                        cache = cipher.doFinal(data, offSet, ENCRYPT);
                    } else {
                        cache = cipher.doFinal(data, offSet, inputLen - offSet);
                    }
                    out.write(cache, 0, cache.length);
                    i++;
                    offSet = i * ENCRYPT;
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
            final Cipher cipher = Cipher.getInstance(ECB);
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            byte[] encryptedData;
            try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                int offSet = 0;
                byte[] cache;
                int i = 0;
                final int inputLen = data.length;
                // 对数据分段加密
                while (inputLen - offSet > 0) {
                    if (inputLen - offSet > ENCRYPT) {
                        cache = cipher.doFinal(data, offSet, ENCRYPT);
                    } else {
                        cache = cipher.doFinal(data, offSet, inputLen - offSet);
                    }
                    out.write(cache, 0, cache.length);
                    i++;
                    offSet = i * ENCRYPT;
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
            final Cipher cipher = Cipher.getInstance(ECB);
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            byte[] decryptedData;
            try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                int offSet = 0;
                byte[] cache;
                int i = 0;
                final int inputLen = encryptedData.length;
                // 对数据分段解密
                while (inputLen - offSet > 0) {
                    if (inputLen - offSet > DECRYPT) {
                        cache = cipher.doFinal(encryptedData, offSet, DECRYPT);
                    } else {
                        cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
                    }
                    out.write(cache, 0, cache.length);
                    i++;
                    offSet = i * DECRYPT;
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
            final Cipher cipher = Cipher.getInstance(ECB);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptedData;
            try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                int offSet = 0;
                byte[] cache;
                int i = 0;
                final int inputLen = encryptedData.length;
                // 对数据分段解密
                while (inputLen - offSet > 0) {
                    if (inputLen - offSet > DECRYPT) {
                        cache = cipher.doFinal(encryptedData, offSet, DECRYPT);
                    } else {
                        cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
                    }
                    out.write(cache, 0, cache.length);
                    i++;
                    offSet = i * DECRYPT;
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
            return Base64.encode(keyPair.getPublic().getEncoded());
        }

        /**
         * 获取私钥
         *
         * @return 私钥
         */
        public static String privateKey(KeyPair keyPair) {
            return Base64.encode(keyPair.getPrivate().getEncoded());
        }

        private static final class Base64 {

            private static final int BASELENGTH = 128;
            private static final int LOOKUPLENGTH = 64;
            private static final int TWENTYFOURBITGROUP = 24;
            private static final int EIGHTBIT = 8;
            private static final int SIXTEENBIT = 16;
            private static final int FOURBYTE = 4;
            private static final int SIGN = -128;
            private static char PAD = '=';
            private static byte[] base64Alphabet = new byte[BASELENGTH];
            private static char[] lookUpBase64Alphabet = new char[LOOKUPLENGTH];

            static {
                for (int i = 0; i < BASELENGTH; ++i) {
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
                return (octect >= BASELENGTH || base64Alphabet[octect] == -1);
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
                int lengthDataBits = binaryData.length * EIGHTBIT;
                if (lengthDataBits == 0) {
                    return "";
                }
                int fewerThan24bits = lengthDataBits % TWENTYFOURBITGROUP;
                int numberTriplets = lengthDataBits / TWENTYFOURBITGROUP;
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
                if (fewerThan24bits == EIGHTBIT) {
                    b1 = binaryData[dataIndex];
                    k = (byte) (b1 & 0x03);
                    byte val1 = ((b1 & SIGN) == 0) ? (byte) (b1 >> 2) : (byte) ((b1) >> 2 ^ 0xc0);
                    encodedData[encodedIndex++] = lookUpBase64Alphabet[val1];
                    encodedData[encodedIndex++] = lookUpBase64Alphabet[k << 4];
                    encodedData[encodedIndex++] = PAD;
                    //noinspection UnusedAssignment
                    encodedData[encodedIndex++] = PAD;
                } else if (fewerThan24bits == SIXTEENBIT) {
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
                if (len % FOURBYTE != 0) {
                    return null;// should be divisible by four
                }
                int numberQuadruple = (len / FOURBYTE);
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
    }

    public static final class Base64 {
        /**
         * 将图片转换成Base64编码
         *
         * @param imgPath 图片文件地址
         */
        public static String encode(String imgPath) {
            // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
            InputStream in = null;
            byte[] data = null;
            // 读取图片字节数组
            try {
                in = new FileInputStream(imgPath);
                data = new byte[in.available()];
                in.read(data);
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return android.util.Base64.encodeToString(data, DEFAULT);
        }

        /**
         * base64字符串转化成图片
         *
         * @param imgStr 图片base64字符串
         */
        public static byte[] decode(String imgStr) {
            // Base64解码
            byte[] b = android.util.Base64.decode(imgStr, DEFAULT);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }
            return b;
        }
    }

    public static final class SHA1 {

        public static String key(Context context) {
            try {
                @SuppressLint("PackageManagerGetSignatures")
                PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
                byte[] cert = info.signatures[0].toByteArray();
                MessageDigest md = MessageDigest.getInstance("SHA1");
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
            } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return null;
        }

    }

}