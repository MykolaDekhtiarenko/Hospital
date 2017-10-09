<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <c:if test="${not empty language}">
        <fmt:setLocale value="${language}" />
    </c:if>
    <fmt:setBundle basename="Labels"/>
    <title>${title}</title>
</head>
<body>
    <fmt:message key="staffpage.header_label" var="header_label" />
    <fmt:message key="staffpage.patient_label" var="patient_label" />
    <fmt:message key="staffpage.phone_label" var="phone_label" />
    <fmt:message key="staffpage.birthday_label" var="birthday_label" />
    <fmt:message key="staffpage.email_label" var="email_label" />
    <fmt:message key="staffpage.page_label" var="page_label" />
    <fmt:message key="staffpage.open_full_info" var="open_full_info" />
    <fmt:message key="staffpage.sick_patients_title" var="sick_patients_title"/>
    <fmt:message key="staffpage.all_patients_title" var="all_patients_title"/>


    <c:import url="components/navbar.jsp"/>

    <center>
        <h1>
            <c:if test="${title == 'all'}">
                ${all_patients_title}
            </c:if>
            <c:if test="${title == 'sick'}">
                ${sick_patients_title}
            </c:if>

        </h1>
    </center>

    <div class="container">
        <table class="table table-striped">
            <tr>
                <th>${patient_label}</th>
                <th>${phone_label}</th>
                <th>${birthday_label}</th>
                <th>${email_label}</th>
                <th class="text-center">${page_label}</th>

            </tr>
            <c:forEach items="${patientList}" var="patient">
                <tr>
                    <th>${patient.firstName} ${patient.lastName}</th>
                    <th>${patient.phone}</th>
                    <th>${patient.birthday}</th>
                    <th>${patient.email}</th>
                    <th class="text-center">
                        <form method="GET" action="/rest/patient/">
                            <input type="hidden" name="userId" value="${patient.id}"/>
                            <input class="btn btn-outline-info btn-sm" type="submit" value="${open_full_info}">
                        </form>

                    </th>
                </tr>
            </c:forEach>
        </table>
    </div>


</body>
</html>
