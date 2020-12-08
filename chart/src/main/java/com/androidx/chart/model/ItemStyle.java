package com.androidx.chart.model;

public class ItemStyle {
    public String color;
    public String cursor;
    public String pointer;
    public String fontSize;
    public String fontWeight;


    public ItemStyle color(String prop) {
        color = prop;
        return this;
    }

    public ItemStyle cursor(String prop) {
        cursor = prop;
        return this;
    }

    public ItemStyle pointer(String prop) {
        pointer = prop;
        return this;
    }

    public ItemStyle fontSize(Float prop) {
        fontSize = prop + "px";
        return this;
    }

    public ItemStyle fontWeight(String prop) {
        fontWeight = prop;
        return this;
    }
}
