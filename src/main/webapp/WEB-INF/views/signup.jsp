<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<title>Регистрация пользователя</title>
</head>
<body>
	<c:choose>
		<c:when test="${mode == Modes.MODE_CREATE}">
			<c:set var="actionValue"
				value="${pageContext.request.contextPath}/user/create" />
			<c:set var="formMethod" value="post" />
			<c:set var="submitValue" value="Зарегистрировать" />
		</c:when>
		<c:when test="${mode == Modes.MODE_EDIT}">
			<c:set var="actionValue"
				value="${pageContext.request.contextPath}/user/${userCommand.id}/save" />
			<c:set var="formMethod" value="post" />
			<c:set var="submitValue" value="Сохранить" />
		</c:when>
		<c:otherwise>
			<c:set var="actionValue"
				value="${pageContext.request.contextPath}/user/${userCommand.id}/edit" />
			<c:set var="formMethod" value="get" />
			<c:set var="submitValue" value="Редактировать" />
		</c:otherwise>
	</c:choose>
	<form:form action="${actionValue}" method="${formMethod}"
		commandName="userCommand">
		<fieldset>
			<legend>Личная информация</legend>
			<table>
				<tbody>
					<tr>
						<td width="150px"><strong>Имя пользователя</strong></td>
						<td><c:choose>
								<c:when test="${mode == Modes.MODE_CREATE}">
									<form:input type="text" path="userName" />
								</c:when>
								<c:otherwise>
									<label>${userCommand.userName}</label>
								</c:otherwise>
							</c:choose></td>
						<td><form:errors path="userName" cssStyle="color: #ff0000" /></td>							
					</tr>
					<tr>
						<td><strong>Электронная почта</strong></td>
						<td><c:choose>
								<c:when test="${mode == Modes.MODE_CREATE}">
									<form:input type="text" path="email" />
									<td><form:errors path="email" cssStyle="color: #ff0000" /></td>	
								</c:when>
								<c:otherwise>
									<label>${userCommand.email}</label>
								</c:otherwise>
							</c:choose></td>
					</tr>
					<tr>
						<td><strong>Имя</strong></td>
						<td><c:choose>
								<c:when test="${mode != Modes.MODE_VIEW}">
									<form:input type="text" path="name" />
									<td><form:errors path="name" cssStyle="color: #ff0000" /></td>	
								</c:when>
								<c:otherwise>
									<label>${userCommand.name}</label>
								</c:otherwise>
							</c:choose></td>
					</tr>
					<c:if test="${mode != Modes.MODE_VIEW}">
						<tr>
							<td><strong>Пароль</strong></td>
							<td><form:password path="password" /></td>
						</tr>
						<tr>
							<td><strong>Подтвердить пароль</strong></td>
							<td><form:password path="confirmPassword" /></td>
						</tr>
					</c:if>
				</tbody>
			</table>
			<input type="submit" value="${submitValue}" />
		</fieldset>
	</form:form>
</body>
</html>