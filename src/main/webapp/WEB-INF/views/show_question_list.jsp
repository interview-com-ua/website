<%@page import="ua.com.itinterview.web.command.QuestionCommand"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html;charset=UTF-8" %> 
<html>
<head>
	<title>Список вопросов для собеседования</title>
</head>
<body>
<table>
  <c:forEach var="question" items="${questionList}">
	  <tr>
	     <td>${question.question}</td>  
	  </tr>
  </c:forEach>
</table>
</body>
</html>