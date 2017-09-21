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
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js" integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js" integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1" crossorigin="anonymous"></script>

    <fmt:setBundle basename="Labels"/>
    <fmt:requestEncoding value = "UTF-8" />
    <fmt:message key="patient_homepage_title" var="title" />
    <title>${title}</title>
</head>
<body>
    <fmt:message key="patient_homepage_header_label" var="homepage_header" />
    <fmt:message key="logged_in_message" var="logged_in_message" />
    <fmt:message key="log_out" var="log_out" />
    <fmt:message key="now" var="now" />


    <nav class="navbar navbar-dark sticky-top bg-dark">
        <a class="navbar-brand" href="/">Hospital #1</a>
        <span class="navbar-text ">
            ${logged_in_message} ${fullPatientInfo.getFirstName()} ${fullPatientInfo.getLastName()} / <a href="/rest/logout">${log_out}</a>
        </span>
    </nav>

    <center><h1>${homepage_header}</h1></center>
    <div class="container">
        <div id="accordion" role="tablist">
            <c:forEach items="${fullPatientInfo.getTreatmentHistoryList()}" var="treatmentHistory">
                <div class="card">
                    <div class="card-header" role="tab" id="heading${treatmentHistory.getId()}">
                        <h5 class="mb-0">
                            <a data-toggle="collapse" href="#collapse${treatmentHistory.getId()}" aria-expanded="true" aria-controls="collapse${treatmentHistory.getId()}">
                                ${treatmentHistory.getStartDate()} - <c:out value="${treatmentHistory.getEndDate()}" default="${now}"/>
                            </a>
                        </h5>
                    </div>

                    <div id="collapse${treatmentHistory.getId()}" class="collapse" role="tabpanel" aria-labelledby="heading${treatmentHistory.getId()}" data-parent="#accordion">
                        <div class="card-body">
                            ${fullPatientInfo}
                        </div>
                    </div>
                </div>
            </c:forEach>

        </div>
    </div>




</body>
</html>
