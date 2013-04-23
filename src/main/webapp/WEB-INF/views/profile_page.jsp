<jsp:include page="header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script>
    $(document).ready(function() {
        $(".profile_edit_form a").click(function(){
            $(this).parents("dl").next().slideToggle();
            return false
        });

        $(".pass_data .jNiceCheckbox").click(function(){
            $(this).parents("dl").next().slideToggle();
        });

        $(".registration").click(function(){
            $(this).toggleClass("active");
            $(".menu_item").removeClass("active");
            $(".menu_item span").hide();
            return false
        });
    });
</script>
<!-- main -->
<div id="main">
    <div class="profile_page">
        <!-- photo -->
        <div class="profile_photo">
            <img width="385" height="405" alt="" src="<c:url value='/resources/img/big_photo_m.jpg'/>" />
            <a href="">Загрузить новую фотографию</a>
        </div>
        <!-- end photo -->

        <!-- form -->
        <div class="profile_edit_form">
            <form:form action="${pageContext.request.contextPath}/user/${userCommand.id}/edit" method="post" commandName="userCommand" class="jNice">
                <fieldset class="login_data">
                    <dl>
                        <dt>Логин: </dt><dd><a href="" class="edit_link"><form:label path="userName" /></a></dd>
                    </dl>
                    <div class="edit_block">
                        <input type="text" class="text_inp" placeholder="Логин" />
                        <button>Отправить</button>
                    </div>
                </fieldset>
                <fieldset class="name_data">
                    <dl>
                        <dt>Имя пользователя: </dt><dd><a href="" class="edit_link"><form:label path="name" /></a></dd>
                    </dl>
                    <div class="edit_block">
                        <input type="text" class="text_inp" placeholder="Имя пользователя" />
                        <button>Отправить</button>
                    </div>
                </fieldset>
                <fieldset class="email_data">
                    <dl>
                        <dt>Email: </dt><dd><a href="" class="edit_link"><form:label path="email" /></a></dd>
                    </dl>
                    <div class="edit_block">
                        <input type="text" class="text_inp" placeholder="Email" />
                        <button>Отправить</button>
                    </div>
                </fieldset>
                <fieldset class="sex_data">
                    <label>Пол</label>
                    <input type="radio" name="sex" id="male" /><span class="label">M</span>
                    <input type="radio" name="sex" id="female" /><span class="label">Ж</span>
                </fieldset>
                <fieldset class="pass_data">
                    <dl>
                        <dt><input type="checkbox" name="change_pass" /> </dt><dd>Изменить пароль</dd>
                    </dl>
                    <div class="edit_block change_pass">
                        <input type="text" class="text_inp" placeholder="Старый пароль" /><br>
                        <input type="text" class="text_inp" placeholder="Новый пароль" /><br>
                        <input type="text" class="text_inp" placeholder="Новый пароль" />
                        <button>Отправить</button>
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