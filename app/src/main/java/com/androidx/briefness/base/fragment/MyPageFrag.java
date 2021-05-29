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
import androidx.fragment.app.FragmentActivity;

import com.androidx.briefness.R;
import com.androidx.view.bar.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
/**
 * @date 2021/04/30
 */
@SuppressLint("NonConstantResourceId")
public final class MyPageFrag extends BaseFragment {

    @BindView(R.id.title_layout)
    public FrameLayout titleLayout;
    @BindView(R.id.title_text)
    public AppCompatTextView titleView;

    private Unbinder unbinder;
    private FragmentActivity fThis;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.fThis = (FragmentActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return setRootView(inflater, container, R.layout.frag_my);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        titleLayout.setBackgroundColor(fThis.getResources().getColor(R.color.gray, fThis.getTheme()));
        titleView.setTextColor(getResources().getColor(R.color.black1, fThis.getTheme()));
        titleView.setText("我的");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
