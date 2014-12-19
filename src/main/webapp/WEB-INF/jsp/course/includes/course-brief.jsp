<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>


course brief
<div>
	<span>${course.schedule.start.dayOfMonth}</span> - <span>${course.schedule.end.dayOfMonth}</span>
</div>
<div>
	<span>${course.schedule.start.monthOfYear}</span> - <span>${course.schedule.end.monthOfYear}</span>
</div>


<dl class="dl-horizontal">
	<dt>Код</dt>
	<dd>S2P-12</dd>
	<dt>
		<span class="fui-time"></span> Начало
	</dt>
	<dd>
		<time itemprop="startDate" datetime="${course.schedule.start}">
			 <fmt:formatDate type="both" pattern="" value="${course.schedule.start.toDate()}" />  
		</time>
	</dd>

	<dt>Длительность</dt>
	<dd>
 		<i itemprop="duration" content="${course.schedule.durationAsString}"></i> ${course.schedule.durationAsString}
 	</dd>

	<dt>Место</dt>
	<dd>
		<address>


			<div itemprop="location" itemscope itemtype="http://schema.org/PostalAddress">
				<div itemprop="name">${course.location.name}</div>
				<div itemprop="streetAddress">${course.location.address}</div>
				<div itemprop="streetAddress">${course.location.id}-- TODO: comment</div>
			</div>


		</address>
	</dd>


	<dt>Стоимость</dt>
	<dd>
		<span itemprop="offers" itemscope itemtype="http://schema.org/Offer">
			<div itemprop="price" content="${course.price.value}">${course.price.value}${course.price.currency}</div>
			<meta itemprop="priceCurrency" content="${course.price.currency}" />
			<small>${course.price.comment}</small>



			<div itemprop="reviews" itemscope itemtype="http://schema.org/AggregateRating">
				<img src="four-stars.jpg" />
				<meta itemprop="ratingValue" content="4" />
				<meta itemprop="bestRating" content="5" />
				Based on <span itemprop="ratingCount">25</span> user ratings
			</div>


		</span>
	</dd>
</dl>