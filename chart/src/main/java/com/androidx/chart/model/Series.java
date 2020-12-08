package com.androidx.chart.model;

import java.util.Map;

public class Series {
    public Float borderRadius;
    public Marker marker;
    public String stacking;
    public Animation animation;
    public String[] keys;
    public Boolean colorByPoint;//决定了图表是否给每个数据列或每个点分配一个颜色，默认值是 false， 即默认是给每个数据类分配颜色，
    public Boolean connectNulls;//设置折线是否断点重连
    public Map events;
    public Shadow shadow;
    public DataLabels dataLabels;

    public Series borderRadius(Float prop) {
        borderRadius = prop;
        return this;
    }

    public Series marker(Marker prop) {
        marker = prop;
        return this;
    }

    public Series stacking(String prop) {
        stacking = prop;
        return this;
    }

    public Series animation(Animation prop) {
        animation = prop;
        return this;
    }

    public Series keys(String[] prop) {
        keys = prop;
        return this;
    }

    public Series colorByPoint(Boolean prop) {
        colorByPoint = prop;
        return this;
    }

    public Series connectNulls(Boolean prop) {
        connectNulls = prop;
        return this;
    }

    public Series events(Map prop) {
        events = prop;
        return this;
    }

    public Series shadow(Shadow prop) {
        shadow = prop;
        return this;
    }

    public Series dataLabels(DataLabels prop) {
        dataLabels = prop;
        return this;
    }

}
