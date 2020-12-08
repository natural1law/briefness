package com.androidx.chart.model;

public class Label {
    public String text;
    public Object style;

    public Label text(String prop) {
        text = prop;
        return this;
    }

    public Label style(Object prop) {
        style = prop;
        return this;
    }
}