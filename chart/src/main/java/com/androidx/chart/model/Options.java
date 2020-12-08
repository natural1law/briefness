package com.androidx.chart.model;

public class Options {
    public Chart chart;
    public Title title;
    public Subtitle subtitle;
    public Axis xAxis;
    public YAxis yAxis;
    public Axis[] xAxisArray;
    public YAxis[] yAxisArray;
    public Tooltip tooltip;
    public PlotOptions plotOptions;
    public Object[] series;
    public Legend legend;
    public Pane pane;
    public Object[] colors;
    public Lang defaultOptions;
    public Boolean touchEventEnabled;

    public Options chart(Chart prop) {
        chart = prop;
        return this;
    }

    public Options title(Title prop) {
        title = prop;
        return this;
    }

    public Options subtitle(Subtitle prop) {
        subtitle = prop;
        return this;
    }

    public Options xAxis(Axis prop) {
        xAxis = prop;
        return this;
    }

    public Options yAxis(YAxis prop) {
        yAxis = prop;
        return this;
    }

    public Options xAxisArray(Axis[] prop) {
        xAxisArray = prop;
        return this;
    }

    public Options yAxisArray(YAxis[] prop) {
        yAxisArray = prop;
        return this;
    }

    public Options tooltip(Tooltip prop) {
        tooltip = prop;
        return this;
    }

    public Options plotOptions(PlotOptions prop) {
        plotOptions = prop;
        return this;
    }

    public Options series(Object[] prop) {
        series = prop;
        return this;
    }

    public Options legend(Legend prop) {
        legend = prop;
        return this;
    }

    public Options pane(Pane prop) {
        pane = prop;
        return this;
    }

    public Options colors(Object[] prop) {
        colors = prop;
        return this;
    }

    public Options defaultOptions(Lang prop) {
        defaultOptions = prop;
        return this;
    }

    public Options touchEventEnabled(Boolean prop) {
        touchEventEnabled = prop;
        return this;
    }

}
