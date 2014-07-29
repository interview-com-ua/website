<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- user block -->
<div class="user_block">
    <div class="user_block_left">
        <div class="user_block_right">
            <!-- login form -->
            <div class="login_form">
                <form action="${pageContext.request.contextPath}/j_spring_security_check" method="post" class="jNice">
                    <div class="field user_name">
                        <div class="input_wrapper">
                            <div class="input_inner">
                                <label>
                                    <input type="text" name="j_username" class="text_inp" placeholder="Имя пользователя"
                                           value=""/>
                                </label>
                            </div>
                        </div>
                    </div>

                    <div class="field user_pass">
                        <div class="input_wrapper">
                            <div class="input_inner">
                                <label>
                                    <input type="password" name="j_password" class="text_inp" placeholder="**********"
                                           value=""/>
                                </label>
                            </div>
                        </div>
                    </div>

                    <div class="field remember_me">
                        <div class="remember_checkbox">
                            <label>
                                <input type="checkbox" name="_spring_security_remember_me" checked="checked"/>
                            </label>
                        </div>
                        <div class="remember_label">
                            <label>Запомнить меня на этом компьютере</label>
                            <a href="" class="forget_link">
                                <c:if test="${not empty param.login_error}">
                                    <span style="color: red">Invalid login or password</span>
                                </c:if>
                                Забыли пароль?</a>
                        </div>
                    </div>

                    <input id="loginBtn" type="submit" class="enter_but" value="Вход"/>
                </form>
            </div>
            <!-- end login form -->
        </div>
    </div>
</div>
<!-- end user block -->