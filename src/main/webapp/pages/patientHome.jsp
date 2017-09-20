<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: mykola.dekhtiarenko
  Date: 18.09.17
  Time: 12:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <fmt:setBundle basename="Labels"/>
    <fmt:message key="patient_homepage_title" var="title" />
    <title>${title}</title>
</head>
<body>

    <fmt:message key="patient_homepage_header_label" var="header" />
    <h1>${header}</h1>
</body>
</html>
