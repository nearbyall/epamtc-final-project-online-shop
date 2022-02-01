<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="localization" var="locale"/>
    
<c:if test="${not empty exception_msg}">
	<div class="error-message">
		<p>
			<fmt:message bundle="${exc_msg}"  key="${exception_msg}"/>
		</p>
	</div>
</c:if>

<main class="content">
	<form class="form-wrapper" method="post" action="Controller" id="login-form">
    
		<fieldset class="fieldset">
        
			<input type="hidden" name="redirectPageCommand" value="openMainPage">
			<input type="hidden" name="action" value="logIn">
            
			<legend class="title">
				<fmt:message bundle="${locale}" key="login.legend"/>
			</legend>
            
			<label>
				<span><fmt:message bundle="${locale}" key="user.email"/></span>
				<input type="text" name="email" class="input" required>
			</label>
            
			<label>
				<span><fmt:message bundle="${locale}" key="login.password"/></span>
				<input type="password" name="password" class="input" required>
			</label>
            
			<div class="terms">
				<label class="check">
					<input type="checkbox" name="generateRememberUserToken">
					<span class="checkmark"></span>
				</label>
				<span><fmt:message bundle="${locale}" key="login.remember"/></span>
			</div>
            
			<label class="inputfield">
				<input class="btn submit" type="submit" value=<fmt:message bundle="${locale}" key="login.enter"/> >
			</label>
            
		</fieldset>
        
		<a href="${pageContext.request.contextPath}/jsp/guest/forgetPassword.jsp">
			<fmt:message bundle="${locale}" key="login.forgetPassword"/>
		</a>
        
	</form>
</main>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/xxsProtectionScript.js"></script>