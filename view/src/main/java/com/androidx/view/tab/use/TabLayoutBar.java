package com.androidx.view.tab.use;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.androidx.view.tab.layout.CommonTabLayout;
import com.androidx.view.tab.layout.SegmentTabLayout;
import com.androidx.view.tab.layout.SlidingTabLayout;
import com.androidx.view.tab.listener.CustomTabEntity;
import com.androidx.view.tab.listener.OnTabSelectListener;
import com.androidx.view.tab.moudle.TabEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("ALL")
public class TabLayoutBar implements Parcelable {

    private AppCompatActivity activity;
    private FragmentActivity fragmentActivity;
    private final String[] titles;//标题
    private Fragment[] fragments;
    private final int showDot;//显示消息未读红点*
    private ViewPager2 viewPager;
    private final int initCurrentItem;//初始化当前位置显示
    private final int dotColor;//红点颜色*
    private final String dotColorStr;//红点颜色*
    private SegmentTabLayout segmentTabLayout;
    private CommonTabLayout commonTabLayout;
    private SlidingTabLayout slidingTabLayout;
    private int containerViewId;

    private final List<CustomTabEntity> tabEntities = new ArrayList<>();
    private final ArrayList<Fragment> fragmentList = new ArrayList<>();

    private final ViewPager2.OnPageChangeCallback changeCallback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            if (segmentTabLayout != null) {
                segmentTabLayout.setCurrentTab(position);
            } else if (commonTabLayout != null) {
                commonTabLayout.setCurrentTab(position);
            } else if (slidingTabLayout != null) {
                slidingTabLayout.setCurrentTab(position);
                slidingTabLayout.scrollToCurrentTab();
                slidingTabLayout.invalidate();
            }
        }
    };

    private final OnTabSelectListener selectListener = new OnTabSelectListener() {
        @Override
        public void onTabSelect(int position) {
            viewPager.setCurrentItem(position);
        }

        @Override
        public void onTabReselect(int position) {

        }
    };

    public void execute() {
        if (segmentTabLayout != null && viewPager != null) {
            segmentTabLayout.setTabData(titles);
            viewPager.registerOnPageChangeCallback(changeCallback);
            viewPager.setCurrentItem(initCurrentItem);
            if (activity != null) {
                viewPagerAdapter(activity);
            } else if (fragmentActivity != null) {
                viewPagerAdapter(fragmentActivity);
            }
            segmentTabLayout.setOnTabSelectListener(selectListener);
        } else if (slidingTabLayout != null && viewPager != null) {
            slidingTabLayout.setTabData(titles);
            viewPager.registerOnPageChangeCallback(changeCallback);
            viewPager.setCurrentItem(initCurrentItem);
            if (activity != null) {
                viewPagerAdapter(activity);
            } else if (fragmentActivity != null) {
                viewPagerAdapter(fragmentActivity);
            }
            slidingTabLayout.setOnTabSelectListener(selectListener);
        } else if (commonTabLayout != null && viewPager != null && containerViewId == 0) {
            if (!tabEntities.isEmpty()) {
                commonTabLayout.setTabData(tabEntities);
                viewPager.registerOnPageChangeCallback(changeCallback);
                viewPager.setCurrentItem(initCurrentItem);
                if (activity != null) {
                    viewPagerAdapter(activity);
                } else if (fragmentActivity != null) {
                    viewPagerAdapter(fragmentActivity);
                }
                commonTabLayout.setOnTabSelectListener(selectListener);
            } else {
                Log.e("commonTabLayout异常", "tabEntities data can not be empty");
            }
        } else if (commonTabLayout != null) {
            fragmentList.addAll(Arrays.asList(fragments));
            if (fragmentActivity != null) {
                commonTabLayout.setTabData(tabEntities, fragmentActivity, containerViewId, fragmentList);
            } else if (activity != null) {
                commonTabLayout.setTabData(tabEntities, activity, containerViewId, fragmentList);
            }
        }
    }

    public void destroy() {
        if (viewPager != null) {
            viewPager.unregisterOnPageChangeCallback(changeCallback);
        }
    }

    private void viewPagerAdapter(Object obj) {
        if (obj instanceof AppCompatActivity) {
            viewPager.setAdapter(new FragmentStateAdapter(activity.getSupportFragmentManager(), activity.getLifecycle()) {
                @NonNull
                @Override
                public Fragment createFragment(int position) {
                    return fragments[position];
                }

                @Override
                public int getItemCount() {
                    return fragments.length;
                }
            });
        } else if (obj instanceof FragmentActivity) {
            viewPager.setAdapter(new FragmentStateAdapter(fragmentActivity.getSupportFragmentManager(), fragmentActivity.getLifecycle()) {
                @NonNull
                @Override
                public Fragment createFragment(int position) {
                    return fragments[position];
                }

                @Override
                public int getItemCount() {
                    return fragments.length;
                }
            });
        }
    }

    public static Builder builder() {
        try {
            synchronized (Builder.class) {
                return new Builder();
            }
        } catch (Exception e) {
            return new Builder();
        }
    }

    private TabLayoutBar(Builder builder) {
        if (builder.activity instanceof AppCompatActivity) {
            this.activity = (AppCompatActivity) builder.activity;
        } else if (builder.activity instanceof FragmentActivity) {
            this.fragmentActivity = (FragmentActivity) builder.activity;
        }
        this.titles = builder.titles;
        this.fragments = builder.fragments;
        this.showDot = builder.showDot;
        this.viewPager = builder.viewPager;
        this.initCurrentItem = builder.initCurrentItem;
        this.dotColor = builder.dotColor;
        this.dotColorStr = builder.dotColorStr;
        this.containerViewId = builder.containerViewId;
        tabEntities.clear();
        if (builder.pitch_onIcon != null && builder.unselectedIcon != null && builder.titles != null) {
            for (int i = 0; i < titles.length; i++) {
                tabEntities.add(TabEntity.init(titles[i], builder.pitch_onIcon[i], builder.unselectedIcon[i]));
            }
        }
        if (builder.tabLayout instanceof SegmentTabLayout) {
            segmentTabLayout = (SegmentTabLayout) builder.tabLayout;
        } else if (builder.tabLayout instanceof CommonTabLayout) {
            commonTabLayout = (CommonTabLayout) builder.tabLayout;
        } else if (builder.tabLayout instanceof SlidingTabLayout) {
            slidingTabLayout = (SlidingTabLayout) builder.tabLayout;
        } else {
            Log.e("TabUser", "tabLayoutView null");
        }
    }

    public static final Creator<TabLayoutBar> CREATOR = new Creator<TabLayoutBar>() {
        @Override
        public TabLayoutBar createFromParcel(Parcel in) {
            return new TabLayoutBar(in);
        }

        @Override
        public TabLayoutBar[] newArray(int size) {
            return new TabLayoutBar[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    private TabLayoutBar(Parcel in) {
        titles = in.createStringArray();
        showDot = in.readInt();
        initCurrentItem = in.readInt();
        dotColor = in.readInt();
        dotColorStr = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(titles);
        dest.writeInt(showDot);
        dest.writeInt(initCurrentItem);
        dest.writeInt(dotColor);
        dest.writeString(dotColorStr);
    }

    public static final class Builder implements Parcelable {

        private final Builder builder;
        private Object activity;
        private String[] titles;//标题
        private Fragment[] fragments;
        private int showDot;//显示消息未读红点
        private ViewPager2 viewPager;
        private int initCurrentItem;//初始化当前位置显示
        private int dotColor;//红点颜色
        private String dotColorStr;//红点颜色
        private Object tabLayout;
        private int[] unselectedIcon;//未选中的图片
        private int[] pitch_onIcon;//选中图片
        private int containerViewId;

        protected Builder(Parcel in) {
            builder = in.readParcelable(Builder.class.getClassLoader());
            titles = in.createStringArray();
            showDot = in.readInt();
            initCurrentItem = in.readInt();
            dotColor = in.readInt();
            dotColorStr = in.readString();
        }

        public static final Creator<Builder> CREATOR = new Creator<Builder>() {
            @Override
            public Builder createFromParcel(Parcel in) {
                return new Builder(in);
            }

            @Override
            public Builder[] newArray(int size) {
                return new Builder[size];
            }
        };

        public Builder setActivity(Object activity) {
            if (activity == null) {
                Log.e("参数异常", "activity can not be empty");
                return builder;
            }
            this.activity = activity;
            return builder;
        }

        public Builder setTitles(String... titles) {
            if (titles == null) {
                Log.e("参数异常", "titles can not be empty");
                return builder;
            }
            this.titles = titles;
            return builder;
        }

        public Builder setFragments(Fragment... fragments) {
            if (fragments == null) {
                Log.e("参数异常", "fragments can not be empty");
                return builder;
            }
            this.fragments = fragments;
            return builder;
        }

        public Builder setShowDot(int showDot) {
            this.showDot = showDot;
            return builder;
        }

        public Builder setViewPager2(ViewPager2 viewPager2) {
            if (viewPager2 == null) {
                Log.e("参数异常", "viewPager2 can not be empty");
                return builder;
            }
            this.viewPager = viewPager2;
            return builder;
        }

        public Builder setInitCurrentItem(int initCurrentItem) {
            this.initCurrentItem = initCurrentItem;
            return builder;
        }

        public Builder setDotColor(int dotColor) {
            this.dotColor = dotColor;
            return builder;
        }

        public Builder setDotColorStr(String dotColorStr) {
            this.dotColorStr = dotColorStr;
            return builder;
        }

        public Builder setTabLayout(Object tabLayout) {
            if (tabLayout == null) {
                Log.e("参数异常", "tabLayout can not be empty");
                return builder;
            }
            this.tabLayout = tabLayout;
            return builder;
        }

        public Builder setUnselectedIcon(int[] unselectedIcon) {
            this.unselectedIcon = unselectedIcon;
            return builder;
        }

        public Builder setPitchOnIcon(int[] pitch_onIcon) {
            this.pitch_onIcon = pitch_onIcon;
            return builder;
        }

        public Builder setContainerViewId(@LayoutRes int containerViewId) {
            this.containerViewId = containerViewId;
            return builder;
        }

        private Builder() {
            this.builder = this;
        }

        public TabLayoutBar initBuild() {
            try {
                synchronized (TabLayoutBar.class) {
                    return new TabLayoutBar(builder);
                }
            } catch (Exception e) {
                return new TabLayoutBar(builder);
            }
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(builder, flags);
            dest.writeStringArray(titles);
            dest.writeInt(showDot);
            dest.writeInt(initCurrentItem);
            dest.writeInt(dotColor);
            dest.writeString(dotColorStr);
        }
    }

}
