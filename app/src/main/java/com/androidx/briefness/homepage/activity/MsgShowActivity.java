package com.androidx.briefness.homepage.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.androidx.briefness.R;
import com.androidx.briefness.base.BaseActivity;
import com.androidx.reduce.tools.Idle;
import com.androidx.reduce.tools.This;
import com.androidx.reduce.tools.Toasts;

import java.io.File;

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
    @BindView(R.id.show_msg_layout)
    public ConstraintLayout layoutView;

    private Unbinder unbinder;
    private ActivityResultLauncher<Intent> launcherMovies;

    @Override
    protected int layoutId() {
        return R.layout.activity_msg_show;
    }

    @Override
    protected void onCreate() {
        unbinder = ButterKnife.bind(aThis);
        titleLayout.setBackgroundColor(getResources().getColor(R.color.gray, getTheme()));
        titleView.setTextColor(getResources().getColor(R.color.black1, getTheme()));
        imageView.setVisibility(View.VISIBLE);
        imageView.setColorFilter(R.color.black);
        titleView.setText(getIntent().getStringExtra(getResources().getString(R.string.title)));
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.title_return_image)
    public void imageReturn() {
        if (Idle.isClick()) finish();
    }

    @OnClick(R.id.activity_test)
    public void test() {
        String type2 = "video/Movies/cmp/*";
        This.build().resultAction(Intent.ACTION_GET_CONTENT, type2, launcherMovies).start();
    }

    private void initView() {
        launcherMovies = This.initLauncher(aThis, (resultCode, intent) -> This.resultMoviesListener(aThis, intent, data -> {
            try {
                if (!data.equals("")) {
                    File file = new File(data);
                    Toasts.i("视频地址1", file);
                }
            } catch (Exception e) {
                Log.e("models数据", Log.getStackTraceString(e));
            }
        }));

    }

}