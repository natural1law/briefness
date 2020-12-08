package com.androidx.chart.model;

public class Hover {
    public String borderColor;
    public Float brightness;
    public String color;
    public Halo halo;

    public Hover borderColor(String prop) {
        borderColor = prop;
        return this;
    }

    public Hover brightness(Float prop) {
        brightness = prop;
        return this;
    }

    public Hover color(String prop) {
        color = prop;
        return this;
    }

    public Hover halo(Halo prop) {
        halo = prop;
        return this;
    }
}
