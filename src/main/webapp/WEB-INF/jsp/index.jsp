<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
<meta charset="UTF-8" />
<title>SKILL2PEER</title>

<style>
 

</style>

<link rel="stylesheet/less" type="text/css" href="${pageContext.request.contextPath}/static/css/colorsections.less" />
<!-- <script src="//cdnjs.cloudflare.com/ajax/libs/less.js/2.1.0/less.min.js"></script> -->
<script src="${pageContext.request.contextPath}/static/js/vendor/less.min.js"></script>

<script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?libraries=places&sensor=false"></script>

<script data-main="/static/js/main-edit-course"
	src="${pageContext.request.contextPath}/static/js/bower_components/requirejs/require.js"></script>

</head>
<body>

	

    
    
<div class="container" ng-view></div>
</body>
</html>