package com.androidx.briefness.homepage.activity;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.androidx.briefness.R;
import com.androidx.briefness.base.BaseActivity;
import com.androidx.briefness.homepage.adapter.PageAdapter;
import com.androidx.http.use.Rn;
import com.androidx.reduce.tools.Idle;
import com.androidx.view.page.PaginationRecycleView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

@SuppressLint("NonConstantResourceId")
public class PageRecyclerViewActivity extends BaseActivity {

    private final AppCompatActivity aThis = this;
    @BindView(R.id.title_layout)
    public FrameLayout titleLayout;
    @BindView(R.id.title_return_image)
    public AppCompatImageView imageView;
    @BindView(R.id.title_text)
    public AppCompatTextView titleView;
    @BindView(R.id.listview)
    public PaginationRecycleView listView;
    private Unbinder unbinder;

    /**
     * @param str 32位小写MD5
     */
    private static String MD5(String str) {
        String reStr = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(str.getBytes());
            StringBuilder stringBuffer = new StringBuilder();
            for (byte b : bytes) {
                int bt = b & 0xff;
                if (bt < 16) {
                    stringBuffer.append(0);
                }
                stringBuffer.append(Integer.toHexString(bt));
            }
            reStr = stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return reStr;
    }

    @Override
    protected void onCreate() {
        setContentView(R.layout.activity_pagerecyclerview);
        unbinder = ButterKnife.bind(aThis);
        titleLayout.setBackgroundColor(getResources().getColor(R.color.gray, getTheme()));
        titleView.setTextColor(getResources().getColor(R.color.black1, getTheme()));
        imageView.setVisibility(View.VISIBLE);
        imageView.setColorFilter(R.color.black);
        titleView.setText(getIntent().getStringExtra(getResources().getString(R.string.title)));
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
        unbinder.unbind();
    }

    @OnClick(R.id.title_return_image)
    public void imageReturn() {
        if (Idle.isClick()) finish();
    }

    private void initView() {
        listView.setAdapterAndManager(new PageAdapter(), new LinearLayoutManager(aThis));
        indifference(1);
        listView.setListener(this::indifference);
    }

    private void indifference(int lot) {
        Map<String, Object> param = new WeakHashMap<>();
        String uid = "aa216858cbd80fd21931d1bcd170f4a4";
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String var = "ca2882680429c6549f0ed4c047c20778" + 1009 + lot + "" + uid + timestamp;
        param.put("j", 1009);//接口id
        param.put("p", lot);
        param.put("b", "");
        param.put("n", "");
        param.put("s", "");
        param.put("c", "");
        param.put("a", "");
        param.put("v", "");
        param.put("u", uid);
        param.put("t", timestamp);
        param.put("k", MD5(var));
        Rn.sendMapPost("http://hapi1.syyfkj.cn/app/v1_1/", param, bakeData -> {
            if (listView != null) listView.loadingFinish();
            JsonObject bakeJson = new Gson().fromJson(bakeData, new TypeToken<JsonObject>() {
            }.getType());
            if (bakeJson.get("c").getAsInt() == 0) {
                List<JsonObject> jsonList = new Gson().fromJson(bakeJson.get("d"), new TypeToken<List<JsonObject>>() {
                }.getType());
                listView.addItem(lot, jsonList, bakeJson.get("t").getAsInt());
                Log.i("数据", String.valueOf(bakeJson.get("t").getAsInt()));
            }
        });
    }

    private void attention(int lot) {
        Map<String, Object> param = new WeakHashMap<>();
        String uid = "aa216858cbd80fd21931d1bcd170f4a4";
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String var = "ca2882680429c6549f0ed4c047c20778" + 1013 + lot + "" + uid + timestamp;
        param.put("j", 1013);//接口id
        param.put("p", lot);
        param.put("b", "");
        param.put("n", "");
        param.put("s", "");
        param.put("c", "");
        param.put("a", "");
        param.put("v", "");
        param.put("u", uid);
        param.put("t", timestamp);
        param.put("k", MD5(var));
        Rn.sendMapPost("http://hapi1.syyfkj.cn/app/v1_1/", param, bakeData -> {
            if (listView != null) listView.loadingFinish();
            JsonObject bakeJson = new Gson().fromJson(bakeData, new TypeToken<JsonObject>() {
            }.getType());
            Log.i("数据1", String.valueOf(bakeJson));
            if (bakeJson.get("c").getAsInt() == 0) {
                List<JsonObject> jsonList = new Gson().fromJson(bakeJson.get("d"), new TypeToken<List<JsonObject>>() {
                }.getType());
                listView.addItem(lot, jsonList, bakeJson.get("t").getAsInt());

            }
        });
    }

    public void aaa(View view) {
        indifference(1);
        listView.setListener(this::indifference);
    }

    public void bbb(View view) {
        attention(1);
        listView.setListener(this::attention);
    }
}
