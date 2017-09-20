<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Login page</title>
</head>
<body>
<form action="/rest/login" method="POST">
    <input type="email" name="email"><br>
    <input type="password" name="password"><br>
    <input type="submit">

    <div> <c:out value="${unableToLogin}"/></div>

    <br>
    <a href="/">Index</a>
</form>
</body>
</html>
