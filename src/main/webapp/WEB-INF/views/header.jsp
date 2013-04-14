<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=8"/>
	<title>${title}</title>
    <link rel="stylesheet" type="text/css" href="resources/css/style.css" />
    <link rel="stylesheet" type="text/css" href="resources/css/jNice.css" />
    <script type="text/javascript" src="resources/js/jquery-1.7.1.min.js"></script> 
    <script type="text/javascript" src="resources/js/jquery.jNice.js"></script> 
    <!--[if IE]><script type="text/javascript" src="js/box-shadow.min.js"></script><![endif]-->
	<script>
		$(document).ready(function() {
			$(".menu_item").click(function(){
				$(".menu_item").removeClass("active");
				$(".menu_item span").hide();
				$(this).addClass("active");
				$(this).next().children("span").css({"display":"block"});
				return false
			});
			
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
                        	<li class="menu_item">
                            	<a href="">Главная</a>
                                <span class="item_shadow"></span>
                            </li>
                            <li class="menu_item">
                            	<a href="">О нас</a>
                                <span class="item_shadow"></span>
                            </li>
                            <li class="menu_item">
                            	<a href="">История создания</a>
                                <span class="item_shadow"></span>
                            </li>
                        </ul>
                    	<!-- end main menu -->
                        
                        <!-- registration button -->
                        <a href="" class="registration active">Регистрация</a>
                        <!-- end registration button -->
                    </div>
                </div>
            </div>
        </div>
        <!-- end header -->
        
        <!-- user block -->
        <div class="user_block">
        	<div class="user_block_left">
            	<div class="user_block_right">
                	<!-- login form -->
                	<div class="login_form">
                    	<form action="" class="jNice">
                        	<div class="field user_name">
                                <div class="input_wrapper">
                                    <div class="input_inner">
                                        <input type="text" class="text_inp" placeholder="Имя пользователя" value="" />
                                    </div>
                                </div>
                            </div>    

                        	<div class="field user_pass">
                                <div class="input_wrapper">
                                    <div class="input_inner">
                                        <input type="text" class="text_inp" placeholder="**********" value="" />
                                    </div>
                                </div>
                            </div>  
                            
                            <div class="field remember_me">
                            	<div class="remember_checkbox"><input type="checkbox" checked="checked" /></div>
                                <div class="remember_label">
                                	<label>Запомнить меня на этом компьютере</label>
                                	<a href="" class="forget_link">Забыли пароль?</a>
                                </div>
                            </div>  
                            
                            <input type="submit" class="enter_but" value="Вход" />
                        </form>
                    </div>
                	<!-- end login form -->
                </div>
            </div>
        </div>
        <!-- end user block -->
        