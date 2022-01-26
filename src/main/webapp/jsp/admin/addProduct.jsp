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
    <div class="form-wrapper" id="component-form">
    
        <form class="fieldset" method="post" action="Controller?action=addProduct" enctype="multipart/form-data">
        
        	<input type="hidden" name="redirectPageCommand" value="openMainPage">
        
            <legend class="title">
                <fmt:message bundle="${locale}" key="admin.addProduct"/>
            </legend>
            
            <label class="product-select_wrapper">
            	<fmt:message bundle="${locale}" key="admin.addition.product.image"/>
            	<input type="file" name="file" accept=".png" required/>
            </label>

            <label class="product-select_wrapper">
            
            	<fmt:message bundle="${locale}" key="admin.addition.product.category"/>
            
                <select class="product-select" name="category" required>
                    <c:if test="${empty productPreviousData}">
                        <option selected disabled value=""><fmt:message bundle="${locale}" key="product.category" /></option>
                    </c:if>                
                    <c:forEach var="category" items="${categories}">
                        <c:choose>
                            <c:when test="${not empty productPreviousData}">
                                <option selected value="${category.id}">${category.name}</option>
                            </c:when>
                            <c:otherwise>
                                <option value="${category.id}">${category.name}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
                
            </label>           

            <label class="product-select_wrapper">
            
            	<fmt:message bundle="${locale}" key="admin.addition.product.title"/>
            	
                <input type="text" name="title" required
                    pattern="^[^!@#$%^&*()_|+~\d]{3,40}$"
                    oninvalid="this.setCustomValidity('<fmt:message bundle="${exc_msg}" key="validation.product.title" />')"
                    onchange="this.setAttribute('value', this.value);
                    this.setCustomValidity(this.validity.patternMismatch ? '<fmt:message bundle="${exc_msg}" key="validation.product.title" />' : '');"
                >
                
            </label>
            
            <label class="product-select_wrapper">
            
            	<fmt:message bundle="${locale}" key="admin.addition.product.description"/>
            	
                <input type="text" name="description" required
                    pattern="^(.|\s)*[a-zA-Z]+(.|\s)*$"
                    oninvalid="this.setCustomValidity('<fmt:message bundle="${exc_msg}" key="validation.product.description" />')"
                    onchange="this.setAttribute('value', this.value);
                    this.setCustomValidity(this.validity.patternMismatch ? '<fmt:message bundle="${exc_msg}" key="validation.product.description" />' : '');"
                >
                
            </label>
            
            <label class="product-select_wrapper">
            
            	<fmt:message bundle="${locale}" key="admin.addition.product.price"/>
            	
                <input type="text" name="price" required
                    pattern="^[+-]?[0-9]{1,3}(?:,?[0-9]{3})*\.[0-9]{2}$"
                    oninvalid="this.setCustomValidity('<fmt:message bundle="${exc_msg}" key="validation.product.price" />')"
                    onchange="this.setAttribute('value', this.value);
                    this.setCustomValidity(this.validity.patternMismatch ? '<fmt:message bundle="${exc_msg}" key="validation.product.price" />' : '');"
                >
                
            </label>
            
            <label class="product-select_wrapper">
            
            	<fmt:message bundle="${locale}" key="admin.addition.product.count"/>
            	
                <input type="text" name="count" required
                    pattern="(?<![-.])\b[0-9]+\b(?!\.[0-9])"
                    oninvalid="this.setCustomValidity('<fmt:message bundle="${exc_msg}" key="validation.product.count" />')"
                    onchange="this.setAttribute('value', this.value);
                    this.setCustomValidity(this.validity.patternMismatch ? '<fmt:message bundle="${exc_msg}" key="validation.product.count" />' : '');"
                >
                
            </label>
            
            <label class="product-select_wrapper">
            	<button class="submit" name="action" value="addProduct">
                    <span><fmt:message bundle="${locale}" key="admin.btn.addProduct"/></span>
                </button>
            </label>
            
        </form>
        
    </div>
</main>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/xxsProtectionScript.js"></script>