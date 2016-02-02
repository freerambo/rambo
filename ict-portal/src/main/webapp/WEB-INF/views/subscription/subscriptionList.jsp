<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
				<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>Subscription Management</title>
</head>

<body>
	<legend><small>Operate Subscription</small></legend>

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
		<thead><tr><th>Email</th><th>Type</th><th>Status</th><th>Create Date</th><th>Operate</th></tr></thead>
		<tbody>
		<c:forEach items="${subscriptions.content}" var="subscription">
			<tr>
				<td><a href="${ctx}/subscription/update/${subscription.id}">${subscription.email}</a></td>
				
				<td>${subscription.type}</td>
				<td>
					<fmt:formatDate value="${subscription.createTime}" pattern="yyyy-MM-dd HH:mm:ss" />
				</td>
				<td>${subscription.status}</td>
				<td><a href="${ctx}/subscription/delete/${subscription.id}" onclick="return confirm('are you sure to delete this item?')">
				<spring:message code="main.delete"/>
				
				</a></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<tags:pagination page="${subscriptions}" paginationSize="5"/>

	<div><a class="btn" href="${ctx}/subscription/create">Add Subscription</a></div>
</body>
</html>
