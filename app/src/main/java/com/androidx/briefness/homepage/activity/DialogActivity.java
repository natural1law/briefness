package com.androidx.briefness.homepage.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.androidx.briefness.R;
import com.androidx.briefness.base.BaseActivity;
import com.androidx.http.net.listener.Enqueue;
import com.androidx.reduce.tools.Idle;
import com.androidx.reduce.tools.This;
import com.androidx.reduce.tools.Toasts;
import com.androidx.view.dialog.DialogTools;
import com.androidx.view.dialog.adapter.CameraAdapter;
import com.androidx.view.scan.ScanActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.view.Gravity.BOTTOM;
import static com.androidx.briefness.base.App.toasts;
import static com.androidx.view.dialog.DialogTools.LayoutResId.CAMERA;
import static com.androidx.view.dialog.DialogTools.LayoutResId.RADIO;
import static com.androidx.view.dialog.DialogTools.LayoutResId.VERIFICATION_CODE;
import static com.androidx.view.scan.ScanActivity.REQUEST_CODE;

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
    Enqueue enqueue;

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
        enqueue.close();
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
    }

    @OnClick(R.id.activity_dialog)
    public void dialog() {
        DialogTools.builder(aThis)
                .setLayout(VERIFICATION_CODE)
                .setContentId(R.id.dialog_content)
                .setTitleText("验证码")
                .setAffirmText("提交")
                .setQuitColorId(R.color.irs)
                .setAffirmColorId(R.color.white)
                .setCanceled(false)
                .setCancelable(false)
                .setListener((DialogTools.OnClickCodeListener) (dialog, param, code) -> {
                    if (code == null) {
                        Toasts.builder(aThis).setMsg("请点击右方按钮获取验证码").showInfo();
                    } else if (param.equals("")) {
                        Toasts.builder(aThis).setMsg("请输入验证码").showWarning();
                    } else if (!param.equalsIgnoreCase(code)) {
                        Toasts.builder(aThis).setMsg("请输入正确的验证码").showError();
                    } else {
                        dialog.cancel();
                    }
                })
                .build()
                .show();
        Log.i("发送状态", String.valueOf(enqueue.send(0, "你好".getBytes())));
    }

    @OnClick(R.id.activity_dialog1)
    public void dialog1() {
        DialogTools.builder(aThis)
                .setLayout(RADIO)
                .setTitleText("分享")
                .setCanceled(false)
                .setCancelable(false)
                .setHintText1("请输入分享人的手机号")
                .setAffirmColorId(R.color.white)
                .setListener((DialogTools.OnClickRadioListener) (dialog, param, radio) -> Log.i("分享数据", param + " / " + radio))
                .build()
                .show();
    }

    @OnClick(R.id.activity_dialog2)
    public void dialog2() {
        DialogTools.builder(aThis)
                .setLayout(DialogTools.LayoutResId.INPUT_CHECK)
                .setStyle(R.style.dialogStyle)
                .setTitleText("添加烤房设备")
                .setHintText1("请输入烤房编号")
                .setHintText2("请输入烤房名称")
                .setHintText3("请输入验证码")
                .setHintText4("请输入详细地址（道路、街道、楼号）")
                .setAffirmText("提交")
                .setAffirmColorId(R.color.white)
                .setQuitColorId(R.color.black)
                .setQuitText("取消")
                .setCanceled(false)
                .setCancelable(false)
                .setListener(new DialogTools.OnClickQrListener() {

                    @Override
                    public void qr(AppCompatAutoCompleteTextView paramView) {
                        This.start(This.resultActivity(aThis, ScanActivity.class, REQUEST_CODE));
                    }

                    @Override
                    public void code(AppCompatTextView getCodeView) {
                        toasts.setMsg("获取验证码成功").showSuccess();
                    }

                    @Override
                    public void callbackValue(DialogTools dialog, String var1, String var2, String var3, String var4) {
                        if (var1.equals("")) toasts.setMsg("请输入设备编码").showWarning();
                        else if (var1.length() != 14) toasts.setMsg("请输入正确的设备编码").showWarning();
                        else if (var2.equals("")) toasts.setMsg("请输入设备名称").showWarning();
                        else if (var3.equals("")) toasts.setMsg("请输入详细地址").showWarning();
                        else if (var4.equals("")) toasts.setMsg("请输入手机验证码").showWarning();
                        else {
                            toasts.setMsg("提交成功").showSuccess();
                        }
                    }
                })
                .build()
                .show();
    }

    @OnClick(R.id.activity_dialog3)
    public void dialog3() {
        DialogTools.builder(aThis)
                .setLayout(CAMERA)
                .setQuitColorId(R.color.irs)
                .setCanceled(false)
                .setCancelable(false)
                .setDatas("相机", "录像")
                .setGravity(BOTTOM)
                .setListener((CameraAdapter.OnClickCameraAdapterListener) (position, dialog) -> {
                    toasts.setMsg(position).showSuccess();
                    dialog.cancel();
                })
                .build()
                .show();
    }

}
