<%@page import="java.util.Date" %>
<html>
<body>
<h1>Default page it-interview</h1>
<%
    Date date = new Date();
    out.print(date);
%>

<ul>
    <li><a href="/interview/list">Interview list</a></li>
    <li><a href="/interview/add">Interview add</a></li>
</ul>

</body>
</html>