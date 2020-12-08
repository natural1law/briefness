package com.androidx.chart.model;

public class PlotLinesElement {

    public Object color;
    public String dashStyle;
    public Float width;
    public Float value;
    public Integer zIndex;
    public Label label;

    public PlotLinesElement color(Object prop) {
        color = prop;
        return this;
    }

    public PlotLinesElement dashStyle(String prop) {
        dashStyle = prop;
        return this;
    }

    public PlotLinesElement width(Float prop) {
        width = prop;
        return this;
    }

    public PlotLinesElement value(Float prop) {
        value = prop;
        return this;
    }

    public PlotLinesElement zIndex(Integer prop) {
        zIndex = prop;
        return this;
    }

    public PlotLinesElement label(Label prop) {
        label = prop;
        return this;
    }



}






