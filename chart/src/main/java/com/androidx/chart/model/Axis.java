package com.androidx.chart.model;

public class Axis {
    public String type;
    public PlotBandsElement[] plotBands;
    public PlotLinesElement[] plotLines;
    public String[] categories;
    public Integer linkedTo;
    public Boolean reversed;
    public Boolean opposite;
    public Float lineWidth; //x轴轴线宽度
    public String lineColor; //x轴轴线线颜色
    public Float max; //x轴最大值
    public Float min;//x轴最小值（设置为0就不会有负数）
    public String tickColor; //x轴轴线下方刻度线颜色
    public Float gridLineWidth; //x轴网格线宽度
    public String gridLineColor; //x轴网格线颜色
    public String gridLineDashStyle; //x轴网格线样式
    public Float off;//x轴垂直偏移
    public Labels labels; //用于设置 x 轴文字相关的
    public Boolean visible; //用于设置 x 轴以及 x 轴文字是否显示
    public Boolean startOnTick; //Whether to force the axis to start on a tick. Use this option with the minPadding option to control the axis start. 默认是：false.
    public Integer tickInterval;//x轴刻度点间隔数(设置每隔几个点显示一个 X轴的内容:
    public Crosshair crosshair; //准星线样式设置
    public String tickmarkPlacement; //本参数只对分类轴有效。 当值为 on 时刻度线将在分类上方显示；当值为 between 时，刻度线将在两个分类中间显示。当 tickInterval 为 1 时，默认是 between，其他情况默认是 on。 默认是：null.
    public Float tickWidth;//坐标轴刻度线的宽度，设置为 0 时则不显示刻度线
    public Float tickLength;//坐标轴刻度线的长度。 默认是：10.
    public String tickPosition; //刻度线相对于轴线的位置，可用的值有 inside 和 outside，分别表示在轴线的内部和外部。 默认是：outside.



    public Axis type(String prop) {
        type = prop;
        return this;
    }

    public Axis plotBands(PlotBandsElement[] prop) {
        plotBands = prop;
        return this;
    }

    public Axis plotLines(PlotLinesElement[] prop) {
        plotLines = prop;
        return this;
    }

    public Axis categories(String[] prop) {
        categories = prop;
        return this;
    }

    public Axis linkedTo(Integer prop) {
        linkedTo = prop;
        return this;
    }

    public Axis reversed(Boolean prop) {
        reversed = prop;
        return this;
    }

    public Axis opposite(Boolean prop) {
        opposite = prop;
        return this;
    }

    public Axis lineWidth(Float prop) {
        lineWidth = prop;
        return this;
    }

    public Axis lineColor(String prop) {
        lineColor = prop;
        return this;
    }

    public Axis max(Float prop) {
        max = prop;
        return this;
    }

    public Axis min(Float prop) {
        min = prop;
        return this;
    }

    public Axis tickColor(String prop) {
        tickColor = prop;
        return this;
    }

    public Axis gridLineWidth(Float prop) {
        gridLineWidth = prop;
        return this;
    }

    public Axis gridLineColor(String prop) {
        gridLineColor = prop;
        return this;
    }

    public Axis gridLineDashStyle(String prop) {
        gridLineDashStyle = prop;
        return this;
    }

    public Axis off(Float prop) {
        off = prop;
        return this;
    }

    public Axis labels(Labels prop) {
        labels = prop;
        return this;
    }

    public Axis visible(Boolean prop) {
        visible = prop;
        return this;
    }

    public Axis startOnTick(Boolean prop) {
        startOnTick = prop;
        return this;
    }

    public Axis tickInterval(Integer prop) {
        tickInterval = prop;
        return this;
    }

    public Axis crosshair(Crosshair prop) {
        crosshair = prop;
        return this;
    }

    public Axis tickmarkPlacement(String prop) {
        tickmarkPlacement = prop;
        return this;
    }

    public Axis tickWidth(Float prop) {
        tickWidth = prop;
        return this;
    }

    public Axis tickLength(Float prop) {
        tickLength = prop;
        return this;
    }

    public Axis tickPosition(String prop) {
        tickPosition = prop;
        return this;
    }
}
