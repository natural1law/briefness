package com.androidx.briefness.homepage.activity;

import android.annotation.SuppressLint;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.androidx.briefness.R;
import com.androidx.briefness.base.BaseActivity;
import com.androidx.reduce.tools.Idle;
import com.androidx.reduce.tools.Toasts;
import com.tencent.mmkv.MMKV;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @date 2021/04/30
 */
@SuppressLint("NonConstantResourceId")
public final class MsgShowActivity extends BaseActivity {

    @BindView(R.id.title_layout)
    public FrameLayout titleLayout;
    @BindView(R.id.title_return_image)
    public AppCompatImageView imageView;
    @BindView(R.id.title_text)
    public AppCompatTextView titleView;

    private final AppCompatActivity aThis = this;

    private MMKV mmkv;

    @Override
    protected int layoutId() {
        return R.layout.activity_msg_show;
    }

    @Override
    protected void onCreate() {
        setTitle(titleLayout, imageView, titleView);
        initView();
    }

    @OnClick(R.id.title_return_image)
    public void imageReturn() {
        if (Idle.isClick()) finish();
    }

    @OnClick(R.id.activity_test)
    public void test() {
        Toasts.i(mmkv.decodeString("1"));
        Toasts.i(mmkv.decodeString("2"));
    }

    private void initView() {
        try {
            mmkv = MMKV.mmkvWithID(MMKV.initialize(aThis));
            mmkv.encode("1", "1");
            mmkv.encode("2", "2");
        } catch (Exception e) {
            Toasts.e(getClass().getName(), e);
        }

    }

}