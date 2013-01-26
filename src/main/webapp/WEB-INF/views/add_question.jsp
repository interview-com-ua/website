<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<c:choose>
<c:when test="${edit}">
	<title>Редактировать вопрос</title>
	</head>
	<body>
		<form:form action="../${questionId}/edit" method="post"
			commandName="questionCommand">
			<legend> Редактировать вопрос </legend>
			<form:input type="text" path="question" />
			<input type="submit" value="Сохранить" />
		</form:form>
</c:when>
<c:otherwise>
<title>Добавить вопрос</title>
	</head>
	<body>
		<form:form action="../${interviewId}/add_question" method="post"
			commandName="questionCommand">
			<legend> Разместить вопрос </legend>
			<form:input type="text" path="question" />
			<input type="submit" value="Добавить вопрос" />
		</form:form>
	</c:otherwise>
</c:choose>
	</body>
</html>