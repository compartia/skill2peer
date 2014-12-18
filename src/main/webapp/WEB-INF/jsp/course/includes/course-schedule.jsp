<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<c:forEach var="lesson" items="${course.lessons}">
	<div class="lesson">
		<h4>${lesson.name}<small>-${lesson.id}</small>
		</h4>
		<div>${lesson.schedule.start}</div>
		<div>${lesson.schedule.durationAsString}</div>
		<div>${lesson.summary}</div>
		<div>${lesson.description}</div>
	</div>
</c:forEach>