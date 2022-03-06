package com.androidx.briefness.homepage.activity;

import static com.androidx.briefness.base.App.toasts;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.FrameLayout;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.androidx.briefness.R;
import com.androidx.briefness.base.BaseActivity;
import com.androidx.reduce.tools.Idle;
import com.androidx.reduce.tools.Toasts;
import com.androidx.view.dialog.DialogDefault;
import com.androidx.view.scan.ScanTools;

import butterknife.BindView;
import butterknife.OnClick;

@SuppressLint("NonConstantResourceId")
public final class DialogActivity extends BaseActivity {

    private final DialogActivity aThis = this;
    @BindView(R.id.title_layout)
    public FrameLayout titleLayout;
    @BindView(R.id.title_return_image)
    public AppCompatImageView imageView;
    @BindView(R.id.title_text)
    public AppCompatTextView titleView;

    @Override
    protected int layoutId() {
        return R.layout.activity_dialog;
    }

    @Override
    protected void onCreate() {
        initView();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            imageView.performClick();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @OnClick(R.id.title_return_image)
    public void imageReturn() {
        if (Idle.isClick()) finishAfterTransition();
    }

    @SuppressLint("SetTextI18n")
    private void initView() {
        setTitle(titleLayout, imageView, titleView);

        ScanTools.callback(aThis, (resultCode, data) -> {
            Log.i("回调码", String.valueOf(resultCode));
            Log.i("回调数据", data);
        });

    }

    @OnClick(R.id.activity_dialog)
    public void dialog() {
        DialogDefault.alert(aThis, "标题", "内容", Dialog::cancel);
    }

    @OnClick(R.id.activity_dialog1)
    public void dialog1() {
        DialogDefault.console(aThis, "您要退出登录吗？", dialog -> {
            toasts.setMsg("确认").showSuccess();
            dialog.cancel();
        });
    }

    @OnClick(R.id.activity_dialog2)
    public void dialog2() {
        ScanTools.start();
    }

    @OnClick(R.id.activity_dialog3)
    public void dialog3() {
        DialogDefault.camera(aThis, photos -> {
            Toasts.i("Uri", photos.get(0).uri);
            Toasts.i("Path", photos.get(0).path);
        });
    }

    @OnClick(R.id.activity_dialog5)
    public void dialog5() {
        String[] name = {"条目1", "条目2", "条目3", "条目4", "条目5"};
        DialogDefault.list(aThis, name, (position, dialog) -> {
            Toasts.i("选择", position);
            toasts.setMsg(name[position]).showSuccess();
            dialog.cancel();
        });
    }

    @OnClick(R.id.activity_dialog6)
    public void dialog6() {
        DialogDefault.countDownTime(aThis, "正在加载", "还有", 60, "秒加载完成", () -> {
            toasts.setMsg("加载完成").showSuccess();
            Toasts.i("正在加载", "加载完成");
        });
    }

}
