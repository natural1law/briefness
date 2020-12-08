package com.androidx.chart.model;

public class PlotBandsElement {
    public Float from;
    public Float to;
    public Object color;
    public String borderColor;
    public Float borderWidth;
    public String className;
    public Label label;
    public Integer zIndex;

    public PlotBandsElement from(Float prop) {
        from = prop;
        return this;
    }

    public PlotBandsElement to(Float prop) {
        to = prop;
        return this;
    }

    public PlotBandsElement color(Object prop) {
        color = prop;
        return this;
    }

    public PlotBandsElement borderColor(String prop) {
        borderColor = prop;
        return this;
    }

    public PlotBandsElement borderWidth(Float prop) {
        borderWidth = prop;
        return this;
    }

    public PlotBandsElement className(String prop) {
        className = prop;
        return this;
    }

    public PlotBandsElement label(Label prop) {
        label = prop;
        return this;
    }

    public PlotBandsElement zIndex(Integer prop) {
        zIndex = prop;
        return this;
    }

}
