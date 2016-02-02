<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>Project Management</title>
</head>

<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success">
			<button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<div class="row">
	
	
	<div class="span7">
	
	<shiro:hasRole name="user,admin">
			<a class="btn" href="${ctx}/project/create">Create Project </a>&nbsp;
		</shiro:hasRole>
		
	<span  class="bg-red"> ICT-Core </span>&nbsp; <span class="bg-green">
				 NTU collaboration</span>  &nbsp; <span
				class="bg-other">  External</span>&nbsp;<span class="bg-blue">Legacy system</span>

	
	</div>
		<div class="span4 offset6">
			<form class="form-search" action="#">
				<label>Title：</label> <input type="text" name="search_LIKE_title"
					class="input-medium" value="${param.search_LIKE_title}">
				<button type="submit" class="btn" id="search_btn">Search</button>
			</form>
		</div>
		<tags:sort />
	</div>

	<table id="contentTable" class="table  table-bordered">
		<!--  table-striped table-condensed -->
		<thead>
			<tr>

				<th>Title</th>
				<th>Owner</th>
				<th>Location</th>
				<th>Developer</th>
			<!-- 	<th>Username</th>
				<th>Password</th> -->
				<!-- <th>Category</th> -->
				<th>Operations</th>


			</tr>
		</thead>
		<tbody>
			<c:forEach items="${projects.content}" var="project">

				<%-- 				<c:if test=" ${ project.category  = 'ICT-Core'}">
					<tr style="background-color: #FF0000">
				</c:if> --%>


				<tr <c:choose>
					<c:when test="${ project.category  == '1'}">
						class="bg-red"
					</c:when>

					<c:when test="${ project.category  == '2'}">
						class="bg-green" 
					</c:when>
					<c:when test="${ project.category  == '3'}">
						class="bg-other"
					</c:when>
					<c:otherwise>
						 class="bg-blue"
					</c:otherwise>
				</c:choose>
">
					<!-- <tr> -->
					<%-- 					<td><a href="${project.link}" target="_blank">${project.title}</a></td>
 --%>
					<td><b>${project.title}</b></td>
					<td>${project.owner}</td>
					<td>${project.location}</td>
					<td>${project.developer}</td>
					<%-- <td>${project.username}</td>
					<td>${project.password}</td> --%>
					<%-- <td>
					
					<c:choose>
					<c:when test="${ project.category  == '1'}">
						ICT-Core
					</c:when>
					<c:when test="${ project.category  == '2'}">
						Externel
						
					</c:when>
					<c:when test="${ project.category  == '3'}">
						Corporate
					</c:when>
					<c:otherwise>
						Outdate
					</c:otherwise>
				</c:choose>
					</td> --%>

					<td><a href="${project.link}" target="_blank">view</a> <shiro:hasRole
							name="admin">
							<a href="${ctx}/project/update/${project.id}">update</a>
							<a href="${ctx}/project/delete/${project.id}">delete</a>
						</shiro:hasRole></td>


				</tr>
			</c:forEach>
		</tbody>
	</table>

	<tags:pagination page="${projects}" paginationSize="5" />


	<div class="control-group">
		<shiro:hasRole name="admin">
			<a class="btn" href="${ctx}/project/create">Create Project </a>&nbsp;
		</shiro:hasRole>
		
		<span  class="bg-red"> ICT-Core </span>&nbsp; <span class="bg-green">
				 NTU collaboration </span> &nbsp; <span
				class="bg-other">  External</span>&nbsp;<span class="bg-blue">Legacy system</span>
	
		
	</div>


</body>
</html>
