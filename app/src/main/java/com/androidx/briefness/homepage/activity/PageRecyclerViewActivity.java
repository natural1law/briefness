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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.androidx.briefness.R;
import com.androidx.briefness.homepage.adapter.PageAdapter;
import com.androidx.http.use.NetRequest;
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
public class PageRecyclerViewActivity extends AppCompatActivity {

    @BindView(R.id.title_layout)
    public FrameLayout titleLayout;
    @BindView(R.id.title_return_image)
    public AppCompatImageView imageView;
    @BindView(R.id.title_text)
    public AppCompatTextView titleView;
    @BindView(R.id.listview)
    public PaginationRecycleView listView;

    private Unbinder unbinder;
    private final AppCompatActivity aThis = this;

    @SuppressLint("SetTextI18n")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        listView.setListener(this::indifference);
        indifference(1);
    }


    private void indifference(int lot) {
        Map<String, Object> param = new WeakHashMap<>();
        String uid = "326b3b25158fda1099249fa1a9c2016d";
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
        NetRequest.sendMapPost("http://hapi1.syyfkj.cn/app/v1_1/", param, bakeData -> {
            if (listView != null) listView.loadingFinish();
            JsonObject bakeJson = new Gson().fromJson(bakeData, new TypeToken<JsonObject>() {
            }.getType());
            if (bakeJson.get("c").getAsInt() == 0) {
                if (listView != null)
                    listView.setDataTotalSize(Integer.parseInt(bakeJson.get("t").toString()));
                List<JsonObject> jsonList = new Gson().fromJson(bakeJson.get("d"), new TypeToken<List<JsonObject>>() {
                }.getType());
                listView.addItem(lot, jsonList);
                Log.i("数据", String.valueOf(jsonList));
            }
        });
    }

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
}
