package com.androidx.briefness.homepage.activity;

import static com.androidx.briefness.base.App.toasts;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.androidx.briefness.R;
import com.androidx.briefness.base.BaseActivity;
import com.androidx.http.use.Rn;
import com.androidx.reduce.tools.Toasts;
import com.androidx.view.list.HolderView;
import com.androidx.view.list.RefreshAdapter;
import com.androidx.view.list.RefreshRecycler;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

@SuppressLint("NonConstantResourceId")
public final class RefreshActivity extends BaseActivity {

    private final RefreshActivity aThis = this;
    @BindView(R.id.title_layout)
    public FrameLayout titleLayout;
    @BindView(R.id.title_return_image)
    public AppCompatImageView imageView;
    @BindView(R.id.title_text)
    public AppCompatTextView titleView;
    private Unbinder unbinder;

    private static native String url();

    @Override
    protected int layoutId() {
        return R.layout.activity_refresh;
    }

    @Override
    protected void onCreate() {
        unbinder = ButterKnife.bind(aThis);
        titleLayout.setBackgroundColor(getResources().getColor(R.color.gray, getTheme()));
        titleView.setTextColor(getResources().getColor(R.color.black1, getTheme()));
        imageView.setVisibility(View.VISIBLE);
        imageView.setColorFilter(R.color.black);
        titleView.setText(getIntent().getStringExtra(getResources().getString(R.string.title)));
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.title_return_image)
    public void imageReturn() {
        setResult(5, new Intent().putExtra("haha", "你好"));
        finish();
    }

    private void initData() {
        List<String> list1 = new ArrayList<>(Arrays.asList("json5", "json6", "json7", "json8"));
        RefreshRecycler.execute(aThis, new Adapter(), (refresh, adapter, pageCode, status) -> {
            Toasts.i("页码", pageCode);
            Map<String, Object> param = new ConcurrentHashMap<>();
            param.put("userName", "18604900857");
            param.put("password", "LNgz@082");
            param.put("version", "1.1.6");
            Rn.sendMapPost(url(), param, JsonObject.class, data -> {
                List<String> list = new ArrayList<>(Arrays.asList("json1", "json2", "json3", "json4"));
                adapter.addTotalItem(11);
                if (status) {
                    adapter.addItem(list);
                    refresh.finishRefresh();
                } else {
                    adapter.loadItem(list1);
                    refresh.finishLoadMore();
                }
            });
        }, module -> {
            Toasts.i("条目数据", module);
            toasts.setMsg(module).showSuccess();
        });

    }

    public static final class Adapter extends RefreshAdapter<String> {

        @Override
        protected int layoutId() {
            return R.layout.adapter_refresh;
        }

        @Override
        protected void dispose(@NonNull HolderView holder, int position, String model) {
            int a = R.id.a;
            holder.setText(a, model);
            holder.setOnClickListener(a, view -> setOnClickItemListener(position));
        }

    }
}
