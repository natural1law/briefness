package com.androidx.view.bar;

import android.annotation.SuppressLint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.IntRange;
import androidx.annotation.IntegerRes;
import androidx.annotation.MenuRes;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;

import com.androidx.reduce.tools.This;
import com.androidx.view.R;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

/**
 * 底部导航栏
 *
 * @author 李玄道
 * @date 2021/04/29
 */
public final class NavigationBar {

    /**
     * 基础参数
     */
    private final int[] space;
    private List<Fragment> fragments;
    private final FragmentActivity aThis;
    /**
     * 基础布局
     */
    private Fragment cf;//当前碎片
    public BottomNavigationView navigationView;
    private final LinearLayoutCompat navigationLayout;
    private final FragmentContainerView fragmentLayout;

    @SuppressLint("NonConstantResourceId")
    private final BottomNavigationView.OnItemSelectedListener selectedListener = item -> {
        Menu menu = navigationView.getMenu();
        int let = menu.size() & fragments.size();
        for (int i = 0; i < let; i++) {
            if (menu.getItem(i).getItemId() == item.getItemId()) {
                switchFragment(fragments.get(i)).commit();
            }
        }
        return true;
    };

    private NavigationBar(NewBuilder builder) {
        this.aThis = builder.aThis;
        this.navigationView = aThis.findViewById(R.id.nav_view);
        this.navigationLayout = aThis.findViewById(R.id.nav_layout);
        this.fragmentLayout = aThis.findViewById(R.id.nav_frame);
        this.cf = new Fragment();
        this.fragments = builder.fragments;
        this.space = builder.space;
        initView(builder);
        This.delay(() -> initData(builder.lazy), 17);
    }

    @NotNull
    @Contract("_ -> new")
    public static <T extends FragmentActivity> NewBuilder builder(@NotNull T aThis) {
        return new NewBuilder(aThis);
    }

    /**
     * 初始化布局
     */
    @SuppressLint({"WrongConstant", "ResourceType"})
    private void initView(NewBuilder b) {
        if (fragments.size() < 2 || fragments.size() > 5) {
            navigationLayout.setVisibility(View.GONE);
            Log.e(getClass().getName(), "navigation只支持2~5条fragment");
            return;
        }
        navigationView.inflateMenu(b.menu);
        navigationView.setItemTextColor(aThis.getResources().getColorStateList(b.itemTextColor, aThis.getTheme()));
        navigationView.setBackgroundColor(aThis.getResources().getColor(R.color.transparent, aThis.getTheme()));
        navigationLayout.setBackgroundColor(aThis.getResources().getColor(b.navigationBackgroundColor, aThis.getTheme()));
        fragmentLayout.setBackgroundColor(aThis.getResources().getColor(b.frameBackgroundColor, aThis.getTheme()));
        navigationView.setItemIconTintList(null);
        navigationView.setOnItemSelectedListener(selectedListener);
        adjustNavigationIcoSize(navigationView);
        navigationView.setLabelVisibilityMode(b.mode);
    }

    /**
     * 初始化数据
     *
     * @param isLazy 是否懒加载
     */
    private void initData(boolean isLazy) {
        if (!fragments.isEmpty()) {
            if (!isLazy) fragments.forEach(fragment -> {
                if (fragment != fragments.get(0)) switchFragment(fragment).commit();
            });
            switchFragment(fragments.get(0)).commit();
        }
    }

    /**
     * 加载布局
     */
    private FragmentTransaction switchFragment(Fragment targetFragment) {
        FragmentTransaction transaction = aThis.getSupportFragmentManager().beginTransaction();
        if (!targetFragment.isAdded()) {
            if (cf != null) transaction.hide(cf);
            transaction.add(fragmentLayout.getId(), targetFragment, targetFragment.getClass().getName());
        } else transaction.hide(cf).show(targetFragment);
        cf = targetFragment;
        return transaction;
    }

    /**
     * 底部导航栏按钮间距字体调整
     */
    private void adjustNavigationIcoSize(BottomNavigationView navigation) {
        try {
            BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigation.getChildAt(0);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                View iconView = menuView.getChildAt(i);
                ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
                if (aThis != null) {
                    DisplayMetrics displayMetrics = aThis.getResources().getDisplayMetrics();
                    layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, space[0], displayMetrics);
                    layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, space[1], displayMetrics);
                    iconView.setLayoutParams(layoutParams);
                }
            }
        } catch (Exception e) {
            Log.e(getClass().getName(), String.valueOf(e.getMessage()), e);
        }
    }

    @SuppressWarnings("unused")
    public static final class NewBuilder {

        private final FragmentActivity aThis;
        private final NewBuilder builder;
        private int menu = R.menu.nav_menu;
        private int itemTextColor = R.drawable.pagination_selector_tab_color;
        private int frameBackgroundColor = R.color.transparent;
        private int navigationBackgroundColor = R.color.transparent;
        private final List<Fragment> fragments = new LinkedList<>();
        private boolean lazy = false;
        private int mode = Mode.AUTO.getValue();
        private int[] space = {25, 25};

        /**
         * @param activity activity对象
         */
        private <T extends FragmentActivity> NewBuilder(@NonNull T activity) {
            this.aThis = activity;
            this.builder = this;
        }

        /**
         * 添加菜单
         */
        public NewBuilder setMenu(@MenuRes int menu) {
            this.menu = menu;
            return builder;
        }

        /**
         * 添加fragment对象
         */
        public NewBuilder setAddFragment(Fragment fragment) {
            fragments.add(fragment);
            return builder;
        }

        /**
         * 移除fragment对象
         */
        public NewBuilder setDelFragment(Fragment fragment) {
            fragments.remove(fragment);
            return builder;
        }

        /**
         * 移除fragment对象
         */
        public NewBuilder setDelFragment(@IntegerRes int position) {
            fragments.remove(position);
            return builder;
        }

        /**
         * 清空fragment对象
         */
        public NewBuilder setClearFragment() {
            fragments.clear();
            return builder;
        }

        /**
         * 设置条目文字颜色
         */
        public NewBuilder setItemTextColor(@DrawableRes int color) {
            this.itemTextColor = color;
            return builder;
        }

        /**
         * 设置底部导航栏背景颜色
         */
        public NewBuilder setBackgroundColor(@ColorRes int color) {
            this.navigationBackgroundColor = color;
            return builder;
        }

        /**
         * 设置背景颜色
         */
        public NewBuilder setFrameBackgroundColor(@ColorRes int color) {
            this.frameBackgroundColor = color;
            return builder;
        }

        /**
         * 设置间距
         */
        public NewBuilder setSpace(@IntRange(from = 0, to = 30) int... space) {
            this.space = space;
            return builder;
        }

        /**
         * 懒加载
         */
        public NewBuilder setLazy(boolean lazy) {
            this.lazy = lazy;
            return builder;
        }

        /**
         * 显示模式
         */
        public NewBuilder setMode(Mode mode) {
            this.mode = mode.getValue();
            return builder;
        }

        @NotNull
        @Contract(" -> new")
        @SuppressWarnings("UnusedReturnValue")
        public NavigationBar build() {
            return new NavigationBar(builder);
        }

    }

}
