package com.androidx.chart.model;

public class MarkerHover {

    public Boolean enabled;
    public String fillColor;
    public String lineColor;
    public Float lineWidth;
    public Float lineWidthPlus;
    public Float radius;
    public Float radiusPlus;

    public MarkerHover enabled(Boolean prop) {
        enabled = prop;
        return this;
    }

    public MarkerHover fillColor(String prop) {
        fillColor = prop;
        return this;
    }

    public MarkerHover lineColor(String prop) {
        lineColor = prop;
        return this;
    }

    public MarkerHover lineWidth(Float prop) {
        lineWidth = prop;
        return this;
    }

    public MarkerHover lineWidthPlus(Float prop) {
        lineWidthPlus = prop;
        return this;
    }

    public MarkerHover radius(Float prop) {
        radius = prop;
        return this;
    }

    public MarkerHover radiusPlus(Float prop) {
        radiusPlus = prop;
        return this;
    }

}
