<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>Subscription Management</title>
</head>

<body>
	<form id="inputForm" action="${ctx}/subscription/${action}" method="post" class="form-horizontal">
		<input type="hidden" name="id" value="${subscription.id}"/>
		<fieldset>
			<legend><small>Operate Subscription</small></legend>
			<div class="control-group">
				<label for="subscription_email" class="control-label">Email:</label>
				<div class="controls">
					<input type="text" id="subscription_email" name="email"  value="${subscription.email}" class="input-large required" minlength="3"/>
				</div>
			</div>	
			<div class="control-group">
				<label for="status" class="control-label">Status:</label>
				<div class="controls">
					<input type="text" id="status" name="status"  value="${subscription.status}" class="input-large required"/>
				</div>
			</div>	
				<div class="control-group">
				<label for="type" class="control-label">Type:</label>
				<div class="controls">
					<input type="text" id="type" name="type"  value="${subscription.type}" class="input-large required" />
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
			$("#subscription_email").focus();
			//为inputForm注册validate函数
			$("#inputForm").validate();
		});
	</script>
</body>
</html>
