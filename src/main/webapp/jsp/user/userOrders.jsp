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
					<th><fmt:message bundle="${locale}" key="order.id"/></th>
					<th><fmt:message bundle="${locale}" key="order.createdAt"/></th>
					<th><fmt:message bundle="${locale}" key="order.updatedAt"/></th>
					<th><fmt:message bundle="${locale}" key="order.items"/></th>
					<th><fmt:message bundle="${locale}" key="order.totalPrice"/></th>
					<th><fmt:message bundle="${locale}" key="order.accepted"/></th>
				</tr>
				<c:forEach var="order" items="${orders}">
					<tr>
						<td>
							${order.id}
						</td>
						<td>
							${order.createdAt}
						</td>
						<td>
							${order.updatedAt}
						</td>
						<td>
							<c:forEach var="item" items="${order.orderItems}">
								<fmt:message bundle="${locale}" key="orders.productTitle" /> ${item.product.title} <br>
								<fmt:message bundle="${locale}" key="orders.productCount" /> ${item.count} <br>
							</c:forEach>
						</td>
						<td>
							${order.totalPrice}
						</td>
						<td>
							${order.status.type}
						</td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
		
	</div>
</main>