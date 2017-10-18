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
<fmt:message key="index_page.about_me" var="about_me"/>
<fmt:message key="index_page.task" var="task"/>
<fmt:message key="index_page.db_diagram" var="db_diagram"/>
<fmt:message key="index_page.technology" var="technology"/>

<div class="container">
    <center><h1>${index_page}</h1></center>

    <article>
        <h3>${about_me}</h3>
        <p>
            Моє ім'я Микола Дехтяренко і я студент 4 курсу Києво-Могилянської Академії напряму програмна інженерія.
        </p>
        <h3>${task}</h3>
        <h6>Варіант 5. Система Лікарня.</h6>
        <p>
            Лікар визначає діагноз, робить призначення
            Пацієнту (процедури, ліки, операції). Призначення може виконати
            Медсестра (процедури, ліки) або Лікар (будь-яке призначення). пацієнт
            може бути виписаний з лікарні, при цьому фіксується остаточний
            діагноз.
        </p>

        <h3>${db_diagram}</h3>
        <div class="text-center">
            <img src="/static/pictures/hospital.png" style="width:60%" alt="Database diagram">
        </div>

        <h3>${technology}</h3>
        <ul>
            <li>Java 8</li>
            <li>MySQL</li>
            <li>Java Servlet API</li>
            <li>JDBC API</li>
            <li>Log4j</li>
            <li>Mockito</li>
            <li>Project Lombok</li>
            <li>jUnit</li>
            <li>Bootstrap</li>
            <li>JQuery</li>
            <li>CSS</li>
        </ul>
    </article>

</div>

</body>
</html>
