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
                <fmt:message bundle="${locale}" key="product.btn.addProduct.category"/>
            </legend>
            
            <label class="product-select_wrapper">
                <select class="product-select" name="category" required>
                    <c:forEach var="category" items="${categories}">
                        <c:choose>
                            <c:when test="${not empty bookPreviousData && bookPreviousData.genre.uuid.equals(genre.uuid)}">
                                <option selected value="${category.name}">${category.name}</option>
                            </c:when>
                            <c:otherwise>
                                <option value="${category.name}">${category.name}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
            </label>
            
            <label>
            	<input type="file" name="file" accept=".png" required/>
            </label>
            
            <label class="product-select_wrapper">
            
            	<fmt:message bundle="${locale}" key="product.addProduct.category.image"/>
            	
                <input type="text" name="name" required
                    pattern="^[^!@#$%^&*()_|+~\d]{3,40}$"
                    oninvalid="this.setCustomValidity('<fmt:message bundle="${exc_msg}" key="validation.product.add.category" />')"
                    onchange="this.setAttribute('value', this.value);
                    this.setCustomValidity(this.validity.patternMismatch ? '<fmt:message bundle="${exc_msg}" key="validation.product.add.category" />' : '');"
                >
                <input type="hidden" name="redirectPageCommand" value="addProductCategory">

            </label>
            
            <label>
                <button class="submit" name="action" value="addProductCategory">
                    <span><fmt:message bundle="${locale}" key="product.btn.addProduct.category"/></span>
                </button>
            </label>
            
        </form>
    </div>
</main>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/xxsProtectionScript.js"></script>