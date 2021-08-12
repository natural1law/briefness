package com.androidx.view.dialog.listener;

import com.androidx.view.dialog.DialogServlet;

public interface OnClickCameraListener {

    void ok(int position, DialogServlet dialog);

    default void no(DialogServlet dialog) { dialog.cancel(); }
}
