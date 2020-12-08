package com.androidx.chart.model;

public class ScrollablePlotArea {
    public Integer minHeight;
    public Integer minWidth;
    public Float opacity;
    public Float scrollPositionX;
    public Float scrollPositionY;

    public ScrollablePlotArea minHeight(Integer prop) {
        minHeight = prop;
        return this;
    }

    public ScrollablePlotArea minWidth(Integer prop) {
        minWidth = prop;
        return this;
    }

    public ScrollablePlotArea opacity(Float prop) {
        opacity = prop;
        return this;
    }

    public ScrollablePlotArea scrollPositionX(Float prop) {
        scrollPositionX = prop;
        return this;
    }

    public ScrollablePlotArea scrollPositionY(Float prop) {
        scrollPositionY = prop;
        return this;
    }
}
