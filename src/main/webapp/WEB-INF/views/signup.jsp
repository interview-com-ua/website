<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%@page contentType="text/html;charset=UTF-8" %> 
<html>
	<head>
		<title>Регистрация пользователя</title>
	</head>
	<body>
		<form:form action="../user/create" method="post" commandName="userCommand">
			<fieldset>
			    <legend>Личная информация</legend>
			    <table cellpadding="3">
			      <tbody><tr><td width="150px"><strong>Имя пользователя</strong></td><td>
			      	<c:choose >
			      		<c:when test="${!view}"> 
				      		<label>${userCommand.userName}</label>
				      	</c:when>
				      	<c:otherwise>
				      		<form:input type="text" path="userName"/></td></tr>
				      	</c:otherwise>
			      	</c:choose>
			      <tr><td><strong>Электронная почта</strong></td><td><form:input type="text" path="email"/></td></tr>
			      <tr><td><strong>Имя</strong></td><td><form:input type="text" path="name"/></td></tr>
			      <tr><td><strong>Пароль</strong></td><td><form:password path="password"/></td></tr>
			      <tr><td><strong>Подтвердить пароль</strong></td><td><form:password path="confirmPassword"/></td></tr>
			    </tbody></table>
			    <input type="submit" value="Зарегистрировать" />
			</fieldset>
		</form:form>
	</body>
</html>