<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="main" style="height:500px;width:760px" ></div>

<script src="/js/plugins/echarts/echarts-all.js"></script>
<script>
    var myChart = echarts.init(document.getElementById('main'));

    var option = {
        title : {
            text: '销售报表',
            subtext: '${groupType}',
            x:'center'
        },
        tooltip : {
            trigger: 'axis'
        },
        legend: {
            data:['销售总额'],
            x:'left'
        },
        toolbox: {
            show : true,
            feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: false},
                magicType : {show: true, type: ['line', 'bar']},
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        calculable : true,
        xAxis : [
            {
                type : 'category',
                data : ${groupTypes}
            }
        ],
        yAxis : [
            {
                type : 'value'
            }
        ],
        series : [
            {
                name:'销售总额',
                type:'bar',
                data:${totalAmounts},
                markPoint : {
                    data : [
                        {type : 'max', name: '最大值'},
                        {type : 'min', name: '最小值'}
                    ]
                },
                markLine : {
                    data : [
                        {type : 'average', name: '平均值'}
                    ]
                }
            }
        ]
    };

    // 为echarts对象加载数据
    myChart.setOption(option);
</script>
</body>
</html>
