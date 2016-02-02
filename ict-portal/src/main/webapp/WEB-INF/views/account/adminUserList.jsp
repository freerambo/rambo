<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
	<title>User Management</title>
</head>

<body>

		<legend><small>User Management</small></legend>

	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th><spring:message code="main.username"/>
</th><th><spring:message code="main.name"/>
</th><th>Email</th><th>Phone</th><th><spring:message code="main.registerDate"/></th>
		<th>Status</th>
		<th><spring:message code="main.mgr"/>
</th></tr></thead>
		<tbody>
		<c:forEach items="${users}" var="user">
			<tr>
				<td><a href="${ctx}/admin/user/update/${user.id}">${user.loginName}</a></td>
				<td>${user.name}</td>
				<td>${user.email}</td>
				<td>${user.phone}</td>
				<td>
					<fmt:formatDate value="${user.registerDate}" pattern="yyyy-MM-dd HH:mm:ss" />
				</td>
				<td>${allStatus[user.status]}&nbsp;</td>
				<td><a href="${ctx}/admin/user/delete/${user.id}"  onclick="return confirm('are you sure to delete this item?')">
<spring:message code="main.delete"/>
</a></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>
