<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <c:if test="${not empty language}">
        <fmt:setLocale value="${language}" />
    </c:if>
    <fmt:setBundle basename="Labels"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js" integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js" integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="/static/css/main.css">
    <script src="/static/javascript/locale.js"></script>
</head>
<body>
    <fmt:message key="log_out" var="log_out"/>
    <fmt:message key="log_in" var="log_in"/>
    <nav class="navbar navbar-expand navbar-dark sticky-top bg-dark">
        <a class="navbar-brand" href="/">Hospital.One</a>

        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <fmt:message key="navbar.home_page" var="home_page"/>
        <fmt:message key="navbar.patient_list_page" var="patient_list_page"/>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link " href="/rest/home">${home_page}</a>
                </li>
                <c:if test="${not empty user}">
                    <c:if test="${user.role == 'NURSE' or user.role == 'DOCTOR'}">
                        <li class="nav-item">
                            <a class="nav-link" href="/rest/patients">${patient_list_page}</a>
                        </li>
                    </c:if>
                </c:if>

                <li class="nav-item dropdown">
                    <fmt:message key="navbar.language" var="language"/>
                    <a class="nav-link dropdown-toggle" href="/" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        ${language}
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                        <a class="dropdown-item locale" locale="en_US" href="javascript:void(0)">English</a>
                        <a class="dropdown-item locale" locale="uk_UA" href="javascript:void(0)">Українська</a>
                    </div>
                </li>

            </ul>

            <span class="navbar-text ">
                <c:if test="${not empty user}">
                    ${user.firstName} ${user.lastName} / <a href="/rest/logout"> <c:out value="${log_out}"/></a>
                </c:if>
                <c:if test="${empty user}">
                    <a href="/rest/login" role="button" class="btn btn-outline-info btn-sm">${log_in}</a>
                </c:if>
                <%--<button id="localeBtn" role="button" class="btn btn-outline-info btn-sm">Set English</button>--%>
            </span>
        </div>


    </nav>
</body>
</html>
