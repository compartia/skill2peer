<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 

 <div class="panel-body lecturer-wrap">
	<div class="lecturer-info">
        <div class="img-wrap">
			<img src="${course.author.imageUrl}"/>
		</div>
		<h4 class="lecturer-name">${course.author.firstName} <strong>${course.author.lastName}</strong></h4>
		<c:if test="${course.author.rating>0}">
			<div class="star-rating"> 
				<span class="fa fa-star" data-rating="1"></span>
				<span class="fa fa-star" data-rating="2"></span>
				<span class="fa fa-star" data-rating="3"></span>
				<span class="fa fa-star disabled" data-rating="4"></span>
				<span class="fa fa-star disabled" data-rating="5"></span>
				<input type="hidden" name="whatever" class="rating-value" value="3"> 
			</div>
		</c:if>
		<a href="#" class="navigate-link">Задать вопрос<i class="fa fa-angle-right"></i></a>
	</div>
       <p class="lecturer-description">${course.author.info}</p>
</div>