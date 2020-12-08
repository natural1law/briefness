package com.androidx.chart.model;

import com.androidx.chart.enums.ChartVerticalAlignType;

public class Subtitle {
    public String text;
    public Style style;
    public String align;
    public String verticalAlign;
    public Float x;
    public Float y;
    public Boolean userHTML;

    public Subtitle text(String prop) {
        text = prop;
        return this;
    }

    public Subtitle style(Style prop) {
        style = prop;
        return this;
    }

    public Subtitle align(String prop) {
        align = prop;
        return this;
    }

    public Subtitle verticalAlign(ChartVerticalAlignType prop) {
        verticalAlign = prop.toString();
        return this;
    }

    public Subtitle x(Float prop) {
        x = prop;
        return this;
    }

    public Subtitle y(Float prop) {
        y = prop;
        return this;
    }

    public Subtitle userHTML(Boolean prop) {
        userHTML = prop;
        return this;
    }
}
