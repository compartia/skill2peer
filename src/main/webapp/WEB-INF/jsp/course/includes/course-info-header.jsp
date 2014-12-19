<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- course-info-header.jsp -->


			
			
	<div class="page-header-wrap">
			<header class="page-header">
				<div class="page-header-inner">
					<!-- <ol class="breadcrumb">
			            <li>
			                <a href="#">Курсы</a>
			            </li>
			            <li>
			            	<a href="#">Искусство</a>
			            </li>
			            <li class="active">Конкурсы</li>
			        </ol> -->

			        <h1 itemprop="name">${course.name}</h1>
			    </div>

		    </header><!-- /Page-header -->

			<%-- <div class="featured-img">
				<img src="{course.imageurl}" alt="${course.name}"/>
			</div>
			 --%>
			
			<div class="course-time-info">
				<div class="course-dates">
					<div class="course-dates-wrap">
						<time class="course-date" datetime="${course.schedule.start}" itemprop="startDate" content="${course.schedule.start}">
							<em>${course.schedule.start.dayOfMonth}</em> ${course.schedule.startMonth}
						</time>
				
 						<c:if test="${course.schedule.end != null}">
							<span class="course-duration" itemprop="duration">
								<span class="course-duration-wrap">
									<em content="${course.totalDurationAsString}">${course.totalDurationAsString}</em>
								</span>
							</span>
							<time class="course-date" datetime="${course.schedule.end} itemprop="endDate" content="${course.schedule.end}">
								<em>${course.schedule.end.dayOfMonth}</em> ${course.schedule.endMonth}
							</time>
						</c:if>
					</div>
				</div>
				<div class="course-time-place">
					<div class="course-time">
						<ul class="course-timetable">
							<li>Пн</li>
							<li class="active">
								Вт
								<span class="course-timerange">15.00-17.00</span>
							</li>
							<li>Ср</li>
							<li class="active">
								Чт
								<span class="course-timerange">14.00-16.00</span>
							</li>
							<li>Пт</li>
							<li>Сб</li>
							<li>Вс</li>
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