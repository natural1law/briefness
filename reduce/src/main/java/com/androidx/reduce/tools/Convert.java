package com.androidx.reduce.tools;

/**
 * 转换工具
 */
public final class Convert {

    private Convert() {
    }

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
            byte b = Byte.parseByte(String.valueOf(value));
            return String.valueOf((b & 128) == 0 ? 0 : (b & 128) >> 7) +
                    ((b & 64) == 0 ? 0 : (b & 64) >> 6) +
                    ((b & 32) == 0 ? 0 : (b & 32) >> 5) +
                    ((b & 16) == 0 ? 0 : (b & 16) >> 4) +
                    ((b & 8) == 0 ? 0 : (b & 8) >> 3) +
                    ((b & 4) == 0 ? 0 : (b & 4) >> 2) +
                    ((b & 2) == 0 ? 0 : (b & 2) >> 1) +
                    (b & 1);
        }
    }

}
