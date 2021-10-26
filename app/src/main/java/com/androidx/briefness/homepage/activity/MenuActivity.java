package com.androidx.briefness.homepage.activity;

import static com.androidx.briefness.base.App.toasts;

import android.annotation.SuppressLint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.androidx.briefness.R;
import com.androidx.briefness.base.BaseActivity;
import com.androidx.reduce.tools.Convert;
import com.androidx.reduce.tools.Idle;
import com.androidx.view.menu.FloatMenu;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import butterknife.Unbinder;

@SuppressLint("NonConstantResourceId")
public final class MenuActivity extends BaseActivity {

    private final AppCompatActivity aThis = this;
    @BindView(R.id.title_layout)
    public FrameLayout titleLayout;
    @BindView(R.id.title_return_image)
    public AppCompatImageView imageView;
    @BindView(R.id.title_text)
    public AppCompatTextView titleView;

    private Unbinder unbinder;
    private FloatMenu floatMenu;
    private final Point point = new Point();

    @Override
    protected void onCreate() {
        setContentView(R.layout.activity_menu);
        unbinder = ButterKnife.bind(aThis);
        titleLayout.setBackgroundColor(getResources().getColor(R.color.gray, getTheme()));
        titleView.setTextColor(getResources().getColor(R.color.black1, getTheme()));
        imageView.setVisibility(View.VISIBLE);
        imageView.setColorFilter(R.color.black);
        titleView.setText(getIntent().getStringExtra(getResources().getString(R.string.title)));
        floatMenu = new FloatMenu(aThis);
        floatMenu.inflate(R.menu.tools, Convert.Pixel.get(aThis).dp(60));
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            point.x = (int) ev.getRawX();
            point.y = (int) ev.getRawY();
        }
        return super.dispatchTouchEvent(ev);
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

    @OnLongClick(R.id.activity_long)
    public void longMenu() {
        floatMenu.show(point);
        floatMenu.setOnItemClickListener((v, i) -> {
            switch (i) {
                case 0:
                    toasts.setMsg("分享成功").showInfo();
                    break;
                case 1:
                    toasts.setMsg("编辑成功").showInfo();
                    break;
                case 2:
                    toasts.setMsg("删除成功").showInfo();
                    break;
            }
            floatMenu.dismiss();
        });
    }

}
