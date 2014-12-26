<!DOCTYPE html>
<html lang="en" ng-app="skill2peerApp">
<head>
<meta charset="UTF-8" />
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>


<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />


<title><sitemesh:write property="title" /> [SKILL2PEER]</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/vendor/bootstrap.min.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/styles_min.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/style.css" />


<%-- <script data-main="${pageContext.request.contextPath}/static/js/main" 
src="${pageContext.request.contextPath}/static/js/bower_components/requirejs/require.js"></script> --%>
<%-- <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/vendor/jquery-2.1.1.min.js"></script>
 --%>


<script data-main="/static/js/main"
	src="${pageContext.request.contextPath}/static/js/bower_components/requirejs/require.js"></script>

<%-- <script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/vendor/flat-ui.min.js"></script> --%>

<!-- 	<script src="js/flat-ui.min.js"></script> -->
<sitemesh:write property="head" />


</head>
<body>



	<div class="page">
		<!-- NAVBAR -->
		 <%@include file="top-header.jsp"%>
		<!-- /NAVBAR -->

		<div class="content">
			<div id="view-holder">
				<sitemesh:write property="body" />
			</div>
		</div>
	</div>
	<%-- <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/vendor/flat-ui.min.js"></script> --%>
</body>
</html>