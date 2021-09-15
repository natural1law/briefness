package com.androidx.view.dialog;

import static android.view.Gravity.BOTTOM;
import static android.view.Gravity.CENTER;

import android.content.Context;
import android.os.CountDownTimer;

import androidx.annotation.ColorRes;
import androidx.annotation.Size;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidx.animation.view.ProgressTextView;
import com.androidx.reduce.tools.Convert;
import com.androidx.view.R;
import com.androidx.view.dialog.adapter.CameraAdapter;
import com.androidx.view.dialog.adapter.ListAdapter;
import com.androidx.view.dialog.listener.OnClickCameraListener;
import com.androidx.view.dialog.listener.OnClickCountDownTimeListener;
import com.androidx.view.dialog.listener.OnClickTriggerListener;

public final class DialogDefault {

    /**
     * 倒计时
     *
     * @param content   显示内容
     * @param prefix    提示前缀
     * @param totalTime 计时时间
     * @param suffix    提示后最
     * @param listener  完成
     */
    public static void countDownTime(Context context, String content, String prefix, long totalTime, String suffix, OnClickCountDownTimeListener listener) {
        int count = 1000;
        DialogServlet dialog = DialogCall.builder()
                .setLayoutView(R.layout.dialog_count_down_time)
                .setLayoutViewId(R.id.dialog_frame)
                .setCanceled(false)
                .setCancelable(false)
                .setLayoutGravity(CENTER)
                .build()
                .get(context)
                .setTextView(R.id.dialog_content, content);
        ProgressTextView view = dialog.getView(R.id.dialog_timing_animation);
        CountDownTimer countDownTimer = new CountDownTimer(totalTime * count, count) {
            @Override
            public void onTick(long m) {
                if (view != null){
                    view.setText(prefix + ((m / count) + 1) + suffix);
                }

            }

            @Override
            public void onFinish() {
                cancel();
                dialog.cancel();
                listener.ok();
            }
        };
        countDownTimer.start();
        dialog.show();
    }

    /**
     * 相机选择器
     *
     * @param datas    按钮名称
     * @param listener 完成
     */
    public static void camera(Context context, String[] datas, OnClickCameraListener listener) {
        camera(context, datas, R.color.cameraText, 20, listener);
    }

    /**
     * 相机选择器
     *
     * @param datas        按钮名称
     * @param contentColor 按钮颜色
     * @param contentSize  按钮大小
     * @param listener     完成
     */
    public static void camera(Context context, String[] datas, @ColorRes int contentColor, @Size int contentSize, OnClickCameraListener listener) {
        DialogServlet dialog = DialogCall.builder()
                .setLayoutView(R.layout.dialog_camera)
                .setLayoutViewId(R.id.dialog_frame)
                .setCanceled(false)
                .setCancelable(false)
                .setLayoutGravity(BOTTOM)
                .build()
                .get(context)
                .setTextView(R.id.camera_cancel, "取消")
                .setOnClickListener(R.id.camera_cancel, listener::no);
        RecyclerView rv = dialog.getView(R.id.camera_rv);
        CameraAdapter adapter = new CameraAdapter();
        rv.setLayoutManager(new LinearLayoutManager(context));
        rv.setAdapter(adapter);
        adapter.setDt(dialog);
        adapter.setData(datas);
        adapter.setTextColor(contentColor);
        adapter.setTextSize(contentSize);
        adapter.setListener(listener);
        dialog.show();
    }

    /**
     * 列表选择器
     *
     * @param datas    列表名称
     * @param listener 完成
     */
    public static void list(Context context, String[] datas, OnClickCameraListener listener) {
        list(context, datas, R.color.cameraText, 20, listener);
    }

    /**
     * 列表选择器
     *
     * @param datas        列表名称
     * @param contentColor 列表颜色
     * @param contentSize  列表大小
     * @param listener     完成
     */
    public static void list(Context context, String[] datas, @ColorRes int contentColor, @Size int contentSize, OnClickCameraListener listener) {
        DialogServlet dialog = DialogCall.builder()
                .setLayoutView(R.layout.dialog_list)
                .setLayoutViewId(R.id.dialog_frame)
                .setCanceled(false)
                .setCancelable(false)
                .setLayoutGravity(BOTTOM)
                .build()
                .get(context);
        RecyclerView rv = dialog.getView(R.id.dialog_frame);
        ListAdapter adapter = new ListAdapter();
        rv.setLayoutManager(new LinearLayoutManager(context));
        rv.setAdapter(adapter);
        adapter.setDt(dialog);
        adapter.setData(datas);
        adapter.setTextColor(contentColor);
        adapter.setTextSize(contentSize);
        adapter.setListener(listener);
        dialog.show();
    }

    /**
     * 提示
     *
     * @param title   标题
     * @param content 内容
     */
    public static void alert(Context context, String title, String content, OnClickTriggerListener listener) {
        DialogCall.builder()
                .setLayoutView(R.layout.dialog_alert)
                .setLayoutViewId(R.id.dialog_frame)
                .setCanceled(false)
                .setCancelable(false)
                .setLayoutGravity(CENTER)
                .build()
                .get(context)
                .setTextView(R.id.dialog_title, title)
                .setTextView(R.id.dialog_content, content)
                .setOnClickListener(R.id.dialog_affirm, listener)
                .show();
    }

    /**
     * 提醒
     *
     * @param content  内容
     * @param listener 确认取消
     */
    public static void console(Context context, String content, OnClickTriggerListener listener) {
        DialogServlet dialog = DialogCall.builder()
                .setLayoutView(R.layout.dialog_console)
                .setLayoutViewId(R.id.dialog_frame)
                .setCanceled(false)
                .setCancelable(false)
                .setLayoutGravity(CENTER)
                .build()
                .get(context)
                .setTextView(R.id.dialog_content, content)
                .setOnClickListener(R.id.dialog_affirm, listener)
                .setOnClickListener(R.id.dialog_quit, listener::no);
        AppCompatTextView contentView = dialog.getView(R.id.dialog_content);
        contentView.setHeight(Convert.Pixel.get(context).dp(150));
        dialog.show();

    }

}
