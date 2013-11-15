<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<title>Обратная связь</title>
</head>
<body>
	<form:form action="${pageContext.request.contextPath}/feedback/add"
		method="post" commandName="feedbackCommand">
		<fieldset>
			<legend>Обратная связь</legend>
			<table cellpadding="3">
				<tbody>
					<tr>
						<td><strong>Электронная почта*</strong></td>
						<td><form:input type="text" placeholder="Отзыв" path="email" />
						</td>
					</tr>
					<tr>
						<td><strong>Текст сообщения</strong></td>
						<td><form:errors path="feedbackText"
								cssStyle="color: #ff0000" /> <form:textarea placeholder="Отзыв"
								path="feedbackText" /></td>
					</tr>
					<tr>
						<td><input type="submit" value="Отправить" /></td>
					</tr>
				</tbody>
			</table>
		</fieldset>
	</form:form>
</body>
</html>
