<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>News Management</title>
</head>

<body>
	<legend>
		<small>Operate News</small>
	</legend>

	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success">
			<button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<div class="row">
		<div class="span4 offset1">
			<form class="form-search" action="#">
				<label>Name：</label> <input type="text" name="search_LIKE_title"
					class="input-medium" value="${param.search_LIKE_title}">
				<button type="submit" class="btn" id="search_btn">Search</button>
			</form>
		</div>
		<tags:sort />
	</div>

	<table id="contentTable"
		class="table table-striped table-bordered table-condensed">
		<thead>
			<tr><th>Title</th>
				<th>Description</th>
				<th>Publisher</th>
				
				<th>Create Date</th>
				<th>Type</th>
				<th>Operate</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${newss.content}" var="news">
				<tr>
					<td><a href="${ctx}/news/update/${news.id}">${news.title}</a></td>
					<td>${news.description}</td>

					<td>${news.publisher.name}</td>
					<td><fmt:formatDate value="${news.createDate}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>					
					<td>${types[news.type]}</td>
					<td><a class='btn btn-danger btn-xs launchConfirm'  onclick="return confirm('are you sure to delete this item?')" href="${ctx}/news/delete/${news.id}">
					
<spring:message code="main.delete"/>
					</a>
					
					
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<tags:pagination page="${newss}" paginationSize="10" />

	<div>
		<a class="btn" href="${ctx}/news/create">Add News</a>
	</div>
</body>
</html>
