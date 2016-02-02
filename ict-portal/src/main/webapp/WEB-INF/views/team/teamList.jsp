<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>Team Management</title>
</head>

<body>
	<legend><small>Operate Team</small></legend>

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
		<thead><tr><th>Name</th><th>Team Leader</th><th>Create Date</th><th>Operate</th></tr></thead>
		<tbody>
		<c:forEach items="${teams.content}" var="team">
			<tr>
				<td><a href="${ctx}/team/update/${team.id}">${team.name}</a></td>
				<td>${team.master.name}</td>
				<td>
									<fmt:formatDate value="${team.createDate}" pattern="yyyy-MM-dd HH:mm:ss" />
				</td>
				<td><a href="${ctx}/team/delete/${team.id}"  onclick="return confirm('are you sure to delete this item?')">

<spring:message code="main.delete"/>

</a></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<tags:pagination page="${teams}" paginationSize="5"/>

	<div><a class="btn" href="${ctx}/team/create">Add Team</a></div>
</body>
</html>
