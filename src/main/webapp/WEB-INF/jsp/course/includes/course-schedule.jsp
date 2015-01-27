<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>


<!-- List group -->

<ul class="list-group course-lessons list-group-item">


	<c:forEach var="lesson" items="${course.lessons}">

		<li class="list-group-item-wrap" itemprop="subEvent" itemscope itemtype="http://schema.org/Event" data-toggle="collapse" data-target="#lesson-info-${lesson.id}">
			<!-- lesson schedule--> <!-- /lesson schedule-->

			<div class="date-wrap">
				<div class="lesson-time">
					<time datetime="${lesson.schedule.nextEvent}" itemprop="startDate" content="${lesson.schedule.nextEvent}">
						<h3>${lesson.schedule.nextEvent.start.dayOfMonth}</h3>
						<small>${lesson.schedule.startMonth}</small>
						${lesson.schedule.nextEvent.start.hourOfDay}:${lesson.schedule.nextEvent.start.minuteOfHour}
					</time>
					<div class="lesson-duration">${lesson.schedule.durationAsString}</div>
				</div>
			</div>
			<div class="text-wrap" >

				<c:if test="${lesson.recurrent}">
					<div class="btn-group btn-group-sm" role="group">
						<c:forEach var="day" begin="0" end="6">
							<c:if test="${lesson.schedule.nextEvent.start.dayOfWeek == day+1}">
								<button type="button" class="btn btn-primary">
									<spring:message code="days.${day}" />
								</button>
							</c:if>
							<c:if test="${lesson.schedule.nextEvent.start.dayOfWeek != day+1}">
								<button type="button" class="btn btn-default">
									<spring:message code="days.${day}" />
								</button>
							</c:if>
						</c:forEach>
					</div>
				</c:if>
				<h3 class="lesson-title expand-link" itemprop="name">${lesson.name}</h3>
				<c:if test="${lesson.location != null}">
					<address>
						<div class="lesson-address" itemprop="address" itemscope itemtype="http://schema.org/PostalAddress">
							<span itemprop="streetAddress">${lesson.location.html}</span>
						</div>
					</address>
				</c:if>



				<div id="lesson-info-${lesson.id}" class="collapse">${lesson.summary} ${lesson.description}</div>


			</div>


		</li>


	</c:forEach>

</ul>