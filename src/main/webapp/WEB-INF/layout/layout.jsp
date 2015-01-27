<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
 <meta name="viewport" content="width=device-width, initial-scale=1">

<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />


<title><sitemesh:write property="title" /> [SKILL2PEER]</title>
 
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/styles_min.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/style.css" />  
<link href='//fonts.googleapis.com/css?family=Istok+Web:400,700,400italic,700italic|Roboto:400,100,300,700,300italic&subset=latin,cyrillic-ext' rel='stylesheet' type='text/css'>

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

</body>
</html>