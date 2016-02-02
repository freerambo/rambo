<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%response.setStatus(200);%>

<!DOCTYPE html>
<html>
<head>
	<title>404 - page not exists</title>
</head>

<body>
	<h2>404 - page not exists.</h2>
	<p><a href="<c:url value="/"/>">Home</a></p>
</body>
</html>