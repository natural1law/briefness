package com.androidx.chart.model;

public class Crosshair {
    public Float width;
    public String color;
    public String dashStyle;

    public Crosshair width(Float prop) {
        width = prop;
        return this;
    }

    public Crosshair color(String prop) {
        color = prop;
        return this;
    }

    public Crosshair dashStyle(String prop) {
        dashStyle = prop;
        return this;
    }
}
