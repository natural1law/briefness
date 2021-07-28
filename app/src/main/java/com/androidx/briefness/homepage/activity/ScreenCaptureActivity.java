package com.androidx.briefness.homepage.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.androidx.briefness.R;
import com.androidx.briefness.base.BaseActivity;
import com.androidx.reduce.tools.Idle;
import com.androidx.view.screen.NotificationBar;
import com.androidx.view.screen.ScreenRecording;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.androidx.briefness.base.App.toasts;

/**
 * @date 2021/07/22
 */
@SuppressLint("NonConstantResourceId")
public final class ScreenCaptureActivity extends BaseActivity {

    @BindView(R.id.title_layout)
    public FrameLayout titleLayout;
    @BindView(R.id.title_return_image)
    public AppCompatImageView imageView;
    @BindView(R.id.title_text)
    public AppCompatTextView titleView;

    private Unbinder unbinder;
    private ScreenRecording sr;
    private final AppCompatActivity aThis = this;

    @SuppressLint("SetTextI18n")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            initView();
        } catch (Exception e) {
            Log.e("截屏异常", Log.getStackTraceString(e));
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        sr.onDestroy();
    }

    @OnClick(R.id.title_return_image)
    public void imageReturn() {
        if (Idle.isClick()) finish();
    }

    private void initView() {
        setContentView(R.layout.activity_screen_capture);
        unbinder = ButterKnife.bind(aThis);
        titleLayout.setBackgroundColor(getResources().getColor(R.color.gray, getTheme()));
        titleView.setTextColor(getResources().getColor(R.color.black1, getTheme()));
        imageView.setVisibility(View.VISIBLE);
        imageView.setColorFilter(R.color.black);
        titleView.setText(getIntent().getStringExtra(getResources().getString(R.string.title)));
        sr = ScreenRecording.build(aThis).setNotification(NotificationBar.notification(aThis, "正在使用录屏丨截屏功能", false));
    }

    @OnClick(R.id.activity_dialog)
    public void dialog() {
        sr.onStartCapture((fileUrl, exists) -> {
            Log.i("截图地址", fileUrl);
            if (exists) toasts.setMsg("已将图片保存至文件管理中").showSuccess();
            else toasts.setMsg("图片保存失败").showError();
        });
    }

    @OnClick(R.id.activity_dialog1)
    public void dialog1() {
        sr.onStartRecording((fileUrl, exists) -> {
            if (exists) toasts.setMsg("已将视频保存至文件管理中").showSuccess();
            else toasts.setMsg("图片保存失败").showError();
            Log.i("视频地址", fileUrl);
        });
    }

    @OnClick(R.id.activity_dialog2)
    public void dialog2() {
        sr.onStopRecording();
    }

}
