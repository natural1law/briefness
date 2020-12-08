package com.androidx.chart.model;

import com.androidx.chart.enums.ChartAlignType;
import com.androidx.chart.enums.ChartVerticalAlignType;

public class Title {
    public String text;
    public Style style;
    public String align;
    public String verticalAlign;
    public Float x;
    public Float y;
    public Boolean userHTML;

    public Title text(String prop) {
        text = prop;
        return this;
    }

    public Title style(Style prop) {
        style = prop;
        return this;
    }

    public Title align(ChartAlignType prop) {
        align = prop.toString();
        return this;
    }

    public Title verticalAlign(ChartVerticalAlignType prop) {
        verticalAlign = prop.toString();
        return this;
    }

    public Title x(Float prop) {
        x = prop;
        return this;
    }

    public Title y(Float prop) {
        y = prop;
        return this;
    }

    public Title userHTML(Boolean prop) {
        userHTML = prop;
        return this;
    }
}
