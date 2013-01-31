<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<title>Обратная связь</title>
</head>
<body>
 <form:form action="../add" method="post"
			commandName="feedbackCommand">
  <fieldset>
	<legend>Обратная связь</legend>
		<table cellpadding="3" >
		 <tbody>
			<tr><td><strong>Электронная почта*</strong></td><td><input type="text" name="email"></td></tr>
			<tr><td><strong>Текст сообщения</strong></td><td><input type="text" style="width:200px ; height:100px" name="feedbackText"></td></tr>
			<tr><td><input type="submit" value="Отправить" /></td></tr>
		 </tbody>
		</table>
</fieldset>
</form:form>
</body>
</html>				