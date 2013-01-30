<%@page import="ua.com.itinterview.web.command.QuestionCommand"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html;charset=UTF-8" %> 
<html>
<head>
	<title>Список всех интервью</title>
</head>
<body>
<table width="100%" cellpadding="0" cellspacing="1" border="0">
<tr bgcolor="cccccc" >
<th valign="top" width="14%" align="center" ><strong>FeedBack</strong></th>
<th valign="top" width="14%" align="center" ><strong>User</strong></th>
<th valign="top" width="14%" align="center" ><strong>Date</strong></th>
</tr>
<c:forEach var="interview" items="${list}">
<tr>
<td><c:out value="${interview.feedback}"/></td>
<td><c:out value="${interview.user}"/> </td>
<td><c:out value="${interview.created}"/> </td>
</c:forEach>
</table> 
</body>
</html>