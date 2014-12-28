<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
<meta charset="UTF-8" />
<title>SKILL2PEER</title>
</head>
<body>

	
	
	
<div class="container" >
	<div>
		<a href="/course/create" class="btn btn-default">создать курс</a>
	</div>
		
	<div class="related-courses-wrap"  ng-controller="courseListsController">    
			<div class="panel panel-light">
					<div class="panel-heading"><h2>Мои курсы</h2></div>
					<div class="panel-body">
					 	<div class="related-courses"  >
					 	
							<div class="related-course-block" ng-repeat="mcourse in myCourses | orderBy : 'status':true">
								<div class="img-wrap">
									<img src="{{mcourse.imageUrl}}"/>
								</div>
								
								<div class="text-wrap {{mcourse.status=='PUBLISHED'?'':'draft-course'}}">
									<div class="related-course-description">
										<h3 class="related-course-title">{{mcourse.name}}</h3>
										<time class="related-course-date">
											 {{mcourse.scheduleInfo.start.dayOfMonth}} {{mcourse.scheduleInfo.startMonth}} - {{mcourse.scheduleInfo.end.dayOfMonth}} {{mcourse.scheduleInfo.endMonth}}<br>
											{{mcourse.totalDurationAsString}}
											</time>

										<p>{{mcourse.summary}}</p>
										 
										
										<a ng-show="mcourse.status=='PUBLISHED'" href="/course/info?id={{mcourse.id}}">link</a>
										
									</div>
								</div>									
							</div>
							 
						</div>
					</div>
				</div>
			</div>
			</div>
			
</body>
</html>