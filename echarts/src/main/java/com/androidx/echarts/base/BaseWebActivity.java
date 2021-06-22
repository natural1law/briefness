package com.androidx.echarts.base;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.just.agentweb.AgentWeb;
import com.just.agentweb.WebChromeClient;
import com.just.agentweb.WebViewClient;

import org.json.JSONObject;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static com.just.agentweb.AgentWeb.SecurityType.STRICT_CHECK;
import static com.just.agentweb.DefaultWebClient.OpenOtherPageWays.ASK;

public abstract class BaseWebActivity extends AppCompatActivity {

    private String way;
    private JSONObject json;
    public AgentWeb agentWeb;
    private OnLoadListener listener;
    private static final String URL = "file:///android_asset/";

    private final Handler handler = new Handler(Looper.getMainLooper(), msg -> {
        if (msg.what == 0) json = (JSONObject) msg.obj;
        return false;
    });

    private final WebViewClient wvc = new WebViewClient() {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            handler.sendMessage(listener.onLoad());
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            try {
                super.onPageFinished(view, url);
                if (json == null) return;
                agentWeb.getJsAccessEntrace().callJs("javascript:" + way + "(" + json + ")");
            } catch (Exception e) {
                Log.e("JS参数异常", e.getMessage(), e);
            }
        }

    };

    private final WebChromeClient wcc = new WebChromeClient() {
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            return super.onJsAlert(view, url, message, result);
        }

        @Override
        public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
            return super.onJsConfirm(view, url, message, result);
        }

        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
            return super.onJsPrompt(view, url, message, defaultValue, result);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 初始化布局
     *
     * @param view 传入AgentWeb 的父控件
     */
    @SuppressLint("SetJavaScriptEnabled")
    public <T extends ViewGroup> void initWeb(T view) {
        initWeb(view, 0);
    }

    /**
     * 初始化布局
     *
     * @param view 传入AgentWeb 的父控件
     * @param pb   进度条宽度
     */
    @SuppressLint("SetJavaScriptEnabled")
    public <T extends ViewGroup> void initWeb(T view, int pb) {
        initWeb(view, pb, null, null);
    }

    /**
     * 初始化布局
     *
     * @param view 传入AgentWeb 的父控件
     * @param pb   进度条宽度
     */
    @SuppressLint("SetJavaScriptEnabled")
    public <T extends ViewGroup> void initWeb(T view, int pb, WebChromeClient wcc) {
        initWeb(view, pb, wcc, null);
    }

    /**
     * 初始化布局
     *
     * @param view 传入AgentWeb 的父控件
     * @param pb   进度条宽度
     */
    @SuppressLint("SetJavaScriptEnabled")
    public <T extends ViewGroup> void initWeb(T view, int pb, WebViewClient wvc) {
        initWeb(view, pb, null, wvc);
    }

    /**
     * 初始化布局
     *
     * @param view 传入AgentWeb 的父控件
     */
    @SuppressLint("SetJavaScriptEnabled")
    public <T extends ViewGroup> void initWeb(T view, int pb, WebChromeClient wcc, WebViewClient wvc) {
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
    }


    /**
     * 开始加载
     */
    public void startLoad(String suffix) {
        agentWeb.getUrlLoader().loadUrl(URL + suffix);
    }

    /**
     * 重新加载
     */
    public void reload() {
        agentWeb.getUrlLoader().reload();
    }

    /**
     * 停止加载
     */
    public void stopLoad() {
        agentWeb.getUrlLoader().stopLoading();
    }

    /**
     * 加载事件
     */
    public void setListener(OnLoadListener listener) {
        this.listener = listener;
    }

    /**
     * 设置加载JS中的方法
     */
    public void setWay(String way) {
        this.way = way;
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

    @Override
    public void onDestroy() {
        if (agentWeb != null) agentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }

    public interface OnLoadListener {
        Message onLoad();
    }
}
