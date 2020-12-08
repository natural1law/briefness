package com.androidx.chart.model;

public class Pie {
    public String type;
    public String name;
    public Object[] data;
    public DataLabels dataLabels;
    public Float size;
    public String innerSize;
    public Boolean allowPointSelect;
    public String cursor;
    public Boolean showInLegend;
    public Float startAngle;
    public Float endAngle;
    public Float depth;
    public Object center;

    public Pie type(String prop) {
        type = prop;
        return this;
    }

    public Pie name(String prop) {
        name = prop;
        return this;
    }

    public Pie data(Object[] prop) {
        data = prop;
        return this;
    }

    public Pie dataLabels(DataLabels prop) {
        dataLabels = prop;
        return this;
    }

    public Pie size(Float prop) {
        size = prop;
        return this;
    }

    public Pie innerSize(String prop) {
        innerSize = prop;
        return this;
    }

    public Pie allowPointSelect(Boolean prop) {
        allowPointSelect = prop;
        return this;
    }

    public Pie cursor(String prop) {
        cursor = prop;
        return this;
    }

    public Pie showInLegend(Boolean prop) {
        showInLegend = prop;
        return this;
    }

    public Pie startAngle(Float prop) {
        startAngle = prop;
        return this;
    }

    public Pie endAngle(Float prop) {
        endAngle = prop;
        return this;
    }

    public Pie depth(Float prop) {
        depth = prop;
        return this;
    }

    public Pie center(Object prop) {
        center = prop;
        return this;
    }

}
