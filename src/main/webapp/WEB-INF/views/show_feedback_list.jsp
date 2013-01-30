<%@page import="ua.com.itinterview.web.command.QuestionCommand"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html;charset=UTF-8" %> 
<html>
<head>
	<title>Feedback</title>
</head>
<body>
<p>Hello, I`m FEEDBACK</p>
<table border="1">
<tr>
	<td>Время</td>
	<td>Проверка</td>
	<td>Отзыв</td>
</tr>
  <c:forEach var="feedback" items="${feedbackList}">
	  <tr>
	     <td>${feedback.getCreateTime()}</td>
	     <td>${feedback.isChecked()}</td>
	     <td>${feedback.getFeedbackText()}</td>  
	  </tr>
  </c:forEach>
</table>
</body>
</html>