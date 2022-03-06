package com.androidx.briefness.homepage.activity;

import static com.google.android.material.transition.MaterialContainerTransform.FADE_MODE_THROUGH;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.androidx.briefness.R;
import com.androidx.briefness.base.BaseActivity;
import com.androidx.reduce.tools.Idle;
import com.androidx.reduce.tools.Toasts;
import com.google.android.material.transition.platform.MaterialContainerTransform;
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @date 2022/02/09
 */
@SuppressLint("NonConstantResourceId")
public final class DragViewActivity extends BaseActivity {

    private final DragViewActivity aThis = this;
    @BindView(R.id.title_layout)
    public FrameLayout titleLayout;
    @BindView(R.id.title_return_image)
    public AppCompatImageView imageView;
    @BindView(R.id.title_text)
    public AppCompatTextView titleView;
    @BindView(R.id.l0)
    public LinearLayoutCompat l0View;
    private float moveX, moveY;

    public static void onWillCreateNewActivity(Activity activity, Intent intent) {
        View view = activity.findViewById(android.R.id.content);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity, view, "sharedElementName");
        intent.putExtras(options.toBundle());
        activity.startActivity(intent);
    }

    @SuppressLint("WrongConstant")
    public static void onNewActivityCreated(Activity act) {
        View view = act.findViewById(android.R.id.content);
        view.setTransitionName("sharedElementName");
        act.setEnterSharedElementCallback(new MaterialContainerTransformSharedElementCallback());
        MaterialContainerTransform materialContainerTransform = new MaterialContainerTransform();//fromFragment.requireContext());
        materialContainerTransform.setContainerColor(act.getResources().getColor(R.color.black, act.getTheme()));
        materialContainerTransform.setFadeMode(FADE_MODE_THROUGH);
        materialContainerTransform.addTarget(android.R.id.content);
        materialContainerTransform.setDuration(600);
        act.getWindow().setSharedElementEnterTransition(materialContainerTransform);
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_dragview;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate() {
        setTitle(titleLayout, imageView, titleView);
    }

    @OnClick(R.id.title_return_image)
    public void imageReturn() {
        if (Idle.isClick()) finish();
    }

    @OnClick(R.id.a1)
    public void a1() {
        try {
            onNewActivityCreated(aThis);
        } catch (Exception e) {
            Toasts.e(getClass().getName(), e);
        }
    }

}
