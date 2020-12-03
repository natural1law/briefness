package com.androidx.reduce.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public final class Astrict {

    private Astrict() {
    }

    private static volatile Astrict instance = SingletonHolder.INSTANCE;

    private static final class SingletonHolder {
        private static final Astrict INSTANCE = new Astrict();
    }

    public static Astrict get() {
        return instance;
    }

    public void isBuyPrice(AppCompatEditText text) {
        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String temp = editable.toString();
                int posDot = temp.indexOf(".");
                if (temp.startsWith(".") || temp.startsWith("00") || temp.startsWith("01") || temp.startsWith("02") || temp.startsWith("03")
                        || temp.startsWith("04") || temp.startsWith("05") || temp.startsWith("06") || temp.startsWith("07")
                        || temp.startsWith("08") || temp.startsWith("09")) {
                    editable.delete(0, 1);
                    return;
                }
                if (posDot < 0) {
                    return;
                }
                if (temp.length() - posDot - 1 > 2) {
                    editable.delete(posDot + 3, posDot + 4);
                }

            }
        });
    }

    public void isBuyCount(AppCompatEditText text) {
        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String temp = editable.toString();
                if (temp.startsWith("00") || temp.startsWith("01") || temp.startsWith("02") || temp.startsWith("03")
                        || temp.startsWith("04") || temp.startsWith("05") || temp.startsWith("06") || temp.startsWith("07")
                        || temp.startsWith("08") || temp.startsWith("09")) {
                    editable.delete(0, 1);
                }
            }
        });
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if ((v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            return !(event.getX() > left) || !(event.getX() < right)
                    || !(event.getY() > top) || !(event.getY() < bottom);
        }
        return false;
    }

    public boolean isPermission(AppCompatActivity aThis) {
        if ((ContextCompat.checkSelfPermission(aThis, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                && (ContextCompat.checkSelfPermission(aThis, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)
                && (ContextCompat.checkSelfPermission(aThis, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            // 检查该权限是否已经获取
            String camera = Manifest.permission.CAMERA;
            String recordAudio = Manifest.permission.RECORD_AUDIO;
            String storage = Manifest.permission.WRITE_EXTERNAL_STORAGE;
            ActivityCompat.requestPermissions(aThis, new String[]{camera, recordAudio, storage}, 888);
            return false;
        } else {
            return true;
        }
    }

    public int vector(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        double minWidth = Math.floor(metrics.widthPixels / metrics.density);
        int i;
        int[] dp = {320, 360, 384, 392, 400, 410, 411, 428, 432, 480, 533, 592, 600, 640, 662, 720, 768, 800};
        if (minWidth == dp[0]) {
            i = -258;
            return i;
        } else if (minWidth == dp[1]) {
            i = -230;
            return i;
        } else if (minWidth == dp[2]) {
            i = -212;
            return i;
        } else if (minWidth == dp[3]) {
            i = -180;
            return i;
        } else if (minWidth == dp[4]) {
            i = -205;
            return i;
        } else if (minWidth == dp[5]) {
            i = -200;
            return i;
        } else if (minWidth == dp[6]) {
            i = -170;
            return i;
        } else if (minWidth == dp[7]) {
            i = -195;
            return i;
        } else if (minWidth == dp[8]) {
            i = -188;
            return i;
        } else if (minWidth == dp[9]) {
            i = -171;
            return i;
        } else if (minWidth == dp[10]) {
            i = -155;
            return i;
        } else if (minWidth == dp[11]) {
            i = -155;
            return i;
        } else if (minWidth == dp[12]) {
            i = -138;
            return i;
        } else if (minWidth == dp[13]) {
            i = -129;
            return i;
        } else if (minWidth == dp[14]) {
            i = -122;
            return i;
        } else if (minWidth == dp[15]) {
            i = -116;
            return i;
        } else if (minWidth == dp[16]) {
            i = -110;
            return i;
        } else if (minWidth == dp[17]) {
            i = -104;
            return i;
        } else {
            return 0;
        }
    }

}
