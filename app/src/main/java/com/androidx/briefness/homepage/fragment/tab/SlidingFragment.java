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
import com.androidx.view.tab.layout.SlidingTabLayout;
import com.androidx.view.tab.use.TabLayoutBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @date 2021/07/05
 */
@SuppressLint("NonConstantResourceId")
public class SlidingFragment extends Fragment {

    @BindView(R.id.sliding1)
    public SlidingTabLayout slidingTabLayout1;
    @BindView(R.id.sliding2)
    public SlidingTabLayout slidingTabLayout2;
    @BindView(R.id.sliding3)
    public SlidingTabLayout slidingTabLayout3;
    @BindView(R.id.sliding4)
    public SlidingTabLayout slidingTabLayout4;
    @BindView(R.id.sliding5)
    public SlidingTabLayout slidingTabLayout5;
    @BindView(R.id.sliding6)
    public SlidingTabLayout slidingTabLayout6;
    @BindView(R.id.sliding7)
    public SlidingTabLayout slidingTabLayout7;
    @BindView(R.id.sliding8)
    public SlidingTabLayout slidingTabLayout8;
    @BindView(R.id.sliding9)
    public SlidingTabLayout slidingTabLayout9;
    @BindView(R.id.sliding10)
    public SlidingTabLayout slidingTabLayout10;
    @BindView(R.id.vp2)
    public ViewPager2 viewPager2;

    private TabLayoutBar tab1;
    private TabLayoutBar tab2;
    private TabLayoutBar tab3;
    private TabLayoutBar tab4;
    private TabLayoutBar tab5;
    private TabLayoutBar tab6;
    private TabLayoutBar tab7;
    private TabLayoutBar tab8;
    private TabLayoutBar tab9;
    private TabLayoutBar tab10;

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
        return inflater.inflate(R.layout.frag_sliding, container, false);
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
        tab6.destroy();
        tab7.destroy();
        tab8.destroy();
        tab9.destroy();
        tab10.destroy();
    }

    private void initView() {
        tab1 = TabLayoutBar.builder()
                .setActivity(aThis)
                .setViewPager2(viewPager2)
                .setTabLayout(slidingTabLayout1)
                .setTitles("首页1", "消息1", "我的1")
                .setFragments(new TabActivity.OneFragment(), new TabActivity.TowFragment(), new TabActivity.ThreeFragment())
                .initBuild();

        tab2 = TabLayoutBar.builder()
                .setActivity(aThis)
                .setViewPager2(viewPager2)
                .setTabLayout(slidingTabLayout2)
                .setTitles("首页2", "消息2", "我的2")
                .setFragments(new TabActivity.OneFragment(), new TabActivity.TowFragment(), new TabActivity.ThreeFragment())
                .initBuild();

        tab3 = TabLayoutBar.builder()
                .setActivity(aThis)
                .setViewPager2(viewPager2)
                .setTabLayout(slidingTabLayout3)
                .setTitles("首页3", "消息3", "我的3")
                .setFragments(new TabActivity.OneFragment(), new TabActivity.TowFragment(), new TabActivity.ThreeFragment())
                .initBuild();

        tab4 = TabLayoutBar.builder()
                .setActivity(aThis)
                .setViewPager2(viewPager2)
                .setTabLayout(slidingTabLayout4)
                .setTitles("首页4", "消息4", "我的4")
                .setFragments(new TabActivity.OneFragment(), new TabActivity.TowFragment(), new TabActivity.ThreeFragment())
                .initBuild();

        tab5 = TabLayoutBar.builder()
                .setActivity(aThis)
                .setViewPager2(viewPager2)
                .setTabLayout(slidingTabLayout5)
                .setTitles("首页5", "消息5", "我的5")
                .setFragments(new TabActivity.OneFragment(), new TabActivity.TowFragment(), new TabActivity.ThreeFragment())
                .initBuild();

        tab6 = TabLayoutBar.builder()
                .setActivity(aThis)
                .setViewPager2(viewPager2)
                .setTabLayout(slidingTabLayout6)
                .setTitles("首页6", "消息6", "我的6")
                .setFragments(new TabActivity.OneFragment(), new TabActivity.TowFragment(), new TabActivity.ThreeFragment())
                .initBuild();

        tab7 = TabLayoutBar.builder()
                .setActivity(aThis)
                .setViewPager2(viewPager2)
                .setTabLayout(slidingTabLayout7)
                .setTitles("首页7", "消息7", "我的7")
                .setFragments(new TabActivity.OneFragment(), new TabActivity.TowFragment(), new TabActivity.ThreeFragment())
                .initBuild();

        tab8 = TabLayoutBar.builder()
                .setActivity(aThis)
                .setViewPager2(viewPager2)
                .setTabLayout(slidingTabLayout8)
                .setTitles("首页8", "消息8", "我的8")
                .setFragments(new TabActivity.OneFragment(), new TabActivity.TowFragment(), new TabActivity.ThreeFragment())
                .initBuild();

        tab9 = TabLayoutBar.builder()
                .setActivity(aThis)
                .setViewPager2(viewPager2)
                .setTabLayout(slidingTabLayout9)
                .setTitles("首页9", "消息9", "我的9")
                .setFragments(new TabActivity.OneFragment(), new TabActivity.TowFragment(), new TabActivity.ThreeFragment())
                .initBuild();

        tab10 = TabLayoutBar.builder()
                .setActivity(aThis)
                .setViewPager2(viewPager2)
                .setTabLayout(slidingTabLayout10)
                .setTitles("首页10", "消息10", "我的10")
                .setFragments(new TabActivity.OneFragment(), new TabActivity.TowFragment(), new TabActivity.ThreeFragment())
                .initBuild();

    }

    private void start() {
        tab1.execute();
        tab2.execute();
        tab3.execute();
        tab4.execute();
        tab5.execute();
        tab6.execute();
        tab7.execute();
        tab8.execute();
        tab9.execute();
        tab10.execute();
    }
}
