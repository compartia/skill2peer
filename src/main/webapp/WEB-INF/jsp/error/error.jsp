<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta charset="UTF-8" />
<title>[SKILL2PEER] error</title>
</head>
<body>
	<div>
		<p>ERROR: ${exception.message}</p>
	</div>

<pre>
	<c:forEach items="${exception.stackTrace}" var="ste">
		${ste} 
    </c:forEach>
</pre>
</body>
</html>