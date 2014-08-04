<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

        <c:set var="pageTitle" value="Добавить собеседование" />
		<c:set var="formAction"
			value="${pageContext.request.contextPath}/interview/add" />
		<c:set var="formMethod" value="post" />
		<c:set var="formSubmit" value="Зарегистрировать" />

<%@ include file="header.jsp" %>
<!-- main -->
        <div id="main">
            <div class="registration_form">
                <form:form action="${ formAction }" method="${formMethod}" commandName="interviewCommand" class="jNice">
                    <fieldset>
                        <legend>${ pageTitle }</legend>
                          Город :
                           <form:select path="city">
                               <form:options items="${listCity}" itemValue="id" itemLabel="cityName"/>
                           </form:select>
                           <form:errors path="city" cssClass="color: #ff0000" />
                           Позиция :
                           <form:select path="position">
                               <form:options items="${listPosition}" itemValue="id" itemLabel="name"/>
                           </form:select>
                           <form:errors path="position" cssClass="color: #ff0000" />
                           Компания :
                           <form:select path="company">
                               <form:options items="${listCompany}" itemValue="id" itemLabel="name"/>
                           </form:select>
                           <form:errors path="company" cssClass="color: #ff0000" />
                           Технология :
                           <form:select path="technology" >
                                <form:options items="${listTechnology}" itemValue="id" itemLabel="name"/>
                           </form:select>
                           <form:errors path="technology" cssClass="color: #ff0000" />
                           Отзыв :
                           <form:errors path="feedback" cssClass="color: #ff0000" />
                           <form:textarea cols="40" rows="15" path="feedback" placeholder="Отзыв" />
                           <div>
                             <input id="btnSaveInterview" type="submit"  value="${formSubmit }" />
                           </div>
                    </fieldset>
                </form:form>
            </div>
        </div>
  <!-- end main -->
<%@ include file="footer.jsp" %>