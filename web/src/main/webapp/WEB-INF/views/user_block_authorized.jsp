<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- user block -->
<div class="user_block">
    <div class="user_block_left">
        <div class="user_block_right">
            <!-- login form -->
            <div class="login_form logged">
                <div class="user_photo">
                    <img alt="" src="<c:url value='/resources/img/user_m.jpg'/>"/>
                </div>
                <div class="user_name">
                    <a href="${pageContext.request.contextPath}/user/<sec:authentication property="principal.info.id"/>/view"
                       class="user_name_link"><sec:authentication property="principal.info.name"/>
                        (<sec:authentication property="principal.info.email"/>)</a>
                    <a href="${pageContext.request.contextPath}/user/<sec:authentication property="principal.info.id"/>/view"
                       class="quiz_link">Пройти quiz (underline)</a>
                </div>
                <a href="${pageContext.request.contextPath}/j_spring_security_logout" class="logout">Выход</a>
            </div>
            <!-- end login form -->
        </div>
    </div>
</div>
<!-- end user block -->