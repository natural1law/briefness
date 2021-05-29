package com.androidx.briefness.homepage.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
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
import com.androidx.reduce.tools.Captcha;
import com.androidx.reduce.tools.Idle;
import com.androidx.reduce.tools.Toasts;
import com.androidx.view.dialog.DialogTools;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.androidx.reduce.tools.Captcha.TYPE.CHARS;

@SuppressLint("NonConstantResourceId")
public final class DialogActivity extends BaseActivity {

    @BindView(R.id.title_layout)
    public FrameLayout titleLayout;
    @BindView(R.id.title_return_image)
    public AppCompatImageView imageView;
    @BindView(R.id.title_text)
    public AppCompatTextView titleView;

    private final AppCompatActivity aThis = this;
    private Unbinder unbinder;
    private DialogTools dialog;
    private String code = null;

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
        if (dialog != null) dialog.dismiss();
    }

    @OnClick(R.id.title_return_image)
    public void imageReturn() {
        if (Idle.isClick()) finish();
    }

    @SuppressLint("SetTextI18n")
    private void initView() {
        titleLayout.setBackgroundColor(getResources().getColor(R.color.gray, getTheme()));
        titleView.setTextColor(getResources().getColor(R.color.black1, getTheme()));
        imageView.setVisibility(View.VISIBLE);
        imageView.setColorFilter(R.color.black);
        titleView.setText(getIntent().getStringExtra(getResources().getString(R.string.title)));
        dialog = DialogTools.builder(aThis)
                .setLayout(R.layout.dialog_graphic_code)
                .isLockage(true)
                .setTitleText("远程生效验证")
                .setQuitText("提交")
                .setQuitColorId(R.color.white)
                .setAffirmColorId(R.color.white)
                .setCanceled(false)
                .setCancelable(false)
                .setListener(new DialogTools.OnEventTriggerListener() {

                    @Override
                    public void ok(DialogTools dialog) {

                    }

                    @Override
                    public void ok(DialogTools dialog, String param) {
                        if (code == null) {
                            Toasts.builder(aThis).setMsg("请点击右方按钮获取验证码").showInfo();
                        } else if (param.equals("")) {
                            Toasts.builder(aThis).setMsg("请输入验证码").showWarning();
                        } else if (!param.equalsIgnoreCase(code)) {
                            Toasts.builder(aThis).setMsg("请输入正确的验证码").showError();
                        } else {
                            Log.i("参数", param);
                            dialog.cancel();
                        }
                    }

                    @Override
                    public void on(DialogTools dialog, AppCompatImageView view, AppCompatTextView contentView) {
                        Captcha.build().type(CHARS).into(view);
                        code = Captcha.build().getCode();
                        contentView.setText(code);
                    }
                })
                .build();
    }

    @OnClick(R.id.activity_dialog)
    public void dialog() {
//        dialog.show();
        dbsSelector.show();
        wbsSelector.show();
    }

    private OptionsPickerView<String> dbsSelector;
    private OptionsPickerView<String> wbsSelector;
    private final List<String> dbsList = new ArrayList<>();
    private final List<String> wbsList = new ArrayList<>();

    /**
     * 干球设定
     */
    private void dbs() {
        for (int i = 0; i <= 80; i++) {
            dbsList.add(String.valueOf(i));
        }
        dbsSelector = new OptionsPickerBuilder(aThis, (options1, options2, options3, v) -> {

        }).setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
                .setTitleText("干球设定")//标题
                .setSubCalSize(12)//确定和取消文字大小
                .setTitleSize(15)//标题文字大小
                .setTitleColor(Color.parseColor("#525252"))//标题文字颜色
                .setSubmitColor(Color.parseColor("#E47168"))//确定按钮文字颜色
                .setCancelColor(Color.parseColor("#AFAFAF"))//取消按钮文字颜色
                .setTitleBgColor(getResources().getColor(R.color.gray, getTheme()))//标题背景颜色 Night mode
                .setBgColor(getResources().getColor(R.color.white, getTheme()))//滚轮背景颜色 Night mode
                .setContentTextSize(18)//滚轮文字大小
                .setLabels(null, null, null)//设置选择的三级单位
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setCyclic(false, false, false)//循环与否
                .setSelectOptions(0, 0, 0)  //设置默认选中项
                .setOutSideCancelable(false)//点击外部dismiss default true
                .isDialog(false)//是否显示为对话框样式
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .build();
        dbsSelector.setPicker(dbsList, null, null);
    }
    /**
     * 湿球设定
     */
    private void wbs() {
        for (int i = 0; i <= 80; i++) {
            wbsList.add(String.valueOf(i - 0.5));
            wbsList.add(String.valueOf(i));
            if (i == -0.5) wbsList.remove(String.valueOf(i));
        }
        wbsSelector = new OptionsPickerBuilder(aThis, (options1, options2, options3, v) -> {

        }).setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
                .setTitleText("干球设定")//标题
                .setSubCalSize(12)//确定和取消文字大小
                .setTitleSize(15)//标题文字大小
                .setTitleColor(Color.parseColor("#525252"))//标题文字颜色
                .setSubmitColor(Color.parseColor("#E47168"))//确定按钮文字颜色
                .setCancelColor(Color.parseColor("#AFAFAF"))//取消按钮文字颜色
                .setTitleBgColor(getResources().getColor(R.color.gray, getTheme()))//标题背景颜色 Night mode
                .setBgColor(getResources().getColor(R.color.white, getTheme()))//滚轮背景颜色 Night mode
                .setContentTextSize(18)//滚轮文字大小
                .setLabels(null, null, null)//设置选择的三级单位
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setCyclic(false, false, false)//循环与否
                .setSelectOptions(0, 0, 0)  //设置默认选中项
                .setOutSideCancelable(false)//点击外部dismiss default true
                .isDialog(false)//是否显示为对话框样式
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .build();
        wbsSelector.setPicker(wbsList, null, null);
    }
}
