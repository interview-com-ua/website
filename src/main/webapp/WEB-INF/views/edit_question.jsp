<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page contentType="text/html;charset=UTF-8"%>
<html>
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
</html>