package com.androidx.chart.creator;

import com.androidx.chart.enums.ChartAnimationType;
import com.androidx.chart.enums.ChartStackingType;
import com.androidx.chart.enums.ChartSymbolStyleType;
import com.androidx.chart.enums.ChartType;
import com.androidx.chart.enums.ChartZoomType;
import com.androidx.chart.model.ScrollablePlotArea;
import com.androidx.chart.model.Style;

public class ChartModel {

    public String animationType;         //动画类型
    public Integer animationDuration;     //动画时间
    public String title;                 //标题内容
    public Style titleStyle;            //标题文本风格样式
    public String subtitle;              //副标题内容
    public String subtitleAlign;         //副标题水平对齐方式
    public Style subtitleStyle;         //副标题文本风格样式
    public String axesTextColor;         //x 轴和 y 轴文字颜色
    public String chartType;             //图表类型
    public String stacking;              //堆积样式
    public String markerSymbol;          //折线曲线连接点的类型："circle", "square", "diamond", "triangle","triangle-down"，默认是"circle"
    public String markerSymbolStyle;     //折线曲线连接点的自定义风格样式
    public String zoomType;              //缩放类型 AAChartZoomTypeX表示可沿着 x 轴进行手势缩放
    public Boolean inverted;              //x 轴是否翻转(垂直)
    public Boolean xAxisReversed;         //x 轴翻转
    public Boolean yAxisReversed;         //y 轴翻转
    public Boolean tooltipEnabled;        //是否显示浮动提示框(默认显示)
    public String tooltipValueSuffix;    //浮动提示框单位后缀
    public Boolean tooltipCrosshairs;     //是否显示准星线(默认显示)
    public Boolean gradientColorEnable;   //是否要为渐变色
    public Boolean polar;                 //是否极化图形(变为雷达图)
    public Float[] margin;                //图表外边缘和绘图区域之间的边距
    public Boolean dataLabelsEnabled;     //是否显示数据
    public Style dataLabelsStyle;       //数据文本风格样式
    public Boolean xAxisLabelsEnabled;    //x 轴是否显示数据
    public Integer xAxisTickInterval;     //x 轴刻度点间隔数(设置每隔几个点显示一个 X轴的内容)
    public String[]categories;            //x 轴是否显示数据
    public Float xAxisGridLineWidth;    //x 轴网格线的宽度
    public Boolean xAxisVisible;          //x 轴是否显示
    public Boolean yAxisVisible;          //y 轴是否显示
    public Boolean yAxisLabelsEnabled;    //y 轴是否显示数据
    public String yAxisTitle;            //y 轴标题
    public Float yAxisLineWidth;        //y 轴轴线的宽度
    public Float yAxisMin;              //y 轴最小值
    public Float yAxisMax;              //y 轴最大值
    public Boolean yAxisAllowDecimals;    //y 轴是否允许显示小数
    public Float yAxisGridLineWidth;    //y 轴网格线的宽度
    public Object[]colorsTheme;           //图表主题颜色数组
    public Boolean legendEnabled;         //是否显示图例
    public Object backgroundColor;       //图表背景色
    public Float borderRadius;          //柱状图长条图头部圆角半径(可用于设置头部的形状,仅对条形图,柱状图有效)
    public Float markerRadius;          //折线连接点的半径长度
    public Object[] series;               //图表的数据列内容
    public Boolean touchEventEnabled;     //是否支持用户触摸事件
    public ScrollablePlotArea scrollablePlotArea;



    public ChartModel animationType(String prop) {
        animationType = prop;
        return this;
    }

    public ChartModel animationDuration(Integer prop) {
        animationDuration = prop;
        return this;
    }

    public ChartModel title(String prop) {
        title = prop;
        return this;
    }

    public ChartModel titleStyle(Style prop) {
        titleStyle = prop;
        return this;
    }

    public ChartModel subtitle(String prop) {
        subtitle = prop;
        return this;
    }

    public ChartModel subtitleAlign(String prop) {
        subtitleAlign = prop;
        return this;
    }

    public ChartModel subtitleStyle(Style prop) {
        subtitleStyle = prop;
        return this;
    }

    public ChartModel axesTextColor(String prop) {
        axesTextColor = prop;
        return this;
    }

    public ChartModel chartType(String prop) {
        chartType = prop;
        return this;
    }

    public ChartModel stacking(String prop) {
        stacking = prop;
        return this;
    }

    public ChartModel markerSymbol(String prop) {
        markerSymbol = prop;
        return this;
    }

    public ChartModel markerSymbolStyle(String prop) {
        markerSymbolStyle = prop;
        return this;
    }

    public ChartModel zoomType(String prop) {
        zoomType = prop;
        return this;
    }

    public ChartModel inverted(Boolean prop) {
        inverted = prop;
        return this;
    }

