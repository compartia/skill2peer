<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
<title></title>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/app/user.form.js"></script>
</head>
<body>
	<div class="page-header">
		<h1>
			<spring:message code="label.user.registration.page.title" />
		</h1>
	</div>
	<sec:authorize access="isAnonymous()">


		<div class="login-form col-xs-12 col-sm-6 col-md-5 col-lg-4">

			<div>
				<form:form action="/user/register" commandName="user" method="POST" enctype="utf8" role="form">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					<c:if test="${user.signInProvider != null}">
						<form:hidden path="signInProvider" />
					</c:if>


					<div id="form-group-firstName" class="form-group">
						<spring:message code="label.user.firstName" var="fName" />
						<form:input id="user-firstName" path="firstName" cssClass="form-control" placeholder="${fName}" />
						<form:errors id="error-firstName" path="firstName" cssClass="help-block" />
					</div>


					<div id="form-group-lastName" class="form-group">
						<spring:message code="label.user.lastName" var="sName" />
						<form:input id="user-lastName" path="lastName" cssClass="form-control" placeholder="${sName}" />
						<form:errors id="error-lastName" path="lastName" cssClass="help-block" />
					</div>


					<div id="form-group-email" class="form-group">
						<spring:message code="label.user.email" var="mEmail" />
						<form:input id="user-email" path="email" cssClass="form-control" placeholder="${mEmail}" />
						<form:errors id="error-email" path="email" cssClass="help-block" />
					</div>

					<c:if test="${user.signInProvider == null}">

						<div id="form-group-password" class="form-group">
							<spring:message code="label.user.password" var="mPassword" />
							<form:password id="user-password" path="password" cssClass="form-control" placeholder="${mPassword}" />
							<form:errors id="error-password" path="password" cssClass="help-block" />

						</div>

						<div id="form-group-passwordVerification" class="form-group">
							<spring:message code="label.user.passwordVerification" var="mPassword2" />

							<form:password id="user-passwordVerification" path="passwordVerification" cssClass="form-control"
								placeholder="${mPassword2}" />

							<form:errors id="error-passwordVerification" path="passwordVerification" cssClass="help-block" />
						</div>
					</c:if>
					
					
					<button type="submit" class="btn btn-primary btn-lg btn-block">
						<spring:message code="label.user.registration.submit.button" />
					</button>
				</form:form>
			</div>
		</div>
	</sec:authorize>
	<sec:authorize access="isAuthenticated()">
		<p>
			<spring:message code="text.registration.page.authenticated.user.help" />
		</p>
	</sec:authorize>
</body>
</html>