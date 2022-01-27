
<main class="content">

    <c:if test="${not empty exception_msg}">
        <div class="error-message">
            <p>
                <fmt:message bundle="${exc_msg}"  key="${exception_msg}"/>
            </p>
        </div>
    </c:if>
    
   <a class="product-search" href="${pageContext.request.contextPath}/Controller?action=productCatalogPage&recordsPerPage=10&currentPage=1">
        <fmt:message bundle="${locale}"  key="product.btn.catalog"/>
    </a>
    
    <section>
    	<div id="grid">
		    <c:if test="${ empty exception_msg}">
		    	<c:forEach var="category" items="${categories}">
		    		<div class="grid-item">
		    			<a href="">
		    				<img class="grid-image-item" src="${pageContext.request.contextPath}${category.imgPath}" width="180" height="180" alt="">
		    			</a>
		    			<h4> 
		    				${category.name} 
		    			</h4> 
		    		</div>
		    	</c:forEach>
		    </c:if>
	    </div>
    </section>
    
</main>