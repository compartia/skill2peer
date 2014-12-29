<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>
<head>

<title>${course.name}</title>


<script data-main="/static/js/main-view-course"
	src="${pageContext.request.contextPath}/static/js/bower_components/requirejs/require.js"></script>

<script type="text/javascript">
	var courseId = ${course.id}
</script>
 
</head>
<body>
	
	<div class="container" itemscope itemtype="http://schema.org/EducationEvent">

	<%@include file="includes/course-info-header.jsp"%>
	<%@include file="includes/header-toolbar.jsp"%>
	
	
	 
        <div class="sections-wrap">
			<section class="section course-description">
                <p class="lead" itemprop="description">${course.summary}</p>
				<p>${course.description}</p>	 
			</section><!-- /Course-description -->
            
            
            

			<aside class="section-sidebar">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3>Преподаватели</h3>
					</div>
					
				   	<%@include file="includes/course-info-teachers.jsp"%>
				</div><!-- /panel -->

				<div class="panel panel-default">
					<div class="panel-heading">
						<h3>Рейтинг курса</h3>
						<span class="badge badge-info">
							<span>Информация</span>
						</span>
					</div>
					<%@include file="includes/course-info-rating.jsp"%>
					
				</div><!-- /panel -->

				<div class="panel panel-default">
					<div class="panel-heading">
						<h3>Загрузки</h3>
					</div>

					<div class="panel-body">
						<ul class="download-list">
							<li>
								<a href="#" class="download-link"><i class="glyphicon glyphicon-download"></i>Литература</a>
							</li>
							<li>
								<a href="#" class="download-link"><i class="glyphicon glyphicon-download"></i>Литература</a>
							</li>
						</ul>
					</div>
				</div>

			</aside><!-- /Sidebar -->	
		</div><!-- /sections-wrap -->
        
        

        <div class="sections-wrap">
			<aside class="section-sidebar"><c:if test="${!course.single}">
				<div class="panel panel-default">
				  <!-- Default panel contents -->
				  <div class="panel-heading"><h3>Расписание</h3></div>

				  <!-- List group -->
				   <%@include file="includes/course-schedule.jsp"%>
				</div></c:if>
			</aside>

			<section class="section">
				<div class="panel panel-light">
					<div class="panel-heading"><h2>Отзывы</h2></div>

					<div class="panel-body comments-list">
						<div class="comment-block">
							<p>Программа рассчитана на обучение с нуля и базируется на классической 
							русской школе академического рисунка. Курс поможет раскрыть 
							внутренние возможности слушателя в изобразительном искусстве.
							Программа рассчитана на обучение с нуля и базируется на классической 
							русской школе академического рисунка. Курс поможет раскрыть 
							внутренние возможности слушателя в изобразительном искусстве.</p>

							<div class="comment-info">
								<time datetime="2014-10-22">22 октября</time>
								<a href="#" class="comment-author">
									<span class="comment-author-name">klipdass</span>
									<div class="img-wrap">
										<img src="https://lh5.googleusercontent.com/-pCyPzLumSHs/U6QNeMPkDqI/AAAAAAAAAmY/4TLZ0GjnDhA/w140-h140-p/Daria.jpg"/>
									</div>
								</a>
							</div>
						</div>
						<div class="comment-block">
							<p>Программа рассчитана на обучение с нуля и базируется на классической 
							русской школе академического рисунка. Курс поможет раскрыть 
							внутренние возможности слушателя в изобразительном искусстве.</p>

							<div class="comment-info">
								<time datetime="2014-10-22">22 октября</time>
								<a href="#" class="comment-author">
									<span class="comment-author-name">klipdass</span>
									<div class="img-wrap">
										<img src="http://www.riken.jp/TMS2012/tms/image/member/main/icon_women.png"/>
									</div>
								</a>
							</div>
						</div>
						<div class="comment-block">
							<p>Программа рассчитана на обучение с нуля и базируется на классической 
							русской школе академического рисунка. Курс поможет раскрыть 
							внутренние возможности слушателя в изобразительном искусстве.</p>

							<div class="comment-info">
								<time datetime="2014-10-22">22 октября</time>
								<a href="#" class="comment-author">
									<span class="comment-author-name">klipdass</span>
									<div class="img-wrap">
										<img src="http://www.riken.jp/TMS2012/tms/image/member/main/icon_women.png"/>
									</div>
								</a>
							</div>
						</div>
						<div class="comment-block">
							<p>Программа рассчитана на обучение с нуля и базируется на классической 
							русской школе академического рисунка. Курс поможет раскрыть 
							внутренние возможности слушателя в изобразительном искусстве.</p>

							<div class="comment-info">
								<time datetime="2014-10-22">22 октября</time>
								<a href="#" class="comment-author">
									<span class="comment-author-name">klipdass</span>
									<div class="img-wrap">
										<img src="http://www.riken.jp/TMS2012/tms/image/member/main/icon_women.png"/>
									</div>
								</a>
							</div>
						</div>
					</div>
				</div>
			</section>
		</div><!-- sections-wrap --> 
		 
	</div>
	
 
	
</body>
</html>