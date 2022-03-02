package com.androidx.briefness.base.fragment;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

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
    @BindView(R.id.title_return_image)
    public AppCompatImageView returnView;
    @BindView(R.id.title_text)
    public AppCompatTextView titleView;

    private Unbinder unbinder;

    @Override
    protected int layoutId() {
        return R.layout.frag_my;
    }

    @Override
    protected void onCreateView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    protected void onViewCreated(View view) {
        titleLayout.setBackgroundColor(context.getResources().getColor(R.color.gray, context.getTheme()));
        titleView.setTextColor(getResources().getColor(R.color.black1, context.getTheme()));
        returnView.setVisibility(View.GONE);
        titleView.setText("我的");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
