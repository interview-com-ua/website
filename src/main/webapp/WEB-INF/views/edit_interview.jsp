<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page pageEncoding="UTF-8"%>
<%@ page session="false"%>
<html>
<head>
<title>Редактирование интервью</title>
</head>
<body>
	<legend><h3>Редактировать интервью</h3></legend>	
	<form:form action="../{interviewId}/edit" method="post"
		commandName="interviewCommand">
		<fieldset>
		<legend>Редактировать интервью</legend> 		
		<form:textarea cols="40" rows="15" path="feedback"/>	
		<h3>${userName}</h3>>	
		<input type="hidden" name="name" value="${userName}" />				
		<input type="submit" value="Изменить" />		
		</fieldset>
	</form:form>
</body>
</html>