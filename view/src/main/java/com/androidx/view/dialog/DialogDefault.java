package com.androidx.view.dialog;

import static android.view.Gravity.BOTTOM;
import static android.view.Gravity.CENTER;

import android.app.Activity;
import android.content.Context;
import android.os.CountDownTimer;
import android.view.View;

import androidx.annotation.ColorRes;
import androidx.annotation.IntRange;
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
import com.androidx.view.dialog.listener.OnResultPhotoListener;
import com.androidx.view.scan.GlideEngine;
import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.callback.SelectCallback;
import com.huantansheng.easyphotos.models.album.entity.Photo;

import java.util.ArrayList;

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
                .get(context);
        dialog.setText(R.id.dialog_content, content);
        ProgressTextView view = dialog.getView(R.id.dialog_timing_animation);
        CountDownTimer countDownTimer = new CountDownTimer(totalTime * count, count) {
            @Override
            public void onTick(long m) {
                if (view != null) {
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
     */
    public static void camera(Context context, OnResultPhotoListener pathListener) {
        camera(context, R.color.cameraFontColor, 20, 1, pathListener);
    }

    /**
     * 相机选择器
     *
     * @param contentColor 按钮颜色
     * @param contentSize  按钮大小
     */
    public static void camera(Context context, @ColorRes int contentColor
            , @IntRange(from = 6, to = 36) int contentSize, @IntRange(from = 1, to = 9) int maxCount
            , OnResultPhotoListener pathListener) {
        DialogServlet ds = DialogCall.builder()
                .setLayoutView(R.layout.dialog_camera)
                .setLayoutViewId(R.id.dialog_frame)
                .setCanceled(false)
                .setCancelable(false)
                .setLayoutGravity(BOTTOM)
                .build()
                .get(context);
        CameraAdapter adapter = new CameraAdapter();
        RecyclerView rv = ds.getView(R.id.camera_rv);
        rv.setHasFixedSize(true);
        rv.setItemViewCacheSize(5);
        rv.setAdapter(adapter);
        rv.setDrawingCacheEnabled(true);
        LinearLayoutManager manager;
        rv.setLayoutManager(manager = new LinearLayoutManager(context));
        rv.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        manager.setRecycleChildrenOnDetach(true);
        adapter.setDt(ds);
        adapter.setData("相机", "相册");
        adapter.setTextColor(context.getResources().getColor(contentColor, context.getTheme()));
        adapter.setTextSize(contentSize);
        adapter.setListener((position, dialog) -> {
            switch (position + 1) {
                case 1:
                    EasyPhotos.createCamera((Activity) context, false)
                            .setFileProviderAuthority("com.androidx.photo.fileProvider")
                            .start(new SelectCallback() {
                                @Override
                                public void onResult(ArrayList<Photo> photos, boolean isOriginal) {
                                    if (pathListener != null) pathListener.onPhoto(photos);
                                }

                                @Override
                                public void onCancel() {
                                    if (pathListener != null) pathListener.cancel();
                                }
                            });
                    break;
                case 2:
                    EasyPhotos.createAlbum((Activity) context, true, false, GlideEngine.getInstance())
                            .setFileProviderAuthority("com.androidx.photo.fileProvider")
                            .setCount(maxCount)
                            .setVideo(true)
                            .setGif(true)
                            .setMinWidth(48)
                            .setMinHeight(48)
                            .setOriginalMenu(false, true, "原图不可用")
                            .start(new SelectCallback() {
                                @Override
                                public void onResult(ArrayList<Photo> photos, boolean isOriginal) {
                                    if (pathListener != null) pathListener.onPhoto(photos);
                                }

                                @Override
                                public void onCancel() {
                                    if (pathListener != null) pathListener.cancel();
                                }
                            });
                default:
                    break;
            }
            dialog.cancel();
        });
        ds.setText(R.id.camera_cancel, "取消");
        ds.setOnClickListener(R.id.camera_cancel, d -> {
            adapter.onViewRecycled(adapter.getHolder());
            d.cancel();
        });
        ds.show();
    }

    /**
     * 列表选择器
     *
     * @param datas    列表名称
     * @param listener 完成
     */
    public static void list(Context context, String[] datas, OnClickCameraListener listener) {
        list(context, datas, R.color.cameraFontColor, 20, listener);
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
                .setText(R.id.dialog_title, title)
                .setText(R.id.dialog_content, content)
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
                .setText(R.id.dialog_content, content)
                .setOnClickListener(R.id.dialog_affirm, listener)
                .setOnClickListener(R.id.dialog_quit, listener::no);
        AppCompatTextView contentView = dialog.getView(R.id.dialog_content);
        contentView.setHeight(Convert.Pixel.get(context).dp(150));
        dialog.show();

    }

}
