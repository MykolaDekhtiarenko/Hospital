<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: mykola.dekhtiarenko
  Date: 03.10.17
  Time: 19:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create user</title>
    <c:if test="${not empty language}">
        <fmt:setLocale value="${language}" />
    </c:if>
    <fmt:setBundle basename="Labels"/>
    <link rel="stylesheet" href="/static/css/login.css">
</head>
<body>
<c:import url="components/navbar.jsp"/>

<div class="registration-page">
    <div class="form">
        <form class="login-form" action="/rest/user/create" method="POST">
            <input type="email" name="email" placeholder="email"/>
            <input type="password" name="password" placeholder="password"/>
            <input type="text" name="firstName" placeholder="first name"/>
            <input type="text" name="lastName" placeholder="last name"/>
            <input type="text" name="phone" placeholder="phone"/>
            <input type="text" name="birthday" placeholder="your birthday"/>
            <input type="text" name="role" placeholder="role"/>




            <input type="submit" class="btn btn-info button margin-zero">
            <p class="message error"> <c:out value="${unableToLogin}"/></p>
        </form>
    </div>
</div>

</body>
</html>
