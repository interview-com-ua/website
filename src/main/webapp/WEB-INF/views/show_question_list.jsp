<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="header.jsp" %>

<div class="listing">
<c:forEach var="question" items="${questionList}">
    <ul>
        <li>
            <div class="ins">
                <div>Название вопроса: "${question.title}"</div>
                <div>Текст вопроса: "${question.question}"</div>
                <div>Ответ:"${question.answer}"</div>
            </div>
       </li>
    </ul>
</c:forEach>
</div>

<%@ include file="footer.jsp" %>