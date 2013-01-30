<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
	<head>
		<title>Поиск вопросов</title>
	</head>
	<body>
 
		<form:select path="companies">
    		<form:options items="${companies}" itemLabel="name" itemValue="name" />
		</form:select> 

		<form:select path="positions">
    		<form:options items="${positions}" itemLabel="name" itemValue="name"/>
		</form:select> 

		<form:select path="technologies">
    		<form:options items="${technologies}" itemLabel="name" itemValue="name"/>
		</form:select> 

		<c:forEach var="question" items="${questions}">
			<div>
				${question.question}
	      	</div>
		</c:forEach>

	</body>
</html>