<%@ page language="java" contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="localization" var="locale"/>
<fmt:setBundle basename="exceptionMessages" var="exc_msg"/>

<!DOCTYPE html>
<html lang=&{lang}>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/reset.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
    <title><fmt:message bundle="${locale}" key="header.home"/></title>
</head>
<body>
<header>

    <ul>
    
		<li>
			<a href="${pageContext.request.contextPath}/jsp/main.jsp">
            		<fmt:message bundle="${locale}" key="header.home"/>
			</a>
		</li>

		<c:if test="${role.equals('guest')}">
			<li>
				<a class="active" href="${pageContext.request.contextPath}/jsp/login.jsp">
					<fmt:message bundle="${locale}" key="header.login"/>
            	</a>
            </li>
            
            <li>
            	<a class="active" href="${pageContext.request.contextPath}/jsp/registration.jsp">
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
            <li><a href="${pageContext.request.contextPath}/Controller?action=userOrdersPage&recordsPerPage=10&currentPage=1">
                <fmt:message bundle="${locale}" key="header.userOrdersPage"/>
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
                    <li><a href="${pageContext.request.contextPath}/Controller?action=adminPage">
                        <fmt:message bundle="${locale}" key="admin.onlineUserList" />
                    </a></li>
                    <li><a href="${pageContext.request.contextPath}/Controller?action=allUsersList&recordsPerPage=10&currentPage=1">
                        <fmt:message bundle="${locale}" key="admin.usersList" />
                    </a></li>
                    <li><a href="${pageContext.request.contextPath}/Controller?action=addBookPage">
                        <fmt:message bundle="${locale}" key="admin.addProduct" />
                    </a></li>
                    <li><a href="${pageContext.request.contextPath}/Controller?action=openOrdersPage&recordsPerPage=10&currentPage=1">
                        <fmt:message bundle="${locale}" key="admin.orders" />
                    </a></li>
                </ul>
            </div>
        </div>
    </c:if>
    
</header>