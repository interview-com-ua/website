insert into users (id, userName, name, password, email, sex) 
	values (1, 'vkuchyn', 'Viktor Kuchyn', 'password', 'kuchin.victor@gmail.com', 'male');
alter sequence users_id start with 2; 
insert into interview (id, userId, feedback, created) values (1, 1, 'Cool interview', now());
alter sequence interview_id start with 2;
insert into interview_question (id, interviewid, title, question, answer, created) 
	values (1,1,'Some question', 'Full question description', 'Some answer', now());
alter sequence interview_question_id start with 2;
insert into comments (id, question_id, rate, authorName, email, commentText, userpicUrl) values 
	(1, 1, 3, 'Viktor Kuchyn', 'kuchin.victor@gmail.com', 'This was realy easy question', 'http://www.gravatar.com/avatar/7d82916977c1b182a232d9c25f833bb4.png');
alter sequence comments_id start with 2;