<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: mykola.dekhtiarenko
  Date: 03.10.17
  Time: 19:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <c:if test="${not empty language}">
        <fmt:setLocale value="${language}" />
    </c:if>
    <fmt:setBundle basename="Labels"/>
    <fmt:message key="navbar.add_user" var="add_user"/>
    <title>${add_user}</title>
    <link rel="stylesheet" href="/static/css/login.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.7.1/css/bootstrap-datepicker.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.7.1/js/bootstrap-datepicker.js"></script>
</head>
<body>
<c:import url="components/navbar.jsp"/>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.7.1/css/bootstrap-datepicker.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.7.1/js/bootstrap-datepicker.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-nice-select/1.1.0/css/nice-select.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-nice-select/1.1.0/js/jquery.nice-select.min.js"></script>
<script>
    $(document).ready(function() {
        $('select').niceSelect();
    });
</script>


<div class="registration-page">
    <div class="form">
        <form class="login-form" action="/rest/user/create" method="POST">
            <input type="email" name="email" placeholder="email"/>
            <input type="password" name="password" placeholder="password"/>
            <input type="text" name="firstName" placeholder="first name"/>
            <input type="text" name="lastName" placeholder="last name"/>
            <input type="text" name="phone" placeholder="phone"/>
            <select name="role" class="selectpicker">
                <option value="PATIENT">Patient</option>
                <option value="NURSE">Nurse</option>
                <option value="DOCTOR">Doctor</option>
            </select>

            <input class="datepicker" name="birthday" placeholder="birthday" data-date-format="mm/dd/yyyy"/>
            <script>
                $('.datepicker').datepicker({
                 startDate: '-3d'
                });
            </script>


            <input type="submit" class="btn btn-info button margin-zero">
            <p class="message error"> <c:out value="${errorResp.errorMessage}"/></p>
        </form>
    </div>
</div>

</body>
</html>
