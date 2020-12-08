package com.androidx.chart.model;

public class States {

    public Hover hover;
    public Select select;

    public States hover(Hover prop) {
        hover = prop;
        return this;
    }

    public States select(Select prop) {
        select = prop;
        return this;
    }

}
