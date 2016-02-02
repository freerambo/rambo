<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<spring:message code="main.delete"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>UploadFile Management</title>
	
</head>

<body>
		<legend><small>List Uploaded Files</small></legend>
	
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
		<thead><tr><th>Name</th><th>Type</th><th>Author</th><th>UploadTime</th><th>status</th></tr></thead>
		<tbody>
		<c:forEach items="${uploadFiles.content}" var="uploadFile">
			<tr>
				<td>${uploadFile.name}</td>
				<td>${uploadFile.type}</td>
				<td>${uploadFile.author.name}</td>
				<td>
			<fmt:formatDate value="${uploadFile.uploadTime}" pattern="yyyy-MM-dd HH:mm:ss" />
				
				</td>
				
				<td>${uploadFile.status}</td>
				<td>		
			<div class="btn-group pull-right">
				<!--  <button class="btn">Action</button>  --><button data-toggle="dropdown" class="btn dropdown-toggle"><span class="caret"></span></button>
				<ul class="dropdown-menu">
					<li>
						<a href="${ctx }/uploadFile/view/${uploadFile.id}" target="_blank">View</a>
					</li>
					<li>
						<a href="${ctx }/uploadFile/download/${uploadFile.id}">Download</a>
					</li>
					<li>
						<a href="${ctx }/uploadFile/delete/${uploadFile.id}">
<spring:message code="main.delete"/>
</a>
					</li>
				</ul>
			</div>
					
				</td>
				
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<tags:pagination page="${uploadFiles}" paginationSize="5"/>

	<div><a class="btn" href="${ctx}/uploadFile/create">Upload File</a></div>
</body>
</html>
