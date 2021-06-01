package com.androidx.reduce.tools;

import android.icu.math.BigDecimal;
import android.util.Log;

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
        public static String toHexEncoding(int color) {
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
    public static final class Date {

        private Date() {
        }

        /**
         * @param mss 毫秒
         */
        public static String formatDuring(long mss) {
            long days = mss / (1000 * 60 * 60 * 24);
            long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
            long seconds = (mss % (1000 * 60)) / 1000;
            return days + " 天 " + hours + " 时 " + minutes + " 分 " + seconds + " 秒 ";
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
                byte b = Byte.parseByte(Convert.Scm.build().to(value).sInt());
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

        public static Scm build() {
            synchronized (Scm.class) {
                return new Scm();
            }
        }

        public <T> Scm to(T value) {
            bd = new BigDecimal(String.valueOf(value)).setScale(0, BigDecimal.ROUND_HALF_UP);
            return this;
        }

        public String string() {
            return bd.unscaledValue().toString();
        }

        public String sInt() {
            return String.valueOf(bd.unscaledValue().intValue());
        }

        public int Int() {
            return bd.unscaledValue().intValue();
        }

        public String sDouble() {
            return String.valueOf(bd.unscaledValue().doubleValue());
        }

        public double Double() {
            return bd.unscaledValue().doubleValue();
        }

        public String sLong() {
            return String.valueOf(bd.unscaledValue().longValue());
        }

        public String sByte() {
            return String.valueOf(bd.unscaledValue().byteValue());
        }
    }

}
