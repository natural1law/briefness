package com.androidx.chart.model;

public class Style {
    public String color;
    public String fontSize;
    public String fontWeight;
    public String textOutline;

    public Style color(String prop) {
        color = prop;
        return this;
    }

    public Style fontSize(Float prop) {
        fontSize = prop + "px";
        return this;
    }

    public Style fontWeight(String prop) {
        fontWeight = prop;
        return this;
    }

    public Style textOutline(String prop) {
        textOutline = prop;
        return this;
    }

    public static Style style(
            String color
    ) {
        return Style.style(color,null);
    }

    public static Style style(
            String color,
            Float fontSize
    ) {
        return Style.style(color,fontSize,null);
    }

    public static Style style(
            String color,
            Float fontSize,
            String fontWeight
    ) {
        return Style.style(color,fontSize,fontWeight,null);
    }

    public static Style style(
            String color,
            Float fontSize,
            String fontWeight,
            String textOutline
    ) {
        Style style = new Style()
                .color(color)
                .fontSize(fontSize)
                .fontWeight(fontWeight)
                .textOutline(textOutline);
        return style;
    }

}
