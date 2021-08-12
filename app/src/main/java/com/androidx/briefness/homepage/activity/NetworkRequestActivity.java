package com.androidx.briefness.homepage.activity;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static android.provider.Telephony.Carriers.USER;
import static com.androidx.briefness.base.App.mc;
import static com.androidx.briefness.base.App.toasts;
import static com.androidx.briefness.homepage.service.NotificationService.enqueue;
import static com.androidx.reduce.tools.Convert.Timestamp.DATE_FORMAT10;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
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
import com.androidx.briefness.homepage.service.NotificationService;
import com.androidx.reduce.tools.Convert;
import com.androidx.reduce.tools.Idle;
import com.google.protobuf.ByteString;
import com.module.protobuf.MsgModule;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @date 2021/04/30
 */
@SuppressLint("NonConstantResourceId")
public final class NetworkRequestActivity extends BaseActivity {

    @BindView(R.id.title_layout)
    public FrameLayout titleLayout;
    @BindView(R.id.title_return_image)
    public AppCompatImageView imageView;
    @BindView(R.id.title_text)
    public AppCompatTextView titleView;

    @BindView(R.id.network_content)
    public AppCompatTextView contentView;

    private Unbinder unbinder;
    private final AppCompatActivity aThis = this;

    @SuppressLint("SetTextI18n")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_network);
            unbinder = ButterKnife.bind(aThis);
            titleLayout.setBackgroundColor(getResources().getColor(R.color.gray, getTheme()));
            titleView.setTextColor(getResources().getColor(R.color.black1, getTheme()));
            imageView.setVisibility(View.VISIBLE);
            imageView.setColorFilter(R.color.black);
            titleView.setText(getIntent().getStringExtra(getResources().getString(R.string.title)));
            initView();
        } catch (Exception e) {
            toasts.e("收到数据", e);
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
    }

    @OnClick(R.id.title_return_image)
    public void imageReturn() {
        if (Idle.isClick()) finish();
    }

    private void initView() {
        mc.setApply(USER, "小纯");
        Intent intent = new Intent(aThis, NotificationService.class);
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        } else {
            startService(intent);
        }
    }

    @OnClick(R.id.network_send)
    public void send() {
        MsgModule.MsgRequest request = MsgModule.MsgRequest.newBuilder()
                .setCode(200)
                .setUserId("小纯")
                .setData(ByteString.copyFromUtf8("你好"))
                .setSendTime(Convert.Timestamp.refining(System.currentTimeMillis(), DATE_FORMAT10))
                .build();
        enqueue.send(1, request.toByteArray());
    }

}
