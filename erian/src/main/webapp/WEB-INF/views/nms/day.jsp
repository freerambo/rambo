
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script src="${ctx}/static/highcharts/highcharts.js" type="text/javascript"></script>
<script src="${ctx}/static/highcharts/exporting.js" type="text/javascript"></script>
<title>NMS Exhibition</title>
<a href="${ctx}/nms/hours" class="btn " >Hourly plot</a>&nbsp;&nbsp;<a href="${ctx}/nms/days" class="btn " >Daily plot</a>
<div id="container" style="height: 400px; min-width: 310px"></div>
<br><br><hr><br>
<div id="container1" style="height: 400px; min-width: 310px"></div>
<br>
<script >
var d=new Array();

<c:forEach items="${date}" var="d">
d.push("${d}");
</c:forEach>

$(function () {

	
	

    $('#container').highcharts({
        chart: {
            type: 'column'
        },
        title: {
            text: 'DAILY NMS DATA BY AREA'
        },
        subtitle: {
            text: 'Source: ict.eri.ntu.edu.sg'
        },
        xAxis: {
            categories: d
            //JSON.stringify(xAsis)
            	//['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']

        },
        yAxis: {
            
            title: {
                text: '${unit }'
            }
        },
        tooltip: {
            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                '<td style="padding:0"><b>{point.y:.1f} kwh</b></td></tr>',
            footerFormat: '</table>',
            shared: true,
            useHTML: true
        },
        credits:{
        	enabled:false
        },
        plotOptions: {
            column: {
                pointPadding: 0.2,
                borderWidth: 0
            }
        },
        series: [{
            name: "NTU Laboratory",
            data: ${data1}
        }, {
            name: "Nanyang Crescent",
            data: ${data2}
        }, {
            name: "Nanyang Terrace",
            data: ${data3}
        }, {
            name: "Nanyang Meadow",
            data: ${data4}
        }, {
            name: "Nanyang Valley",
            data: ${data5}
        }, {
            name: "NIEO",
            data: ${data6}
            	}]
    });

});


$(function () {
    $('#container1').highcharts({
        chart: {
            type: 'column'
        },
        title: {
            text: 'DAILY NMS DATA BY STACKED COLUMN'
        },
        xAxis: {
        	categories: d
        },
        yAxis: {
            min: 0,
            title: {
                text: '${unit }'
            },
            stackLabels: {
                enabled: true,
                style: {
                    fontWeight: 'bold',
                    color: (Highcharts.theme && Highcharts.theme.textColor) || 'gray'
                }
            }
        },
        legend: {
            align: 'right',
            x: -30,
            verticalAlign: 'bottom',
            y: 25,
            floating: true,
            backgroundColor: (Highcharts.theme && Highcharts.theme.background2) || 'white',
            borderColor: '#CCC',
            borderWidth: 1,
            shadow: false
        },
        tooltip: {

        headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
        pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
            '<td style="padding:0"><b>{point.y:.1f} kwh</b></td></tr>',
        footerFormat: '</table>',
        shared: false,
        useHTML: true
        
        },
        plotOptions: {
            column: {
                stacking: 'normal',
                dataLabels: {
                    enabled: false,
                    color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white',
                    style: {
                        textShadow: '0 0 3px black'
                    }
                }
            }
        },
        credits:{
        	enabled:false
        },
        series: [{
            name: "NTU Laboratory",
            data: ${data1}
        }, {
            name: "Nanyang Crescent",
            data: ${data2}
        }, {
            name: "Nanyang Terrace",
            data: ${data3}
        }, {
            name: "Nanyang Meadow",
            data: ${data4}
        }, {
            name: "Nanyang Valley",
            data: ${data5}
        }, {
            name: "NIEO",
            data: ${data6}
    	}]
    });

});
</script>