    public ChartModel xAxisReversed(Boolean prop) {
        xAxisReversed = prop;
        return this;
    }

    public ChartModel yAxisReversed(Boolean prop) {
        yAxisReversed = prop;
        return this;
    }

    public ChartModel tooltipEnabled(Boolean prop) {
        tooltipEnabled = prop;
        return this;
    }

    public ChartModel tooltipValueSuffix(String prop) {
        tooltipValueSuffix = prop;
        return this;
    }

    public ChartModel tooltipCrosshairs(Boolean prop) {
        tooltipCrosshairs = prop;
        return this;
    }

    public ChartModel gradientColorEnable(Boolean prop) {
        gradientColorEnable = prop;
        return this;
    }

    public ChartModel polar(Boolean prop) {
        polar = prop;
        return this;
    }

    public ChartModel margin(Float[] prop) {
        margin = prop;
        return this;
    }

    public ChartModel dataLabelsEnabled(Boolean prop) {
        dataLabelsEnabled = prop;
        return this;
    }

    public ChartModel dataLabelsStyle(Style prop) {
        dataLabelsStyle = prop;
        return this;
    }

    public ChartModel xAxisLabelsEnabled(Boolean prop) {
        xAxisLabelsEnabled = prop;
        return this;
    }

    public ChartModel xAxisTickInterval(Integer prop) {
        xAxisTickInterval = prop;
        return this;
    }

    public ChartModel categories(String[] prop) {
        categories = prop;
        return this;
    }

    public ChartModel xAxisGridLineWidth(Float prop) {
        xAxisGridLineWidth = prop;
        return this;
    }

    public ChartModel yAxisGridLineWidth(Float prop) {
        yAxisGridLineWidth = prop;
        return this;
    }

    public ChartModel xAxisVisible(Boolean prop) {
        xAxisVisible = prop;
        return this;
    }

    public ChartModel yAxisVisible(Boolean prop) {
        yAxisVisible = prop;
        return this;
    }

    public ChartModel yAxisLabelsEnabled(Boolean prop) {
        yAxisLabelsEnabled = prop;
        return this;
    }

    public ChartModel yAxisTitle(String prop) {
        yAxisTitle = prop;
        return this;
    }

    public ChartModel yAxisLineWidth(Float prop) {
        yAxisLineWidth = prop;
        return this;
    }

    public ChartModel yAxisMin(Float prop) {
        yAxisMin = prop;
        return this;
    }

    public ChartModel yAxisMax(Float prop) {
        yAxisMax = prop;
        return this;
    }

    public ChartModel yAxisAllowDecimals(Boolean prop) {
        yAxisAllowDecimals = prop;
        return this;
    }

    public ChartModel colorsTheme(Object[] prop) {
        colorsTheme = prop;
        return this;
    }

    public ChartModel legendEnabled(Boolean prop) {
        legendEnabled = prop;
        return this;
    }

    public ChartModel backgroundColor(Object prop) {
        backgroundColor = prop;
        return this;
    }


    public ChartModel borderRadius(Float prop) {
        borderRadius = prop;
        return this;
    }

    public ChartModel markerRadius(Float prop) {
        markerRadius = prop;
        return this;
    }

    public ChartModel series(Object[] prop) {
        series = prop;
        return this;
    }

    public ChartModel touchEventEnabled(Boolean prop) {
        touchEventEnabled = prop;
        return this;
    }

    public ChartModel scrollablePlotArea(ScrollablePlotArea prop) {
        scrollablePlotArea = prop;
        return this;
    }

    public ChartModel() {
        chartType             = ChartType.Line;
        animationDuration     = 500;//以毫秒为单位
        animationType         = ChartAnimationType.Linear;
        inverted              = false;
        stacking              = ChartStackingType.False;
        xAxisReversed         = false;
        yAxisReversed         = false;
        zoomType              = ChartZoomType.None;
        dataLabelsEnabled     = false;
        markerSymbolStyle     = ChartSymbolStyleType.Normal;
        colorsTheme           = new String[]{"#fe117c","#ffc069","#06caf4","#7dffc0"};//默认的颜色数组(必须要添加默认数组,否则就会出错)
        tooltipCrosshairs     = true;
        gradientColorEnable   = false;
        polar                 = false;
        xAxisLabelsEnabled    = true;
        xAxisGridLineWidth    = 0f;
        yAxisLabelsEnabled    = true;
        yAxisGridLineWidth    = 1f;
        legendEnabled         = true;
        backgroundColor       = "#ffffff";
        borderRadius          = 0f;//柱状图长条图头部圆角半径(可用于设置头部的形状,仅对条形图,柱状图有效,设置为1000时,柱形图或者条形图头部为楔形)
        markerRadius          = 6f;//折线连接点的半径长度,如果值设置为0,这样就相当于不显示了
    }

}









