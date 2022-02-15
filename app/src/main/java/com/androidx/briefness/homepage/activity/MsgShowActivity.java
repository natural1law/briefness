package com.androidx.briefness.homepage.activity;

import static com.androidx.briefness.base.App.appThis;
import static com.androidx.briefness.base.App.toasts;
import static com.androidx.reduce.tools.Convert.Timestamp.getTime;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.androidx.briefness.R;
import com.androidx.briefness.base.BaseActivity;
import com.androidx.reduce.tools.Idle;
import com.androidx.reduce.tools.This;
import com.bear.screenshot.ScreenShotTools;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @date 2021/04/30
 */
@SuppressLint("NonConstantResourceId")
public final class MsgShowActivity extends BaseActivity {

    private final AppCompatActivity aThis = this;
    @BindView(R.id.title_layout)
    public FrameLayout titleLayout;
    @BindView(R.id.title_return_image)
    public AppCompatImageView imageView;
    @BindView(R.id.title_text)
    public AppCompatTextView titleView;
    @BindView(R.id.show_msg_layout)
    public ConstraintLayout layoutView;

    private Unbinder unbinder;
    private ActivityResultLauncher<Intent> launcher;

    @Override
    protected int layoutId() {
        return R.layout.activity_msg_show;
    }

    @Override
    protected void onCreate() {
        unbinder = ButterKnife.bind(aThis);
        titleLayout.setBackgroundColor(getResources().getColor(R.color.gray, getTheme()));
        titleView.setTextColor(getResources().getColor(R.color.black1, getTheme()));
        imageView.setVisibility(View.VISIBLE);
        imageView.setColorFilter(R.color.black);
        titleView.setText(getIntent().getStringExtra(getResources().getString(R.string.title)));

        launcher = This.initLauncher(aThis, (resultCode, intent) -> {
//            toasts.i("回调码", resultCode);
//            toasts.i("回调数据", intent.getStringExtra("haha"));

            This.resultListener(aThis, intent, data -> {
                File file = new File(data);
                toasts.i("回调数据", file.isFile());
            });
        });

//        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
//            Log.i("数据", String.valueOf(result.getData().getStringExtra("haha")));
//        });

        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.title_return_image)
    public void imageReturn() {
        if (Idle.isClick()) finish();
    }

    private void initView() {
        toasts.i("当前日期", getTime(1643425433140L));
//        //MediaStore.Images.Media.EXTERNAL_CONTENT_URI
//        ContentResolver cr = getContentResolver();
//        ContentValues values = new ContentValues();
//        String path = Environment.DIRECTORY_PICTURES + "/*";
//        values.put(MediaStore.Images.Media.ALBUM, path);
//        values.put(MediaStore.Images.Media.TITLE, path);
//        Uri uri = cr.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//        toasts.i("地址", uri);
//        Intent picture = new Intent(Intent.ACTION_PICK, uri);//ACTION_VIEW ACTION_PICK
//        startActivityForResult(picture, 0);
//
//        Intent intent = new Intent("android.intent.action.GET_CONTENT");
//        intent.setType("image/picture/*");
//        startActivityForResult(intent, 0); //打开相册

//        appThis.startLauncher(RefreshActivity.class, launcher).execute();

//        launcher.launch(ScreenShotActivity.createIntent(aThis, null, 2000));

        appThis.delay(() -> {
            ScreenShotTools.Companion.getInstance().takeCapture(this, layoutView, screenBitmap -> {
                assert screenBitmap != null;
                toasts.i("数据", screenBitmap.getFilePath());
                appThis.resultAction(launcher).start();
            });
        }, 2000);

    }

}