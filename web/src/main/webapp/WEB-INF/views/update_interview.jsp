<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

		<c:set var="pageTitle" value="Редактировать собеседование" />
		<c:set var="formAction"
			value="${pageContext.request.contextPath}/interview/${interviewCommand.id}/edit" />
		<c:set var="formMethod" value="post" />
		<c:set var="formSubmit" value="Сохранить" />

<%@ include file="header.jsp" %>

 <!-- main -->
  <c:if test="${feedbackMessage != null}">
       <div>${feedbackMessage}</div>
  </c:if>
        <div id="main">
            <!--TODO css class  -->
            <div class="registration_form">
                <form:form action="${ formAction }" method="${formMethod}" commandName="interviewCommand" class="jNice">
                    <fieldset>
                        <legend>${ pageTitle }</legend>

                           <label>${interviewCommand.city.cityName}</label>
                           <form:select path="city" >
                               <form:options items="${listCity}" itemValue="id" itemLabel="cityName"/>
                               <form:errors path="city" cssClass="color: #ff0000" />
                           </form:select>
                           <label>${interviewCommand.position.name}</label>
                           <form:select path="position">
                                <form:options items="${listPosition}" itemValue="id" itemLabel="name"/>
                                <form:errors path="position" cssClass="color: #ff0000" />
                           </form:select>
                           <label>${interviewCommand.company.name}</label>
                           <form:select path="company">
                                <form:options items="${listCompany}" itemValue="id" itemLabel="name"/>
                                <form:errors path="company" cssClass="color: #ff0000" />
                           </form:select>
                           <label>${interviewCommand.technology.name}</label>
                           <form:select path="technology">
                                <form:options items="${listTechnology}" itemValue="id" itemLabel="name"/>
                                <form:errors path="technology" cssClass="color: #ff0000" />
                           </form:select>

                            <label>${interviewCommand.feedback}</label>
                           <form:textarea cols="40" rows="15" path="feedback" />
                                <form:errors path="feedback" cssClass="color: #ff0000" />
                          <div>
                             <input type="submit"  value="${formSubmit }" />
                           </div>
                    </fieldset>
                </form:form>
            </div>
        </div>
  <!-- end main -->
<%@ include file="footer.jsp" %>