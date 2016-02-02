<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.ExcessiveAttemptsException"%>
<%@ page import="org.apache.shiro.authc.IncorrectCredentialsException"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>Login</title>
</head>

<body>
	<form id="loginForm" action="${ctx }/login" method="post" class="form-horizontal">
	<%
	String error = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
	if(error != null){
	%>
		<div class="alert alert-error input-medium controls">
			<button class="close" data-dismiss="alert">×</button>Login Failure, <font color= "red">${username }</font> please try again.
		</div>
	<%
	}
	%>
		<div class="control-group">
			<label for="username" class="control-label">LoginName:</label>
			<div class="controls">
				<input type="text" id="username" name="username"  value="${username}" class="input-medium required"/>
			</div>
		</div>
		<div class="control-group">
			<label for="password" class="control-label">Password:</label>
			<div class="controls">
				<input type="password" id="password" name="password" class="input-medium required"/>
			</div>
		</div>
				
		<div class="control-group">
			<div class="controls">
				<label class="checkbox" for="rememberMe"><input type="checkbox" id="rememberMe" name="rememberMe"/> Remember me</label>
				<input id="submit_btn" class="btn btn-primary" type="submit" value="Login"/> 
				
				
				<%-- <a class="btn" href="${ctx}/register">Register</a>
			 	 --%>
			 	 				<input id="cancel_btn" class="btn" type="button" value="Back" onclick="history.back()"/>
			 	 
			 	 
			 	 <span class="help-block"><!-- (Admin: <b>admin/admin</b>,  User: <b>user/user</b>) --></span>
			</div>
		</div>
	</form>

	<script>
		$(document).ready(function() {
			$("#loginForm").validate();
		});
	</script>
</body>
</html>
