<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="main" style="height:500px;width:760px"></div>

<script src="/js/plugins/echarts/echarts-all.js"></script>
<script>
    //初始化
    var myChart = echarts.init(document.getElementById('main'));

    var option = {
        title : {
            text: '销售报表',
            subtext: '${groupType}',
            x:'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient : 'vertical',
            x : 'left',
            data:${groupTypes}
        },
        toolbox: {
            show : true,
            feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: false},
                magicType : {
                    show: true,
                    type: ['pie', 'funnel'],
                    option: {
                        funnel: {
                            x: '25%',
                            width: '50%',
                            funnelAlign: 'left',
                            max: ${maxAmount}
                        }
                    }
                },
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        calculable : true,
        series : [
            {
                name:'销售总额',
                type:'pie',
                radius : '55%',
                center: ['50%', '60%'],
                data:${datas}
            }
        ]
    };

    // 为echarts对象加载数据
    myChart.setOption(option);
</script>
</body>
</html>
