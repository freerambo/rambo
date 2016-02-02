<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>News Management</title>
	<script type="text/javascript" src="${ctx}/static/ckeditor/ckeditor.js"></script>
	
</head>

<body>
	<form id="inputForm" action="${ctx}/news/${action}" method="post" class="form-horizontal">
		<input type="hidden" name="id" value="${news.id}"/>
		<fieldset>
			<legend><small>Operate News</small></legend>
			<div class="control-group">
				<label for="news_title" class="control-label">Title:</label>
				<div class="controls">
					<input type="text" id="news_title" name="title"  value="${news.title}" class="input-large required" minlength="3"/>
				</div>
			</div>	
			<div class="control-group">
				<label for="description" class="control-label">Description:</label>
				<div class="controls">
					<input type="text" id="description" name="description"  value="${news.description}" class="input-large required"/>
				</div>
			</div>	
				<div class="control-group">
				<label for="content" class="control-label">Content:</label>
				<div class="controls">
					<textarea id="content" name="content" cols="20" rows="2"
						class="ckeditor">${news.content}</textarea>
				</div>
			</div>
			<div class="control-group">
				<label for="type" class="control-label">Type:</label>
				<div class="controls">
				
				<select class="form-control" name="type" id="type">
						<c:if test="${news.type != null}">
								<option value="${news.type }" selected>${type}</option>
						</c:if>
						<c:if test="${not empty types}">
							<c:forEach items="${types}" var="type">
								<option value="${type.key }">${type.value }</option>
							</c:forEach>
						</c:if>
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
			$("#news_title").focus();
			//为inputForm注册validate函数
			$("#inputForm").validate();
		});
	</script>
</body>
</html>
