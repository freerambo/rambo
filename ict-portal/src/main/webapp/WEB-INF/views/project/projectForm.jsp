<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>Project Management</title>
<script type="text/javascript" src="${ctx}/static/ckeditor/ckeditor.js"></script>
</head>

<body>
	<form id="inputForm" action="${ctx}/project/${action}" method="post"
		class="form-horizontal">
		<input type="hidden" name="id" value="${project.id}" />
		<fieldset>
			<legend>
				<small>Operate Project</small>
			</legend>
			<div class="control-group">
				<label for="project_title" class="control-label">Title:</label>
				<div class="controls">
					<input type="text" id="project_title" name="title"
						value="${project.title}" class="input-large required" minlength="10" />
				</div>
			</div>
			<div class="control-group">
				<label for="description" class="control-label">description:</label>
				<div class="controls">
					<textarea id="description" name="description" class="input-large">${project.description}</textarea>
				</div>
			</div>
			<div class="control-group">
				<label for="img" class="control-label">Cover Picture (450X300):</label>
				<div class="controls">
					<input id="img" name="img" class="input-large"
						value="${project.img}" /> 
				</div>
			</div>

			<div class="control-group">
				<label for="content" class="control-label">Content:</label>
				<div class="controls">
					<textarea id="content" name="content" cols="20" rows="2"
						class="ckeditor">${project.content}</textarea>
				</div>
			</div>
			<div class="control-group">
				<label for="type" class="control-label">Type:</label>
				<div class="controls">
				
				<select class="form-control" name="type" id="type">
						<c:if test="${project.type != null}">
								<option value="${project.type }" selected>${type}</option>
						</c:if>
						<c:if test="${not empty types}">
							<c:forEach items="${types}" var="type">
								<option value="${type.key }">${type.value }</option>
							</c:forEach>
						</c:if>
					</select>
				
				</div>
			</div>	

			<div class="control-group">
					<label for="TeamMember" class="control-label">Project Members:</label>
					<div class="controls">					
					<select multiple class="form-control" name="projectMembers"  id="projectMembers">
						<option value="-1" >Select the Team members</option>
						<c:if test="${not empty project.projectMembers}">
							<c:forEach items="${project.projectMembers}" var="user">
								<option selected value="${user.id }">${user.name }</option>
							</c:forEach>
						</c:if>
						<c:if test="${not empty users}">
							<c:forEach items="${users}" var="user">
								<option value="${user.id }">${user.name }</option>
							</c:forEach>
						</c:if>
					</select>
				</div>
			</div>

			<div class="form-actions">
				<input id="submit_btn" class="btn btn-primary" type="submit"
					value="Submit" />&nbsp; <input id="cancel_btn" class="btn"
					type="button" value="Back" onclick="history.back()" />
			</div>
		</fieldset>



	</form>
	<script>
		$(document).ready(function() {
			//focus on the 1st input box
			$("#project_title").focus();
			//register validation for inputForm
			$("#inputForm").validate();
		});
	</script>
</body>
</html>
