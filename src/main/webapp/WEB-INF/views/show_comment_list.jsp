<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>   
<%@ page contentType="text/html;charset=UTF-8" %> 
<%@ page import="java.util.Date" %>
<html>
	<head>
		<title>Список комментариев</title>
	</head>
	<body>
		<c:forEach var="comment" items="${commentsToPrint}">
			<div>
				<img src="${comment.userpicUrl}" alt="">
				<a href="">${comment.authorName}</a>
				<%-- Replace to comment date which now doesn't exists --%>
				<time><%= new Date() %></time>
				<span> [${comment.rate}]</span>
				<div>${comment.commentText}</div>
	      	</div>
		</c:forEach>
	</body>
</html>