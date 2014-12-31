<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<div class="course-list-item draft-course {{mcourse.past?'past-course':''}}">
	<div class="image-wrap"
		style="background-image: url('{{mcourse.imageUrl}}')">
		<a class="overlay-link" href="#/edit/{{mcourse.id}}"></a>
		<div class="inner-image-wrap">
			<strong>25</strong>декабря {{mcourse.scheduleInfo.nextEvent}}
		</div>
	</div>

	<div class="text-wrap">
		<a class="overlay-link" href="#/home/edit/{{mcourse.id}}"></a>
		<div class="course-tools">
			<!--            кнопка редактирования-->
			<a href="#/edit/{{mcourse.id}}"><i class="glyphicon glyphicon-pencil"></i></a>
			<!--            кнопка удаления	если курс НЕ опубликован-->
			<a href="#delete"><i class="glyphicon glyphicon-remove"></i></a>
		</div>

		<h4>{{mcourse.name}}</h4>
		<h3>{{mcourse.name}}</h3>
		<time>
			<!--индикатор повторяемости	если в курс входит хотябы один повторяющийся урок-->
			<i class="glyphicon glyphicon-repeat"></i> 16:00 - 20:00 ({{mcourse.totalDurationAsString}})
		</time>

	</div>
</div>



 
		 

		 
			 
		 
 

