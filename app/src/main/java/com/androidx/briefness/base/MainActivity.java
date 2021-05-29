package com.androidx.briefness.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.androidx.briefness.R;
import com.androidx.briefness.base.fragment.HomePageFrag;
import com.androidx.briefness.base.fragment.MyPageFrag;
import com.androidx.view.bar.NavigationBar;

import butterknife.ButterKnife;
import butterknife.Unbinder;

@SuppressLint("NonConstantResourceId")
public class MainActivity extends FragmentActivity {

    private Unbinder unbinder;
    private final FragmentActivity aThis = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(aThis);
        try {
            //底部导航栏
            NavigationBar.builder(aThis)
                    .setMenu(R.menu.nav_menu_default)
                    .setAddFragment(new HomePageFrag())
                    .setAddFragment(new MyPageFrag())
                    .setAddMenuItem(R.id.first, R.id.second)
                    .setBackgroundColor(R.color.gray)
                    .build();
        } catch (Exception e) {
            Log.e("MainActivity异常", e.getMessage(), e);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

}