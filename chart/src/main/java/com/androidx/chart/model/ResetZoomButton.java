package com.androidx.chart.model;

import java.util.Map;

public class ResetZoomButton {
    public Position position;
    public String relativeTo;
    public Map theme;

    public ResetZoomButton position(Position prop) {
        position = prop;
        return this;
    }

    public ResetZoomButton relativeTo(String prop) {
        relativeTo = prop;
        return this;
    }

    public ResetZoomButton theme(Map prop) {
        theme = prop;
        return this;
    }


}
