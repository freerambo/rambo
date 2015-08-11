<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
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
	</c:if>	<a href="${ctx}/pmWlg/tran/hours" class="btn" >Go Charts</a>
	
	<div class="row">
		<div class="span4 offset7">
			<form class="form-search" action="#">
				<label>NO.：</label> <input type="text" id="search_EQ_pmId" name="search_EQ_pmId" class="input-medium" value="${param.search_EQ_pmId}"> 
				<button type="submit" class="btn" id="search_btn">Search</button>
		    </form>
	    </div>
	    <tags:sort/>
	</div>

	<script>	
		$( ".form-search" ).validate({
			  rules: {
				  search_EQ_pmId: {
			      required: true,
			      number: true
			    }
			  }
			});
	</script>
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>pm_id </th><th> date_time </th><th>VALUE</th><th>MAX</th><th> DT_MAX </th><th>MIN</th><th>DT_MIN</th></tr></thead>
		<tbody>
		<c:forEach items="${pmWlgs.content}" var="pmWlg">
			<tr>
				<td><a href="${ctx}/pmWlg/tran/hour/?search_EQ_pmId=${pmWlg.pmId}">${pmWlg.pmId}</a></td>
				<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${pmWlg.dateTime}" /></td>
				<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${pmWlg.value}"/></td>
				<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${pmWlg.max}"/></td>
				<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${pmWlg.dtMax}" /></td>
				<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${pmWlg.min}"/></td>
				<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${pmWlg.dtMin}" /></td>
				
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<tags:pagination page="${pmWlgs}" paginationSize="12"/>

	<div><a href="${ctx}/pmWlg" class="btn" >Minutely</a>&nbsp;&nbsp;
	<a href="${ctx}/pmWlg/tran/hour" class="btn" >Hourly</a>&nbsp;&nbsp;
	<a href="${ctx}/pmWlg/tran/day" class="btn" >Daily</a>&nbsp;&nbsp;
	<a class="btn btn-default btn-lg disabled">Weekly</a>&nbsp;&nbsp;
	<a href="${ctx}/pmWlg/tran/month" class="btn" >Monthly</a>
	</div>
	
			<div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
	
	<script type="text/javascript">
	
	$(function () {
	    $('#container').highcharts({
	        chart: {
	            type: 'column'
	        },
	        title: {
	            text: '${type} Energy for ${build} of ${school}'
	        },
	        subtitle: {
	            text: "Current Time : ${date}"
	        },
	        credits: {
	            enabled: false
	         },
	        xAxis: {
	        	
	        	title:{text: 'DateTime (week)'},
	        	//type: 'datetime',
	         
	           // categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
	       		//categories: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', 
	              //       '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23', '24']
	        	categories:['pre-8', 'pre-7','pre-6','pre-5', 'pre-4', 'pre-3', 'pre-2', 'pre-1']
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
	        
	        series: [ {
	            name: 'Weekly',
	            data: ${values}
	        }]
	    });
	});
	
	</script>
	
</body>
</html>
