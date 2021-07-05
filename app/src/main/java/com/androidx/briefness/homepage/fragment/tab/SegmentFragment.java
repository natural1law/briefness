package com.androidx.briefness.homepage.fragment.tab;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.androidx.briefness.R;
import com.androidx.briefness.homepage.activity.TabActivity;
import com.androidx.view.tab.layout.SegmentTabLayout;
import com.androidx.view.tab.use.TabLayoutBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

@SuppressLint("NonConstantResourceId")
public class SegmentFragment extends Fragment {

    @BindView(R.id.tl_1)
    public SegmentTabLayout segmentTabLayout1;
    @BindView(R.id.tl_2)
    public SegmentTabLayout segmentTabLayout2;
    @BindView(R.id.tl_3)
    public SegmentTabLayout segmentTabLayout3;
    @BindView(R.id.tl_4)
    public SegmentTabLayout segmentTabLayout4;
    @BindView(R.id.tl_5)
    public SegmentTabLayout segmentTabLayout5;
    @BindView(R.id.vp_2)
    public ViewPager2 viewPager2;

    private TabLayoutBar tab1;
    private TabLayoutBar tab2;
    private TabLayoutBar tab3;
    private TabLayoutBar tab4;
    private TabLayoutBar tab5;
    private Unbinder unbinder;
    private AppCompatActivity aThis;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.aThis = (AppCompatActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        start();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_segment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        initView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        tab1.destroy();
        tab2.destroy();
        tab3.destroy();
        tab4.destroy();
        tab5.destroy();
    }

    private void initView() {
        tab1 = TabLayoutBar.builder()
                .setActivity(aThis)
                .setViewPager2(viewPager2)
                .setTabLayout(segmentTabLayout1)
                .setTitles("首页1", "消息1", "我的1")
                .setFragments(new TabActivity.OneFragment(), new TabActivity.TowFragment(), new TabActivity.ThreeFragment())
                .initBuild();

        tab2 = TabLayoutBar.builder()
                .setActivity(aThis)
                .setViewPager2(viewPager2)
                .setTabLayout(segmentTabLayout2)
                .setTitles("首页2", "消息2", "我的2")
                .setFragments(new TabActivity.OneFragment(), new TabActivity.TowFragment(), new TabActivity.ThreeFragment())
                .initBuild();

        tab3 = TabLayoutBar.builder()
                .setActivity(aThis)
                .setViewPager2(viewPager2)
                .setTabLayout(segmentTabLayout3)
                .setTitles("首页3", "消息3", "我的3")
                .setFragments(new TabActivity.OneFragment(), new TabActivity.TowFragment(), new TabActivity.ThreeFragment())
                .initBuild();

        tab4 = TabLayoutBar.builder()
                .setActivity(aThis)
                .setViewPager2(viewPager2)
                .setTabLayout(segmentTabLayout4)
                .setTitles("首页4", "消息4", "我的4")
                .setFragments(new TabActivity.OneFragment(), new TabActivity.TowFragment(), new TabActivity.ThreeFragment())
                .initBuild();

        tab5 = TabLayoutBar.builder()
                .setActivity(aThis)
                .setViewPager2(viewPager2)
                .setTabLayout(segmentTabLayout5)
                .setTitles("首页5", "消息5", "我的5")
                .setFragments(new TabActivity.OneFragment(), new TabActivity.TowFragment(), new TabActivity.ThreeFragment())
                .initBuild();

    }

    private void start() {
        tab1.execute();
        tab2.execute();
        tab3.execute();
        tab4.execute();
        tab5.execute();
    }
}
