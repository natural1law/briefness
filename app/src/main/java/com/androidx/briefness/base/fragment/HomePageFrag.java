package com.androidx.briefness.base.fragment;

import static com.androidx.briefness.base.App.appThis;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidx.briefness.R;
import com.androidx.briefness.homepage.activity.DialogActivity;
import com.androidx.briefness.homepage.activity.DragViewActivity;
import com.androidx.briefness.homepage.activity.EchartsActivity;
import com.androidx.briefness.homepage.activity.MenuActivity;
import com.androidx.briefness.homepage.activity.MsgShowActivity;
import com.androidx.briefness.homepage.activity.NetworkRequestActivity;
import com.androidx.briefness.homepage.activity.PageRecyclerViewActivity;
import com.androidx.briefness.homepage.activity.RefreshActivity;
import com.androidx.briefness.homepage.activity.ScreenCaptureActivity;
import com.androidx.briefness.homepage.activity.TabActivity;
import com.androidx.briefness.homepage.adapter.HomepageAdapter;
import com.androidx.view.bar.BaseFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @date 2021/04/30
 */
@SuppressLint("NonConstantResourceId")
public final class HomePageFrag extends BaseFragment {

    @BindView(R.id.title_layout)
    public FrameLayout titleLayout;
    @BindView(R.id.title_return_image)
    public AppCompatImageView returnView;
    @BindView(R.id.title_text)
    public AppCompatTextView titleView;
    @BindView(R.id.homepage_rv)
    public RecyclerView homepageRv;

    private Unbinder unbinder;
    private HomepageAdapter adapter;

    private final List<String> list = new ArrayList<>();
    private final Bundle bundle = new Bundle();
    private final String[] arr = {"Toast功能演示", "dialog功能演示", "图表功能演示", "Tab导航栏功能演示",
            "网络请求接口演示", "截屏录屏功能演示", "分页功能演示", "长按菜单演示", "刷新列表演示", "拖拽布局演示"};

    @Override
    protected int layoutId() {
        return R.layout.frag_homepage;
    }

    @Override
    protected void onCreateView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    protected void initUI() {
        initView();
        initData();
        initListener();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initView() {
        titleLayout.setBackgroundColor(getResources().getColor(R.color.gray, context.getTheme()));
        titleView.setTextColor(getResources().getColor(R.color.black1, context.getTheme()));
        returnView.setVisibility(View.GONE);

        titleView.setText("目录");

        homepageRv.setLayoutManager(new LinearLayoutManager(context));
        homepageRv.setAdapter(adapter = new HomepageAdapter());

    }

    private void initData() {
        Collections.addAll(list, arr);
        adapter.addData(list);
    }

    private void initListener() {
        adapter.setListener(position -> {
            if (list.isEmpty()) return;
            if (list.get(position).equals(list.get(0))) {
                bundle.putString(getResources().getString(R.string.title), list.get(position));
                appThis.activity(context, MsgShowActivity.class, bundle).start();
            } else if (list.get(position).equals(list.get(1))) {
                bundle.putString(getResources().getString(R.string.title), list.get(position));
                appThis.activity(context, DialogActivity.class, bundle).start();
            } else if (list.get(position).equals(list.get(2))) {
                bundle.putString(getResources().getString(R.string.title), list.get(position));
                appThis.activity(context, EchartsActivity.class, bundle).start();
            } else if (list.get(position).equals(list.get(3))) {
                bundle.putString(getResources().getString(R.string.title), list.get(position));
                appThis.activity(context, TabActivity.class, bundle).start();
            } else if (list.get(position).equals(list.get(4))) {
                bundle.putString(getResources().getString(R.string.title), list.get(position));
                appThis.activity(context, NetworkRequestActivity.class, bundle).start();
            } else if (list.get(position).equals(list.get(5))) {
                bundle.putString(getResources().getString(R.string.title), list.get(position));
                appThis.activity(context, ScreenCaptureActivity.class, bundle).start();
            } else if (list.get(position).equals(list.get(6))) {
                bundle.putString(getResources().getString(R.string.title), list.get(position));
                appThis.activity(context, PageRecyclerViewActivity.class, bundle).start();
            } else if (list.get(position).equals(list.get(7))) {
                bundle.putString(getResources().getString(R.string.title), list.get(position));
                appThis.activity(context, MenuActivity.class, bundle).start();
            } else if (list.get(position).equals(list.get(8))) {
                bundle.putString(getResources().getString(R.string.title), list.get(position));
                appThis.activity(context, RefreshActivity.class, bundle).start();
            } else if (list.get(position).equals(list.get(9))) {
                bundle.putString(getResources().getString(R.string.title), list.get(position));
                appThis.activity(context, DragViewActivity.class, bundle).start();
            } else if (list.get(position).equals(list.get(10))) {
                bundle.putString(getResources().getString(R.string.title), list.get(position));
            } else if (list.get(position).equals(list.get(11))) {
                bundle.putString(getResources().getString(R.string.title), list.get(position));
            } else if (list.get(position).equals(list.get(12))) {
                bundle.putString(getResources().getString(R.string.title), list.get(position));
            } else if (list.get(position).equals(list.get(13))) {
                bundle.putString(getResources().getString(R.string.title), list.get(position));
            } else if (list.get(position).equals(list.get(14))) {
                bundle.putString(getResources().getString(R.string.title), list.get(position));
            }
        });
    }
}
