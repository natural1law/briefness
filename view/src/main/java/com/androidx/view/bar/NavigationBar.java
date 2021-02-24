package com.androidx.view.bar;

import android.annotation.SuppressLint;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.MenuRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;

import com.androidx.view.R;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 底部导航栏
 *
 * @author 李玄道
 * @date 2020/06/29
 */
@SuppressWarnings("ALL")
public final class NavigationBar {

    private int menu;
    private List<Integer> menuIds;
    private int frameBackgroundColor;
    private int navBackgroundColor;
    private int navItemTextColor;

    private AppCompatActivity aThis;
    private Fragment currentFragment;//当前碎片

    private List<Fragment> fragments;

    public BottomNavigationView navigationView;
    private LinearLayoutCompat navigationLayout;
    private FragmentContainerView fragmentLayout;

    private NavigationBar(NewBuilder builder) {
        try {
            this.aThis = builder.aThis;
            this.currentFragment = new Fragment();
            this.fragments = builder.fragments;
            this.menu = builder.menu;
            this.menuIds = builder.menuIds;
            this.navItemTextColor = builder.navItemTextColor;
            this.frameBackgroundColor = builder.frameBackgroundColor;
            this.navBackgroundColor = builder.navigationBackgroundColor;
            this.navigationView = aThis.findViewById(R.id.navigation_view);
            this.navigationLayout = aThis.findViewById(R.id.navigation_layout);
            this.fragmentLayout = aThis.findViewById(R.id.standard_frame);
            initView();
            adjustNavigationIcoSize(navigationView);
            for (Fragment fragment : fragments) {
                fragmentManager(fragment);
            }
        } catch (Exception e) {
            Log.e("NavigationBar", String.valueOf(e.getMessage()));
        }
    }

