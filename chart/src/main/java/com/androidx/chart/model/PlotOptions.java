package com.androidx.chart.model;

public class PlotOptions {
    public Column column;
    public Line line;
    public Pie pie;
    public Bar bar;
    public Spline spline;
    public Area area;
    public Areaspline areaspline;
    public Columnrange columnrange;
    public Arearange arearange;
    public Series series;


    public PlotOptions column(Column prop) {
        column = prop;
        return this;
    }

    public PlotOptions line(Line prop) {
        line = prop;
        return this;
    }

    public PlotOptions pie(Pie prop) {
        pie = prop;
        return this;
    }

    public PlotOptions bar(Bar prop) {
        bar = prop;
        return this;
    }

    public PlotOptions spline(Spline prop) {
        spline = prop;
        return this;
    }

    public PlotOptions area(Area prop) {
        area = prop;
        return this;
    }

    public PlotOptions areaspline(Areaspline prop) {
        areaspline = prop;
        return this;
    }

    public PlotOptions columnrange(Columnrange prop) {
        columnrange = prop;
        return this;
    }

    public PlotOptions arearange(Arearange prop) {
        arearange = prop;
        return this;
    }

    public PlotOptions series(Series prop) {
        series = prop;
        return this;
    }

}
