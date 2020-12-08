package com.androidx.chart.model;

import com.androidx.chart.tools.JSStringPurer;

public class Tooltip {
    public String backgroundColor;
    public String borderColor;
    public Float borderRadius;
    public Float borderWidth;
    public Style style;
    public Boolean enabled;
    public Boolean useHTML;
    public String formatter;
    public String headerFormat;
    public String pointFormat;
    public String footerFormat;
    public Integer valueDecimals;
    public Boolean shared;
    public Boolean crosshairs;
    public String valueSuffix;

    public Tooltip backgroundColor(String prop) {
        backgroundColor = prop;
        return this;
    }

    public Tooltip borderColor(String prop) {
        borderColor = prop;
        return this;
    }

    public Tooltip borderRadius(Float prop) {
        borderRadius = prop;
        return this;
    }

    public Tooltip borderWidth(Float prop) {
        borderWidth = prop;
        return this;
    }

    public Tooltip style(Style prop) {
        style = prop;
        return this;
    }

    public Tooltip enabled(Boolean prop) {
        enabled = prop;
        return this;
    }

    public Tooltip useHTML(Boolean prop) {
        useHTML = prop;
        return this;
    }

    public Tooltip formatter(String prop) {
        String pureJSFunctionStr = "(" + prop + ")";
        pureJSFunctionStr = JSStringPurer.pureJavaScriptFunctionString(pureJSFunctionStr);
        formatter = pureJSFunctionStr;
        return this;
    }

    public Tooltip headerFormat(String prop) {
        headerFormat = prop;
        return this;
    }

    public Tooltip pointFormat(String prop) {
        pointFormat = prop;
        return this;
    }

    public Tooltip footerFormat(String prop) {
        footerFormat = prop;
        return this;
    }

    public Tooltip valueDecimals(Integer prop) {
        valueDecimals = prop;
        return this;
    }

    public Tooltip shared(Boolean prop) {
        shared = prop;
        return this;
    }

    public Tooltip crosshairs(Boolean prop) {
        crosshairs = prop;
        return this;
    }

    public Tooltip valueSuffix(String prop) {
        valueSuffix = prop;
        return this;
    }

    public Tooltip() {
        enabled = true;
        shared = true;
        crosshairs = true;
    }


    
  
}
