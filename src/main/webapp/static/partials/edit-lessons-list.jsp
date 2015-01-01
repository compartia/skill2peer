<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<!-- lessons list -->
<div class="panel panel-default">
	<!-- Default panel contents -->
	<div class="panel-heading">
		<span class="glyphicon glyphicon-time"></span> Расписание занятий
	</div>


	<!-- List group -->
	<div class="list-group">
        
        <a href="#" ng-repeat="lesson in course.lessons" >
            <h4 class="list-group-item-heading">
				<small class="pull-right glyphicon glyphicon-remove"></small>
              {{lesson.name}}</h4>
            
            
            <p class="list-group-item-text text-info">
				<time>
					<strong>ВТ</strong> {{lesson.schedule.dateTime | date:"MMMM, d HH:mm"}}<span ng-show="lesson.schedule.recurrent"
						class="glyphicon glyphicon-repeat"></span>
				</time>
            <address>{{lesson.locaion.address}}</address>
			</p>
        </a>
        
        
		   

		<div class="list-group-item">

			<div class="form-group">
				<input type="text" class="form-control"
					placeholder="название урока, тема">
			</div>

			<div class="form-group">
				<textarea class="form-control" rows="3"
					placeholder="описание, комментарии"></textarea>
			</div>



			<div class="form-group">
				<div class="row">
					<div class="col-xs-9 text-right">
						<label class="control-label">максимальное число учеников 
					</div>
					<div class="col-xs-3">
						<input type="text" class="form-control">
					</div>
				</div>
			</div>





			<!-- end of location -->

			<p class="bg-warning">Внимание! Все, кто записался, будут
				уведомлены об изменениях в расписании</p>
			<div class="form-group">

				<button type="submit" class="btn btn-default">отмена</button>
				<button type="submit" class="btn btn-primary">Ok</button>
			</div>
		</div>

	</div>




</div>






<!--        end of panel-->
<!-- end of lessons list -->