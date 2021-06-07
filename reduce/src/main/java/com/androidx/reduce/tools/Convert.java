package com.androidx.reduce.tools;

import android.annotation.SuppressLint;
import android.icu.math.BigDecimal;
import android.util.Log;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * 转换工具
 */
public final class Convert {

    @Contract(pure = true)
    private Convert() {
    }

    /**
     * 颜色转换类
     */
    public static final class Color {

        @Contract(pure = true)
        private Color() {
        }

        /**
         * 16进制颜色值转换
         */
        public static @NotNull String toHexEncoding(int color) {
            String R, G, B;
            StringBuilder sb = new StringBuilder();
            R = Integer.toHexString(android.graphics.Color.red(color));
            G = Integer.toHexString(android.graphics.Color.green(color));
            B = Integer.toHexString(android.graphics.Color.blue(color));
            R = R.length() == 1 ? "0" + R : R;
            G = G.length() == 1 ? "0" + G : G;
            B = B.length() == 1 ? "0" + B : B;
            sb.append("#");
            sb.append(R.toUpperCase());
            sb.append(G.toUpperCase());
            sb.append(B.toUpperCase());
            return sb.toString();
        }

    }

    /**
     * 时间转换类
     */
    public static final class Timestamp {

        @Contract(pure = true)
        private Timestamp() {
        }

        /**
         * @param mss 毫秒
         */
        @Contract(pure = true)
        public static @NotNull String formatDuring(long mss) {
            long days = mss / (1000 * 60 * 60 * 24);
            long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
            long seconds = (mss % (1000 * 60)) / 1000;
            return days + " 天 " + hours + " 时 " + minutes + " 分 " + seconds + " 秒 ";
        }

        /**
         * 转换Date类型
         */
        @SuppressLint("SimpleDateFormat")
        public static Date toDate(String datetime, String format) {
            try {
                return new SimpleDateFormat(format).parse(datetime);
            } catch (ParseException e) {
                return null;
            }
        }

        /**
         * 将时间戳转成日期时间格式
         *
         * @param datetime 日期时间戳
         * @param format   现在的日期时间格式
         */
        @SuppressLint("SimpleDateFormat")
        public static <T> String refining(T datetime, String format) {
            try {
                return new SimpleDateFormat(format).format(datetime);
            } catch (Exception e) {
                return e.getMessage();
            }

        }

        /**
         * 日期时间格式类型转换(10时间戳位转换)
         *
         * @param datetime 日期时间戳
         * @param format   现在的日期时间格式
         */
        @SuppressLint("SimpleDateFormat")
        public static <T> String cover(T datetime, String format) {
            try {
                return new SimpleDateFormat(format).format(new Date(Long.parseLong(datetime + "000")));
            } catch (Exception e) {
                return e.getMessage();
            }

        }

        /**
         * 将时间转换为时间戳
         */
        @SuppressLint("SimpleDateFormat")
        public static <T> String changeover(T datetime, String format) {
            return String.valueOf(Objects.requireNonNull(toDate(String.valueOf(datetime), format)).getTime());
        }

        /**
         * 日期时间格式类型转换
         *
         * @param pastFormat 之前的日期时间格式
         * @param nowFormat  现在的日期时间格式
         */
        @SuppressLint("SimpleDateFormat")
        public static String refining(String datetime, String pastFormat, String nowFormat) {
            Date d1 = toDate(datetime, pastFormat);
            return (d1 == null) ? "" : new SimpleDateFormat(nowFormat).format(d1);
        }

    }

    /**
     * 进制转换类
     */
    public static final class Ary {

        @Contract(pure = true)
        private Ary() {
        }

        /**
         * 转二进制
         *
         * @param value 需要转换的值
         * @return 八位二进制
         */
        public strictfp static <T> @NotNull String toBinary(T value) {
            try {
                byte b = Byte.parseByte(Convert.Scm.build().set(value).tosInt());
                return String.valueOf((b & 128) == 0 ? 0 : (b & 128) >> 7) +
                        ((b & 64) == 0 ? 0 : (b & 64) >> 6) +
                        ((b & 32) == 0 ? 0 : (b & 32) >> 5) +
                        ((b & 16) == 0 ? 0 : (b & 16) >> 4) +
                        ((b & 8) == 0 ? 0 : (b & 8) >> 3) +
                        ((b & 4) == 0 ? 0 : (b & 4) >> 2) +
                        ((b & 2) == 0 ? 0 : (b & 2) >> 1) +
                        (b & 1);
            } catch (Exception e) {
                Log.e("转二进制异常", e.getMessage(), e);
                return "";
            }
        }
    }

    /**
     * 科学计算法转换
     * Scientific Computing Method
     */
    public static final class Scm {

        private BigDecimal bd;

        @Contract(pure = true)
        private Scm() {
        }

        @Contract(value = " -> new", pure = true)
        public static @NotNull Scm build() {
            synchronized (Scm.class) {
                return new Scm();
            }
        }

        @Contract("_ -> this")
        public <T> Scm set(T value) {
            bd = new BigDecimal(String.valueOf(value)).setScale(0, BigDecimal.ROUND_HALF_UP);
            return this;
        }

        public @NotNull String toString() {
            return bd.unscaledValue().toString();
        }

        public @NotNull String tosInt() {
            return String.valueOf(bd.unscaledValue().intValue());
        }

        public @NotNull String tosDouble() {
            return String.valueOf(bd.unscaledValue().doubleValue());
        }

        public @NotNull String tosLong() {
            return String.valueOf(bd.unscaledValue().longValue());
        }

        public @NotNull String tosByte() {
            return String.valueOf(bd.unscaledValue().byteValue());
        }

        public int toInt() {
            return bd.unscaledValue().intValue();
        }

        public long toLong() {
            return bd.unscaledValue().longValue();
        }

        public byte toByte() {
            return bd.unscaledValue().byteValue();
        }

        public double toDouble() {
            return bd.unscaledValue().doubleValue();
        }
    }

}
