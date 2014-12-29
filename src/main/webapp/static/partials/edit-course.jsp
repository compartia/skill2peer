<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
 

 
 
  
  

		<div class="row">

			<div class="col-md-4">
				<%@include file="edit-lessons-list.jsp"%>
			</div>


			<div class="col-md-8">




 

				<form role="form" name="courseEditForm" >


					 
					
<div ng-repeat="lesson in course.lessons" >
					<div class="what-block panel-body form-panel">
						<div class="row">
							<div class="col-md-1">
								<div class="section-label">1</div>
							</div>

							<div class="col-md-11">


								<div class="form-group">

									<input name="name" ng-model="course.name" type="text" class="form-control input-lg"
										placeholder="название урока" required>

								</div>

								<!-- SKILLS -->
								<div class="form-group">
									<label>Навыки <br> <small>Укажите навыки(skills), которые студенты приобретут, пройдя этот
											курс. Не более пяти.</small></label>

									<div class="tagsinput-primary">
										<input name="tagsinput" class="tagsinput" ng-model="course.tags" data-role="tagsinput"
											value="School, Teacher, Colleague" />
									</div>
								</div>
								<!-- end of SKILLS -->

								<div class="form-group">
									<textarea class="form-control" rows="3" placeholder="краткое описание курса" ng-model="course.summary"></textarea>
									<span class="help-inline small">Этот текст будет выводиться в результатах поиска</span>
								</div>
								<button ng-click="save()" ng-disabled="courseEditForm.$invalid" class="btn btn-primary">Сохранить</button>
							</div>
						</div>




					</div>

					<!-- WHEN -->
					<div class="when-block panel-body form-panel">
						<div class="row">
							<div class="col-md-1">
								<div class="section-label">2</div>
							</div>
							<div class="col-md-11">
								<h3>расписание</h3>
								<%@include file="edit-schedule.jsp"%>
							</div>
						</div>
					</div>
</div>
					<!-- end of when-block -->

					<div class="where-block panel-body form-panel">

						<div class="row">
							<div class="col-md-1">
								<div class="section-label">3</div>
							</div>

							<div class="col-md-11">
								<%@include file="edit-place.jsp"%>
							</div>
						</div>
					</div>

					<div class="details-block panel-body form-panel">
						<textarea class="form-control" rows="6" 
							ng-model="course.lessons[0].description" placeholder="детальное описание урока"></textarea>
					</div>



					<div class="form-group">
						<button class="btn">Добавить видео</button>
						<label for="exampleInputFile"> Фотографии </label> <input type="file" id="exampleInputFile">
						<p class="help-block">Вы можете загрузить любые фотографии, которые раскроют суть вашего курса/тренинга</p>
					</div>




					<div class="panel panel-default">
						<!-- Default panel contents -->
						<div class="panel-heading">
							<span class="glyphicon glyphicon-euro"></span> Цена
						</div>
						<div class="panel-body">

							<div class="form-group">
								<div class="row">
									<div class="col-xs-9 text-right">
										<label>базовая цена за час</label>
									</div>
									<div class="col-xs-3 text-right">
										<input type="text" class="form-control">
									</div>
								</div>
							</div>


							<div class="form-group">
								<input type="text" class="form-control" placeholder="комментарий к цене">
							</div>

							<div class="checkbox">
								<label><input type="checkbox">первый урок бесплатно </label>
							</div>

							<div class="row">
								<div class="col-xs-9 text-right">
									<label> скидка за привлечение друга</label>
								</div>
								<div class="col-xs-2">
									<input type="text" class="form-control" placeholder="cкидка">
								</div>
								<div class="col-xs-1">%</div>
							</div>

						</div>

					</div>
				</form>
			</div>
			<!--    right col-->

			<!--end of row-->

		</div>

		<a href="view_course.html" class="btn btn-default">предпросмотр</a>

		<div class="alert alert-info" role="alert">
			Внимание, все изменения должны пройти модерацию
			<button type="submit" class="btn btn-default text-right">Сохранить</button>
			(как черновик)
			<button type="submit" class="btn btn-default text-right">Опубликовать</button>

		</div>




 