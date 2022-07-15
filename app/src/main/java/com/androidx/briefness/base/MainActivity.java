package com.androidx.briefness.base;

import android.annotation.SuppressLint;

import com.androidx.briefness.R;
import com.androidx.briefness.base.fragment.HomePageFrag;
import com.androidx.briefness.base.fragment.MyPageFrag;
import com.androidx.view.bar.NavigationBar;

@SuppressLint("NonConstantResourceId")
public class MainActivity extends BaseActivity {

    private final MainActivity aThis = this;

    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate() {
        //底部导航栏
        NavigationBar.builder(aThis)
                .setMenu(R.menu.nav_menu_default)
                .setAddFragment(new HomePageFrag())
                .setAddFragment(new MyPageFrag())
                .setItemTextColor(R.drawable.nav_item)
                .setBackgroundDrawable(R.drawable.bg_theme)
                .setLazy(true)
                .build();
    }

}