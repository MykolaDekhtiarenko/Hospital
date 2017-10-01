<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <fmt:setBundle basename="Labels"/>


</head>
<body>

<c:import url="pages/components/navbar.jsp"/>

<h1>Index page</h1>
<a href="/rest/login">Login page</a>
<a href="/rest/home">Home page</a>
<a href="/rest/error">Error</a>

</body>
</html>
