<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

 <!-- List group -->
<ul class="list-group course-lessons">
				  
				  
<c:forEach var="lesson" items="${course.lessons}">

	<li class="list-group-item" itemprop="subEvent" itemscope itemtype="http://schema.org/Event">
		<h3 class="name" itemprop="name">${lesson.name} ${lesson.id}</h3>
		<div class="lesson-time">
			<time datetime="${lesson.schedule.start}" itemprop="startDate" content="${lesson.schedule.start}">
				<em>ВТ</em>, ${lesson.schedule.start}
			</time>
			
			-
			<time datetime="2014-10-27" itemprop="startDate" content="2014-10-27T15:45">${lesson.schedule.end}</time>
		</div>
		${lesson.schedule.durationAsString}
		<c:if test="${lesson.location != null}">
			<div class="lesson-address" itemprop="address" itemscope itemtype="http://schema.org/PostalAddress">
				<span itemprop="streetAddress">${lesson.location}</span>
			</div>
		</c:if>
					
		
		<button type="button" class="btn btn-danger" 
			data-toggle="collapse" data-target="#lesson-info-1">simple collapsible</button>

		<div id="lesson-info-1" class="collapse">${lesson.summary} ${lesson.description}</div>
	</li>


</c:forEach>

</ul>