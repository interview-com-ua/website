<!-- feedback -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="feedback_form">
	<form id="feedback_form" action="#" method="post" class="jNice">
		<a href="" class="feedback_link"><span>Напишите свой отзыв, мы его рассмотрим!</span></a>
		<div class="fbd_form">
			<div id="ftext_error" style="display: none; color: #ff0000;"></div>
			<textarea id="feedback_text" name="feedbackText" placeholder="Введите текст сообщения..." ></textarea>
			
			<div id="femail_error" style="display: none; color: #ff0000;"></div>
			<input id="feedback_email" name="email" class="text_inp" placeholder="E-mail" type="text"/>
			
			<button id="feedback_submit" type="submit">Отправить</button>
		</div>
	</form>	
</div>

<script>
	$(document).ready(function() {
		$(".feedback_link").toggle(function() {
			$(this).animate({
				'width' : 260
			}).addClass("feedback_header");
			$(".fbd_form").delay(500).slideDown();
			return false
		}, function() {
			$(".fbd_form").slideUp();
			$(this).delay(500).animate({
				'width' : 39
			}, function() {
				$(this).removeClass("feedback_header");
				$("#ftext_error").hide();
				$("#femail_error").hide();
				$("#feedback_text").prop("value", "");					
				$("#feedback_email").prop("value", "");
			});

			return false
		});
		
		$("#feedback_form").submit(function(event) {
			event.preventDefault();
			$("#feedback_submit").prop("disabled", true);
			var formData = $("#feedback_form").serialize();
			$.ajax({
				url : "${pageContext.request.contextPath}/feedback/add",
				type: "post",
				data : formData
			}).done(function(errors) {
				var count = 0;
				for(var x in errors) count++;
				
				if (count > 0) {
					if (errors["email"]) {
						$("#femail_error").text(errors["email"]);
						$("#femail_error").fadeIn();
					} else {
						$("#femail_error").fadeOut();
					}
						
					if (errors["feedbackText"]) {
						$("#ftext_error").text(errors["feedbackText"]);
						$("#ftext_error").fadeIn();
					} else {
						$("#ftext_error").fadeOut();
					}	
				} else {
					$(".feedback_link").click();
				}
				
				$("#feedback_submit").prop("disabled", false);
			}).fail(function() {
				$(".feedback_link").click();
				$("#feedback_submit").prop("disabled", false);
			});
		});
	});
</script>
