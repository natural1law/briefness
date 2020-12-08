package com.androidx.chart.model;

public class Shadow {
    public String color;
    public Float offsetX;
    public Float offsetY;
    public Float opacity;
    public Float width;

    public Shadow color(String prop) {
        color = prop;
        return this;
    }

    public Shadow offsetX(Float prop) {
        offsetX = prop;
        return this;
    }

    public Shadow offsetY(Float prop) {
        offsetY = prop;
        return this;
    }

    public Shadow opacity(Float prop) {
        opacity = prop;
        return this;
    }

    public Shadow width(Float prop) {
        width = prop;
        return this;
    }

}
