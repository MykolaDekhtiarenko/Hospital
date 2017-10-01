<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--
  Created by IntelliJ IDEA.
  User: mykola.dekhtiarenko
  Date: 18.09.17
  Time: 12:21
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <c:if test="${not empty language}">
        <fmt:setLocale value="${language}" />
    </c:if>
    <fmt:setBundle basename="Labels"/>
    <fmt:message key="patient_homepage_title" var="title"/>
    <title>${title}</title>
    <link rel="stylesheet" href="/static/css/main.css">

</head>
<body>
<fmt:message key="patient_homepage_header_label" var="homepage_header"/>
<fmt:message key="now" var="now"/>
<fmt:message key="diagnose_date_label" var="diagnose_date_label"/>
<fmt:message key="doctor_assignment" var="doctor_assignment"/>
<fmt:message key="assignment_done" var="assignment_done"/>

<c:import url="components/navbar.jsp"/>
<script src="/static/javascript/main.js"></script>

<center><h1>${homepage_header}</h1></center>
<div class="container">
    <div id="accordion" role="tablist">
        <c:if test="${user.role == 'DOCTOR'}">
            <form method="POST" action="/rest/treatmentHistory/create">
                <fmt:message key="patient_detailed_info.new_treatment_history" var="new_treatment_history"/>
                <input type="hidden" name="userId" value="${fullPatientInfo.id}">
                <input type="submit" class="btn btn-outline-info" value="${new_treatment_history}"/></input>
            </form>
            <c:if test="${not empty error}">
                <div class="alert alert-danger" role="alert">
                    ${error}
                </div>
            </c:if>
        </c:if>

        <c:forEach items="${fullPatientInfo.treatmentHistoryList}" var="treatmentHistory">
            <div class="card">
                <div class="card-header" role="tab" id="heading${treatmentHistory.id}">
                    <h5 class="mb-0">
                        <a data-toggle="collapse" href="#collapse${treatmentHistory.id}" aria-expanded="true"
                           aria-controls="collapse${treatmentHistory.getId()}">
                            <fmt:formatDate type="date" value="${treatmentHistory.startDate}"/>
                            -
                            <fmt:formatDate type="date" value="${treatmentHistory.endDate}"/>
                            <c:if test="${empty treatmentHistory.endDate}">
                                ${now}
                            </c:if>

                        </a>
                    </h5>
                </div>

                <div id="collapse${treatmentHistory.id}" class="collapse" role="tabpanel"
                     aria-labelledby="heading${treatmentHistory.getId()}" data-parent="#accordion">
                    <div class="card-body">
                        <c:choose>
                            <c:when test="${not empty treatmentHistory.endDate}">
                                <div class="alert alert-info" role="alert">
                                    ${treatmentHistory.conclusion}
                                </div>
                            </c:when>

                            <c:otherwise>
                                <c:if test="${user.role == 'DOCTOR'}">
                                    <form method="post" action="/rest/treatmentHistory/update">
                                        <input type="hidden" name="userId" value="${fullPatientInfo.id}">
                                        <input type="hidden" name="treatmentHistoryId"
                                               value="${treatmentHistory.id}">
                                        <fmt:message key="patient_detailed_info.conclusion" var="conclusion"/>
                                        <fmt:message key="patient_detailed_info.close_treatment_history"
                                                     var="close_treatment_history"/>

                                        <div class="input-group input-group-lg">
                                            <input type="text" name="conclusion" class="form-control" placeholder="${conclusion}"/>

                                            <div class="input-group-btn">
                                                <button type="submit" class="btn btn-outline-danger">${close_treatment_history}</button>
                                            </div><!-- /btn-group -->
                                        </div>


                                    </form>
                                </c:if>
                            </c:otherwise>
                        </c:choose>
                        <c:forEach items="${treatmentHistory.diagnoseList}" var="diagnose">
                            <div>
                                <h5>${diagnose_date_label} <ftm:formatDate type="date" value="${diagnose.date}"/></h5>
                                <div>${diagnose.diagnose}</div>
                                <c:if test="${not empty diagnose.assignmentList}">
                                    <div>${doctor_assignment}:</div>
                                </c:if>
                                <ol>
                                    <c:forEach items="${diagnose.assignmentList}" var="assignment">
                                        <li>
                                            <span class="<c:if test="${not empty assignment.dateOfExecution}">strike</c:if>">${assignment.description}</span>

                                            <c:if test="${empty assignment.dateOfExecution and empty treatmentHistory.conclusion }">
                                                <c:if test="${user.role == 'NURSE' or user.role == 'DOCTOR'}">
                                                    <c:if test="${assignment.type == 'MEDICINE' or assignment.type =='PROCEDURE'}">
                                                        <fmt:message key="assignment.execute" var="execute"/>
                                                        <form class="inline-form" method="post" action="/rest/assignment/update">
                                                            <input type="hidden" name="assignmentId" value="${assignment.id}">
                                                            <input type="hidden" name="userId" value="${fullPatientInfo.id}">
                                                            <input type="submit" role="button" class="btn btn-link execute" value="${execute}">
                                                        </form>
                                                    </c:if>
                                                </c:if>
                                                <c:if test="${user.role == 'DOCTOR'}">
                                                    <c:if test="${assignment.type == 'SURGERY'}">
                                                        <fmt:message key="assignment.execute" var="execute"/>
                                                        <form class="inline-form" method="post" action="/rest/assignment/update">
                                                            <input type="hidden" name="assignmentId" value="${assignment.id}">
                                                            <input type="hidden" name="userId" value="${fullPatientInfo.id}">
                                                            <input type="submit" role="button" class="btn btn-link execute" value="${execute}">
                                                        </form>
                                                    </c:if>
                                                </c:if>
                                            </c:if>



                                        </li>
                                    </c:forEach>
                                </ol>
                                <c:if test="${empty treatmentHistory.endDate && user.role == 'DOCTOR'}">
                                    <form method="POST" action="/rest/assignment/create">
                                        <input type="hidden" name="userId" value="${fullPatientInfo.id}"/>
                                        <input type="hidden" name="diagnoseId" value="${diagnose.id}" />
                                        <fmt:message key="patient_detailed_info.surgery" var="surgery"/>
                                        <fmt:message key="patient_detailed_info.procedure" var="procedure"/>
                                        <fmt:message key="patient_detailed_info.medicine" var="medicine"/>
                                        <div class="link-block">
                                            <a class="add-procedure link" href="javascript:void(0)">+1 ${procedure}</a>
                                            <a class="add-medicine link" href="javascript:void(0)">+1 ${medicine}</a>
                                            <a class="add-surgery link" href="javascript:void(0)">+1 ${surgery}</a>
                                        </div>
                                        <div class="text-center">
                                            <fmt:message key="patient_detailed_info.add_assignment_list" var="add_assignment_list"/>
                                            <input type="submit" class="submit btn btn-outline-info" style="display: none" value="${add_assignment_list}"/>
                                        </div>
                                    </form>
                                </c:if>

                            </div>
                        </c:forEach>
                        <c:if test="${empty treatmentHistory.conclusion}">
                            <c:if test="${user.role == 'DOCTOR'}">
                                <form class="form diagnose-form" method="post" action="/rest/diagnose/create">
                                    <input type="hidden" name="userId" value="${fullPatientInfo.id}">
                                    <input type="hidden" name="treatmentHistoryId"
                                           value="${treatmentHistory.id}">

                                    <fmt:message key="patient_detailed_info.diagnose" var="diagnose"/>
                                    <textarea type="text" rows="3" class="form-control col-xs-12" name="diagnose" placeholder="${diagnose}"></textarea>


                                    <fmt:message key="patient_detailed_info.create_diagnose" var="create_diagnose"/>
                                    <div class="float-right">
                                        <button type="submit"
                                                class="btn btn-info">${create_diagnose}</button>
                                    </div>



                                </form>
                            </c:if>
                        </c:if>

                    </div>
                </div>
            </div>
        </c:forEach>

    </div>
</div>


<fmt:message key="patient_detailed_info.surgery_assignment" var="surgery_assignment"/>
<fmt:message key="patient_detailed_info.procedure_assignment" var="procedure_assignment"/>
<fmt:message key="patient_detailed_info.medicine_assignment" var="medicine_assignment"/>

<script>
    var medicineInput = '<div class="input-group">'+
        '<input type="text" class="form-control" name="medicine" placeholder="${medicine_assignment}"/>'+
        '</div>';

    var procedureInput = '<div class="input-group">'+
        '<input type="text" class="form-control" name="procedure" placeholder="${procedure_assignment}"/>'+
        '</div>';

    var surgeryInput = '<div class="input-group">'+
        '<input type="text" class="form-control" name ="surgery" placeholder="${surgery_assignment}"/>'+
        '</div>';
</script>

</body>
</html>
