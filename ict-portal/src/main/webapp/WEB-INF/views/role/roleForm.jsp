<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>Role Management</title>
</head>

<body>
	<form id="inputForm" action="${ctx}/role/${action}" method="post" class="form-horizontal">
		<input type="hidden" name="id" value="${role.id}"/>
		<fieldset>
			<legend><small>Operate Role</small></legend>
			<div class="control-group">
				<label for="role_name" class="control-label">Name:</label>
				<div class="controls">
					<input type="text" id="role_name" name="name"  value="${role.name}" class="input-large required" minlength="3"/>
				</div>
			</div>	
			<div class="control-group">
				<label for="permissions" class="control-label">Permissions:</label>
				<div class="controls">
					<textarea id="permissions" name="permissions" class="input-large">${role.permissions}</textarea>
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
			$("#role_name").focus();
			//为inputForm注册validate函数
			$("#inputForm").validate();
		});
	</script>
</body>
</html>
