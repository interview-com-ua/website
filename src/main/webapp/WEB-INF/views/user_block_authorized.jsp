<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!-- user block -->
<div class="user_block">
    <div class="user_block_left">
        <div class="user_block_right">
            <!-- login form -->
            <div class="login_form logged">
                <div class="user_photo">
                    <img alt="" src="resources/img/user_m.jpg"/>
                </div>
                <div class="user_name">
                    <a href="" class="user_name_link"><sec:authentication property="principal.info.name" /> (<sec:authentication property="principal.info.email" />)</a>
                    <a href="" class="quiz_link">Пройти quiz (underline)</a>
                </div>
                <a href="${pageContext.request.contextPath}/j_spring_security_logout" class="logout">Выход</a>
            </div>
            <!-- end login form -->
        </div>
    </div>
</div>
<!-- end user block -->