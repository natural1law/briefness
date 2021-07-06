package com.androidx.briefness.homepage.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.androidx.briefness.R;
import com.androidx.briefness.homepage.fragment.tab.CommonFragment;
import com.androidx.briefness.homepage.fragment.tab.SegmentFragment;
import com.androidx.briefness.homepage.fragment.tab.SlidingFragment;
import com.androidx.http.net.listener.ActionListener;
import com.androidx.http.net.listener.Enqueue;
import com.androidx.http.use.NetRequest;
import com.androidx.reduce.tools.Idle;
import com.androidx.view.tab.layout.SegmentTabLayout;
import com.androidx.view.tab.use.TabLayoutBar;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Tab导航栏功能展示
 *
 * @date 2021/07/02
 */
@SuppressLint("NonConstantResourceId")
public class TabActivity extends AppCompatActivity {

    @BindView(R.id.title_layout)
    public FrameLayout titleLayout;
    @BindView(R.id.title_return_image)
    public AppCompatImageView imageView;
    @BindView(R.id.title_text)
    public AppCompatTextView titleView;

    @BindView(R.id.sliding)
    public SegmentTabLayout segmentTabLayout;
    @BindView(R.id.vp2)
    public ViewPager2 viewPager2;

    private Unbinder unbinder;
    private TabLayoutBar tabView;
    private final TabActivity aThis = this;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_tab);
            unbinder = ButterKnife.bind(aThis);
            initView();
        } catch (Exception e) {
            Log.e("Tab导航异常", e.getMessage(), e);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            imageView.performClick();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        tabView.destroy();
    }

    @OnClick(R.id.title_return_image)
    public void imageReturn() {
        if (Idle.isClick()) finishAfterTransition();
    }

    @SuppressLint("SetTextI18n")
    private void initView() {
        titleLayout.setBackgroundColor(getResources().getColor(R.color.gray, getTheme()));
        titleView.setTextColor(getResources().getColor(R.color.black1, getTheme()));
        imageView.setVisibility(View.VISIBLE);
        imageView.setColorFilter(R.color.black);
        titleView.setText(getIntent().getStringExtra(getResources().getString(R.string.title)));

        tabView = TabLayoutBar.builder()
                .setActivity(aThis)
                .setViewPager2(viewPager2)
                .setTabLayout(segmentTabLayout)
                .setTitles("Common", "Sliding", "Segment")
                .setFragments(new CommonFragment(), new SlidingFragment(), new SegmentFragment())
                .initBuild();

        tabView.execute();

        Map<String, Object> map = new ConcurrentHashMap<>();
        map.put("user", "张三");
        map.put("pass", "123123");
        map.put("code", "111111");
        Enqueue enqueue = NetRequest.initWebSocket("http://192.168.1.133:9966/android/control/", map);
        enqueue.start();
        enqueue.setActionListener(new ActionListener() {
            @Override
            public void online(String user) {
                Log.i("在线", user);
            }

            @Override
            public void offline(String user) {
                Log.i("离线", user);
            }
        });
        enqueue.send(0, "123".getBytes());
        enqueue.setLoginCallback(() -> Log.i("登录", "成功"));
        enqueue.setMsgCallback((type, msg, bs) -> {
            if (type == 0) {
                Log.i("消息", msg);
            }
        });
        enqueue.close();

    }

    public static class OneFragment extends Fragment {

    }

    public static class TowFragment extends Fragment {

    }

    public static class ThreeFragment extends Fragment {

    }

}
