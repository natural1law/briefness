package com.androidx.chart.model;

import com.androidx.chart.creator.SeriesElement;

public class Waterfall extends SeriesElement {
    public String upColor;
    public String color;
//    public Float borderWidth;
    public Object[] data;

    public Waterfall upColor(String prop) {
        upColor = prop;
        return this;
    }

    public Waterfall color(String prop) {
        color = prop;
        return this;
    }

//    public AAWaterfall borderWidth(Float prop) {
//        borderWidth = prop;
//        return this;
//    }

    public Waterfall data(Object[] prop) {
        data = prop;
        return this;
    }
}
