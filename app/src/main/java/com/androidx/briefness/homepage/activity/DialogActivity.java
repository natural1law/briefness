 package com.androidx.briefness.homepage.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
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
import com.androidx.view.dialog.DialogTools;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.androidx.view.dialog.DialogTools.LayoutResId.VERIFICATION_CODE;

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
                .setLayout(VERIFICATION_CODE)
                .setContentId(R.id.dialog_content)
                .setTitleText("远程生效验证")
                .setAffirmText("提交")
                .setQuitColorId(R.color.irs)
                .setAffirmColorId(R.color.white)
                .setCanceled(false)
                .setCancelable(false)
                .setListener(new DialogTools.OnEventTriggerListener() {

                    @Override
                    public void ok(DialogTools dialog) {

                    }

                    @Override
                    public void ok(DialogTools dialog, String param, String code) {
                        dialog.cancel();
                    }
                })
                .build();
//
//        dialog.views.forEach(view -> {
//            if (view instanceof AppCompatImageView) {
//                verificationView = (AppCompatImageView) view;
//            }
//            if (view instanceof AppCompatTextView) {
//                contentView = (AppCompatTextView) view;
//            }
//        });
//
//        verificationView.setOnClickListener(v -> {
//            captcha.into(verificationView);
//            code = captcha.getCode();
//            contentView.setText(code);
//        });

    }

    @OnClick(R.id.activity_dialog)
    public void dialog() {
        dialog.show();
    }

}
