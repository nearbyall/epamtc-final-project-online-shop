<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<main class="content">
	<div class="table_content-wrapper">
		<c:if test="${not empty exception_msg}">
			<div class="error-message">
				<p>
					<fmt:message bundle="${exc_msg}"  key="${exception_msg}"/>
				</p>
			</div>
		</c:if>
		<c:if test="${empty exception_msg}">

		<section>
			<div id="grid">
				<c:if test="${ empty exception_msg}">
					<c:forEach var="product" items="${products}">
						<div class="grid-item">
							<center>
								<a href="${pageContext.request.contextPath}/Controller?action=openProductPage&productId=${product.id}">
									<img class="grid-image-item" align="middle" src="${pageContext.request.contextPath}${product.imgPath}" width="180" height="170" alt="">
								</a>
							</center>
							<h4> 
								${product.title} 
							</h4> 
							<p>
								${product.price} 
							</p>
						</div>
					</c:forEach>
				</c:if>
			</div>
		</section>

		<nav>
			<ul class="pagination">
				<c:if test="${currentPage != 1}">
					<li class="page-item">
						<a class="page-link" href="Controller?action=openCatalogPage&recordsPerPage=${recordsPerPage}&currentPage=${currentPage-1}">
							<fmt:message bundle="${locale}" key="pagination.prev"/>
						</a>
					</li>
				</c:if>

				<c:forEach begin="1" end="${pagesQuantity}" var="i">
					<c:choose>
						<c:when test="${currentPage eq i}">
							<li class="page-item active">
								<a class="page-link">
									${i} <span class="sr-only"><fmt:message bundle="${locale}" key="pagination.current"/></span>
								</a>
							</li>
						</c:when>
						<c:otherwise>
							<li class="page-item">
								<a class="page-link" href="Controller?action=openCatalogPage&recordsPerPage=${recordsPerPage}&currentPage=${i}">${i}</a>
							</li>
						</c:otherwise>
					</c:choose>
				</c:forEach>

				<c:if test="${currentPage lt pagesQuantity}">
					<li class="page-item">
						<a class="page-link" href="Controller?action=openCatalogPage&recordsPerPage=${recordsPerPage}&recordsPerPage=${recordsPerPage}&currentPage=${currentPage+1}">
							<fmt:message bundle="${locale}" key="pagination.next"/>
						</a>
					</li>
				</c:if>
                    
			</ul>
		</nav>
            
		</c:if>
        
		<c:if test="${empty products}">
			<p class="error-message">
				<fmt:message bundle="${locale}" key="find.catalog.isEmpty"/>
			</p>
		</c:if>
        
	</div>
</main>