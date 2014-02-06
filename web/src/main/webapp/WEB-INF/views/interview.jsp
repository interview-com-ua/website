<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:choose>
	<c:when test='${mode == "CREATE"}'>
		<c:set var="pageTitle" value="Добавить собеседование" />
		<c:set var="formAction"
			value="${pageContext.request.contextPath}/interview/add" />
		<c:set var="formMethod" value="post" />
		<c:set var="formSubmit" value="Зарегистрировать" />
	</c:when>
	<c:when test='${mode == "EDIT"}'>
		<c:set var="pageTitle" value="Редактировать собеседование" />
		<c:set var="formAction"
			value="${pageContext.request.contextPath}/interview/${interviewCommand.id}/edit" />
		<c:set var="formMethod" value="post" />
		<c:set var="formSubmit" value="Сохранить" />
	</c:when>
	<c:otherwise>
		<c:set var="pageTitle" value="Просмотр собеседования" />
		<c:set var="formAction"
			value="${pageContext.request.contextPath}/interview/my" />
		<c:set var="formMethod" value="get" />
		<c:set var="formSubmit" value="Вернуться к списку собеседований" />
	</c:otherwise>
</c:choose>
<jsp:include page="header.jsp" />
        <!-- main -->
        <div id="main">
            <!-- registration -->
            <div class="registration_form">
                <form:form action="${ formAction }" method="${ formMethod }"commandName="interviewCommand" class="jNice">
                    <fieldset>
                        <legend>${ pageTitle }</legend>
                        <c:choose>
                             <c:when test='${mode != "VIEW"}'>
                                <form:select path="city" >
                                    <form:options items="${listCity}" itemValue="id" itemLabel="cityName"/>
                                    <form:errors path="city" cssClass="color: #ff0000" />
                                </form:select>
                             </c:when>
                             <c:otherwise>
                                <label>${interviewCommand.city.cityName}</label>
                             </c:otherwise>
                        </c:choose>
                        <c:choose>
                             <c:when test='${mode != "VIEW"}'>
                                <form:select path="position">
                                    <form:options items="${listPosition}" itemValue="id" itemLabel="name"/>
                                    <form:errors path="position" cssClass="color: #ff0000" />
                                </form:select>
                             </c:when>
                             <c:otherwise>
                                <label>${interviewCommand.position.name}</label>
                             </c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test='${mode != "VIEW"}'>
                                <form:select path="company">
                                    <form:options items="${listCompany}" itemValue="id" itemLabel="name"/>
                                    <form:errors path="company" cssClass="color: #ff0000" />
                                </form:select>
                             </c:when>
                             <c:otherwise>
                                <label>${interviewCommand.company.name}</label>
                             </c:otherwise>
                        </c:choose>
                         <c:choose>
                             <c:when test='${mode != "VIEW"}'>
                                <form:select path="technology">
                                    <form:options items="${listTechnology}" itemValue="id" itemLabel="name"/>
                                    <form:errors path="technology" cssClass="color: #ff0000" />
                                </form:select>
                                </c:when>
                                <c:otherwise>
                                    <label>${interviewCommand.technology.name}</label>
                                 </c:otherwise>
                          </c:choose>
                         <c:choose>
                             <c:when test='${mode != "VIEW"}'>
                                <form:textarea cols="40" rows="15" path="feedback" />
                                <form:errors path="feedback" cssClass="color: #ff0000" />
                             </c:when>
                             <c:otherwise>
                                <label>${interviewCommand.feedback}</label>
                             </c:otherwise>
                         </c:choose>
                         <div>
                             <input type="submit"  value="${formSubmit }" />

                         </div>
                    </fieldset>
                </form:form>
            </div>
            <!-- end registration -->
        </div>
        <!-- end main -->
<%@ include file="footer.jsp" %>
