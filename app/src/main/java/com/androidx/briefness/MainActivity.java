package com.androidx.briefness;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.androidx.briefness.frag.HomePageFrag;
import com.androidx.briefness.frag.MyPageFrag;
import com.androidx.view.bar.NavigationBar;

import butterknife.ButterKnife;
import butterknife.Unbinder;

@SuppressLint("NonConstantResourceId")
public class MainActivity extends AppCompatActivity {

    private Unbinder unbinder;
    private final AppCompatActivity aThis = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        unbinder = ButterKnife.bind(aThis);
        try {
            initData();
            initView();
        } catch (Exception e) {
            Log.e("异常", e.getMessage(), e);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void initView() {
        NavigationBar.builder(aThis)
                .setMenu(R.menu.nav_menu_default)
                .setAddFragment(new HomePageFrag())
                .setAddFragment(new MyPageFrag())
                .setAddMenuItem(R.id.first, R.id.second)
                .setBackgroundColor(R.color.gray)
                .build();
    }

    private void initData() {

    }
}