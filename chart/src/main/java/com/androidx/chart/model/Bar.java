package com.androidx.chart.model;

public class Bar {
    public String name;
    public Object[] data;
    public String color;
    public Boolean grouping;//Whether to group non-stacked columns or to let them render independent of each other. Non-grouped columns will be laid out individually and overlap each other. 默认是：true.
    public Float pointPadding;//Padding between each column or bar, in x axis units. 默认是：0.1.
    public Float pointPlacement;//Padding between each column or bar, in x axis units. 默认是：0.1.
    public Float groupPadding;//Padding between each value groups, in x axis units. 默认是：0.2.
    public Float borderWidth;
    public Boolean colorByPoint;//对每个不同的点设置颜色(当图表类型为 AABar 时,设置为 AABar 对象的属性,当图表类型为 bar 时,应该设置为 bar 对象的属性才有效)
    public DataLabels dataLabels;
    public String stacking;
    public Float borderRadius;
    public Float yAxis;

    public Bar name(String prop) {
        name = prop;
        return this;
    }

    public Bar data(Object[] prop) {
        data = prop;
        return this;
    }

    public Bar color(String prop) {
        color = prop;
        return this;
    }

    public Bar grouping(Boolean prop) {
        grouping = prop;
        return this;
    }

    public Bar pointPadding(Float prop) {
        pointPadding = prop;
        return this;
    }

    public Bar pointPlacement(Float prop) {
        pointPlacement = prop;
        return this;
    }

    public Bar groupPadding(Float prop) {
        groupPadding = prop;
        return this;
    }

    public Bar borderWidth(Float prop) {
        borderWidth = prop;
        return this;
    }

    public Bar colorByPoint(Boolean prop) {
        colorByPoint = prop;
        return this;
    }

    public Bar dataLabels(DataLabels prop) {
        dataLabels = prop;
        return this;
    }

    public Bar stacking(String prop) {
        stacking = prop;
        return this;
    }

    public Bar borderRadius(Float prop) {
        borderRadius = prop;
        return this;
    }

    public Bar yAxis(Float prop) {
        yAxis = prop;
        return this;
    }
}
