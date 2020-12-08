package com.androidx.chart.model;

public class Position {
    public String align;
    public String verticalAlign;
    public Number x;
    public Number y;

    public Position align(String prop) {
        align = prop;
        return this;
    }

    public Position verticalAlign(String prop) {
        verticalAlign = prop;
        return this;
    }

    public Position align(Number prop) {
        x = prop;
        return this;
    }

    public Position y(Number prop) {
        y = prop;
        return this;
    }

}
