<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- course-info-header-2.jsp -->

<div class="row row-md-height course-info-head-block">
	<div class="col-md-8 col-md-push-4 col-md-height course-info-head-block"
		style="background: url(/static/img/tkLOe7nnQ7mnMsiuijBy_hm.jpg); background-size: cover">
		<div class="course-info-head-block-image">
			<h1 itemprop="name">${course.name}</h1>
			<!-- skills -->

			<ul class="skills-list">
				<c:forTokens items="${course.skills}" delims="," var="name">
					<li><span class="label label-default"> <i class="glyphicon glyphicon-tag"></i>${name}
					</span></li>
				</c:forTokens>
			</ul>
		</div>
	</div>

	<div class="col-md-4 col-md-pull-8 col-md-height">
		<div class="course-dates ${course.single? "single-event":""}">
			<div class="course-dates-wrap ">
				<time class="course-date" datetime="${course.scheduleInfo.start}" itemprop="startDate"
					content="${course.scheduleInfo.start}">
					<em>${course.scheduleInfo.start.dayOfMonth}</em> ${course.scheduleInfo.startMonth}
				</time>

				<c:if test="${course.totalDurationAsString!=null}">
					<span class="course-duration" itemprop="duration"> <span class="course-duration-wrap"> <c:if
								test="${course.totalDurationAsString!=null}">
								<em content="${course.totalDurationAsString}">${course.totalDurationAsString}</em>
							</c:if>
					</span>
					</span>
				</c:if>
				<c:if test="${!course.single}">
					<time class="course-date" datetime="${course.scheduleInfo.end}" itemprop="endDate"
						content="${course.scheduleInfo.end}">
						<em>${course.scheduleInfo.end.dayOfMonth}</em> ${course.scheduleInfo.endMonth}
					</time>
				</c:if>
			</div>
		</div>

		<div class="course-time-place">
			<div class="course-time-place-wrap">
				<div class="course-time">
					<ul class="course-timetable">
						<c:forEach var="dayEvents" items="${course.weekSchedule}">
							<c:if test="${dayEvents.events.isEmpty()}">
								<li>${dayEvents.dayShortName}</li>
							</c:if>
							<c:if test="${!dayEvents.events.isEmpty()}">
								<li class="active">${dayEvents.dayShortName}<c:forEach var="event" items="${dayEvents.events}">
										<span class="course-timerange">${event.start.hourOfDay}:${event.start.minuteOfHour} -
											${event.end.hourOfDay}:${event.end.minuteOfHour}</span>
									</c:forEach>
								</li>
							</c:if>
						</c:forEach>
					</ul>
				</div>


				<div class="course-options-wrap">
					<div class="course-options">

						<div class="course-place-wrap" itemprop="location" itemscope itemtype="http://schema.org/Place">
							<div class="course-place">
								<div itemprop="name">${course.location.name}</div>
								<div class="course-address" itemprop="address" itemscope itemtype="http://schema.org/PostalAddress">
									<span itemprop="addressLocality">${course.location.vicinity}</span>, <span itemprop="streetAddress">${course.location.address}</span>
								</div>
							</div>
						</div>

						<a itemprop="url" href="#" class="btn btn-primary btn-lg btn-block">Записаться</a>


						<div class="course-toolbar btn-group" ng-controller="favoriteCourseController">
							<a href="#" class="btn btn-info" itemprop="offers" itemscope itemtype="http://schema.org/AggregateOffer"> <span
								itemprop="lowPrice" content="${course.price.value}">${course.price.value} ${course.price.currency}</span>
								<meta itemprop="priceCurrency" content="${course.price.currency}" />
							</a>
							<button class="btn btn-like" ng-class="{'activated':course.favorited}" ng-click="favoriteCourse()">
								<i class="glyphicon glyphicon-heart"></i>
							</button>
						</div>

					</div>
				</div>
			</div>
		</div>
	</div>


</div>

