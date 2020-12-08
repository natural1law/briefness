package com.androidx.chart.creator;

import com.androidx.chart.enums.ChartAnimationType;
import com.androidx.chart.enums.ChartSymbolStyleType;
import com.androidx.chart.enums.ChartType;
import com.androidx.chart.model.Animation;
import com.androidx.chart.model.Bar;
import com.androidx.chart.model.Chart;
import com.androidx.chart.model.Column;
import com.androidx.chart.model.Columnrange;
import com.androidx.chart.model.DataLabels;
import com.androidx.chart.model.ItemStyle;
import com.androidx.chart.model.Labels;
import com.androidx.chart.model.Legend;
import com.androidx.chart.model.Marker;
import com.androidx.chart.model.Options;
import com.androidx.chart.model.Pie;
import com.androidx.chart.model.PlotOptions;
import com.androidx.chart.model.Series;
import com.androidx.chart.model.Style;
import com.androidx.chart.model.Subtitle;
import com.androidx.chart.model.Title;
import com.androidx.chart.model.Tooltip;
import com.androidx.chart.model.Axis;
import com.androidx.chart.model.YAxis;

public class OptionsConstructor
{
    public static Options configureChartOptions (
            ChartModel chartModel
    ) {
        Chart chart = new Chart()
                .type(chartModel.chartType) //绘图类型
                .inverted(chartModel.inverted) //设置是否反转坐标轴，使X轴垂直，Y轴水平。 如果值为 true，则 x 轴默认是 倒置 的。 如果图表中出现条形图系列，则会自动反转
                .backgroundColor(chartModel.backgroundColor) //设置图表的背景色(包含透明度的设置)
                .pinchType(chartModel.zoomType) //设置手势缩放方向
                .panning(true) //设置手势缩放后是否可平移
                .polar(chartModel.polar) //是否极化图表(开启极坐标模式)
                .margin(chartModel.margin) //图表边距
                .scrollablePlotArea(chartModel.scrollablePlotArea)
                ;

        Title title = new Title()
                .text(chartModel.title) //标题文本内容
                .style(chartModel.titleStyle)
                ;

        Subtitle subtitle = new Subtitle()
                .text(chartModel.subtitle) //副标题内容
                .align(chartModel.subtitleAlign) //图表副标题文本水平对齐方式。可选的值有 “left”，”center“和“right”。 默认是：center.
                .style(chartModel.subtitleStyle)
                ;

        Tooltip tooltip = new Tooltip()
                .enabled(chartModel.tooltipEnabled) //启用浮动提示框
                .shared(true) //多组数据共享一个浮动提示框
                .crosshairs(true) //启用准星线
                .valueSuffix(chartModel.tooltipValueSuffix) //浮动提示框的单位名称后缀
                ;

        PlotOptions plotOptions = new PlotOptions()
                .series(new Series()
                        .stacking(chartModel.stacking) //设置是否百分比堆叠显示图形
                )
                ;

        if (!chartModel.animationType.equals(ChartAnimationType.Linear)) {
            plotOptions.series.animation((new Animation()
                    .easing(chartModel.animationType)
                    .duration(chartModel.animationDuration)
            ));
        }

        configureAAPlotOptionsMarkerStyle(chartModel, plotOptions);
        configureAAPlotOptionsDataLabels(plotOptions, chartModel);

        Legend legend = new Legend()
                .enabled(chartModel.legendEnabled) //是否显示 legend
                .itemStyle(new ItemStyle()
                        .color(chartModel.axesTextColor))
                ;

        Options options = new Options()
                .chart(chart)
                .title(title)
                .subtitle(subtitle)
                .tooltip(tooltip)
                .plotOptions(plotOptions)
                .legend(legend)
                .series(chartModel.series)
                .colors(chartModel.colorsTheme) //设置颜色主题
                .touchEventEnabled(chartModel.touchEventEnabled) //是否支持点击事件
                ;

        configureAxisContentAndStyle(options, chartModel);

        return options;
    }

    private static void configureAAPlotOptionsMarkerStyle (
            ChartModel chartModel,
            PlotOptions plotOptions
    ) {
        String chartType = chartModel.chartType;
        //数据点标记相关配置，只有线性图(折线图、曲线图、折线区域填充图、曲线区域填充图、散点图、折线范围填充图、曲线范围填充图、多边形图)才有数据点标记
        if (       chartType.equals(ChartType.Area)
                || chartType.equals(ChartType.Areaspline)
                || chartType.equals(ChartType.Line)
                || chartType.equals(ChartType.Spline)
                || chartType.equals(ChartType.Scatter)
                || chartType.equals(ChartType.Arearange)
                || chartType.equals(ChartType.Areasplinerange)
                || chartType.equals(ChartType.Polygon))
        {
            Marker marker = new Marker()
                    .radius(chartModel.markerRadius) //曲线连接点半径，默认是4
                    .symbol(chartModel.markerSymbol); //曲线点类型："circle", "square", "diamond", "triangle","triangle-down"，默认是"circle"
            if (chartModel.markerSymbolStyle.equals(ChartSymbolStyleType.InnerBlank)) {
                marker.fillColor("#ffffff") //点的填充色(用来设置折线连接点的填充色)
                        .lineWidth(2f) //外沿线的宽度(用来设置折线连接点的轮廓描边的宽度)
                        .lineColor(""); //外沿线的颜色(用来设置折线连接点的轮廓描边颜色，当值为空字符串时，默认取数据点或数据列的颜色)
            } else if (chartModel.markerSymbolStyle.equals(ChartSymbolStyleType.BorderBlank)) {
                marker.lineWidth(2f)
                        .lineColor(chartModel.backgroundColor);
            }
            Series series = plotOptions.series;
            series.marker(marker);

        }
    }


