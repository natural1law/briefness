package com.androidx.chart.model;

import java.util.Map;

public class Halo {
    public Map attributes;
    public Float opacity;
    public Float size;

    public Halo attributes(Map prop) {
        attributes = prop;
        return this;
    }

    public Halo opacity(Float prop) {
        opacity = prop;
        return this;
    }

    public Halo size(Float prop) {
        size = prop;
        return this;
    }
}
