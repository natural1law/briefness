package com.androidx.chart.creator;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.androidx.chart.model.Options;
import com.androidx.chart.tools.JSStringPurer;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ChartView extends WebView {

    public interface AAChartViewCallBack {
        void chartViewDidFinishLoad(ChartView chartView);

        void chartViewMoveOverEventMessage(
                ChartView chartView,
                MOEMessageModel messageModel
        );
    }

    public Float contentWidth;
    public Float contentHeight;
    public Boolean chartSeriesHidden;
    public Boolean isClearBackgroundColor;
    public AAChartViewCallBack callBack;

    public void setContentWidth(Float contentWidth) {
        this.contentWidth = contentWidth;
        String jsStr = "setTheChartViewContentWidth('" + this.contentWidth + "')";
        safeEvaluateJavaScriptString(jsStr);
    }

    public void setContentHeight(Float contentHeight) {
        this.contentHeight = contentHeight;
        String jsStr = "setTheChartViewContentHeight('" + this.contentHeight + "')";
        safeEvaluateJavaScriptString(jsStr);
    }

    public void setChartSeriesHidden(Boolean chartSeriesHidden) {
        this.chartSeriesHidden = chartSeriesHidden;
        String jsStr = "setChartSeriesHidden('" + this.chartSeriesHidden + "')";
        safeEvaluateJavaScriptString(jsStr);
    }

    public void setIsClearBackgroundColor(Boolean isClearBackgroundColor) {
        this.isClearBackgroundColor = isClearBackgroundColor;
        if (this.isClearBackgroundColor) {
            this.setBackgroundColor(0);
            this.getBackground().setAlpha(0);
        } else {
            this.setBackgroundColor(1);
            this.getBackground().setAlpha(255);
        }

    }


    private String optionsJson;

    public ChartView(Context context) {
        super(context);
        setupBasicContent();
    }

    public ChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupBasicContent();
    }

    public ChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupBasicContent();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setupBasicContent() {
        // Do some initialize work.
        this.contentWidth = 420f;
        this.contentHeight = 580f;
        this.isClearBackgroundColor = false;
        this.getSettings().setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setWebContentsDebuggingEnabled(true);
        }
        //把当前对象作为androidObject别名传递给js
        //js通过window.androidObject.androidMethod()就可以直接调用安卓的androidMethod方法
        this.addJavascriptInterface(this, "androidObject");
    }


    //js调用安卓，必须加@JavascriptInterface注释的方法才可以被js调用
    @JavascriptInterface
    public String androidMethod(String message) {
        Gson gson = new Gson();
        Map<String, Object> messageBody = new HashMap<>();
        messageBody = gson.fromJson(message, messageBody.getClass());
        MOEMessageModel eventMessageModel = getEventMessageModel(messageBody);
        if (callBack != null) {
            callBack.chartViewMoveOverEventMessage(this, eventMessageModel);
        }
//       Log.i("androidMethod","++++++++++++++++显示总共调用了几次");
        return "";
    }


    public void chartModel(final ChartModel chartModel) {
        Options options = OptionsConstructor.configureChartOptions(chartModel);
        this.chartOptions(options);
    }

    public void refreshModel(ChartModel chartModel) {
        Options options = OptionsConstructor.configureChartOptions(chartModel);
        this.refreshOptions(options);
    }

    public void chartOptions(final Options chartOptions) {
        if (this.optionsJson != null) {
            this.refreshOptions(chartOptions);
        } else {
            this.loadLocalFilesAndDrawChart(chartOptions);
            this.showJavaScriptAlertView();
        }
    }

    public void refreshOptions(Options chartOptions) {
        configureChartOptionsAndDrawChart(chartOptions);
    }


    public void onlyRefreshSeriesArray(SeriesElement[] seriesElementsArr) {
        onlyRefreshOptionsSeriesArray(seriesElementsArr, true);
    }

    public void onlyRefreshOptionsSeriesArray(SeriesElement[] seriesElementsArr, Boolean animation) {
        String seriesArr = new Gson().toJson(seriesElementsArr);
        String javaScriptStr = "onlyRefreshTheChartDataWithSeries('" + seriesArr + "','" + animation + "')";
        this.safeEvaluateJavaScriptString(javaScriptStr);
    }

    public void addSeriesElement(Integer elementIndex, Object options) {
        addPointSeriesElement(elementIndex, options, true);
    }

    public void addPointSeriesElement(Integer elementIndex, Object options, Boolean shift) {
        addPointSeriesElement(elementIndex, options, true, shift, true);
    }


    public void addPointSeriesElement(
            Integer elementIndex,
            Object options,
            Boolean redraw,
            Boolean shift,
            Boolean animation) {
        String optionsStr;
        if (options instanceof Integer || options instanceof Float || options instanceof Double) {
            optionsStr = String.valueOf(options);
        } else {
            optionsStr = new Gson().toJson(options);
        }

        String javaScriptStr = "addPointToChartSeries('"
                + elementIndex + "','"
                + optionsStr + "','"
                + redraw + "','"
                + shift + "','"
                + animation + "')";
        this.safeEvaluateJavaScriptString(javaScriptStr);
    }

    public void showSeriesElementContent(Integer elementIndex) {
        String javaScriptStr = "showTheSeriesElementContentWithIndex('" + elementIndex + "')";
        this.safeEvaluateJavaScriptString(javaScriptStr);
    }

    public void hideSeriesElementContent(Integer elementIndex) {
        String javaScriptStr = "hideTheSeriesElementContentWithIndex('" + elementIndex + "')";
        this.safeEvaluateJavaScriptString(javaScriptStr);
    }

    public void addElementSeries(SeriesElement seriesElement) {
        String pureElementJsonStr = new Gson().toJson(seriesElement);
        String javaScriptStr = "addElementToChartSeriesWithElement('" + pureElementJsonStr + "')";
        this.safeEvaluateJavaScriptString(javaScriptStr);
    }

    public void removeElementFromSeries(Integer elementIndex) {
        String javaScriptStr = "removeElementFromChartSeriesWithElementIndex('" + elementIndex + "')";
        this.safeEvaluateJavaScriptString(javaScriptStr);
    }

    public void evaluateTheJavaScriptStringFunction(String jsFunctionStr) {
        String pureJSFunctionStr = JSStringPurer.pureJavaScriptFunctionString(jsFunctionStr);
        String jsFunctionNameStr = "evaluateTheJavaScriptStringFunction('" + pureJSFunctionStr + "')";
        safeEvaluateJavaScriptString(jsFunctionNameStr);
    }


    private void loadLocalFilesAndDrawChart(final Options options) {
        this.loadUrl("file:///android_assets/ChartView.html");
        this.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                configureChartOptionsAndDrawChart(options);
                if (callBack != null) {
                    callBack.chartViewDidFinishLoad(ChartView.this);
                }
            }
        });
    }

    private void configureChartOptionsAndDrawChart(Options chartOptions) {
        if (isClearBackgroundColor) {
            chartOptions.chart.backgroundColor("rgba(0,0,0,0)");
        }
        Gson gson = new Gson();
        String aaOptionsJsonStr = gson.toJson(chartOptions);
        this.optionsJson = aaOptionsJsonStr;
        String javaScriptStr = "loadTheHighChartView('"
                + aaOptionsJsonStr + "','"
                + this.contentWidth + "','"
                + this.contentHeight + "')";
        this.safeEvaluateJavaScriptString(javaScriptStr);
    }

    private void showJavaScriptAlertView() {
        this.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view,
                                     String url,
                                     String message,
                                     final JsResult result) {
                super.onJsAlert(view, url, message, result);

                String urlStr = "url --->" + url + "\n\n\n";
                String messageStr = "message --->" + message + "\n\n\n";
                String resultStr = "result --->" + result;

                String alertMessageStr = urlStr + messageStr + resultStr;

                new AlertDialog.Builder(getContext())
                        .setTitle("JavaScript alert Information")//设置对话框标题
                        .setMessage(alertMessageStr)
                        .setNeutralButton("sure", null)
                        .show();

                return true;
            }
        });
    }

    private MOEMessageModel getEventMessageModel(Map<String, Object> messageBody) {
        MOEMessageModel eventMessageModel = new MOEMessageModel();
        eventMessageModel.name = Objects.requireNonNull(messageBody.get("name")).toString();
        eventMessageModel.x = (Double) messageBody.get("x");
        eventMessageModel.y = (Double) messageBody.get("y");
        eventMessageModel.category = Objects.requireNonNull(messageBody.get("category")).toString();
        eventMessageModel.offset = (LinkedTreeMap) messageBody.get("offset");
        Double index = (Double) messageBody.get("index");
        eventMessageModel.index = index != null ? index.intValue() : 0;
        return eventMessageModel;
    }


    private void safeEvaluateJavaScriptString(String javaScriptString) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            this.evaluateJavascript("javascript:" + javaScriptString, s -> {
//                Log.i("call back information", "输出打印查看回调的结果" + s);
            });
        } else {
            this.loadUrl("javascript:" + javaScriptString);
        }
    }


}
