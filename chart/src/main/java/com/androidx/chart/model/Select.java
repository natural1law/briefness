package com.androidx.chart.model;

public class Select {
    public String borderColor;
    public String color;
    public Halo halo;

    public Select borderColor(String prop) {
        borderColor = prop;
        return this;
    }

    public Select color(String prop) {
        color = prop;
        return this;
    }

    public Select halo(Halo prop) {
        halo = prop;
        return this;
    }
}
