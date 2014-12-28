<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 

 <div class="panel-body rating">
	<div class="star-rating-wrap">
		<span class="total-rating">67</span>
		<div class="star-rating active"> 
			<span class="fa fa-star" data-rating="1"></span>
			<span class="fa fa-star" data-rating="2"></span>
			<span class="fa fa-star" data-rating="3"></span>
			<span class="fa fa-star disabled" data-rating="4"></span>
			<span class="fa fa-star disabled" data-rating="5"></span>
			<input type="hidden" name="whatever" class="rating-value" value="3">
		</div>
	</div>
	<a href="#" class="navigate-link">Посоветовать другу<i class="fa fa-angle-right"></i></a>
</div>