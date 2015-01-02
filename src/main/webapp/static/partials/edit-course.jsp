<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>




<form role="form" name="courseEditForm">
	<div class="row">
		<div class="col-md-12">
			<div class="panel section-basic">
				<div class="panel-body">
					<ng-include src="'/static/partials/edit-course-title.jsp'"></ng-include>
				</div>
                <div class="panel-footer" ng-show="isSingle()">
                     Курс состоит из нескольких уроков/лекций?<br>
                    <button data-ng-click="addLesson()" class="btn btn-default"><span class="glyphicon glyphicon-plus"></span> Добавить урок</button></div>
               
			</div>
		</div>
	</div>

	<div class="row">

		<div class="col-sm-4 edit-lessons-list" ng-show="!isSingle()">
			<div class="panel">
				<!-- Default panel contents -->
                <div class="panel-heading">Уроки в рамках курса</div>
				 
			     <div class="panel-body">
				    <ng-include src="'/static/partials/edit-lessons-list.jsp'"></ng-include>
                </div>
                
                <div class="panel-footer">
                    <button data-ng-click="addLesson()" class="btn btn-default"><span class="glyphicon glyphicon-plus"></span> Добавить урок</button></div>
                
			</div>
		</div>


		<div class="col-sm-8" ng-class="{'lesson-edit-block':!isSingle()}">

			<div ng-controller="lessonEditController">



				<!-- WHAT-->
				<div class="panel section-basic" ng-show="!isSingle()">
                    <div class="panel-heading">Что?</div>
					<div class="panel-body">

						<div class="form-group">
							<input name="name" ng-model="lesson.name" type="text" class="form-control input-lg" placeholder="название урока"
								required>
						</div>

						<div class="form-group">
							<textarea class="form-control" rows="3" placeholder="краткое описание урока" ng-model="summary.summary"></textarea>
						</div>

						

					</div>

				</div>

				<!-- WHEN -->
				<div class="panel section-time">
					<div class="panel-heading">Когда?</div>
					<div class="panel-body">
						<%@include file="edit-schedule.jsp"%>
					</div>
				</div>


				<!-- end of when-block -->

				<div class="panel section-place">
					<div class="panel-heading">Где?</div>
					<div class="panel-body">
						<%@include file="edit-place.jsp"%>
					</div>
				</div>



			</div>

			<div class="panel section-descr">
                <div class="panel-heading">Как и зачем?</div>
				<div class="panel-body">
					<textarea class="form-control" rows="6" ng-model="course.lessons[0].description"
						placeholder="детальное описание урока"></textarea>
				</div>
			</div>



			<div class="panel section-price">
				<!-- Default panel contents -->
				<div class="panel-heading">Цена</div>
				<div class="panel-body">

					<div class="row">
						<div class="col-xs-3">
							<label>базовая цена за час</label>
						</div>
						<div class="col-xs-1">
							<input type="text" class="form-control">
						</div>

					</div>


					<div class="form-group">
						<input type="text" class="form-control" placeholder="комментарий к цене">
					</div>

				</div>

			</div>



		</div>
		<!--    right col-->

		<!--end of row-->

	</div>

	<a href="view_course.html" class="btn btn-default">предпросмотр</a>

	<div class="alert alert-info" role="alert">
		Внимание, все изменения должны пройти модерацию
		<button ng-click="save()" ng-disabled="courseEditForm.$invalid" class="btn btn-primary">Сохранить</button>
		(как черновик)
		<button type="submit" class="btn btn-default text-right">Опубликовать</button>

	</div>

</form>


