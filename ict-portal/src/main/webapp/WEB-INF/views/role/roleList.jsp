<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>Role Management</title>
</head>

<body>
		<legend><small>Operate Role</small></legend>
	
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<div class="row">
		<div class="span4 offset1">
			<form class="form-search" action="#">
				<label>Name：</label> <input type="text" name="search_LIKE_title" class="input-medium" value="${param.search_LIKE_title}"> 
				<button type="submit" class="btn" id="search_btn">Search</button>
		    </form>
	    </div>
	    <tags:sort/>
	</div>
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>Name</th><th>Permissions</th><th>Operate</th></tr></thead>
		<tbody>
		<c:forEach items="${roles.content}" var="role">
			<tr>
				<td><a href="${ctx}/role/update/${role.id}">${role.name}</a></td>
				<td>${role.permissions}</td>
				<td><a href="${ctx}/role/delete/${role.id}"  onclick="return confirm('are you sure to delete this item?')">
				<spring:message code="main.delete"/>
				
				</a></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<tags:pagination page="${roles}" paginationSize="5"/>

	<div><a class="btn" href="${ctx}/role/create">Add Role</a></div>
</body>
</html>
