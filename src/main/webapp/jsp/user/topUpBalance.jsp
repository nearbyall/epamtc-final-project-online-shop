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
        
			<input type="hidden" name="action" value="topUpBalance">
			<input type="hidden" name="redirectPageCommand" value="topUpBalancePage">
            
			<legend class="title">
				<fmt:message  bundle="${locale}" key="topUpBalance.legend"/>
			</legend>
            
			<label>
				<span><fmt:message  bundle="${locale}" key="topUpBalance.cardNumber"/></span>
				<input type="text"  name="cardNumber" class="input" required
					pattern="(?<!\d)\d{16}(?!\d)|(?<!\d[ _-])(?<!\d)\d{4}([_ -])\d{4}(?:\1\d{4}){2}(?![_ -]?\d)"
					oninvalid="this.setCustomValidity('<fmt:message bundle="${exc_msg}" key="validation.topUpBalance.cardNumber"/>')"
					onchange="this.setAttribute('value', this.value);
					this.setCustomValidity(this.validity.patternMismatch ? '<fmt:message bundle="${exc_msg}" key="validation.topUpBalance.cardNumber" />' : '');"
				>
			</label>
            
			<label>
				<span><fmt:message  bundle="${locale}" key="topUpBalance.date"/></span>
				<input class="input"  type="text" name="cardDate" required
					pattern="(0[1-9]|1[0-2])\/?(([0-9]{4})|[0-9]{2}$)"
					oninvalid="this.setCustomValidity('<fmt:message bundle="${exc_msg}" key="validation.topUpBalance.cardDate" />')"
					onchange="this.setAttribute('value', this.value);
					this.setCustomValidity(this.validity.patternMismatch ? '<fmt:message bundle="${exc_msg}" key="validation.topUpBalance.cardDate" />' : '');"
				>
			</label>
            
			<label>
				<span><fmt:message  bundle="${locale}" key="topUpBalance.CVV"/></span>
				<input type="text" name="cardCVV" class="input" required
					pattern="^[0-9]{3, 4}$"
					oninvalid="this.setCustomValidity('<fmt:message bundle="${exc_msg}" key="validation.topUpBalance.cardCVV" />')"
					onchange="this.setAttribute('value', this.value);
					this.setCustomValidity(this.validity.patternMismatch ? '<fmt:message bundle="${exc_msg}" key="validation.topUpBalance.cardCVV" />' : '');"
				>
			</label>
            
			<label>
				<span><fmt:message  bundle="${locale}" key="topUpBalance.money"/></span>
				<input type="text" name="cardSumm" class="input" required
					pattern="^[+-]?[0-9]{1,3}(?:,?[0-9]{3})*\.[0-9]{2}$"
					oninvalid="this.setCustomValidity('<fmt:message bundle="${exc_msg}" key="validation.topUpBalance.money" />')"
					onchange="this.setAttribute('value', this.value);
					this.setCustomValidity(this.validity.patternMismatch ? '<fmt:message bundle="${exc_msg}" key="validation.topUpBalance.money" />' : '');"
				>
			</label>

			<label class="inputfield">
				<input class="btn  submit" type="submit" value=<fmt:message  bundle="${locale}" key="topUpBalance.btn"/>>
			</label>
            
		</fieldset>
	</form>
</main>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/xxsProtectionScript.js"></script>