<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>



<div ng-repeat="ls in course.lessons">

	<div ng-click="selectLesson(ls)" class="lesson-list-item" ng-class="{'active':ls == lesson}">

		<div class="text-wrap">

			<div class="course-tools">
				<a ng-click="deleteLesson(ls)"><i class="glyphicon glyphicon-remove"></i></a>
			</div>

			<time>
				<span ng-show="ls.schedule.recurrent" class="glyphicon glyphicon-repeat"></span> {{ls.schedule.dateTime |
				date:"dd.MM.yyyy"}} <strong>{{ls.schedule.dateTime | date:"HH:mm"}}</strong>
			</time>
			<h3>
				<span class="index">{{$index+1}}.</span> {{ls.name}}
			</h3>
			<address>
				<img ng-show="ls.location.icon!=null" src="{{ls.location.icon}}" /> <span ng-show="ls.location.html"
					ng-bind-html="getTrustedHtml(ls.location.html)"></span>
			</address>
		</div>
	</div>



</div>


<!-- end of lessons list -->