<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"  %>
<%@page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <title> Добавить вопрос</title>
    </head>
        <body>
            <form:form action="../${interviewId}/add_question" method="post" commandName="questionCommand">
                <legend> Разместить вопрос </legend>                                                                                                                    
            <form:input type="text" path="question"/>
                	<input type="submit" value="Добавить вопрос" />                                          
            </form:form>
            
        </body>
</html>