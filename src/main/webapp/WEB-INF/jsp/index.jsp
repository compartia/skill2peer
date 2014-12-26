<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
<meta charset="UTF-8" />
<title>SKILL2PEER</title>
</head>
<body>
	<div class="page-header">
		<h4>
			<spring:message code="label.homepage.title" />
			<sec:authentication property="principal.firstName" />
			<sec:authentication property="principal.lastName" />
		</h4>
	</div>
	<div>
		<p>
			<spring:message code="text.homepage.greeting" />
		</p>
		
		<a href="/course/create" class="btn btn-default">создать курс</a>
	</div>
</body>
</html>