<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>
<head>

<title>${course.name}</title>


 
<script type="text/javascript">
	var courseId = ${course.id}
</script>
 
</head>
<body>
	



	<div class="container" itemscope itemtype="http://schema.org/EducationEvent">

	<%@include file="includes/course-info-header.jsp"%>
	<%@include file="includes/header-toolbar.jsp"%>
	
	
	<div>
		<div class="container">
			<div class="navbar-right text-right">
				<small>Ближайший урок: 16 Ноября в 16:00 ${course.schedule}</small>
				<button class="btn btn-danger navbar-btn" type="button">Записаться</button>

				<span ng-controller="favoriteCourseController">
					<button ng-click="favoriteCourse()" class="btn {{btnStyle}} navbar-btn" type="button">
						<i class="fui-heart"></i>
					</button>
				</span>
			</div>
		</div>
	</div>

 

		<div class="row">
			<div class="col-md-8">
				<div class="panel panel-default">
					<div class="panel-body">

						<div class="course-detail-image">
							<img typeof="foaf:Image" class="img-responsive" src="img/im2.jpg" property="image"
								alt="S2P-320 - Постархангельская прионика" title="S2P-320 - Постархангельская прионика">
						</div>
						 
					
						<!-- краткое описание курса-->
						<p itemprop="description" class="lead">${course.summary}</p>

					</div>
				</div>

				




			</div>
			<!-- end  of left col -->

			<div class="col-md-4">

				 
 
				<aside class="section-sidebar">
					<c:if test="${!course.single}">
						<div class="panel panel-default">
							<!-- Default panel contents -->
							<div class="panel-heading">
								<h3>Расписание</h3>
							</div>
							<%@include file="includes/course-schedule.jsp"%>
						</div>
					</c:if>
				</aside>


			</div>
			<!-- end  of right col -->
		</div>


		<div class="row">
			<div class="col-md-8">
				<!-- детальное описание курса -->

				<p>${course.description}</p>

				<form role="form">
					<div class="form-group">Фотографии TODO:</div>
				</form>
				
			</div>
			<!--    right col-->
			<div class="col-md-4">

				 


				<!-- <span itemscope itemtype="http://schema.org/performers">  -->
				<div class="tile" itemscope itemtype="http://schema.org/Person" itemprop="performer">
					<!-- Default panel contents -->
					<div class="panel-heading">курс ведет</div>

					<div class="panel-body">
						<img itemprop="image" src="img/dmalan00.jpg" alt="..." class="img-circle pull-left" width="60px" height="60px">
						<label> <span itemprop="name">Johnatahan Smithderson </span><br> University of Nikaragua
						</label> <br> <span class="fui-star-2"></span> <span class="fui-star-2"></span> <span class="fui-star-2"></span> <span
							class="fui-star-2"></span> <br> рейтинг: 54.4<br> <a href="#" class="btn btn-default btn-sm">
							написать вопрос </a>

					</div>
				</div>
				<!-- </span> -->

				<div class="course-detail-overview views-fieldset" data-module="views_fieldsets">
					<div>
						<h3>подготовка:</h3>
						High school maths and physics.
					</div>
				</div>


				<div class="panel panel-default">
					<!-- Default panel contents -->
					<div class="panel-heading">
						<span class="glyphicon glyphicon-time"></span> Программа курса
					</div>

					  




				</div>






				<!--        end of panel-->


				<div class="panel panel-default">

					<div class="panel-heading">
						<span class="glyphicon glyphicon-comment"></span> Отзывы студентов
					</div>

					<div class="list-group">
						<a href="#" class="list-group-item">While this course is being only offered for the first time, the first and
							second courses in the A.N.U. have been offered before and they were both exc ... more</a> <a href="#"
							class="list-group-item">While this course is being only offered for the first time, the first and second
							courses in the A.N.U. have been offered before and they were both exc</a>
					</div>
					<div class="panel-footer">
						<a href="#" class="btn btn-default btn-sm"> написать отзыв </a>
					</div>
				</div>
				<!--        end of panel-->


			</div>
			<!--end of row-->




			<h4>Вам могут быть интересны</h4>
			<div class="row">
				<div class="col-lg-12">
					<div class="row">
						<div class="col-xs-12 col-sm-4 col-md-3 col-lg-3">
							<div class="tile">
								<img src="../../img/icons/svg/compas.svg" alt="Compas" class="tile-image big-illustration">
								<h3 class="tile-title">Web Oriented</h3>
								<p>100% convertable to HTML/CSS layout.</p>
								<a class="btn btn-primary btn-large btn-block" href="http://designmodo.com/flat">Get Pro</a>
							</div>
						</div>

						<div class="col-xs-12 col-sm-4 col-md-3 col-lg-3">
							<div class="tile">
								<img src="../../img/icons/svg/loop.svg" alt="Infinity-Loop" class="tile-image">
								<h3 class="tile-title">Easy to Customize</h3>
								<p>Vector-based shapes and minimum of layer styles.</p>
								<a class="btn btn-primary btn-large btn-block" href="http://designmodo.com/flat">Get Pro</a>
							</div>
						</div>

						<div class="col-xs-12 col-sm-4 col-md-3 col-lg-3">
							<div class="tile">
								<img src="../../img/icons/svg/pencils.svg" alt="Pensils" class="tile-image">
								<h3 class="tile-title">Color Swatches</h3>
								<p>Easy to add or change elements.</p>
								<a class="btn btn-primary btn-large btn-block" href="http://designmodo.com/flat">Get Pro</a>
							</div>
						</div>

						<div class="col-xs-12 col-sm-4 col-md-3 col-lg-3">
							<div class="tile">
								<img src="../../img/icons/svg/chat.svg" alt="Chat" class="tile-image">
								<h3 class="tile-title">Free for Share</h3>
								<p>Your likes, shares and comments helps us.</p>
								<a class="btn btn-primary btn-large btn-block" href="http://designmodo.com/flat">Get Pro</a>
							</div>

						</div>
					</div>
					<!-- /row -->
				</div>
			</div>
			<!-- /row -->

		</div>





		<div class="row">
			<div class="col-xs-6 col-md-3">
				<a href="#" class="thumbnail"> <img data-src="holder.js/100%x180" alt="...">
				</a>
			</div>

		</div>
	</div>
	<script src="/static/js/bower_components/jquery/dist/jquery.min.js"></script>
	<script src="/static/js/script.js"></script>
	<script src="/static/js/bootstrap.js"></script>
	
</body>
</html>