    private static void configureAAPlotOptionsDataLabels (
            PlotOptions plotOptions,
            ChartModel chartModel
    ) {
        String chartType = chartModel.chartType;

        DataLabels dataLabels = new DataLabels()
                .enabled(chartModel.dataLabelsEnabled);
        if (chartModel.dataLabelsEnabled) {
            dataLabels
                    .style(chartModel.dataLabelsStyle);
        }

        switch (chartType) {
            case ChartType.Column:
                Column aaColumn = new Column()
                        .borderWidth(0f)
                        .borderRadius(chartModel.borderRadius);
                if (chartModel.polar) {
                    aaColumn.pointPadding(0f)
                            .groupPadding(0.005f);
                }
                plotOptions.column(aaColumn);
                break;
            case ChartType.Bar:
                Bar bar = new Bar()
                        .borderWidth(0f)
                        .borderRadius(chartModel.borderRadius)
                        ;
                if (chartModel.polar) {
                    bar.pointPadding(0f)
                            .groupPadding(0.005f);
                }
                plotOptions.bar(bar);
                break;
            case ChartType.Pie:
                Pie pie = new Pie()
                        .allowPointSelect(true)
                        .cursor("pointer")
                        .showInLegend(true);
                if (chartModel.dataLabelsEnabled) {
                    dataLabels.format("<b>{point.name}</b>: {point.percentage:.1f} %");
                }
                plotOptions.pie(pie);
                break;
            case ChartType.Columnrange:
                Columnrange columnrange = new Columnrange()
                        .borderRadius(0f) //The color of the border surrounding each column or bar
                        .borderWidth(0f) //The corner radius of the border surrounding each column or bar. default：0
                        ;
                plotOptions.columnrange(columnrange);
                break;
        }
        plotOptions.series.dataLabels(dataLabels);

    }

    private static void configureAxisContentAndStyle (
            Options options,
            ChartModel chartModel
    ) {
        String chartType = chartModel.chartType;
        //x 轴和 Y 轴的相关配置,扇形图、金字塔图和漏斗图则不需要设置 X 轴和 Y 轴的相关内容
        if (       !chartType.equals(ChartType.Pie)
                && !chartType.equals(ChartType.Pyramid)
                && !chartType.equals(ChartType.Funnel)
        ) {
            Boolean aaXAxisLabelsEnabled = chartModel.xAxisLabelsEnabled;
            Labels aaXAxisLabels = new Labels()
                    .enabled(aaXAxisLabelsEnabled);//设置 x 轴是否显示文字
            if (aaXAxisLabelsEnabled) {
                aaXAxisLabels.style(new Style()
                        .color(chartModel.axesTextColor)
                );
            }

            Axis aaXAxis = new Axis()
                    .labels(aaXAxisLabels) //设置 x 轴是否显示文字
                    .reversed(chartModel.xAxisReversed)
                    .gridLineWidth(chartModel.xAxisGridLineWidth) //x轴网格线宽度
                    .categories(chartModel.categories)
                    .visible(chartModel.xAxisVisible) //x轴是否可见
                    .tickInterval(chartModel.xAxisTickInterval)
                    ;//x轴坐标点间隔数

            Boolean aaYAxisLabelsEnabled = chartModel.yAxisLabelsEnabled;
            Labels aaYAxisLabels = new Labels()
                    .enabled(chartModel.yAxisLabelsEnabled);
            if (aaYAxisLabelsEnabled) {
                aaYAxisLabels.style(new Style()
                        .color(chartModel.axesTextColor)
                );
            }

            YAxis aaYAxis = new YAxis()
                    .labels(aaYAxisLabels) //设置 y 轴是否显示数字
                    .min(chartModel.yAxisMin) //设置 y 轴最小值,最小值等于零就不能显示负值了
                    .max(chartModel.yAxisMax) //y轴最大值
                    .allowDecimals(chartModel.yAxisAllowDecimals) //是否允许显示小数
                    .reversed(chartModel.yAxisReversed)
                    .gridLineWidth(chartModel.yAxisGridLineWidth) //y轴网格线宽度
                    .title(new Title()
                            .text(chartModel.yAxisTitle)
                            .style(new Style()
                                    .color(chartModel.axesTextColor))
                    ) //y 轴标题
                    .lineWidth(chartModel.yAxisLineWidth) //设置 y轴轴线的宽度,为0即是隐藏 y轴轴线
                    .visible(chartModel.yAxisVisible)
                    ;

            options.xAxis(aaXAxis)
                    .yAxis(aaYAxis);
        }
    }


}
