package com.androidx.view.bar;

import android.annotation.SuppressLint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.IntRange;
import androidx.annotation.IntegerRes;
import androidx.annotation.MenuRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;

import com.androidx.view.R;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

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
    private final int menu;
    private AppCompatActivity aThis;
    private FragmentActivity fThis;
    private final int fbc;
    private final int nbc;
    private final int itc;
    private int[] items;
    private final int[] space;
    private List<Fragment> fragments;
    private FragmentTransaction sf;
    /**
     * 基础布局
     */
    private Fragment cf;//当前碎片
    public BottomNavigationView navigationView;
    private LinearLayoutCompat navigationLayout;
    private FragmentContainerView fragmentLayout;

    @SuppressLint("NonConstantResourceId")
    private final NavigationBarView.OnItemSelectedListener selectedListener = item -> {
        switch (fragments.size()) {
            case 2:
                if (items[0] == item.getItemId()) {
                    Objects.requireNonNull(switchFragment(fragments.get(0))).commit();
                } else if (items[1] == item.getItemId()) {
                    Objects.requireNonNull(switchFragment(fragments.get(1))).commit();
                }
                return true;
            case 3:
                if (items[0] == item.getItemId()) {
                    Objects.requireNonNull(switchFragment(fragments.get(0))).commit();
                } else if (items[1] == item.getItemId()) {
                    Objects.requireNonNull(switchFragment(fragments.get(1))).commit();
                } else if (items[2] == item.getItemId()) {
                    Objects.requireNonNull(switchFragment(fragments.get(2))).commit();
                }
                return true;
            case 4:
                if (items[0] == item.getItemId()) {
                    Objects.requireNonNull(switchFragment(fragments.get(0))).commit();
                } else if (items[1] == item.getItemId()) {
                    Objects.requireNonNull(switchFragment(fragments.get(1))).commit();
                } else if (items[2] == item.getItemId()) {
                    Objects.requireNonNull(switchFragment(fragments.get(2))).commit();
                } else if (items[3] == item.getItemId()) {
                    Objects.requireNonNull(switchFragment(fragments.get(3))).commit();
                }
                return true;
            case 5:
                if (items[0] == item.getItemId()) {
                    Objects.requireNonNull(switchFragment(fragments.get(0))).commit();
                } else if (items[1] == item.getItemId()) {
                    Objects.requireNonNull(switchFragment(fragments.get(1))).commit();
                } else if (items[2] == item.getItemId()) {
                    Objects.requireNonNull(switchFragment(fragments.get(2))).commit();
                } else if (items[3] == item.getItemId()) {
                    Objects.requireNonNull(switchFragment(fragments.get(3))).commit();
                } else if (items[4] == item.getItemId()) {
                    Objects.requireNonNull(switchFragment(fragments.get(4))).commit();
                }
                return true;
            default:
                return false;
        }
    };

    /**
     * 初始化布局
     */
    private void initView() {
        if (fragments.size() < 2 || fragments.size() > 5) {
            navigationLayout.setVisibility(View.GONE);
            Log.e("selectedListener异常", "条目最小长度支持2，条目最大长度支持5");
            return;
        }
        if (items.length < 2 || items.length > 5) {
            navigationLayout.setVisibility(View.GONE);
            Log.e("selectedListener异常", "条目最小长度支持2，条目最大长度支持5");
            return;
        }
        navigationView.inflateMenu(menu);
        navigationView.setItemIconTintList(null);
        if (aThis != null) {
            navigationView.setItemTextColor(aThis.getResources().getColorStateList(itc, aThis.getTheme()));
            navigationView.setBackgroundColor(aThis.getResources().getColor(R.color.transparent, aThis.getTheme()));
            navigationLayout.setBackgroundColor(aThis.getResources().getColor(nbc, aThis.getTheme()));
            fragmentLayout.setBackgroundColor(aThis.getResources().getColor(fbc, aThis.getTheme()));
        }
        if (fThis != null) {
            navigationView.setItemTextColor(fThis.getResources().getColorStateList(itc, fThis.getTheme()));
            navigationView.setBackgroundColor(fThis.getResources().getColor(R.color.transparent, fThis.getTheme()));
            navigationLayout.setBackgroundColor(fThis.getResources().getColor(nbc, fThis.getTheme()));
            fragmentLayout.setBackgroundColor(fThis.getResources().getColor(fbc, fThis.getTheme()));
        }
        adjustNavigationIcoSize(navigationView);
        navigationView.setItemHorizontalTranslationEnabled(true);
        navigationView.setOnItemSelectedListener(selectedListener);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        fragments.forEach(fragment -> {
            if (fragment != fragments.get(0)) {
                sf = switchFragment(fragment);
                if (sf != null) sf.commit();
            }
        });
        sf = switchFragment(fragments.get(0));
        if (sf != null) sf.commit();
    }

    /**
     * 加载布局
     */
    private FragmentTransaction switchFragment(Fragment targetFragment) {
        try {
            FragmentTransaction transaction;
            if (aThis != null) {
                transaction = aThis.getSupportFragmentManager().beginTransaction();
                if (!targetFragment.isAdded()) {
                    if (cf != null) transaction.hide(cf);
                    transaction.add(fragmentLayout.getId(), targetFragment, targetFragment.getClass().getName());
                } else {
                    transaction.hide(cf).show(targetFragment);
                }
                cf = targetFragment;
                return transaction;
            } else if (fThis != null) {
                transaction = fThis.getSupportFragmentManager().beginTransaction();
                if (!targetFragment.isAdded()) {
                    if (cf != null) transaction.hide(cf);
                    transaction.add(fragmentLayout.getId(), targetFragment, targetFragment.getClass().getName());
                } else {
                    transaction.hide(cf).show(targetFragment);
                }
                cf = targetFragment;
                return transaction;
            } else {
                return null;
            }
        } catch (Exception e) {
            Log.e("switchFragment异常", Log.getStackTraceString(e));
            return null;
        }
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
                if (fThis != null) {
                    DisplayMetrics displayMetrics = fThis.getResources().getDisplayMetrics();
                    layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, space[0], displayMetrics);
                    layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, space[1], displayMetrics);
                    iconView.setLayoutParams(layoutParams);
                }
            }
        } catch (Exception e) {
            Log.e("NavigationBar", String.valueOf(e.getMessage()), e);
        }
    }

    private NavigationBar(NewBuilder builder) {
        if (builder.obj instanceof AppCompatActivity) {
            this.aThis = (AppCompatActivity) builder.obj;
            this.navigationView = aThis.findViewById(R.id.nav_view);
            this.navigationLayout = aThis.findViewById(R.id.nav_layout);
            this.fragmentLayout = aThis.findViewById(R.id.nav_frame);
        } else if (builder.obj instanceof FragmentActivity) {
            this.fThis = (FragmentActivity) builder.obj;
            this.navigationView = fThis.findViewById(R.id.nav_view);
            this.navigationLayout = fThis.findViewById(R.id.nav_layout);
            this.fragmentLayout = fThis.findViewById(R.id.nav_frame);
        } else {
            Log.e("异常", "请填写正确activity对象");
        }
        this.menu = builder.menu;
        this.cf = new Fragment();
        this.fragments = new ArrayList<>(builder.fragments);
        this.items = builder.items;
        this.fbc = builder.frameBackgroundColor;
        this.nbc = builder.navigationBackgroundColor;
        this.itc = builder.navItemTextColor;
        this.space = builder.space;
        initView();
        initData();
    }

    public static final class NewBuilder {

        private final Object obj;
        private final NewBuilder builder;
        private int menu = R.menu.nav_menu;
        private int navItemTextColor = R.drawable.pagination_selector_tab_color;
        private int frameBackgroundColor = R.color.transparent;
        private int navigationBackgroundColor = R.color.transparent;
        private final List<Fragment> fragments = new LinkedList<>();
        private int[] items;
        private int[] space = {25, 25};

        /**
         * @param obj activity对象
         */
        private NewBuilder(@NonNull Object obj) {
            this.obj = obj;
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
         * 设置添加菜单条目
         */
        public NewBuilder setAddMenuItem(@IdRes int... item) {
            items = item;
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
        public NewBuilder setItemTextColor(@DrawableRes int itemTextColor) {
            this.navItemTextColor = itemTextColor;
            return builder;
        }

        /**
         * 设置底部导航栏背景颜色
         */
        public NewBuilder setBackgroundColor(@ColorRes int backgroundColor) {
            this.navigationBackgroundColor = backgroundColor;
            return builder;
        }

        /**
         * 设置背景颜色
         */
        public NewBuilder setFrameBackgroundColor(@ColorRes int frameBackgroundColor) {
            this.frameBackgroundColor = frameBackgroundColor;
            return builder;
        }

        public NewBuilder setSpace(@IntRange(from = 0, to = 30) int... space) {
            this.space = space;
            return builder;
        }

        @NotNull
        @Contract(" -> new")
        @SuppressWarnings("UnusedReturnValue")
        public NavigationBar build() {
            synchronized (NavigationBar.class) {
                return new NavigationBar(builder);
            }
        }

    }

    @NotNull
    @Contract("_ -> new")
    public static NewBuilder builder(Object aThis) {
        synchronized (NewBuilder.class) {
            return new NewBuilder(aThis);
        }
    }

}
