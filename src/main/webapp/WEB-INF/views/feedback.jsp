<!-- feedback -->
<div class="feedback_form">
	<a href="" class="feedback_link"><span>Напишите свой отзыв, мы его рассмотрим!</span></a>
    <div class="fbd_form">
    	<textarea placeholder="Введите текст сообщения..."></textarea>
        <input type="text" class="text_inp" placeholder="E-mail" />
        <button>Отправить</button>
    </div>
</div>

<script>
	$(document).ready(function() {
		$(".feedback_link").toggle(
			function(){
				$(this).animate({
					'width': 260
				}).addClass("feedback_header");
				$(".fbd_form").delay(500).slideDown();
				return false
			},
			function(){
				$(".fbd_form").slideUp();
				$(this).delay(500).animate({
					'width': 39
				},function(){$(this).removeClass("feedback_header")});
				
				return false
			}
		);
	});		
</script>
