<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: mykola.dekhtiarenko
  Date: 13.09.17
  Time: 15:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <fmt:setBundle basename="Labels"/>
    <fmt:message key="staff_homepage_title" var="title" />
    <title>${title}</title>
</head>
<body>
    <fmt:message key="staff_homepage_header_label" var="header_label" />
    <h1>${header_label}</h1>
</body>
</html>
