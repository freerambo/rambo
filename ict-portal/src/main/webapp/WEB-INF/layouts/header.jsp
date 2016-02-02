<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<link href="/static/styles/default.css" type="text/css" rel="stylesheet" />

<div id="header" class="row">
	<div><h1><a href="${ctx }" style="color: #658A16;
    text-decoration: none;">ICT Portal</a><small>--Resources Management</small></h1></div>
	<div class="pull-left">
<a href="${pageContext.request.requestURL}?locale=en">English</a>, <a href="${pageContext.request.requestURL}?locale=zh_CN">Chinese(Simplified)</a>
	</div>
	<div class="pull-right"> 
		<shiro:guest><a href="${ctx}/login"><spring:message code="main.login" /></a></shiro:guest>
		<shiro:user><spring:message code="main.hello" />, <shiro:principal property="name"/> <a href="${ctx}/logout"><spring:message code="main.logout" /></a></shiro:user>
	</div>
</div>