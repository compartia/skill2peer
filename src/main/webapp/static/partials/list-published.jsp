<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<!--<div class="related-courses-wrap">-->
	<div class="panel">
		
		<div class="panel-body" ng-cloak>
            <div ng-repeat="mcourse in myCourses | filter : {status: 'PUBLISHED'}">
                <ng-include src="'/static/partials/course-list-item.jsp'"></ng-include>
            </div>
		</div>
	</div>
<!--</div>-->