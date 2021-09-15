package com.androidx.briefness.homepage.activity;

import static com.androidx.briefness.base.App.appThis;
import static com.androidx.briefness.base.App.toasts;
import static com.androidx.view.scan.ScanActivity.RESULT_KEY;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.androidx.briefness.R;
import com.androidx.briefness.base.BaseActivity;
import com.androidx.reduce.tools.Idle;
import com.androidx.view.dialog.DialogDefault;
import com.androidx.view.scan.ScanActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

@SuppressLint("NonConstantResourceId")
public final class DialogActivity extends BaseActivity {

    @BindView(R.id.title_layout)
    public FrameLayout titleLayout;
    @BindView(R.id.title_return_image)
    public AppCompatImageView imageView;
    @BindView(R.id.title_text)
    public AppCompatTextView titleView;

    private Unbinder unbinder;
    private final DialogActivity aThis = this;
    private ActivityResultLauncher<Intent> launcher;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        unbinder = ButterKnife.bind(aThis);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.title_return_image)
    public void imageReturn() {
        if (Idle.isClick()) finishAfterTransition();
    }

    @SuppressLint("SetTextI18n")
    private void initView() {
        titleLayout.setBackgroundColor(getResources().getColor(R.color.gray, getTheme()));
        titleView.setTextColor(getResources().getColor(R.color.black1, getTheme()));
        imageView.setVisibility(View.VISIBLE);
        imageView.setColorFilter(R.color.black);
        titleView.setText(getIntent().getStringExtra(getResources().getString(R.string.title)));

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getData() != null) {
                Log.i("回调码", String.valueOf(result.getResultCode()));
                Log.i("回调数据", String.valueOf(result.getData().getStringExtra(RESULT_KEY)));
            }
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
        appThis.resultActivity(aThis, ScanActivity.class, launcher).start();
    }

    @OnClick(R.id.activity_dialog3)
    public void dialog3() {
        String[] name = {"相机", "相册"};
        DialogDefault.camera(aThis, name, (position, dialog) -> {
            toasts.i("选择", position);
            toasts.setMsg(name[position]).showSuccess();
            dialog.cancel();
        });
    }

    @OnClick(R.id.activity_dialog5)
    public void dialog5() {
        String[] name = {"条目1", "条目2", "条目3", "条目4", "条目5"};
        DialogDefault.list(aThis, name, (position, dialog) -> {
            toasts.i("选择", position);
            toasts.setMsg(name[position]).showSuccess();
            dialog.cancel();
        });
    }

    @OnClick(R.id.activity_dialog6)
    public void dialog6() {
        DialogDefault.countDownTime(aThis, "正在加载", "还有", 60, "秒加载完成", () -> {
            toasts.setMsg("加载完成").showSuccess();
            toasts.i("正在加载", "加载完成");
        });
    }

}
