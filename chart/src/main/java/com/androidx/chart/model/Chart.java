package com.androidx.chart.model;

public class Chart {
    public String type;
    public Object backgroundColor;
    public String plotBackgroundImage;
    public String pinchType;
    public Boolean panning;
    public String panKey;
    public Boolean polar;
    public Animation animation;
    public Boolean inverted;
    /*图表外边缘和绘图区域之间的边距。 数组中的数字分别表示顶部，右侧，底部和左侧 ([👆,👉,👇,👈])。
    也可以使用 marginTop，marginRight，marginBottom 和 marginLeft 来设置某一个方向的边距*/
    public Float[] margin;
    public Float marginTop; //👆
    public Float marginRight; //👉
    public Float marginBottom; //👇
    public Float marginLeft; //👈
    public ScrollablePlotArea scrollablePlotArea;
    public ResetZoomButton resetZoomButton;


    public Chart type(String prop) {
        type = prop;
        return this;
    }

    public Chart backgroundColor(Object prop) {
        backgroundColor = prop;
        return this;
    }

    public Chart plotBackgroundImage(String prop) {
        plotBackgroundImage = prop;
        return this;
    }

    public Chart pinchType(String prop) {
        pinchType = prop;
        return this;
    }

    public Chart panning(Boolean prop) {
        panning = prop;
        return this;
    }

    public Chart panKey(String prop) {
        panKey = prop;
        return this;
    }

    public Chart polar(Boolean prop) {
        polar = prop;
        return this;
    }

    public Chart animation(Animation prop) {
        animation = prop;
        return this;
    }

    public Chart inverted(Boolean prop) {
        inverted = prop;
        return this;
    }

    public Chart margin(Float[] prop) {
        margin = prop;
        return this;
    }

    public Chart marginTop(Float prop) {
        marginTop = prop;
        return this;
    }

    public Chart marginRight(Float prop) {
        marginRight = prop;
        return this;
    }

    public Chart marginBottom(Float prop) {
        marginBottom = prop;
        return this;
    }

    public Chart marginLeft(Float prop) {
        marginLeft = prop;
        return this;
    }

    public Chart scrollablePlotArea(ScrollablePlotArea prop) {
        scrollablePlotArea = prop;
        return this;
    }

    public Chart resetZoomButton(ResetZoomButton prop) {
        resetZoomButton = prop;
        return this;
    }



}
