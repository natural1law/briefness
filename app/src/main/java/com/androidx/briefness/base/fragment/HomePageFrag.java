package com.androidx.briefness.base.fragment;

import static com.androidx.briefness.base.App.appThis;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidx.briefness.R;
import com.androidx.briefness.homepage.activity.DialogActivity;
import com.androidx.briefness.homepage.activity.EchartsActivity;
import com.androidx.briefness.homepage.activity.MsgShowActivity;
import com.androidx.briefness.homepage.activity.NetworkRequestActivity;
import com.androidx.briefness.homepage.activity.PageRecyclerViewActivity;
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

    private final List<String> list = new ArrayList<>();
    private final Bundle bundle = new Bundle();
    private final String[] arr = {"Toast功能演示", "dialog功能演示", "图表功能演示", "Tab导航栏功能演示", "网络请求接口演示", "截屏录屏功能演示",
            "分页功能演示"};
    @BindView(R.id.title_layout)
    public FrameLayout titleLayout;
    @BindView(R.id.title_return_image)
    public AppCompatImageView returnView;
    @BindView(R.id.title_text)
    public AppCompatTextView titleView;
    @BindView(R.id.homepage_rv)
    public RecyclerView homepageRv;
    private Context c;
    private Unbinder unbinder;
    private HomepageAdapter adapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.c = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return setRootView(inflater, container, R.layout.frag_homepage);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
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
        titleLayout.setBackgroundColor(getResources().getColor(R.color.gray, c.getTheme()));
        titleView.setTextColor(getResources().getColor(R.color.black1, c.getTheme()));
        returnView.setVisibility(View.GONE);

        titleView.setText("目录");

        homepageRv.setLayoutManager(new LinearLayoutManager(c));
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
                appThis.activity(c, MsgShowActivity.class, bundle).start();
            } else if (list.get(position).equals(list.get(1))) {
                bundle.putString(getResources().getString(R.string.title), list.get(position));
                appThis.activity(c, DialogActivity.class, bundle).start();
            } else if (list.get(position).equals(list.get(2))) {
                bundle.putString(getResources().getString(R.string.title), list.get(position));
                appThis.activity(c, EchartsActivity.class, bundle).start();
            } else if (list.get(position).equals(list.get(3))) {
                bundle.putString(getResources().getString(R.string.title), list.get(position));
                appThis.activity(c, TabActivity.class, bundle).start();
            } else if (list.get(position).equals(list.get(4))) {
                bundle.putString(getResources().getString(R.string.title), list.get(position));
                appThis.activity(c, NetworkRequestActivity.class, bundle).start();
            } else if (list.get(position).equals(list.get(5))) {
                bundle.putString(getResources().getString(R.string.title), list.get(position));
                appThis.activity(c, ScreenCaptureActivity.class, bundle).start();
            } else if (list.get(position).equals(list.get(6))) {
                bundle.putString(getResources().getString(R.string.title), list.get(position));
                appThis.activity(c, PageRecyclerViewActivity.class, bundle).start();
            } else if (list.get(position).equals(list.get(7))) {
                bundle.putString(getResources().getString(R.string.title), list.get(position));
            } else if (list.get(position).equals(list.get(8))) {
                bundle.putString(getResources().getString(R.string.title), list.get(position));
            } else if (list.get(position).equals(list.get(9))) {
                bundle.putString(getResources().getString(R.string.title), list.get(position));
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
