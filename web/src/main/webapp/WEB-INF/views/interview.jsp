<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8"%>

<c:choose>
	<c:when test='${mode == "CREATE"}'>
		<c:set var="pageTitle" value="Добавить собеседование" />
		<c:set var="formAction"
			value="${pageContext.request.contextPath}/interview/add" />
		<c:set var="formMethod" value="post" />
		<c:set var="formSubmit" value="Зарегистрировать" />
	</c:when>
	<c:when test='${mode == "EDIT"}'>
		<c:set var="pageTitle" value="Редактировать собеседование" />
		<c:set var="formAction"
			value="${pageContext.request.contextPath}/interview/${interviewCommand.id}/edit" />
		<c:set var="formMethod" value="post" />
		<c:set var="formSubmit" value="Сохранить" />
	</c:when>
	<c:otherwise>
		<c:set var="pageTitle" value="Просмотр собеседования" />
		<c:set var="formAction"
			value="${pageContext.request.contextPath}/interview/${interviewCommand.id}/edit" />
		<c:set var="formMethod" value="get" />
		<c:set var="formSubmit" value="Редактировать" />
	</c:otherwise>
</c:choose>

<html>
<head>
<title>${ pageTitle }</title>
</head>
<body>
<c:choose>
	<c:when test='${mode == "CREATE" || mode == "EDIT"}'>
	<form:form action="${ formAction }" method="${ formMethod }"
		commandName="interviewCommand">
		<fieldset>
			<legend>${ pageTitle }</legend>

            <form:select path="city">
                <form:options items="${listCity}" itemValue="id" itemLabel="cityName"/>
            </form:select>

            <form:select path="position">
                <form:options items="${listPosition}" itemValue="id" itemLabel="name"/>
            </form:select>

            <form:select path="company">
                <form:options items="${listCompany}" itemValue="id" itemLabel="name"/>
            </form:select>
            <form:select path="technology">
                <form:options items="${listTechnology}" itemValue="id" itemLabel="name"/>
            </form:select>

            <form:textarea cols="40" rows="15" path="feedback" />
			    <div>
			        <form:errors path="feedback" cssClass="error" />
			    </div>
			<div>
			<input type="submit"  value="${ formSubmit }" />
			</div>
		</fieldset>
	</form:form>
	</c:when>
	<c:when test='${mode == "VIEW"}'>
	  	<legend>${ pageTitle }</legend>
	    <label>${interviewCommand.position.name}</label>
	    <label>${interviewCommand.company.name}</label>
	    <label>${interviewCommand.city.cityName}</label>
		<label>${interviewCommand.feedback}</label>
	</c:when>
	</c:choose>
</body>
</html>