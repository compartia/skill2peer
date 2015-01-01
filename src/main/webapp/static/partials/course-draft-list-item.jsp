<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<div class="course-list-item draft-course" ng-class="{'past-course': mcourse.past}">
	<div class="image-wrap"
		style="background-image: url('{{mcourse.imageUrl}}')">
		<a class="overlay-link" href="#/edit/{{mcourse.id}}"></a>
		<div class="inner-image-wrap">
			<strong>{{mcourse.scheduleInfo.nextEvent.startDate}}</strong>{{mcourse.scheduleInfo.nextEvent.startMonth}}
		</div>
	</div>

	<div class="text-wrap">
		<a class="overlay-link" href="#/edit/{{mcourse.id}}"></a>
		<div class="course-tools">
			<!--            кнопка редактирования-->
			<a href="#/edit/{{mcourse.id}}"><i class="glyphicon glyphicon-pencil"></i></a>
			<!--            кнопка удаления	если курс НЕ опубликован-->
			<a href="#delete"><i class="glyphicon glyphicon-remove"></i></a>
		</div>

		<h4 ng-show="!mcourse.single">{{mcourse.name}}</h4>
		<h3>{{mcourse.scheduleInfo.nextEvent.name}}</h3>
		<time>
			<!--индикатор повторяемости	если в курс входит хотябы один повторяющийся урок-->
			<i ng-show="mcourse.recurrent" class="glyphicon glyphicon-repeat"></i> {{mcourse.scheduleInfo.nextEvent.times}} ({{mcourse.scheduleInfo.nextEvent.totalDurationAsString}})
		</time>

	</div>
</div>



 
		 

		 
			 
		 
 

