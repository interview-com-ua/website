<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
	<head>
		<title>Поиск вопросов</title>
	</head>
	<body>
 		<form:form action="../question/search" method="post" commandName="questionSearchCommand">
			<form:select path="companyId">
	    		<form:options items="${companies}" itemLabel="name" itemValue="id" />
			</form:select> 
	
			<form:select path="positionId">
	    		<form:options items="${positions}" itemLabel="name" itemValue="id" />
			</form:select> 
	
			<form:select path="technologyId">
	    		<form:options items="${technologies}" itemLabel="name" itemValue="id"/>
			</form:select> 
	
			<c:forEach var="question" items="${questions}">
				<div>
					${question.question}
		      	</div>		
		    </c:forEach>
		    <input type="submit" value="submit" />
		</form:form>

	</body>
</html>