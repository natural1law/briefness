package com.androidx.briefness.homepage.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.androidx.briefness.R;
import com.androidx.echarts.base.BaseWebActivity;
import com.androidx.http.use.NetRequest;
import com.androidx.reduce.tools.Idle;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

@SuppressLint("NonConstantResourceId")
public final class EchartsActivity extends BaseWebActivity {

    @BindView(R.id.title_layout)
    public FrameLayout titleLayout;
    @BindView(R.id.title_return_image)
    public AppCompatImageView returnView;
    @BindView(R.id.title_text)
    public AppCompatTextView titleView;
    @BindView(R.id.activity_web_layout)
    public LinearLayoutCompat webView;

    private Unbinder unbinder;
    private final EchartsActivity aThis = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_echarts);
        unbinder = ButterKnife.bind(aThis);
        initView();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (agentWeb == null) return super.onKeyDown(keyCode, event);
        if (agentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            returnView.performClick();
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
        returnView.setVisibility(View.VISIBLE);
        returnView.setColorFilter(R.color.black);
        titleView.setText(getIntent().getStringExtra(getResources().getString(R.string.title)));
        Map<String, Object> map = new ConcurrentHashMap<>();
        super.initWeb(webView).setLoadListener(() -> NetRequest.sendMapPost("hapi.syyfkj.cn", "/app/v1/", map, data -> super.setCallJs("callJS", data)));
    }

    @OnClick(R.id.activity_echarts)
    public void line() {
        super.setStart("index.html");
    }

}
