<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <c:if test="${not empty language}">
        <fmt:setLocale value="${language}" />
    </c:if>
    <fmt:setBundle basename="Labels"/>
</head>
<body>

<c:import url="pages/components/navbar.jsp"/>
<fmt:message key="general.index_page" var="index_page"/>

<center><h1>${index_page}</h1></center>

</body>
</html>
