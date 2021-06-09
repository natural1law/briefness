package com.androidx.briefness.base.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidx.briefness.R;
import com.androidx.briefness.homepage.activity.DialogActivity;
import com.androidx.briefness.homepage.activity.MsgShowActivity;
import com.androidx.briefness.homepage.adapter.HomepageAdapter;
import com.androidx.reduce.tools.This;
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
    @BindView(R.id.title_text)
    public AppCompatTextView titleView;
    @BindView(R.id.homepage_rv)
    public RecyclerView homepageRv;

    private Context c;
    private Unbinder unbinder;
    private HomepageAdapter adapter;
    private final List<String> list = new ArrayList<>();
    private final Bundle bundle = new Bundle();
    private final String[] arr = {"Toast功能展示", "dialog功能展示"};
    private Runnable run;

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
        if (run != null) This.destroy(run);
    }

    private void initView() {
        titleLayout.setBackgroundColor(getResources().getColor(R.color.gray, c.getTheme()));
        titleView.setTextColor(getResources().getColor(R.color.black1, c.getTheme()));
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
                This.start(run = This.activity(c, MsgShowActivity.class, bundle));
            } else if (list.get(position).equals(list.get(1))) {
                bundle.putString(getResources().getString(R.string.title), list.get(position));
                This.start(run = This.activity(c, DialogActivity.class, bundle));
            } else if (list.get(position).equals(list.get(2))) {
                bundle.putString(getResources().getString(R.string.title), list.get(position));
            } else if (list.get(position).equals(list.get(3))) {
                bundle.putString(getResources().getString(R.string.title), list.get(position));
            } else if (list.get(position).equals(list.get(4))) {
                bundle.putString(getResources().getString(R.string.title), list.get(position));
            } else if (list.get(position).equals(list.get(5))) {
                bundle.putString(getResources().getString(R.string.title), list.get(position));
            } else if (list.get(position).equals(list.get(6))) {
                bundle.putString(getResources().getString(R.string.title), list.get(position));
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
