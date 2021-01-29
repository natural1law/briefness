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
}
