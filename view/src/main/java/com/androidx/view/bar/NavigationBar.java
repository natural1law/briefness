package com.androidx.view.bar;

import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;

import com.androidx.view.R;
import com.androidx.view.bar.fragment.FragmentsFirst;
import com.androidx.view.bar.fragment.FragmentsFourthly;
import com.androidx.view.bar.fragment.FragmentsSecond;
import com.androidx.view.bar.fragment.FragmentsThirdly;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * 底部导航栏
 *
 * @author 李玄道
 * @date 2020/06/29
 */
@SuppressWarnings("ALL")
public final class NavigationBar {

    private int frameBackgroundColor;
    private int navigationBackgroundColor;

    private AppCompatActivity aThis;
    private Fragment currentFragment;//当前碎片

    private FragmentsFirst fragmentsFirst;
    private FragmentsSecond fragmentsSecond;
    private FragmentsThirdly fragmentsThirdly;
    private FragmentsFourthly fragmentsFourthly;

    public BottomNavigationView navigationView;
    private LinearLayoutCompat navigationLayout;
    private FragmentContainerView fragmentLayout;

    private NavigationBar(NewBuilder builder) {
        try {
            this.aThis = builder.aThis;
            this.currentFragment = new Fragment();
            this.fragmentsFirst = builder.fragmentsFirst;
            this.fragmentsSecond = builder.fragmentsSecond;
            this.fragmentsThirdly = builder.fragmentsThirdly;
            this.fragmentsFourthly = builder.fragmentsFourthly;
            this.frameBackgroundColor = builder.frameBackgroundColor;
            this.navigationBackgroundColor = builder.navigationBackgroundColor;
            this.navigationView = aThis.findViewById(R.id.navigation_view);
            this.navigationLayout = aThis.findViewById(R.id.navigation_layout);
            this.fragmentLayout = aThis.findViewById(R.id.standard_frame);
            initView();
            fragmentManager(fragmentsFourthly);
            fragmentManager(fragmentsThirdly);
            fragmentManager(fragmentsSecond);
            fragmentManager(fragmentsFirst);
            adjustNavigationIcoSize(navigationView);
        } catch (Exception e) {
            Log.e("BottomNavigation异常", String.valueOf(e.getMessage()));
        }
    }

    private void initView() {
        navigationView.setItemIconTintList(null);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            navigationView.setBackgroundColor(aThis.getResources().getColor(R.color.transparent, aThis.getTheme()));
            navigationLayout.setBackgroundColor(aThis.getResources().getColor(navigationBackgroundColor, aThis.getTheme()));
            fragmentLayout.setBackgroundColor(aThis.getResources().getColor(frameBackgroundColor, aThis.getTheme()));
        } else {
            navigationView.setBackgroundColor(aThis.getResources().getColor(R.color.transparent));
            navigationLayout.setBackgroundColor(aThis.getResources().getColor(navigationBackgroundColor));
            fragmentLayout.setBackgroundColor(aThis.getResources().getColor(frameBackgroundColor));
        }
        navigationView.setOnNavigationItemSelectedListener(selectedListener);

//        navigationView.inflateMenu();
//        navigationView.setItemIconTintList(aThis.getResources().getColorStateList(R.drawable.selector_tab_color, aThis.getTheme()));
        navigationView.setItemTextColor(aThis.getResources().getColorStateList(R.drawable.pagination_selector_tab_color, aThis.getTheme()));
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
            Log.e("2", String.valueOf(e.getMessage()));
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
            Log.e("1", String.valueOf(e.getMessage()));
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener = menuItem -> {
        int itemId = menuItem.getItemId();
        if (itemId == R.id.first) {
            fragmentManager(fragmentsFirst);
            return true;
        } else if (itemId == R.id.second) {
            fragmentManager(fragmentsSecond);
            return true;
        } else if (itemId == R.id.thirdly) {
            fragmentManager(fragmentsThirdly);
            return true;
        } else if (itemId == R.id.fourthly) {
            fragmentManager(fragmentsFourthly);
            return true;
        } else {
            return false;
        }
    };

    public static final class NewBuilder {

        private static volatile NavigationBar instance;
        private NewBuilder builder;
        private AppCompatActivity aThis;
        private FragmentsFirst fragmentsFirst;
        private FragmentsSecond fragmentsSecond;
        private FragmentsThirdly fragmentsThirdly;
        private FragmentsFourthly fragmentsFourthly;
        private int navigationBackgroundColor = R.color.transparent;
        private int frameBackgroundColor = R.color.transparent;

        private NewBuilder(@NonNull AppCompatActivity obj) {
            this.aThis = obj;
            this.builder = this;
        }

        public NewBuilder setBackgroundColor(@ColorRes int backgroundColor) {
            this.navigationBackgroundColor = backgroundColor;
            return builder;
        }

        public NewBuilder setFrameBackgroundColor(@ColorRes int frameBackgroundColor) {
            this.frameBackgroundColor = frameBackgroundColor;
            return builder;
        }

        public NewBuilder setFragmentsFirst(FragmentsFirst fragmentsFirst) {
            this.fragmentsFirst = fragmentsFirst;
            return builder;
        }

        public NewBuilder setFragmentsSecond(FragmentsSecond fragmentsSecond) {
            this.fragmentsSecond = fragmentsSecond;
            return builder;
        }

        public NewBuilder setFragmentsThirdly(FragmentsThirdly fragmentsThirdly) {
            this.fragmentsThirdly = fragmentsThirdly;
            return builder;
        }

        public NewBuilder setFragmentsFourthly(FragmentsFourthly fragmentsFourthly) {
            this.fragmentsFourthly = fragmentsFourthly;
            return builder;
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
