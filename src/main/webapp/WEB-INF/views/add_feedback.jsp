<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<title>Обратная связь</title>
</head>
<body>
 <form action="../affiliates/signup.php" method="post">
  <fieldset>
	<legend>Обратная связь</legend>
		<table cellpadding="3" >
		 <tbody>
			<tr><td width="150px"><strong>Имя пользователя*</strong></td><td><input type="text" name="username"></td></tr>
			<tr><td><strong>Электронная почта*</strong></td><td><input type="text" name="email"></td></tr>
			<tr><td><strong>Тема сообщения*</strong></td><td><input type="text" name="title"></td></tr>
			<tr><td><strong>Текст сообщения</strong></td><td><input type="text" style="width:200px ; height:100px" name="message"></td></tr>
		 </tbody>
		</table>
</fieldset>
</form>
</body>
</html>				