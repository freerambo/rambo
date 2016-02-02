<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.slf4j.Logger,org.slf4j.LoggerFactory" %>
<%	
	//设置返回码200，避免浏览器自带的错误页面
	response.setStatus(200);
	//记录日志
	Logger logger = LoggerFactory.getLogger("500.jsp");
	logger.error(exception.getMessage(), exception);
%>

<!DOCTYPE html>
<html>
<head>
	<title>500 - Server Error</title>
</head>

<body>
	<h2>500 - internal Sever error.</h2>
	<p><a href="<c:url value="https://${pageContext.request.serverName}:${pageContext.request.serverPort}/manager"/>">restart the server</a></p>
</body>
</html>
