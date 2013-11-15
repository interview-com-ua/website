<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<title>Добавить комментарий</title>
</head>
	<body>
		<form:form action="../${questionId}/add_comment" method="post"
			commandName="commentCommand">
			<fieldset>
				<legend>Добавить комментарий</legend>
				<table cellpadding="3">
					<tbody>
						<tr>
							<td>
								<strong>Имя</strong>
							</td>
							<td>
								<form:input type="text" size="30px" path="authorName"/>
							</td>
							<td>
								<form:errors path="authorName" cssStyle="color: #ff0000"/>
							</td>							
						</tr>
						<tr>
							<td>
								<strong>Электронная почта</strong>
							</td>
							<td>
								<form:input type="text" size="30px" path="email"/>
							</td>
							<td>
								<form:errors path="email" cssStyle="color: #ff0000"/>
							</td>							
						</tr>
						<tr>
							<td>
								<strong>Ссылка на юзерпик</strong>
							</td>
							<td>
								<form:input type="text" size="30px" path="userpicUrl"/>
							</td>							
						</tr>
						<tr>
							<td>
								<strong>Текст комментария</strong>
							</td>
							<td>
								<form:textarea rows="5" cols="30" path="commentText"/>
							</td>
							<td>
								<form:errors path="commentText" cssStyle="color: #ff0000"/>
							</td>							
						</tr>
					</tbody>
				</table>
				<input type="submit" value="Отправить комментарий" />
			</fieldset>
		</form:form>
	</body>
</html>