package com.androidx.reduce.tools;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.widget.AppCompatEditText;

/**
 * 输入限制
 */
public final class Astrict {

    private Astrict() {
    }

    public static void isBuyPrice(AppCompatEditText text) {
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

    public static void isBuyCount(AppCompatEditText text) {
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

    public static boolean isShouldHideInput(View v, MotionEvent event) {
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

}
