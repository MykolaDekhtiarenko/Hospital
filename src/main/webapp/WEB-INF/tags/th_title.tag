<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@attribute name="startDate" required="true" type="java.sql.Date"%>
<%@attribute name="endDate" required="true" type="java.sql.Date"%>

<c:if test="${not empty language}">
    <fmt:setLocale value="${language}" />
</c:if>
<fmt:setBundle basename="Labels"/>

<fmt:message key="patient_detailed_info.treatment_history" var="treatment_history"/>
<fmt:message key="patient_detailed_info.now" var="now"/>

${treatment_history}:
<fmt:formatDate type="date" value="${startDate}"/>
-
<fmt:formatDate type="date" value="${endDate}"/>
<c:if test="${empty endDate}">
    ${now}
</c:if>