package com.androidx.chart.model;

public class Lang {
    public String resetZoom;
    public String thousandsSep;

    public Lang resetZoom(String prop) {
        resetZoom = prop;
        return this;
    }

    public Lang thousandsSep(String prop) {
        thousandsSep = prop;
        return this;
    }
}
