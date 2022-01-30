<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
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
			<table id="users-list">
				<tr>
					<th><fmt:message bundle="${locale}" key="cart.img"/></th>
					<th><fmt:message bundle="${locale}" key="cart.title"/></th>
					<th><fmt:message bundle="${locale}" key="cart.count"/></th>
					<th><fmt:message bundle="${locale}" key="cart.price"/></th>
				</tr>
				<c:forEach var="item" items="${cartItems}">
					<tr>
						<td width="150">
							<img class="grid-image-item" align="middle" src="${pageContext.request.contextPath}${item.product.imgPath}" width="150" height="130" alt="${product.title}">
						</td>
						<td>
							${item.product.title}
						</td>
						<td>
							<a class="active" href="${pageContext.request.contextPath}/Controller?action=addProductToCart&productId=${item.product.id}&userId=${userId}&redirectPageCommand=openCartPage">
								<fmt:message bundle="${locale}" key="cart.addItem"/>
							</a>						
							${item.count}
							<a class="active" href="${pageContext.request.contextPath}/Controller?action=deleteProductFromCart&productId=${item.product.id}&userId=${userId}&redirectPageCommand=openCartPage">
								<fmt:message bundle="${locale}" key="cart.removeItem"/>
							</a>
						</td>
						<td>
							${item.totalPrice}
						</td>
					</tr>
				</c:forEach>
			</table>
			<center>
				<a href="${pageContext.request.contextPath}/Controller?action=constructOrder&userId=${userId}&redirectPageCommand=openOrderPage" class="floating-button">
					<fmt:message bundle="${locale}" key="cart.constructOrder"/>
				</a>				
			</center>
		</c:if>
		
	</div>
</main>