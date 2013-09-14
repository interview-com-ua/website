<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=8"/>
	<title>${title}</title>
    <script type="text/javascript" src="<c:url value="/resources/js/jquery-1.7.1.min.js"/>" ></script>
    <script type="text/javascript" src="<c:url value="/resources/js/jquery.jNice.js"/>" ></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/style.css"/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/jNice.css"/>" />
    <!--Needed by interview list-->
    <script type="text/javascript" src="<c:url value="/resources/js/jquery.fancybox.js?v=2.1.4"/>" ></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/jquery.fancybox.css?v=2.1.4"/>" media="screen" />

    <!--[if IE]><script type="text/javascript" src="<c:url value="/resources/js/box-shadow.min.js"/>" ></script><![endif]-->
	<script>
		$(document).ready(function() {
			var content_height = $(document).height() - $("#header").height() - $("#footer").height() - $(".user_block").height() - 28;
			$("#main").css({"min-height": content_height});


			$("input[type=text], textarea").each(function () {
				var default_value = this.value;
				$(this).css('color', '#808080'); // this could be in the style sheet instead
				$(this).focus(function () {
					if (this.value == default_value) {
						this.value = '';
						$(this).css('color', '#555');
					}
				});
				$(this).blur(function () {
					if (this.value == '') {
						$(this).css('color', '#808080');
						this.value = default_value;
					}
				});
			});

		});
	</script>
</head>
<body>
	<div id="page">
        <!-- header -->
        <div id="header">
            <!-- logo -->
            <h1 class="logo"><a href="#">Interview It</a></h1>
            <!-- end logo -->
        	<div class="header_nav">
            	<div class="header_nav_ins">
                	<div class="wrapper">
                    	<!-- main menu -->
                    	<ul id="nav">
                        	<li class="menu_item ${mainActive}">
                            	<a href="${mainLink}">Главная</a>
                                <span class="item_shadow"></span>
                            </li>
                            <li class="menu_item ${aboutActive}">
                            	<a href="">О нас</a>
                                <span class="item_shadow"></span>
                            </li>
                            <li class="menu_item ${developersActive}">
                            	<a href="">История создания</a>
                                <span class="item_shadow"></span>
                            </li>
                        </ul>
                    	<!-- end main menu -->
                        
                        <!-- registration button -->
                        <a href="" class="registration ${registerActive}">Регистрация</a>
                        <!-- end registration button -->
                    </div>
                </div>
            </div>
        </div>
        <!-- end header -->

        <sec:authorize access="isAnonymous()">
            <jsp:include page="user_block_anonymous.jsp" />
        </sec:authorize>
        <sec:authorize access="isAuthenticated()">
            <jsp:include page="user_block_authorized.jsp" />
        </sec:authorize>
        