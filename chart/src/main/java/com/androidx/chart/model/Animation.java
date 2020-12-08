package com.androidx.chart.model;

public class Animation {
    public Integer duration;
    public String easing;

    public Animation duration(Integer prop) {
        duration = prop;
        return this;
    }

    public Animation easing(String prop) {
        easing = prop;
        return this;
    }
}
