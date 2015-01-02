<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>



<div ng-repeat="ls in course.lessons">

	<div class="lesson-list-item" ng-class="{'active grey-bg':ls == lesson}">
       
        <a ng-click="selectLesson(ls)">edit</a>
		<div class="text-wrap">
			
			<div class="course-tools">
				<a ng-click="deleteLesson(ls)"><i class="glyphicon glyphicon-remove"></i></a>
			</div>

			<h3><span>{{$index+1}}.</span> {{ls.name}}</h3>
			<time>
				<i ng-show="ls.schedule.recurrent" 
                   class="glyphicon glyphicon-repeat"></i> {{ls.schedule.dateTime | date:"dd.MM.yyyy HH:mm"}}
			</time>

		</div>
	</div>


	<!-- <div class="row" 
            ng-class="{'active':ls == lesson}">
		<div class="col-xs-1">{{1+$index}}</div>
		<div class="col-xs-11">
			<h4>
				<a data-ng-click="selectLesson(ls)"> {{ls.name}} </a>
				<button data-ng-click="deleteLesson(ls)" class="btn btn-default btn-xs pull-right">
					<span class="glyphicon glyphicon-remove"></span>
				</button>
			</h4>

			<div>
				<time>
					{{ls.schedule.dateTime | date:"dd.MM.yyyy HH:mm"}}<span data-ng-show="ls.schedule.recurrent"
						class="glyphicon glyphicon-repeat"></span>
				</time>
				<address>{{ls.locaion.address}}</address>
			</div>

		</div>
	 </div>  -->
</div>




<!--        end of panel-->
<!-- end of lessons list -->