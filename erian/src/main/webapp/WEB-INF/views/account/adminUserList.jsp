<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>User management</title>
</head>

<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>LoginName</th><th>UserName</th><th>RegisterDate<th>Operate</th></tr></thead>
		<tbody>
		<c:forEach items="${users}" var="user">
			<tr>
				<td><a href="${ctx}/admin/user/update/${user.id}">${user.loginName}</a></td>
				<td>${user.name}</td>
				<td>
					<fmt:formatDate value="${user.registerDate}" pattern="yyyy-MM-dd HH:mm:ss" />
				</td>
				<td>
<c:choose>
    <c:when test="${user.loginName != 'admin'}">
      <a href="${ctx}/admin/user/delete/${user.id}">delete</a>
    </c:when>
    <c:otherwise>
        delete
    </c:otherwise>
</c:choose>				
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>
