<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@page contentType="text/html;charset=UTF-8" %>  
<html>
	<head>	
		<title>Зарегистрироваться</title>
	</head>
	<body>
		<form:form action="../user/signup" method="post" commandName="userCommand">
		<fieldset>
		    <legend>Личная информация</legend>
		    <table cellpadding="3">
		      <tbody>
		      	<tr><td width="150px"><strong>Имя пользователя</strong></td><td><form:input type="text" path= "userName"/></td></tr>
		      	<tr><td><strong>Email</strong></td><td><form:input type="text" path="email"/></td></tr>
		      	<tr><td><strong>Имя</strong></td><td><form:input type="text" path="name"/></td></tr>
		      	<tr><td><strong>Пароль</strong></td><td><form:input type="text" path="password"/></td></tr>
		      	<tr><td><strong>Подтвердить пароль</strong></td><td><form:input type="text" path="confirmPassword"/></td></tr>
		      </tbody>
		    </table>
  		    <input type="submit" value="Зарегистрироваться">
		</fieldset>
		
		</form:form>
	</body>
</html>