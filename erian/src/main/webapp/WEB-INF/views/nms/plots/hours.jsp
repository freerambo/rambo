
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>


<script src="${ctx}/static/highcharts/stock/highstock.js" type="text/javascript"></script>
<script src="${ctx}/static/highcharts/stock/exporting.js" type="text/javascript"></script>
	

<div><a href="${ctx}/nms/days" class="btn" >Daily plot</a><a href="${ctx}/nms/day" class="btn" >Bar plot</a>
</div>
<div id="container" style="height: 400px; min-width: 310px"></div>
<script >
$(function () {

        // Create the chart
        $('#container').highcharts('StockChart', {
            chart: {
                events: {
                    load: function () {
                        this.setTitle(null, {
                            text: 'Built chart in ${date}'
                        });
                    }
                },
                zoomType: 'x'
            },

            rangeSelector: {
                
                buttons: [
{
    type: 'day',
    count: 1,
    text: '1d'
},
{
    type: 'day',
    count: 3,
    text: '3d'
},
                {
                    type: 'week',
                    count: 1,
                    text: '1w'
                }, {
                    type: 'month',
                    count: 1,
                    text: '1m'
                },
               , {
                    type: 'month',
                    count: 6,
                    text: '6m'
                }, {
                    type: 'year',
                    count: 1,
                    text: '1y'
                },
            
                {
                    type: 'all',
                    text: 'All'
                }],
                selected: 3
            },

            yAxis: {
                title: {
                    text: 'Energy (Kwh)'
                }
            },
            credits: {
	            enabled: false
	         },
            title: {
                text: 'Daily Energy Consumption of NMS, Nov. 2013-present'
            },

            subtitle: {
                text: 'Built chart in ...' // dummy text to reserve space for dynamic subtitle
            },

            series: [{
                name: 'nanyang',
                data: ${values}, 
                pointStart: Date.UTC(2013, 10, 05,0,0,0) ,
                pointInterval: 3600 * 1000,
                tooltip: {
                    valueDecimals: 1,
                    valueSuffix: 'Kwh'
                }
            },
            {
                name: 'nieo',
                data: ${nieos}, 
                pointStart: Date.UTC(2013, 10, 05,0,0,0) ,
                pointInterval: 3600 * 1000,
                tooltip: {
                    valueDecimals: 1,
                    valueSuffix: 'Kwh'
                }
            }
            ]

        });
    });



</script>

<div id="container1" style="height: 400px; min-width: 310px"></div>
