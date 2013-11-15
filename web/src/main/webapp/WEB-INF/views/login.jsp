<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter" %>


<html>
<head>
<title>Login</title>
</head>
<body>
	<h1>Login</h1>
	
	<div id="login-error">${error}</div>
 
    <form action="${pageContext.request.contextPath}/j_spring_security_check" method="post">
        <label for="j_username">Username</label>
	<input type="text" name="j_username" id="j_username" />
	<br/>
	<label for="j_password">Password</label>
	<input type="password" name="j_password" id="j_password"/>
	<br/>
	<input type='checkbox' name='_spring_security_remember_me'/> Remember me on this computer.
	<br/>
	<input type="submit" value="Login"/>    
    </form>
</body>
</html>

		