<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page contentType="text/html;charset=UTF-8"%>
<html>

<c:choose>
<c:when test='${mode == "EDIT"}'>
<head>
	<title>Редактировать вопрос</title>
	</head>
	<body>
		<form:form action="../${questionId}/edit" method="post"
			commandName="questionCommand">
			<legend> Редактировать вопрос </legend>
			<form:input type="text" path="question" />
			<input type="submit" value="Сохранить" />
		</form:form>
		</body>
</c:when>
<c:when test='${mode == "CREATE"}'>
<head>
<title>Добавить вопрос</title>
	</head>
	<body>
		<form:form action="../${interviewId}/add_question" method="post"
			commandName="questionCommand">
			<legend> Разместить вопрос </legend>
			<form:input type="text" path="question" />
			<input type="submit" value="Добавить вопрос" />
		</form:form>
		</body>
	</c:when>
	<c:when test='${mode == "VIEW"}'>
	<head>
	<title>Просмотр вопросов</title>
</head>
<body>
	<h1>${oneQuestionCommand.getQuestion()}</h1>
	
		<c:forEach var="comment" items="${commentsForQuestion}">
			
			<b>${comment.authorName}</b>
			<p>${comment.commentText}</p>
		</c:forEach>
		</body>
	</c:when>
</c:choose>
	
</html>