<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

 
 <!-- List group -->
<ul class="list-group course-lessons">
				  
				  
<c:forEach var="lesson" items="${course.lessons}">

	<li class="list-group-item" itemprop="subEvent" itemscope itemtype="http://schema.org/Event">
		<div class="btn-group btn-group-sm" role="group">
			 
			<c:forEach var="day" begin="0" end="6">
				<c:if test="${lesson.schedule.start.dayOfWeek == day}">
					<button type="button" class="btn btn-primary"><spring:message code="days.${day}" /></button>
				</c:if>
				<c:if test="${lesson.schedule.start.dayOfWeek != day}">
					<button type="button" class="btn btn-default"><spring:message code="days.${day}" /></button>
				</c:if>
			</c:forEach>
			 
		</div>
		
		
		<div class="lesson-time">
			<time datetime="${lesson.schedule.start}" itemprop="startDate" content="${lesson.schedule.start}">
				<em>${lesson.schedule.start.dayOfMonth}</em> ${lesson.schedule.startMonth} ${lesson.schedule.start.hourOfDay}:${lesson.schedule.start.minuteOfHour}
			</time>
		</div>
		<h3 class="name" itemprop="name">${lesson.name} ${lesson.id}</h3>
		<div class="lesson-duration">
		${lesson.schedule.durationAsString}
		</div>
		<c:if test="${lesson.location != null}">
			<div class="lesson-address" itemprop="address" itemscope itemtype="http://schema.org/PostalAddress">
				<span itemprop="streetAddress">${lesson.location}</span>
			</div>
		</c:if>
					
		
		<button type="button" class="btn btn-default" 
			data-toggle="collapse" data-target="#lesson-info-${lesson.id}"><span class="caret"></span></button>

		<div id="lesson-info-${lesson.id}" class="collapse">${lesson.summary} ${lesson.description}</div>
	</li>


</c:forEach>

</ul>