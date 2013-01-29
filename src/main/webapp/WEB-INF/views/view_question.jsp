
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<title>Просмотр вопросов</title>
</head>
<body>
	<h1>${oneQuestionCommand.getQuestion()}</h1>
	
		<c:forEach var="comment" items="${commentsForQuestion}">
			
			<b>${comment.authorName}</b>
			<p>${comment.commentText}</p>
		</c:forEach>
</body>
</html>