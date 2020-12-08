package com.androidx.chart.model;

public class DataElement {
    public String name;
    public Float y;
    public Object color;
    public DataLabels dataLabels;
    public Marker marker;

    public DataElement name(String prop) {
        name = prop;
        return this;
    }

    public DataElement y(Float prop) {
        y = prop;
        return this;
    }

    public DataElement color(Object prop) {
        color = prop;
        return this;
    }

    public DataElement dataLabels(DataLabels prop) {
        dataLabels = prop;
        return this;
    }

    public DataElement marker(Marker prop) {
        marker = prop;
        return this;
    }
}
