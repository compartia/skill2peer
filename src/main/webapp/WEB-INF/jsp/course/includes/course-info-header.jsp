<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- course-info-header.jsp -->


			
			
	<div class="page-header-wrap">
			<header class="page-header"  style="background: url(/static/img/tkLOe7nnQ7mnMsiuijBy_hm.jpg); background-size: cover">
				<div class="page-header-inner">
			 

			        <h1 itemprop="name">${course.name}</h1>
			        <!-- skills -->
					
					<ul class="skills-list">
						<c:forTokens items="${course.skills}" delims="," var="name">
							<li>
								<span class="label label-default">
									<i class="glyphicon glyphicon-ok"></i>${name}
								</span>
							</li>
						</c:forTokens>
					</ul>
			    </div>

		    </header><!-- /Page-header -->
		    
		    
 
			
			<div class="course-time-info">
				<div class="course-dates">
					<div class="course-dates-wrap">
						<time class="course-date" datetime="${course.schedule.start}" itemprop="startDate" content="${course.schedule.start}">
							<em>${course.schedule.start.dayOfMonth}</em> ${course.schedule.startMonth}
						</time>
						
						
					 
						 <c:if test="${!(course.totalDurationAsString==null && course.single)}">
						<span class="course-duration" itemprop="duration">
							<span class="course-duration-wrap">
								<c:if test="${course.totalDurationAsString!=null}">
									<em content="${course.totalDurationAsString}">${course.totalDurationAsString}</em>
								</c:if>
							</span>
						</span>
						 </c:if>
						
 						<c:if test="${!course.single}">
							<time class="course-date" datetime="${course.schedule.end}" itemprop="endDate" content="${course.schedule.end}">
								<em>${course.schedule.end.dayOfMonth}</em> ${course.schedule.endMonth}
							</time>
						</c:if>
					</div>
				</div>
				
				<div class="course-time-place">
					 
					<div class="course-time">
						<ul class="course-timetable">
							<c:forEach var="dayEvents" items="${course.weekSchedule}">
								<c:if test="${dayEvents.events.isEmpty()}">
									<li>${dayEvents.dayShortName}</li>
								</c:if>
								<c:if test="${!dayEvents.events.isEmpty()}">
									<li class="active">${dayEvents.dayShortName}
										<c:forEach var="event" items="${dayEvents.events}">
											<span class="course-timerange">${event.start.hourOfDay}:${event.start.minuteOfHour} - ${event.end.hourOfDay}:${event.end.minuteOfHour}</span>
										</c:forEach>
									</li>
								</c:if>
							</c:forEach>
						</ul>
					</div>
					 
				
					<div class="course-options-wrap">
					
						<div  class="course-place-wrap" itemprop="location" itemscope itemtype="http://schema.org/Place">
							<i class="glyphicon glyphicon-map-marker"></i>
							<div class="course-place" >
								<div itemprop="name">${course.location.name}</div>
								<div class="course-address" itemprop="address" itemscope itemtype="http://schema.org/PostalAddress">
									<span itemprop="addressLocality">Москва</span>,
									<span itemprop="streetAddress">${course.location.address}</span>
							    </div>
							</div>
						</div>

						<a itemprop="url" href="#" class="btn btn-primary btn-lg btn-block">Записаться</a>
						
						<div class="course-toolbar btn-group">
							<a href="#" class="btn btn-info" itemprop="offers" itemscope itemtype="http://schema.org/AggregateOffer">
								<span itemprop="lowPrice" content="${course.price.value}">${course.price.value} ${course.price.currency}</span>
								<meta itemprop="priceCurrency" content="${course.price.currency}" />
							</a>
							<a href="#" class="btn btn-like">
								<i class="glyphicon glyphicon-heart"></i>
							</a>
						</div>	
					</div>
				</div>
			</div><!-- /Course-time-info -->
		</div><!-- /Page-hedaer-wrap -->