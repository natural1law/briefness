package com.androidx.chart.model;

public class Background {
    public Object backgroundColor;
    public String borderColor;
    public Float borderWidth;
    public String className;
    public Float innerRadius;
    public Float outerRadius;
    public String shape;

    public Background backgroundColor(Object prop) {
        backgroundColor = prop;
        return this;
    }
    public Background borderColor(String prop) {
        borderColor = prop;
        return this;
    }

    public Background borderWidth(Float prop) {
        borderWidth = prop;
        return this;
    }

    public Background className(String prop) {
        className = prop;
        return this;
    }

    public Background innerRadius(Float prop) {
        innerRadius = prop;
        return this;
    }

    public Background outerRadius(Float prop) {
        outerRadius = prop;
        return this;
    }

    public Background shape(String prop) {
        shape = prop;
        return this;
    }

}
