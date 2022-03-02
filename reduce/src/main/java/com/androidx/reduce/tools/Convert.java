package com.androidx.reduce.tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.icu.math.BigDecimal;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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

        public static final String DATE_FORMAT1 = "yyyy-MM-dd hh:mm:ss";
        public static final String DATE_FORMAT2 = "yyyy/MM/dd hh:mm:ss";
        public static final String DATE_FORMAT3 = "yyyy年MM月dd日 hh时mm分ss秒";
        public static final String DATE_FORMAT4 = "yyyy年MM月dd日 hh时mm分";
        public static final String DATE_FORMAT5 = "yyyy年MM月dd日";
        public static final String DATE_FORMAT6 = "yyyy-MM-dd hh:mm";
        public static final String DATE_FORMAT7 = "yyyy/MM/dd hh:mm";
        public static final String DATE_FORMAT8 = "MM/dd hh:mm:ss";
        public static final String DATE_FORMAT9 = "MM-dd hh:mm:ss";
        public static final String DATE_FORMAT10 = "yyyy-MM-dd HH:mm:ss";
        public static final String DATE_FORMAT11 = "yyyy/MM/dd HH:mm:ss";
        public static final String DATE_FORMAT12 = "yyyy年MM月dd日 HH时mm分ss秒";
        public static final String DATE_FORMAT13 = "yyyy年MM月dd日 HH时mm分";
        public static final String DATE_FORMAT14 = "yyyy-MM-dd HH:mm";
        public static final String DATE_FORMAT15 = "yyyy/MM/dd HH:mm";
        public static final String DATE_FORMAT16 = "MM/dd HH:mm:ss";
        public static final String DATE_FORMAT17 = "MM-dd HH:mm:ss";
        public static final String DATE_FORMAT18 = "HH:mm:ss";
        public static final String DATE_FORMAT19 = "yyyy/MM/dd";
        public static final String DATE_FORMAT20 = "yyyy-MM-dd";
        public static final String DATE_FORMAT21 = "yyyy,MM,dd,HH,mm,ss";

        private Timestamp() {
        }

        /**
         * 获取当前日期时间
         *
         * @param format 时间格式
         */
        @SuppressLint("SimpleDateFormat")
        @RequiresApi(api = Build.VERSION_CODES.O)
        public static String getCurrentDatetime(String... format) {
            return dispose(LocalDateTime.now(), format[0]);
        }

        @SuppressLint("SimpleDateFormat")
        private static <Timestamp> String dispose(Timestamp timestamp, String format) {
            try {
                Date date;
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                if (timestamp instanceof Long) {
                    date = sdf.parse(sdf.format(timestamp));
                } else if (timestamp instanceof String) {
                    date = sdf.parse(sdf.format(timestamp));
                } else {
                    date = new Date();
                }
                assert date != null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    LocalDateTime datetime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
                    return DateTimeFormatter.ofPattern(format).format(datetime);
                } else {
                    return new SimpleDateFormat(format).format(date);
                }
            } catch (Exception e) {
                return Log.getStackTraceString(e);
            }
        }

        /**
         * 转换Date类型
         *
         * @param timestamp 格式化日期时间(1970-01-01 00:00:00)
         * @param format    日期格式
         * @param <S>       String类型
         * @return 返回date类型
         */
        @SuppressLint("SimpleDateFormat")
        public static <S extends String> Date toDate(S timestamp, String format) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                return sdf.parse(String.valueOf(timestamp));
            } catch (ParseException e) {
                return new Date();
            }
        }

        /**
         * 时间戳转时间
         */
        public static <Timestamp> String getTime(Timestamp timestamp) {
            return dispose(timestamp, DATE_FORMAT18);
        }

        /**
         * 获取斜杠格式日期
         */
        public static <Timestamp> String getDateOblique(Timestamp timestamp) {
            return dispose(timestamp, DATE_FORMAT19);
        }

        /**
         * 获取正短格式日期
         */
        public static <Timestamp> String getDateStraight(Timestamp timestamp) {
            return dispose(timestamp, DATE_FORMAT20);
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
                Log.e("日期转换异常", Log.getStackTraceString(e));
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
        @SuppressWarnings("StringBufferMayBeStringBuilder")
        public static <T> String toBinary(T value) {
            try {
                StringBuffer sb = new StringBuffer();
                int b = Convert.Scm.set(value).intValue();
                int b1 = (b & 128) == 0 ? 0 : (b & 128) >>> 7;
                int b2 = (b & 64) == 0 ? 0 : (b & 64) >>> 6;
                int b3 = (b & 32) == 0 ? 0 : (b & 32) >>> 5;
                int b4 = (b & 16) == 0 ? 0 : (b & 16) >>> 4;
                int b5 = (b & 8) == 0 ? 0 : (b & 8) >>> 3;
                int b6 = (b & 4) == 0 ? 0 : (b & 4) >>> 2;
                int b7 = (b & 2) == 0 ? 0 : (b & 2) >>> 1;
                int b8 = (b & 1);
                sb.append(b1).append(b2).append(b3).append(b4)
                        .append(b5).append(b6).append(b7).append(b8);
                return sb.toString();
            } catch (Exception e) {
                Log.e("转二进制异常", Log.getStackTraceString(e));
                return "00000000";
            }
        }
    }

    /**
     * 科学计算法转换
     * Scientific Computing Method
     */
    public static final class Scm {

        private Scm() {
        }

        public static <V> BigDecimal set(V value) {
            return set(value, 0);
        }

        public static <V> BigDecimal set(V value, int scale) {
            return set(value, scale, BigDecimal.ROUND_HALF_UP);
        }

        public static <V> BigDecimal set(V value, int scale, int round) {
            return new BigDecimal(String.valueOf(value)).setScale(scale, round);
        }

    }

    public static final class Pixel {

        private final DisplayMetrics dm = new DisplayMetrics();

        private Pixel(Context context) {
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            windowManager.getDefaultDisplay().getRealMetrics(dm);
        }

        public static Pixel get(Context context) {
            return new Pixel(context);
        }

        /**
         * dp值
         */
        public int dp(double px) {
            return (int) (px * dm.density);
        }

        /**
         * px值
         */
        public int px(double dp) {
            return (int) (dp / dm.density);
        }

        /**
         * sp值
         */
        public int sp(double px) {
            return (int) (px * dm.scaledDensity);
        }

    }
}
