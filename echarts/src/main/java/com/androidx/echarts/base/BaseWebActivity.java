package com.androidx.echarts.base;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.WebChromeClient;
import com.just.agentweb.WebViewClient;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static com.just.agentweb.AgentWeb.SecurityType.STRICT_CHECK;
import static com.just.agentweb.DefaultWebClient.OpenOtherPageWays.ASK;

public abstract class BaseWebActivity extends AppCompatActivity {

    public AgentWeb agentWeb;
    private ViewGroup viewGroup;
    private OnLoadListener loadListener;
    private static final String URL = "file:///android_asset/";

    private final WebViewClient wvc = new WebViewClient() {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            viewGroup.setVisibility(View.GONE);
            loadListener.onLoad();
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            viewGroup.setVisibility(View.VISIBLE);
        }

    };

    private final WebChromeClient wcc = new WebChromeClient() {
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            Log.i("alert信息", message);
            return super.onJsAlert(view, url, message, result);
        }

        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
            Log.i("prompt信息", message);
            return super.onJsPrompt(view, url, message, defaultValue, result);
        }

        @Override
        public boolean onConsoleMessage(ConsoleMessage cm) {
            Log.i("console" + cm.sourceId(), cm.message());
            return super.onConsoleMessage(cm);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            Log.i("prompt信息", String.valueOf(newProgress));
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        if (agentWeb != null) agentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onPause() {
        if (agentWeb != null) agentWeb.getWebLifeCycle().onPause();
        super.onPause();
    }

    @Override
    public void onResume() {
        if (agentWeb != null) agentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (agentWeb != null) {
            if (agentWeb.handleKeyEvent(keyCode, event)) {
                return true;
            } else {
                return super.onKeyDown(keyCode, event);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 初始化布局
     *
     * @param view 传入AgentWeb 的父控件
     */
    @SuppressLint("SetJavaScriptEnabled")
    public <T extends ViewGroup> BaseWebActivity initWeb(T view) {
        initWeb(view, -1);
        return this;
    }

    /**
     * 初始化布局
     *
     * @param view 传入AgentWeb 的父控件
     * @param pb   进度条宽度
     */
    @SuppressLint("SetJavaScriptEnabled")
    public <T extends ViewGroup> BaseWebActivity initWeb(T view, int pb) {
        initWeb(view, pb, null, null);
        return this;
    }

    /**
     * 初始化布局
     *
     * @param view 传入AgentWeb 的父控件
     * @param pb   进度条宽度
     */
    @SuppressLint("SetJavaScriptEnabled")
    public <T extends ViewGroup> BaseWebActivity initWeb(T view, int pb, WebChromeClient wcc) {
        initWeb(view, pb, wcc, null);
        return this;
    }

    /**
     * 初始化布局
     *
     * @param view 传入AgentWeb 的父控件
     * @param pb   进度条宽度
     */
    @SuppressLint("SetJavaScriptEnabled")
    public <T extends ViewGroup> BaseWebActivity initWeb(T view, int pb, WebViewClient wvc) {
        initWeb(view, pb, null, wvc);
        return this;
    }

    /**
     * 初始化布局
     *
     * @param view 传入AgentWeb 的父控件
     */
    @SuppressLint("SetJavaScriptEnabled")
    public <T extends ViewGroup> BaseWebActivity initWeb(T view, int pb, WebChromeClient wcc, WebViewClient wvc) {
        this.viewGroup = view;
        agentWeb = AgentWeb.with(this)
                .setAgentWebParent(view, new ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT))//传入AgentWeb 的父控件 ，如果父控件为 RelativeLayout ， 那么第二参数需要传入 RelativeLayout.LayoutParams ,第一个参数和第二个参数应该对应。
                .useDefaultIndicator(MATCH_PARENT, pb)// 使用默认进度条
                .setSecurityType(STRICT_CHECK)
                .setOpenOtherPageWays(ASK)
                .interceptUnkownUrl()
                .setWebChromeClient(wcc == null ? this.wcc : wcc)
                .setWebViewClient(wvc == null ? this.wvc : wvc)
                .createAgentWeb()
                .ready()
                .get();
        WebSettings setting = agentWeb.getAgentWebSettings().getWebSettings();
        setting.setJavaScriptEnabled(true);
        setting.setJavaScriptCanOpenWindowsAutomatically(true);
        setting.setSupportZoom(false);
        setting.setDisplayZoomControls(false);
        return this;
    }

    /**
     * 开始加载
     */
    public void setStart(String suffix) {
        agentWeb.getUrlLoader().loadUrl(URL + suffix);
    }

    /**
     * 开始加载
     */
    public void setStart(String url, String suffix) {
        if (url == null || suffix == null) Log.e("开始加载", "参数为空");
        agentWeb.getUrlLoader().loadUrl(url + suffix);
    }

    /**
     * 重新加载
     */
    public void setReload() {
        agentWeb.getUrlLoader().reload();
    }

    /**
     * 停止加载
     */
    public void setStop() {
        agentWeb.getUrlLoader().stopLoading();
    }

    /**
     * 加载事件
     */
    public void setLoadListener(OnLoadListener listener) {
        this.loadListener = listener;
    }

    /**
     * 设置加载JS中的方法
     *
     * @param way  js中的方法名（函数名）
     * @param data 数据
     */
    public <J> void setCallJs(String way, J data) {
        JsonObject json = new Gson().fromJson(String.valueOf(data), new TypeToken<JsonObject>() {
        }.getType());
        agentWeb.getJsAccessEntrace().callJs("javascript:" + way + "(" + json + ")");
    }

    public interface OnLoadListener {
        void onLoad();
    }
}
