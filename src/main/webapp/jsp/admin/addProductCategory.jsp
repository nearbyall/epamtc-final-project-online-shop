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
    
        <form class="fieldset" method="post" action="Controller?action=addProductCategory" enctype="multipart/form-data">
        
            <legend class="title">
                <fmt:message bundle="${locale}" key="admin.addProductCategory"/>
            </legend>
            
            <label class="product-select_wrapper">
            	<fmt:message bundle="${locale}" key="admin.addition.productCategory.image"/>
            	<input type="file" name="file" accept=".jpg" required/>
            </label>
			       
            <label class="product-select_wrapper">
            
            	<fmt:message bundle="${locale}" key="admin.addition.productCategory.name"/>
            	
                <input type="text" name="categoryName" required
                    pattern="^[^!@#$%^&*()_|+~\d]{3,40}$"
                    oninvalid="this.setCustomValidity('<fmt:message bundle="${exc_msg}" key="validation.product.category.name" />')"
                    onchange="this.setAttribute('value', this.value);
                    this.setCustomValidity(this.validity.patternMismatch ? '<fmt:message bundle="${exc_msg}" key="validation.product.category.name" />' : '');"
                >
                <input type="hidden" name="redirectPageCommand" value="addProductCategoryPage">
                
            </label>
            
            <label class="product-select_wrapper">
            	<button class="submit" name="action" value="addProductCategory">
                    <span><fmt:message bundle="${locale}" key="admin.btn.addProductCategory"/></span>
                </button>
            </label>
            
        </form>
    </div>
</main>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/xxsProtectionScript.js"></script>