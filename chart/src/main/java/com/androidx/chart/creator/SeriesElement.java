package com.androidx.chart.creator;

import com.androidx.chart.model.DataLabels;
import com.androidx.chart.model.Marker;
import com.androidx.chart.model.Shadow;
import com.androidx.chart.model.Tooltip;

/**
 * Created by anan on 2018/4/16.
 */

public class SeriesElement {

    private String type;
    private Boolean allowPointSelect;
    private String name;
    private Object[] data;
    private Float lineWidth;//折线图、曲线图、直方折线图、折线填充图、曲线填充图、直方折线填充图的线条宽度
    private Float borderWidth;
    private Object color;
    private Object fillColor;
    private Float fillOpacity;//折线填充图、曲线填充图、直方折线填充图等填充图类型的填充颜色透明度
    private Float threshold;//The threshold, also called zero level or base level. For line type series this is only used in conjunction with negativeColor. default：0.
    private String negativeColor;// The color for the parts of the graph or points that are below the threshold
    private Object negativeFillColor;
    private Object size;
    private Object innerSize;
    private String dashStyle;
    private Integer yAxis;
    private DataLabels dataLabels;
    private Marker marker;
    private Object step;
    private Object states;
    private Boolean colorByPoint;
    private Integer zIndex;
    private Object[] zones;
    private Shadow shadow;
    private String stack;
    private Tooltip tooltip;
    private Boolean showInLegend;
    private Boolean enableMouseTracking;
    private Boolean reversed;


    public SeriesElement type(String prop) {
        type = prop;
        return this;
    }

    public SeriesElement allowPointSelect(Boolean prop) {
        allowPointSelect = prop;
        return this;
    }

    public SeriesElement name(String prop) {
        name = prop;
        return this;
    }

    public SeriesElement data(Object[] prop) {
        data = prop;
        return this;
    }

    public SeriesElement lineWidth(Float prop) {
        lineWidth = prop;
        return this;
    }

    public SeriesElement borderWidth(Float prop) {
        borderWidth = prop;
        return this;
    }

    public SeriesElement color(Object prop) {
        color = prop;
        return this;
    }

    public SeriesElement fillColor(Object prop) {
        fillColor = prop;
        return this;
    }

    public SeriesElement fillOpacity(Float prop) {
        fillOpacity = prop;
        return this;
    }

    public SeriesElement threshold(Float prop) {
        threshold = prop;
        return this;
    }

    public SeriesElement negativeColor(String prop) {
        negativeColor = prop;
        return this;
    }

    public SeriesElement negativeFillColor(Object prop) {
        negativeFillColor = prop;
        return this;
    }

    public SeriesElement size(Object prop) {
        size = prop;
        return this;
    }

    public SeriesElement innerSize(Object prop) {
        innerSize = prop;
        return this;
    }

    public SeriesElement dashStyle(String prop) {
        dashStyle = prop;
        return this;
    }

    public SeriesElement yAxis(Integer prop) {
        yAxis = prop;
        return this;
    }

    public SeriesElement dataLabels(DataLabels prop) {
        dataLabels = prop;
        return this;
    }

    public SeriesElement marker(Marker prop) {
        marker = prop;
        return this;
    }

    public SeriesElement step(Object prop) {
        step = prop;
        return this;
    }

    public SeriesElement states(Object prop) {
        states = prop;
        return this;
    }

    public SeriesElement colorByPoint(Boolean prop) {
        colorByPoint = prop;
        return this;
    }

    public SeriesElement zIndex(Integer prop) {
        zIndex = prop;
        return this;
    }

    public SeriesElement zones(Object[] prop) {
        zones = prop;
        return this;
    }

    public SeriesElement shadow(Shadow prop) {
        shadow = prop;
        return this;
    }

    public SeriesElement stack(String prop) {
        stack = prop;
        return this;
    }

    public SeriesElement tooltip(Tooltip prop) {
        tooltip = prop;
        return this;
    }

    public SeriesElement showInLegend(Boolean prop) {
        showInLegend = prop;
        return this;
    }

    public SeriesElement enableMouseTracking(Boolean prop) {
        enableMouseTracking = prop;
        return this;
    }

    public SeriesElement reversed(Boolean prop) {
        reversed = prop;
        return this;
    }

}





