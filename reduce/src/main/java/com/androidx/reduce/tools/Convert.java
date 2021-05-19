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

}
