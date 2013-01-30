<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Добавление интервью</title>
</head>
<body>
<h3>Добавить интервью</h3>
<c:if test="${SUCCESS_MESSAGE != null}">
    <div id="status_message">${SUCCESS_MESSAGE}</div>
</c:if>
<form:form action="../interview/add" method="post" commandName="interviewCommand">
    <fieldset>
        <legend>Добавить интервью</legend>
        <form:textarea cols="40" rows="15" path="feedback"/>
        <div>
            <input type="submit" value="Добавить"/>
        </div>
    </fieldset>
</form:form>
</body>
</html>