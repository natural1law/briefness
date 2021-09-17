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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @date 2021/04/30
 */
@SuppressLint("NonConstantResourceId")
public final class MsgShowActivity extends BaseActivity {

    private final AppCompatActivity aThis = this;
    @BindView(R.id.title_layout)
    public FrameLayout titleLayout;
    @BindView(R.id.title_return_image)
    public AppCompatImageView imageView;
    @BindView(R.id.title_text)
    public AppCompatTextView titleView;
    private Unbinder unbinder;

    @SuppressLint("SetTextI18n")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_show);
        unbinder = ButterKnife.bind(aThis);
        titleLayout.setBackgroundColor(getResources().getColor(R.color.gray, getTheme()));
        titleView.setTextColor(getResources().getColor(R.color.black1, getTheme()));
        imageView.setVisibility(View.VISIBLE);
        imageView.setColorFilter(R.color.black);
        titleView.setText(getIntent().getStringExtra(getResources().getString(R.string.title)));
        initView();
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

    }
}
