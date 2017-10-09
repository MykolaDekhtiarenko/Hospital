<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
    <c:if test="${not empty language}">
        <fmt:setLocale value="${language}" />
    </c:if>
    <fmt:setBundle basename="Labels"/>
    <title>Login page</title>
    <link rel="stylesheet" href="/static/css/login.css">
</head>
<body>

<c:import url="components/navbar.jsp"/>

<fmt:message key="registration.email" var="email"/>
<fmt:message key="registration.password" var="password"/>
<fmt:message key="general.go_back" var="go_back"/>
<fmt:message key="user.log_in" var="log_in"/>
<fmt:message key="general.index_page" var="index_page"/>

<div class="login-page">
    <div class="form">
        <form class="login-form" action="/rest/login" method="POST">
            <input type="text" name="email" placeholder="${email}"/>
            <input type="password" name="password" placeholder="${password}"/>
            <input type="submit" class="btn btn-info button" value="${log_in}">
            <p class="message error"> <c:out value="${unableToLogin}"/></p>
            <p class="message">${go_back}? <a href="/">${index_page}</a></p>
        </form>
    </div>
</div>
</body>
</html>
