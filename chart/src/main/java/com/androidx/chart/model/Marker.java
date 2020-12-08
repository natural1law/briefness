package com.androidx.chart.model;

public class Marker {
    public Float radius;
    public String symbol;
    public String fillColor;//点的填充色(用来设置折线连接点的填充色)
    public Float lineWidth;//外沿线的宽度(用来设置折线连接点的轮廓描边的宽度)
    public Object lineColor;//外沿线的颜色(用来设置折线连接点的轮廓描边颜色，当值为空字符串时，默认取数据点或数据列的颜色。)
    public MarkerStates states;

    public Marker radius(Float prop) {
        radius = prop;
        return this;
    }

    public Marker symbol(String prop) {
        symbol = prop;
        return this;
    }

    public Marker fillColor(String prop) {
        fillColor = prop;
        return this;
    }

    public Marker lineWidth(Float prop) {
        lineWidth = prop;
        return this;
    }

    public Marker lineColor(Object prop) {
        lineColor = prop;
        return this;
    }

    public Marker states(MarkerStates prop) {
        states = prop;
        return this;
    }
}
