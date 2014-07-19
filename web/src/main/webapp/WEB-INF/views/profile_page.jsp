<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set scope="request" var="mainLink" value="${pageContext.request.contextPath}/interview/my" />
<jsp:include page="header.jsp"/>

<script>
    $(document).ready(function () {
        $(".pass_data .jNiceCheckbox").click(function () {
            $(this).parents("dl").next().slideToggle();
        });
    });
</script>
<!-- main -->
<div id="main">
    <div class="profile_page">
        <!-- photo -->
        <div class="profile_photo">
            <img width="385" height="405" alt="" src="<c:url value='/resources/img/big_photo_m.jpg'/>" />
            <c:if test="${userEditProfileCommand != null}">
                <a href="">Загрузить новую фотографию</a>
            </c:if>
        </div>
        <!-- end photo -->

        <c:choose>
            <c:when test="${userCommand != null}">
                <c:set var="formCommandName" value="userCommand" />
            </c:when>
            <c:when test="${userEditProfileCommand != null}">
                <c:set var="formCommandName" value="userEditProfileCommand" />
            </c:when>
        </c:choose>

        <!-- form -->
        <div class="profile_edit_form">
            <form:form action="${pageContext.request.contextPath}/user/${userEditProfileCommand.id}/save" method="post"
                       commandName="${formCommandName}" class="jNice">
                <fieldset class="name_data">
                    <dl>
                        <dt>Имя пользователя:</dt>
                        <c:choose>
                            <c:when test="${userCommand != null}">
                                <dd><a id="name" href="${pageContext.request.contextPath}/user/${userCommand.id}/edit"
                                       class="edit_link">
                                ${userCommand.name}
                                </a></dd>
                            </c:when>
                            <c:when test="${userEditProfileCommand != null}">
                                <form:input type="text" class="text_inp" placeholder="Имя пользователя" path="name"/>
                                <form:errors path="name" cssStyle="color: #ff0000"/>
                            </c:when>
                        </c:choose>
                    </dl>
                </fieldset>
                <fieldset class="email_data">
                    <dl>
                        <dt>Email:</dt>
                        <c:choose>
                            <c:when test="${userCommand != null}">
                                <dd><a id="email" href="${pageContext.request.contextPath}/user/${userCommand.id}/edit"
                                       class="edit_link">
                                ${userCommand.email}
                                </a></dd>
                            </c:when>
                            <c:when test="${userEditProfileCommand != null}">
                                <form:input type="text" class="text_inp" placeholder="Email" path="email"/>
                                <form:errors path="email" cssStyle="color: #ff0000"/>
                            </c:when>
                        </c:choose>
                    </dl>
                </fieldset>
                <fieldset class="sex_data">
                    <c:if test="${userEditProfileCommand != null}">
                        <button type="submit">Save</button>
                    </c:if>
                </fieldset>
            </form:form>
            <form:form action="${pageContext.request.contextPath}/user/${userCommand.id}/change_password" method="post"
                       commandName="changePasswordCommand" class="jNice">
                <fieldset class="pass_data">
                    <dl>
                        <dt><input type="checkbox" name="change_pass"/></dt>
                        <dd>Изменить пароль</dd>
                    </dl>
                    <div class="edit_block change_pass">
                        <form:input path="userId" type="hidden" value="${userCommand.id}"/>
                        <form:input path="oldPassword" type="password" class="text_inp"
                                    placeholder="Старый пароль"/><br>
                        <form:input path="newPassword" type="password" class="text_inp" placeholder="Новый пароль"/><br>
                        <form:input path="confirmPassword" type="password" class="text_inp" placeholder="Новый пароль"/>
                        <button type="submit" id="submitPasswordBtn">Отправить</button>
                    </div>
                </fieldset>
            </form:form>
        </div>
        <!-- end form -->

        <div class="clear"></div>
    </div>
</div>
<!-- end main -->
<%@ include file="footer.jsp" %>