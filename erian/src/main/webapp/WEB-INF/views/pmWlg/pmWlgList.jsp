<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
				<label>NO.：</label> <input type="text"  id="search_EQ_pmNum" name="search_EQ_pmNum" class="input-medium" value="${param.search_EQ_pmNum}"> 
				<button type="submit" class="btn" id="search_btn">Search</button>
		    </form>
	    </div>
	    <tags:sort/>
	</div>
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th> ID </th><th> PM_Num </th><th> DateTime </th><th>realPower</th></tr></thead>
		<tbody>
		<%
			String s = new String("hello");
		%>
		<c:forEach items="${pmWlgs.content}" var="pmWlg">
			<tr>
				<td>${pmWlg.id}</td>
				<td><a href="${ctx}/pmWlg/?search_EQ_pmNum=${pmWlg.pmNum}">${pmWlg.pmNum}</a></td>
				<td>${pmWlg.dateTime}</td>
				<td>${pmWlg.realPower}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<tags:pagination page="${pmWlgs}" paginationSize="12"/>

	<div><a class="btn btn-default btn-lg disabled" >Minutely</a>&nbsp;&nbsp;
	<a href="${ctx}/pmWlg/tran/hour" class="btn" >Hourly</a>&nbsp;&nbsp;
	<a href="${ctx}/pmWlg/tran/day" class="btn" >Daily</a>&nbsp;&nbsp;
	<a href="${ctx}/pmWlg/tran/week" class="btn" >Weekly</a>&nbsp;&nbsp;
	<a href="${ctx}/pmWlg/tran/month" class="btn" >Monthly</a></div>
	



<div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
	
</body>
</html>
	