<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.ExcessiveAttemptsException"%>
<%@ page import="org.apache.shiro.authc.IncorrectCredentialsException"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>Login</title>
</head>

<body>
<div align="center">

<h1>Welcome to ICT Portal</h1> </div>

<div class="container-fluid">
	<div class="row-fluid">
		<div class="span12">
<%-- <img src="${ctx }/static/images/qfast.jpg" />
 --%> <img src="${ctx }/static/images/ERIANresized.jpg" /> 

			<dl>
				<dt>
						Vision

				</dt>
				<dd>
To be a leading research institute for innovative energy solutions
				</dd>
				<dt>

Mission
				</dt>
				<dd>
To be a centre-of-excellence for advanced research, development, and demonstration of innovative energy solutions with global impact.
​				</dd>
				<dt>
					Objectives
				</dt>
				<dd>

Advance research aimed at improving the efficiency of current energy systems while maximizing synergistic effects of alternative energy sources
Foster a multidisciplinary and collaborative environment for scientists, engineers, social scientists, and others to interact and together promote relevant energy solutions and policies for the future
Enable interactions with research, policy, and economic development authorities as well as the Industry through collaborative knowledge creation and technology transfer in areas of strategic importance to Singapore and beyond.​
					</dd>
			</dl>
		</div>
	</div>
</div>
	


</body>
</html>
