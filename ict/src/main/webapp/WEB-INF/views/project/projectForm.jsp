<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>Project Management</title>
</head>

<body>
	<form id="inputForm" action="${ctx}/project/${action}" method="post" class="form-horizontal">
		<input type="hidden" name="id" value="${project.id}"/>
		<fieldset>
			<legend><small>Manager Project </small></legend>
			<div class="control-group">
				<label for="project_title" class="control-label">Project Title:</label>
				<div class="controls">
					<textarea id="project_title" name="title" class="input-large required" >${project.title}</textarea>
				</div>
			</div>	
			<div class="control-group">
				<label for="owner" class="control-label">Project Owner:</label>
				<div class="controls">
					<textarea id="owner" name="owner" class="input-large required">${project.owner}</textarea>
				</div>
			</div>	
			<div class="control-group">
				<label for="link" class="control-label">Project Link:</label>
				<div class="controls">
					<textarea id="link" name="link" class="input-large required">${project.link}</textarea>
				</div>
			</div>	
			<div class="control-group">
				<label for="developer" class="control-label">Project Developers:</label>
				<div class="controls">
					<textarea id="developer" name="developer" class="input-large required">${project.developer}</textarea>
				</div>
			</div>	
			<div class="control-group">
				<label for="location" class="control-label">Project location:</label>
				<div class="controls">
					<textarea id="location" name="location" class="input-large required">${project.location}</textarea>
				</div>
			</div>	
			<div class="control-group">
				<label for="username" class="control-label">username:</label>
				<div class="controls">
					<input id="username" name="username" class="input-large required" value="${project.username}" />
				</div>
			</div>	
			<div class="control-group">
				<label for="password" class="control-label">password:</label>
				<div class="controls">
					<input id="password" name="password" class="input-large required" value="${project.password}"/>
				</div>
			</div>	
			<div class="control-group">
				<label for="category" class="control-label">category:</label>
				<div class="controls">
<%-- 					<textarea id="category" name="category" class="input-large">${project.category}</textarea>
 --%>					
					 <select class="form-control" id="category" name="category">
				        <option value = "1" <c:if test="${project.category == '1'}">
				        	selected
				        </c:if> >
				        ICT-Core
				        </option>
				        <option value="2" <c:if test="${project.category == '2'}">
				        	selected
				        </c:if>>NTU collaboration</option>
				        <option value="3" <c:if test="${project.category == '3'}">
				        	selected
				        </c:if>>External</option>
				        <option value="4" <c:if test="${project.category == '4'}">
				        	selected
				        </c:if>>Legacy system</option>
				      </select>
				
				</div>
				
				
				
			</div>	
			<div class="form-actions">
				<input id="submit_btn" class="btn btn-primary" type="submit" value="Submit"/>&nbsp;	
				<input id="cancel_btn" class="btn" type="button" value="Back" onclick="history.back()"/>
			</div>
		</fieldset>
	</form>
	<script>
		$(document).ready(function() {
			//聚焦第一个输入框
			$("#project_title").focus();
			//为inputForm注册validate函数
			$("#inputForm").validate();
		});
	</script>
</body>
</html>
