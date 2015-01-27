<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>


<div class="navbar navbar-default navbar-static-top" role="navigation">
	<div class="container-fluid">
		<div class="navbar-header">
			<sec:authorize access="isAuthenticated()">
				<button type="button" class="btn btn-xs btn-danger navbar-btn">12</button>
			</sec:authorize>
			<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
				<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span>
			</button>

			<a class="navbar-brand" href="/"><img alt="logo" src="/static/img/skill2peerlogo.png"></a>

		</div>



		<div class="navbar-collapse collapse">

			<form role="search" class="navbar-form navbar-left">
				<!-- <div class="form-group"> -->
				<div class="input-group">
					<input type="text" placeholder="найти курс" class="form-control"> <span class="input-group-btn">
						<button type="submit" class="btn">
							<span class="glyphicon glyphicon-search"></span>
						</button>
					</span>
				</div>
				<!-- </div> -->
			</form>



			<ul class="nav navbar-nav navbar-right">



				<sec:authorize access="isAnonymous()">
					<li><a href="/user/register"><spring:message code="label.navigation.registration.link" /></a></li>
				</sec:authorize>

				<sec:authorize access="isAuthenticated()">

					<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown"> <sec:authentication
								property="principal.socialSignInProvider" var="signInProvider" /> <c:if test="${signInProvider == 'FACEBOOK'}">
								<i class="icon-facebook-sign"></i>
							</c:if> <c:if test="${signInProvider == 'TWITTER'}">
								<i class="icon-twitter-sign"></i>
							</c:if> <c:if test="${empty signInProvider}">
								<spring:message code="label.navigation.signed.in.as.text" />
							</c:if> <sec:authentication property="principal.firstName" /> <sec:authentication property="principal.lastName" /><b
							class="caret"></b></a>

						<ul class="dropdown-menu">
							<li><a href="#">Настройки</a></li>


							<li><a href="/"><spring:message code="label.navigation.home.link" /></a></li>


							<li><a href="calendar.html"></a></li>
							<li class="divider"></li>
							<!-- <li><a href="#">выйти</a></li> -->

							<li>
								<form action="/logout" method="POST">
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
									<button type="submit" class="btn btn-link">
										<spring:message code="label.navigation.logout.link" />
									</button>
								</form>
							</li>
						</ul> <!-- end of dropdown --></li>

					<li>
				</sec:authorize>

			</ul>

		</div>
		<!--/.nav-collapse -->
	</div>
</div>

<!-- /NAVBAR -->




