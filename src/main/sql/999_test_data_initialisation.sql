insert into users (id, userName, name, password, email, sex) 
	values (1, 'vkuchyn', 'Viktor Kuchyn', 'password', 'kuchin.victor@gmail.com', 'MALE');
alter sequence users_id start with 2; 
insert into company (id, companyName, companyPhone, companyLogoUrl, companyAddress, companyWebPage) values
	(1, 'TestCompany', '2223322', 'http://logo.url', 'address', '');
alter sequence company_id start with 2;
insert into city (id, cityName) values (1, 'TestCity');
alter sequence city_id start with 2;
insert into interview (id, userId, feedback, created, company_id, technology_id, position_id, city_id) values 
	(1, 1, 'Cool interview', now(), 1, 1, 1, 1);
alter sequence interview_id start with 2;
insert into interview_question (id, interviewid, title, question, answer, created) 
	values (1,1,'Some question', 'Full question description', 'Some answer', now());
alter sequence interview_question_id start with 2;
insert into comments (id, question_id, rate, authorName, email, commentText, userpicUrl) values 
	(1, 1, 3, 'Viktor Kuchyn', 'kuchin.victor@gmail.com', 'This was realy easy question', 'http://www.gravatar.com/avatar/7d82916977c1b182a232d9c25f833bb4.png');
alter sequence comments_id start with 2;