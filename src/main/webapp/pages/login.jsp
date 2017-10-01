<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
    <c:if test="${not empty language}">
        <fmt:setLocale value="${language}" />
    </c:if>
    <fmt:setBundle basename="Labels"/>    <title>Login page</title>
    <link rel="stylesheet" href="/static/css/login.css">
</head>
<body>

<c:import url="components/navbar.jsp"/>


<%--<br>--%>
<%--<form action="/rest/login" method="POST">--%>
    <%--<input type="email" name="email"><br>--%>
    <%--<input type="password" name="password"><br>--%>
    <%--<input type="submit">--%>

    <%--<div> <c:out value="${unableToLogin}"/></div>--%>

    <%--<br>--%>
    <%--<a href="/">Index</a>--%>
<%--</form>--%>

<div class="login-page">
    <div class="form">
        <form class="login-form" action="/rest/login" method="POST">
            <input type="text" name="email" placeholder="email"/>
            <input type="password" name="password" placeholder="password"/>
            <input type="submit" class="btn btn-info button">
            <p class="message error"> <c:out value="${unableToLogin}"/></p>
            <p class="message">Go back? <a href="/">Index page</a></p>
        </form>
    </div>
</div>
</body>
</html>
