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
<ul>
<li>get nms data hourly ： <a target="blank" href="${ctx}/api/v1/nms/hours">${ctx}/api/v1/nms/hours</a></li>
<li>get nms data daily ： <a target="blank" href="${ctx}/api/v1/nms/days">${ctx}/api/v1/nms/days</a></li>
</ul>

<h4>Modification API</h4>
<ul>
	<li>create task ：${ctx}/api/v1/task method=Post, consumes=JSON (sample)</li>
	<li>update task(id=1) ：${ctx}/api/v1/task/1 method=Put, consumes=JSON (sample)</li> 
</ul>
</body>
</html>
