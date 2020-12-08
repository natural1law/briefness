package com.androidx.chart.model;

public class Columnrange {
    public Float borderRadius;
    public Float borderWidth;
    public DataLabels dataLabels;

    public Columnrange borderRadius(Float prop) {
        borderRadius = prop;
        return this;
    }

    public Columnrange borderWidth(Float prop) {
        borderWidth = prop;
        return this;
    }

    public Columnrange dataLabels(DataLabels prop) {
        dataLabels = prop;
        return this;
    }
}