    @SuppressLint("ResourceType")
    private void initView() {
        navigationView.inflateMenu(menu);
        navigationView.setItemIconTintList(null);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            navigationView.setItemTextColor(aThis.getResources().getColorStateList(navItemTextColor, aThis.getTheme()));
            navigationView.setBackgroundColor(aThis.getResources().getColor(R.color.transparent, aThis.getTheme()));
            navigationLayout.setBackgroundColor(aThis.getResources().getColor(navBackgroundColor, aThis.getTheme()));
            fragmentLayout.setBackgroundColor(aThis.getResources().getColor(frameBackgroundColor, aThis.getTheme()));
        } else {
            navigationView.setItemTextColor(aThis.getResources().getColorStateList(navItemTextColor));
            navigationView.setBackgroundColor(aThis.getResources().getColor(R.color.transparent));
            navigationLayout.setBackgroundColor(aThis.getResources().getColor(navBackgroundColor));
            fragmentLayout.setBackgroundColor(aThis.getResources().getColor(frameBackgroundColor));
        }
        navigationView.setOnNavigationItemSelectedListener(selectedListener);
    }

    private void fragmentManager(Fragment fragment) {
        try {
            FragmentTransaction transaction = aThis.getSupportFragmentManager().beginTransaction();
            if (!fragment.isAdded()) {
                transaction.hide(currentFragment).add(R.id.standard_frame, fragment).commit();
            } else {
                transaction.hide(currentFragment).show(fragment).commit();
            }
            currentFragment = fragment;
        } catch (Exception e) {
            Log.e("NavigationBar", String.valueOf(e.getMessage()));
        }
    }

    /**
     * 底部导航栏按钮间距字体调整
     */
    private void adjustNavigationIcoSize(BottomNavigationView navigation) {
        try {
            BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigation.getChildAt(0);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                View iconView = menuView.getChildAt(i).findViewById(R.id.icon);
                ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
                DisplayMetrics displayMetrics = aThis.getResources().getDisplayMetrics();
                layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25, displayMetrics);
                layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25, displayMetrics);
                iconView.setLayoutParams(layoutParams);
            }
        } catch (Exception e) {
            Log.e("NavigationBar", String.valueOf(e.getMessage()));
        }
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener selectedListener = menuItem -> {
        int itemId = menuItem.getItemId();
        if (fragments.size() == 2) {
            if (itemId == menuIds.get(0)) {
                fragmentManager(fragments.get(0));
                return true;
            } else if (itemId == menuIds.get(1)) {
                fragmentManager(fragments.get(1));
                return true;
            } else {
                return false;
            }
        } else if (fragments.size() == 3) {
            if (itemId == menuIds.get(0)) {
                fragmentManager(fragments.get(0));
                return true;
            } else if (itemId == menuIds.get(1)) {
                fragmentManager(fragments.get(1));
                return true;
            } else if (itemId == menuIds.get(2)) {
                fragmentManager(fragments.get(2));
                return true;
            } else {
                return false;
            }
        } else if (fragments.size() == 4) {
            if (itemId == menuIds.get(0)) {
                fragmentManager(fragments.get(0));
                return true;
            } else if (itemId == menuIds.get(1)) {
                fragmentManager(fragments.get(1));
                return true;
            } else if (itemId == menuIds.get(2)) {
                fragmentManager(fragments.get(2));
                return true;
            } else if (itemId == menuIds.get(3)) {
                fragmentManager(fragments.get(3));
                return true;
            } else {
                return false;
            }
        } else if (fragments.size() == 5) {
            if (itemId == menuIds.get(0)) {
                fragmentManager(fragments.get(0));
                return true;
            } else if (itemId == menuIds.get(1)) {
                fragmentManager(fragments.get(1));
                return true;
            } else if (itemId == menuIds.get(2)) {
                fragmentManager(fragments.get(2));
                return true;
            } else if (itemId == menuIds.get(3)) {
                fragmentManager(fragments.get(3));
                return true;
            } else if (itemId == menuIds.get(4)) {
                fragmentManager(fragments.get(4));
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    };

    public static final class NewBuilder {

        private final NewBuilder builder;
        private final AppCompatActivity aThis;
        private final List<Fragment> fragments = new ArrayList<>();
        private int menu = R.menu.nav_menu_default;
        private final List<Integer> menuIds = new ArrayList<>();
        private int navItemTextColor = R.drawable.pagination_selector_tab_color;
        private int navigationBackgroundColor = R.color.transparent;
        private int frameBackgroundColor = R.color.transparent;

        private NewBuilder(@NonNull AppCompatActivity obj) {
            this.aThis = obj;
            this.builder = this;
        }

        public NewBuilder setMenu(@MenuRes int menu) {
            this.menu = menu;
            return builder;
        }

        public NewBuilder setMenuIds(@IdRes Integer... menuIds) {
            //默认值
            Integer[] ids = {R.id.second, R.id.thirdly, R.id.fourthly, R.id.first};
            this.menuIds.addAll(Arrays.asList(ids));
            if (menuIds.length < 2) {
                Log.e("NavigationBar", "menuIds长度不能小于2");
                return builder;
            } else if (menuIds.length > 5) {
                Log.e("NavigationBar", "menuIds长度不能大于5");
                return builder;
            } else {
                this.menuIds.clear();
                this.menuIds.addAll(Arrays.asList(menuIds));
                return builder;
            }
        }

        public NewBuilder setItemTextColor(@DrawableRes int itemTextColor) {
            this.navItemTextColor = itemTextColor;
            return builder;
        }

        public NewBuilder setBackgroundColor(@ColorRes int backgroundColor) {
            this.navigationBackgroundColor = backgroundColor;
            return builder;
        }

        public NewBuilder setFrameBackgroundColor(@ColorRes int frameBackgroundColor) {
            this.frameBackgroundColor = frameBackgroundColor;
            return builder;
        }

        public NewBuilder setFragments(@NonNull Fragment... fragment) {
            if (fragment.length < 2) {
                Log.e("NavigationBar", "fragment长度不能小于2");
                return builder;
            } else if (fragment.length > 5) {
                Log.e("NavigationBar", "fragment长度不能大于5");
                return builder;
            } else {
                this.fragments.addAll(Arrays.asList(fragment));
                return builder;
            }
        }

        public NavigationBar build() {
            synchronized (NavigationBar.class) {
                return new NavigationBar(builder);
            }
        }
    }

    public static NewBuilder builder(AppCompatActivity aThis) {
        synchronized (NewBuilder.class) {
            return new NewBuilder(aThis);
        }
    }

}
