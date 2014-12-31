<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
<meta charset="UTF-8" />
<title>SKILL2PEER</title>

<style>
time {
	-webkit-font-smoothing: antialiased;
	color: rgb(153, 153, 153);
	cursor: auto;
	display: inline;
	font-family: 'Roboto', Arial, sans-serif;
	font-size: 12px;
	font-style: normal;
	font-weight: normal;
	height: auto;
	letter-spacing: 0.75px;
	line-height: 15px;
	text-transform: uppercase;
	width: auto;
}
    
.img-wrap{
    border: 1px solid rgb(153, 153, 153);
}

</style>

<script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?libraries=places&sensor=false"></script>

<script data-main="/static/js/main-edit-course"
	src="${pageContext.request.contextPath}/static/js/bower_components/requirejs/require.js"></script>

</head>
<body>

	

    
    
<div class="container" ng-view></div>
</body>
</html>