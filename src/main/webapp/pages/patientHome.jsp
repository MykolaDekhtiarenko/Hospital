<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: mykola.dekhtiarenko
  Date: 18.09.17
  Time: 12:21
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <fmt:setBundle basename="Labels"/>
    <fmt:requestEncoding value = "UTF-8" />
    <fmt:message key="patient_homepage_title" var="title" />
    <title>${title}</title>
</head>
<body>
    <fmt:message key="patient_homepage_header_label" var="homepage_header" />
    <h1>${homepage_header}</h1>
    <div> <c:out value="${fullPatientInfo}"/></div>
    <div>${fullPatientInfo.lastName}</div>
    <div>${fullPatientInfo.getEmail()}</div>


</body>
</html>
