<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<main class="content">

	<c:if test="${not empty exception_msg}">
		<div class="error-message">
			<p>
				<fmt:message bundle="${exc_msg}"  key="${exception_msg}"/>
			</p>
		</div>
	</c:if>
    
	<form class="form-wrapper" method="post" action="Controller" id="user-info-form">
		<fieldset class="fieldset">
        
			<input type="hidden" name="action" value="updateUserInfo">
			<input type="hidden" name="redirectPageCommand" value="profilePage">
            
			<legend class="title">
				<fmt:message  bundle="${locale}" key="profile.legend"/>
			</legend>
            
			<label>
				<span><fmt:message  bundle="${locale}" key="user.password"/></span>
				<input type="password"  name="password" class="input" required
					pattern="^[\w-]{8,16}$"
					oninvalid="this.setCustomValidity('<fmt:message bundle="${exc_msg}" key="validation.user.registration.password" />')"
					onchange="this.setAttribute('value', this.value);
					this.setCustomValidity(this.validity.patternMismatch ? '<fmt:message bundle="${exc_msg}" key="validation.user.registration.password" />' : '');"
				>
			</label>
            
			<label>
				<span><fmt:message  bundle="${locale}" key="user.firstname"/></span>
				<input class="input"  type="text" name="name" required
					pattern="^.{1,25}$"
					oninvalid="this.setCustomValidity('<fmt:message bundle="${exc_msg}" key="validation.user.registration.fieldlength" />')"
					onchange="this.setAttribute('value', this.value);
					this.setCustomValidity(this.validity.patternMismatch ? '<fmt:message bundle="${exc_msg}" key="validation.user.registration.fieldlength" />' : '');"
					<c:if test="${not empty user_registration_data}"> value=${user_registration_data.name} </c:if>
				>
			</label>
            
			<label>
				<span><fmt:message  bundle="${locale}" key="user.lastname"/></span>
				<input type="text" name="surname" class="input" required
					pattern="^.{1,25}$"
					oninvalid="this.setCustomValidity('<fmt:message bundle="${exc_msg}" key="validation.user.registration.fieldlength" />')"
					onchange="this.setAttribute('value', this.value);
					this.setCustomValidity(this.validity.patternMismatch ? '<fmt:message bundle="${exc_msg}" key="validation.user.registration.fieldlength" />' : '');"
					<c:if test="${not empty user_registration_data}"> value=${user_registration_data.surname} </c:if>
				>
			</label>

			<label>
					<span><fmt:message  bundle="${locale}" key="user.phone"/></span>
					<input type="text" name="mobile" class="input" required pattern="^[+]?[\d]{7,15}$"
					oninvalid="this.setCustomValidity('<fmt:message bundle="${exc_msg}" key="validation.user.registration.phone" />')"
					onchange="this.setAttribute('value', this.value);
					this.setCustomValidity(this.validity.patternMismatch ? '<fmt:message bundle="${exc_msg}" key="validation.user.registration.phone" />' : '');"
					<c:if test="${not empty user_registration_data}"> value=${user_registration_data.mobile} </c:if>
				>
			</label>

			<label class="inputfield">
				<input class="btn  submit" type="submit" value=<fmt:message  bundle="${locale}" key="profile.btn.updateInfo"/>>
			</label>
            
		</fieldset>
	</form>
</main>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/xxsProtectionScript.js"></script>