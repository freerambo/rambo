<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>Restful API List</title>
</head>

<body>

<h3>Restful API List</h3>
<h4>Enquire API</h4>
<ul>
	<li>get task list ： <a href="${ctx}/api/v1/task">${ctx}/api/v1/task</a>(sample)</li>
	<li>get task(id=1) ： <a href="${ctx}/api/v1/task/1">${ctx}/api/v1/task/1</a>(sample)</li>
</ul>
<ul>
	<li>get spms data hours ： <a target="blank" href="${ctx}/api/v1/pmWlg/hours">${ctx}/api/v1/pmWlg/hours</a></li>
	<li>get spms data days ： <a target="blank" href="${ctx}/api/v1/pmWlg/days">${ctx}/api/v1/pmWlg/days</a></li>
</ul>

<h4>NMS data API </h4>

<ul>

<li>get nms data hourly ： <a target="blank" href="${ctx}/api/v1/nms/hours">${ctx}/api/v1/nms/hours</a> method=GET, (from and end with format yyyy-MM-dd HH:mm:ss)</li>
<li>get nms data daily ： <a target="blank" href="${ctx}/api/v1/nms/days">${ctx}/api/v1/nms/days</a> method=GET, (from and end with format yyyy-MM-dd)</li>
<li>get nms data weekly ： <a target="blank" href="${ctx}/api/v1/nms/weeks">${ctx}/api/v1/nms/weeks</a> method=GET, (from and end with format yyyy-MM-dd)</li>
<li>get nms data monthly ： <a target="blank" href="${ctx}/api/v1/nms/months">${ctx}/api/v1/nms/months</a> method=GET, (from and end with format yyyy-MM-dd) </li>

</ul>
<ul>
	<li>get all Hourly NMS data ： <a target="blank" href="${ctx}/api/v1/nms/allHours">${ctx}/api/v1/nms/allHours</a></li>
	<li>get all Daily NMS data ： <a target="blank" href="${ctx}/api/v1/nms/allDays">${ctx}/api/v1/nms/allDays</a></li>
	<li>get all Weekly NMS data ： <a target="blank" href="${ctx}/api/v1/nms/allWeeks">${ctx}/api/v1/nms/allWeeks</a></li>
	<li>get all Monthly NMS data ： <a target="blank" href="${ctx}/api/v1/nms/allMonths">${ctx}/api/v1/nms/allMonths</a></li>

</ul>

<h4>Modification API</h4>
<ul>
	<li>create task ：${ctx}/api/v1/task method=Post, consumes=JSON (sample)</li>
	<li>update task(id=1) ：${ctx}/api/v1/task/1 method=Put, consumes=JSON (sample)</li> 
</ul>
</body>
</html>
