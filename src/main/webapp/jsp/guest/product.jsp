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
	
	<c:if test="${empty exception_msg}">
		<div class="product-content">
		
			<center>
				<a href="${pageContext.request.contextPath}/Controller?action=openProductPage&productId=${product.id}">
					<img class="grid-image-item" align="middle" src="${pageContext.request.contextPath}${product.imgPath}" width="400" height="300" alt="${product.title}">
				</a>
			</center>
			
			<h2> 
				${product.title} 
			</h2>
			 
			<h4>
				${product.description} 
			</h4>
			
			<p>
				${product.price} 
			</p>
			
			<c:if test="${fn:length(reviews) > 0}">
				<div class="slider">
					<div class="slider__container">
						<div class="slider__wrapper">
							<div class="slider__items">
								<c:forEach var="review" items="${reviews}">
									<div class="slider__item">
										${review.text}
									</div>
							    </c:forEach>	
							</div>
						</div>
					</div>
					<a href="#" class="slider__control" data-slide="prev"></a>
					<a href="#" class="slider__control" data-slide="next"></a>
				</div>
			</c:if>
	
			<c:if test="${role.equals('admin')}">
			
				<div class="admin-bar">                    
					<ul>
						<li>
							<a class="animated-button" href="${pageContext.request.contextPath}/Controller?action=updateProductData">
								<fmt:message bundle="${locale}" key="product.editProduct" />
							</a>
						</li>   
					</ul>
				</div>
			
			</c:if>
			
			<c:if test="${role.equals('admin') || role.equals('user')}">
	
				<div class="admin-bar">                    
					<ul>
						<li>
							<a class="animated-button" href="${pageContext.request.contextPath}/Controller?action=addProductToCart&productId=${product.id}&userId=${userId}&redirectPageCommand=openCartPage">
								<fmt:message bundle="${locale}" key="product.addToCart"/>
							</a>
						</li>
					</ul>
				</div>
				
				<form class="form-wrapper" method="post" action="Controller">
			    
					<fieldset class="fieldset">
			        
						<input type="hidden" name="redirectPageCommand" value="openMainPage">
						<input type="hidden" name="action" value="writeReview">
						<input type="hidden" name="productId" value="${product.id}">
						<input type="hidden" name="userId" value="${user_registration_data.id}">
			
						<legend class="title">
							<fmt:message bundle="${locale}" key="review.legend"/>
						</legend>
			
						<label>
							<span><fmt:message bundle="${locale}" key="review.context"/></span>
							<textarea rows="10" cols="45" name="review" class="input" required></textarea>
						</label>
			            
						<label class="inputfield">
							<input class="btn submit" type="submit" value=<fmt:message bundle="${locale}" key="review.write"/> >
						</label>
			            
					</fieldset>
			        
				</form>
				
			</c:if>	
				
		</div>
	</c:if>
		
</main>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/xxsProtectionScript.js"></script>