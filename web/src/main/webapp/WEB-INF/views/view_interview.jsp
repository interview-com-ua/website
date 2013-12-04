<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

      	<c:set var="pageTitle" value="Просмотр собеседования" />
		<c:set var="formAction"
			value="${pageContext.request.contextPath}/interview/my" />
		<c:set var="formMethod" value="get" />
		<c:set var="formSubmit" value="Вернуться к списку собеседований" />

<%@ include file="header.jsp" %>

        <!-- main -->
        <div id="main">
            <!-- TODO css class view interview -->
            <div >
                <form:form action="${ formAction }" method="${ formMethod }"commandName="interviewCommand" class="jNice">
                    <fieldset>
                        <legend>${ pageTitle }</legend>
                               <label>${interviewCommand.city.cityName}</label>
                               <label>${interviewCommand.position.name}</label>
                               <label>${interviewCommand.company.name}</label>
                               <label>${interviewCommand.technology.name}</label>
                               <label>${interviewCommand.feedback}</label>
                        <div>
                             <input type="submit"  value="${formSubmit }" />
                        </div>
                    </fieldset>
                </form:form>
            </div>

        </div>
        <!-- end main -->
<%@ include file="footer.jsp" %>
