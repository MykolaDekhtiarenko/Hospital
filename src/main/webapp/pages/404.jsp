<%--
  Created by IntelliJ IDEA.
  User: mykola.dekhtiarenko
  Date: 13.09.17
  Time: 16:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <c:if test="${not empty language}">
        <fmt:setLocale value="${language}" />
    </c:if>
    <fmt:setBundle basename="Labels"/>
    <title>Title</title>
</head>
<body>
    <%--<h1>Во фсем виноват Нэвэльный!</h1>--%>
    <h1>Уккщк зфпу</h1>
    <br>
    <a href="/">Index</a>

</body>
</html>
