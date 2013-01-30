<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Добавление интервью</title>
</head>
<body>
<legend><h3>Добавить интервью</h3></legend>
<form:form action="../interview/add" method="post"
           commandName="interviewCommand">
    <fieldset>
        <legend>Добавить интервью</legend>
        <form:textarea cols="40" rows="15" path="feedback"/>
        <input type="submit" value="Добавить"/>
    </fieldset>
</form:form>
</body>
</html>