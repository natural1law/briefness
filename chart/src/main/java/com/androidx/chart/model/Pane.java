package com.androidx.chart.model;

public class Pane {
    public Background background;
    public Object[] center;
    public Float endAngle;
    public Float size;
    public Float startAngle;

    public Pane background(Background prop) {
        background = prop;
        return this;
    }

    public Pane center(Object[] prop) {
        center = prop;
        return this;
    }

    public Pane endAngle(Float prop) {
        endAngle = prop;
        return this;
    }

    public Pane size(Float prop) {
        size = prop;
        return this;
    }

    public Pane startAngle(Float prop) {
        startAngle = prop;
        return this;
    }
}
