<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title> PM_WLG  Management </title>
	<script src="${ctx}/static/highcharts/highcharts.js" type="text/javascript"></script>
<script src="${ctx}/static/highcharts/exporting.js" type="text/javascript"></script>
	
</head>

<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	
<%-- 	<div>
		<c:forEach items="${pmWlgs}" var="pmWlg">
		${pmWlg[0]},${pmWlg[1]} <br/>
		</c:forEach>
	</div> --%>
	

	
	<div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
	
	<script type="text/javascript">
	
	$(function () {
	    $('#container').highcharts({
	        chart: {
	            type: 'line'
	        },
	        title: {
	            text: 'Hourly Average Power'
	        },
	        subtitle: {
	            text: "Current Time : ${date}"
	        },
	        credits: {
	            enabled: false
	         },
	        xAxis: {
	        	
	        	title:{text: 'DateTime (hours)'},
	        	//type: 'datetime',
	         
	           // categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
	       		//categories: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', 
	              //       '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23', '24']
	        	categories:${times}
	        },
	        yAxis: {
	            title: {
	                text: 'Energy (kwh)'
	            }
	        },
	        plotOptions: {
	        	
	            line: {
	                dataLabels: {
	                    enabled: true
	                },
	                enableMouseTracking: true
	            }
	        },
	        
	        series: [{
	            name: 'Previous',
	            data: ${pres}
	        }, {
	            name: 'Current',
	            data: ${values}
	        }]
	    });
	});
	
	</script>
	
	<div>
	<a href="${ctx}/pmWlg/tran/hour" class="btn" >Raw Data</a><br/>
	<a href="${ctx}/pmWlg" class="btn" >Minutely</a>&nbsp;&nbsp;
	<a class="btn btn-default btn-lg disabled">Hourly</a>&nbsp;&nbsp;
	<a href="${ctx}/pmWlg/tran/day" class="btn" >Daily</a>&nbsp;&nbsp;
	<a href="${ctx}/pmWlg/tran/week" class="btn">Weekly</a>&nbsp;&nbsp;
	<a href="${ctx}/pmWlg/tran/month" class="btn" >Monthly</a>
	</div>
	

</body>
</html>
