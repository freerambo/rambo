<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>Team Management</title>
</head>

<body>
	<form id="inputForm" action="${ctx}/team/${action}" method="post"
		class="form-horizontal">
		<input type="hidden" name="id" value="${team.id}" />
		<fieldset>
			<legend>
				<small>Operate Team</small>
			</legend>
			<div class="control-group">
				<label for="team_name" class="control-label">Team Name:</label>
				<div class="controls">
					<input type="text" id="team_name" name="name" value="${team.name}"
						class="input-large required" minlength="3" />
				</div>
			</div>
			<div class="control-group">
				<label for="description" class="control-label">Team Leader:</label>
				<div class="controls">

					<select class="form-control" name="master.id" id="master.id">
						<option value="-1">Select the Team Leader</option>
						<c:if test="${not empty users}">
							<c:forEach items="${users}" var="user">
								<option value="${user.id }">${user.name }</option>
							</c:forEach>
						</c:if>
					</select>


				</div>
			</div>
			<div class="control-group">
				<label for="status" class="control-label">Status:</label>
				<div class="controls">
					<%-- 					<input type="text" id="status" name="status" value="${team.status}" class="input-large required"/>
 --%>
					<select class="form-control" name="status" id="status">
						<c:forEach items="${status}" var="statu">
							<option value="${statu.key }">${statu.value }</option>
						</c:forEach>
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
			//聚焦第一个输入框
			$("#team_name").focus();
			//为inputForm注册validate函数
			$("#inputForm").validate();
		});
	</script>
</body>
</html>
