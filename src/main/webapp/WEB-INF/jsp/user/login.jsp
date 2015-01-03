<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
<title></title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/static/css/social-buttons-3.css" />
	
<style type="text/css">
body
{
    background: url('https://lh4.googleusercontent.com/-tRhY30Ga9dk/Usvkl4QtLVI/AAAAAAAASik/OYw12d0XASw/s0-I/IMG_0140.JPG') fixed;
    background-size: cover;
    padding: 0;
    margin: 0;
}

.wrap{
	width: 100%;
 	 height: 80%;
   /*  min-height: 100%; */
    position: absolute;
    top: 61.8%;
    left: 0;
    z-index: 99;
    vertical-align: middle;
 }
</style>
</head>
<body>
<div class="container-fluid">
<!-- <div class="wrap noisy">мы делаем мир лучше.  Новые знания, новые возможности, новые знакомства.</div>
 -->	 

 
	<sec:authorize access="isAnonymous()">
		<div class="row">
			<!-- <div class="col-xs-5"></div> -->
			<div class="dlogin-screen col-xs-12 col-sm-6 col-md-7 col-lg-8"></div>
			<div class="dlogin-screen col-xs-12 col-sm-6 col-md-5 col-lg-4">

				<div class="login-form">

					<c:if test="${param.error eq 'bad_credentials'}">
						<div class="alert alert-danger alert-dismissable">
							<button type="button" class="close" data-dismiss="alert"
								aria-hidden="true">&times;</button>
							<spring:message code="text.login.page.login.failed.error" />
						</div>
					</c:if>

					<form
						action="${pageContext.request.contextPath}/login/authenticate"
						method="POST" role="form">
						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />

						<div id="form-group-email" class="form-group">
							<input id="user-email" name="username" type="text"
								class="form-control login-field"
								placeholder="<spring:message code="label.user.email"/>" /> <label
								class="login-field-icon fui-user" for="login-name"></label>
						</div>
						<!-- PWD -->
						<div class="form-group" id="form-group-password">
							<input id="user-password" name="password" type="password"
								class="form-control login-field" value=""
								placeholder="<spring:message code="label.user.password"/>"
								id="login-pass"> <label
								class="login-field-icon fui-lock" for="login-pass"></label>
						</div>


						<button type="submit" class="btn btn-primary btn-lg btn-block">
							<spring:message code="label.user.login.submit.button" />
						</button>

						<a class="login-link" href="#">Lost your password?</a>
					</form>
					
					<!-- social -->
					<div class="form-group">
						<!-- FACEBOOK -->

						<a href="<c:url value="/auth/facebook"/>"
							class="btn btn-facebook  btn-block"> <i class="fui-facebook pull-left"></i>
							<spring:message code="label.facebook.sign.in.button" />
						</a>
					</div>

					<div class="form-group">
						<!-- twitter -->
						<a class="btn btn-twitter btn-block"
							href="<c:url value="/auth/twitter"/>"> <i class="fui-twitter  pull-left"></i>
							<spring:message code="label.twitter.sign.in.button" />
						</a>
					</div>


					<a href="/user/register" class="btn login-link"><spring:message
							code="label.navigation.registration.link" /></a>


				</div>
			</div>
		</div>



	</sec:authorize>
	<sec:authorize access="isAuthenticated()">
		<p>
			<spring:message code="text.login.page.authenticated.user.help" />
		</p>
	</sec:authorize>
	
	</div>
</body>
</html>