package com.androidx.reduce.tools;

import android.annotation.SuppressLint;
import android.icu.math.BigDecimal;
import android.util.Log;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 转换工具
 */
public final class Convert {

    private Convert() {
    }

    /**
     * 颜色转换类
     */
    public static final class Color {

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

        public static final String SECOND = "ss";
        public static final String MINUTE = "mm:ss";
        public static final String HOUR = "hh:mm:ss";
        public static final String DATE_SPRIT = "yyyy/MM/dd";
        public static final String DATE_WHIPPLETREE = "yyyy-MM-dd";
        public static final String DATE_FORMAT1 = "yyyy-MM-dd hh:mm:ss";
        public static final String DATE_FORMAT2 = "yyyy/MM/dd hh:mm:ss";
        public static final String DATE_FORMAT3 = "yyyy年MM月dd日 hh时mm分ss秒";
        public static final String DATE_FORMAT4 = "yyyy年MM月dd日 hh时mm分";
        public static final String DATE_FORMAT5 = "yyyy年MM月dd日";
        public static final String DATE_FORMAT6 = "yyyy-MM-dd hh:mm";
        public static final String DATE_FORMAT7 = "yyyy/MM/dd hh:mm";
        public static final String DATE_FORMAT8 = "MM/dd hh:mm:ss";
        public static final String DATE_FORMAT9 = "MM-dd hh:mm:ss";

        private Timestamp() {
        }

        /**
         * @param mss 毫秒
         */
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
        public static <D>Date toDate(D datetime, String format) {
            try {
                return new SimpleDateFormat(format).parse(String.valueOf(datetime));
            } catch (ParseException e) {
                return new Date();
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
                return new SimpleDateFormat(format).format(Long.parseLong(datetime.toString()));
            } catch (Exception e) {
                Log.e("日期转换异常", e.getMessage(), e);
                return new SimpleDateFormat(format).format(datetime);
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
            return String.valueOf(toDate(String.valueOf(datetime), format).getTime());
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

        private Ary() {
        }

        /**
         * 转二进制
         *
         * @param value 需要转换的值
         * @return 八位二进制
         */
        public strictfp static <T> String toBinary(T value) {
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

        private Scm() {
        }

        @Contract(value = " -> new", pure = true)
        public static @NotNull Scm build() {
            synchronized (Scm.class) {
                return new Scm();
            }
        }

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
