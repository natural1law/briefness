package com.androidx.view.dialog.listener;

import com.androidx.view.dialog.DialogServlet;

public interface OnClickTriggerListener {

    void ok(DialogServlet dialog);

    default void no(DialogServlet dialog) {
        dialog.cancel();
    }
}
