<%@page import="ua.com.itinterview.web.command.QuestionCommand"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html;charset=UTF-8" %> 
<html>
<head>
	<title>${oneQuestionCommand.title}</title>
</head>
<body>
<table>
<h1>${oneQuestionCommand.question}</h1>
  <c:forEach var="comments" items="${commentsForQuestion}">
	<tr>
		<td>Автор: "${comments.authorName}"</td>
	</tr>
	<tr>
		<td>"${comments.commentText}"</td>
	</tr>
	<tr>
		<td>Рейтинг:"${comments.rate}"</td>
	</tr>
 </c:forEach>
</table>
</body>
</html>