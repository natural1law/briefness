function callJS(param){
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('container'));
    // 指定图表的配置项和数据
    var option = {
        title: {
                text: ''
            },
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data: ['目标干球', '干球温度', '目标湿球', '湿球温度'],
                textStyle: {
                    "fontSize": 18
                }
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            toolbox: {
                feature: {
                    dataZoom: {
                        yAxisIndex: 'none'
                    },
                    magicType: {type: ['line', 'bar']},
                    restore: {}
                }
            },
            xAxis: {
                type: 'category',
                boundaryGap: false,
                data: param.times,
                axisLabel:{
//                    interval: 0
                }
            },
            yAxis: {
                type: 'value',
                min:0,
                max: 80,
                axisLabel: {
                    formatter: '{value} °C'
                }
            },
            series: [
                {
                    name: '目标干球',
                    type: 'line',
                    smooth: true,
                    lineStyle: {
                        width: 1,
                        type: 'dashed'
                    },
                    data: param.tdbList
                },
                {
                    name: '干球温度',
                    type: 'line',
                    smooth: true,
                    data: param.dbtList
                },
                {
                    name: "目标湿球",
                    type: 'line',
                    smooth: true,
                    lineStyle: {
                        width: 1,
                        type: 'dashed'
                    },
                    data: param.twbList
                },
                {
                    name: "湿球温度",
                    type: 'line',
                    smooth: true,
                    data: param.wbtList
                },
                {
                    name: "火力档位",
                    type: 'line',
                    smooth: false,
                    showSymbol: false,
                    symbolSize: -1,
                    lineStyle: {
                        width: 0, // 线宽是0
                        color: 'rgba(0, 0, 0, 0)' // 线的颜色是透明的
                    },
//                    stack: '总量',
                    data: param.gearsList
                }
            ]
    };
    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
}

