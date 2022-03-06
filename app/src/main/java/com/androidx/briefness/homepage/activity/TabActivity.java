package com.androidx.briefness.homepage.activity;

import android.annotation.SuppressLint;
import android.view.KeyEvent;
import android.widget.FrameLayout;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.androidx.briefness.R;
import com.androidx.briefness.base.BaseActivity;
import com.androidx.briefness.homepage.fragment.tab.CommonFragment;
import com.androidx.briefness.homepage.fragment.tab.SegmentFragment;
import com.androidx.briefness.homepage.fragment.tab.SlidingFragment;
import com.androidx.reduce.tools.Idle;
import com.androidx.view.tab.layout.SegmentTabLayout;
import com.androidx.view.tab.use.TabLayoutBar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Tab导航栏功能展示
 *
 * @date 2021/07/02
 */
@SuppressLint("NonConstantResourceId")
public class TabActivity extends BaseActivity {

    private final TabActivity aThis = this;
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

    private TabLayoutBar tabView;

    @Override
    protected int layoutId() {
        return R.layout.activity_tab;
    }

    @Override
    protected void onCreate() {
        initView();
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
        tabView.destroy();
    }

    @OnClick(R.id.title_return_image)
    public void imageReturn() {
        if (Idle.isClick()) finishAfterTransition();
    }

    @SuppressLint("SetTextI18n")
    private void initView() {
        setTitle(titleLayout, imageView, titleView);
        tabView = TabLayoutBar.builder()
                .setActivity(aThis)
                .setViewPager2(viewPager2)
                .setTabLayout(segmentTabLayout)
                .setTitles("Common", "Sliding", "Segment")
                .setFragments(new CommonFragment(), new SlidingFragment(), new SegmentFragment())
                .initBuild();
        tabView.execute();

    }

    public static class OneFragment extends Fragment {

    }

    public static class TowFragment extends Fragment {

    }

    public static class ThreeFragment extends Fragment {

    }

}
