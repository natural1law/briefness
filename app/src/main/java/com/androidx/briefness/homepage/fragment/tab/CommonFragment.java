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
import com.androidx.view.tab.layout.CommonTabLayout;
import com.androidx.view.tab.use.TabLayoutBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

@SuppressLint("NonConstantResourceId")
public class CommonFragment extends Fragment {

    private final int[] unselectedIcon = {R.mipmap.tab_home_unselect, R.mipmap.tab_speech_unselect, R.mipmap.tab_contact_unselect};
    private final int[] selectedIcon = {R.mipmap.tab_home_select, R.mipmap.tab_speech_select, R.mipmap.tab_contact_select};
    @BindView(R.id.common1)
    public CommonTabLayout commonTabLayout1;
    @BindView(R.id.common2)
    public CommonTabLayout commonTabLayout2;
    @BindView(R.id.common3)
    public CommonTabLayout commonTabLayout3;
    @BindView(R.id.common4)
    public CommonTabLayout commonTabLayout4;
    @BindView(R.id.common5)
    public CommonTabLayout commonTabLayout5;
    @BindView(R.id.common6)
    public CommonTabLayout commonTabLayout6;
    @BindView(R.id.common7)
    public CommonTabLayout commonTabLayout7;
    @BindView(R.id.common8)
    public CommonTabLayout commonTabLayout8;
    @BindView(R.id.common_vp2)
    public ViewPager2 viewPager2;
    private TabLayoutBar tab1;
    private TabLayoutBar tab2;
    private TabLayoutBar tab3;
    private TabLayoutBar tab4;
    private TabLayoutBar tab5;
    private TabLayoutBar tab6;
    private TabLayoutBar tab7;
    private TabLayoutBar tab8;
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
        return inflater.inflate(R.layout.frag_common, container, false);
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
    }

    @SuppressLint("ResourceType")
    private void initView() {
        tab1 = TabLayoutBar.builder()
                .setActivity(aThis)
                .setTabLayout(commonTabLayout1)
                .setUnselectedIcon(unselectedIcon)
                .setPitchOnIcon(selectedIcon)
                .setContainerViewId(R.id.common_fl)
                .setTitles("首页1", "消息1", "我的1")
                .setFragments(new TabActivity.OneFragment(), new TabActivity.TowFragment(), new TabActivity.ThreeFragment())
                .initBuild();

        tab2 = TabLayoutBar.builder()
                .setActivity(aThis)
                .setViewPager2(viewPager2)
                .setPitchOnIcon(selectedIcon)
                .setTabLayout(commonTabLayout2)
                .setUnselectedIcon(unselectedIcon)
                .setTitles("首页2", "消息2", "我的2")
                .setFragments(new TabActivity.OneFragment(), new TabActivity.TowFragment(), new TabActivity.ThreeFragment())
                .initBuild();

        tab3 = TabLayoutBar.builder()
                .setActivity(aThis)
                .setViewPager2(viewPager2)
                .setTabLayout(commonTabLayout3)
                .setUnselectedIcon(unselectedIcon)
                .setPitchOnIcon(selectedIcon)
                .setTitles("首页3", "消息3", "我的3")
                .setFragments(new TabActivity.OneFragment(), new TabActivity.TowFragment(), new TabActivity.ThreeFragment())
                .initBuild();

        tab4 = TabLayoutBar.builder()
                .setActivity(aThis)
                .setViewPager2(viewPager2)
                .setTabLayout(commonTabLayout4)
                .setUnselectedIcon(unselectedIcon)
                .setPitchOnIcon(selectedIcon)
                .setTitles("首页4", "消息4", "我的4")
                .setFragments(new TabActivity.OneFragment(), new TabActivity.TowFragment(), new TabActivity.ThreeFragment())
                .initBuild();

        tab5 = TabLayoutBar.builder()
                .setActivity(aThis)
                .setViewPager2(viewPager2)
                .setTabLayout(commonTabLayout5)
                .setUnselectedIcon(unselectedIcon)
                .setPitchOnIcon(selectedIcon)
                .setTitles("首页5", "消息5", "我的5")
                .setFragments(new TabActivity.OneFragment(), new TabActivity.TowFragment(), new TabActivity.ThreeFragment())
                .initBuild();

        tab6 = TabLayoutBar.builder()
                .setActivity(aThis)
                .setViewPager2(viewPager2)
                .setTabLayout(commonTabLayout6)
                .setUnselectedIcon(unselectedIcon)
                .setPitchOnIcon(selectedIcon)
                .setTitles("首页6", "消息6", "我的6")
                .setFragments(new TabActivity.OneFragment(), new TabActivity.TowFragment(), new TabActivity.ThreeFragment())
                .initBuild();

        tab7 = TabLayoutBar.builder()
                .setActivity(aThis)
                .setViewPager2(viewPager2)
                .setTabLayout(commonTabLayout7)
                .setUnselectedIcon(unselectedIcon)
                .setPitchOnIcon(selectedIcon)
                .setTitles("首页7", "消息7", "我的7")
                .setFragments(new TabActivity.OneFragment(), new TabActivity.TowFragment(), new TabActivity.ThreeFragment())
                .initBuild();

        tab8 = TabLayoutBar.builder()
                .setActivity(aThis)
                .setViewPager2(viewPager2)
                .setTabLayout(commonTabLayout8)
                .setUnselectedIcon(unselectedIcon)
                .setPitchOnIcon(selectedIcon)
                .setTitles("首页8", "消息8", "我的8")
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
    }
}
