<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<div class="course-list-item" ng-class="{'past-course': mcourse.past}">
	<div class="image-wrap"
		style="background-image: url('{{mcourse.imageUrl}}')">
		<a class="overlay-link" href="/course/info?id={{mcourse.id}}"></a>
		<div class="inner-image-wrap">
			<strong>{{mcourse.scheduleInfo.nextEvent.startDate}}</strong>{{mcourse.scheduleInfo.nextEvent.startMonth}}
		</div>

	</div>


	<div class="text-wrap">
		<a class="overlay-link" href="/course/info?id={{mcourse.id}}"></a>
		<div class="course-tools">
            <a href="#/edit/{{mcourse.id}}"><i class="glyphicon glyphicon-pencil"></i></a>
            <small class="course-id">Рейтинг: *****  ID-{{mcourse.id}} </small>
		</div>

		<h4 ng-show="!mcourse.single">{{mcourse.name}}</h4>
		<h3>{{mcourse.scheduleInfo.nextEvent.name}}</h3>
		
		<time>
			<!--индикатор повторяемости	если в курс входит хотябы один повторяющийся урок-->
			<i ng-show="mcourse.recurrent" class="glyphicon glyphicon-repeat"></i> {{mcourse.scheduleInfo.nextEvent.times}} ({{mcourse.totalDurationAsString}})
		</time>

		<div>
			записалось: <span class="label label-danger">-122</span>
		</div>
	</div>
</div>



 
		 

		 
			 
		 
 

