package com.androidx.chart.model;

public class Legend {
    public String layout; //图例数据项的布局。布局类型： "horizontal" 或 "vertical" 即水平布局和垂直布局 默认是：horizontal.
    public String align; //设定图例在图表区中的水平对齐方式，合法值有left，center 和 right。
    public String verticalAlign; //设定图例在图表区中的垂直对齐方式，合法值有 top，middle 和 bottom。垂直位置可以通过 y 选项做进一步设定。
    public Boolean enabled;
    public String borderColor;
    public Float borderWidth;
    public Float itemMarginTop; //图例的每一项的顶部外边距，单位px。 默认是：0.
    public ItemStyle itemStyle;
    public Float x;
    public Float y;
    public Boolean floating;

    public Legend layout(String prop) {
        layout = prop;
        return this;
    }

    public Legend align(String prop) {
        align = prop;
        return this;
    }

    public Legend verticalAlign(String prop) {
        verticalAlign = prop;
        return this;
    }

    public Legend enabled(Boolean prop) {
        enabled = prop;
        return this;
    }

    public Legend borderColor(String prop) {
        borderColor = prop;
        return this;
    }

    public Legend BorderWidth(Float prop) {
        borderWidth = prop;
        return this;
    }

    public Legend itemMarginTop(Float prop) {
        itemMarginTop = prop;
        return this;
    }

    public Legend itemStyle(ItemStyle prop) {
        itemStyle = prop;
        return this;
    }

    public Legend x(Float prop) {
        x = prop;
        return this;
    }

    public Legend y(Float prop) {
        y = prop;
        return this;
    }

    public Legend floating(Boolean prop) {
        floating = prop;
        return this;
    }



}
