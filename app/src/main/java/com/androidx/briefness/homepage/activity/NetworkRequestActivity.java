package com.androidx.briefness.homepage.activity;

import static com.androidx.briefness.base.App.toasts;

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
import com.androidx.http.net.listener.Enqueue;
import com.androidx.http.use.NetRequest;
import com.androidx.reduce.tools.Idle;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.WeakHashMap;

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
    private Enqueue enqueue;
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
        enqueue.close();
    }

    @OnClick(R.id.title_return_image)
    public void imageReturn() {
        if (Idle.isClick()) finish();
    }

    private void initView() throws Exception {
        Map<String, Object> map = new WeakHashMap<>();
        map.put("username", "小纯");
        enqueue = NetRequest.initWebSocket("http://192.168.1.133:8081/cmp/push/notification/", map);
        enqueue.setLoginCallback(() -> toasts.setMsg("连接成功").showSuccess());
        enqueue.setMsgCallback((type, msg, bs) -> {
            contentView.setText(msg);
            toasts.i("收到数据", msg);
        });
    }

    @OnClick(R.id.network_send)
    public void send() {
        enqueue.send(1, "您好".getBytes(StandardCharsets.UTF_8));
    }

}
