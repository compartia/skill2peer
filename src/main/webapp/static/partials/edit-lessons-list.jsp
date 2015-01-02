<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<!-- lessons list -->
<div class="panel edit-lessons-list">
	<!-- Default panel contents -->
	<div class="panel-heading">
		<span class="glyphicon glyphicon-time"></span> Расписание занятий
	</div>


	<!-- List group -->
	<div class="list-group">
        
        <div ng-repeat="ls in course.lessons" ng-class="{'active':ls == lesson}" >
            <ng-include src="'/static/partials/edit-lessons-item.jsp'"/>
        </div>
        
        <button ng-click="addLesson()"  class="btn btn-primary">Добавить урок</button>		

	</div>


</div>






<!--        end of panel-->
<!-- end of lessons list -->