<%@ page language="java" contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="crt" uri="customtags"%>

<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="localization" var="locale"/>
<fmt:setBundle basename="exceptionMessages" var="exc_msg"/>

<!DOCTYPE html>
<html lang=&{lang}>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/reset.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/slider.css">
    <script defer type="text/javascript" src="${pageContext.request.contextPath}/js/slider.js"></script>
    <title><fmt:message bundle="${locale}" key="header.home"/></title>
</head>
<body background="${pageContext.request.contextPath}/img/bg2.jpg">
<header>

	<ul>
    
		<li>
			<a href="${pageContext.request.contextPath}/Controller?action=openMainPage">
				<fmt:message bundle="${locale}" key="header.home"/>
			</a>
		</li>

		<c:if test="${role.equals('guest')}">
			<li>
				<a class="active" href="${pageContext.request.contextPath}/jsp/guest/login.jsp">
					<fmt:message bundle="${locale}" key="header.login"/>
				</a>
			</li>
            
			<li>
				<a class="active" href="${pageContext.request.contextPath}/jsp/guest/registration.jsp">
					<fmt:message bundle="${locale}" key="header.registration"/>
				</a>
			</li>
		</c:if>
        
		<c:if test="${role.equals('admin') || role.equals('user')}">
			<li><a class="active" href="${pageContext.request.contextPath}/Controller?action=profilePage">
				<fmt:message bundle="${locale}" key="header.profile"/>
			</a></li>
			<li><a class="active" href="${pageContext.request.contextPath}/Controller?action=logOut">
				<fmt:message bundle="${locale}" key="header.logout"/>
			</a></li>
			<li><a href="${pageContext.request.contextPath}/Controller?action=openUserOrdersPage&recordsPerPage=10&currentPage=1">
				<fmt:message bundle="${locale}" key="header.userOrdersPage"/>
			</a></li>
			<li><a href="${pageContext.request.contextPath}/Controller?action=openCartPage">
				<fmt:message bundle="${locale}" key="header.userCartPage"/>
			</a></li>
			<li><a href="${pageContext.request.contextPath}/Controller?action=topUpBalancePage">
				<fmt:message bundle="${locale}" key="header.userBalance"/> <c:if test="${not empty user_registration_data}"> ${user_registration_data.balance} </c:if>
			</a></li>
		</c:if>
        
		<c:if test="${role.equals('user') || role.equals('guest')}">
			<li>
				<div class="dropdown">
					<form action="Controller" method="post">
						<input type="hidden" name="action" value="switchLanguage">
						<input type="hidden" name="currentPageAbsoluteURL" value="${pageContext.request.requestURL}">
						<input type="hidden" name="currentParameters" value="${pageContext.request.getQueryString()}">
						<div class="dropbtn"><fmt:message bundle="${locale}" key="header.lang"/></div>
						<div class="dropdown-content">
							<button type="submit" name="language" value="ru">
								<fmt:message bundle="${locale}" key="header.lang.rus"/>
							</button >
							<button type="submit" name="language" value="en">
								<fmt:message bundle="${locale}" key="header.lang.eng"/>
							</button>
						</div>
					</form>
				</div>
			</li>
		</c:if>
        
	</ul>
     
	<c:if test="${role.equals('admin')}">
		<div class="admin-bar">
			<div>
				<ul>
					<li>
						<div class="dropdown">
							<form action="Controller" method="post">
								<input type="hidden" name="action" value="switchLanguage">
								<input type="hidden" name="currentPageAbsoluteURL" value="${pageContext.request.requestURL}">
								<input type="hidden" name="currentParameters" value="${pageContext.request.getQueryString()}">
								<div class="dropbtn"><fmt:message bundle="${locale}" key="header.lang"/></div>
								<div class="dropdown-content">
									<button type="submit" name="language" value="ru">
										<fmt:message bundle="${locale}" key="header.lang.rus"/>
									</button >
									<button type="submit" name="language" value="en">
										<fmt:message bundle="${locale}" key="header.lang.eng"/>
									</button>
								</div>
							</form>
						</div>
					</li>
	 
					<li><a href="${pageContext.request.contextPath}/Controller?action=allUsersList&recordsPerPage=10&currentPage=1">
						<fmt:message bundle="${locale}" key="admin.usersList" />
					</a></li>
                    
					<li><a href="${pageContext.request.contextPath}/Controller?action=addProductPage">
						<fmt:message bundle="${locale}" key="admin.addProduct" />
					</a></li>
                    
					<li><a href="${pageContext.request.contextPath}/Controller?action=addProductCategoryPage">
						<fmt:message bundle="${locale}" key="admin.addProductCategory" />
					</a></li>
                    
					<li><a href="${pageContext.request.contextPath}/Controller?action=openAllOrdersPage&recordsPerPage=10&currentPage=1">
						<fmt:message bundle="${locale}" key="admin.orders" />
					</a></li>
                    
				</ul>
			</div>
		</div>
	</c:if>
    
</header>