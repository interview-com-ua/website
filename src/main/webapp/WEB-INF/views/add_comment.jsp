<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<title>Добавить комментарий</title>
</head>
	<body>
		<form:form action="../question/${questionId}/add_comment" method="get"
			commandName="">
			<form:input type="text" path="" title=""/>
			<form:input type="text" path="" />
			<input type="submit" value="Отправить комментарий" />
		</form:form>





	</body>
</html>