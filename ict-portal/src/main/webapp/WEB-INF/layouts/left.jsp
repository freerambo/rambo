<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<div id="leftbar" class="span2">
	<h1>Accounts</h1>
	<div class="submenu">
		<a id="account-tab" href="${ctx}/admin/user/">User </a> <a
			id="account-tab" href="${ctx}/team/">Team </a> <a id="account-tab"
			href="${ctx}/role/">Role </a> <a id="account-tab"
			href="${ctx}/subscription/">Subscription</a>

	</div>
	<h1>Production</h1>
	<div class="submenu">
		<a id="web-tab" href="${ctx}/project">Project</a> <a
			id="webservice-tab" href="${ctx}/energy/#services">Service</a>
					<a id="webservice-tab" href="${ctx}/news">News & Events</a>
			
	</div>
	<h1>Resources</h1>
	<div class="submenu">
		<a id="web-tab" href="${ctx}/uploadFile">Files</a> <a
			id="webservice-tab" href="${ctx}/assets">Assets</a>

	</div>
	<h1>System</h1>
	<div class="submenu">
		<a id="persistence-tab" href="${ctx}/api">APIs</a> <a
			id="persistence-tab" href="${ctx}/configure">Configuration</a>
		<a id="persistence-tab" href="${ctx}/energy">Portal</a>
	</div>
</div>