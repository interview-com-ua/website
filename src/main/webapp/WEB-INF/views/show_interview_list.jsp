<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Список всех интервью</title>
</head>
<body>
<form:form action="/it-interview/interview/list" method="post" commandName="interviewCommand">
    <form:select path="user.id" items="${users}"></form:select>
    <form:button ></form:button>
</form:form>
<table width="100%" cellpadding="0" cellspacing="1" border="0">
    <tr bgcolor="cccccc">
        <th valign="top" width="14%" align="center"><strong>Id</strong></th>
        <th valign="top" width="14%" align="center"><strong>FeedBack</strong></th>
        <th valign="top" width="14%" align="center"><strong>User</strong></th>
        <th valign="top" width="14%" align="center"><strong>Date</strong></th>
    </tr>
    <c:forEach var="interview" items="${list}">
    <tr>
        <td><c:out value="${interview.id}"/></td>
        <td><c:out value="${interview.feedback}"/></td>
        <td><c:out value="${interview.user.name}"/></td>
        <td><c:out value="${interview.created}"/></td>
        </c:forEach>
</table>
</body>
</html>