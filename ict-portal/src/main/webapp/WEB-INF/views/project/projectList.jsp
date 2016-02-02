<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
<title>Project Management</title>
<link href="${ctx}/static/styles/style.css" type="text/css"
	rel="stylesheet" />

</head>

<body>
	<!-- 	<legend>
		<small>Operate Project</small>
	</legend> -->

	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success">
			<button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>

	<!---->
	<div class="project-sec">
		<div class="container">
			<h2 class="team_head">Our Projects</h2>
			<div class="works">

				<c:forEach items="${projects.content}" var="project"
					varStatus="theCount">



					<div class="prjt-grid">
						<div class="box maxheight">
							<a href="${project.img}" class="gal"> <span></span><img
								src="${project.img}" class="img-rounded"
								style="height: 134px; width: 200px" alt="${project.title }"></a>
							<div class="text1">
								<a href="#">${project.title }</a>
								<p>${project.description }</p>
							</div>
							<p>
								<a class="btn btn-primary"
									href="${ctx}/project/update/${project.id}">Edit</a> <a
									class="btn btn-danger"
									href="${ctx}/project/delete/${project.id}"
									onclick="return confirm('are you sure to delete this item?')">

									<spring:message code="main.delete" />
								</a>
							</p>
						</div>
					</div>
					<c:if test="${theCount.count %3 == 0}">
						<div class="clearfix"></div>

					</c:if>
				</c:forEach>
				<div class="clearfix"></div>

				<tags:pagination page="${projects}" paginationSize="6" />

				<div>
					<a class="btn" href="${ctx}/project/create">Add Project</a>
				</div>
			</div>





		</div>


	</div>
	<!-- 
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
			<tr>
				<th>Name</th>
				<th>Permissions</th>
				<th>Operate</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${projects.content}" var="project">
				<tr>
					
					<td><a href="${ctx}/project/delete/${project.id}">删除</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
 -->


</body>
</html>
