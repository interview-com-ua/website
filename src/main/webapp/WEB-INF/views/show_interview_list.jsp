<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set scope="request" var="mainActive" value="active" />
<jsp:include page="header.jsp"/>
<script>
    $(document).ready(function () {
        $('.fancybox').fancybox();
    });
</script>
<!-- main -->
<div id="main" class="your_interview">
    <!-- your interview -->
    <h1>
        <a href="#add_smth" class="add_interview fancybox">Добавить собеседование</a>
        Список Ваших собеседований
    </h1>

    <div class="clear"></div>

    <c:forEach var="interview" items="${interviewList}" varStatus="currN">
        <div class="listing">
            <ul>
                <!-- one item -->
                <li>
                    <div class="ins">
                        <div class="interview_date">
                            ${interview.created}
                        </div>
                        <div class="interview_company_logo">
                            <img alt="" width="60" height="42" src="${interview.company.logoURL}"/>
                        </div>
                        <div class="interview_company_name"><a href="">${interview.company.name}</a></div>
                        <div class="interview_company_city"><a href="">${interview.city.cityName}</a></div>
                        <div class="interview_company_vacancy"><a href="">${interview.position.name}</a></div>
                        <div class="interview_questions">
                            <span>${interview.questionCount}</span>
                            вопросов
                        </div>
                    </div>
                </li>
                <!-- end one item -->
            </ul>
        </div>
        <!--  end listing -->
    </c:forEach>

    <!-- paginator -->
    <div class="paginator">
        <ul>
            <li><a href="">&laquo;</a></li>
            <li><a href="">&lt;</a></li>
            <li><a href="" class="current">1</a></li>
            <li><a href="">2</a></li>
            <li><a href="">3</a></li>
            <li><a href="">4</a></li>
            <li><a href="">5</a></li>
            <li>...</li>
            <li><a href="">&gt;</a></li>
            <li><a href="">&raquo;</a></li>
        </ul>
    </div>
    <!-- end paginator -->
</div>
<!-- end main -->
<%@ include file="footer.jsp" %>