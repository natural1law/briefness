package com.androidx.view.scan;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.androidx.view.R;
import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.callback.SelectCallback;
import com.huantansheng.easyphotos.models.album.entity.Photo;

import java.util.ArrayList;

import cn.bingoogolapple.qrcode.core.BarcodeType;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;

/**
 * @date 2021/04/13
 */
@SuppressWarnings({"unused", "RedundantSuppression"})
public final class ScanActivity extends AppCompatActivity implements QRCodeView.Delegate {

    public static final int RESULT_CODE = 99;//返回码
    public static final String RESULT_KEY = "QR";//返回值
    public static final String LAYOUT_UI = "ui";//加载布局

    private ZXingView zxView;
    private AppCompatImageView zxPhotoView;
    private AppCompatImageView zxReturn;
    private final ScanActivity aThis = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setFlags(FLAG_FULLSCREEN, FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(null);

        @LayoutRes
        int layout = getIntent().getIntExtra(LAYOUT_UI, 0);
        if (layout == 0) {
            setContentView(R.layout.activity_scan);
        } else {
            setContentView(layout);
        }
        zxView = findViewById(R.id.zx_view);
        zxReturn = findViewById(R.id.zx_return);
        zxPhotoView = findViewById(R.id.zx_photo);
        zxView.setDelegate(this);
        zxView.changeToScanQRCodeStyle(); // 切换成扫描二维码样式
        zxView.setType(BarcodeType.ALL, null); // 识别所有类型的码
        zxReturn.setOnClickListener(v -> finish());
        photoView();
    }

    @Override
    public void onStart() {
        super.onStart();
        zxView.startCamera(); // 打开后置摄像头开始预览，但是并未开始识别
        zxView.startSpotAndShowRect(); // 显示扫描框，并开始识别
    }

    @Override
    public void onStop() {
        zxView.stopCamera(); // 关闭摄像头预览，并且隐藏扫描框
        super.onStop();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            zxReturn.performClick();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public void onDestroy() {
        zxView.onDestroy(); // 销毁二维码扫描控件
        super.onDestroy();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            vibrator.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_TICK));
        } else {
            vibrator.vibrate(500);
        }
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        try {
            vibrate();
            if (result != null) setResult(RESULT_CODE, new Intent().putExtra(RESULT_KEY, result));
        } catch (Exception e) {
            Log.e("扫码异常", String.valueOf(e.getMessage()), e);
        }
        finish();
    }

    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {
        // 这里是通过修改提示文案来展示环境是否过暗的状态，接入方也可以根据 isDark 的值来实现其他交互效果
        String tipText = zxView.getScanBoxView().getTipText();
        String ambientBrightnessTip = "\n环境过暗，请打开闪光灯";
        if (isDark) {
            if (!tipText.contains(ambientBrightnessTip)) {
                zxView.openFlashlight();
                zxView.getScanBoxView().setTipText(tipText + ambientBrightnessTip);
            }
        } else {
            if (tipText.contains(ambientBrightnessTip)) {
                tipText = tipText.substring(0, tipText.indexOf(ambientBrightnessTip));
                zxView.getScanBoxView().setTipText(tipText);
            }
        }
    }

    @Override
    public void onScanQRCodeOpenCameraError() {

    }

    private void photoView() {
        zxPhotoView.setOnClickListener(v -> EasyPhotos.createAlbum(aThis, false, GlideEngine.getInstance())
                .setCleanMenu(true)
                .setGif(false)
                .isCompress(false)
                .setCount(1)
                .setOriginalMenu(false, true, "原图不可用")
                .start(new SelectCallback() {
                    @Override
                    public void onResult(ArrayList<Photo> photos, ArrayList<String> paths, boolean isOriginal) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            paths.forEach(photo -> {
                                if (photo.contains("content:")) {
                                    zxView.decodeQRCode(realUri(Uri.parse(photo)));
                                } else {
                                    zxView.decodeQRCode(photo);
                                }
                            });
                        } else {
                            for (Photo photo : photos) {
                                if (photo.path.contains("content:")) {
                                    zxView.decodeQRCode(realUri(Uri.parse(photo.path)));
                                } else {
                                    zxView.decodeQRCode(photo.path);
                                }
                            }
                        }
                    }
                }));
    }

    /**
     * 转换真实路径
     */
    private String realUri(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] pro = {MediaStore.Images.Media.DATA};
            cursor = aThis.getContentResolver().query(contentUri, pro, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

}
